package com.example.data_management.controller;

import com.example.data_management.bean.DataSourceInfo;
import com.example.data_management.bean.TableData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataBaseController{
    @RequestMapping(value = "/getTableByDataSource",method = RequestMethod.POST)
    public TableData getTable(@RequestBody DataSourceInfo dataSourceInfo) throws Exception {
        String url="jdbc:mysql://"+dataSourceInfo.getDataSourceIP()+":"+dataSourceInfo.getDataSourcePort()+"/"+dataSourceInfo.getDataSourceName()+"?serverTimezone=GMT%2B8";
        String username=dataSourceInfo.getDataSourceLoginName();
        String password=dataSourceInfo.getDataSourceLoginPwd();
        String tableName=dataSourceInfo.getDataSourceName();

        //存储列名和数据
        List<String> colNames=new ArrayList<>();
        List<List<String>> dataList=new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //注册类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+tableName+"?characterEncoding=utf8&serverTimezone=GMT%2B8",username,password);
            //查询语句
            String sql = "select * from "+tableName;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //获取列数
            ResultSetMetaData md= rs.getMetaData();
            int columnSize = md.getColumnCount();

            System.out.println("查询结果如下:");
            //打印字段名
            for(int i = 1; i <= columnSize; i++){
                colNames.add(md.getColumnName(i));
                System.out.printf("%-12s",md.getColumnName(i));
            }
            System.out.println();

            //打印所有记录
            while(rs.next()) {
                List<String> rowList=new ArrayList<>();
                for(int i = 1; i <= columnSize ; i++){
                    rowList.add(rs.getObject(i).toString());
                    System.out.printf("%-12s",rs.getObject(i));
                }
                dataList.add(rowList);
                System.out.println();
            }
            System.out.println(dataList);
            System.out.println("\n结束查询");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭顺序: ResultSet-->Statement-->Connection
            try {
                if(rs!=null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(ps!=null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TableData(colNames,dataList);
    }


    @RequestMapping(value = "/mergeTableByDataSource",method = RequestMethod.POST)
    public TableData mergeTable(@RequestBody List<DataSourceInfo> dataSourceInfoList)
    {
        DataSourceInfo dataSourceInfoTemp1=dataSourceInfoList.get(0);
        DataSourceInfo dataSourceInfoTemp2=dataSourceInfoList.get(1);
        //获取两个数据源连接
        String tableName1=dataSourceInfoTemp1.getDataSourceName();
        String tableName2=dataSourceInfoTemp2.getDataSourceName();

        String username=dataSourceInfoTemp1.getDataSourceLoginName();
        String password=dataSourceInfoTemp1.getDataSourceLoginPwd();

        List<String> colNames1=new ArrayList<>();
        List<String> colNames2=new ArrayList<>();
        List<String> colUnionNames=new ArrayList<>();
        List<List<String>>  dataList=new ArrayList<>();

        Connection conn1= null;
        Connection conn2=null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        try {
            //注册类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接
            conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+tableName1+"?characterEncoding=utf8&serverTimezone=GMT%2B8",username,password);
            //查询语句
            String sql1 = "select * from "+tableName1;
            ps1 = conn1.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            //获取列数
            ResultSetMetaData md1= rs1.getMetaData();
            int columnSize1 = md1.getColumnCount();

            System.out.println("查询结果如下:");
            //获取表一的字段名
            for(int i = 1; i <= columnSize1; i++){
                colNames1.add(md1.getColumnName(i));
                System.out.printf("%-12s",md1.getColumnName(i));
            }
            System.out.println();

            conn2 =  DriverManager.getConnection("jdbc:mysql://localhost:3306/"+tableName2+"?characterEncoding=utf8&serverTimezone=GMT%2B8",username,password);
            //查询语句
            String sql2 = "select * from "+tableName2;
            ps2 = conn2.prepareStatement(sql2);
            rs2 = ps2.executeQuery();
            //获取列数
            ResultSetMetaData md2= rs2.getMetaData();
            int columnSize2 = md2.getColumnCount();

            System.out.println("查询结果如下:");
            //获取表二的字段名
            for(int i = 1; i <= columnSize2; i++){
                colNames2.add(md2.getColumnName(i));
                System.out.printf("%-12s",md2.getColumnName(i));
            }
            System.out.println();

            //获取主键
            String primaryKey="";
            DatabaseMetaData metaData=conn1.getMetaData();
            ResultSet pkInfo=metaData.getPrimaryKeys(null,tableName1,tableName1);
            while(pkInfo.next()){
                primaryKey=pkInfo.getString("COLUMN_NAME");
                System.out.println(primaryKey);
            }
            //生成SQL语句
            String sql3 = createSql(tableName1,tableName2,primaryKey,colNames1,colNames2);
            System.out.println("生成的SQL语句： "+sql3);
            ps3 = conn1.prepareStatement(sql3);
            rs3 = ps3.executeQuery();
            //获取查询列数
            ResultSetMetaData md3= rs3.getMetaData();
            int columnSize3 = md3.getColumnCount();

            System.out.println("查询结果如下:");
            //获取合并表的字段名
            for(int i = 1; i <= columnSize3; i++){
                colUnionNames.add(md3.getColumnName(i));
                System.out.printf("%-12s",md3.getColumnName(i));
            }
            System.out.println();

            //打印所有记录
            while(rs3.next()) {
                //存放每一行数据
                List<String> rowList=new ArrayList<>();
                for(int i = 1; i <= columnSize3 ; i++){
                    if(rs3.getObject(i)==null)
                    {
                        rowList.add("NULL");
                        System.out.printf("%-12s","NULL");
                        continue;
                    }
                    rowList.add(rs3.getObject(i).toString());
                    System.out.printf("%-12s",rs3.getObject(i));
                }
                dataList.add(rowList);
                System.out.println();
            }
            System.out.println(dataList);
            System.out.println("\n结束查询");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭顺序: ResultSet-->Statement-->Connection
            try {
                if(rs1!=null&&rs2!=null&&rs3!=null) {
                    rs1.close();
                    rs2.close();
                    rs3.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(ps1!=null&&ps2!=null&&ps3!=null) {
                    ps1.close();
                    ps2.close();
                    ps3.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(conn1!=null&&conn2!=null) {
                    conn1.close();
                    conn2.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TableData(colUnionNames,dataList);

    }

    public static String createSql(String tableName1,String tableName2,String primaryKey,List<String> colName1,List<String> colName2){
        String res="select ";
        //取两个列名中的交集，各自的差集
        List<String> intersection=(List<String>)CollectionUtils.intersection(colName1,colName2);
        List<String> subtract1=(List<String>) CollectionUtils.subtract(colName1,colName2);
        List<String> subtract2=(List<String>) CollectionUtils.subtract(colName2,colName1);

        for(int i=0;i<intersection.size();i++)
        {
            res+="a."+ intersection.get(i)+",";
        }
        for(int i=0;i<subtract1.size();i++)
        {
            res+="a."+subtract1.get(i)+",";
        }
        for(int i=0; i<subtract2.size();i++){
            //最后一个不加，
            if(i==subtract2.size()-1) {
                res += "b." + subtract2.get(i)+" ";
                break;
            }
            res+="b."+ subtract2.get(i)+",";
        }
        //左连接语句
        String leftSql= "from "+tableName1+"."+tableName1+" as a left outer join "+tableName2+"."+tableName2+" as b on (a."+primaryKey+"=b."+primaryKey+")";
        res+=leftSql;
        res+=" union ";
        res+="select ";
        for(int i=0;i<intersection.size();i++)
        {
            res+="b."+ intersection.get(i)+",";
        }
        for(int i=0;i<subtract1.size();i++)
        {
            res+="a."+subtract1.get(i)+",";
        }
        for(int i=0; i<subtract2.size();i++){
            //最后一个不加，
            if(i==subtract2.size()-1) {
                res += "b." + subtract2.get(i)+" ";
                break;
            }
            res+="b."+ subtract2.get(i)+",";
        }
        //右连接语句
        String rightSql= "from "+tableName1+"."+tableName1+" as a right outer join "+tableName2+"."+tableName2+" as b on (a."+primaryKey+"=b."+primaryKey+")";
        res+=rightSql;

        return res;
    }

}
