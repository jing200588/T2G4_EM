package event;

import dialog.*;
import au.com.bytecode.opencsv.CSVWriter;
import budget.*;
import venue.*;
import participant.*;
import program.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import com.ibm.icu.text.Collator;


public class ViewEvent extends Composite {
	private static Table BudgetResult, VenueResult, FlowResult, ParticipantResult;
	private static boolean BudgetFlag, VenueFlag, FlowFlag, ParticipantFlag;
	private static Eventitem cevent;
	private static Composite BudgetComp, BookVenueComp, EventFlowComp, ParticipantListComp;
	private static Label Edescription, StartDate, StartTime, EndDate, EndTime, Ename;
	private static ScrolledComposite sc1;
	private static Composite MainComp;
	private static int budgetTableSelectedIndex;
	private static ControllerBudget bc;
	protected LoginEmailDialog loginDiag; 
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEvent(Composite parent, int style, Eventitem curevent) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		sc1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc1.setExpandHorizontal(true);
		sc1.setExpandVertical(true);

		MainComp = new Composite(sc1, SWT.NONE);
		MainComp.setLayout(new FormLayout());
		BudgetFlag = false;
		VenueFlag = false;
		cevent = curevent;

		//View Event title label
		Label lblViewEvent = new Label(MainComp, SWT.NONE);
		lblViewEvent.setText("View Event:");
		lblViewEvent.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		FormData fd_lblViewTitle = new FormData();
		fd_lblViewTitle.top = new FormAttachment(0, 10);
		fd_lblViewTitle.left = new FormAttachment(0, 10);
		lblViewEvent.setLayoutData(fd_lblViewTitle);

		/**********************************************************************************************
		 * 
		 * EVENT PARTICULARS COMPOSITE SECTION
		 * 
		 *********************************************************************************************/
		Composite eventParticularsComp = new Composite(MainComp, SWT.NONE);
		FormData fd_eventParticularsComp = new FormData();
		fd_eventParticularsComp.right = new FormAttachment(90);
		fd_eventParticularsComp.top = new FormAttachment(lblViewEvent, 30);
		fd_eventParticularsComp.left = new FormAttachment(10);
		eventParticularsComp.setLayoutData(fd_eventParticularsComp);
		eventParticularsComp.setLayout(new GridLayout(4, false));

		Label lblEventParticulars = new Label(eventParticularsComp, SWT.NONE);
		lblEventParticulars.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblEventParticulars.setText("Event Particulars");
		new Label(eventParticularsComp, SWT.NONE);

		/**********************************************************************************************
		 * 
		 * EDIT EVENT PARTICULARS BUTTON
		 * 
		 *********************************************************************************************/
		new Label(eventParticularsComp, SWT.NONE);
		Button btnEventParticulars = new Button(eventParticularsComp, SWT.NONE);

