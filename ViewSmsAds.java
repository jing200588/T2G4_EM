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
	private Eventitem currentEvent;
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Text txtToInputBox;
	private Composite compSmsMain;
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
		currentEvent = input_ei;

		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Form ViewSmsAdsForm = formToolkit.createForm(this);
		ViewSmsAdsForm.setBounds(0, 0, 700, 400);
		ViewSmsAdsForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(ViewSmsAdsForm);
		ViewSmsAdsForm.setText("SMS Advertising");

		/**********************************************************************************
		 *Main screen composite 
		 **********************************************************************************/
		ViewSmsAdsForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		compSmsMain = new Composite(ViewSmsAdsForm.getBody(), SWT.NONE);
		compSmsMain.setLayout(new FormLayout());
		formToolkit.adapt(compSmsMain);
		formToolkit.paintBordersFor(compSmsMain);

		lblTo = new Label(compSmsMain, SWT.NONE);
		FormData fd_lblTo = new FormData();
		fd_lblTo.right = new FormAttachment(0, 65);
		fd_lblTo.top = new FormAttachment(5, 2);
		fd_lblTo.left = new FormAttachment(0, 10);
		lblTo.setLayoutData(fd_lblTo);
		formToolkit.adapt(lblTo, true, true);
		lblTo.setText("To:");

		txtToInputBox = new Text(compSmsMain, SWT.BORDER);
		FormData fd_txtToInputBox = new FormData();
		fd_txtToInputBox.right = new FormAttachment(95);
		fd_txtToInputBox.top = new FormAttachment(5);
		fd_txtToInputBox.left = new FormAttachment(0, 71);
		txtToInputBox.setLayoutData(fd_txtToInputBox);
		formToolkit.adapt(txtToInputBox, true, true);

		lblMessage = new Label(compSmsMain, SWT.NONE);
		FormData fd_lblMessage = new FormData();

		fd_lblMessage.right = new FormAttachment(txtMessageInputBox, 65);
		fd_lblMessage.left = new FormAttachment(0, 10);
		lblMessage.setLayoutData(fd_lblMessage);
		formToolkit.adapt(lblMessage, true, true);
		lblMessage.setText("Message:");

		txtMessageInputBox = new Text(compSmsMain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		FormData fd_txtMessageInputBox = new FormData();
		fd_txtMessageInputBox.bottom = new FormAttachment(90);
		fd_txtMessageInputBox.right = new FormAttachment(95);
		fd_txtMessageInputBox.left = new FormAttachment(0, 71);
		txtMessageInputBox.setLayoutData(fd_txtMessageInputBox);
		formToolkit.adapt(txtMessageInputBox, true, true);
		String start = currentEvent.getStartDateTime().getDateRepresentation() + " - " + currentEvent.getStartDateTime().getTimeRepresentation();
		String end = currentEvent.getEndDateTime().getDateRepresentation() + " - " + currentEvent.getEndDateTime().getTimeRepresentation();
		txtMessageInputBox.setText("Upcoming event: " + currentEvent.getName()+".\nStart date: " + start + ".\nEnd date: " + end +".");
		btnSend = new Button(compSmsMain, SWT.NONE);
		FormData fd_btnSend = new FormData();
		fd_btnSend.right = new FormAttachment(0, 490);
		fd_btnSend.top = new FormAttachment(0, 282);
		fd_btnSend.left = new FormAttachment(0, 415);
		btnSend.setLayoutData(fd_btnSend);
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
		formToolkit.adapt(btnSend, true, true);
		btnSend.setText("Send");
		
		Button btnImportNumber = new Button(compSmsMain, SWT.NONE);
		fd_lblMessage.top = new FormAttachment(btnImportNumber, 10);
		fd_txtMessageInputBox.top = new FormAttachment(btnImportNumber, 10);
		FormData fd_btnImportNumber = new FormData();
		fd_btnImportNumber.width = 110;
		fd_btnImportNumber.top = new FormAttachment(txtToInputBox, 10);
		btnImportNumber.setLayoutData(fd_btnImportNumber);
		btnImportNumber.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);

				ImportCSV(fsd.open());
			}
		});
		formToolkit.adapt(btnImportNumber, true, true);
		btnImportNumber.setText("Import Number");
		
		Button btnNotifyParticipants = new Button(compSmsMain, SWT.NONE);
		fd_btnImportNumber.right = new FormAttachment(btnNotifyParticipants, -10);
		FormData fd_btnNotifyParticipants = new FormData();
		fd_btnNotifyParticipants.width = 110;
		fd_btnNotifyParticipants.top = new FormAttachment(txtToInputBox, 10);
		fd_btnNotifyParticipants.right = new FormAttachment(95);
		btnNotifyParticipants.setLayoutData(fd_btnNotifyParticipants);
		btnNotifyParticipants.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String participantsContact = "";
				for(int i=0 ; i< currentEvent.getParticipantList().size(); i++) {
					participantsContact += currentEvent.getParticipantList().get(i).getContact() + " ";
				}
				
				System.out.println("Here " + participantsContact);
				txtToInputBox.setText(participantsContact);
			}
		});
		formToolkit.adapt(btnNotifyParticipants, true, true);
		btnNotifyParticipants.setText("Notify Participants");


	}
	
	public void ImportCSV (String filepath) {
		errormessageDialog errordiag;
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
