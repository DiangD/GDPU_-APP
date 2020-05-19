package com.qzh.daka.task;

import android.os.AsyncTask;

import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.entity.ClockInItem;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.ClockInItemsVo;
import com.qzh.daka.entity.vo.LogUserDetails;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ClockInHistoryTask extends AsyncTask<LogUserDetails, Void, ClockInItemsVo> {

    private String url = "http://eswis.gdpu.edu.cn/opt_rc_jkdkcx.aspx?key=%s&fid=20";

    private List<ClockInItem> clockInItems = new ArrayList<>();

    @Override
    protected ClockInItemsVo doInBackground(LogUserDetails... params) {
        LogUserDetails logUserDetails = params[0];
        url = String.format(url, logUserDetails.getKey());
        Connection.Response response;
        try {
            response = getResponse(url, logUserDetails);
        } catch (SocketTimeoutException e) {
            return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.NETWORK_BUSY);
        } catch (IOException e) {
            return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.FAIL);
        }
        try {
            if (response != null) {
                Document document = response.parse();
                Element sysErrMsg = document.getElementById("ctl00_SysErrMsg");
                if (sysErrMsg != null) {
                    if (sysErrMsg.text().contains("未登录") || sysErrMsg.text().contains("无权访问该页面")) {
                        return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.USER_EXPIRE);
                    }
                }
                Elements tableBody = document.select("table#ctl00_cph_right_gv3 >tbody >tr");
                if (tableBody != null) {
                    for (Element element : tableBody) {
                        Elements tds = element.select("td");
                        if (tds.size() > 0) {
                            String modifyDate = tds.get(0).text();
                            String atSchool = tds.get(1).text();
                            String location = tds.get(2).text();
                            String observation = tds.get(3).text();
                            String health = tds.get(4).text();
                            String temp = tds.get(5).text();
                            String describe = tds.get(6).text();
                            ClockInItem clockInItem = new ClockInItem(modifyDate, atSchool, location, observation, health, temp, describe);
                            clockInItems.add(clockInItem);
                        }
                    }
                }
            }
            if (clockInItems.size() > 0) {
                return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.PULL_SUCCESS);
            }
            return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.NETWORK_BUSY);
        } catch (IOException e) {
            return new ClockInItemsVo(clockInItems, LogUserOperatedStatus.FAIL);
        }
    }

    private Connection.Response getResponse(String url, LogUserDetails logUserDetails) throws IOException {
        Connection connect = Jsoup.connect(url);
        Connection.Response response = null;
        if (logUserDetails.getCookies() != null) {
            response = connect.header("User-Agent", SystemConstant.USER_AGENT)
                    .cookies(logUserDetails.getCookies())
                    .ignoreContentType(true)
                    .timeout(300000)
                    .execute();
        }

        return response;
    }
}
