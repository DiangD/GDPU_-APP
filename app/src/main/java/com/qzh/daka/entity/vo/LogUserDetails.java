package com.qzh.daka.entity.vo;

import com.qzh.daka.entity.LogUser;
import com.qzh.daka.entity.LogUserOperatedStatus;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogUserDetails {
    private LogUser logUser;
    private String key;
    private Map<String,String> cookies;
    private LogUserOperatedStatus status;
}
