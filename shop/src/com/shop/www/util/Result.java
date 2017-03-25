package com.shop.www.util;

public class Result implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public static String SUCCESS="SUCCESS";
	public static String ERROR="ERROR";
	public static String INPUT="INPUT";
	public static String LOGIN="LOGIN";
	public static String NONE="NONE";
	private String status;
	private String msg;
	
	
	public void s(String msg){
		this.status=SUCCESS;
		this.msg=msg;
	}
	
	public void e(String msg){
		this.status=ERROR;
		this.msg=msg;
	}
	
	public void i(String msg){
		this.status=INPUT;
		this.msg=msg;
	}
	
	public void l(String msg){
		this.status=LOGIN;
		this.msg=msg;
	}
	
	public void n(String msg){
		this.status=NONE;
		this.msg=msg;
	}
	
	public void x(String sta,String msg){
		this.status=sta;
		this.msg=msg;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
