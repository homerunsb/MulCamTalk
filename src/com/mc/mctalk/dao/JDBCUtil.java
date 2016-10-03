package com.mc.mctalk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {

	public static Connection getConnection() {
		try {
			/*Oracle
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        return DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/orcl", "scott", "tiger");
	        */
	        /*MySQL*/
	        Class.forName("com.mysql.jdbc.Driver");
//	        return DriverManager.getConnection("jdbc:mysql://70.12.109.103:3306/multicampus", "admin", "1234");
//	        return DriverManager.getConnection("jdbc:mysql://172.30.1.51:3306/multicampus", "admin", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(PreparedStatement stmt, Connection conn) {
		try {
			if(stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt = null;
		}
		try {
			if(conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	public static void close(ResultSet rst, PreparedStatement stmt, Connection conn) {
		try {
			if(rst != null)
				rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rst = null;
		}
		try {
			if(stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt = null;
		}
		try {
			if(conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

}
