package ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

public class ConditionNameDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private String name;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConditionNameDialog(Shell parent) {
		super(parent, SWT.CLOSE);
		setText("收藏条件名字");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CompositeMoveListener cml = new CompositeMoveListener(shell);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		composite.addListener(SWT.MouseDown , cml);
		composite.addListener(SWT.MouseMove, cml);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(10, 10, 424, 251);
		composite_1.addListener(SWT.MouseDown , cml);
		composite_1.addListener(SWT.MouseMove, cml);
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		label.setBounds(71, 59, 86, 29);
		label.setText("收藏名字：");
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(177, 59, 174, 23);
		
		CLabel lblOK = new CLabel(composite_1, SWT.SHADOW_OUT | SWT.SHADOW_NONE | SWT.CENTER);
		lblOK.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		lblOK.setBounds(105, 140, 67, 35);
		lblOK.setText("确定");
		lblOK.addMouseListener(new MouseListenerAdapt() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(text.getText() == ""){
					MessageBox messageBox =  
							new MessageBox(shell, 
									SWT.OK| SWT.CANCEL| SWT.ICON_WARNING);   
					messageBox.setMessage("收藏条件名不能为空！");   
					messageBox.open(); 
					return ;
				}
				name = text.getText();
				shell.dispose();
			}
		});
		
		
		CLabel lblCancel = new CLabel(composite_1, SWT.SHADOW_OUT | SWT.CENTER);
		lblCancel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		lblCancel.setBounds(259, 140, 67, 35);
		lblCancel.setText("取消");
		lblCancel.addMouseListener(new MouseListenerAdapt() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
//				name = null;
				shell.dispose();
			}
		});
		

	}

	public String getName() {
		return name;
	}
}