		btnEventParticulars.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EventParticulars(cevent);
			}
		});
		GridData gd_btnEventParticulars = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnEventParticulars.widthHint = 85;
		btnEventParticulars.setLayoutData(gd_btnEventParticulars);
		btnEventParticulars.setText("Edit");
		new Label(eventParticularsComp, SWT.NONE);
		new Label(eventParticularsComp, SWT.NONE);
		new Label(eventParticularsComp, SWT.NONE);
		new Label(eventParticularsComp, SWT.NONE);

		Label lblEname = new Label(eventParticularsComp, SWT.NONE);
		lblEname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEname.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblEname.setText("Event Name:");

		Ename = new Label(eventParticularsComp, SWT.WRAP);
		Ename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		Ename.setFont(SWTResourceManager.getFont("Century Gothic", 12, SWT.NORMAL));
		Ename.setText(cevent.getName());

		Label lblStartDnT = new Label(eventParticularsComp, SWT.NONE);
		lblStartDnT.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDnT.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblStartDnT.setText("Start Date:");

		StartDate = new Label(eventParticularsComp, SWT.NONE);
		StartDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		StartDate.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		StartDate.setText(cevent.getStartDateTime().getDateRepresentation());
		
		Label lblStartTime = new Label(eventParticularsComp, SWT.NONE);
		lblStartTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		lblStartTime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblStartTime.setText("Start Time:");

		StartTime = new Label(eventParticularsComp, SWT.NONE);
		StartTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		StartTime.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		StartTime.setText(cevent.getStartDateTime().getTimeRepresentation()+"HRS");

		Label lblEndDnT = new Label(eventParticularsComp, SWT.NONE);
		lblEndDnT.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDnT.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblEndDnT.setText("End Date:");

		EndDate = new Label(eventParticularsComp, SWT.NONE);
		EndDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		EndDate.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		EndDate.setText(cevent.getEndDateTime().getDateRepresentation());
		
		Label lblEndTime = new Label(eventParticularsComp, SWT.NONE);
		lblEndTime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblEndTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblEndTime.setText("End Time:");

		EndTime = new Label(eventParticularsComp, SWT.NONE);
		EndTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		EndTime.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		EndTime.setText(cevent.getEndDateTime().getTimeRepresentation()+"HRS");

		Label lblEdescription = new Label(eventParticularsComp, SWT.NONE);
		lblEdescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEdescription.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblEdescription.setText("Description:");

		Edescription = new Label(eventParticularsComp, SWT.WRAP | SWT.SHADOW_NONE);
		Edescription.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_Edescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_Edescription.widthHint = 90;
		Edescription.setLayoutData(gd_Edescription);
		Edescription.setText(cevent.getDescription());
		/**********************************************************************************************
		 * END OF EVENT PARTICULARS SECTION
		 *********************************************************************************************/

		//Divider1
		Label divider1 = createDivider(MainComp, eventParticularsComp);

		/**********************************************************************************************
		 * 
		 * BOOK VENUE SECTION
		 * 
		 *********************************************************************************************/
		BookVenueComp = new Composite(MainComp, SWT.NONE);
		BookVenueComp.setLayout(new GridLayout(3, false));
		FormData fd_BookVenueComp = new FormData();
		fd_BookVenueComp.right = new FormAttachment(90);
		fd_BookVenueComp.left = new FormAttachment(10);
		fd_BookVenueComp.top = new FormAttachment(divider1, 30);
		BookVenueComp.setLayoutData(fd_BookVenueComp);

		Label lblBookVenue = new Label(BookVenueComp, SWT.NONE);
		lblBookVenue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblBookVenue.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblBookVenue.setText("Book Venue");

		Label dummy = new Label(BookVenueComp, SWT.NONE);
		GridData gd_dummy = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dummy.widthHint = 27;
		dummy.setLayoutData(gd_dummy);

		/**********************************************************************************************
		 * 
		 * BOOK VENUE BUTTON
		 * 
		 *********************************************************************************************/
		Button btnBookVenue = new Button(BookVenueComp, SWT.NONE);
		btnBookVenue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.BookVenue();
			}
		});
		GridData gd_btnBookVenue = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnBookVenue.widthHint = 85;
		btnBookVenue.setLayoutData(gd_btnBookVenue);
		btnBookVenue.setText("Edit");
		/**********************************************************************************************
		 * END OF BOOK VENUE
		 *********************************************************************************************/

		//Divider 2
		Label divider2 = createDivider(MainComp, BookVenueComp);

		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET SECTION
		 * 
		 *********************************************************************************************/
		BudgetComp = new Composite(MainComp, SWT.NONE);
		BudgetComp.setLayout(new GridLayout(3, false));
		FormData fd_BudgetComp = new FormData();
		fd_BudgetComp.right = new FormAttachment(90);
		fd_BudgetComp.left = new FormAttachment(10);
		fd_BudgetComp.top = new FormAttachment(divider2, 30);
		BudgetComp.setLayoutData(fd_BudgetComp);

		Label lblOptimizeBudget = new Label(BudgetComp, SWT.NONE);
		lblOptimizeBudget.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblOptimizeBudget.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblOptimizeBudget.setText("Optimal Purchase");

		Label dummy3 = new Label(BudgetComp, SWT.NONE);
		GridData gd_dummy3 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_dummy3.widthHint = 49;
		dummy3.setLayoutData(gd_dummy3);

		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET BUTTON
		 * 
		 *********************************************************************************************/
		Button btnOptimizeBudget = new Button(BudgetComp, SWT.NONE);
		btnOptimizeBudget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.CalcBudget();
			}
		});
		GridData gd_btnOptimizeBudget = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnOptimizeBudget.widthHint = 85;
		btnOptimizeBudget.setLayoutData(gd_btnOptimizeBudget);
		btnOptimizeBudget.setText("Calculate");
		/**********************************************************************************************
		 * END OF CALCULATE BUDGET
		 *********************************************************************************************/

		//Divider 3
		Label divider3 = createDivider(MainComp, BudgetComp);


		/**********************************************************************************************
		 * 
		 * EVENT PROGRAM FLOW SECTION
		 * 
		 *********************************************************************************************/
		EventFlowComp = new Composite(MainComp, SWT.NONE);
		EventFlowComp.setLayout(new GridLayout(3, false));
		FormData fd_EventFlowComp = new FormData();
		fd_EventFlowComp.right = new FormAttachment(90);
		fd_EventFlowComp.left = new FormAttachment(10);
		fd_EventFlowComp.top = new FormAttachment(divider3, 30);
		EventFlowComp.setLayoutData(fd_EventFlowComp);

		Label lblEventFlow = new Label(EventFlowComp, SWT.NONE);
		lblEventFlow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblEventFlow.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblEventFlow.setText("Event Program Flow");

		Label lblNewLabel_1 = new Label(EventFlowComp, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 38;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText(" ");

		/**********************************************************************************************
		 * 
		 * EDIT EVENT FLOW BUTTON
		 * 
		 *********************************************************************************************/
		Button btnEventFlow = new Button(EventFlowComp, SWT.NONE);
		btnEventFlow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EventFlow();
			}
		});
		GridData gd_btnEventFlow = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnEventFlow.widthHint = 85;
		btnEventFlow.setLayoutData(gd_btnEventFlow);
		btnEventFlow.setText("Edit");
		/**********************************************************************************************
		 * END OF EVENT PROGRAM FLOW
		 *********************************************************************************************/

		//Divider 4
		Label divider4 = createDivider(MainComp, EventFlowComp);

		/**********************************************************************************************
		 * 
		 * PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		ParticipantListComp = new Composite(MainComp, SWT.NONE);
		ParticipantListComp.setLayout(new GridLayout(3, false));
		FormData fd_ParticipantListComp = new FormData();
		fd_ParticipantListComp.left = new FormAttachment(10);
		fd_ParticipantListComp.right = new FormAttachment(90);
		fd_ParticipantListComp.top = new FormAttachment(divider4, 30);
		ParticipantListComp.setLayoutData(fd_ParticipantListComp);

		Label lblParticipantList = new Label(ParticipantListComp, SWT.NONE);
		lblParticipantList.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblParticipantList.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblParticipantList.setText("Participant List");

		Label dummy4 = new Label(ParticipantListComp, SWT.NONE);
		dummy4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		/**********************************************************************************************
		 * 
		 * EDIT PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		Button btnParticipantList = new Button(ParticipantListComp, SWT.NONE);
		btnParticipantList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ParticipantList(cevent);
			}
		});
		GridData gd_btnParticipantList = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnParticipantList.widthHint = 85;
		btnParticipantList.setLayoutData(gd_btnParticipantList);
		btnParticipantList.setText("Edit");
		/**********************************************************************************************
		 * END OF PARTICIPANT LIST
		 *********************************************************************************************/
		//Divider 5
		Label divider5 = createDivider(MainComp, ParticipantListComp);
		

		/**********************************************************************************************
		 * 
		 * ADVERTISMENT SECTION
		 * 
		 *********************************************************************************************/
		Composite AdvertComp = new Composite(MainComp, SWT.NONE);
		AdvertComp.setLayout(new GridLayout(3, false));
		FormData fd_AdvertisementComp = new FormData();
		fd_AdvertisementComp.top = new FormAttachment(divider5, 30);
		fd_AdvertisementComp.left = new FormAttachment(10);
		fd_AdvertisementComp.right = new FormAttachment(90);
		fd_AdvertisementComp.bottom = new FormAttachment(95);
		AdvertComp.setLayoutData(fd_AdvertisementComp);

		Label lblAdvertisement = new Label(AdvertComp, SWT.NONE);
		lblAdvertisement.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAdvertisement.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblAdvertisement.setText("Advertising");

		Label dummy5 = new Label(AdvertComp, SWT.NONE);
		dummy5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		new Label(AdvertComp, SWT.NONE);
		new Label(AdvertComp, SWT.NONE);
		new Label(AdvertComp, SWT.NONE);
		new Label(AdvertComp, SWT.NONE);
		/**********************************************************************************************
		 * 
		 * ADVERTISE BUTTON
		 * 
		 *********************************************************************************************/
		Button btnAdvertEmail = new Button(AdvertComp, SWT.NONE);
		btnAdvertEmail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginDiag = new LoginEmailDialog(new Shell(), SWT.NONE, cevent);
				loginDiag.open();

			}
		});
		GridData gd_btnAdvertEmail = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnAdvertEmail.widthHint = 85;
		btnAdvertEmail.setLayoutData(gd_btnAdvertEmail);
		btnAdvertEmail.setText("E-Mail");

		Button btnAdvertFB = new Button(AdvertComp, SWT.NONE);
		btnAdvertFB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.FaceBookAds();
			}
		});
		GridData gd_btnAdvertFB = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnAdvertFB.widthHint = 85;
		btnAdvertFB.setLayoutData(gd_btnAdvertFB);
		btnAdvertFB.setText("Facebook");

		Button btnAdvertSMS = new Button(AdvertComp, SWT.NONE);
		btnAdvertSMS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.SMSAds(cevent);
			}
		});
		GridData gd_btnAdvertSMS = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnAdvertSMS.widthHint = 85;
		btnAdvertSMS.setLayoutData(gd_btnAdvertSMS);
		btnAdvertSMS.setText("SMS");
		new Label(AdvertComp, SWT.NONE);
		new Label(AdvertComp, SWT.NONE);
		new Label(AdvertComp, SWT.NONE);
		/**********************************************************************************************
		 * END OF ADVERTISING
		 *********************************************************************************************/

		/**********************************************************************************************
		 * 
		 * DELETE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		Button btnDeleteEvent = new Button(MainComp, SWT.NONE);
		btnDeleteEvent.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", cevent.getName());
				if ((Integer) confirm.open() == 1) {
					ModelEvent.DeleteEvent(cevent);
					ViewMain.DeleteItem();
				}

			}
		});
		FormData fd_btnDeleteEvent = new FormData();
		fd_btnDeleteEvent.width = 100;
		fd_btnDeleteEvent.bottom = new FormAttachment(lblViewEvent, 0, SWT.BOTTOM);
		fd_btnDeleteEvent.right = new FormAttachment(divider1, 0, SWT.RIGHT);
		btnDeleteEvent.setLayoutData(fd_btnDeleteEvent);
		btnDeleteEvent.setText("Delete");

		Button btnExport = new Button(MainComp, SWT.NONE);
		btnExport.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				try {
					FileDialog fsd = new FileDialog(new Shell());
					String[] extension = {"*.csv"};
					fsd.setFilterExtensions(extension);

					String input = fsd.open();
					if (input != null) {
						if (!input.toLowerCase().endsWith(".csv"))
							input+= ".csv";
					}
					CSVWriter writer = new CSVWriter(new FileWriter(input));
					boolean allEmptyTable = true;
					if(VenueResult != null) {
						String[] tableHeader = {"Booked Venue Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, VenueResult, writer);	
						allEmptyTable = false;
					}
					if(BudgetResult != null) {
						String[] tableHeader = {"Budget Item List Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, BudgetResult, writer);
						allEmptyTable = false;
					}
					if(FlowResult != null) {
						String[] tableHeader = {"Event Flow Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, FlowResult, writer);	
						allEmptyTable = false;
					}
					if(ParticipantResult != null) {
						String[] tableHeader = {"Participant Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, ParticipantResult, writer);	
						allEmptyTable = false;
					}
					if (allEmptyTable == true) throw new IOException();
					new errormessageDialog(new Shell(), "The file was exported successfully!").open();
					writer.close();
				}catch (IOException ex) {
					// TODO Auto-generated catch block
					System.out.println("Error exporting");
					new errormessageDialog(new Shell(), "There was an error exporting the file.").open();
					//ex.printStackTrace();
				} catch(NullPointerException ex) {
					//User close the file browser with cancel button or 'X' button.
				}
		}
	});
		FormData fd_btnExport = new FormData();
		fd_btnExport.width = 100;
		//fd_btnExport.left = new FormAttachment(lblViewEvent, 282);
		fd_btnExport.right = new FormAttachment(btnDeleteEvent, -10);
		fd_btnExport.bottom = new FormAttachment(lblViewEvent, 0, SWT.BOTTOM);
		btnExport.setLayoutData(fd_btnExport);
		btnExport.setText("Export");

		//Checks and refreshes the individual sections
		RefreshBudget();
		RefreshVenue();
		RefreshFlow();
		RefreshParticipant();

		//Removes ability to edit if event is archived
		if (cevent.isExpired()) {
			btnEventParticulars.setEnabled(false);
			btnBookVenue.setEnabled(false);
			btnParticipantList.setEnabled(false);
			btnOptimizeBudget.setEnabled(false);
			btnEventFlow.setEnabled(false);
			btnAdvertEmail.setEnabled(false);
			btnAdvertFB.setEnabled(false);
			btnAdvertSMS.setEnabled(false);

		}
		sc1.setContent(MainComp);
		sc1.setMinSize(MainComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
}

/**
 * Description: Creates a vertical separator in View Event page which attaches itself below  a composite object
 * @param container The composite the divider is in
 * @param object The composite the divider is going to attach to
 * @return The created divider is returned
 */
