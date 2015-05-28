package ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MinListerner implements MouseTrackListener, MouseListener {

	private static final Image Min = 
			new Image(Display.getDefault(), "icons/mini_btn.png");
	private static final Image Min_HOVER = 
			new Image(Display.getDefault(), "icons/mini_btn_active.png");
	
	private Shell shell;
	private Label btnMin;
	
	public MinListerner(Shell shell){
		this.shell = shell;
		btnMin = null;
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
		shell.setMinimized(true);
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnMin(e);
		btnMin.setImage(Min_HOVER);
	}

	@Override
	public void mouseExit(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnMin(e);
		btnMin.setImage(Min);
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private Label getBtnMin(MouseEvent e){
		if(btnMin == null)
			btnMin = (Label) e.getSource();
		return btnMin;
	}

}
