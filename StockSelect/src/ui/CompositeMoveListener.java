package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class CompositeMoveListener implements Listener{

	private int startX;
	private int startY;
	
	private Composite composite;
//	private Shell shell;
	
	public CompositeMoveListener(Composite composite){
		this.composite = composite;
//		this.shell = shell;
	}
	
	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		
//		Composite composite = e.getSource();
		if (e.type == SWT.MouseDown && e.button == 1) {
            startX = e.x;
            startY = e.y;
        }
        if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
            Point p = composite.toDisplay(e.x, e.y);
            p.x -= startX;
            p.y -= startY;
            composite.setLocation(p);
        }
    }
	
}
