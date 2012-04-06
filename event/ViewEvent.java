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

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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
	private static Table tableBudget, tableVenue, tableEventFlow, tableParticipant, tableTemp;
	private static boolean budgetFlag, venueFlag, eventFlowFlag, participantFlag;
	private static EventItem currentEvent;
	private static Composite compBudget, compVenue, compEventFlow, compParticipant;
	private static Label lblDynamicDescription, lblDynamicStartDate, lblDynamicStartTime, lblDynamicEndDate, lblDynamicEndTime, lblDynamicname;
	private static ScrolledComposite scrollCompositeMain;
	private static Composite compMain;
	private static ControllerBudget bc;
	private static int indexParticipant =0;
	private static int indexVenue =0;
	private static int indexEventFlow =0;
	protected LoginEmailDialog loginDialog; 

	// These two variables are for implementing column sort.
	private static List<Participant> participantList;
	private static TableViewer tableViewerParticipant;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEvent(Composite parent, int style, EventItem curevent) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		scrollCompositeMain = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrollCompositeMain.setExpandHorizontal(true);
		scrollCompositeMain.setExpandVertical(true);

		compMain = new Composite(scrollCompositeMain, SWT.NONE);
		compMain.setLayout(new FormLayout());
		budgetFlag = false;
		venueFlag = false;
		participantFlag = false;
		currentEvent = curevent;

		//View Event title label
		Label lblViewEvent = new Label(compMain, SWT.NONE);
		lblViewEvent.setText("View Event:");
		lblViewEvent.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 20, SWT.BOLD));
		FormData fdViewTitle = new FormData();
		fdViewTitle.top = new FormAttachment(0, 10);
		fdViewTitle.left = new FormAttachment(0, 10);
		lblViewEvent.setLayoutData(fdViewTitle);

		/**********************************************************************************************
		 * 
		 * EVENT PARTICULARS COMPOSITE SECTION
		 * 
		 *********************************************************************************************/
		Composite compEventParticulars = new Composite(compMain, SWT.NONE);
		FormData fdEventParticulars = new FormData();
		fdEventParticulars.right = new FormAttachment(90);
		fdEventParticulars.top = new FormAttachment(lblViewEvent, 30);
		fdEventParticulars.left = new FormAttachment(10);
		compEventParticulars.setLayoutData(fdEventParticulars);
		compEventParticulars.setLayout(new GridLayout(4, false));

		Label lblEventParticulars = new Label(compEventParticulars, SWT.NONE);
		lblEventParticulars.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblEventParticulars.setText("Event Particulars");
		new Label(compEventParticulars, SWT.NONE);

		/**********************************************************************************************
		 * 
		 * EDIT EVENT PARTICULARS BUTTON
		 * 
		 *********************************************************************************************/
		new Label(compEventParticulars, SWT.NONE);
		Button btnEventParticulars = new Button(compEventParticulars, SWT.NONE);
		btnEventParticulars.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));

		btnEventParticulars.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EventParticulars();
			}
		});
		GridData gdEventParticulars = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gdEventParticulars.widthHint = 85;
		btnEventParticulars.setLayoutData(gdEventParticulars);
		btnEventParticulars.setText("Edit");
		new Label(compEventParticulars, SWT.NONE);
		new Label(compEventParticulars, SWT.NONE);
		new Label(compEventParticulars, SWT.NONE);
		new Label(compEventParticulars, SWT.NONE);

		Label lblEname = new Label(compEventParticulars, SWT.NONE);
		lblEname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEname.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblEname.setText("Event Name:");

		lblDynamicname = new Label(compEventParticulars, SWT.WRAP);
		lblDynamicname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		lblDynamicname.setFont(SWTResourceManager.getFont("Malgun Gothic", 13, SWT.NORMAL));
		lblDynamicname.setText(currentEvent.getName());

		Label lblStartDate = new Label(compEventParticulars, SWT.NONE);
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblStartDate.setText("Start Date:");

		lblDynamicStartDate = new Label(compEventParticulars, SWT.NONE);
		lblDynamicStartDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		lblDynamicStartDate.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		lblDynamicStartDate.setText(currentEvent.getStartDateTime().getDateRepresentation());

		Label lblStartTime = new Label(compEventParticulars, SWT.NONE);
		lblStartTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		lblStartTime.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblStartTime.setText("Start Time:");

		lblDynamicStartTime = new Label(compEventParticulars, SWT.NONE);
		lblDynamicStartTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblDynamicStartTime.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		lblDynamicStartTime.setText(currentEvent.getStartDateTime().getTimeRepresentation()+"HRS");

		Label lblEndDate = new Label(compEventParticulars, SWT.NONE);
		lblEndDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDate.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblEndDate.setText("End Date:");

		lblDynamicEndDate = new Label(compEventParticulars, SWT.NONE);
		lblDynamicEndDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblDynamicEndDate.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		lblDynamicEndDate.setText(currentEvent.getEndDateTime().getDateRepresentation());

		Label lblEndTime = new Label(compEventParticulars, SWT.NONE);
		lblEndTime.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblEndTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblEndTime.setText("End Time:");

		lblDynamicEndTime = new Label(compEventParticulars, SWT.NONE);
		lblDynamicEndTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblDynamicEndTime.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		lblDynamicEndTime.setText(currentEvent.getEndDateTime().getTimeRepresentation()+"HRS");

		Label lblEdescription = new Label(compEventParticulars, SWT.NONE);
		lblEdescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEdescription.setFont(SWTResourceManager.getFont("Lithos Pro Regular", 12, SWT.BOLD));
		lblEdescription.setText("Description:");

		lblDynamicDescription = new Label(compEventParticulars, SWT.WRAP | SWT.SHADOW_NONE);
		lblDynamicDescription.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		GridData gdDynamicDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gdDynamicDescription.widthHint = 90;
		lblDynamicDescription.setLayoutData(gdDynamicDescription);
		lblDynamicDescription.setText(currentEvent.getDescription());
		/**********************************************************************************************
		 * END OF EVENT PARTICULARS SECTION
		 *********************************************************************************************/
		//Divider1
		Label divider1 = createDivider(compMain, compEventParticulars);

		/**********************************************************************************************
		 * 
		 * BOOK VENUE SECTION
		 * 
		 *********************************************************************************************/
		compVenue = new Composite(compMain, SWT.NONE);
		compVenue.setLayout(new GridLayout(3, false));
		FormData fdBookVenue = new FormData();
		fdBookVenue.right = new FormAttachment(90);
		fdBookVenue.left = new FormAttachment(10);
		fdBookVenue.top = new FormAttachment(divider1, 30);
		compVenue.setLayoutData(fdBookVenue);

		Label lblBookVenue = new Label(compVenue, SWT.NONE);
		lblBookVenue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblBookVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblBookVenue.setText("Book Venue");

		Label lblDummyVenue = new Label(compVenue, SWT.NONE);
		GridData gdDummyVenue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gdDummyVenue.widthHint = 27;
		lblDummyVenue.setLayoutData(gdDummyVenue);

		/**********************************************************************************************
		 * 
		 * BOOK VENUE BUTTON
		 * 
		 *********************************************************************************************/
		Button btnBookVenue = new Button(compVenue, SWT.NONE);
		btnBookVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnBookVenue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.BookVenue();
				//ViewMain.setPage(new ViewHomepage(ViewMain.getC2(),SWT.NONE));
			}
		});
		GridData gdBookVenue = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gdBookVenue.widthHint = 85;
		btnBookVenue.setLayoutData(gdBookVenue);
		btnBookVenue.setText("Edit");
		/**********************************************************************************************
		 * END OF BOOK VENUE
		 *********************************************************************************************/
		//Divider 2
		Label divider2 = createDivider(compMain, compVenue);

		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET SECTION
		 * 
		 *********************************************************************************************/
		compBudget = new Composite(compMain, SWT.NONE);
		compBudget.setLayout(new GridLayout(3, false));
		FormData fdBudget = new FormData();
		fdBudget.right = new FormAttachment(90);
		fdBudget.left = new FormAttachment(10);
		fdBudget.top = new FormAttachment(divider2, 30);
		compBudget.setLayoutData(fdBudget);

		Label lblOptimizeBudget = new Label(compBudget, SWT.NONE);
		lblOptimizeBudget.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblOptimizeBudget.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblOptimizeBudget.setText("Optimal Purchase");

		Label lblDummyBudget = new Label(compBudget, SWT.NONE);
		GridData gdDummyBudget = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gdDummyBudget.widthHint = 49;
		lblDummyBudget.setLayoutData(gdDummyBudget);

		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET BUTTON
		 * 
		 *********************************************************************************************/
		Button btnOptimizeBudget = new Button(compBudget, SWT.NONE);
		btnOptimizeBudget.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnOptimizeBudget.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.CalcBudget();
			}
		});
		GridData gdOptimizeBudget = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gdOptimizeBudget.widthHint = 85;
		btnOptimizeBudget.setLayoutData(gdOptimizeBudget);
		btnOptimizeBudget.setText("Calculate");
		/**********************************************************************************************
		 * END OF CALCULATE BUDGET
		 *********************************************************************************************/
		//Divider 3
		Label divider3 = createDivider(compMain, compBudget);


		/**********************************************************************************************
		 * 
		 * EVENT PROGRAM FLOW SECTION
		 * 
		 *********************************************************************************************/
		compEventFlow = new Composite(compMain, SWT.NONE);
		compEventFlow.setLayout(new GridLayout(3, false));
		FormData fdEventFlow = new FormData();
		fdEventFlow.right = new FormAttachment(90);
		fdEventFlow.left = new FormAttachment(10);
		fdEventFlow.top = new FormAttachment(divider3, 30);
		compEventFlow.setLayoutData(fdEventFlow);

		Label lblEventFlow = new Label(compEventFlow, SWT.NONE);
		lblEventFlow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblEventFlow.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblEventFlow.setText("Event Program Flow");

		Label lblEventDummy = new Label(compEventFlow, SWT.NONE);
		GridData gdEventDummy = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gdEventDummy.widthHint = 38;
		lblEventDummy.setLayoutData(gdEventDummy);
		lblEventDummy.setText(" ");

		/**********************************************************************************************
		 * 
		 * EDIT EVENT FLOW BUTTON
		 * 
		 *********************************************************************************************/
		Button btnEventFlow = new Button(compEventFlow, SWT.NONE);
		btnEventFlow.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnEventFlow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EventFlow();
			}
		});
		GridData gdEventFlow = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gdEventFlow.widthHint = 85;
		btnEventFlow.setLayoutData(gdEventFlow);
		btnEventFlow.setText("Edit");
		/**********************************************************************************************
		 * END OF EVENT PROGRAM FLOW
		 *********************************************************************************************/
		//Divider 4
		Label divider4 = createDivider(compMain, compEventFlow);

		/**********************************************************************************************
		 * 
		 * PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		compParticipant = new Composite(compMain, SWT.NONE);
		compParticipant.setLayout(new GridLayout(3, false));
		FormData fdParticipantList = new FormData();
		fdParticipantList.left = new FormAttachment(10);
		fdParticipantList.right = new FormAttachment(90);
		fdParticipantList.top = new FormAttachment(divider4, 30);
		compParticipant.setLayoutData(fdParticipantList);

		Label lblParticipantList = new Label(compParticipant, SWT.NONE);
		lblParticipantList.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblParticipantList.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblParticipantList.setText("Participant List");

		Label lblDummyParticipant = new Label(compParticipant, SWT.NONE);
		lblDummyParticipant.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		/**********************************************************************************************
		 * 
		 * EDIT PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		Button btnParticipantList = new Button(compParticipant, SWT.NONE);
		btnParticipantList.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnParticipantList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ParticipantList();
			}
		});
		GridData gdParticipantList = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gdParticipantList.widthHint = 85;
		btnParticipantList.setLayoutData(gdParticipantList);
		btnParticipantList.setText("Edit");
		/**********************************************************************************************
		 * END OF PARTICIPANT LIST
		 *********************************************************************************************/
		//Divider 5
		Label divider5 = createDivider(compMain, compParticipant);


		/**********************************************************************************************
		 * 
		 * ADVERTISMENT SECTION
		 * 
		 *********************************************************************************************/
		Composite compAdvertise = new Composite(compMain, SWT.NONE);
		compAdvertise.setLayout(new GridLayout(3, false));
		FormData fdAdvertise = new FormData();
		fdAdvertise.top = new FormAttachment(divider5, 30);
		fdAdvertise.left = new FormAttachment(10);
		fdAdvertise.right = new FormAttachment(90);
		fdAdvertise.bottom = new FormAttachment(95);
		compAdvertise.setLayoutData(fdAdvertise);

		Label lblAdvertisement = new Label(compAdvertise, SWT.NONE);
		lblAdvertisement.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAdvertisement.setFont(SWTResourceManager.getFont("Maiandra GD", 18, SWT.BOLD));
		lblAdvertisement.setText("Advertising");

		Label lblDummyAdvertise = new Label(compAdvertise, SWT.NONE);
		lblDummyAdvertise.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		new Label(compAdvertise, SWT.NONE);
		new Label(compAdvertise, SWT.NONE);
		new Label(compAdvertise, SWT.NONE);
		new Label(compAdvertise, SWT.NONE);
		/**********************************************************************************************
		 * 
		 * ADVERTISE BUTTON
		 * 
		 *********************************************************************************************/
		Button btnAdvertEmail = new Button(compAdvertise, SWT.NONE);
		btnAdvertEmail.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				loginDialog = new LoginEmailDialog(new Shell(), currentEvent);
				loginDialog.open();

			}
		});
		GridData gdAdvertEmail = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gdAdvertEmail.widthHint = 85;
		btnAdvertEmail.setLayoutData(gdAdvertEmail);
		btnAdvertEmail.setText("E-Mail");

		Button btnAdvertFB = new Button(compAdvertise, SWT.NONE);
		btnAdvertFB.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.FaceBookAds();
			}
		});
		GridData gdAdvertFB = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gdAdvertFB.widthHint = 85;
		btnAdvertFB.setLayoutData(gdAdvertFB);
		btnAdvertFB.setText("Facebook");

		Button btnAdvertSMS = new Button(compAdvertise, SWT.NONE);
		btnAdvertSMS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.SMSAds();
			}
		});
		GridData gdAdvertSMS = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gdAdvertSMS.widthHint = 85;
		btnAdvertSMS.setLayoutData(gdAdvertSMS);
		btnAdvertSMS.setText("SMS");
		new Label(compAdvertise, SWT.NONE);
		new Label(compAdvertise, SWT.NONE);
		new Label(compAdvertise, SWT.NONE);
		/**********************************************************************************************
		 * END OF ADVERTISING
		 *********************************************************************************************/

		/**********************************************************************************************
		 * 
		 * DELETE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		Button btnDeleteEvent = new Button(compMain, SWT.NONE);
		btnDeleteEvent.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnDeleteEvent.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", currentEvent.getName());
				if ((Integer) confirm.open() == 1) {
					ModelEvent.DeleteEvent(currentEvent);
					ViewMain.DeleteItem();
				}

			}
		});
		FormData fdDeleteEvent = new FormData();
		fdDeleteEvent.width = 100;
		fdDeleteEvent.bottom = new FormAttachment(lblViewEvent, 0, SWT.BOTTOM);
		fdDeleteEvent.right = new FormAttachment(divider1, 0, SWT.RIGHT);
		btnDeleteEvent.setLayoutData(fdDeleteEvent);
		btnDeleteEvent.setText("Delete");

		Button btnExport = new Button(compMain, SWT.NONE);
		btnExport.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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
					if(tableVenue != null) {
						String[] tableHeader = {"Booked Venue Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, tableVenue, writer);	
						allEmptyTable = false;
					}
					if(tableBudget != null) {
						String[] tableHeader = {"Budget Item List Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, tableBudget, writer);
						allEmptyTable = false;
					}
					if(tableEventFlow != null) {
						String[] tableHeader = {"Event Flow Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, tableEventFlow, writer);	
						allEmptyTable = false;
					}
					if(tableParticipant != null) {
						String[] tableHeader = {"Participant Details"};
						writer.writeNext(tableHeader);
						ExportCSV(input, tableParticipant, writer);	
						allEmptyTable = false;
					}
					if (allEmptyTable == true) throw new IOException();
					new ErrorMessageDialog(new Shell(), "The file was exported successfully!").open();
					writer.close();
				}catch (IOException ex) {
					System.out.println("Error exporting");
					new ErrorMessageDialog(new Shell(), "There was an error exporting the file.").open();
				} catch(NullPointerException ex) {
					//User close the file browser with cancel button or 'X' button.
				}
			}
		});
		FormData fdExport = new FormData();
		fdExport.width = 100;
		fdExport.right = new FormAttachment(btnDeleteEvent, -10);
		fdExport.bottom = new FormAttachment(lblViewEvent, 0, SWT.BOTTOM);
		btnExport.setLayoutData(fdExport);
		btnExport.setText("Export");

		//Checks and refreshes the individual sections
		RefreshBudget();
		RefreshVenue();
		RefreshFlow();
		RefreshParticipant();

		//Removes ability to edit if event is archived
		if (currentEvent.isExpired()) {
			btnEventParticulars.setEnabled(false);
			btnBookVenue.setEnabled(false);
			btnParticipantList.setEnabled(false);
			btnOptimizeBudget.setEnabled(false);
			btnEventFlow.setEnabled(false);
			btnAdvertEmail.setEnabled(false);
			btnAdvertFB.setEnabled(false);
			btnAdvertSMS.setEnabled(false);

		}
		scrollCompositeMain.setContent(compMain);
		scrollCompositeMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * Description: Creates a vertical separator in View Event page which attaches itself below  a composite object
	 * @param container The composite the divider is in
	 * @param object The composite the divider is going to attach to
	 * @return The created divider is returned
	 */
	public Label createDivider (Composite container, Composite object) {
		Label divider = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fdDivider = new FormData();
		fdDivider.top = new FormAttachment(object, 30);
		fdDivider.left = new FormAttachment(5);
		fdDivider.right = new FormAttachment(95);
		divider.setLayoutData(fdDivider);

		return divider;
	}

	/**
	 * Description: Refreshes the budget table in the Optimize Budget section of View Event by disposing the current one (if any) and replacing with a new table.
	 * Table will only be created if there is any entry.
	 */
	public static void RefreshBudget() {
		if (budgetFlag)
			tableBudget.dispose();
		tableBudget = BudgetJfaceTable();
	//	tableBudget = BudgetTable();
	}

	/**
	 * Description: Refreshes the venue table in the Book Venue section of View Event by disposing the current one (if any) and replacing with a new table.
	 * Table will only be created if there is any entry.
	 */
	public static void RefreshVenue() {
		if (venueFlag)
			tableVenue.dispose();
		//tableVenue = VenueTable();
		tableVenue = VenueJfaceTable();
	}

	public static void RefreshFlow() {
		if (eventFlowFlag)
			tableEventFlow.dispose();
		//tableEventFlow = FlowTable();
		tableEventFlow = FlowJfaceTable();
	}

	public static void RefreshParticipant() {
		if (participantFlag)
			tableParticipant.dispose();
		//tableParticipant = ParticipantTable();
		tableParticipant =  ParticipantJfaceTable();
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
		List<Participant> participantList = currentEvent.getParticipantList();

		//Checks if there is any entry before creating the table
		if (participantList.isEmpty()) {
			participantFlag = false;	
			return null;
		}

		String columnName[] = {"Name","Matric No.","Contact","Email Address","Home Address","Remark"};
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableParticipant = createTableMethod(compParticipant, data, columnName);

		final TableColumn col0 = tableParticipant.getColumn(0);
		final TableColumn col1 = tableParticipant.getColumn(1);
		final TableColumn col2 = tableParticipant.getColumn(2);
		final TableColumn col3 = tableParticipant.getColumn(3);
		final TableColumn col4 = tableParticipant.getColumn(4);
		final TableColumn col5 = tableParticipant.getColumn(5);
		tableParticipant.getColumn(0).setAlignment(16384);

		tableParticipant.removeAll();
		for (int loopIndex = 0; loopIndex < participantList.size(); loopIndex++) {
			TableItem item = new TableItem(tableParticipant, SWT.NULL);
			item.setText(0, participantList.get(loopIndex).getName());
			item.setText(1, participantList.get(loopIndex).getMatric());
			item.setText(2, participantList.get(loopIndex).getContact());
			item.setText(3, participantList.get(loopIndex).getEmail());
			item.setText(4, participantList.get(loopIndex).getAddress());
			item.setText(5, participantList.get(loopIndex).getRemark());
		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			tableParticipant.getColumn(loopIndex).pack();
		}					

		//Column Resize with table fix
		compParticipant.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = compParticipant.getClientArea();
				int width = area.width - 2*tableParticipant.getBorderWidth();

				//			controlResizePackage(compParticipant, tableParticipant, width, area);
				Point preferredSize = tableParticipant.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if (preferredSize.y > area.height + tableParticipant.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = tableParticipant.getVerticalBar().getSize();
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

		mouseOverPackage(tableParticipant, data);
		setTableRowDisplayed(tableParticipant, data, 15);
		participantFlag = true;
		return tableParticipant;
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
	public static Table ParticipantJfaceTable() {
		participantList = currentEvent.getParticipantList();

		//Checks if there is any entry before creating the table
		if (participantList.isEmpty()) {
			participantFlag = false;	
			return null;
		}

		final String columnName[] = {"Name","Matric No.","Contact","Email Address","Home Address","Remark"};
		TableViewerColumn[] tvc = new TableViewerColumn[columnName.length];

		Composite tableComposite = new Composite(compParticipant, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		tableComposite.setLayoutData(data);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);

		tableViewerParticipant = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableParticipant = tableViewerParticipant.getTable();
		tableParticipant.setHeaderVisible(true);
		tableParticipant.getVerticalBar().setEnabled(true);
		tableParticipant.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewerParticipant, SWT.NONE);
			tvc[i].getColumn().setText(columnName[i]);
		}

		//Setting column width.
		tcl_tableComposite.setColumnData(tvc[0].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[1].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[2].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[3].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[4].getColumn(), new ColumnWeightData(30));
		tcl_tableComposite.setColumnData(tvc[5].getColumn(), new ColumnWeightData(10));

		//Populating the table.
		tableViewerParticipant.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<columnName.length; i++) {
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (indexParticipant == columnName.length)
						indexParticipant = 0;						
					Participant parti = (Participant) element;
					switch (indexParticipant)	{
					case 0:
						indexParticipant++;
						return parti.getName();
					case 1:
						indexParticipant++;
						return parti.getMatric();
					case 2:
						indexParticipant++;
						return parti.getContact();
					case 3:
						indexParticipant++;
						return parti.getEmail();
					case 4:
						indexParticipant++;
						return parti.getAddress();
					case 5:
						indexParticipant++;
						return parti.getRemark();
					default:
						return null;
					}
				}
			});
		}
		
		//////////////////////////////////////////////////////////////////////////////
		// Add listeners for table columns
		/////////////////////////////////////////////////////////////////////////////

		// Column 0: Name
		tvc[0].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.NAME);
				tableViewerParticipant.refresh();
			}
		});

		// Column 1: Matric
		tvc[1].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.MATRIC);
				tableViewerParticipant.refresh();
			}
		});

		// Column 2: Contact
		tvc[2].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.CONTACT);
				tableViewerParticipant.refresh();
			}
		});

		// Column 3: Email
		tvc[3].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.EMAIL);
				tableViewerParticipant.refresh();
			}
		});

		// Column 4: Address
		tvc[4].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.ADDRESS);
				tableViewerParticipant.refresh();
			}
		});

		// Column 5: Remark
		tvc[5].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(participantList, Participant.COLUMNSORTCRITERIA.REMARK);
				tableViewerParticipant.refresh();
			}
		});

		tableViewerParticipant.setInput(participantList);
		tvc[1].getColumn().pack();
		tvc[2].getColumn().pack();


		mouseOverPackage(tableParticipant, data);
		//Dictates when vertical scrollbar appears
		setTableRowDisplayed(tableParticipant, data, 15);


		participantFlag = true;
		return tableParticipant;
	}
	/**
	 * Description: Creates a table that is populated with the details of the event's list of booked venues
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table VenueTable() {
		Vector<BookedVenueInfo> venueList = currentEvent.getBVIList();

		//Checks if there is any entry before creating the table
		if (venueList.isEmpty()) {
			venueFlag = false;	
			return null;
		}
		String columnName[] = {"Venue Name", "Capacity", "Cost", "Start Date", "Start Time","End Date","End Time"};
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableVenue = createTableMethod(compVenue, data, columnName);

		final TableColumn col0 = tableVenue.getColumn(0);
		final TableColumn col1 = tableVenue.getColumn(1);
		final TableColumn col2 = tableVenue.getColumn(2);
		final TableColumn col3 = tableVenue.getColumn(3);
		final TableColumn col4 = tableVenue.getColumn(4);
		final TableColumn col5 = tableVenue.getColumn(5);
		final TableColumn col6 = tableVenue.getColumn(6);
		tableVenue.getColumn(0).setAlignment(16384);

		//		tableVenue.removeAll();
		for (int loopIndex = 0; loopIndex < venueList.size(); loopIndex++) {
			TableItem item = new TableItem(tableVenue, SWT.NULL);
			item.setText(0, venueList.get(loopIndex).getName());
			item.setText(1, venueList.get(loopIndex).getMaxCapacityString());
			item.setText(2, "$" + venueList.get(loopIndex).getCostInDollarString());
			item.setText(3, venueList.get(loopIndex).getStartDateString());
			item.setText(4, venueList.get(loopIndex).getStartTimeString());
			item.setText(5, venueList.get(loopIndex).getEndDateString());		
			item.setText(6, venueList.get(loopIndex).getEndTimeString());		

		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			tableVenue.getColumn(loopIndex).pack();
		}					

		//Column Resize with table fix
		compVenue.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = compVenue.getClientArea();
				int width = area.width - 2*tableVenue.getBorderWidth();

				controlResizePackage(compVenue, tableVenue, width, area);

				col0.setWidth(width/5-10);
				col1.setWidth((width - col0.getWidth())/6-10);
				col3.setWidth((width - col0.getWidth())/6+4);
				col4.setWidth((width - col0.getWidth())/6-4);
				col5.setWidth((width - col0.getWidth())/6+4);
				col6.setWidth((width - col0.getWidth())/6-4);
				col2.setWidth(width - col0.getWidth() - col1.getWidth() - col3.getWidth() - col4.getWidth() -col5.getWidth() -col6.getWidth()-10);
			}
		});

		mouseOverPackage(tableVenue, data);

		venueFlag = true;
		return tableVenue;
	}

	public static Table FlowTable() {
		Vector<EventFlowEntry> flowList = currentEvent.getEventFlow();

		//Checks if there is any entry before creating the table
		if (flowList.isEmpty()) {
			eventFlowFlag = false;	
			return null;
		}

		String columnName[] = {"Start Date Time", "End Date Time", "Activity", "Venue", "Remark"};
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableEventFlow = createTableMethod(compEventFlow, data, columnName);

		final TableColumn col0 = tableEventFlow.getColumn(0);
		final TableColumn col1 = tableEventFlow.getColumn(1);
		final TableColumn col2 = tableEventFlow.getColumn(2);
		final TableColumn col3 = tableEventFlow.getColumn(3);
		final TableColumn col4 = tableEventFlow.getColumn(4);
		tableEventFlow.getColumn(0).setAlignment(16384);

		tableEventFlow.removeAll();
		for (int loopIndex = 0; loopIndex < flowList.size(); loopIndex++) {
			TableItem item = new TableItem(tableEventFlow, SWT.NULL);
			item.setText(0, flowList.get(loopIndex).getDuration().getStartDateTime().getDateRepresentation() + " " +
					flowList.get(loopIndex).getDuration().getStartDateTime().getTimeRepresentation());
			item.setText(1, flowList.get(loopIndex).getDuration().getEndDateTime().getDateRepresentation() + " " +
					flowList.get(loopIndex).getDuration().getEndDateTime().getTimeRepresentation());
			item.setText(2, flowList.get(loopIndex).getActivityName());
			item.setText(3, flowList.get(loopIndex).getVenueName());
			item.setText(4, flowList.get(loopIndex).getUserNote());


		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			tableEventFlow.getColumn(loopIndex).pack();
		}					

		//Column Resize with table fix
		compEventFlow.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = compEventFlow.getClientArea();
				int width = area.width - 2*tableEventFlow.getBorderWidth();

				controlResizePackage(compEventFlow, tableEventFlow, width, area);

				col0.pack();
				col1.pack();
				col2.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
				col3.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
				col4.setWidth((width - col0.getWidth() - col1.getWidth() - 10)/3);
			}
		});

		mouseOverPackage(tableEventFlow, data);

		eventFlowFlag = true;
		return tableEventFlow;
	}
	
	/**********************************************************************************************
	 * 
	 * EVENT FLOW TABLE 
	 * 
	 *********************************************************************************************/
	/**
	 * Description:  
	 * @return  
	 */
	public static Table FlowJfaceTable() {
		Vector<EventFlowEntry> flowList = currentEvent.getEventFlow();

		//Checks if there is any entry before creating the table
		if (flowList.isEmpty()) {
			eventFlowFlag = false;	
			return null;
		}
	

		final String columnName[] = {"Start Date Time", "End Date Time", "Activity", "Venue", "Remark"};
		TableViewerColumn[] tvc = new TableViewerColumn[columnName.length];

		Composite tableComposite = new Composite(compEventFlow, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		tableComposite.setLayoutData(data);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);

		TableViewer tableViewerEventFlow = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableEventFlow = tableViewerEventFlow.getTable();
		tableEventFlow.setHeaderVisible(true);
		tableEventFlow.getVerticalBar().setEnabled(true);
		tableEventFlow.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewerEventFlow, SWT.NONE);
			tvc[i].getColumn().setText(columnName[i]);
		}

		//Setting column width.
		tcl_tableComposite.setColumnData(tvc[0].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[1].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[2].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[3].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[4].getColumn(), new ColumnWeightData(20));
	
		tvc[0].getColumn().setAlignment(SWT.CENTER);
		tvc[1].getColumn().setAlignment(SWT.CENTER);
		tvc[2].getColumn().setAlignment(SWT.CENTER);
		tvc[3].getColumn().setAlignment(SWT.CENTER);
		tvc[4].getColumn().setAlignment(SWT.CENTER);
		
		//Populating the table.
		tableViewerEventFlow.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<columnName.length; i++) {
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (indexEventFlow == columnName.length)
						indexEventFlow = 0;						
					EventFlowEntry EFE = (EventFlowEntry) element;
					switch (indexEventFlow)	{
					case 0:
						indexEventFlow++;
						return EFE.getDuration().getStartDateTime().getDateRepresentation() + " " +
									EFE.getDuration().getStartDateTime().getTimeRepresentation();
					case 1:
						indexEventFlow++;
						return EFE.getDuration().getEndDateTime().getDateRepresentation() + " " +
								EFE.getDuration().getEndDateTime().getTimeRepresentation();
					case 2:
						indexEventFlow++;
						return EFE.getActivityName();
					case 3:
						indexEventFlow++;
						return EFE.getVenueName();
					case 4:
						indexEventFlow++;
						return EFE.getUserNote();
					default:
						return null;
					}
				}
			});
		}
		tableViewerEventFlow.setInput(flowList);

		mouseOverPackage(tableEventFlow, data);
		//Dictates when vertical scrollbar appears
		setTableRowDisplayed(tableEventFlow, data, 15);


		eventFlowFlag = true;
		return tableEventFlow;
	}
	
	/**********************************************************************************************
	 * 
	 * BOOK VENUE TABLE 
	 * 
	 *********************************************************************************************/
	/**
	 * Description:  
	 * @return  
	 */
	public static Table VenueJfaceTable() {
		Vector<BookedVenueInfo> venueList = currentEvent.getBVIList();

		//Checks if there is any entry before creating the table
		if (venueList.isEmpty()) {
			venueFlag = false;	
			return null;
		}
	

		final String columnName[] = {"Venue Name", "Capacity", "Cost", "Start Date", "Start Time","End Date","End Time"};
		TableViewerColumn[] tvc = new TableViewerColumn[columnName.length];

		Composite tableComposite = new Composite(compVenue, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		tableComposite.setLayoutData(data);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);

		TableViewer tableViewerVenue = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableVenue = tableViewerVenue.getTable();
		tableVenue.setHeaderVisible(true);
		tableVenue.getVerticalBar().setEnabled(true);
		tableVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewerVenue, SWT.NONE);
			tvc[i].getColumn().setText(columnName[i]);
		}

		//Setting column width.
		tcl_tableComposite.setColumnData(tvc[0].getColumn(), new ColumnWeightData(20));
		tcl_tableComposite.setColumnData(tvc[1].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[2].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[3].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[4].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[5].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[6].getColumn(), new ColumnWeightData(10));

		tvc[1].getColumn().setAlignment(SWT.CENTER);
		tvc[2].getColumn().setAlignment(SWT.CENTER);
		tvc[3].getColumn().setAlignment(SWT.CENTER);
		tvc[4].getColumn().setAlignment(SWT.CENTER);
		tvc[5].getColumn().setAlignment(SWT.CENTER);
		tvc[6].getColumn().setAlignment(SWT.CENTER);
		
		//Populating the table.
		tableViewerVenue.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<columnName.length; i++) {
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (indexVenue == columnName.length)
						indexVenue = 0;						
					BookedVenueInfo BVI = (BookedVenueInfo) element;
					switch (indexVenue)	{
					case 0:
						indexVenue++;
						return BVI.getName();
					case 1:
						indexVenue++;
						return BVI.getMaxCapacityString();
					case 2:
						indexVenue++;
						return "$" + BVI.getCostInDollarString();
					case 3:
						indexVenue++;
						return BVI.getStartDateString();
					case 4:
						indexVenue++;
						return BVI.getStartTimeString();
					case 5:
						indexVenue++;
						return BVI.getEndDateString();
					case 6:
						indexVenue++;
						return BVI.getEndTimeString();
					default:
						return null;
					}
				}
			});
		}
		tableViewerVenue.setInput(venueList);

		mouseOverPackage(tableVenue, data);
		//Dictates when vertical scrollbar appears
		setTableRowDisplayed(tableVenue, data, 15);


		venueFlag = true;
		return tableVenue;
	}
	/**
	 * Description: Creates a sortable table that is populated with items of the event's optimized item list
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table BudgetTable() {
		final Vector<Item> itemList = currentEvent.getItemList();

		//Checks if there is any entry before creating the table
		if (itemList.isEmpty()) {
			budgetFlag = false;	
			return null;
		}

		String columnName[] = {"No.", "Item Name", "Price", "Satisfaction", "Type","Quantity"};
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableBudget = createTableMethod(compBudget, data, columnName);

		final TableColumn col0 = tableBudget.getColumn(0);
		final TableColumn col1 = tableBudget.getColumn(1);
		final TableColumn col2 = tableBudget.getColumn(2);
		final TableColumn col3 = tableBudget.getColumn(3);
		final TableColumn col4 = tableBudget.getColumn(4);
		final TableColumn col5 = tableBudget.getColumn(5);
		tableBudget.getColumn(1).setAlignment(16384);

		col0.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(0, itemList);
			}
		});

		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(1, itemList);
			}
		});

		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(2, itemList);
			}
		});

		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(3, itemList);
			}
		});

		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(4, itemList);
			}
		});

		col5.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(5, itemList);
			}
		});
		refreshBudgetTable(itemList);

		//Column Resize with table fix
		compBudget.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = compBudget.getClientArea();
				int width = area.width - 2*tableBudget.getBorderWidth();

				controlResizePackage(compBudget, tableBudget, width, area);

				col0.pack();
				col2.pack();
				col3.pack();
				col4.pack();
				col5.pack();
				col1.setWidth(width - col0.getWidth() - col2.getWidth() - col3.getWidth() - col4.getWidth() - col5.getWidth() -10);
			}
		});

		mouseOverPackage(tableBudget, data);

		Menu menu = new Menu(tableBudget);
		tableBudget.setMenu(menu);

		/************************************************************
		 * DELETE ITEM
		 ***********************************************************/
		MenuItem mntmDeleteEvent = new MenuItem(menu, SWT.PUSH);
		mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tb = tableBudget.getItem(tableBudget.getSelectionIndex());
				int itemToDelete = Integer.parseInt(tb.getText(0).substring(5, tb.getText(0).length()));
				DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(1));
				if ((Integer) confirm.open() == 1) {
					deleteBudgetItem(itemList, itemToDelete);
				}
			}
		});
		mntmDeleteEvent.setText("Delete Item");

		if (currentEvent.isExpired()) {
			mntmDeleteEvent.setEnabled(false);
		}

		budgetFlag = true;
		return tableBudget;
	}

	/**
	 * Description: Creates a sortable table that is populated with items of the event's optimized item list
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table BudgetJfaceTable() {
		final Vector<Item> itemList = currentEvent.getItemList();

		//Checks if there is any entry before creating the table
		if (itemList.isEmpty()) {
			budgetFlag = false;	
			return null;
		}

		final String columnName[] = {"No.", "Item Name", "Price", "Satisfaction", "Type","Quantity"};
		TableViewerColumn[] tvc = new TableViewerColumn[columnName.length];

		Composite tableComposite = new Composite(compBudget, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		tableComposite.setLayoutData(data);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);

		TableViewer tableViewerBudget = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableBudget = tableViewerBudget.getTable();
		tableBudget.setHeaderVisible(true);
		tableBudget.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewerBudget, SWT.NONE);
			tvc[i].getColumn().setText(columnName[i]);
		}

		//Setting column width.
		tcl_tableComposite.setColumnData(tvc[0].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[1].getColumn(), new ColumnWeightData(30));
		tcl_tableComposite.setColumnData(tvc[2].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[3].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[4].getColumn(), new ColumnWeightData(10));
		tcl_tableComposite.setColumnData(tvc[5].getColumn(), new ColumnWeightData(10));

		refreshBudgetTable(itemList);
		tvc[0].getColumn().pack();
		tvc[2].getColumn().pack();
		tvc[3].getColumn().pack();
		tvc[4].getColumn().pack();
		tvc[5].getColumn().pack();


		tvc[0].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(0, itemList);
			}
		});

		tvc[1].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(1, itemList);
			}
		});

		tvc[2].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(2, itemList);
			}
		});

		tvc[3].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(3, itemList);
			}
		});

		tvc[4].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(4, itemList);
			}
		});

		tvc[5].getColumn().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(5, itemList);
			}
		});

		mouseOverPackage(tableBudget, data);
	
		//Dictates when vertical scrollbar appears
		setTableRowDisplayed(tableBudget, data, 15);

		Menu menu = new Menu(tableBudget);
		tableBudget.setMenu(menu);

		/************************************************************
		 * DELETE ITEM
		 ***********************************************************/
		MenuItem mntmDeleteEvent = new MenuItem(menu, SWT.PUSH);
		mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tb = tableBudget.getItem(tableBudget.getSelectionIndex());
				int itemToDelete = Integer.parseInt(tb.getText(0).substring(5, tb.getText(0).length()));
				DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(1));
				if ((Integer) confirm.open() == 1) {
					deleteBudgetItem(itemList, itemToDelete);
				}
			}
		});
		mntmDeleteEvent.setText("Delete Item");

		if (currentEvent.isExpired()) {
			mntmDeleteEvent.setEnabled(false);
		}


		budgetFlag = true;
		return tableBudget;
	}
	public static void deleteBudgetItem(Vector<Item> itemList, int itemToDelete) {

		bc = new ControllerBudget();
		bc.deleteBudgetItem(currentEvent.getID(), itemList.get(itemToDelete-1).getID());

		itemList.remove(itemToDelete-1);
		currentEvent.setItemList(itemList);
		refreshBudgetTable(itemList);
	}

	/**
	 * Description: Sorts the columns for the Budget Table
	 * @param columnNo - Column number
	 * @param itemList - Vector list of items
	 */
	public static void sortColumn(int columnNo, Vector<Item> itemList) {

		TableItem[] items = tableBudget.getItems();
		Collator collator = Collator.getInstance(Locale.getDefault());
		int col0Value1=0, col0Value2=0, col3Value1=0, col3Value2=0,col5Value1=0, col5Value2=0;
		double col2Value1=0, col2Value2=0;
		String col1Value1="", col1Value2="", col4Value1="", col4Value2="";
		boolean compareCorrect = false;
		for (int i = 1; i<items.length; i++) {
			if(columnNo == 0) 
				col0Value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
			else if (columnNo == 1) 
				col1Value1 = items[i].getText(1);
			else if (columnNo == 2)
				col2Value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
			else if (columnNo == 3)
				col3Value1 = Integer.parseInt(items[i].getText(3));
			else if (columnNo == 4)
				col4Value1 = items[i].getText(4);
			else if (columnNo == 5)
				col5Value1 = Integer.parseInt(items[i].getText(5));
			for(int j=0; j < i; j++) {
				compareCorrect = false;
				if(columnNo == 0)  {
					col0Value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
					if(col0Value1 - col0Value2 < 0) 
						compareCorrect = true;
				}					
				else if (columnNo == 1) {
					col1Value2 = items[j].getText(1);
					if(collator.compare(col1Value1, col1Value2) < 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 2) {
					col2Value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
					if((col2Value1 - col2Value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 3) {
					col3Value2 = Integer.parseInt(items[j].getText(3));
					if((col3Value1 - col3Value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 4) {
					col4Value2 = items[j].getText(4);
					if(collator.compare(col4Value1, col4Value2) < 0) 
						compareCorrect = true;
				}
				else if (columnNo == 5) {
					col5Value2 = Integer.parseInt(items[j].getText(5));
					if((col5Value1 - col5Value2) > 0) 
						compareCorrect = true;
				}

				if(compareCorrect == true) {
					Item temp = itemList.get(i);
					itemList.remove(i);
					String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4),items[i].getText(5)};
					items[i].dispose();
					TableItem item = new TableItem(tableBudget, SWT.NONE, j);
					itemList.add(j, temp);
					item.setText(values);
					items = tableBudget.getItems();
					break;
				}
			}	
		}

		refreshBudgetTable(itemList);
	}

	/**
	 * Description: Refreshes the budget table by re-populating the table
	 * @param itemList - Vector list of items
	 */
	public static void refreshBudgetTable(Vector<Item> itemList) {

		tableBudget.removeAll();
		for (int loopIndex = 0; loopIndex < itemList.size(); loopIndex++) {
			TableItem item = new TableItem(tableBudget, SWT.NULL);
			item.setText(0, "Item " + (loopIndex+1));
			item.setText(1, itemList.get(loopIndex).getItem());
			item.setText(2, "$"+((double) itemList.get(loopIndex).getPrice())/100);
			if(itemList.get(loopIndex).getSatisfactionValue() == -1)
				item.setText(3, "");
			else
				item.setText(3, ""+itemList.get(loopIndex).getSatisfactionValue());
			if(itemList.get(loopIndex).getType() == null)
				item.setText(4, "");
			else
				item.setText(4, ""+itemList.get(loopIndex).getType());					
			item.setText(5, ""+itemList.get(loopIndex).getQuantity());
		}
	}

	/**
	 * Description: Method to generate tables
	 * @param inputComposite - Composite for the table to reside in
	 * @param data - Layout data of the composite
	 * @param inputColumnName - Column names of the table columns
	 * @return - Returns the table object
	 */
	public static Table createTableMethod(Composite inputComposite, GridData data, String[] inputColumnName) {
		tableTemp = new Table(inputComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);

		tableTemp.setLayoutData(data);
		tableTemp.setHeaderVisible(true);
		tableTemp.getVerticalBar().setEnabled(false);

		for(int i=0; i<inputColumnName.length; i++) {
			final TableColumn tempColumn = new TableColumn(tableTemp, SWT.CENTER);
			tempColumn.setText(inputColumnName[i]);
		}

		return tableTemp;
	}

	/**
	 * Description: Exports a table in ViewEvent into a CSV file
	 * @param filepath - location where the exported CSV file is stored
	 * @param inputTable - Table to be exported
	 * @param writer - CSVWriter
	 */
	public void ExportCSV (String filepath, Table inputTable, CSVWriter writer) {
		Table processingTable = inputTable;

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

	public static void controlResizePackage(Composite inputComposite, Table inputTable, int width, Rectangle area) {

		Point preferredSize = inputTable.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (preferredSize.y > area.height + inputTable.getHeaderHeight()) {
			// Subtract the scrollbar width from the total column width
			// if a vertical scrollbar will be required
			Point vBarSize = inputTable.getVerticalBar().getSize();
			width -= vBarSize.x;
		}
	}
	/**
	 * Description: Method that sets the mouseover properties of the table to display the values of the individual cell the 
	 * mouse is currently hovering on
	 * @param inputTable - Table to be affected
	 * @param data - layout data of the composite where the table is residing in
	 */
	public static void mouseOverPackage(final Table inputTable, GridData data) {
		//Table Tooltip
		inputTable.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(MouseEvent e) {
				TableItem item = inputTable.getItem(new Point(e.x, e.y));

				if (item != null) {
					//tooltip for every column
					for (int i=0; i<inputTable.getColumnCount()-1; i++) {
						if (e.x > item.getBounds(i).x && e.x < item.getBounds(i+1).x) {
							inputTable.setToolTipText(item.getText(i));
							break;
						}
						else if (i == inputTable.getColumnCount()-2 && (e.x > item.getBounds(i+1).x))
							inputTable.setToolTipText(item.getText(++i));
					}
				}
			}
		});
	}

	/**
	 * Description: Limits the maximum number of rows the table can display at a single time before the vertical scrollbar appears
	 * @param inputTable - Table to be affected
	 * @param data - Layout data of the composite the table is residing in
	 * @param value - The maximum number of rows to be displayed at a time
	 */
	public static void setTableRowDisplayed (Table inputTable, GridData data, int value) {
		if (inputTable.getItemCount() > value) {
			data.heightHint = (value + 2) * inputTable.getItemHeight();
			inputTable.getVerticalBar().setEnabled(true);
		}
	}
	
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}