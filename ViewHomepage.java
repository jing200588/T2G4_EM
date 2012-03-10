import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;


public class ViewHomepage extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewHomepage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
	//	Image img = new Image(new Display(), "C:\\Users\\Lacryia\\Pictures\\b34wl.jpg");
	//	GC gc = new GC(img);
	//	gc.drawText("WELCOME TO E-MAN", 50, 50);
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblWelcomeToEman = new Label(composite, SWT.CENTER);
		lblWelcomeToEman.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		lblWelcomeToEman.setFont(SWTResourceManager.getFont("Tekton Pro", 32, SWT.NORMAL));
		lblWelcomeToEman.setText("\n\n\n\nWelcome to E-MAN!");
		/*Label lblWelcomeToEman = new Label(composite, SWT.CENTER);
		lblWelcomeToEman.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		lblWelcomeToEman.setFont(SWTResourceManager.getFont("Tekton Pro", 32, SWT.NORMAL));
		FormData fd_lblWelcomeToEman = new FormData();
		fd_lblWelcomeToEman.bottom = new FormAttachment(70, 0);
		fd_lblWelcomeToEman.top = new FormAttachment(50, 0);
		fd_lblWelcomeToEman.left = new FormAttachment(30, 0);
		fd_lblWelcomeToEman.right = new FormAttachment(70, 0);
		lblWelcomeToEman.setLayoutData(fd_lblWelcomeToEman);
		lblWelcomeToEman.setText("Welcome to E-MAN!");
		//lblWelcomeToEman.setBackground(SWT.COLOR_WIDGET_BACKGROUND);
		lblWelcomeToEman.pack();
		*/
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