public Label createDivider (Composite container, Composite object) {
	Label divider = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
	FormData fd_divider = new FormData();
	fd_divider.top = new FormAttachment(object, 30);
	fd_divider.left = new FormAttachment(5);
	fd_divider.right = new FormAttachment(95);
	divider.setLayoutData(fd_divider);

	return divider;
}

/**
 * Description: Refreshes the budget table in the Optimize Budget section of View Event by disposing the current one (if any) and replacing with a new table.
 * Table will only be created if there is any entry.
 */
public static void RefreshBudget() {
	if (BudgetFlag)
		BudgetResult.dispose();
	BudgetResult = BudgetTable();

}

/**
 * Description: Refreshes the venue table in the Book Venue section of View Event by disposing the current one (if any) and replacing with a new table.
 * Table will only be created if there is any entry.
 */
public static void RefreshVenue() {
	if (VenueFlag)
		VenueResult.dispose();
	VenueResult = VenueTable();

}

public static void RefreshFlow() {
	if (FlowFlag)
		FlowResult.dispose();
	FlowResult = FlowTable();

}

public static void RefreshParticipant() {
	if (ParticipantFlag)
		ParticipantResult.dispose();
	ParticipantResult = ParticipantTable();

}


/**********************************************************************************************
 * 
 * PARTICIPANTTABLE 
 * 
 *********************************************************************************************/
