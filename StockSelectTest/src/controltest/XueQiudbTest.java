package controltest;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.CrawStockXueQiu;
import controller.CrawStocks;
import controller.CrawStockTongHuaShun;
import controller.StockSourceFactory;

public class XueQiudbTest extends SqldbTest {

	private static StockSourceFactory sourceFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sourceFactory = new StockSourceFactory();
		CrawStocks xueqiu = sourceFactory.make(2);
		SqldbTest.BeforeClass(xueqiu);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SqldbTest.AfterClass();
	}

	@Test
	// 测试数据库链接状态
	public void testConnetion() {
		SqldbTest.connetion();
	}

	@Test
	// 测试数据库中记录的总数应该大于2000
	public void testCount() {
		SqldbTest.count();
	}

	@Test
	// 测试市盈率pe极值
	public void testExtenOf_pe() {
		SqldbTest.extenOf_pe();
	}

	@Test
	// 测试涨跌幅极值
	public void testExtenOf_priceChangeRatio() {
		SqldbTest.extenOf_priceChangeRatio();
	}

	@Test
	// 测试现价极值
	public void testExtenOf_curPrice() {
		SqldbTest.extenOf_curPrice();
	}

	@Test
	// 测试动态市盈率极值
	public void testExtenOf_dynamicPE() {
		SqldbTest.extenOf_dynamicPE();
	}

	@Test
	// 测试市净率极值
	public void testExtenOf_pb() {
		SqldbTest.extenOf_pb();
	}

	@Test
	// 测试市盈率pe查询的结果
	public void testResultOf_pe() {
		SqldbTest.resultOf_pe();
	}

	@Test
	// 测试涨跌幅查询的结果
	public void testResultOf_priceChangeRatio() {
		SqldbTest.resultOf_priceChangeRatio();
	}

	@Test
	// 测试现价查询的结果
	public void testResultOf_curPrice() {
		SqldbTest.resultOf_curPrice();
	}

	@Test
	// 测试动态市盈率查询的结果
	public void testResultOf_dynamicPE() {
		SqldbTest.resultOf_dynamicPE();
	}

	@Test
	// 测试市净率查询的结果
	public void testResultOf_pb() {
		SqldbTest.resultOf_pb();
	}


}
