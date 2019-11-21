package com.example.idtk.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 数字类型数据均为10进制
 */
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** idtk计数器设备号 */
    private String sn;
    /** 0x00  不包含校验时间与营业时间;0x01  包含校验系统时间;0x02  包含校验营业时间;0x03  包含校验系统时间与营业时间 */
    private Integer commandType;
    /** 设备探测速度 0x00 低速，0x01 是高速 */
    private Integer speed;
    /** 记录周期的分钟数 0-255 */
    private Integer recordingCycle;
    /** 上传周期 0-255 0表示实时上传 */
    private Integer uploadCycle;
    /** 指定上传时间  0不指定 1-4依次指定*/
    private Integer fixedUploadTime;
    private Time uploadTime1;
    private Time uploadTime2;
    private Time uploadTime3;
    private Time uploadTime4;
    /** 运行模式 00联网模式01单机模式 */
    private Integer mode;
    /** 显示类型 00计数不在幕上显示;01显示总数;02显示双向 */
    private Integer displayType;
    private String mac1;
    private String mac2;
    private String mac3;
    /** 开店时间 时分 */
    private Time openTime;
    /** 关店时间 时分 */
    private Time closeTime;
    /** 最新的接受数据时间 */
    private Date latestReceiveTime;
    /** 最新的修改信息时间 */
    private Date latestUpdateTime;
    /** 对焦状态 0正常 1失焦 */
    private boolean focus;
    /** 发射器剩余电量 */
    private Integer irVoltage;
    /** 计数器剩余电量 */
    private Integer counterVoltage;
    /** 删除状态 0未删除 1删除 */
    private boolean deleted = false;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getRecordingCycle() {
        return recordingCycle;
    }

    public void setRecordingCycle(Integer recordingCycle) {
        this.recordingCycle = recordingCycle;
    }

    public Integer getUploadCycle() {
        return uploadCycle;
    }

    public void setUploadCycle(Integer uploadCycle) {
        this.uploadCycle = uploadCycle;
    }

    public Integer getFixedUploadTime() {
        return fixedUploadTime;
    }

    public void setFixedUploadTime(Integer fixedUploadTime) {
        this.fixedUploadTime = fixedUploadTime;
    }

    public Time getUploadTime1() {
        return uploadTime1;
    }

    public void setUploadTime1(Time uploadTime1) {
        this.uploadTime1 = uploadTime1;
    }

    public Time getUploadTime2() {
        return uploadTime2;
    }

    public void setUploadTime2(Time uploadTime2) {
        this.uploadTime2 = uploadTime2;
    }

    public Time getUploadTime3() {
        return uploadTime3;
    }

    public void setUploadTime3(Time uploadTime3) {
        this.uploadTime3 = uploadTime3;
    }

    public Time getUploadTime4() {
        return uploadTime4;
    }

    public void setUploadTime4(Time uploadTime4) {
        this.uploadTime4 = uploadTime4;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getMac2() {
        return mac2;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getMac3() {
        return mac3;
    }

    public void setMac3(String mac3) {
        this.mac3 = mac3;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public Date getLatestReceiveTime() {
        return latestReceiveTime;
    }

    public void setLatestReceiveTime(Date latestReceiveTime) {
        this.latestReceiveTime = latestReceiveTime;
    }

    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public Integer getIrVoltage() {
        return irVoltage;
    }

    public void setIrVoltage(Integer irVoltage) {
        this.irVoltage = irVoltage;
    }

    public Integer getCounterVoltage() {
        return counterVoltage;
    }

    public void setCounterVoltage(Integer counterVoltage) {
        this.counterVoltage = counterVoltage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