/**
 * Description: Creates a table of participant details in the ViewEvent page. 
 * @return Table containing participant details or null if ParticipantList is empty 
 */
public static Table ParticipantTable() {
	List<Participant> participant_list = cevent.getParticipantList();


	//Checks if there is any entry before creating the table
	if (participant_list.isEmpty()) {
		ParticipantFlag = false;	
		return null;
	}


	ParticipantResult = new Table(ParticipantListComp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);		
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
	ParticipantResult.setLayoutData(data);
	ParticipantResult.setHeaderVisible(true);
	ParticipantResult.getVerticalBar().setEnabled(false);

	final TableColumn col0 = new TableColumn(ParticipantResult, SWT.LEFT);
	final TableColumn col1 = new TableColumn(ParticipantResult, SWT.CENTER);
	final TableColumn col2 = new TableColumn(ParticipantResult, SWT.CENTER);
	final TableColumn col3 = new TableColumn(ParticipantResult, SWT.CENTER);
	final TableColumn col4 = new TableColumn(ParticipantResult, SWT.CENTER);
	final TableColumn col5 = new TableColumn(ParticipantResult, SWT.CENTER);

	col0.setText("Name");
	col1.setText("Matric No.");
	col2.setText("Contact");
	col3.setText("Email Address");
	col4.setText("Home Address");
	col5.setText("Remark");

	ParticipantResult.removeAll();
	for (int loopIndex = 0; loopIndex < participant_list.size(); loopIndex++) {
		TableItem item = new TableItem(ParticipantResult, SWT.NULL);
		item.setText(0, participant_list.get(loopIndex).getName());
		item.setText(1, participant_list.get(loopIndex).getMatric());
		item.setText(2, participant_list.get(loopIndex).getContact());
		item.setText(3, participant_list.get(loopIndex).getEmail());
		item.setText(4, participant_list.get(loopIndex).getAddress());
		item.setText(5, participant_list.get(loopIndex).getRemark());


	}
	for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
		ParticipantResult.getColumn(loopIndex).pack();
	}					


	//Column Resize with table fix
	ParticipantListComp.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle area = ParticipantListComp.getClientArea();
			Point preferredSize = ParticipantResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int width = area.width - 2*ParticipantResult.getBorderWidth();
			if (preferredSize.y > area.height + ParticipantResult.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = ParticipantResult.getVerticalBar().getSize();
				width -= vBarSize.x;

			}
			col1.pack();
			col2.pack();
			col4.setWidth((width - col1.getWidth() - col2.getWidth())/3);
			col0.setWidth((width - col1.getWidth() - col2.getWidth() - col4.getWidth())*2/5);
			col3.setWidth((width - col1.getWidth() - col2.getWidth() - col4.getWidth())*2/5-10);
			col5.setWidth(width - col1.getWidth() - col2.getWidth() - col4.getWidth() - col3.getWidth() - col0.getWidth()-10);
		}
	});

	//Table Tooltip
	ParticipantResult.addMouseTrackListener(new MouseTrackAdapter() {
		@Override
		public void mouseHover(MouseEvent e) {
			TableItem item = ParticipantResult.getItem(new Point(e.x, e.y)); 

			if (item != null) {
				//tooltip for every column
				for (int i=0; i<ParticipantResult.getColumnCount()-1; i++) {
					if (e.x > item.getBounds(i).x && e.x < item.getBounds(i+1).x) {
						ParticipantResult.setToolTipText(item.getText(i));
						break;
					}

					else if (i == ParticipantResult.getColumnCount()-2 && (e.x > item.getBounds(i+1).x))
						ParticipantResult.setToolTipText(item.getText(++i));
				}
			}
		}
	});

	//Dictates when vertical scrollbar appears
	if (ParticipantResult.getItemCount() > 10) {
		data.heightHint = 11 * ParticipantResult.getItemHeight();
		ParticipantResult.getVerticalBar().setEnabled(true);
	}
	ParticipantFlag = true;
	return ParticipantResult;
}

