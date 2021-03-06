package com.hxy.isw.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

	
	
	@SuppressWarnings("rawtypes")
	public static void listToJson(List lstMap,int count,HttpServletResponse response) throws IOException{
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.add("rows", arr);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		out.close();
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void listToJson(List lstMap,int records,int total,HttpServletResponse response) throws IOException{
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",total);
		obj.addProperty("records",records);
		obj.add("rows", arr);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		out.close();
		
	}
	
	public static void listToJson(JsonArray arr,int records,int total,HttpServletResponse response) throws IOException{
		JsonObject obj = new JsonObject();
		obj.addProperty("total",total);
		obj.addProperty("records",records);
		obj.add("rows", arr);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		out.close();
		
	}
	
	public static List<Map<String,Object>> list2ListMap(List<Object> lst){ 
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object object : lst) {
			lstMap.add(obj2map(object));
		}
	    return lstMap;  
	   }      
	
	public static Map<String,Object> obj2map(Object o){  
		Field[] fields=o.getClass().getDeclaredFields(); 
	    //Object[] value=new Object[fieldNames.length];  
	    Map<String,Object> map = new HashMap<String,Object>();
	    for(int i=0;i<fields.length;i++){  
	    	String filename = fields[i].getName();
	    	String type = fields[i].getType().toString();
	    	Object obj = getFieldValueByName(filename, o); 
	        map.put(filename, obj.toString());
	    }  
	    return map;  
	   }      

	
	public static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	           return null;    
	       }    
	   }   
	
	public static void objToJson(Object obj,HttpServletResponse response ) throws IOException{
		
		String json = getGson().toJson(obj);
		PrintWriter pw = response.getWriter();
		if(pw!=null){
			pw.print(json.toString());
			pw.flush();
			pw.close();
		}

	}
	
	public static void mapToJson(Map<String , Object> map,HttpServletResponse response ) throws IOException{
		
		objToJson(map,response);
	
	}
	
	

	public static void listToJson(JsonArray arr,int count,HttpServletResponse response) throws IOException{
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.add("rows", arr);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		out.close();
		
	}
	

	public static void success2page(HttpServletResponse response){
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("{\"op\":\"success\"}");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void noRight2page(HttpServletResponse response){
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("{\"op\":\"noright\"}");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void timeout2page(HttpServletResponse response){
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("{\"op\":\"timeout\"}");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void success2page(HttpServletResponse response,String json){
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void success2client(HttpServletResponse response,String json){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		Sys.out("===="+sdf.format(now)+"=======result===start=============");
		Sys.out(json);
		Sys.out("===========result====end============");
		PrintWriter out;
		try {
			response.setContentType("text/json; charset=UTF-8");
			out = response.getWriter();
			out.print(json);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void success2client(HttpServletResponse response){
		PrintWriter out;
		try {
			response.setContentType("text/json; charset=UTF-8");
			out = response.getWriter();
			out.println("{\"msg\":\"success\",\"info\":\"操作成功\"}");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void success2page(PrintWriter out){
		out.println("{\"op\":\"success\"}");
		out.close();
	}
	
	private static JsonParser jsonParser = null;
	private static Gson gson = null;
	public static JsonParser getJsonParser(){
		if(jsonParser==null)jsonParser = new JsonParser();
		return jsonParser;
	}
	public static Gson getGson(){
		if(gson==null){
			GsonBuilder gsonBuilder = new GsonBuilder(); 
			gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss  
			gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());  
			gson = gsonBuilder.create();
		}
		return gson;
	}
	
	public static void castGson2Time(){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");// HH:mm:ss  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapterTime());  
		gson = gsonBuilder.create();
	}
	
	public static void reGson(){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		gsonBuilder.setDateFormat("yyyy-MM-dd");// HH:mm:ss  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());  
		gson = gsonBuilder.create();
	}
	
	@SuppressWarnings("rawtypes")
	public static String castObject2Json(Object obj,Class xclass){
		return getGson().toJson(obj,xclass);
	}
	
	public static JsonObject castString2Object(String json){
		return (JsonObject) getJsonParser().parse(json);
	}
	
	public static JsonObject castObjs2JsonObject(Object...objs){
		JsonObject jsonObject = new JsonObject();
		for (Object obj : objs) {
			if(obj==null)continue;
			String className = obj.getClass().getSimpleName();
			String perprotyName = (className.charAt(0)+"").toLowerCase()+className.substring(1, className.length());
			JsonObject jsonObj = castString2Object(castObject2Json(obj, obj.getClass()));
			if(jsonObject.get(perprotyName)==null){
				jsonObject.add(perprotyName, jsonObj);
			}else if(jsonObject.get(perprotyName+1)==null){
				jsonObject.add(perprotyName+1, jsonObj);
			}else{
				jsonObject.add(perprotyName+2, jsonObj);
			}
		}
		return jsonObject;
	}
	
	public static JsonArray castLst2Arr(List<Object[]> lst){
		JsonArray arr = new JsonArray();
		if( lst != null && lst.size()>0 ){
			for (Object[] objs: lst) {
				JsonObject jsonObject = castObjs2JsonObject(objs);
				arr.add(jsonObject);
			}
		}
		return arr;
	}
	
	public static JsonArray castLst2ArrTime(List<Object[]> lst){
		castGson2Time();
		JsonArray arr = new JsonArray();
		if( lst != null && lst.size()>0 ){
			for (Object[] objs: lst) {
				JsonObject jsonObject = castObjs2JsonObject(objs);
				arr.add(jsonObject);
			}
		}
		reGson();
		return arr;
	}
	
	public static JsonArray castLst2Arr4Single(List<Object> lst){
		JsonArray arr = new JsonArray();
		if( lst != null && lst.size()>0 ){
			for (Object obj: lst) {
				JsonObject jsonObject = castObjs2JsonObject(obj);
				arr.add(jsonObject);
			}
		}
		return arr;
	}
	
	public static JsonArray castLst2Arr4SingleTime(List<Object> lst){
		castGson2Time();
		JsonArray arr = new JsonArray();
		if( lst != null && lst.size()>0 ){
			for (Object obj: lst) {
				JsonObject jsonObject = castObjs2JsonObject(obj);
				arr.add(jsonObject);
			}
		}
		reGson();
		return arr;
	}
	
	
	
}

