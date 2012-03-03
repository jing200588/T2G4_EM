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
import org.eclipse.swt.graphics.Rectangle;


public class BudgetViewOverall extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite inner;
	private Composite inner2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BudgetViewOverall(final Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		Composite container = new Composite(parent, SWT.NONE);

		container.setBounds(0, 0, 670, 318);
		Composite inner1 = new Composite(container, SWT.NONE);
		BudgetViewStep1 bs1 = new BudgetViewStep1(inner1, SWT.NONE);
		toolkit.adapt(inner1);
		toolkit.paintBordersFor(inner1);
	}
}
