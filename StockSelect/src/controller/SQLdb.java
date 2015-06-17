package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.ui.Saveable;
import org.json.JSONArray;
import org.json.JSONException;

public class SQLdb {
	
	public static final String[] TABLE_COL_NAME = 
			new String[]{"priceChangeRatio", "curPrice", "pe", "dynamicPE", "pb"};
	
	private CrawStocks crawStocks;
	private SingletonDB singletonDB;
	private Connection connection;
	private Statement statement;
	private JSONArray stockArray;
	
	private String tableName;
	private String sourceName;
	//数据来源列表
//	private ArrayList<CrawStocks> dataList;
	
	public SQLdb(CrawStocks crawStocks){
		singletonDB = SingletonDB.Instance();
		statement = singletonDB.getStatement();
		connection = singletonDB.getConnection();
		this.crawStocks = crawStocks;
		
		this.tableName = crawStocks.getTableName();
		this.sourceName = crawStocks.getSourceName();
		
		createdbTable();
	}
	
	public void execute(){
//		createdbTable();
		crawStocks.execute();
		this.stockArray = crawStocks.getDataArray();
		this.tableName = crawStocks.getTableName();
		
//		createdbTable();
		try {
			insertData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){
		
		crawStocks.update();
		
//		JSONArray stocksArray_json = CrawStocksTongHuaShun.getStocksArray(ajaxStr);
		this.stockArray = crawStocks.getDataArray();
		dropTable();
		createdbTable();
		
		try {
			insertData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Boolean executeSQL(String sql) throws SQLException{
		return statement.execute(sql);
		
//		return false;
	}
	
	//查找, conditionSql为查询条件
	public ResultSet query(String conditionSql){
		String querySql = "select * from " + tableName;
		if(conditionSql != ""){
			querySql += " where " + conditionSql;
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(querySql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	// 查找所有记录
	public ResultSet query() {
		String querySql = "select * from " + tableName;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(querySql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	// 查询极值
	public ResultSet queryExtre(String colName) {
		String sqlString = "select min("+colName+"),max("+colName+") from "+tableName;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sqlString);
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs; 
	}
	
	//创建表
	public void createdbTable(){
		try {
			executeSQL(createTableSQL());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createTableSQL(){
		String sql = "create table " + tableName 
				+ "( code varchar(10), shortName varchar(50), "
				+ "priceChangeRatio float(4), curPrice float(4), "
				+ "pe float(4), dynamicPE float(4), pb float(4))";
		
		return sql;
	}
	
	//删除表
	public void dropTable(){
		try{
			queryExtre("code");
			executeSQL("drop table " + tableName);
			connection.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void insert(String valueSql){
		try {
			executeSQL("insert into " + tableName + " values(" + valueSql + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert(JSONArray ja){
		
		insert(jsonArray2insertsql(ja));
	}
	public String jsonArray2insertsql(JSONArray ja){
		String insertsql = "";
		try {
			insertsql = "'" + codeNum(ja.getString(0)) + "','" + ja.getString(1) + "',"
				+ emptyValue2null(ja.getString(2)) + "," + emptyValue2null(ja.getString(3)) + "," 
				+ emptyValue2null(ja.getString(4)) + "," + emptyValue2null(ja.getString(5)) + ","
				+ emptyValue2null(ja.getString(6));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return insertsql;
	}
	
	public String codeNum(String code){
		Pattern pattern = Pattern.compile("\\D");  
        Matcher matcher = pattern.matcher(code);  
        String codeStr = matcher.replaceAll("");
        return codeStr;
	}
	
	public String emptyValue2null(String str){
		if(str.equals("--")){
			return "NULL";
		}
		return str;
	}
	
	public void insertData() throws JSONException{
		int len = stockArray.length();
		for(int i = 0; i < len; ++i){
			JSONArray ja = stockArray.getJSONArray(i);
			insert(ja);
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCount(){
		int count = 0;
		try {
			ResultSet rs = query();
//			
			while(rs.next()){
				count++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	public void shutdow(){
		
		System.out.println("Closedresultsetandstatement");
		try {
			statement.close();
			connection.commit();
			connection.close();
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Committedtransactionandclosedconnection"); 
		
	}
	
	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public JSONArray getStockArray() {
		return stockArray;
	}

	public String getSourceName() {
		return sourceName;
	}

	public static void main(String[] argv) 
			throws ClientProtocolException, IOException, SQLException{
//		CrawStocksTongHuaShun ths = new CrawStocksTongHuaShun();
		CrawStockXueQiu xq = new CrawStockXueQiu();
		System.out.println("xueqiu");
//		SQLdb thsSql = new SQLdb(ths);
//		thsSql.update();
//		thsSql.execute();
		
		SQLdb xqSql = new SQLdb(xq);
		xqSql.update();
	}
}
