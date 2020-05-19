package com.qzh.daka.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import lombok.AllArgsConstructor;
import lombok.Data;

@SmartTable(name = "最近健康打卡记录")
@AllArgsConstructor
@Data
public class ClockInItem {
    @SmartColumn(id = 0, name = "日期", autoMerge = true)
    private String modifyDate;
    @SmartColumn(id = 1, name = "在校")
    private String atSchool;
    @SmartColumn(id = 2, name = "所在地")
    private String location;
    @SmartColumn(id = 3, name = "医学观察情况")
    private String observation;
    @SmartColumn(id = 4, name = "健康情况")
    private String health;
    @SmartColumn(id = 5, name = "额温")
    private String temp;
    @SmartColumn(id = 6, name = "情况描述")
    private String describe;

}
