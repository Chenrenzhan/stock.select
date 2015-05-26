package ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class CollectCondition extends Composite {

	private CLabel lblCollectCondition;
	private CLabel lblDelete;
	
	public CLabel getLblCollectCondition() {
		return lblCollectCondition;
	}

	public CLabel getLblDelete() {
		return lblDelete;
	}

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CollectCondition(Composite parent) {
		super(parent, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		lblCollectCondition = new CLabel(this, SWT.NONE);
		lblCollectCondition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCollectCondition.setBounds(10, 3, 258, 23);
		lblCollectCondition.setText("收藏条件1");
		
		lblDelete = new CLabel(this, SWT.NONE);
		lblDelete.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDelete.setBounds(288, 8, 17, 17);
		lblDelete.setText("删除");

	}
	
	public void setLblDeleteIcon(Image deleteIcon){
//		Image deleteIcon = new Image(Display.getDefault(), iconPath);
		lblDelete.setImage(deleteIcon);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
