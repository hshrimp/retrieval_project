package com.java.version2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

public class Test111 {

//	public static void tese() {
//		// json�ܰ��÷���ʵ��
//
//		JSONObject ss = new JSONObject();
//
//		ss.put("1ererer", "sdsdf111112f");
//		ss.put("2", "sdfsd222222f");
//		ss.put("3", "sdfsd333333f");
//		ss.put("4", "sdfsd444444f");
//		ss.put("5", "sdfsd555555f");
//
//		JSONObject oo = new JSONObject();
//		oo.element("kssdf", ss);
//		System.out.println(oo);
//
//		JSONObject ee = oo.getJSONObject("kssdf");
//		System.out.println(ee);
//		System.out.println(ee.get("2"));
//	}

	public static void testdb() {
		Connection conn = null;// conn用于连接数据库
		Statement stmt = null;// stmt用于发送sql语句到数据库并执行sql语句
		String strSQL;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}

		try {
			/*conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC", "root",
					"");*/
			conn=new dbconnect().getConnection();
			stmt = conn.createStatement();

			strSQL = "select text from docinfo where docid=46";
			ResultSet rs = stmt.executeQuery(strSQL);
			
			while (rs.next()) {
				System.out.println(rs.getString(1).replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

	}

	@SuppressWarnings("static-access")
	public static void createtable() {
		Connection conn = null;// conn用于连接数据库
		Statement stmt = null;// stmt用于发送sql语句到数据库并执行sql语句
		String strSQL;
		String s[] = { "b", "p", "m", "f", "d", "t", "n", "l", "g", "k", "h", "j", "q", "x", "zh", "ch", "sh", "r", "z",
				"c", "s", "y", "w", "a", "o", "e", "ai", "ao", "an", "en", "zhi", "chi", "shi", "ri", "zi", "ci", "si",
				"yi", "wu", "yu", "ye", "yue", "yuan", "yin", "yun", "ying","er","ou","none","ang" };

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		for (int i = 0; i < s.length; i++) {
			try {
				/*conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
						"root", "123456");*/
				 conn=new dbconnect().getConnection();
				stmt = conn.createStatement();

				strSQL = "create table " + s[i]
						+ " (word varchar(20),mark char(5),docID int,location longtext)ENGINE=InnoDB DEFAULT CHARSET=utf8;";
				stmt.executeUpdate(strSQL);
				
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			System.out.println("第"+(i+1)+"张表已建.");
		}
		try {
			/*conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
					"root", "123456");*/
			conn=new dbconnect().getConnection();
			stmt = conn.createStatement();

			strSQL = "CREATE TABLE docinfo ("+
					"docid  int NOT NULL ,"+
					"filename  varchar(100) NOT NULL ,"+
					"url  varchar(300) NOT NULL ,"+
					"text  longtext NOT NULL ,"+
					"PRIMARY KEY (docid))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.executeUpdate(strSQL);
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		System.out.println("docinfo表已建.");
		
		try {
			/*conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
					"root", "123456");*/
			conn=new dbconnect().getConnection();
			stmt = conn.createStatement();

			strSQL ="CREATE TABLE `wordinfo` ("+
					"`word`  varchar(20) NOT NULL ,"+
					"`mark`  char(5) NOT NULL "+
					")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.executeUpdate(strSQL);
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		System.out.println("wordinfo表已建.");
	   // System.out.println();
		
	}
	
	public static void testInsertDataControler(){
		new InsertDataControler().doInsert();
	}

	public static void main(String[] args) {
		// tese();
		
		//testdb();
		//建数据库表
		createtable();
		//插入数据
		testInsertDataControler();
	}

}
