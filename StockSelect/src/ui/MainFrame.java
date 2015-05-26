package ui;
import javax.lang.model.type.PrimitiveType;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;


public class MainFrame {
	
	private static final String[] HEADER = 
			new String[]{"序号", "股票代码", "股票简称", "涨跌幅(%)", 
			"现价(元)", "市盈率(pe)", "动态市盈率", "市净率"};
	private static final int[] HEADER_SIZE = 
			new int[]{55, 88, 74, 74, 66, 100, 85, 78};
	private static final String[] CHOICE_CONDITION = 
			new String[]{"涨跌幅(%)", "现价(元)", "市盈率(pe)", "动态市盈率", "市净率"};

	protected Shell shell;
	private Table table;
	private Table table_1;
	private Table table_2;
	private Table table_3;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainFrame window = new MainFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(987, 593);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		composite.setLayout(new FormLayout());
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(0, 544);
		fd_composite_1.right = new FormAttachment(0, 331);
		fd_composite_1.top = new FormAttachment(0, 34);
		fd_composite_1.left = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setBounds(0, 0, 321, 510);
		
		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		composite_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_4.setBounds(0, 232, 321, 278);
		
		CLabel label = new CLabel(composite_4, SWT.SHADOW_NONE);
		label.setSize(321, 30);
		label.setText("收藏");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		label.setAlignment(SWT.CENTER);
		
		Composite composite_5 = new Composite(composite_3, SWT.NONE);
		composite_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_5.setBounds(0, 0, 321, 204);
		
