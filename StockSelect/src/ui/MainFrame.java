package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CollectionCondition;
import model.Condition;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.CollectConditionCtrl;
import controller.CrawStockXueQiu;
import controller.CrawStocks;
import controller.CrawStockTongHuaShun;
import controller.SQLdb;
import controller.StockSourceFactory;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Combo;

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
	public static final String[] TABLE_COL_NAME = 
			new String[]{"priceChangeRatio", "curPrice", "pe", "dynamicPE", "pb"};

	//最大的收藏条件个数
	private static final int MAX_COLLECTIONS_LEN = 8;
	
	protected Shell shell;
	private Composite topComposite;
	
	private Label btnRefresh;

	private Composite optionComposite;
	private Composite selectComposite;
	private Composite selectHeadComposite;
	private Table conditionTable;
	private ArrayList<TableItem> conditionItem;
	private ArrayList<TableEditor> minEditorItem;
//	private ArrayList<Text> minTextItem;
	private ArrayList<TableEditor> maxEditortem;
//	private ArrayList<Text> maxTextItem;
	private ArrayList<Condition> textItem;
	private CLabel lblSelect;
	private CLabel lblCollectCondition;
	private CLabel lblResetCondition;
	private CLabel lblStartReseach;

	private CompositeMove compositeMove;
	
	private Composite collectComposite;
	private Composite colHeadComposite;
	private Composite collectionComposite;
	private CLabel lblCollect;

	private Composite stockListComposite;
	private CLabel lblResultCount;
	private Table stockListTable;
	private FormData fd_optionComposite;
	private CLabel lblNewLabel;
	private Label lblNewLabel_1;
	private Label btnClose;
	private Label btnMin;
	
	private ArrayList<SQLdb> sqldbs;
	private SQLdb sqldb;
	private ArrayList<String> sourceNames;
	
	private CollectConditionCtrl collectCondCtrl;
	
	private ArrayList<Condition> conditionValue;
	
	private ArrayList<CollectCondition> collCompArray;
	
	private StockSourceFactory sourceFactory;

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
		
//		sqldb = new SQLdb();//连接数据库
		
		//创建开始界面
		Shell startShell = new StartShell(Display.getDefault());
		startShell.open();
		try {
			initSql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sqldb.getCount() == 0) {
			sqldb.update();
		}
		//数据加载完成，注销开始界面
		startShell.dispose();
		
		createContents();
		
		shell.open();
		shell.layout();
	}
	
	public void eventLoop(Display display) {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void initSql() throws Exception{
		if(sqldbs == null){
			sqldbs = new ArrayList<SQLdb>();
		}
		if(sourceNames == null){
			sourceNames = new ArrayList<String>();
		}
		
		sourceFactory = new StockSourceFactory();
		
		CrawStocks ths = sourceFactory.make(1);
		CrawStocks xueqiu = sourceFactory.make(2);
		
		sqldbs.add(new SQLdb(ths));
		sqldbs.add(new SQLdb(xueqiu));
		
		sourceNames.add(ths.getSourceName());
		sourceNames.add(xueqiu.getSourceName());
		
		sqldb = sqldbs.get(0);
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
		btnRefresh.addMouseListener(new MouseListenerAdapt() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Thread td =  new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						sqldb.update();
						System.out.println(("update finished"));
					}
				});
				td.start();
			}
		});
		
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
		compositeMove = new CompositeMove(shell);
		compositeMove.add(topComposite);
		compositeMove.add(optionComposite);
		compositeMove.add(selectComposite);
		compositeMove.add(conditionTable);
		compositeMove.add(collectComposite);
		compositeMove.add(stockListComposite);
		compositeMove.add(stockListTable);
		compositeMove.add(lblSelect);
		compositeMove.add(lblCollect);
		compositeMove.add(lblResultCount);
		compositeMove.add(collectionComposite);
		compositeMove.add(selectHeadComposite);
		compositeMove.add(colHeadComposite);
		
	}
	
