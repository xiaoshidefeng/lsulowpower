package com.example.demo.domain;

import javax.persistence.*;

/**
 * Created by cw on 2017/7/20.
 */
@Entity
@Table(name = "lowpower")
public class Power {

    @Id
    @GeneratedValue
    @Column(name = "POWER_ID")
    private Integer powerId;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @Column(name = "DORM_NUM")
    private String dormNum;

    @Column(name = "RESIDUAL_ELECTRICITY")
    private String powerNum;

    @Column(name = "DATE_NUM")
    private String dateNum;

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public String getPowerNum() {
        return powerNum;
    }

    public void setPowerNum(String powerNum) {
        this.powerNum = powerNum;
    }

    public String getDateNum() {
        return dateNum;
    }

    public void setDateNum(String dateNum) {
        this.dateNum = dateNum;
    }
}
