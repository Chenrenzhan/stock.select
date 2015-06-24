package controltest;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.CrawStockTongHuaShun;
import controller.CrawStockXueQiu;
import controller.StockSourceFactory;

public class StockSourceFactoryTest {
	
	private static StockSourceFactory sourceFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sourceFactory = new StockSourceFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testTongHuaShun() throws Exception {
		CrawStockTongHuaShun ths = new CrawStockTongHuaShun();
		assertThat(ths, instanceOf(sourceFactory.make(1).getClass()));
	}

	@Test
	public void testXueQiu() throws Exception {
		CrawStockXueQiu xq = new CrawStockXueQiu();
		assertThat(xq, instanceOf(sourceFactory.make(2).getClass()));
	}
}
