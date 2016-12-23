package com.java.version2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.java.version2.DocDataSet.DocData;
import com.java.version2.LocDataSet.LocData;
import com.java.version2.WordDataSet.WordData;

public class SqlManager {

	// 创建成员变量

	Connection conn = null;// conn用于连接数据库
	Statement stmt = null;// stmt用于发送sql语句到数据库并执行sql语句
	private String strSQL;

	public ArrayList<String> insertDocInfo(DocDataSet dds) {
		System.out.println("正在插入第一张表。。。");
		ArrayList<String> ExceptionInfo = new ArrayList<String>();// 存储异常信息
		// localhost:表示数据库服务器地址,如192.168.0.1
		// 3306表示端口号
		// test是数据库名称
		// user是数据库用户名
		// password是数据库的密码
		try {
			/*
		}
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
			try{		"root", "123456");*/
		
		conn=new dbconnect().getConnection();
		
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println(e.toString());
			ExceptionInfo.add(e.getMessage());
		}
		Iterator<DocData> itor = dds.getIterator();
		DocData ddtemp = null;
		// 对迭代器进行遍历，获取数据
		for (; itor.hasNext();) {
			ddtemp = itor.next();
			// 下面就是一条记录的信息，和第一张表是对应起来的
			int docid = ddtemp.getDocid();
			String filename = ddtemp.getFilename();
			String url = ddtemp.getUrl();
			String text = ddtemp.getText();
			String strSQL = "insert into docInfo(docid,filename,url,text) values('" + docid + "','" + filename + "','"
					+ url + "','" + text + "');";

			try {
				stmt.executeUpdate(strSQL);

			} catch (SQLException sqle) {
				System.out.println(sqle.toString());
				ExceptionInfo.add(sqle.getMessage());
			}
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ExceptionInfo.size() == 0) {
			return null;
		} else
			return ExceptionInfo;

	}

	public ArrayList<String> insertWord(WordDataSet wds) {
		System.out.println("正在插入第二张表。。。");
		ArrayList<String> ExceptionInfo = new ArrayList<String>();// 存储异常信息
		Iterator<WordData> itor = wds.getIterator();
		WordData wdtemp = null;
		HashSet<String> Record=new HashSet<String>();
		// 对迭代器进行遍历，获取数据
		
		/*try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
			try{			"root", "123456");*/
		try{
		conn=new dbconnect().getConnection();
	
			stmt = conn.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (; itor.hasNext();) {
			wdtemp = itor.next();

			// 下面就是一条记录的信息，和第2张表是对应起来的
			String word = wdtemp.getWord();
			String mark = wdtemp.getMark();
			if(Record.contains(word)){
				continue;
			}else{
				Record.add(word);			}
			try {
			
				String strSQL = "insert into wordInfo(word,mark) values('" + word + "','" + mark + "');";
				stmt.executeUpdate(strSQL);
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ExceptionInfo.size() == 0) {
			return null;
		} else
			return ExceptionInfo;

	}


	public ArrayList<String> insertWordInfo(LocDataSet lds) {
		System.out.println("正在插入第三张表。。。");
		ArrayList<String> ExceptionInfo = new ArrayList<String>();// 存储异常信息
		Iterator<LocData> itor = lds.getIterator();
		LocData ldtemp = null;
		try {
			/*conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC",
					"root", "123456");*/
			conn=new dbconnect().getConnection();
			
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println(e.toString());
			ExceptionInfo.add(e.getMessage());
		}
		// 对迭代器进行遍历，获取数据
		while (itor.hasNext()) {
			ldtemp = itor.next();
			String word = ldtemp.getWord();
			String mark = ldtemp.getMark();
			int docid = ldtemp.getDocid();
			String locinfo = ldtemp.getLocinfo();
			String tablename = mark;
			String strSQL = "insert into " + tablename + "(word,mark,docid,location) values('" + word + "','" + mark
					+ "','" + docid + "','" + locinfo + "');";
			try {
				stmt.executeUpdate(strSQL);
			} catch (SQLException sqle) {
				System.out.println(sqle.toString());
				ExceptionInfo.add(sqle.getMessage());
			}
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ExceptionInfo.size() == 0) {
			return null;
		} else
			return ExceptionInfo;

	}
}
