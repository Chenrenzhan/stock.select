package uitest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class MainFrameTest extends AbstractMainFrameTest{

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testCondition() {
////		fail("Not yet implemented");
//		
//		
//	}
	
	

	//测试市盈率复选框
	@Test
	public void testShiYingLv() {
		
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		assertEquals(true, lbl.isChecked());
	}
	
	//测试涨跌幅复选框
	@Test
	public void testZhangDieFu() {
		
		SWTBotCheckBox lbl = bot.checkBox("涨跌幅(%)");
		lbl.click();
		assertEquals(true, lbl.isChecked());
	}
	
	//测试现价复选框
	@Test
	public void testCurPrice() {
		
		SWTBotCheckBox lbl = bot.checkBox("现价(元)");
		lbl.click();
		assertEquals(true, lbl.isChecked());
	}
	
	//测试市净率复选框
	@Test
	public void testShiJingLv() {
		
		SWTBotCheckBox lbl = bot.checkBox("市净率");
		lbl.click();
		assertEquals(true, lbl.isChecked());
	}
	
	//测试最小值输入框
	@Test
	public void testMinValue(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		SWTBotText text = bot.textWithTooltip("MinValue");
		text.setText("1234567");
		assertEquals("1234567", text.getText());
	}
	
	//测试最大值输入框
	@Test
	public void testMaxValue(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		SWTBotText text = bot.textWithTooltip("MaxValue");
		text.setText("7654321");
		assertEquals("7654321", text.getText());
	}
	
	//测试最小值输入框输入非数字字符
	@Test
	public void testUnNumberMinInput(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		SWTBotText text = bot.textWithTooltip("MinValue");
		text.setText("agile");
		assertEquals("0.00", text.getText());
	}
	
	//测试最大值输入的非数字字符
	@Test
	public void testUnNumberMaxInput(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		SWTBotText text = bot.textWithTooltip("MaxValue");
		text.setText("agile");
		assertEquals("100.00", text.getText());
	}
	
	//测试输入最小值大于最大值的情况
	@Test
	public void testMinBiggerMaxInput(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		SWTBotText maxText = bot.textWithTooltip("MaxValue");
		maxText.setText("12");
		SWTBotText minText = bot.textWithTooltip("MinValue");
		minText.setText("123");
		
		assertEquals("12", minText.getText());
	}
	
	//测试输入最大值小于最小值的情况
	@Test
	public void testMaxSmallerMinInput(){
		SWTBotCheckBox lbl = bot.checkBox("市盈率");
		lbl.click();
		
		SWTBotText minText = bot.textWithTooltip("MinValue");
		minText.setText("123");
		SWTBotText maxText = bot.textWithTooltip("MaxValue");
		maxText.setText("12");
		assertEquals("123", maxText.getText());
	}
	
	
	
	

	@Test
	public void testMin() {
		bot.label("Min").click();
		assertEquals(false, bot.shell("StockPiking").isActive());
	}

	@Test
	public void testClose() {
		bot.label("Close");
		assertEquals(true, bot.shell("StockPiking").isOpen());
	}

}
