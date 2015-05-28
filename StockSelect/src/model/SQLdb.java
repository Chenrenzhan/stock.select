package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.ui.Saveable;
import org.json.JSONArray;
import org.json.JSONException;

import controller.CrawStocks;

public class SQLdb {
	private static final String URL_RESEACH = 
			"http://www.iwencai.com/stockpick/search"
			+ "?typed=0&preParams=&ts=1&f=1&qs=1&selfsectsn="
			+ "&querytype=&searchfilter=&tid=stockpick&w=pe";
	private static final String URL_BASE_AJAX = 
			"http://www.iwencai.com/stockpick";
	
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String PROTOCOL = "jdbc:derby:";
	private static final String DB_NAME = "data/stockdb";
	private static final String TABLE_NAME = "stock";
	
	private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME 
			+ "( code varchar(10), shortName varchar(50), "
			+ "priceChangeRatio float(4), curPrice float(4), "
			+ "pe float(4), dynamicPE float(4), pb float(4))";
	
	public static final String[] TABLE_COL_NAME = 
			new String[]{"priceChangeRatio", "curPrice", "pe", "dynamicPE", "pb"};
	
	private static final String USERNAME = "user1";
	private static final String PASSWORD = "user1";
	
	private Connection connection;
	private Statement statement;
	private JSONArray stockArray;
	
	
	public SQLdb(){
		
		loadDriver();
		statement = connect();
//		createdbTable();
//		this.stockArray = stockArray;
	}
	
	public SQLdb(JSONArray stockArray){
		
		loadDriver();
		statement = connect();
		this.stockArray = stockArray;
		
		dropTable();
		createdbTable();
	}
	
	public void update(){
		
		String ajaxStr = "[]";
		try {
			ajaxStr = CrawStocks.ajaxResquest(CrawStocks.URL_BASE_AJAX, 
					CrawStocks.getAjaxParam(CrawStocks.URL_RESEACH, 
							CrawStocks.CHARSET));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray stocksArray_json = CrawStocks.getStocksArray(ajaxStr);
		this.stockArray = stocksArray_json;
		
		dropTable();
		createdbTable();
		try {
			insertData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void loadDriver() {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement connect(){
		try { // load the driver
//			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			System.out.println("Load the embedded driver");
			connection = null;
			Properties props = new Properties();
			props.put("user", USERNAME); 
			props.put("password", PASSWORD);
			//create and connect the database named helloDB
			connection=DriverManager.getConnection(PROTOCOL + DB_NAME+ ";create=true");
			System.out.println("create and connect to helloDB");
			connection.setAutoCommit(false);
			// create a table and insert two records
			Statement s = connection.createStatement();
			System.out.println("connections finish");
			return s;
		}
		catch (SQLException e) {
		    for (Throwable t : e) {
		        System.out.println("Message: " + t.getMessage());
		    }

		}
		return null;
	}
	
	public void executeSQL(String sql){
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			do {
		        System.out.println("\n----- SQLException -----");
		        System.out.println("  SQLState:   " + e.getSQLState());
		        System.out.println("  Error Code: " + e.getErrorCode());
		        System.out.println("  Message:    " + e.getMessage());
		        e = e.getNextException();
		    } while (e != null);
			for (Throwable t : e) {
		        System.out.println("Message: " + t.getMessage());
		    }
		}
	}
	
	//查找, conditionSql为查询条件
	public ResultSet query(String conditionSql){
		String querySql = "select * from " + TABLE_NAME;
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
		String querySql = "select * from " + TABLE_NAME;
		
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
	String sqlString = "select min("+colName+"),max("+colName+") from "+TABLE_NAME;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sqlString);
			rs.next();
			System.out.println("min   " + rs.getDouble(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs; 
	}
	
	//创建表
	public void createdbTable(){
		try{
			
			statement.execute("select code form " + TABLE_NAME);
			
		} catch (SQLException e) {
			executeSQL(CREATE_TABLE_SQL);

			try {
				connection.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}
	
	//删除表
	public void dropTable(){
		try{
			queryExtre("code");
			executeSQL("drop table " + TABLE_NAME);
			connection.commit();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	
	public void insert(String valueSql){
		executeSQL("insert into " + TABLE_NAME + " values(" + valueSql + ")");
	}
	public void insert(JSONArray ja){
		
		insert(jsonArray2insertsql(ja));
//		executeSQL("insert into hellotable values(" + valueSql + ")");
	}
//	code, shortName, priceChangeRatio, curPrice, pe, dynamicPE, pb
	public String jsonArray2insertsql(JSONArray ja){
		String insertsql = "";
		try {
			insertsql = "'" + ja.getString(0).substring(0, 6) + "','" + ja.getString(1) + "',"
				+ emptyValue2null(ja.getString(2)) + "," + emptyValue2null(ja.getString(3)) + "," 
				+ emptyValue2null(ja.getString(4)) + "," + emptyValue2null(ja.getString(5)) + ","
				+ emptyValue2null(ja.getString(6));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return insertsql;
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
	
	public static void main(String[] argv) 
			throws ClientProtocolException, IOException, SQLException{
		
		
//		String ajaxStr = CrawStocks.ajaxResquest(url_base_ajax, 
//				CrawStocks.getAjaxParam(url_search, CrawStocks.CHARSET));
//		JSONArray stocksArray_json = CrawStocks.getStocksArray(ajaxStr);
		
//		JSONArray stocksArray_json = CrawStocks.getJA();
//		System.out.println(stocksArray_json.toString());
//		SQLdb sqldb = new SQLdb(stocksArray_json);
		SQLdb sqldb = new SQLdb();
//		sqldb.update();
//		sqldb.connect();
//		sqldb.dropTable();
//		sqldb.createdbTable();
//		try {
//			sqldb.insertData();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ResultSet rs = sqldb.query();
//		ResultSet rs = sqldb.statement.executeQuery("select * from stock");
		while(rs.next()) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < 7; ++i){
				builder.append(rs.getString(i + 1));
				builder.append("  ");
			}
		}
		rs.close();
		
//		for(int i = 0; i < TABLE_COL_NAME.length; ++i){
//			ResultSet rs1 = sqldb.queryExtre(TABLE_COL_NAME[i]);
//			System.out.println(rs1.getString(1) + "   " + rs1.getString(2));
//		}
		System.out.println("drop Tabel");
		sqldb.dropTable();
		System.out.println("drop suceess");
		sqldb.createdbTable();
		sqldb.getCount();
//		sqldb.shutdow();
	}
}
