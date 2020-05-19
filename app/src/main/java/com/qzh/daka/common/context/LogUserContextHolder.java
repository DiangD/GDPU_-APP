package com.qzh.daka.common.context;

import android.content.Context;

import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.entity.LogUser;
import com.qzh.daka.entity.vo.LogUserDetails;
import com.qzh.daka.service.LogUserService;
import com.qzh.daka.task.LogUserTask;

import java.util.concurrent.ExecutionException;

public class LogUserContextHolder {
    public static LogUserDetails getContext(Context context) {
        if (LogUserContext.logUserDetails != null) {
            return LogUserContext.logUserDetails;
        } else {
            // 从数据库查询 并设置回去
            LogUser logUser = LogUserService.getLogUser(context, SystemConstant.ID_IN_DB);
            try {
                if (logUser!=null) {
                    LogUserDetails logUserDetails = new LogUserTask().execute(logUser).get();
                    LogUserContext.logUserDetails = logUserDetails;
                    return logUserDetails;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void setContext(LogUserDetails logUserDetails) {
        LogUserContext.logUserDetails = logUserDetails;
    }

    public static void clearContext() {
        if (LogUserContext.logUserDetails != null) {
            LogUserContext.logUserDetails = null;
        }
    }
}
