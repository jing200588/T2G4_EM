import gnu.io.CommPortIdentifier;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import org.eclipse.swt.widgets.FileDialog;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import au.com.bytecode.opencsv.CSVReader;


public class ViewSmsAds extends Composite {
	private Eventitem current_event;
	protected errormessageDialog errordiag;
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Text txtToInputBox;
	private Composite mainComposite;
	private Label lblTo;
	private Label lblMessage;
	private Text txtMessageInputBox;
	private Button btnSend;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewSmsAds(Composite parent, int style, Eventitem input_ei) {
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
		Form ViewSmsAdsForm = formToolkit.createForm(this);
		ViewSmsAdsForm.setBounds(0, 0, 700, 400);
		ViewSmsAdsForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(ViewSmsAdsForm);
		ViewSmsAdsForm.setText("SMS Advertising");
		ViewSmsAdsForm.getBody().setLayout(new FormLayout());

		/**********************************************************************************
		 *Main screen composite 
		 **********************************************************************************/
		mainComposite = new Composite(ViewSmsAdsForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.top = new FormAttachment(50, -160);
		fd_mainComposite.bottom = new FormAttachment (50, 180);
		fd_mainComposite.left = new FormAttachment(50, -350);
		fd_mainComposite.right = new FormAttachment(50, 350);
		mainComposite.setLayoutData(fd_mainComposite);
		formToolkit.adapt(mainComposite);
		formToolkit.paintBordersFor(mainComposite);

		lblTo = new Label(mainComposite, SWT.NONE);
		lblTo.setBounds(10, 56, 55, 15);
		formToolkit.adapt(lblTo, true, true);
		lblTo.setText("To:");

		txtToInputBox = new Text(mainComposite, SWT.BORDER);
		txtToInputBox.setBounds(71, 53, 419, 21);
		formToolkit.adapt(txtToInputBox, true, true);

		lblMessage = new Label(mainComposite, SWT.NONE);
		lblMessage.setBounds(10, 118, 55, 15);
		formToolkit.adapt(lblMessage, true, true);
		lblMessage.setText("Message:");

		txtMessageInputBox = new Text(mainComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtMessageInputBox.setBounds(71, 115, 419, 144);
		formToolkit.adapt(txtMessageInputBox, true, true);
		String start = current_event.getStartDateTime().getDateRepresentation() + " - " + current_event.getStartDateTime().getTimeRepresentation();
		String end = current_event.getEndDateTime().getDateRepresentation() + " - " + current_event.getEndDateTime().getTimeRepresentation();
		txtMessageInputBox.setText("Upcoming event: " + current_event.getName()+".\nStart date: " + start + ".\nEnd date: " + end +".");
		btnSend = new Button(mainComposite, SWT.NONE);
		btnSend.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				Enumeration ports = CommPortIdentifier.getPortIdentifiers();  

				String defaultPort="";
				while(ports.hasMoreElements()){  
					CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
					System.out.println(port.getName());
					defaultPort = port.getName();
				}

				String[] input_num = txtToInputBox.getText().split(" ");
				System.out.println(input_num.length);
				String message = txtMessageInputBox.getText();
								
				/* to be use IFF required!
				try {	
					
					SerialToGsm stg = new SerialToGsm(defaultPort);
					for(int i=0; i<input_num.length; i++)
						stg.sendSms(input_num[i],message);
					
					ViewMain.ReturnView();
				} catch (Exception ex) {
					errordiag = new errormessageDialog(new Shell(), "Check your GSM modem.");
					errordiag.open();
				}
				*/

			}
		});
		btnSend.setBounds(415, 282, 75, 25);
		formToolkit.adapt(btnSend, true, true);
		btnSend.setText("Send");
		
		Button btnImportNumber = new Button(mainComposite, SWT.NONE);
		btnImportNumber.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);

				ImportCSV(fsd.open());
			}
		});
		btnImportNumber.setBounds(368, 84, 122, 25);
		formToolkit.adapt(btnImportNumber, true, true);
		btnImportNumber.setText("Import Number");


	}
	
	public void ImportCSV (String filepath) {
		try {
			String importedText = "";
			CSVReader reader = new CSVReader(new FileReader(filepath));

			List<String[]> myEntries = reader.readAll();

			//populating entries.
			for (int i=0; i<myEntries.size(); i++) {
				for(int j=0; j<myEntries.get(i).length; j++) {
					importedText += "+" + myEntries.get(i)[j] + " ";
				}
			}		
			txtToInputBox.setText(importedText);

		} catch (FileNotFoundException e) {
			errordiag = new errormessageDialog(new Shell(), "File not found.");
			errordiag.open();
		} catch (IOException e) {
			errordiag = new errormessageDialog(new Shell(), "Incorrect file format.");
			errordiag.open();
		}

	}
}
