package controltest;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.SingletonDB;

public class SingletonDBTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	//测试单例，即每次调用都是同一个实例化对象
	public void testSingletonDB() {
		SingletonDB s1 = SingletonDB.Instance();
		SingletonDB s2 = SingletonDB.Instance();
		assertSame(s1, s2);
	}
	
	@Test
	//测试单例类没有public构造函数
	public void testNoPublicConstructors() {
		Class s;
		try {
			s = Class.forName("controller.SingletonDB");
			Constructor[] constructors = s.getConstructors();
			System.out.println(constructors.length);
			assertEquals(0, constructors.length);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