/**
 * Description: Creates a table that is populated with the details of the event's list of booked venues
 * @return A table is returned if there are entries in the list, else null is returned
 */
public static Table VenueTable() {
	Vector<BookedVenueInfo> venue_list = cevent.getBVI_list();

	//Checks if there is any entry before creating the table
	if (venue_list.isEmpty()) {
		VenueFlag = false;	
		return null;
	}


	VenueResult = new Table(BookVenueComp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
	VenueResult.setLayoutData(data);
	VenueResult.setHeaderVisible(true);
	VenueResult.getVerticalBar().setEnabled(false);

	final TableColumn col0 = new TableColumn(VenueResult, SWT.LEFT);
	final TableColumn col1 = new TableColumn(VenueResult, SWT.CENTER);
	final TableColumn col2 = new TableColumn(VenueResult, SWT.CENTER);
	final TableColumn col3 = new TableColumn(VenueResult, SWT.CENTER);
	final TableColumn col4 = new TableColumn(VenueResult, SWT.CENTER);
	final TableColumn col5 = new TableColumn(VenueResult, SWT.CENTER);
	final TableColumn col6 = new TableColumn(VenueResult, SWT.CENTER);

	col0.setText("Venue Name");
	col1.setText("Capacity");
	col2.setText("Cost");
	col3.setText("Start Date");
	col4.setText("Start Time");
	col5.setText("End Date");
	col6.setText("End Time");

	VenueResult.removeAll();
	for (int loopIndex = 0; loopIndex < venue_list.size(); loopIndex++) {
		TableItem item = new TableItem(VenueResult, SWT.NULL);
		item.setText(0, venue_list.get(loopIndex).getName());
		item.setText(1, venue_list.get(loopIndex).getMaxCapacityString());
		item.setText(2, "$" + venue_list.get(loopIndex).getCostInDollarString());
		item.setText(3, venue_list.get(loopIndex).getStartDateString());
		item.setText(4, venue_list.get(loopIndex).getStartTimeString());
		item.setText(5, venue_list.get(loopIndex).getEndDateString());		
		item.setText(6, venue_list.get(loopIndex).getEndTimeString());		

	}
	for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
		VenueResult.getColumn(loopIndex).pack();
	}					


	//Column Resize with table fix
	BookVenueComp.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle area = BookVenueComp.getClientArea();
			Point preferredSize = VenueResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int width = area.width - 2*VenueResult.getBorderWidth();
			if (preferredSize.y > area.height + VenueResult.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = VenueResult.getVerticalBar().getSize();
				width -= vBarSize.x;

			}
			col0.setWidth(width/5-10);
			col1.setWidth((width - col0.getWidth())/6-10);
			col3.setWidth((width - col0.getWidth())/6+4);
			col4.setWidth((width - col0.getWidth())/6-4);
			col5.setWidth((width - col0.getWidth())/6+4);
			col6.setWidth((width - col0.getWidth())/6-4);
			col2.setWidth(width - col0.getWidth() - col1.getWidth() - col3.getWidth() - col4.getWidth() -col5.getWidth() -col6.getWidth()-10);
		}
	});

	//Table Tooltip
	VenueResult.addMouseTrackListener(new MouseTrackAdapter() {
		@Override
		public void mouseHover(MouseEvent e) {
			TableItem item = VenueResult.getItem(new Point(e.x, e.y));

			if (item != null) {
				//tooltip for every column
				for (int i=0; i<VenueResult.getColumnCount()-1; i++) {
					if (e.x > item.getBounds(i).x && e.x < item.getBounds(i+1).x) {
						VenueResult.setToolTipText(item.getText(i));
						break;
					}

					else if (i == VenueResult.getColumnCount()-2 && (e.x > item.getBounds(i+1).x))
						VenueResult.setToolTipText(item.getText(++i));
				}
			}
		}
	});

	//Dictates when vertical scrollbar appears
	if (VenueResult.getItemCount() > 10) {
		data.heightHint = 11 * VenueResult.getItemHeight();
		VenueResult.getVerticalBar().setEnabled(true);
	}
	VenueFlag = true;
	return VenueResult;
}

