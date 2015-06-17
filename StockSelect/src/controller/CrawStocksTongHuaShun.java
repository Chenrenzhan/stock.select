package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.IntRange;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.CrawStockXueQiu.GetThread;

public class CrawStocksTongHuaShun extends CrawStocks{
	public static final String CHARSET = "utf-8";
	public static final String GET_PARAM_URL = 
			"http://www.iwencai.com/stockpick/search"
			+ "?typed=0&preParams=&ts=1&f=1&qs=1&selfsectsn="
			+ "&querytype=&searchfilter=&tid=stockpick&w=pe";
	public static final String URL_BASE_AJAX = 
			"http://www.iwencai.com/stockpick";
	
	private String url;
	private JSONArray dataArray;
	private int total;
	private String token;
	
	private final String tableName = "tonghuashun";
	private String sourceName = "同花顺";
	
	public CrawStocksTongHuaShun(){
		super();
		
//		execute();
	}

	@Override
	public void execute() {
		token = getToken();
		url = getUrl();
		dataArray = new JSONArray();
		multiThread();
	}
	
	@Override
	public void update(){
//		CrawStocksTongHuaShun ths = new CrawStocksTongHuaShun(name);
		Thread td = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				execute();
				System.out.println("[同花顺]数据更新完成！");
			}
		});
		td.start();
		try {
			td.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void multiThread() {
		// TODO Auto-generated method stub
		String str = getUrlString(url, CHARSET);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(str);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (jsonObj != null) {
			try {
				dataArray = jsonObj.getJSONArray("result");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getUrl() {
		String url = URL_BASE_AJAX + "/cache?token=" + token + "&p=" + 1
				+ "&perpage=" + total;

		return url;
	}

	public String getToken() {
		String token = null;
		String htmlStr = getUrlString(GET_PARAM_URL, CHARSET);
		//正则表达式处理HTML DOM，提取json对象的字符串
		Pattern pattern = Pattern.compile("var allResult = (.*)?;");
		Matcher matcher = pattern.matcher(htmlStr);
		if (matcher.find()) {
			String ret = matcher.group(1);
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(ret);//字符串转成json对象
				token = jsonObj.getString("token");
				total = jsonObj.getInt("total");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		this.token = token;
		return token;
	}
	
	private String getUrlString(String url, String charset){
		String htmlStr = null;
		HttpGet hg = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(hg);
			HttpEntity entity = response.getEntity();
			htmlStr = InputStream2String(entity, charset);
			
//			return htmlStr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("finally");
			hg.releaseConnection();
		}
		return htmlStr;
	}

	@Override
	public JSONArray getDataArray() {
		return dataArray;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public String getSourceName() {
		return sourceName;
	}

	@Override
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public static void main(String[] args) throws Exception {
		String str1;
		CrawStocksTongHuaShun ths = new CrawStocksTongHuaShun();
		ths.update();
//		ths.execute();
		String str = ths.getDataArray().toString();
		 try {
			IORW.write("data/tonghuashun.json", str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		System.out.println("finish");

	}
	
}