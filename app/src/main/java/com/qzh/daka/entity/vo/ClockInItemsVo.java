package com.qzh.daka.entity.vo;

import com.qzh.daka.entity.ClockInItem;
import com.qzh.daka.entity.LogUserOperatedStatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClockInItemsVo {
    private List<ClockInItem> clockInItems;
    private LogUserOperatedStatus status;
}