//	public void setCompositeMove(Composite composite){
//		CompositeMoveListener listener = new CompositeMoveListener(shell);
//		composite.addListener(SWT.MouseDown, listener);
//		composite.addListener(SWT.MouseMove, listener);
//	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NO_TRIM);
		shell.setSize(993, 580);
		shell.setText("选股神器");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		shellCenter();
		
		topComposite = new Composite(shell, SWT.NONE);
		topComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		topComposite.setLayout(new FormLayout());

		collectCondCtrl = new CollectConditionCtrl();
		
		collCompArray = new ArrayList<CollectCondition>();
		
		createOptionComposite();

		createStockListComposite();
		createIcon();
		
		createIcon();
		
		CompositeMove();
		
	}
	
	//使shell居中显示
	public void shellCenter(){
		Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 3;
		shell.setLocation(x, y);
	}
	
	
	public void createOptionComposite() {
		optionComposite = new Composite(topComposite, SWT.NONE);
		fd_optionComposite = new FormData();
		fd_optionComposite.top = new FormAttachment(0, 30);
		fd_optionComposite.bottom = new FormAttachment(100, -10);
		fd_optionComposite.right = new FormAttachment(0, 331);
		fd_optionComposite.left = new FormAttachment(0, 10);
		optionComposite.setLayoutData(fd_optionComposite);
		optionComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		createSelectComposite();
		
		createCollectComposite();
		
		
	}

	public void createSelectComposite() {

		selectComposite = new Composite(optionComposite, SWT.NONE);
		selectComposite.setSize(321, 204);
		selectComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));

		createSelectHeadComposite();
		
		createConditionTable();
		
		operation();
	}
	
	public void createSelectHeadComposite(){
		selectHeadComposite = new Composite(selectComposite, SWT.NONE);
		selectHeadComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		selectHeadComposite.setBounds(0, 0, 321, 30);
		
		lblSelect = new CLabel(selectHeadComposite, SWT.SHADOW_NONE);
		lblSelect.setLocation(0, 0);
		lblSelect.setSize(80, 30);
		lblSelect.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblSelect.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12,
				SWT.BOLD));
		lblSelect.setText("选股");
		lblSelect.setAlignment(SWT.CENTER);
		
		final Combo combo = new Combo(selectHeadComposite, SWT.READ_ONLY);
		combo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		combo.setToolTipText("选取数据来源");
		combo.setBounds(230, 2, 88, 25);
		
		String[] arr = new String[sourceNames.size()];
		sourceNames.toArray(arr);
		combo.setItems(arr);
		combo.select(0);
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				int index = combo.getSelectionIndex();
				sqldb = sqldbs.get(index);
