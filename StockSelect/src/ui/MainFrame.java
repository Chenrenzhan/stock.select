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

	private static final String[] HEADER = new String[] { "序号", "股票代码", "股票简称",
			"涨跌幅(%)", "现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };
	private static final int[] HEADER_SIZE = new int[] { 55, 90, 75, 75, 70,
			100, 85, 75 };
	private static final String[] CHOICE_CONDITION = new String[] { "涨跌幅(%)",
			"现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };

	protected Shell shell;
	private Composite topComposite;

	private Composite optionComposite;
	private Composite selectComposite;
	private Table conditionTable;
	private CLabel lblSelect;
	private CLabel lblCollectCondition;
	private CLabel lblResetCondition;
	private CLabel lblStartReseach;

	private Composite collectComposite;
	private CLabel lblCollect;

	private Composite stockListComposite;
	private Table stockListTable;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainFrame window = new MainFrame();
			window.open();
			window.eventLoop(Display.getDefault());
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
		
		CompositeMove();
		
//		setCompositeMove();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
	}
	
	public void eventLoop(Display display) {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	//设置按下鼠标可移动窗口
	public void CompositeMove(){
//		setCompositeMove(shell);
		setCompositeMove(topComposite);
		setCompositeMove(optionComposite);
		setCompositeMove(selectComposite);
		setCompositeMove(conditionTable);
		setCompositeMove(collectComposite);
		setCompositeMove(stockListComposite);
		setCompositeMove(stockListTable);
		setCompositeMove(lblSelect);
		setCompositeMove(lblCollect);
		
	}
	
	public void setCompositeMove(Composite composite){
		CompositeMoveListener listener = new CompositeMoveListener(shell);
		composite.addListener(SWT.MouseDown, listener);
		composite.addListener(SWT.MouseMove, listener);
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NO_TRIM);
		shell.setSize(993, 557);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		topComposite = new Composite(shell, SWT.NONE);
		topComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		topComposite.setLayout(new FormLayout());

		createOptionComposite();

		createStockListComposite();

	}

	public void createOptionComposite() {
		optionComposite = new Composite(topComposite, SWT.NONE);
		FormData fd_optionComposite = new FormData();
		fd_optionComposite.bottom = new FormAttachment(0, 544);
		fd_optionComposite.right = new FormAttachment(0, 331);
		fd_optionComposite.top = new FormAttachment(0, 34);
		fd_optionComposite.left = new FormAttachment(0, 10);
		optionComposite.setLayoutData(fd_optionComposite);
		optionComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		createSelectComposite();
		
		createCollectComposite();
		
		
	}

	public void createSelectComposite() {

		selectComposite = new Composite(optionComposite, SWT.NONE);
		selectComposite.setSize(321, 204);
		selectComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		
		lblSelect = new CLabel(selectComposite, SWT.SHADOW_NONE);
		lblSelect.setSize(321, 30);
		lblSelect.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		lblSelect.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12,
				SWT.BOLD));
		lblSelect.setText("选股");
		lblSelect.setAlignment(SWT.CENTER);
		

		createConditionTable();
		
		operation();
	}
	
	public void createConditionTable() {
		conditionTable = new Table(selectComposite, SWT.CHECK
				| SWT.FULL_SELECTION | SWT.MULTI);
		conditionTable.setLocation(0, 30);
		conditionTable.setSize(321, 134);
		conditionTable.setHeaderVisible(true);
		conditionTable.setLinesVisible(true);
		// conditionTable.setLinesVisible(false);
		// conditionTable.setBackgroundMode(SWT.INHERIT_FORCE); //设置背景透明

		TableColumn tcCondition = new TableColumn(conditionTable, SWT.NONE);
		tcCondition.setResizable(false);
		tcCondition.setWidth(117);
		tcCondition.setText("筛选条件");

		TableColumn tcMin = new TableColumn(conditionTable, SWT.NONE);
		tcMin.setResizable(false);
		tcMin.setWidth(102);
		tcMin.setText("最小值");

		TableColumn tcMax = new TableColumn(conditionTable, SWT.NONE);
		tcMax.setResizable(false);
		tcMax.setWidth(102);
		tcMax.setText("最大值");

		for (int i = 0; i < CHOICE_CONDITION.length; ++i) {
			TableItem tableItem_1 = new TableItem(conditionTable, SWT.NONE);
			tableItem_1.setText(new String[] { CHOICE_CONDITION[i], "0.00",
					"100.00" });
			tableItem_1.setText(CHOICE_CONDITION[i]);
			// tableItem_1.set

			final TableEditor editorMin = new TableEditor(conditionTable);
			final TableEditor editorMax = new TableEditor(conditionTable);

			// 创建一个文本框，用于输入文字
			final Text textMin = new Text(conditionTable, SWT.NONE);
			final Text textMax = new Text(conditionTable, SWT.NONE);
			// textMin.setEditable(false);
			textMin.setEnabled(false); // 设置可否编辑
			textMin.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

			// 将文本框当前值，设置为表格中的值
			textMin.setText(tableItem_1.getText(1));
			textMax.setText(tableItem_1.getText(2));
			// 设置编辑单元格水平填充
			editorMin.grabHorizontal = true;
			editorMax.grabHorizontal = true;
			// 关键方法，将编辑单元格与文本框绑定到表格的第一列
			editorMin.setEditor(textMin, tableItem_1, 1);
			editorMax.setEditor(textMax, tableItem_1, 2);
			// 当文本框改变值时，注册文本框改变事件，该事件改变表格中的数据。
			// 否则即使改变的文本框的值，对表格中的数据也不会影响
			textMin.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					editorMin.getItem().setText(1, textMin.getText());
				}
			});
			textMax.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					editorMax.getItem().setText(2, textMax.getText());
				}
			});
		}
	}
	
	public void operation() {
		lblCollectCondition = new CLabel(selectComposite, SWT.CENTER);
		lblCollectCondition.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblCollectCondition.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_SELECTION));
		lblCollectCondition.setBounds(10, 180, 67, 23);
		lblCollectCondition.setText("收藏条件");
		lblCollectCondition.addMouseTrackListener(new CursorListener(shell));

		lblResetCondition = new CLabel(selectComposite, SWT.CENTER);
		lblResetCondition.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_SELECTION));
		lblResetCondition.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblResetCondition.setBounds(125, 180, 67, 23);
		lblResetCondition.setText("重置条件");
		lblResetCondition.addMouseTrackListener(new CursorListener(shell));

		lblStartReseach = new CLabel(selectComposite, SWT.CENTER);
		lblStartReseach.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblStartReseach.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_SELECTION));
		lblStartReseach.setBounds(244, 180, 67, 23);
		lblStartReseach.setText("开始搜索");
		lblStartReseach.addMouseTrackListener(new CursorListener(shell));
	}
	
	public void createCollectComposite() {
		collectComposite = new Composite(optionComposite, SWT.NONE);
		collectComposite.setLocation(0, 232);
		collectComposite.setSize(321, 278);
		collectComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));

		lblCollect = new CLabel(collectComposite, SWT.SHADOW_NONE);
		lblCollect.setSize(321, 30);
		lblCollect.setText("收藏");
		lblCollect.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12,
				SWT.BOLD));
		lblCollect.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		lblCollect.setAlignment(SWT.CENTER);
		
		createCollectCondition();
	}
	
	public void createCollectCondition(){
		for(int i = 0; i < 7; ++i){
			CollectCondition cc = new CollectCondition(collectComposite);
			cc.setSize(321, 30);
			cc.setLocation(0, 35 * (i+1));
			CLabel lblCondition = cc.getLblCollectCondition();
			lblCondition.setText("收藏条件" + i);
			lblCondition.addMouseTrackListener(new CursorListener(cc));
		}
	}

	public void createStockListComposite() {
		stockListComposite = new Composite(topComposite, SWT.NONE);
		FormData fd_stockListComposite = new FormData();
		fd_stockListComposite.bottom = new FormAttachment(0, 544);
		fd_stockListComposite.right = new FormAttachment(0, 983);
		fd_stockListComposite.top = new FormAttachment(0, 34);
		fd_stockListComposite.left = new FormAttachment(0, 337);
		stockListComposite.setLayoutData(fd_stockListComposite);
		stockListComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		stockListComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		stockListTable = new Table(stockListComposite, SWT.BORDER
				| SWT.FULL_SELECTION);
		stockListTable.setHeaderVisible(true);
		stockListTable.setLinesVisible(true);

		for (int i = 0; i < HEADER.length; ++i) {
			TableColumn tblclmnNewColumn = new TableColumn(stockListTable,
					SWT.CENTER);
			tblclmnNewColumn.setWidth(HEADER_SIZE[i]);
			tblclmnNewColumn.setText(HEADER[i]);
			// tblclmnNewColumn.setMoveable(true);
			tblclmnNewColumn.setResizable(false);
			tblclmnNewColumn.setAlignment(SWT.CENTER);
			// tblclmnNewColumn.setFont(SWTResourceManager.getFont("Microsoft YaHei UI",
			// 10, SWT.BOLD));
		}

		TableCursor tableCursor = new TableCursor(stockListTable, SWT.BORDER);

		for(int i = 0; i < 30; ++i){
			TableItem tableItem = new TableItem(stockListTable, SWT.CENTER);
			tableItem.setText(new String[] 
					{ String.valueOf(i), "2", "3", "4", "5", "6", "7", "8" });
		}
		
	}
}
