package com.example.idtk.repository;

import com.example.idtk.domain.DataStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataStatisticRepository extends JpaRepository<DataStatistic, Integer> {
}
