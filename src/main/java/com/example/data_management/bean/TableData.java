package com.example.data_management.bean;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    private List<String> colNames=new ArrayList<>();
    private List<List<String>> dataList=new ArrayList<>();

    public TableData(){}
    public TableData(List<String> colNames, List<List<String>> dataList) {
        this.colNames = colNames;
        this.dataList = dataList;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public List<List<String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
    }
}
