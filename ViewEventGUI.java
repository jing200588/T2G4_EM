import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ViewEventGUI extends Composite {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventGUI(Composite parent, int style, Eventitem curevent) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledForm scrldfrmNewScrolledform = formToolkit.createScrolledForm(this);
		
		scrldfrmNewScrolledform.setMinHeight(415);
		scrldfrmNewScrolledform.setAlwaysShowScrollBars(true);
//		scrldfrmNewScrolledform.reflow(true);
		scrldfrmNewScrolledform.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(scrldfrmNewScrolledform);
		scrldfrmNewScrolledform.setText("View Event:");
		scrldfrmNewScrolledform.getBody().setLayout(new FormLayout());
		
		Composite EparticularsComp = new Composite(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		FormData fd_EparticularsComp = new FormData();
		fd_EparticularsComp.right = new FormAttachment(80, 0);
		fd_EparticularsComp.top = new FormAttachment(0, 85);
		fd_EparticularsComp.left = new FormAttachment(20, 0);
		EparticularsComp.setLayoutData(fd_EparticularsComp);
		formToolkit.adapt(EparticularsComp);
		formToolkit.paintBordersFor(EparticularsComp);
		EparticularsComp.setLayout(new GridLayout(3, false));
		
		Label Eparticulars = new Label(EparticularsComp, SWT.NONE);
		Eparticulars.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Eparticulars.setFont(SWTResourceManager.getFont("Courier New Baltic", 16, SWT.BOLD));
		formToolkit.adapt(Eparticulars, true, true);
		Eparticulars.setText("Event Particulars");
		new Label(EparticularsComp, SWT.NONE);
		
		Button Epartedit = new Button(EparticularsComp, SWT.NONE);
		GridData gd_Epartedit = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_Epartedit.widthHint = 81;
		Epartedit.setLayoutData(gd_Epartedit);
		formToolkit.adapt(Epartedit, true, true);
		Epartedit.setText("Edit");
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);
		
		Label Enamelabel = new Label(EparticularsComp, SWT.NONE);
		Enamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enamelabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		formToolkit.adapt(Enamelabel, true, true);
		Enamelabel.setText("Event Name:");
		
		Label Ename = new Label(EparticularsComp, SWT.NONE);
		Ename.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		Ename.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		formToolkit.adapt(Ename, true, true);
		Ename.setText(curevent.getName());
		
		Label Startdntlabel = new Label(EparticularsComp, SWT.NONE);
		Startdntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Startdntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		formToolkit.adapt(Startdntlabel, true, true);
		Startdntlabel.setText("Start Date and Time:");
		
		Label Startdate = new Label(EparticularsComp, SWT.NONE);
		Startdate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Startdate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		formToolkit.adapt(Startdate, true, true);
		Startdate.setText(curevent.getStartDate());
		
		Label Starttime = new Label(EparticularsComp, SWT.NONE);
		Starttime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Starttime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		formToolkit.adapt(Starttime, true, true);
		Starttime.setText(curevent.getStartTime()+"HRS");
		
		Label Enddntlabel = new Label(EparticularsComp, SWT.NONE);
		Enddntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enddntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		formToolkit.adapt(Enddntlabel, true, true);
		Enddntlabel.setText("End Date and Time:");
		
		Label Enddate = new Label(EparticularsComp, SWT.NONE);
		Enddate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Enddate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		formToolkit.adapt(Enddate, true, true);
		Enddate.setText(curevent.getEndDate());
		
		Label Endtime = new Label(EparticularsComp, SWT.NONE);
		Endtime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Endtime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		formToolkit.adapt(Endtime, true, true);
		Endtime.setText(curevent.getEndTime()+"HRS");
		
		Label Edescriptionlabel = new Label(EparticularsComp, SWT.NONE);
		Edescriptionlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Edescriptionlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		formToolkit.adapt(Edescriptionlabel, true, true);
		Edescriptionlabel.setText("Description:");
		
		CLabel Edescription = new CLabel(EparticularsComp, SWT.NONE);
		Edescription.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		formToolkit.adapt(Edescription);
		formToolkit.paintBordersFor(Edescription);
		Edescription.setText("New Label");
		
		Label divider1 = new Label(scrldfrmNewScrolledform.getBody(), SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider1 = new FormData();
		fd_divider1.top = new FormAttachment(EparticularsComp, 53);
		fd_divider1.bottom = new FormAttachment(EparticularsComp, 55, SWT.BOTTOM);
		fd_divider1.left = new FormAttachment(5, 0);
		fd_divider1.right = new FormAttachment(95, 0);
		divider1.setLayoutData(fd_divider1);
		formToolkit.adapt(divider1, true, true);
		
		Composite composite = new Composite(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(80, 0);
		fd_composite.left = new FormAttachment(20, 0);
		fd_composite.top = new FormAttachment(divider1, 30);
		composite.setLayoutData(fd_composite);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		
		Label EProgramFlow = new Label(composite, SWT.NONE);
		EProgramFlow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		EProgramFlow.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		formToolkit.adapt(EProgramFlow, true, true);
		EProgramFlow.setText("Event Program Flow");
		new Label(composite, SWT.NONE);
		
		Button Eprogflowedit = new Button(composite, SWT.NONE);
		Eprogflowedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		GridData gd_Eprogflowedit = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_Eprogflowedit.widthHint = 85;
		Eprogflowedit.setLayoutData(gd_Eprogflowedit);
		formToolkit.adapt(Eprogflowedit, true, true);
		Eprogflowedit.setText("Edit");
		
		Label Divider2 = new Label(scrldfrmNewScrolledform.getBody(), SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_Divider2 = new FormData();
		fd_Divider2.top = new FormAttachment(composite, 30);
		fd_Divider2.left = new FormAttachment(5, 0);
		fd_Divider2.right = new FormAttachment(95, 0);
		Divider2.setLayoutData(fd_Divider2);
		formToolkit.adapt(Divider2, true, true);
	
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
