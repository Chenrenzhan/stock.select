package controltest;

import static org.junit.Assert.*;

import java.awt.image.DirectColorModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controller.IORW;

/**
 * @author jasoncar
 *
 */

public class IORWTest {

	final private static String EMPTY_FILE="testdata/testempty.json";
	final private static String NORMAL_FILE="testdata/testnormal.json";
	final private static String WRITE_FILE="testdata/testwrite.json";
	private final static String DIRECT = "testdata";
	
	private final static String TEST_CONTEXT = "this is the test file";
	
	@BeforeClass
	//在类开始之前创建测试文件
	public static void setUpBeforeClass() throws Exception {
		//创建目录
		File direct = new File(DIRECT);
		if(!direct.exists()){
			direct.mkdirs();
		}
		
		//创建空文件测试文件
		File empty = new File(EMPTY_FILE);
		if(!empty.exists()){
			empty.createNewFile();
		}
		
		//创建正常的读写文件，并写入测试内容
		File normal = new File(NORMAL_FILE);
		if(!normal.exists()){
			normal.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(  
	                new FileOutputStream(normal), "utf-8"));  
	        writer.write(TEST_CONTEXT);  
			writer.close();
		}

		// 创建空的写入文件
		File write = new File(WRITE_FILE);
		if (!write.exists()) {
			write.createNewFile();
		}

	}

	@AfterClass
	// 在测试结束之后删除测试文件
	public static void tearDownAfterClass() throws Exception {
		File direct = new File(DIRECT);
		if (direct.exists()) { // 判断文件是否存在
			if (direct.isFile()) { // 判断是否是文件
				direct.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (direct.isDirectory()) { // 否则如果它是一个目录
				File files[] = direct.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					files[i].delete(); 
				}
			}
			direct.delete();
		} else {
			System.out.println("所删除的文件不存在！" + '\n');
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.IORW#read(java.lang.String)}.
	 */
	@Test
	public void testReadEmpty() {
		String empty=IORW.read(EMPTY_FILE);
	assertEquals("", empty);	
		
	}
	@Test
	public void testReadNormal(){
		String context=IORW.read(NORMAL_FILE);
		assertEquals(TEST_CONTEXT, context);
	}
	
	/**
	 * Test method for {@link controller.IORW#write(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testWriteEmpty() {
		String context="";
		try {
			IORW.write(WRITE_FILE, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(context, IORW.read(WRITE_FILE));
		
	}
	@Test
	public void testWriteNormal() {
//		String context="\"Write for test\ntest enter";
		try {
			IORW.write(WRITE_FILE, TEST_CONTEXT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(TEST_CONTEXT, IORW.read(WRITE_FILE));
		
	}

}
