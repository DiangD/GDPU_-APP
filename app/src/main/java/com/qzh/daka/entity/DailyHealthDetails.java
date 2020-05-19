package com.qzh.daka.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import lombok.Builder;
import lombok.ToString;

@Entity
@ToString
@Builder
public class DailyHealthDetails {
    @Id
    private Long id;
    @Property(nameInDb = "isAtSchool")
    private String isAtSchool;
    @Property(nameInDb = "location")
    private String location;
    @Property(nameInDb = "observation")
    private String observation;
    @Property(nameInDb = "health")
    private String health;
    @Property(nameInDb = "temperature")
    private Double temperature;
    @Property(nameInDb = "describe")
    private String describe;
    @Generated(hash = 1849635211)
    public DailyHealthDetails(Long id, String isAtSchool, String location,
            String observation, String health, Double temperature,
            String describe) {
        this.id = id;
        this.isAtSchool = isAtSchool;
        this.location = location;
        this.observation = observation;
        this.health = health;
        this.temperature = temperature;
        this.describe = describe;
    }
    @Generated(hash = 8423293)
    public DailyHealthDetails() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIsAtSchool() {
        return this.isAtSchool;
    }
    public void setIsAtSchool(String isAtSchool) {
        this.isAtSchool = isAtSchool;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getObservation() {
        return this.observation;
    }
    public void setObservation(String observation) {
        this.observation = observation;
    }
    public String getHealth() {
        return this.health;
    }
    public void setHealth(String health) {
        this.health = health;
    }
    public Double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public String getDescribe() {
        return this.describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
