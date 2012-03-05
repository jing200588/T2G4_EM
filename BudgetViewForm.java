import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;


public class BudgetViewForm extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Button btnStepInputDetails;
	private Button btnStepSelectCompulsory;
	private Button btnStepConfirmResult;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BudgetViewForm(Composite parent, int style,int id) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FormLayout());
		
		
		Label lblBudgetOptimization = new Label(this, SWT.NONE);
		FormData fd_lblBudgetOptimization = new FormData();
		fd_lblBudgetOptimization.top = new FormAttachment(0, 10);
		fd_lblBudgetOptimization.left = new FormAttachment(0, 10);
		lblBudgetOptimization.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		lblBudgetOptimization.setLayoutData(fd_lblBudgetOptimization);
		toolkit.adapt(lblBudgetOptimization, true, true);
		lblBudgetOptimization.setText("Budget Optimization");
		
		Composite btnComposite = new Composite(this, SWT.NONE);
		btnComposite.setLayout(new FormLayout());
		FormData fd_btnComposite = new FormData();
		fd_btnComposite.right = new FormAttachment(5, 620);
		fd_btnComposite.bottom = new FormAttachment(lblBudgetOptimization, 60, SWT.BOTTOM);
		fd_btnComposite.top = new FormAttachment(lblBudgetOptimization, 6);
		fd_btnComposite.left = new FormAttachment(5, 20);
		btnComposite.setLayoutData(fd_btnComposite);
		toolkit.adapt(btnComposite);
		toolkit.paintBordersFor(btnComposite);
		
		btnStepInputDetails = new Button(btnComposite, SWT.NONE);
		FormData fd_btnStepInputDetails = new FormData();
		fd_btnStepInputDetails.right = new FormAttachment(0, 170);
		fd_btnStepInputDetails.bottom = new FormAttachment(0, 35);
		fd_btnStepInputDetails.top = new FormAttachment(0, 10);
		fd_btnStepInputDetails.left = new FormAttachment(0, 10);
		btnStepInputDetails.setLayoutData(fd_btnStepInputDetails);
		toolkit.adapt(btnStepInputDetails, true, true);
		btnStepInputDetails.setText("Step1: Input Details");
		
		btnStepSelectCompulsory = new Button(btnComposite, SWT.NONE);
		FormData fd_btnStepSelectCompulsory = new FormData();
		fd_btnStepSelectCompulsory.right = new FormAttachment(btnStepInputDetails, 210, SWT.RIGHT);
		fd_btnStepSelectCompulsory.left = new FormAttachment(btnStepInputDetails, 50);
		fd_btnStepSelectCompulsory.bottom = new FormAttachment(0, 35);
		fd_btnStepSelectCompulsory.top = new FormAttachment(0, 10);
		btnStepSelectCompulsory.setLayoutData(fd_btnStepSelectCompulsory);
		toolkit.adapt(btnStepSelectCompulsory, true, true);
		btnStepSelectCompulsory.setText("Step2: Select Compulsory");
		
		btnStepConfirmResult = new Button(btnComposite, SWT.NONE);
		FormData fd_btnStepConfirmResult = new FormData();
		fd_btnStepConfirmResult.right = new FormAttachment(btnStepSelectCompulsory, 210, SWT.RIGHT);
		fd_btnStepConfirmResult.left = new FormAttachment(btnStepSelectCompulsory, 50);
		fd_btnStepConfirmResult.top = new FormAttachment(0, 10);
		fd_btnStepConfirmResult.bottom = new FormAttachment(0, 35);
		btnStepConfirmResult.setLayoutData(fd_btnStepConfirmResult);
		toolkit.adapt(btnStepConfirmResult, true, true);
		btnStepConfirmResult.setText("Step3: Confirm Result");
	
		
		

	}
}
