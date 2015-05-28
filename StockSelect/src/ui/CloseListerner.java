package ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CloseListerner implements MouseTrackListener, MouseListener {

	private static final Image CLOSE = 
			new Image(Display.getDefault(), "icons/close_btn.png");
	private static final Image CLOSE_HOVER = 
			new Image(Display.getDefault(), "icons/close_btn_active.png");
	
	private Shell shell;
	private Label btnClose;
	
	public CloseListerner(Shell shell){
		this.shell = shell;
		btnClose = null;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		shell.dispose();
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnClose(e);
		btnClose.setImage(CLOSE_HOVER);
	}

	@Override
	public void mouseExit(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnClose(e);
		btnClose.setImage(CLOSE);
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private Label getBtnClose(MouseEvent e){
		if(btnClose == null)
			btnClose = (Label) e.getSource();
		return btnClose;
	}

}
