package ui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CursorListener implements MouseTrackListener {
	private Composite composite;
	private Cursor hand;
	private Cursor arrow;

	public CursorListener(Composite composite) {
		this.composite = composite;
		this.hand = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
		this.arrow = new Cursor(Display.getDefault(), SWT.CURSOR_ARROW);
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExit(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arrow == null){
			arrow = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
		}
		if(hand != null){
			hand.isDisposed();
		}
		composite.setCursor(arrow);
	}

	@Override
	public void mouseEnter(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(hand == null){
			hand = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
		}
		if(arrow != null){
			arrow.isDisposed();
		}
		composite.setCursor(hand);
	}

}