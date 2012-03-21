import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;


public class ViewFaceBookAds extends Composite {
	private final StackLayout stackLayout = new StackLayout();
	private Eventitem current_event;
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Composite BigContent;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewFaceBookAds(Composite parent, int style, Eventitem input_ei) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});
		current_event = input_ei;

		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Form BudgetViewForm = formToolkit.createForm(this);
		BudgetViewForm.setBounds(0, 0, 700, 400);
		BudgetViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(BudgetViewForm);
		BudgetViewForm.setText("Budget Optimization");
		BudgetViewForm.getBody().setLayout(new FormLayout());

		/**********************************************************************************
		 * Overview Budget optimization composite 
		 **********************************************************************************/
		Composite mainComposite = new Composite(BudgetViewForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.top = new FormAttachment(50, -160);
		fd_mainComposite.bottom = new FormAttachment (50, 180);
		fd_mainComposite.left = new FormAttachment(50, -350);
		fd_mainComposite.right = new FormAttachment(50, 350);
		mainComposite.setLayoutData(fd_mainComposite);
		formToolkit.adapt(mainComposite);
		formToolkit.paintBordersFor(mainComposite);
		
		/**********************************************************************************
		 * Composite that will display the content 
		 **********************************************************************************/
		BigContent = new Composite(mainComposite, SWT.NONE);
		BigContent.setBounds(10, 102, 680, 238);
		formToolkit.adapt(BigContent);
		formToolkit.paintBordersFor(BigContent);
		BigContent.setLayout(stackLayout);
		
		FacebookAds fb = new FacebookAds(BigContent);
		stackLayout.topControl = fb;
		BigContent.layout();

	}

}
