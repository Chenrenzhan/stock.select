package ui;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class CompositeMove{
	
	private Vector<Composite> compVec;
	private Shell shell;
	public CompositeMove(Shell shell){
		compVec = new Vector<Composite>();
		this.shell = shell;
	}
	
	public void add(Composite comp){
		compVec.add(comp);
		setCompositeMove(comp);
	}
	
	public void setCompositeMove(Composite composite){
		CompositeMoveListener listener = new CompositeMoveListener(shell);
		composite.addListener(SWT.MouseDown, listener);
		composite.addListener(SWT.MouseMove, listener);
	}
}
