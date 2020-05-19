package com.qzh.daka.service;

import android.content.Context;

import com.qzh.daka.entity.DailyHealthDetails;
import com.qzh.daka.greendao.DailyHealthDetailsDao;

import org.greenrobot.greendao.query.QueryBuilder;

public class DailyHealthDetailsService {
    public static void saveDailyHealthDetails(Context context,DailyHealthDetails details) {
        DataBaseManager.getDaoSession(context).getDailyHealthDetailsDao().insertOrReplace(details);
    }

    public static DailyHealthDetails getDailyHealthDetails(Context context, Long id) {
        QueryBuilder<DailyHealthDetails> builder = DataBaseManager.getDaoSession(context).getDailyHealthDetailsDao().queryBuilder();
        return builder.where(DailyHealthDetailsDao.Properties.Id.eq(id)).unique();
    }

    public static boolean isEmpty(Context context) {
        long count = DataBaseManager.getDaoSession(context).getDailyHealthDetailsDao().count();
        return count == 0;
    }

    public static void deleteHealthDetail(Context context) {
        DataBaseManager.getDaoSession(context).getDailyHealthDetailsDao().deleteAll();
    }
}
