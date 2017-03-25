package com.shop.www.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

public class CF {//CommonFunctionSet
	
	public static boolean isNull(Object obj){
		return obj==null||obj.toString().isEmpty()||"".equals(obj.toString());
	}
	
	public static boolean isChinese(String s){//匹配中文字符 [\u4E00-\u9FA5]汉字﹐[\uFE30-\uFFA0]全角字符  [\u2E80-\uFE4F]UTF-8
		return Pattern.matches("/^\\w*[\u2E80-\uFE4F]+\\w*$/", s);//暂不能匹配，不知道什么情况
	}
	
	public static boolean isEmail(String s){//匹配Email地址
		return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", s);
	}
	
	public static boolean isURL(String s){//匹配网址URL
		return Pattern.matches("^[a-zA-z]+://[^\\s]*$", s);
	}
	
	public static boolean isPhone(String s){//匹配国内电话号码  匹配形式如 0511-4405222 或 021-87888822
		return Pattern.matches("^\\d{3}-\\d{8}|\\d{4}-\\d{7}$", s);
	}
	
	public static boolean isQQ(String s){//匹配腾讯QQ号
		return Pattern.matches("^[1-9][0-9]{4,}$", s);
	}
	
	public static boolean isZIP(String s){//匹配中国邮政编码
		return Pattern.matches("^[1-9]\\d{5}(?!\\d)$", s);
	}
	
	public static boolean isIDCard(String s){//匹配身份证
		return Pattern.matches("^\\d{15}|\\d{18}$", s);
	}
	
	//要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，

	//故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：

	//移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188

	//联通：130、131、132、152、155、156、185、186

	//电信：133、153、180、189、（1349卫通）

	public static boolean isMobileNO(String s){//匹配手机号码
		return Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", s)
				||Pattern.matches("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$", s);
	}
	
	public static String getTimeOfFormat(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
	}
	
	public static String getCurrentTime(){
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString();
	}
	
