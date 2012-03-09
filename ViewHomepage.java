import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;


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
	//	Display display = new  Display();
		Button button = new Button(composite, SWT.NONE);
		button.setBounds(66, 134, 75, 25);
		button.setText("New Button");
	//	button.setImage(Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
