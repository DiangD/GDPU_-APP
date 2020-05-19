package com.qzh.daka.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.common.utils.ClockInUtil;
import com.qzh.daka.entity.DailyHealthDetails;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.LogUserDetails;
import com.qzh.daka.service.DailyHealthDetailsService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

public class ClockInTask extends AsyncTask<LogUserDetails, Void, LogUserDetails> {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public ClockInTask(Context context) {
        this.context = context;
    }

    private String url = "http://eswis.gdpu.edu.cn/opt_rc_jkdk.aspx?key=%s&fid=20";

    @Override
    protected LogUserDetails doInBackground(LogUserDetails... params) {
        LogUserDetails logUserDetails = params[0];
        DailyHealthDetails dailyHealthDetails = DailyHealthDetailsService.getDailyHealthDetails(context, SystemConstant.ID_IN_DB);
        url = String.format(url, logUserDetails.getKey());
        Connection connect = Jsoup.connect(url);
        Connection.Response response;
        try {
            response = getClockInResponse(connect, logUserDetails, dailyHealthDetails);
        } catch (SocketTimeoutException e) {
            logUserDetails.setStatus(LogUserOperatedStatus.NETWORK_BUSY);
            return logUserDetails;
        } catch (IOException e) {
            logUserDetails.setStatus(LogUserOperatedStatus.FAIL);
            return logUserDetails;
        }
        try {
            Document document = response.parse();
            //第一种情况，验证身份过期
            Element sysErrMsg = document.getElementById("ctl00_SysErrMsg");
            if (sysErrMsg!=null) {
                if (sysErrMsg.text().contains("未登录")||sysErrMsg.text().contains("无权访问该页面")) {
                    logUserDetails.setStatus(LogUserOperatedStatus.USER_EXPIRE);
                    return logUserDetails;
                }
            }
            Element rightEMsg = document.getElementById("ctl00_cph_right_e_msg");
            if (rightEMsg!=null&&("打卡成功").equals(rightEMsg.text())) {
                logUserDetails.setStatus(LogUserOperatedStatus.CLOCK_IN_SUCCESS);
                return logUserDetails;
            }
        } catch (IOException e) {
            logUserDetails.setStatus(LogUserOperatedStatus.FAIL);
            return logUserDetails;
        }
        return null;
    }

    private Connection.Response getClockInResponse(Connection connect,LogUserDetails logUserDetails,DailyHealthDetails dailyHealthDetails) throws IOException {
        Connection.Response response = connect
                .header("User-Agent", SystemConstant.USER_AGENT)
                .cookies(logUserDetails.getCookies())
                .ignoreContentType(true)
                .timeout(100000)
                .method(Connection.Method.POST)
                .data(ClockInUtil.BlockInParamsGenerator(dailyHealthDetails))
                .execute();
        Map<String,String> map = ClockInUtil.BlockInParamsGenerator(dailyHealthDetails);
        Log.i("map", JSON.toJSONString(map));
        System.out.println(response.body());
        return response;
    }

}
