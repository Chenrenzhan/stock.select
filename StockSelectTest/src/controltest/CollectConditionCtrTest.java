package controltest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.CollectConditionCtrl;

public class CollectConditionCtrTest {

	private static  CollectConditionCtrl ccCtrl;
	private static  JSONArray conditionArray;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ccCtrl = new CollectConditionCtrl();
		conditionArray = ccCtrl.getConditionArray();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	//测试收藏条件的最大数不应超过8
	public void testMaxLeng() {
		int len = conditionArray.length();
		assertThat(len, lessThanOrEqualTo(8));
	}

//	@Test
//	//测试删除一条收藏条件
//	public void tesDelete() {
//		int before = conditionArray.length();
//		System.out.println(conditionArray.toString());
//		System.out.println(before);
//		try {
//			ccCtrl.deleteConllection(0);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int len = conditionArray.length();
//		System.out.println(len);
//		assertThat(len, equalTo(before - 1));
//	}
//	
	
}
