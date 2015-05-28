package ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RefreshListerner implements MouseTrackListener {

	private static final Image REFRESH = 
			new Image(Display.getDefault(), "icons/refresh.png");
	private static final Image REFRESH_HOVER = 
			new Image(Display.getDefault(), "icons/refresh_active.png");
	
	private Shell shell;
	private Label btnRefresh;
	
	public RefreshListerner(){
//		this.shell = shell;
		btnRefresh = null;
	}
	
//	@Override
//	public void mouseDoubleClick(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseDown(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseUp(MouseEvent arg0) {
//		// TODO Auto-generated method stub
////		System.out.println("mouse up");
////		shell.dispose();
//	}

	@Override
	public void mouseEnter(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnRefresh(e);
		btnRefresh.setImage(REFRESH_HOVER);
	}

	@Override
	public void mouseExit(MouseEvent e) {
		// TODO Auto-generated method stub
		getBtnRefresh(e);
		btnRefresh.setImage(REFRESH);
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private Label getBtnRefresh(MouseEvent e){
		if(btnRefresh == null)
			btnRefresh = (Label) e.getSource();
		return btnRefresh;
	}

}