		table_3 = new Table(composite_5, SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		table_3.setLocation(0, 30);
		table_3.setSize(321, 134);
		table_3.setHeaderVisible(true);
		table_3.setLinesVisible(true);
		//		table_3.setLinesVisible(false);
		//		table_3.setBackgroundMode(SWT.INHERIT_FORCE); //设置背景透明
				
				TableColumn tableColumn = new TableColumn(table_3, SWT.NONE);
				tableColumn.setResizable(false);
				tableColumn.setWidth(117);
				tableColumn.setText("筛选条件");
				
				TableColumn tableColumn_1 = new TableColumn(table_3, SWT.NONE);
				tableColumn_1.setResizable(false);
				tableColumn_1.setWidth(102);
				tableColumn_1.setText("最小值");
				
				TableColumn tableColumn_2 = new TableColumn(table_3, SWT.NONE);
				tableColumn_2.setResizable(false);
				tableColumn_2.setWidth(102);
				tableColumn_2.setText("最大值");
				
				CLabel lblNewLabel = new CLabel(composite_5, SWT.SHADOW_NONE);
				lblNewLabel.setSize(321, 30);
				lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
				lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
				lblNewLabel.setText("选股");
				lblNewLabel.setAlignment(SWT.CENTER);
				
				CLabel lblNewLabel_1 = new CLabel(composite_5, SWT.CENTER);
				lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
				lblNewLabel_1.setBounds(10, 180, 67, 23);
				lblNewLabel_1.setText("收藏条件");
				lblNewLabel_1.addMouseTrackListener(new CursorListener(shell));
				
				CLabel lblNewLabel_2 = new CLabel(composite_5, SWT.CENTER);
				lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
				lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel_2.setBounds(125, 180, 67, 23);
				lblNewLabel_2.setText("重置条件");
				lblNewLabel_2.addMouseTrackListener(new CursorListener(shell));
				
				CLabel lblNewLabel_3 = new CLabel(composite_5, SWT.CENTER);
				lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
				lblNewLabel_3.setBounds(244, 180, 67, 23);
				lblNewLabel_3.setText("开始搜索");
				lblNewLabel_3.addMouseTrackListener(new CursorListener(shell));
		
		
		for(int i = 0; i < CHOICE_CONDITION.length; ++i){
			TableItem tableItem_1 = new TableItem(table_3, SWT.NONE);
			tableItem_1.setText(new String[] {CHOICE_CONDITION[i], "0.00", "100.00"});
			tableItem_1.setText(CHOICE_CONDITION[i]);
//			tableItem_1.set
			
			final TableEditor editorMin = new TableEditor (table_3);  
			final TableEditor editorMax = new TableEditor (table_3);  

	        //创建一个文本框，用于输入文字  
	        final Text textMin = new Text (table_3, SWT.NONE); 
	        final Text textMax = new Text (table_3, SWT.NONE); 
//	        textMin.setEditable(false);
	        textMin.setEnabled(false); //设置可否编辑
	        textMin.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

	        //将文本框当前值，设置为表格中的值  
	        textMin.setText(tableItem_1.getText(1));  
	        textMax.setText(tableItem_1.getText(2));  
	        //设置编辑单元格水平填充  
	        editorMin.grabHorizontal = true;  
	        editorMax.grabHorizontal = true; 
	        //关键方法，将编辑单元格与文本框绑定到表格的第一列  
	        editorMin.setEditor(textMin, tableItem_1, 1);  
	        editorMax.setEditor(textMax, tableItem_1, 2); 
	        //当文本框改变值时，注册文本框改变事件，该事件改变表格中的数据。  
	        //否则即使改变的文本框的值，对表格中的数据也不会影响  
	        textMin.addModifyListener( new ModifyListener(){  
	          public void modifyText(ModifyEvent e) {  
	            editorMin.getItem().setText(1,textMin.getText());  
	          }  
	        });  
	        textMax.addModifyListener( new ModifyListener(){  
		          public void modifyText(ModifyEvent e) {  
		            editorMax.getItem().setText(2,textMax.getText());  
		          }  
		        }); 
		}
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(0, 544);
		fd_composite_2.right = new FormAttachment(0, 961);
		fd_composite_2.top = new FormAttachment(0, 34);
		fd_composite_2.left = new FormAttachment(0, 337);
		composite_2.setLayoutData(fd_composite_2);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table_2 = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table_2.setHeaderVisible(true);
		table_2.setLinesVisible(true);
		
		for(int i = 0; i < HEADER.length; ++i){
			TableColumn tblclmnNewColumn = new TableColumn(table_2, SWT.CENTER);
			tblclmnNewColumn.setWidth(HEADER_SIZE[i]);
			tblclmnNewColumn.setText(HEADER[i]);
//			tblclmnNewColumn.setMoveable(true); 
			tblclmnNewColumn.setResizable(false);
			tblclmnNewColumn.setAlignment(SWT.CENTER);
//			tblclmnNewColumn.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		}
		
//		TableColumn tblclmnNewColumn = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn.setWidth(55);
//		tblclmnNewColumn.setText("序号");
//		tblclmnNewColumn.setMoveable(true); 
//		tblclmnNewColumn.setResizable(false);
//		
//		TableColumn tblclmnNewColumn_1 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_1.setWidth(88);
//		tblclmnNewColumn_1.setText("股票代码");
//		tblclmnNewColumn_1.setMoveable(true); 
//		
//		TableColumn tblclmnNewColumn_2 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_2.setMoveable(true);
//		tblclmnNewColumn_2.setWidth(74);
//		tblclmnNewColumn_2.setText("股票简称");
//		
//		TableColumn tblclmnNewColumn_3 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_3.setWidth(74);
//		tblclmnNewColumn_3.setText("涨跌幅(%)");
//		
//		TableColumn tblclmnNewColumn_6 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_6.setWidth(66);
//		tblclmnNewColumn_6.setText("现价(元)");
//		
//		TableColumn tblclmnNewColumn_4 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_4.setWidth(100);
//		tblclmnNewColumn_4.setText("市盈率(pe)");
//		
//		TableColumn tblclmnNewColumn_5 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_5.setWidth(85);
//		tblclmnNewColumn_5.setText("预测市盈率");
//		
//		TableColumn tblclmnNewColumn_7 = new TableColumn(table_2, SWT.CENTER);
//		tblclmnNewColumn_7.setWidth(78);
//		tblclmnNewColumn_7.setText("市净率");
		
		TableCursor tableCursor = new TableCursor(table_2, SWT.BORDER);
		
		TableItem tableItem = new TableItem(table_2, SWT.CENTER);
//		tableItem.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		tableItem.setText(new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
//		tableItem.set
//		Composite composite_3 = new Composite(composite_2, SWT.NONE);
//		composite_3.setLayout(new TableColumnLayout());
//		
//		TableViewer tableViewer = new TableViewer(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
//		table_1 = tableViewer.getTable();
//		table_1.setHeaderVisible(true);
//		table_1.setLinesVisible(true);
		
//		TableViewer tableViewer = new TableViewer(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
//		table = tableViewer.getTable();
//		table.setLinesVisible(true);
//		table.setHeaderVisible(true);

	}
}
