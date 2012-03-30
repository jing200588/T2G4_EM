import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;


public class ViewEmailAds extends Composite {
	private Eventitem currentEvent;
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Text txtToInputBox;
	private Text txtSubjectInputBox;
	private Composite compMain;
	private Label lblTo;
	private Label lblSubject;
	private Label lblMessage;
	private Text txtMessageInputBox;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEmailAds(Composite parent, int style, Eventitem input_ei) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});
		currentEvent = input_ei;


		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Form formViewEmailAds = formToolkit.createForm(this);
		formViewEmailAds.setBounds(0, 0, 700, 400);
		formViewEmailAds.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(formViewEmailAds);
		formViewEmailAds.setText("Email Advertising");
		formViewEmailAds.getBody().setLayout(new FormLayout());
		
		/**********************************************************************************
		 * Overview Budget optimization composite 
		 **********************************************************************************/
		compMain = new Composite(formViewEmailAds.getBody(), SWT.NONE);
		FormData fd_compMain = new FormData();
		fd_compMain.top = new FormAttachment(50, -160);
		fd_compMain.bottom = new FormAttachment (50, 180);
		fd_compMain.left = new FormAttachment(50, -350);
		fd_compMain.right = new FormAttachment(50, 350);
		compMain.setLayoutData(fd_compMain);
		formToolkit.adapt(compMain);
		formToolkit.paintBordersFor(compMain);
		
		lblTo = new Label(compMain, SWT.NONE);
		lblTo.setBounds(10, 56, 55, 15);
		formToolkit.adapt(lblTo, true, true);
		lblTo.setText("To:");
		
		lblSubject = new Label(compMain, SWT.NONE);
		lblSubject.setBounds(10, 94, 55, 15);
		formToolkit.adapt(lblSubject, true, true);
		lblSubject.setText("Subject:");
		
		lblMessage = new Label(compMain, SWT.NONE);
		lblMessage.setBounds(10, 138, 55, 15);
		formToolkit.adapt(lblMessage, true, true);
		lblMessage.setText("Message:");
		
		txtToInputBox = new Text(compMain, SWT.BORDER);
		txtToInputBox.setBounds(71, 53, 419, 21);
		formToolkit.adapt(txtToInputBox, true, true);
		
		txtSubjectInputBox = new Text(compMain, SWT.BORDER);
		txtSubjectInputBox.setBounds(71, 94, 419, 21);
		formToolkit.adapt(txtSubjectInputBox, true, true);
		
		txtMessageInputBox = new Text(compMain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtMessageInputBox.setBounds(71, 138, 419, 144);
		formToolkit.adapt(txtMessageInputBox, true, true);
		
		Button btnSend = new Button(compMain, SWT.NONE);
		btnSend.setBounds(415, 305, 75, 25);
		formToolkit.adapt(btnSend, true, true);
		btnSend.setText("Send");


	}
}
