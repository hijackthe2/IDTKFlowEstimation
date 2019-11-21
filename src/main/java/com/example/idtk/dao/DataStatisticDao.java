package com.example.idtk.dao;

import com.example.idtk.domain.DataStatistic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataStatisticDao {

    void addBatch(List<DataStatistic> dataStatistics);

}
