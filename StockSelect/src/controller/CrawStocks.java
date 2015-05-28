package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CrawStocks {
	public static final String CHARSET = "utf-8";

	/**
     * Method: crawFromUrl 
     * Description: 获取网页HTML DOM信息
     * @param url
     * 网页链接
     * @param charset
     * 字符编码
     * @return String
     * 返回网页DOM字符串
     */
	public static String crawFromUrl(String url, String charset) 
			throws ClientProtocolException, IOException {
		HttpClient hc = new DefaultHttpClient();//有错
		System.out.println("aaaaaaaaaaaaaaaaaa");
		HttpGet hg = new HttpGet(url);
		HttpResponse response = hc.execute(hg);
		HttpEntity entity = response.getEntity();

		InputStream htmlConten = null;

		if (entity != null) {
			htmlConten = entity.getContent();
			String htmlStr = InputStream2String(htmlConten, charset);
			return htmlStr;
		}
		return null;
	}

	/**
     * Method: getAjaxParam 
     * Description: 获取ajax请求数据的链接的参数
     * @param url
     * 网页链接
     * @param charset
     * 字符编码
     * @return ajaxMap
     * 返回ajax参数的字典
     */
	public static Map<String, String> getAjaxParam(String url, String charset) 
			throws ClientProtocolException, IOException{
		Map<String, String> ajaxMap = new HashMap();
		String htmlStr = crawFromUrl(url, charset);
		//正则表达式处理HTML DOM，提取json对象的字符串
		Pattern pattern = Pattern.compile("var allResult = (.*)?;");
		Matcher matcher = pattern.matcher(htmlStr);
		if (matcher.find()) {
			String ret = matcher.group(1);
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(ret);//字符串转成json对象
				String token = jsonObj.getString("token");
				int total = jsonObj.getInt("total");
				ajaxMap.put("token", token);//ajax请求标识符
				ajaxMap.put("total", String.valueOf(total));//股票总数
				ajaxMap.put("p", "1");//第一页
				return ajaxMap;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
     * Method: ajaxResquest 
     * Description: 模拟ajax向服务器请求数据
     * @param url_base
     * ajax请求基础链接
     * @param ajaxMap
     * ajax链接参数字典
     * @return ajaxMap
     * 返回ajax请求的数据
     */
	public static String ajaxResquest(String url_base, Map<String, String> ajaxMap) 
			throws ClientProtocolException, IOException{
		String token = ajaxMap.get("token");
		String perpage = ajaxMap.get("total");
		String p = ajaxMap.get("p");
		String url = url_base + "/cache?token=" 
				+ token + "&p=" + p + "&perpage=" + perpage;
		return crawFromUrl(url, CHARSET);
	}
	
	public static JSONObject stringToJson(String str) {
		try {
			return new JSONObject(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//获取股票信息的json数组
	public static JSONArray getStocksArray(String str){
		JSONObject jsonObj = stringToJson(str); 
		if(jsonObj != null){
			try {
//				saveFile("data/aaa.txt",str);
				System.out.println("craw finnish");
				return jsonObj.getJSONArray("list");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
     * Method: saveHtml 
     * Description: save String to file
     * @param filepath
     * file path which need to be saved
     * @param str
     * string saved
     */
	public static void saveFile(String filepath, String str){
		try {
            /*@SuppressWarnings("resource")
            FileWriter fw = new FileWriter(filepath);
            fw.write(str);
            fw.flush();*/
            OutputStreamWriter outs = new OutputStreamWriter(
            		new FileOutputStream(filepath, true), CHARSET);
            outs.write(str);
            outs.close();
        } catch (IOException e) {
            System.out.println("Error at save html...");
            e.printStackTrace();
        }
	}

    /**
     * Method: InputStream2String 
     * Description: make InputStream to String
     * @param in_st
     * inputstream which need to be converted
     * @param charset
     * encoder of value
     * @throws IOException
     * if an error occurred 
     */
    public static String InputStream2String(InputStream in_st,String charset) 
    		throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
            res.append(line);
        }
        return res.toString();
    }
    
	public static JSONArray getJA(){
		String str = IORW.read("data/stocks_result.txt");
		System.out.println(str);
		try {
			JSONArray ja = new JSONArray(str);
			return ja;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String url_search = "http://www.iwencai.com/stockpick/search?typed=0&preParams=&ts=1&f=1&qs=1&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=pe";
		String url_base_ajax = "http://www.iwencai.com/stockpick";
		
		String ajaxStr = ajaxResquest(url_base_ajax, 
				getAjaxParam(url_search, CHARSET));
		JSONArray stocksArray_json = getStocksArray(ajaxStr);
		saveFile("data/stocks_result.txt", stocksArray_json.toString());
    
	}
	
}