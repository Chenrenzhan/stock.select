package controltest;

import static org.junit.Assert.*;
//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ui.StockListTable;
import controller.CrawStocksTongHuaShun;

public class CrawStocksTest {

	private static String ajaxStr;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ajaxStr = CrawStocksTongHuaShun.ajaxResquest(CrawStocksTongHuaShun.URL_BASE_AJAX, 
					CrawStocksTongHuaShun.getToken(CrawStocksTongHuaShun.GET_PARAM_URL, 
							CrawStocksTongHuaShun.CHARSET));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	//测试抓取回来的字符串是否为空
	public void testStringEmpty(){
//		System.out.println(ajaxStr);
		assertThat("", not(ajaxStr));
	}
	
//	@Test
//	//测试抓取回来的字符串是包含数据list
//	public void testString(){
////		System.out.println(ajaxStr);
//		assertThat("list", containsString(ajaxStr));
//	}
	
	@Test
	//测试抓取回来的数据的类型
	public void testClassType() {

		JSONArray stocksArray_json = CrawStocksTongHuaShun.getStocksArray(ajaxStr);
		
		assertThat(stocksArray_json, instanceOf(stocksArray_json.getClass()));
	}
	
	

	@Test
	//测试抓取回来的数据
	public void testResult() {

		JSONArray stocksArray_json = CrawStocksTongHuaShun.getStocksArray(ajaxStr);
		int stocksSum = stocksArray_json.length();
		//断言获取股票的数量要大于2000
		assertThat(stocksSum, greaterThan(2000));
	}
	
	
	
	

}
