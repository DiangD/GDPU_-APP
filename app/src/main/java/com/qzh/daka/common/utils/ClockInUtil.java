package com.qzh.daka.common.utils;

import com.qzh.daka.entity.DailyHealthDetails;

import java.util.HashMap;
import java.util.Map;

public class ClockInUtil {

//     <item>无不适</item>
//        <item>发烧</item>
//        <item>咳嗽</item>
//        <item>气促</item>
//        <item>乏力/肌肉酸痛</item>
//        <item>其它症状</item>

    private static final Map<String, String> HealthBlankMap = new HashMap<String, String>() {
        {
            put("无不适", "ctl00$cph_right$e_health$0");
            put("发烧", "ctl00$cph_right$e_health$1");
            put("咳嗽", "ctl00$cph_right$e_health$2");
            put("气促", "ctl00$cph_right$e_health$3");
            put("乏力/肌肉酸痛", "ctl00$cph_right$e_health$4");
            put("其它症状", "ctl00$cph_right$e_health$5");
        }
    };


    private static final String VIEWSTATE_BLANK = "__VIEWSTATE";
    private static final String VIEWSTATE = "/wEPDwUKLTc2MDkyMDk0Mw9kFgJmD2QWAgIDD2QWDmYPFgIeB1Zpc2libGVoZAIBDw8WAh4EVGV4dAURMTcyMDUwNTI0NiDlrabnlJ9kZAICDw8WAh8AZ2RkAgMPZBYCAgEPFgIfAGhkAgQPZBYKAgMPZBYCAgUPEGRkFgBkAgUPFgIeCWlubmVyaHRtbAWnAjxsaSBjbGFzcz0iMCBzZWxlY3RlZCBzZWwgYWN0Ij48YSBocmVmPSJvcHRfcmNfamtkay5hc3B4P2tleT1mUmU4NUlybzhTclJTMElQJmZpZD0yMCI+5YGl5bq35omT5Y2hPC9hPjwvbGk+DQo8bGkgY2xhc3M9IjEiPjxhIGhyZWY9Im9wdF9yY19qa2RrY3guYXNweD9rZXk9ZlJlODVJcm84U3JSUzBJUCZmaWQ9MjAiPuaJk+WNoeafpeivojwvYT48L2xpPg0KPGxpIGNsYXNzPSIyIj48YSBocmVmPSJvcHRfcmNfZnhzcS5hc3B4P2tleT1mUmU4NUlybzhTclJTMElQJmZpZD0yMCI+6L+U5qCh55Sz6K+3PC9hPjwvbGk+DQpkAgkPFgIfAGdkAgsPZBYGAgEPFgIfAGgWBAIBDxAPFgIeB0NoZWNrZWRnZGRkZAIFDw8WAh8BZWRkAgMPZBYEAgEPPCsAEQEMFCsAAGQCBQ88KwARAQwUKwAAZAIFDxYCHwBnFgwCAQ8PFgIfAWRkZAIHDw8WAh8BBQkyMDIwLzUvMTdkZAIJDw8WAh8BBRUxNzIwNTA1MjQ2LCDpgrHmmbrngJpkZAILDw8WAh8BBQsxMzU1Mzc2NjQwMmRkAiEPZBYGAgEPZBYCAgEPEGRkFgBkAgMPZBYCAgEPEGRkFgBkAgUPZBYCAgEPEGRkFgBkAiUPZBYKZg8PFgIfAQUb5bm/5Lic55yB5r2u5bee5biC5rmY5qGl5Yy6ZGQCAQ8PFgIfAQUw5bm/5Lic55yB5r2u5bee5biC5rmY5qGl5Yy65r2u5bee5aSn6YGT5oCh5pmv5bGFZGQCAg8QZGQWAGQCBw8QZGQWAGQCCg8QZGQWAGQCDQ8PFgIfAWVkZAIFDw8WAh8BBT1Fc3dpcyDpq5jmoKHlrabnlJ/nu7zlkIjmnI3liqHlubPlj7Ag5a2m55Sf5bel5L2c566h55CG57O757ufZGQCBg8PFgIfAQVP5Y2V5L2N6IGU57O75pa55byPOuW5v+W3nuW4gueVquemuuWMuuWkp+WtpuWfjuW5v+S4nOiNr+enkeWkp+WtpuWtpueUn+W3peS9nOWkhGRkGAMFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYIBRpjdGwwMCRjcGhfcmlnaHQkZV9oZWFsdGgkMAUaY3RsMDAkY3BoX3JpZ2h0JGVfaGVhbHRoJDEFGmN0bDAwJGNwaF9yaWdodCRlX2hlYWx0aCQyBRpjdGwwMCRjcGhfcmlnaHQkZV9oZWFsdGgkMwUaY3RsMDAkY3BoX3JpZ2h0JGVfaGVhbHRoJDQFGmN0bDAwJGNwaF9yaWdodCRlX2hlYWx0aCQ1BRpjdGwwMCRjcGhfcmlnaHQkZV9oZWFsdGgkNQUZY3RsMDAkY3BoX3JpZ2h0JGVfY2hhbmdlZAUTY3RsMDAkY3BoX3JpZ2h0JGd2MQ9nZAUTY3RsMDAkY3BoX3JpZ2h0JGd2Mg9nZNbw5Kd1aaPszl9JvpcLTMCGoffravuxI4RyS8Wa3ftI";
    private static final String EVENTVALIDATION_BLANK = "__EVENTVALIDATION";
    private static final String EVENTVALIDATION = "/wEdAB2mkjxD9AP9uH6kEsIDknCiCH7C2ooKcj+hye21epcYt57zU+tJOrbkpfeI+4y+0QA5Z9oerNMkjXhVZ0NKo6l0BNuOnAvslhD1zvPfE6p8njwsBakjhQfAu8ecC7+5T+n6I++UYgK22OHU7xYrZo+AKAo7EMQ2twi8SmqnRRLHpCzoZTaRpUmlAHXi1v9rUnrcoWB+ZSaSwSyZ6Qd02q/fS475+yi9pu/K8AEne1pUQSldxvkLsgSZYXV0l/+g5CnUkowtqINm2hseYINhdouXDfiWxlld6EK/kFEymJeFqzUdaox1MfhJKAaU+2/+Xx3jFdvX4jziLBNDAqEehYqvzueLZ3ZddW59ehyg7Yp6RuigWX5Lrhqr4QVjc5zljd9VSUw93iIR+p/Vq1zpHwQug9kiUCHfLWu17Iub8ibnPiuWw5NvonImWnE6wdiOm1AlP3ZSjBpKnYeeXjunNbU4NifJrV4+PZgfSYi8dEJ8WWpnzHL1mRqbMyXodtkOCP/yWwmWBKqAn17OeRrf7PRHqmRamdqGw8vMM5Su3ukGxJDoS3W6wQtiXPuK5s6fg7f2gdyENm5/S/WZYdNiK9fGV2qApdhdm4kj1DrmuhwbL3OeSxaUXyU7SLuZupdDWTJnXP61YARXYrPh1Jz7Kqjz";
    private static final String at_school_blank = "ctl00$cph_right$e_atschool";
    private static final String location_blank = "ctl00$cph_right$e_location";
    private static final String observation_blank = "ctl00$cph_right$e_observation";
    private static final String temp_blank = "ctl00$cph_right$e_temp";
    private static final String describe_blank = "ctl00$cph_right$e_describe";
    private static final String submit_blank = "ctl00$cph_right$e_submit";
    private static final String submit_value = "提交保存";


    public static Map<String, String> BlockInParamsGenerator(DailyHealthDetails details) {
        Map<String, String> map = new HashMap<>();
        map.put(VIEWSTATE_BLANK, VIEWSTATE);
        map.put(EVENTVALIDATION_BLANK, EVENTVALIDATION);
        map.put(at_school_blank, details.getIsAtSchool());
        map.put(location_blank, details.getLocation());
        map.put(observation_blank, details.getObservation());
        // TODO: 2020/5/17
        map.put(getHealthBlank(details.getHealth()), "on");
        map.put(temp_blank, String.valueOf(details.getTemperature()));
        map.put(describe_blank, details.getDescribe());
        map.put(submit_blank, submit_value);
        return map;
    }

    private static String getHealthBlank(String key) {
        return HealthBlankMap.get(key);
    }
}
