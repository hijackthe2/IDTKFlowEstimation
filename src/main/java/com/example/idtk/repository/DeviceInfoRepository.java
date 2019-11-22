package com.example.idtk.repository;

import com.example.idtk.domain.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, String> {

    DeviceInfo findFirstBySnAndDeleted(String sn, boolean deleted);
}
