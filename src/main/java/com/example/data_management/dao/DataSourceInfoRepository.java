package com.example.data_management.dao;

import com.example.data_management.bean.DataSourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSourceInfoRepository extends JpaRepository<DataSourceInfo,Long>{
    public List<DataSourceInfo> findAllBy();
    public void deleteByDataSourceId(int dataSourceId);
}
