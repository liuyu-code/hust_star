package com.example.data_management.controller;

import com.example.data_management.bean.DataSourceInfo;
import com.example.data_management.dao.DataSourceInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class DataSourceInfoController {
    @Autowired
    private DataSourceInfoRepository dataSourceInfoRepository;
    @RequestMapping(value = "/getTableDataSourceInfo",method = RequestMethod.POST)
    public List<DataSourceInfo> findTableDataSource(){
        return dataSourceInfoRepository.findAllBy();
    }
    @Transactional
    @RequestMapping(value = "/delByDataSourceId",method = RequestMethod.POST)
    public void delDataSourceById(@RequestParam int dataSourceId){
        dataSourceInfoRepository.deleteByDataSourceId(dataSourceId);
    }
    @RequestMapping(value = "/addDataSourceInfo",method = RequestMethod.POST)
    public void addSourceInfo(@RequestBody DataSourceInfo dataSourceInfo){
        dataSourceInfoRepository.save(dataSourceInfo);
    }

}
