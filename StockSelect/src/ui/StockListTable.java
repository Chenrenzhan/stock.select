package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class StockListTable extends Table {

	private static final String[] HEADER = new String[] { "序号", "股票代码", "股票简称",
		"涨跌幅(%)", "现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };
	private static final int[] HEADER_SIZE = new int[] { 55, 90, 75, 75, 70,
		100, 85, 75 };
	private static final String[] CHOICE_CONDITION = new String[] { "涨跌幅(%)",
		"现价(元)", "市盈率(pe)", "动态市盈率", "市净率" };
	
	
	private Composite composite;
	
	public StockListTable(Composite parent) {
		super(parent, SWT.BORDER | SWT.FULL_SELECTION);
		// TODO Auto-generated constructor stub
		
		this.composite = parent;
	}
	
	public void createTable(){
//		FormData fd_stockListComposite = new FormData();
//		fd_stockListComposite.bottom = new FormAttachment(0, 544);
//		fd_stockListComposite.right = new FormAttachment(0, 983);
//		fd_stockListComposite.top = new FormAttachment(0, 34);
//		fd_stockListComposite.left = new FormAttachment(0, 337);
//		setLayoutData(fd_stockListComposite);
//		setBackground(SWTResourceManager
//				.getColor(SWT.COLOR_WHITE));
//		setLayout(new FillLayout(SWT.HORIZONTAL));
//		System.out.println(getBounds());
//		 = new Table(stockListComposite, SWT.BORDER
//				| SWT.FULL_SELECTION);
		setHeaderVisible(true);
		setLinesVisible(true);
		for (int i = 0; i < HEADER.length; ++i) {
			TableColumn tblclmnNewColumn = new TableColumn(this,
					SWT.CENTER);
			tblclmnNewColumn.setWidth(HEADER_SIZE[i]);
			tblclmnNewColumn.setText(HEADER[i]);
			// tblclmnNewColumn.setMoveable(true);
			tblclmnNewColumn.setResizable(false);
			tblclmnNewColumn.setAlignment(SWT.CENTER);
			// tblclmnNewColumn.setFont(SWTResourceManager.getFont("Microsoft YaHei UI",
			// 10, SWT.BOLD));
		}

		TableCursor tableCursor = new TableCursor(this, SWT.BORDER);

		for(int i = 0; i < 30; ++i){
			TableItem tableItem = new TableItem(this, SWT.CENTER);
			tableItem.setText(new String[] 
					{ String.valueOf(i), "2", "3", "4", "5", "6", "7", "8" });
		}
	}

	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
