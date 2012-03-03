import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class BudgetViewStep1 extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Label lblHiiiiiiiiStep;
	private Composite composite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BudgetViewStep1(final Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		composite = new Composite(this, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		composite.setBounds(32, 16, 408, 284);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		lblHiiiiiiiiStep = new Label(composite, SWT.NONE);
		lblHiiiiiiiiStep.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblHiiiiiiiiStep.setBounds(35, 71, 55, 15);
		toolkit.adapt(lblHiiiiiiiiStep, true, true);
		lblHiiiiiiiiStep.setText("Hiiiiiiii Step 1");

	}
}