//				String text = lblResultCount.getText();
//				text = text.replaceAll("\\(.*?\\)", "(" + sqldb.getSourceName() + ")");
//				lblResultCount.setText(text);
				try {
					setExtreValue();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(TableItem ti : conditionItem){
					ti.setChecked(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void createConditionTable() {
		conditionTable = new Table(selectComposite, SWT.CHECK
				| SWT.FULL_SELECTION | SWT.MULTI);
		conditionTable.setLocation(0, 30);
		conditionTable.setSize(321, 134);
		conditionTable.setHeaderVisible(true);
		conditionTable.setLinesVisible(true);

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

		conditionItem = new ArrayList<TableItem>();
		minEditorItem = new ArrayList<TableEditor>();
		maxEditortem = new ArrayList<TableEditor>();
		textItem = new ArrayList<Condition>();
		
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
			
			conditionItem.add(tableItem_1);
			minEditorItem.add(editorMin);
//			minTextItem.add(textMin);
			maxEditortem.add(editorMax);
//			maxTextItem.add(textMax);
			textItem.add(new Condition(i, textMin, textMax));
		}
		
		try {
			setExtreValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		collectListerner();
		
		lblResetCondition = new CLabel(selectComposite, SWT.CENTER);
		lblResetCondition.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_SELECTION));
		lblResetCondition.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblResetCondition.setBounds(125, 180, 67, 23);
		lblResetCondition.setText("重置条件");
		lblResetCondition.addMouseTrackListener(new CursorListener(shell));
		reset();

		lblStartReseach = new CLabel(selectComposite, SWT.CENTER);
		lblStartReseach.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblStartReseach.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_SELECTION));
		lblStartReseach.setBounds(244, 180, 67, 23);
		lblStartReseach.setText("开始搜索");
		
		lblStartReseach.addMouseTrackListener(new CursorListener(shell));
		startReseach();
	}
	
	public void createCollectComposite() {
		collectComposite = new Composite(optionComposite, SWT.NONE);
		collectComposite.setLocation(0, 232);
		collectComposite.setSize(321, 308);
		collectComposite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		
		createCollectionComp();
		
		try {
			showCollection();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		createCollectCondition();
	}

	public void createCollectionComp() {
		collectionComposite = new Composite(collectComposite, SWT.NONE);
		collectionComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		collectionComposite.setBounds(0, 30, 321, 278);
		collectionComposite.setLayout(null);
		
		createCollectionHeadCom();
	}

	public void createCollectionHeadCom() {
		colHeadComposite = new Composite(collectComposite, SWT.NONE);
		colHeadComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		colHeadComposite.setBounds(0, 0, 321, 30);

		lblCollect = new CLabel(colHeadComposite, SWT.SHADOW_NONE);
		lblCollect.setSize(80, 30);
		lblCollect.setText("收藏");
		lblCollect.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12,
				SWT.BOLD));
		lblCollect.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblCollect.setAlignment(SWT.CENTER);
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
		lblResultCount.setText("共有 0 股符合条件 (" + sqldb.getSourceName() + ")");
		
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
		
		stockListTable = new Table(stockListComposite, SWT.BORDER
				| SWT.FULL_SELECTION);
		stockListTable.setHeaderVisible(true);
		stockListTable.setLinesVisible(true);
		for (int i = 0; i < HEADER.length; ++i) {
			TableColumn tblclmnNewColumn = new TableColumn(stockListTable,
					SWT.CENTER);
			tblclmnNewColumn.setWidth(HEADER_SIZE[i]);
			tblclmnNewColumn.setText(HEADER[i]);
			tblclmnNewColumn.setResizable(false);
			tblclmnNewColumn.setAlignment(SWT.CENTER);
		}

		try {
			createTableItem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createTableItem() throws SQLException{
		ResultSet rs = sqldb.query();
	}
	
	public void clearTableItem(){
		stockListTable.removeAll();
	}

	// 设置筛选条件的最大值最小值
	public void setExtreValue() throws SQLException {
			for (int i = 0; i < conditionItem.size(); ++i) {
				Text mintext = (Text) textItem.get(i).getMin();
				Text maxtext = (Text) textItem.get(i).getMax();
				
				ResultSet rs = sqldb.queryExtre(TABLE_COL_NAME[i]);
				String min = rs.getString(1);
				String max = rs.getString(2);
				TableItem tim = conditionItem.get(i);
				if(min == null || max == null){
					tim.setGrayed(true);
					min = "--";
					max = "--";
				}
				else {
					tim.setGrayed(false);
				}
				
				
				mintext.setText(min);
				maxtext.setText(max);
			}
		}
	
	//显示结果
	public void showResult(ResultSet rs) throws SQLException {
		
		clearTableItem();
		
		int index = 1;
		while (rs.next()) {
			TableItem tableItem = new TableItem(stockListTable, SWT.CENTER);
			String[] text = new String[8];

			text[0] = String.valueOf(index++);

			for (int i = 1; i < 8; ++i) {
				String str = rs.getString(i);
				if(str == null)
					str = "--";
				text[i] = str;
			}
			tableItem.setText(text);
		}
		rs.close();
		lblResultCount.setText("共有 " + (index-1) + " 股符合条件 (" 
		+ sqldb.getSourceName() + ")");
	}

	// 开始搜索按钮
	public void startReseach() {
		lblStartReseach.addMouseListener(new MouseListenerAdapt() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < conditionItem.size(); ++i) {
					TableItem ti = conditionItem.get(i);
					Boolean isChecked = ti.getChecked();
					Condition cd = textItem.get(i);
					cd.setIsChosen(isChecked);
				}
				ResultSet rs = reseach();
				try {
					showResult(rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	//开始搜索
	public ResultSet reseach(){
		ResultSet rs = null;
		String querySql = "";
		for(int i = 0; i < textItem.size(); ++i){
			Condition cd = textItem.get(i);
			if(cd.getIsChosen()){
				String min = ((Text) cd.getMin()).getText();
				String max = ((Text) cd.getMax()).getText();
				try{
					Double.valueOf(min);
					Double.valueOf(max);
				}
				catch(Exception e){
					e.printStackTrace();
					continue;
				}
//				if(min.equals("--") || max.equals("--")){
//					continue;
//				}
				
				querySql += "(" + TABLE_COL_NAME[i] + ">" 
						 + min +  " and " + TABLE_COL_NAME[i] + "<" 
						 + max + ")" + " and ";
			}
		}
		if(querySql == ""){
			querySql = "pe>100 and pe<0";
		}
		else{
			querySql = querySql.substring(0, querySql.length() - 5);
			querySql = "(" + querySql + ")" ;
		}
		
		rs = sqldb.query(querySql);
		return rs;
	}

	public void reset(){
		lblResetCondition.addMouseListener(new MouseListenerAdapt() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				try {
					setExtreValue();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(TableItem ti : conditionItem){
					ti.setChecked(false);
				}
			}
		});
	}
	
//	lblCollectCondition.addMouseTrackListener(new CursorListener(shell));
	public void collectListerner(){
		lblCollectCondition.addMouseListener(new MouseListenerAdapt() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ConditionNameDialog nameDialog = new ConditionNameDialog(shell);
				nameDialog.open();
				String name = nameDialog.getName();
				if(name == null){
					return ;
				}
				setChecked();
				CollectionCondition cc = 
						new CollectionCondition(name, textItem);
				try {
					collectCondCtrl.addCollection(cc);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				collectCondCtrl.save();
				try {
					showCollection();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setChecked(){
		for(int i = 0; i < conditionItem.size(); ++i){
			textItem.get(i).setIsChosen(
					conditionItem.get(i).getChecked());
		}
	}
	
	//显示收藏条件
	public void showCollection() throws JSONException{
		if(collCompArray != null){
			for(CollectCondition cctemp : collCompArray){
				cctemp.dispose();
			}
		}
		collCompArray = null;
		collCompArray = new ArrayList<CollectCondition>();
		
		collectionComposite.setVisible(true);
		collectionComposite.layout(true);
		
		JSONArray conditionArray = collectCondCtrl.getConditionArray();
		for(int i = 0; i < conditionArray.length(); ++i){
			final JSONObject jo = conditionArray.getJSONObject(i);
			String name = jo.getString("name");
			
			CollectCondition cc = new CollectCondition(collectionComposite, i);
			cc.setSize(321, 30);
			cc.setLocation(0, 35*i + 5);
			CLabel lblCondition = cc.getLblCollectCondition();
			lblCondition.setText(name);
			lblCondition.addMouseTrackListener(new CursorListener(cc));
			cc.setVisible(true);
			
			lblCondition.addMouseListener(new MouseListenerAdapt() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					try {
						showCondition(jo);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			Label lblDelete = cc.getLblDelete();
			lblDelete.addMouseListener(new MouseListenerAdapt() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					Label lbl = (Label) e.getSource();
					int index = (int) lbl.getData("index");
					try {
						collectCondCtrl.deleteConllection(index);
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					collectCondCtrl.save();
					try {
						showCollection();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			collCompArray.add(cc);
		}
	}

	//显示选中的收藏的条件
	public void showCondition(JSONObject jo) throws JSONException{
		JSONArray ja = jo.getJSONArray("condition");
		
		for(int i = 0; i < conditionItem.size(); ++i){
			conditionItem.get(i).setChecked(false);
			
			JSONObject jotemp = ja.getJSONObject(i);
			Boolean isChosen = jotemp.getBoolean("isChosen");
			if(!isChosen){
				continue;
			}
			conditionItem.get(i).setChecked(isChosen);
			textItem.get(i).setIsChosen(isChosen);
			((Text) textItem.get(i).getMin())
				.setText(jotemp.getString("min"));
			((Text) textItem.get(i).getMax())
				.setText(jotemp.getString("max"));
		}
	}
	
	public void selecCondition() {

	}

	public Shell getShell() {
		// TODO Auto-generated method stub
		return shell;
	}
}
