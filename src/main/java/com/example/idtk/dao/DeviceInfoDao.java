package com.example.idtk.dao;

import com.example.idtk.domain.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceInfoDao {

    DeviceInfo findDeviceInfoBySn(String sn);

    void save(DeviceInfo deviceInfo);
}
