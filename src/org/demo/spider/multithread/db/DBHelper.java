package org.demo.spider.multithread.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.demo.spider.multithread.model.WebInfoModel;

public class DBHelper {

    public void insertModelArray(WebInfoModel[] models) {
        
        if (models == null || models.length == 0) {
            return;
        }

        String sql = "INSERT INTO visited_site(name,address,hash_address,date,level) VALUES(?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DBConfig.getMysqlUrl(),
                    DBConfig.getMysqlUesr(), DBConfig.getMysqlPassword());
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (WebInfoModel model : models) {
                ps.setString(1, model.getName());
                ps.setString(2, model.getAddress());
                ps.setInt(3, model.getAddress().hashCode());
                ps.setLong(4, System.currentTimeMillis());
                ps.setInt(5, model.getLevel());
                ps.addBatch();
            }
            
            ps.executeBatch();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Batch insert error:" + e);
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("your sql is: " + sql + "\nError: " + e);
            }

            sql = null;
            ps = null;
            conn = null;
        }
    }
    
    public void insertModelList(List<WebInfoModel> models) {
        
        if (models == null || models.size() == 0) {
            return;
        }

        String sql = "INSERT INTO visited_site(name,address,hash_address,date,level) VALUES(?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DBConfig.getMysqlUrl(),
                    DBConfig.getMysqlUesr(), DBConfig.getMysqlPassword());
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (WebInfoModel model : models) {
                ps.setString(1, model.getName());
                ps.setString(2, model.getAddress());
                ps.setInt(3, model.getAddress().hashCode());
                ps.setLong(4, System.currentTimeMillis());
                ps.setInt(5, model.getLevel());
                ps.addBatch();
            }
            
            ps.executeBatch();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Batch insert error:" + e);
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("your sql is: " + sql + "\nError: " + e);
            }

            sql = null;
            ps = null;
            conn = null;
        }
    }
}
