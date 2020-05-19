package com.qzh.daka.entity;

public enum LogUserOperatedStatus {


    VERIFY_SUCCESS(1,"验证成功！"),
    UNAUTHORIZED(2,"用户名或密码错误!"),
    NETWORK_BUSY(3,"网络繁忙，请稍后重试~"),
    FAIL(4, "操作失败！"),
    PARAMS_NOT_NULL(5, "参数不能为空，请补全！"),
    SUBMIT_SUCCESS(6, "保存成功！"),
    USER_EXPIRE(7, "登录权限已过期，请重试~"),
    CLOCK_IN_SUCCESS(8,"打卡成功！"),
    PULL_SUCCESS(9,"数据拉取成功！"),
    ;
    private Integer code;
    private String message;

    LogUserOperatedStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
