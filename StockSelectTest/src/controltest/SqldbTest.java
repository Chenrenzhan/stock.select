package controltest;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.SQLdb;

public class SqldbTest {
	
	private  static SQLdb sqldb;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		sqldb = new SQLdb();
//		sqldb.createdbTable();
//		System.out.println(sqldb.getCount());
//		if(sqldb.getCount() == 0){
//			System.out.println("sssssssssssss");
//			sqldb.update();
//		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sqldb.shutdow();
	}

	@Test
	//测试数据库链接状态
	public void testConnetion() {
		try {
			assertEquals(false, sqldb.getConnection().isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	//测试数据库中记录的总数应该大于2000
	public void testCount(){
		int count = sqldb.getCount();
		assertThat(count, greaterThan(2000));
	}
	
	@Test
	//测试市盈率pe极值
	public void testExtenOf_pe(){
		testExtenValue("pe");
	}
	@Test
	//测试涨跌幅极值
	public void testExtenOf_priceChangeRatio(){
		testExtenValue("priceChangeRatio");
	}
	@Test
	//测试现价极值
	public void testExtenOf_curPrice(){
		testExtenValue("curPrice");
	}
	@Test
	//测试动态市盈率极值
	public void testExtenOf_dynamicPE(){
		testExtenValue("dynamicPE");
	}
	@Test
	//测试市净率极值
	public void testExtenOf_pb(){
		testExtenValue("pb");
	}
	
	//测试最大值最小值查询
	public void testExtenValue(String condition){
		ResultSet rs = sqldb.queryExtre(condition);
		try {
			double min = rs.getDouble(1);
			double max = rs.getDouble(2);
			
			assertThat(min, lessThan(max));
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	//测试市盈率pe查询的结果
	public void testResultOf_pe(){
		try {
			testResult("pe", -10.0, 20.0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	//测试涨跌幅查询的结果
	public void testResultOf_priceChangeRatio(){
		try {
			testResult("priceChangeRatio", -10.0, 20.0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	//测试现价查询的结果
	public void testResultOf_curPrice(){
		testExtenValue("curPrice");
		try {
			testResult("curPrice", 10.0, 20.0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	//测试动态市盈率查询的结果
	public void testResultOf_dynamicPE(){
		testExtenValue("dynamicPE");
		try {
			testResult("dynamicPE", -10.0, 20.0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	//测试市净率查询的结果
	public void testResultOf_pb(){
		testExtenValue("pb");
		try {
			testResult("pb", -10.0, 20);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//测试单一条件查询的结果
	public void testResult(String condition, double min, double max) 
			throws SQLException{
		String query = "(" + condition + ">" + min
				+ " and " + condition + "<" + max + ")";
		ResultSet rs = sqldb.query(query);
		while(rs.next()){
			double d = rs.getDouble(condition);
//			assertThat(d, anyOf(lessThan(max), greaterThan(min)));
			assertThat(d, greaterThan(min));
			assertThat(d, lessThan(max));
		}
	}
	
	
	@Test
	//测试关闭数据库
	public void testShutdown(){
		sqldb.shutdow();
		try {
			assertEquals(true, sqldb.getConnection().isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