public static Table FlowTable() {
	Vector<EventFlowEntry> flow_list = cevent.getEventFlow();

	System.out.println("Size: " + flow_list.size());
	for(int i=0; i<flow_list.size(); i++) {
		System.out.println(flow_list.get(i).getActivityName());
	}


	//Checks if there is any entry before creating the table
	if (flow_list.isEmpty()) {
		FlowFlag = false;	
		return null;
	}


	FlowResult = new Table(EventFlowComp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);		
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
	FlowResult.setLayoutData(data);
	FlowResult.setHeaderVisible(true);
	FlowResult.getVerticalBar().setEnabled(false);

	final TableColumn col0 = new TableColumn(FlowResult, SWT.LEFT);
	final TableColumn col1 = new TableColumn(FlowResult, SWT.CENTER);
	final TableColumn col2 = new TableColumn(FlowResult, SWT.CENTER);
	final TableColumn col3 = new TableColumn(FlowResult, SWT.CENTER);
	final TableColumn col4 = new TableColumn(FlowResult, SWT.CENTER);

	col0.setText("Start Date Time");
	col1.setText("End Date Time");
	col2.setText("Activity");
	col3.setText("Venue");
	col4.setText("Remark");

	FlowResult.removeAll();
	for (int loopIndex = 0; loopIndex < flow_list.size(); loopIndex++) {
		TableItem item = new TableItem(FlowResult, SWT.NULL);
		item.setText(0, flow_list.get(loopIndex).getDuration().getStartDateTime().getDateRepresentation() + " " +
				flow_list.get(loopIndex).getDuration().getStartDateTime().getTimeRepresentation());
		item.setText(1, flow_list.get(loopIndex).getDuration().getEndDateTime().getDateRepresentation() + " " +
				flow_list.get(loopIndex).getDuration().getEndDateTime().getTimeRepresentation());
		item.setText(2, flow_list.get(loopIndex).getActivityName());
		item.setText(3, flow_list.get(loopIndex).getVenueName());
		item.setText(4, flow_list.get(loopIndex).getUserNote());


	}
	for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
		FlowResult.getColumn(loopIndex).pack();
	}					


	//Column Resize with table fix
	EventFlowComp.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle area = EventFlowComp.getClientArea();
			Point preferredSize = FlowResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int width = area.width - 2*FlowResult.getBorderWidth();
			if (preferredSize.y > area.height + FlowResult.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = FlowResult.getVerticalBar().getSize();
				width -= vBarSize.x;

			}

			col0.pack();
			col1.pack();
			col2.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
			col3.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
			col4.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
		}
	});

	//Table Tooltip
	FlowResult.addMouseTrackListener(new MouseTrackAdapter() {
		@Override
		public void mouseHover(MouseEvent e) {
			TableItem item = FlowResult.getItem(new Point(e.x, e.y));
			//	 BudgetResult.setToolTipText(item.getText(0));

			if (item != null) {
				//tooltip for every column
				for (int i=0; i<FlowResult.getColumnCount()-1; i++) {
					if (e.x > item.getBounds(i).x && e.x < item.getBounds(i+1).x) {
						FlowResult.setToolTipText(item.getText(i));
						break;
					}

					else if (i == FlowResult.getColumnCount()-2 && (e.x > item.getBounds(i+1).x))
						FlowResult.setToolTipText(item.getText(++i));
				}
			}
		}
	});


	//Dictates when vertical scrollbar appears
	if (FlowResult.getItemCount() > 10) {
		data.heightHint = 11 * FlowResult.getItemHeight();
		FlowResult.getVerticalBar().setEnabled(true);
	}
	FlowFlag = true;
	return FlowResult;
}

