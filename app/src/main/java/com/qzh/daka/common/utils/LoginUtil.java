package com.qzh.daka.common.utils;

import java.util.HashMap;
import java.util.Map;

public class LoginUtil {

    private static final String EVENT_TARGET_BLANK = "__EVENTTARGET";
    private static final String VIEW_STATE_BLANK = "__VIEWSTATE";
    private static final String VIEW_STATE_GENERATOR_BLANK = "__VIEWSTATEGENERATOR";
    private static final String EVENT_VALIDATION_BLANK = "__EVENTVALIDATION";
    private static final String EVENT_TARGET = "logon";
    private static final String VIEW_STATE = "/wEPDwUKMTYyMDg3MzEwOA9kFgICAw9kFgQCCQ8PFgIeBFRleHQFPUVzd2lzIOmrmOagoeWtpueUn+e7vOWQiOacjeWKoeW5s+WPsCDlrabnlJ/lt6XkvZznrqHnkIbns7vnu59kZAILDw8WAh8ABU/ljZXkvY3ogZTns7vmlrnlvI865bm/5bee5biC55Wq56a65Yy65aSn5a2m5Z+O5bm/5Lic6I2v56eR5aSn5a2m5a2m55Sf5bel5L2c5aSEZGRkApHRtEp47HU3hwHc7/VDYk7HCWP1VrIoeU831p6jX5o=";
    private static final String VIEW_STATE_GENERATOR = "C2EE9ABB";
    private static final String EVENT_VALIDATION = "/wEdAAR2x90ffMPh62fEUUHFD4Tp1kNwsRYEDqnEZGvD/d7NHmTWfBqM7WrvRN2Hp35y65arCB7eRXhUFaYy1hE/nWj6nK478H4eQaeI8UwPY/TWzZwSA7XuIBUqSutXvspX48U=";
    private static final String LOG_USERNAME_BLANK = "log_username";
    private static final String LOG_PASSWORD_BLANK = "log_password";

    public static Map<String, String> loginParamsMapGenerator(String stuNum,String password) {
        Map<String, String> map = new HashMap<>();
        map.put(EVENT_TARGET_BLANK, EVENT_TARGET);
        map.put(VIEW_STATE_BLANK, VIEW_STATE);
        map.put(VIEW_STATE_GENERATOR_BLANK, VIEW_STATE_GENERATOR);
        map.put(EVENT_VALIDATION_BLANK, EVENT_VALIDATION);
        map.put(LOG_USERNAME_BLANK, stuNum);
        map.put(LOG_PASSWORD_BLANK, password);
        return map;
    }


}
