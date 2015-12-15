package org.demo.spider.multithread.db.pool;

import java.sql.SQLException;

import org.demo.spider.multithread.db.DBConfig;
import org.demo.spider.multithread.db.pool.ConnectionPool.PooledConnection;

public class DBManager {

    private static PooledConnection mConn;
    private static ConnectionPool mConnPool;
    private static DBManager mDBManager;
    
    private String mUrl = DBConfig.getMysqlUrl();
    private String mUser = DBConfig.getMysqlUesr();
    private String mPassword = DBConfig.getMysqlPassword();
    private String mDriver = DBConfig.getMysqlDerver();

    public void close() {
        try {
            mConnPool.closeConnectionPool();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DBManager() {
        if (mDBManager != null) {
            return;
        }

        mConnPool = new ConnectionPool(mDriver, mUrl, mUser, mPassword);
        
        try {
            mConnPool.createPool();
            mDBManager = this;
        } catch (Exception e) {
            System.err.println("DBManager: " + e);
        }
    }

    public static PooledConnection getConnection() {
        if (mDBManager == null) {
            new DBManager();
        }
        
        try {
            mConn = mConnPool.getConnection();
        } catch (SQLException e) {
            System.err.println("DBManager: " + e);
        }

        return mConn;
    }
}