/**
 * Description: Creates a sortable table that is populated with items of the event's optimized item list
 * @return A table is returned if there are entries in the list, else null is returned
 */
public static Table BudgetTable() {
	final Vector<Item> item_list = cevent.getitem_list();

	//Checks if there is any entry before creating the table
	if (item_list.isEmpty()) {
		BudgetFlag = false;	
		return null;
	}


	BudgetResult = new Table(BudgetComp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
	BudgetResult.setLayoutData(data);
	BudgetResult.setHeaderVisible(true);

	final TableColumn col0 = new TableColumn(BudgetResult, SWT.LEFT);
	final TableColumn col1 = new TableColumn(BudgetResult, SWT.LEFT);
	final TableColumn col2 = new TableColumn(BudgetResult, SWT.LEFT);
	final TableColumn col3 = new TableColumn(BudgetResult, SWT.CENTER);
	final TableColumn col4 = new TableColumn(BudgetResult, SWT.CENTER);
	final TableColumn col5 = new TableColumn(BudgetResult, SWT.CENTER);

	col0.setText("No.");
	col1.setText("Item Name");
	col2.setText("Price");
	col3.setText("Satisfaction");
	col4.setText("Type");
	col5.setText("Quantity");

	col0.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(0, item_list);
		}
	});

	//Sorting algorithm of each column
	col1.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(1, item_list);
		}
	});

	col2.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(2, item_list);
		}
	});

	col3.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(3, item_list);
		}
	});

	col4.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(4, item_list);
		}
	});

	col5.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			sortColumn(5, item_list);
		}
	});
	refreshBudgetTable(item_list);


	//Column Resize with table fix
	BudgetComp.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle area = BudgetComp.getClientArea();
			Point preferredSize = BudgetResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int width = area.width - 2*BudgetResult.getBorderWidth();
			if (preferredSize.y > area.height + BudgetResult.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = BudgetResult.getVerticalBar().getSize();
				width -= vBarSize.x;
			}
			col0.pack();
			col2.pack();
			col3.pack();
			col4.pack();
			col5.pack();
			col1.setWidth(width - col0.getWidth() - col2.getWidth() - col3.getWidth() - col4.getWidth() - col5.getWidth() -10);

		}
	});

	//Table Tooltip
	BudgetResult.addMouseTrackListener(new MouseTrackAdapter() {
		@Override
		public void mouseHover(MouseEvent e) {
			TableItem item = BudgetResult.getItem(new Point(e.x, e.y));
			//	 BudgetResult.setToolTipText(item.getText(0));

			if (item != null) {
				//tooltip for every column
				for (int i=0; i<BudgetResult.getColumnCount()-1; i++) {
					if (e.x > item.getBounds(i).x && e.x < item.getBounds(i+1).x) {
						BudgetResult.setToolTipText(item.getText(i));
						break;
					}
	
					else if (i == BudgetResult.getColumnCount()-2 && (e.x > item.getBounds(i+1).x))
						BudgetResult.setToolTipText(item.getText(++i));
				}
			}
		}
	});

	Menu menu = new Menu(BudgetResult);
	BudgetResult.setMenu(menu);

	/************************************************************
	 * DELETE ITEM
	 ***********************************************************/
	MenuItem mntmDeleteEvent = new MenuItem(menu, SWT.PUSH);
	mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			TableItem tb = BudgetResult.getItem(BudgetResult.getSelectionIndex());
			int itemToDelete = Integer.parseInt(tb.getText(0).substring(5, tb.getText(0).length()));
			deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", tb.getText(1));
			if ((Integer) confirm.open() == 1) {
				deleteBudgetItem(item_list, itemToDelete);
			}
		}
	});
	mntmDeleteEvent.setText("Delete Item");

	if (cevent.isExpired()) {
		mntmDeleteEvent.setEnabled(false);
	}
	
	//Dictates when vertical scrollbar appears
	if (BudgetResult.getItemCount() > 10) {
		data.heightHint = 11 * BudgetResult.getItemHeight();
		BudgetResult.getVerticalBar().setEnabled(true);
	}
	BudgetFlag = true;
	return BudgetResult;
}

