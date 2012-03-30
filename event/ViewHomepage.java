package event;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;


public class ViewHomepage extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewHomepage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_INFO_FOREGROUND));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblWelcomeToEman = new Label(composite, SWT.CENTER);
		lblWelcomeToEman.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\workspace\\E-MAN\\Images\\logo.jpg"));
		lblWelcomeToEman.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		lblWelcomeToEman.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	//	lblWelcomeToEman.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		lblWelcomeToEman.setFont(SWTResourceManager.getFont("Tekton Pro", 32, SWT.NORMAL));
		lblWelcomeToEman.setText("\n\n\n\nWelcome to E-MAN!");

		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
