package com.java.version2;

import java.util.ArrayList;
import java.util.Iterator;

import com.java.version2.DocInfoKeyWord.OneDocInfoKeyWord;
import com.local.bean.KeyLocInfo;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.*;

/**
 * 
 * @author x
 * @date 2016年11月14日
 * @description 插入数据，用表
 */

public class yongbiao {
	//private ArrayList<String> keywords = new ArrayList<String>();
	@SuppressWarnings("finally")
	String mark;
	String filename;
	String URL;
	String location;
	String str;
	String doctext;
	String text;

	public DocInfoKeyWord getResults(ArrayList<String> keywords) {
		DocInfoKeyWord dw = new DocInfoKeyWord();
		// ArrayList<String> marks = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}

		Connection connect = null;
		Statement st = null;
		/*try {
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true&serverTimezone=UTC", "root",
					"123456");
			st = connect.createStatement();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		try {
	        connect = new dbconnect().getConnection();
		
			st = connect.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < keywords.size(); i++) {
			String keyword = keywords.get(i);
			try {
				
				// st = connect.createStatement();
				ResultSet rs = st.executeQuery("select mark from wordInfo where word='" + keyword + "';");
				 
				while (rs.next()) {
					str = rs.getString("mark");
				}
				if(str==null)
					break;
				
				String tablename = str;
				
				rs = st.executeQuery("select location from " + tablename + " where word='" + keyword + "';");
				while (rs.next()) {
					String str = rs.getString("location");
					//System.out.println("str= "+str);
					// 字符串转换成JSONObject
					JSONObject ee = JSONObject.fromObject(str);
					// 获得关键词
					// String keyword1 = ee.getString("keyword");
					// 获取json里记录的数量
					int recordnum = ee.getInt("num");
					// 循环取出每条数据
					for (int j = 1; j <= recordnum; j++) {
						JSONObject jstemp = ee.getJSONObject(Integer.toString(j));
						int docid = jstemp.getInt("docid");
						String keytext = jstemp.getString("keytext");
						long parid = jstemp.getLong("parid");
						long sentid = jstemp.getLong("sentid");
						long startpos = jstemp.getLong("startpos");
						long endpos = jstemp.getLong("endpos");
						long length = jstemp.getLong("length");
						if (dw.insertData(docid, keytext, parid, sentid, startpos, endpos, length) == 0) {
							ResultSet rs1 = connect.createStatement()
									.executeQuery("select  *  from docinfo where docid='" + docid + "';");
							// ResultSet rs2 = st.executeQuery("select URL from
							// docinfo where docId=docid");
							
							while (rs1.next()) {
								filename = rs1.getString("filename");
								URL = rs1.getString("URL");
								doctext = rs1.getString("text").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
								//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rs1.getBinaryStream("text"),"UTF-8")); 
								//doctext=new String(bufferedReader.readLine().getBytes(), "UTF-8").replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
								//doctext= new String(rs1.getBytes("text"),"utf-8").replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
								
							}
							
							dw.createNewDocRecord(docid, filename, URL, doctext);
							dw.insertData(docid, keytext, parid, sentid, startpos, endpos, length);

						}
					}
				}
			} catch (Exception e) {
				System.out.print("get data error!");
				e.printStackTrace();
			}
		}

		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dw;

	}
}
