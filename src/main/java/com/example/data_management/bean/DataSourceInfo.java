package com.example.data_management.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="datasourceinfo")
public class DataSourceInfo {
    @Id
    @Column(name="dataSourceId")
    private int dataSourceId;
    @Column(name = "dataSourceType")
    private String dataSourceType;
    @Column(name="dataSourceVersion")
    private String dataSourceVersion;
    @Column(name = "dataSourceName")
    private String dataSourceName;
    @Column(name="dataSourceAlias")
    private String dataSourceAlias;
    @Column(name="dataSourceIP")
    private String dataSourceIP;
    @Column(name="dataSourcePort")
    private String dataSourcePort;
    @Column(name="dataSourceLoginName")
    private String dataSourceLoginName;
    @Column(name = "dataSourceLoginPwd")
    private String dataSourceLoginPwd;
    @Column(name = "dataSourceExtraInfo")
    private String dataSourceExtraInfo;

    public DataSourceInfo(){}

    public DataSourceInfo(int dataSourceId, String dataSourceType, String dataSourceVersion, String dataSourceName,
                          String dataSourceAlias, String dataSourceIP, String dataSourcePort, String dataSourceLoginName,
                          String dataSourceLoginPwd, String dataSourceExtraInfo) {
        this.dataSourceId = dataSourceId;
        this.dataSourceType = dataSourceType;
        this.dataSourceVersion = dataSourceVersion;
        this.dataSourceName = dataSourceName;
        this.dataSourceAlias = dataSourceAlias;
        this.dataSourceIP = dataSourceIP;
        this.dataSourcePort = dataSourcePort;
        this.dataSourceLoginName = dataSourceLoginName;
        this.dataSourceLoginPwd = dataSourceLoginPwd;
        this.dataSourceExtraInfo = dataSourceExtraInfo;
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDataSourceVersion() {
        return dataSourceVersion;
    }

    public void setDataSourceVersion(String dataSourceVersion) {
        this.dataSourceVersion = dataSourceVersion;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceAlias() {
        return dataSourceAlias;
    }

    public void setDataSourceAlias(String dataSourceAlias) {
        this.dataSourceAlias = dataSourceAlias;
    }

    public String getDataSourceIP() {
        return dataSourceIP;
    }

    public void setDataSourceIP(String dataSourceIP) {
        this.dataSourceIP = dataSourceIP;
    }

    public String getDataSourcePort() {
        return dataSourcePort;
    }

    public void setDataSourcePort(String dataSourcePort) {
        this.dataSourcePort = dataSourcePort;
    }

    public String getDataSourceLoginName() {
        return dataSourceLoginName;
    }

    public void setDataSourceLoginName(String dataSourceLoginName) {
        this.dataSourceLoginName = dataSourceLoginName;
    }

    public String getDataSourceLoginPwd() {
        return dataSourceLoginPwd;
    }

    public void setDataSourceLoginPwd(String dataSourceLoginPwd) {
        this.dataSourceLoginPwd = dataSourceLoginPwd;
    }

    public String getDataSourceExtraInfo() {
        return dataSourceExtraInfo;
    }

    public void setDataSourceExtraInfo(String dataSourceExtraInfo) {
        this.dataSourceExtraInfo = dataSourceExtraInfo;
    }
}
