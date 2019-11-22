package com.example.idtk.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 数字类型数据均为10进制
 */
@Entity
@Table(name = "data_statistic")
public class DataStatistic {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 软件版本号 */
    @Column(length = 16)
    private String version;

    /** 设备sn串号 */
    @Column(length = 16)
    private String deviceSn;

    /** 发射器剩余电量 */
    @Column(length = 7)
    private Integer irVoltage;

    /** 计数器剩余电量 */
    @Column(length = 7)
    private Integer counterVoltage;

    /** 接受数据时间 */
    private Date dataTime;

    /** 对焦状态 0正常 1失焦 */
    @Column(length = 1, columnDefinition = "tinyint default 0")
    private boolean focus;

    /** 进门数量 */
    @Column(length = 11, name = "entry_number")
    private Integer entry;

    /** 出门数量 */
    @Column(length = 11, name = "exit_number")
    private Integer exit;

    /** 删除状态 0未删除 1删除 */
    @Column(length = 1, columnDefinition = "tinyint default 0")
    private boolean deleted = false;

    public DataStatistic(){}

    public DataStatistic(String version, String deviceSn, Integer irVoltage, Integer counterVoltage,
                         Date dataTime, boolean focus, Integer entry, Integer exit, boolean deleted) {
        this.version = version;
        this.deviceSn = deviceSn;
        this.irVoltage = irVoltage;
        this.counterVoltage = counterVoltage;
        this.dataTime = dataTime;
        this.focus = focus;
        this.entry = entry;
        this.exit = exit;
        this.deleted = deleted;
    }

    public DataStatistic copy(){
        return new DataStatistic(version, deviceSn, irVoltage, counterVoltage, dataTime, focus, entry, exit, deleted);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
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

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    public Integer getExit() {
        return exit;
    }

    public void setExit(Integer exit) {
        this.exit = exit;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
