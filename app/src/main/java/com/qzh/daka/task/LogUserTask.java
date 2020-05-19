package com.qzh.daka.task;

import android.os.AsyncTask;

import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.common.utils.LoginUtil;
import com.qzh.daka.entity.LogUser;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.LogUserDetails;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;


public class LogUserTask extends AsyncTask<LogUser, Void, LogUserDetails> {

    @Override
    protected LogUserDetails doInBackground(LogUser... logUsers) {
        LogUser logBefore = logUsers[0];
        LogUser logSuccess;
        String stuNum = logBefore.getStudentNum();
        String password = logBefore.getPassword();
        Connection connect = Jsoup.connect(SystemConstant.SYSTEM_LOGON_URL);
        connect.header("User-Agent", SystemConstant.USER_AGENT);
        Connection.Response response;
        try {
            response = connect.timeout(300000).ignoreContentType(true).method(Connection.Method.POST)
                    .data(LoginUtil.loginParamsMapGenerator(stuNum, password))
                    .execute();
        } catch (SocketTimeoutException e) {
            return new LogUserDetails(logBefore, null, null, LogUserOperatedStatus.NETWORK_BUSY);
        } catch (IOException e) {
            return new LogUserDetails(logBefore, null, null, LogUserOperatedStatus.FAIL);
        }
        if (response != null) {
            try {
                Document document = response.parse();
                Map<String, String> cookies = response.cookies();
                if (document != null && cookies != null) {
                    Element logMsg = document.getElementById("ctl00_logmsg");
                    if (logMsg != null) {
                        String logStuNUm = logMsg.text().substring(0, 10);
                        if (!StringUtils.isEmpty(logStuNUm) && stuNum.equals(logStuNUm)) {
                            Elements elements = document.select("li.login_info > a");
                            Element element = elements.get(1);
                            String href = element.attr("href");
                            String key = StringUtils.substringBetween(href, "\\opt_xx_jbxx.aspx?key=", "&fid=7");

                            if (!StringUtils.isEmpty(key)) {
                                logSuccess = LogUser.builder()
                                        .studentNum(stuNum)
                                        .password(password)
                                        .build();
                                return new LogUserDetails(logSuccess, key, cookies, LogUserOperatedStatus.VERIFY_SUCCESS);
                            }
                        }
                    } else {
                        return new LogUserDetails(logBefore, null, null, LogUserOperatedStatus.UNAUTHORIZED);
                    }
                }
            } catch (IOException e) {
                return new LogUserDetails(logBefore, null, null, LogUserOperatedStatus.FAIL);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(LogUserDetails user) {
        super.onPostExecute(user);
    }
}
