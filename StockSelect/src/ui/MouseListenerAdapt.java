package ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public abstract class MouseListenerAdapt implements MouseListener {

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0){
		
	}

	@Override
	abstract public void mouseUp(MouseEvent arg0);

}
