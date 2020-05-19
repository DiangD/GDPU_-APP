package com.qzh.daka.service;

import android.content.Context;

import com.qzh.daka.entity.LogUser;
import com.qzh.daka.greendao.LogUserDao;

import org.greenrobot.greendao.query.QueryBuilder;

public class LogUserService {

    public static void saveLogUser(Context context, LogUser logUser) {
        DataBaseManager.getDaoSession(context).getLogUserDao().insertOrReplace(logUser);
    }


    public static LogUser getLogUser(Context context, Long id) {
        QueryBuilder<LogUser> builder = DataBaseManager.getDaoSession(context).getLogUserDao().queryBuilder();
        return  builder.where(LogUserDao.Properties.Id.eq(id)).unique();
    }

    public static boolean isEmpty(Context context) {
        long count = DataBaseManager.getDaoSession(context).getLogUserDao().count();
        return count == 0;
    }

    public static void deleteLogUser(Context context) {
        DataBaseManager.getDaoSession(context).getLogUserDao().deleteAll();
    }

}
