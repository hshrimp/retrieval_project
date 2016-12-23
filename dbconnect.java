package com.java.version2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {
	private String URL="jdbc:mysql://localhost:3306/yido?characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
	private String USER="root";
	private String PASSWORD="123456";
	private Connection conn=null;
	
	@SuppressWarnings("finally")
	public  Connection getConnection(){
		Connection conn=null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(URL, USER, PASSWORD);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return conn;
		}
	}
}
