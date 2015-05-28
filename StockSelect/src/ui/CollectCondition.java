package ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class CollectCondition extends Composite {

	
	private static final Image delete_icon_hover = 
			new Image(Display.getDefault(), "icons/close_btn_active_small.png");
	private static final Image delete_icon = 
			new Image(Display.getDefault(), "icons/close_btn_small.png");
	
	private CLabel lblCollectCondition;
	private Label lblDelete;
	private int index;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CollectCondition(Composite parent, int index) {
		
		super(parent, SWT.BORDER);
		this.index = index;
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCollectCondition = new CLabel(this, SWT.NONE);
		lblCollectCondition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCollectCondition.setBounds(10, 2, 258, 23);
		lblCollectCondition.setText("收藏条件1");
		
		lblDelete = new Label(this, SWT.NONE);
		lblDelete.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDelete.setBounds(288, 3, 20, 20);
//		lblDelete.setText("删除");
		lblDelete.setImage(delete_icon);
		lblDelete.setData("index", index);
		lblDelete.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				// TODO Auto-generated method stub
				lblDelete.setImage(delete_icon);
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				// TODO Auto-generated method stub
				lblDelete.setImage(delete_icon_hover);
			}
		});

	}
	
	public void setLblDeleteIcon(Image deleteIcon){
//		Image deleteIcon = new Image(Display.getDefault(), iconPath);
		lblDelete.setImage(deleteIcon);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
//		lblDelete.setData("index", index);
	}

	public CLabel getLblCollectCondition() {
		return lblCollectCondition;
		
	}

	public Label getLblDelete() {
		return lblDelete;
	}
}
