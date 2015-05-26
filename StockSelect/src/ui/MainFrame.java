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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class MainFrame {
	
	private static final Image CLOSE = 
			new Image(Display.getDefault(), "icons/close_btn.png");
	private static final Image CLOSE_HOVER = 
			new Image(Display.getDefault(), "icons/close_btn_active.png");
	private static final Image MIN = 
			new Image(Display.getDefault(), "icons/mini_btn.png");
	private static final Image MIN_HOVER = 
			new Image(Display.getDefault(), "icons/mini_btn_active.png");
	private static final Image REFRESH = 
			new Image(Display.getDefault(), "icons/refresh.png");
	

	private static final String[] HEADER = new String[] { "序号", "股票代码", "股票简称",
			"涨跌幅(%)", "现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };
	private static final int[] HEADER_SIZE = new int[] { 55, 90, 75, 75, 70,
			100, 85, 75 };
	private static final String[] CHOICE_CONDITION = new String[] { "涨跌幅(%)",
			"现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };

	protected Shell shell;
	private Composite topComposite;
	
	private Label btnRefresh;

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
	private CLabel lblResultCount;
	private Table stockListTable;
	private FormData fd_optionComposite;
	private CLabel lblNewLabel;
	private Label lblNewLabel_1;
	private Label btnClose;
	private Label btnMin;

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
		
		createIcon();
		
		CompositeMove();
		
		

	}
	
	public void eventLoop(Display display) {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void createIcon(){
		btnRefresh = new Label(topComposite, SWT.NONE);
		btnRefresh.setToolTipText("刷新");
		btnRefresh.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		FormData fd_btnRefresh = new FormData();
		fd_btnRefresh.top = new FormAttachment(optionComposite, 5, SWT.TOP);
		fd_btnRefresh.bottom = new FormAttachment(lblResultCount, 0, SWT.BOTTOM);
		fd_btnRefresh.right = new FormAttachment(100, -24);
		fd_btnRefresh.left = new FormAttachment(100, -48);
		btnRefresh.setLayoutData(fd_btnRefresh);
		btnRefresh.setText("刷新");
		btnRefresh.setImage(REFRESH);
		btnRefresh.setVisible(true);
		btnRefresh.addMouseTrackListener(new RefreshListerner());
		btnRefresh.addMouseListener(new RefreshListerner());
		
		btnClose = new Label(topComposite, SWT.NONE);
		btnClose.setToolTipText("关闭");
		FormData fd_btnClose = new FormData();
		fd_btnClose.top = new FormAttachment(0);
		fd_btnClose.right = new FormAttachment(100);
		fd_btnClose.bottom = new FormAttachment(0, 24);
		fd_btnClose.left = new FormAttachment(100, -24);
		btnClose.setLayoutData(fd_btnClose);
		btnClose.setText("关闭");
		btnClose.setImage(CLOSE);
		btnClose.addMouseTrackListener(new CloseListerner(shell));
		btnClose.addMouseListener(new CloseListerner(shell));

		btnMin = new Label(topComposite, SWT.NONE);
		btnMin.setToolTipText("最小化窗口");
		FormData fd_btnMin = new FormData();
		fd_btnMin.bottom = new FormAttachment(btnRefresh, -12);
		fd_btnMin.top = new FormAttachment(btnClose, 0, SWT.TOP);
		fd_btnMin.left = new FormAttachment(btnClose, -39, SWT.LEFT);
		fd_btnMin.right = new FormAttachment(btnClose, -15);
		btnMin.setLayoutData(fd_btnMin);
		btnMin.setText("最小化");
		btnMin.setImage(MIN);
		btnMin.addMouseTrackListener(new MinListerner(shell));
		btnMin.addMouseListener(new MinListerner(shell));
		
		
		
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
		shell.setSize(993, 580);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		topComposite = new Composite(shell, SWT.NONE);
		topComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		topComposite.setLayout(new FormLayout());

		createOptionComposite();

		createStockListComposite();
		createIcon();
	}

	public void createOptionComposite() {
		optionComposite = new Composite(topComposite, SWT.NONE);
		fd_optionComposite = new FormData();
		fd_optionComposite.top = new FormAttachment(0, 30);
		fd_optionComposite.bottom = new FormAttachment(100, -10);
		fd_optionComposite.right = new FormAttachment(0, 331);
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
		
//		operation();
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
		collectComposite.setSize(321, 308);
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
		for(int i = 0; i < 8; ++i){
			CollectCondition cc = new CollectCondition(collectComposite);
			cc.setSize(321, 30);
			cc.setLocation(0, 35 * (i+1));
			CLabel lblCondition = cc.getLblCollectCondition();
			lblCondition.setText("收藏条件" + i);
			lblCondition.addMouseTrackListener(new CursorListener(cc));
		}
	}

	public void createStockListComposite() {
		lblResultCount = new CLabel(topComposite, SWT.NONE);
		lblResultCount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		lblResultCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		FormData fd_lblResultCount = new FormData();
		fd_lblResultCount.right = new FormAttachment(optionComposite, 273, SWT.RIGHT);
		fd_lblResultCount.top = new FormAttachment(optionComposite, 0, SWT.TOP);
		fd_lblResultCount.left = new FormAttachment(optionComposite, 6);
		lblResultCount.setLayoutData(fd_lblResultCount);
		lblResultCount.setText("共有3700股符合条件");
		
//		lblNewLabel = new CLabel(topComposite, SWT.NONE);
//		FormData fd_lblNewLabel = new FormData();
//		fd_lblNewLabel.top = new FormAttachment(optionComposite, 0, SWT.TOP);
//		fd_lblNewLabel.left = new FormAttachment(optionComposite, 6);
//		lblNewLabel.setLayoutData(fd_lblNewLabel);
//		lblNewLabel.setText("New Label");
		
		stockListComposite = new Composite(topComposite, SWT.NONE);
		FormData fd_stockListComposite = new FormData();
		fd_stockListComposite.top = new FormAttachment(0, 61);
		fd_stockListComposite.bottom = new FormAttachment(100, -10);
		fd_stockListComposite.right = new FormAttachment(0, 983);
		fd_stockListComposite.left = new FormAttachment(0, 337);
		stockListComposite.setLayoutData(fd_stockListComposite);
		stockListComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		stockListComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		System.out.println(stockListComposite.getBounds());
		
//		stockListTable = new StockListTable(stockListComposite);
		
		stockListTable = new Table(stockListComposite, SWT.BORDER
				| SWT.FULL_SELECTION);
		stockListTable.setHeaderVisible(true);
		stockListTable.setLinesVisible(true);
		System.out.println(stockListTable.getBounds());
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
		
//		lblNewLabel_1 = new Label(topComposite, SWT.NONE);
//		FormData fd_lblNewLabel_1 = new FormData();
//		fd_lblNewLabel_1.top = new FormAttachment(optionComposite, 0, SWT.TOP);
//		fd_lblNewLabel_1.right = new FormAttachment(100, -10);
//		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
//		lblNewLabel_1.setText("New Label");
		
		
		
		
		
		
		for(int i = 0; i < 30; ++i){
			TableItem tableItem = new TableItem(stockListTable, SWT.CENTER);
			tableItem.setText(new String[] 
					{ String.valueOf(i), "2", "3", "4", "5", "6", "7", "8" });
		}
		
	}
}
