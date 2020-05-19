package com.qzh.daka.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Builder
@Entity
public class LogUser {
    @Id
    private Long id;
    @NonNull
    @Property(nameInDb = "stuNum")
    private String studentNum;
    @NonNull
    @Property(nameInDb = "password")
    private String password;

    @Generated(hash = 970011619)
    public LogUser(Long id, @NonNull String studentNum, @NonNull String password) {
        this.id = id;
        this.studentNum = studentNum;
        this.password = password;
    }

    @Generated(hash = 2145889911)
    public LogUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentNum() {
        return this.studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