public static void deleteBudgetItem(Vector<Item> item_list, int itemToDelete) {

	bc = new ControllerBudget();
	bc.deleteBudgetItem(cevent.getID(), item_list.get(itemToDelete-1).getID());
	item_list.remove(budgetTableSelectedIndex);
	cevent.setitem_list(item_list);

	refreshBudgetTable(item_list);

}

public static void sortColumn(int columnNo, Vector<Item> item_list) {

	TableItem[] items = BudgetResult.getItems();
	Collator collator = Collator.getInstance(Locale.getDefault());
	int col0_value1=0, col0_value2=0, col3_value1=0, col3_value2=0,col5_value1=0, col5_value2=0;
	double col2_value1=0, col2_value2=0;
	String col1_value1="", col1_value2="", col4_value1="", col4_value2="";
	boolean compareCorrect = false;
	for (int i = 1; i<items.length; i++) {
		if(columnNo == 0) 
			col0_value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
		else if (columnNo == 1) 
			col1_value1 = items[i].getText(1);
		else if (columnNo == 2)
			col2_value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
		else if (columnNo == 3)
			col3_value1 = Integer.parseInt(items[i].getText(3));
		else if (columnNo == 4)
			col4_value1 = items[i].getText(4);
		else if (columnNo == 5)
			col5_value1 = Integer.parseInt(items[i].getText(5));
		for(int j=0; j < i; j++) {
			compareCorrect = false;
			if(columnNo == 0)  {
				col0_value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
				if(col0_value1 - col0_value2 < 0) 
					compareCorrect = true;
			}					
			else if (columnNo == 1) {
				col1_value2 = items[j].getText(1);
				if(collator.compare(col1_value1, col1_value2) < 0) 
					compareCorrect = true;
			}	
			else if (columnNo == 2) {
				col2_value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
				if((col2_value1 - col2_value2) > 0) 
					compareCorrect = true;
			}	
			else if (columnNo == 3) {
				col3_value2 = Integer.parseInt(items[j].getText(3));
				if((col3_value1 - col3_value2) > 0) 
					compareCorrect = true;
			}	
			else if (columnNo == 4) {
				col4_value2 = items[j].getText(4);
				if(collator.compare(col4_value1, col4_value2) < 0) 
					compareCorrect = true;
			}
			else if (columnNo == 5) {
				col5_value2 = Integer.parseInt(items[j].getText(5));
				if((col5_value1 - col5_value2) > 0) 
					compareCorrect = true;
			}

			if(compareCorrect == true) {
				Item temp = item_list.get(i);
				item_list.remove(i);
				String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4),items[i].getText(5)};
				items[i].dispose();
				TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
				item_list.add(j, temp);
				item.setText(values);
				items = BudgetResult.getItems();
				break;
			}
		}	
	}

	refreshBudgetTable(item_list);
}

public static void refreshBudgetTable(Vector<Item> item_list) {

	BudgetResult.removeAll();
	for (int loopIndex = 0; loopIndex < item_list.size(); loopIndex++) {
		TableItem item = new TableItem(BudgetResult, SWT.NULL);
		item.setText(0, "Item " + (loopIndex+1));
		item.setText(1, item_list.get(loopIndex).getItem());
		item.setText(2, "$"+((double) item_list.get(loopIndex).getPrice())/100);
		if(item_list.get(loopIndex).getSatisfactionValue() == -1)
			item.setText(3, "");
		else
			item.setText(3, ""+item_list.get(loopIndex).getSatisfactionValue());
		if(item_list.get(loopIndex).getType() == null)
			item.setText(4, "");
		else
			item.setText(4, ""+item_list.get(loopIndex).getType());					
		item.setText(5, ""+item_list.get(loopIndex).getQuantity());
	}
}

public void ExportCSV (String filepath, Table inputTable, CSVWriter writer) {


		Table processingTable = inputTable;
		/*
		 * Booked Venue
		 */
		String[] headers = new String[processingTable.getColumnCount()];
		for (int i=0; i<processingTable.getColumnCount(); i++) {
			headers[i] = processingTable.getColumns()[i].getText();
		}

		writer.writeNext(headers);

		for (int i=0; i<processingTable.getItemCount(); i++) {
			String[] entries = new String[processingTable.getColumnCount()];
			for (int j=0; j<processingTable.getColumnCount(); j++) 
				entries[j] = processingTable.getItem(i).getText(j);
			writer.writeNext(entries);
		}
		
		String[] emptyLine = {""};
		writer.writeNext(emptyLine);
		


}

@Override
protected void checkSubclass() {
	// Disable the check that prevents subclassing of SWT components
}
}