	public static String getCurrentTimestamp(){
		//12-1月 -15 01.24.31.000000 下午
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()).toString();
	}
	
	public static int getDDFromDateString(Timestamp t){
		DateFormat df=new SimpleDateFormat("dd");
		return Integer.valueOf(df.format(t));
	}
	
	public static String getImgNewName(String imgOldName){
		String fileType=imgOldName.substring(imgOldName.indexOf("."),imgOldName.length());
		StringBuffer ImgNewName=new StringBuffer();
		ImgNewName.append("Img-");
		ImgNewName.append(new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date()).toString());
		ImgNewName.append("-");
		ImgNewName.append(System.nanoTime());
		ImgNewName.append(fileType);
		return ImgNewName.toString();
	}
	
	public static boolean toBool(String value){
		if("true".equalsIgnoreCase(value)) return true;
		else if("false".equalsIgnoreCase(value)) return false;
		else return true;
	}
	
	public static boolean isSystem(String s){
		return System.getProperty("os.name").toUpperCase().startsWith(s.toUpperCase());
	}
	
	public static boolean isConnection(String host,int port){
		boolean con=false;
		Socket s=null;
		try {
			s=new Socket();
			s.connect(new InetSocketAddress(host,port));
			con=true;
		} catch (Exception e) {
			con=false;
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static boolean isPing(String host){
		boolean con=true;
		try {
			con=InetAddress.getByName(host).isReachable(3000);
		} catch (IOException e) {
			con=false;
		}
		return con;
	}
	
	public static String ip(){
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	public static String ipTo2(){
		try {
			String ip=InetAddress.getLocalHost().getHostAddress().toString();
			String[] ips=ip.split("\\.");
			return ips[2]+"."+ips[3];
		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	public static boolean isTelnet(String ip,int port){
		Socket socket=null;
		try {
			socket=new Socket();
			InetSocketAddress isa=new InetSocketAddress(ip,port);
			socket.connect(isa, 3000);
			return true;
		} catch (UnknownHostException e){
			e.printStackTrace();
			return false;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String trimCon(String s,String con){
		if(CF.isNull(s)||CF.isNull(con)) return null;
		String temp=s;
		if(con.equals(s.subSequence(0, 1))){
			temp=s.substring(1, s.length());
		}
		if(con.equals(s.subSequence(s.length()-1, s.length()))){
			temp=temp.substring(0, temp.length()-1);
		}
		return temp;
	}
	
	public static String trimCons(String s,String con){
		if(CF.isNull(s)||CF.isNull(con)) return null;
		if(con.equals(s.substring(0, con.length()))) s=s.substring(con.length());
		if(con.equals(s.substring(s.length()-con.length(), s.length()))) s=s.substring(0,s.length()-con.length());
		return s;
	}
	
	public static boolean isIP(String ip){
		String IPRegex="\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
		Pattern pattern=Pattern.compile(IPRegex);
		Matcher matcher=pattern.matcher(ip);
		return matcher.matches();
	}
	
	public static boolean isIPv4(){
		try {
			InetAddress ia=InetAddress.getLocalHost();
			if(ia instanceof Inet4Address) return true;
			else if(ia instanceof Inet6Address) return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean isIPv4(String ip){
		if(ip.replaceAll("\\d", "").length()==3) return true;//This is IPv4.
		else return false;//This is IPv6.
	}
	
	public static boolean isIPv6(String ip){
		if(ip.replaceAll("\\d", "").length()==3) return false;//This is IPv4.
		else return true;//This is IPv6.
	}
	
	public static int DataPageCount(long count,int size){
		int page=Integer.valueOf(String.valueOf(count%size==0?count/size:(count/size)+1));
		return page;
	}

	public static String ReplaceFileSuffix(String path,String suffix){
		if(CF.isNull(path)) return "";
		return path.substring(0, path.lastIndexOf(".")+1)+suffix;
	}
	
	public static boolean toBools(String s){
		if(CF.isNull(s)) return true;
		else{
			try {
				return Boolean.valueOf(s);
			} catch (Exception e) {
				return true;
			}
		}
	}
	
	public static boolean toBools(List<Object> list){
		if(list==null||list.size()<=0) return false;
		return toBools(list.get(0).toString());
	}
	
	public static void ListClear(List<?> list){
		 if(list == null) return;
		 else if(list.isEmpty()) list=null;
		 else{
			list.clear();
			list=null;
		}
	}
	
	public static String StrInChar(String s,String c){
		char[] cs=s.toCharArray();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<cs.length;i++){
			if(i>=cs.length-1) sb.append(String.valueOf(cs[i])).append(c);
			else sb.append(String.valueOf(cs[i]));
		}
		return sb.toString();
	}
	
	//获取客户端的IP就算Ip多级代理也可以或得真实的IP
	public static String getIp2(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static int getNDay(String st,String et){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date sd=sdf.parse(st);
			Date ed=sdf.parse(et);
			long diff = ed.getTime() - sd.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			return Integer.valueOf(String.valueOf(days));
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public static String phoneNotSee(String s){
		String p="电话有误";
		try {
			if(CF.isNull(s)){
				p="未知";
			}else{
				if(CF.isMobileNO(s)){
					p=s.substring(0, 3)+"***"+s.substring(8, 11);
				}else if(CF.isPhone(s)){
					if(s.indexOf("-")!=-1){
						p=s.substring(0, s.indexOf("-")+1)+"***";
					}
				}
			}
		} catch (Exception e) {
			return p;
		}
		return p;
	}
	
	public static String[] IMG_FORMAT_NAME=new String[]{
		"jpg","jpeg","png","gif","bmp","dib","rle","tif","tiff","bw","cdr",
		"col","dwg","dxb","dxf","wmf","pcx","tga","exif","fpx","svg","psd",
		"cdr","pcd","ufo","eps","ai","hdri"
	};
	public static boolean isImgFile(String fileNameOrFileSuffix){
		return isFileFormatOf(fileNameOrFileSuffix,IMG_FORMAT_NAME);
	}
	
	public static String[] HTML_FORMAT_NAME=new String[]{
		"html","htm","xhtml","jsp","asp","aspx","php"
	};
	public static boolean isHtmlFile(String fileNameOrFileSuffix){
		return isFileFormatOf(fileNameOrFileSuffix,HTML_FORMAT_NAME);
	}
	
	public static boolean isFileFormatOf(String fileNameOrFileSuffix,String[] format){
		String fileSuffix="";
		if(fileNameOrFileSuffix.indexOf(".")!=-1) fileSuffix=fileNameOrFileSuffix.substring(fileNameOrFileSuffix.indexOf(".")+1, fileNameOrFileSuffix.length());
		else fileSuffix=fileNameOrFileSuffix;
		for(int i=0;i<format.length;i++){
			if(fileSuffix.equalsIgnoreCase(format[i])) return true;
		}
		return false;
	}

	public static String replaceChar(String text,String oldChar,String newChar){
		return text.replace(oldChar, newChar);
	}
	
	@SuppressWarnings("static-access")
	public static String jsonObject(Object obj){
		JSONObject j=new JSONObject();
		j.fromObject(obj);
		return j.toString();
	}
	
	public static String jsonArray(Object...objs){
		StringBuffer json=new StringBuffer();
		for(int i=0;i<objs.length;i++){
			JSONArray ja=JSONArray.fromObject(objs[i]);
			json.append(ja.toString()).append(i==objs.length-1?"":",");
		}
		return json.toString();
	}
	
	public static Double SSWR2Money(Double money){
		BigDecimal bd=new BigDecimal(money);
		return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static Object listOne(List<Object> list){
		if(list==null||list.size()<=0) return null;
		return list.get(0);
	}
	
	public static String toLike(String param){
		StringBuffer sb=new StringBuffer();
		sb.append("%").append(param).append("%");
		return sb.toString();
	}
}
