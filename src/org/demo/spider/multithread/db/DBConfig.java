package org.demo.spider.multithread.db;

public class DBConfig {

	private static String MYSQL_URL = "jdbc:mysql://localhost/test_site";
	
	private static String MYSQL_USER = "root";
	
	private static String MYSQL_PASSWORD = "260606";
	
	private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	public static String getMysqlUrl() {
		return MYSQL_URL;
	}
	
	public static String getMysqlUesr() {
		return MYSQL_USER;
	}
	
	public static String getMysqlPassword() {
		return MYSQL_PASSWORD;
	}
	
	public static String getMysqlDerver() {
		return MYSQL_DRIVER;
	}
}
