package com.java.version2;

import java.io.IOException;
import java.util.ArrayList;

public class InsertDataControler {
	//String filepath="C:\\Users\\ruin\\Documents\\codes\\YIDO1.0\\WebContent\\案件文档\\test\\法律资料";
	//String filepath="C:\\Users\\shrimp\\Desktop\\Test\\审查逮捕意见书.docx";
	//2016年12月8日12:02:45
	//String filepath="D:\\jeeworkspace\\YIDO2.0test\\WebContent\\案件文档\\test";
	String filepath="D:\\jeeworkspace\\YIDO2.1\\WebContent\\案件文档\\Spider";
	DocDataSet dds = new DocDataSet();
	WordDataSet wds = new WordDataSet();
	LocDataSet lds = new LocDataSet();
	
	DataPrepare dp=new DataPrepare();
	SqlManager sm=new SqlManager();
	
	public void doInsert(){
		try {
			dp.prepareData(filepath, dds, wds, lds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		ArrayList<String> error1=new ArrayList<String>();
		ArrayList<String> error2=new ArrayList<String>();
		ArrayList<String> error3=new ArrayList<String>();
		error1=sm.insertDocInfo(dds);
		error2=sm.insertWord(wds);
		error3=sm.insertWordInfo(lds);
		
		if(error1!=null){
			for(String str:error1){
				System.out.println(str);
			}
		}
		else{
			System.out.println("第一张表完成。无报错");
		}
		
		if(error2!=null){
			for(String str:error2){
				System.out.println(str);
			}
		}
		else{
			System.out.println("第二张表完成。无报错");
		}
		
		if(error3!=null){
			for(String str:error3){
				System.out.println(str);
			}
		}
		else{
			System.out.println("第三张表完成。无报错");
		}
		
	}
}
