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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import com.ibm.icu.text.Collator;


public class ViewEvent extends Composite {
	private static Table BudgetResult, VenueResult;
	private static boolean budgetflag, venueflag;
	private static Eventitem cevent;
	private static Composite Budgetcomp, Bookvenuecomp;
	private static Label Edescription, Startdate, Starttime, Enddate, Endtime, Ename;
	private static ScrolledComposite sc1;
	private static Composite maincomp;
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

		maincomp = new Composite(sc1, SWT.NONE);
		maincomp.setLayout(new FormLayout());
		budgetflag = false;
		venueflag = false;
		cevent = curevent;

		//View Event title label
		Label Title = new Label(maincomp, SWT.NONE);
		Title.setText("View Event:");
		Title.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		FormData fd_Title = new FormData();
		fd_Title.top = new FormAttachment(0, 10);
		fd_Title.left = new FormAttachment(0, 10);
		Title.setLayoutData(fd_Title);

		/**********************************************************************************************
		 * 
		 * EVENT PARTICULARS COMPOSITE SECTION
		 * 
		 *********************************************************************************************/
		Composite EparticularsComp = new Composite(maincomp, SWT.NONE);
		FormData fd_EparticularsComp = new FormData();
		fd_EparticularsComp.right = new FormAttachment(80, 0);
		fd_EparticularsComp.top = new FormAttachment(Title, 30);
		fd_EparticularsComp.left = new FormAttachment(20, 0);
		EparticularsComp.setLayoutData(fd_EparticularsComp);
		EparticularsComp.setLayout(new GridLayout(3, false));

		Label Eparticulars = new Label(EparticularsComp, SWT.NONE);
		Eparticulars.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Eparticulars.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		Eparticulars.setText("Event Particulars");
		new Label(EparticularsComp, SWT.NONE);
		
		/**********************************************************************************************
		 * 
		 * EDIT EVENT PARTICULARS BUTTON
		 * 
		 *********************************************************************************************/
		Button Epartedit = new Button(EparticularsComp, SWT.NONE);
		Epartedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EventParticulars(cevent);
			}
		});
		GridData gd_Epartedit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_Epartedit.widthHint = 85;
		Epartedit.setLayoutData(gd_Epartedit);
		Epartedit.setText("Edit");
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);

		Label Enamelabel = new Label(EparticularsComp, SWT.NONE);
		Enamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enamelabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Enamelabel.setText("Event Name:");

		Ename = new Label(EparticularsComp, SWT.WRAP);
		Ename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		Ename.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Ename.setText(cevent.getName());

		Label Startdntlabel = new Label(EparticularsComp, SWT.NONE);
		Startdntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Startdntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Startdntlabel.setText("Start Date and Time:");

		Startdate = new Label(EparticularsComp, SWT.NONE);
		Startdate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Startdate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Startdate.setText(cevent.getStartDate());

		Starttime = new Label(EparticularsComp, SWT.NONE);
		Starttime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Starttime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Starttime.setText(cevent.getStartTime()+"HRS");

		Label Enddntlabel = new Label(EparticularsComp, SWT.NONE);
		Enddntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enddntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Enddntlabel.setText("End Date and Time:");

		Enddate = new Label(EparticularsComp, SWT.NONE);
		Enddate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Enddate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Enddate.setText(cevent.getEndDate());

		Endtime = new Label(EparticularsComp, SWT.NONE);
		Endtime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Endtime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Endtime.setText(cevent.getEndTime()+"HRS");

		Label Edescriptionlabel = new Label(EparticularsComp, SWT.NONE);
		Edescriptionlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Edescriptionlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Edescriptionlabel.setText("Description:");

		Edescription = new Label(EparticularsComp, SWT.WRAP);
		Edescription.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		GridData gd_Edescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_Edescription.widthHint = 90;
		Edescription.setLayoutData(gd_Edescription);
		Edescription.setText(cevent.getDescription());
		/**********************************************************************************************
		 * END OF EVENT PARTICULARS SECTION
		 *********************************************************************************************/
		
		//Divider1
		Label divider1 = createdivider(maincomp, EparticularsComp);
		
		/**********************************************************************************************
		 * 
		 * BOOK VENUE SECTION
		 * 
		 *********************************************************************************************/
		Bookvenuecomp = new Composite(maincomp, SWT.NONE);
		Bookvenuecomp.setLayout(new GridLayout(3, false));
		FormData fd_Bookvenuecomp = new FormData();
		fd_Bookvenuecomp.right = new FormAttachment(80, 0);
		fd_Bookvenuecomp.left = new FormAttachment(20, 0);
		fd_Bookvenuecomp.top = new FormAttachment(divider1, 30);
		Bookvenuecomp.setLayoutData(fd_Bookvenuecomp);

		Label lblBookVenue = new Label(Bookvenuecomp, SWT.NONE);
		lblBookVenue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblBookVenue.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		lblBookVenue.setText("Book Venue");

		Label dummy = new Label(Bookvenuecomp, SWT.NONE);
		GridData gd_dummy = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_dummy.widthHint = 27;
		dummy.setLayoutData(gd_dummy);

		/**********************************************************************************************
		 * 
		 * BOOK VENUE BUTTON
		 * 
		 *********************************************************************************************/
		Button Bookvenueedit = new Button(Bookvenuecomp, SWT.NONE);
		Bookvenueedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.BookVenue();
			}
		});
		GridData gd_Bookvenueedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Bookvenueedit.widthHint = 85;
		Bookvenueedit.setLayoutData(gd_Bookvenueedit);
		Bookvenueedit.setText("Edit");
		/**********************************************************************************************
		 * END OF BOOK VENUE
		 *********************************************************************************************/
	
		//Divider 2
		Label divider2 = createdivider(maincomp, Bookvenuecomp);
		
		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET SECTION
		 * 
		 *********************************************************************************************/
		Budgetcomp = new Composite(maincomp, SWT.NONE);
		Budgetcomp.setLayout(new GridLayout(3, false));
		FormData fd_Budgetcomp = new FormData();
		fd_Budgetcomp.right = new FormAttachment(80, 0);
		fd_Budgetcomp.left = new FormAttachment(20, 0);
		fd_Budgetcomp.top = new FormAttachment(divider2, 30);
		Budgetcomp.setLayoutData(fd_Budgetcomp);

		Label Cbudget = new Label(Budgetcomp, SWT.NONE);
		Cbudget.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Cbudget.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		Cbudget.setText("Optimal Purchase");

		Label dummy3 = new Label(Budgetcomp, SWT.NONE);
		GridData gd_dummy3 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_dummy3.widthHint = 49;
		dummy3.setLayoutData(gd_dummy3);

		/**********************************************************************************************
		 * 
		 * CALCULATE BUDGET BUTTON
		 * 
		 *********************************************************************************************/
		Button btnCalculate = new Button(Budgetcomp, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.CalcBudget();
			}
		});
		GridData gd_btnCalculate = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnCalculate.widthHint = 85;
		btnCalculate.setLayoutData(gd_btnCalculate);
		btnCalculate.setText("Calculate");
		/**********************************************************************************************
		 * END OF CALCULATE BUDGET
		 *********************************************************************************************/
		
		//Divider 3
		Label divider3 = createdivider(maincomp, Budgetcomp);

		
		/**********************************************************************************************
		 * 
		 * EVENT PROGRAM FLOW SECTION
		 * 
		 *********************************************************************************************/
		Composite Eprogflowcomp = new Composite(maincomp, SWT.NONE);
		Eprogflowcomp.setLayout(new GridLayout(3, false));
		FormData fd_Eprogflowcomp = new FormData();
		fd_Eprogflowcomp.right = new FormAttachment(80, 0);
		fd_Eprogflowcomp.left = new FormAttachment(20, 0);
		fd_Eprogflowcomp.top = new FormAttachment(divider3, 30);
		Eprogflowcomp.setLayoutData(fd_Eprogflowcomp);

		Label EProgramFlow = new Label(Eprogflowcomp, SWT.NONE);
		EProgramFlow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		EProgramFlow.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		EProgramFlow.setText("Event Program Flow");

		Label lblNewLabel_1 = new Label(Eprogflowcomp, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 38;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText(" ");

		/**********************************************************************************************
		 * 
		 * EDIT EVENT FLOW BUTTON
		 * 
		 *********************************************************************************************/
		Button Eprogflowedit = new Button(Eprogflowcomp, SWT.NONE);
		Eprogflowedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		GridData gd_Eprogflowedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Eprogflowedit.widthHint = 85;
		Eprogflowedit.setLayoutData(gd_Eprogflowedit);
		Eprogflowedit.setText("Edit");
		/**********************************************************************************************
		 * END OF EVENT PROGRAM FLOW
		 *********************************************************************************************/
		
		//Divider 4
		Label divider4 = createdivider(maincomp, Eprogflowcomp);
	
		/**********************************************************************************************
		 * 
		 * PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		Composite Plistcomp = new Composite(maincomp, SWT.NONE);
		Plistcomp.setLayout(new GridLayout(3, false));
		FormData fd_Plistcomp = new FormData();
		fd_Plistcomp.left = new FormAttachment(20, 0);
		fd_Plistcomp.right = new FormAttachment(80, 0);
		fd_Plistcomp.top = new FormAttachment(divider4, 30);
		Plistcomp.setLayoutData(fd_Plistcomp);

		Label ParticipantList = new Label(Plistcomp, SWT.NONE);
		ParticipantList.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		ParticipantList.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		ParticipantList.setText("Participant List");

		Label dummy4 = new Label(Plistcomp, SWT.NONE);
		dummy4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		/**********************************************************************************************
		 * 
		 * EDIT PARTICIPANT LIST SECTION
		 * 
		 *********************************************************************************************/
		Button btnEdit = new Button(Plistcomp, SWT.NONE);
		GridData gd_btnEdit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnEdit.widthHint = 85;
		btnEdit.setLayoutData(gd_btnEdit);
		btnEdit.setText("Edit");
		/**********************************************************************************************
		 * END OF PARTICIPANT LIST
		 *********************************************************************************************/
		//Divider 5
		Label divider5 = createdivider(maincomp, Plistcomp);

		/**********************************************************************************************
		 * 
		 * ADVERTISING SECTION
		 * 
		 *********************************************************************************************/
		Composite Advertcomp = new Composite(maincomp, SWT.NONE);
		Advertcomp.setLayout(new GridLayout(3, false));
		FormData fd_Advertcomp = new FormData();
		fd_Advertcomp.top = new FormAttachment(divider5, 30);
		fd_Advertcomp.left = new FormAttachment(20, 0);
		fd_Advertcomp.right = new FormAttachment(80, 0);
		Advertcomp.setLayoutData(fd_Advertcomp);

		Label Advertising = new Label(Advertcomp, SWT.NONE);
		Advertising.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Advertising.setFont(SWTResourceManager.getFont("Kristen ITC", 16, SWT.BOLD));
		Advertising.setText("Advertising");

		Label dummy5 = new Label(Advertcomp, SWT.NONE);
		dummy5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		/**********************************************************************************************
		 * 
		 * ADVERTISE BUTTON
		 * 
		 *********************************************************************************************/
		Button Advertedit = new Button(Advertcomp, SWT.NONE);
		GridData gd_Advertedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Advertedit.widthHint = 85;
		Advertedit.setLayoutData(gd_Advertedit);
		Advertedit.setText("Edit");
		/**********************************************************************************************
		 * END OF ADVERTISING
		 *********************************************************************************************/
		
		/**********************************************************************************************
		 * 
		 * DELETE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		Button btnDeleteEvent = new Button(maincomp, SWT.NONE);
		btnDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), SWT.APPLICATION_MODAL, cevent.getName());
				if (confirm.open() == 1) {
					ModelEvent.DeleteEvent(cevent);
					ViewMain.DeleteItem();
				}

			}
		});
		FormData fd_btnDeleteEvent = new FormData();
		fd_btnDeleteEvent.bottom = new FormAttachment(Title, 0, SWT.BOTTOM);
		fd_btnDeleteEvent.right = new FormAttachment(divider1, 0, SWT.RIGHT);
		btnDeleteEvent.setLayoutData(fd_btnDeleteEvent);
		btnDeleteEvent.setText("DELETE EVENT");

		//Checks and refreshes the individual sections and scroll space
		RefreshBudget();
		RefreshVenue();
		sc1.setContent(maincomp);
		sc1.setMinSize(maincomp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	/**
	 * Description: Creates a vertical separator in View Event page which attaches itself below  a composite object
	 * @param container The composite the divider is in
	 * @param object The composite the divider is going to attach to
	 * @return The created divider is returned
	 */
	public Label createdivider (Composite container, Composite object) {
		Label divider = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider = new FormData();
		fd_divider.top = new FormAttachment(object, 30);
		fd_divider.left = new FormAttachment(10, 0);
		fd_divider.right = new FormAttachment(90, 0);
		divider.setLayoutData(fd_divider);

		return divider;
	}
/*	//For use in version 0.2
	public static void UpdateEvent(Eventitem event) {
		cevent = event;
		RefreshParticulars();
		RefreshBudget();
		RefreshVenue();
		sc1.layout(true);
		sc1.setContent(maincomp);
		sc1.setMinSize(maincomp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public static void RefreshParticulars() {
		Startdate.setText(cevent.getStartDate());
		Starttime.setText(cevent.getStartTime()+"HRS");
		Enddate.setText(cevent.getEndDate());
		Endtime.setText(cevent.getEndTime()+"HRS");
		Edescription.setText(cevent.getDescription());
		Ename.setText(cevent.getName());
		System.out.println("refreshed");
		System.out.println(cevent.getDescription());
	}*/
	/**
	 * Description: Refreshes the budget table in the Optimize Budget section of View Event by disposing the current one (if any) and replacing with a new table.
	 * Table will only be created if there is any entry.
	 */
	public static void RefreshBudget() {
		if (budgetflag)
			BudgetResult.dispose();
		BudgetResult = OptimizedTable();

	}

	/**
	 * Description: Refreshes the venue table in the Book Venue section of View Event by disposing the current one (if any) and replacing with a new table.
	 * Table will only be created if there is any entry.
	 */
	public static void RefreshVenue() {
		if (venueflag)
			VenueResult.dispose();
		VenueResult = VenueTable();

	}

	/**
	 * Description: Creates a table that is populated with the details of the event's list of booked venues
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table VenueTable() {
		Vector<BookedVenueInfo> venue_list = cevent.getBVI_list();

		//Checks if there is any entry before creating the table
		if (venue_list.isEmpty()) {
			venueflag = false;	
			return null;
		}


		VenueResult = new Table(Bookvenuecomp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);		
		VenueResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		VenueResult.setHeaderVisible(true);

		TableColumn col0 = new TableColumn(VenueResult, SWT.LEFT);
		TableColumn col1 = new TableColumn(VenueResult, SWT.LEFT);
		TableColumn col2 = new TableColumn(VenueResult, SWT.NULL);
		TableColumn col3 = new TableColumn(VenueResult, SWT.NULL);
		TableColumn col4 = new TableColumn(VenueResult, SWT.NULL);
		TableColumn col5 = new TableColumn(VenueResult, SWT.NULL);
		TableColumn col6 = new TableColumn(VenueResult, SWT.NULL);

		col0.setText("Venue Name");
		col1.setText("Capacity");
		col2.setText("Cost");
		col3.setText("Start Date");
		col4.setText("Start Time");
		col5.setText("End Date");
		col6.setText("End Time");

		col0.setResizable(false);
		col1.setResizable(false);
		col2.setResizable(false);
		col3.setResizable(false);
		col4.setResizable(false);
		col5.setResizable(false);
		col6.setResizable(false);

		VenueResult.removeAll();
		System.out.println("Venue_list_size = " + venue_list.size());
		for (int loopIndex = 0; loopIndex < venue_list.size(); loopIndex++) {
			TableItem item = new TableItem(VenueResult, SWT.NULL);
			item.setText(0, venue_list.get(loopIndex).getName());
			item.setText(1, venue_list.get(loopIndex).getMaxCapacityString());
			item.setText(2, venue_list.get(loopIndex).getCostInDollarString());
			item.setText(3, venue_list.get(loopIndex).getStartDateString());
			item.setText(4, venue_list.get(loopIndex).getStartHourString());
			item.setText(5, venue_list.get(loopIndex).getEndDateString());		
			item.setText(6, venue_list.get(loopIndex).getEndHourString());		
		
		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			VenueResult.getColumn(loopIndex).pack();
		}							

		col0.pack();
		col1.pack();
		col2.pack();
		col3.pack();
		col4.pack();
		col5.pack();
		col6.pack();

		venueflag = true;
		return VenueResult;
	}

	/**
	 * Description: Creates a sortable table that is populated with items of the event's optimized item list
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table OptimizedTable() {
		Vector<Item> item_list = cevent.getitem_list();

		//Checks if there is any entry before creating the table
		if (item_list.isEmpty()) {
			budgetflag = false;	
			return null;
		}


		BudgetResult = new Table(Budgetcomp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);		
		BudgetResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		BudgetResult.setHeaderVisible(true);

		TableColumn col0 = new TableColumn(BudgetResult, SWT.LEFT);
		TableColumn col1 = new TableColumn(BudgetResult, SWT.LEFT);
		TableColumn col2 = new TableColumn(BudgetResult, SWT.NULL);
		TableColumn col3 = new TableColumn(BudgetResult, SWT.NULL);
		TableColumn col4 = new TableColumn(BudgetResult, SWT.NULL);

		col0.setText("No.");
		col1.setText("Item Name");
		col2.setText("Price");
		col3.setText("Satisfaction");
		col4.setText("Type");

		col0.setResizable(false);
		col1.setResizable(false);
		col2.setResizable(false);
		col3.setResizable(false);
		col4.setResizable(false);

		col0.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = BudgetResult.getItems();
				for (int i = 1; i<items.length; i++) {
					int value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
					for(int j=0; j < i; j++) {
						int value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
						if(value1 - value2 < 0) {
							System.out.println(value1 + " " + value2);
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
							item.setText(values);
							items = BudgetResult.getItems();
							break;
						}
					}
				}
			}
		});

		//Sorting algorithm of each column
		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = BudgetResult.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				for (int i = 1; i<items.length; i++) {
					String value1 = items[i].getText(1);
					for(int j=0; j < i; j++) {
						String value2 = items[j].getText(1);
						if(collator.compare(value1, value2) < 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
							item.setText(values);
							items = BudgetResult.getItems();
							break;
						}
					}
				}
			}
		});

		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort price
				TableItem[] items = BudgetResult.getItems();
				for (int i = 1; i<items.length; i++) {
					double value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
					for(int j=0; j < i; j++) {
						double value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
						if((value1 - value2) > 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
							item.setText(values);
							items = BudgetResult.getItems();
							break;
						}
					}
				}
			}
		});

		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort satisfaction
				TableItem[] items = BudgetResult.getItems();
				for (int i = 1; i<items.length; i++) {
					int value1 = Integer.parseInt(items[i].getText(3));
					for(int j=0; j < i; j++) {
						int value2 = Integer.parseInt(items[j].getText(3));
						if((value1 - value2) > 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
							item.setText(values);
							items = BudgetResult.getItems();
							break;
						}
					}
				}
			}
		});

		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = BudgetResult.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				for (int i = 1; i<items.length; i++) {
					String value1 = items[i].getText(4);
					for(int j=0; j < i; j++) {
						String value2 = items[j].getText(4);
						if(collator.compare(value1, value2) < 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(BudgetResult, SWT.NONE, j);
							item.setText(values);
							items = BudgetResult.getItems();
							break;
						}
					}
				}
			}
		});

		BudgetResult.removeAll();
		for (int loopIndex = 0; loopIndex < item_list.size(); loopIndex++) {
			TableItem item = new TableItem(BudgetResult, SWT.NULL);
			item.setText(0, "Item " + (loopIndex+1));
			item.setText(1, item_list.get(loopIndex).getItem());
			item.setText(2, "$"+((double) item_list.get(loopIndex).getPrice())/100);
			if(item_list.get(loopIndex).getSatisfaction_value() == -1)
				item.setText(3, "");
			else
				item.setText(3, ""+item_list.get(loopIndex).getSatisfaction_value());
			if(item_list.get(loopIndex).getType() == null)
				item.setText(4, "");
			else
				item.setText(4, ""+item_list.get(loopIndex).getType());					
		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			BudgetResult.getColumn(loopIndex).pack();
		}							

		/*	col0.setWidth(60);
		col1.setWidth(300);
		col2.setWidth(50);
		col3.setWidth(50);
		col4.setWidth(50);*/
		col0.pack();
		col1.pack();
		col2.pack();
		col3.pack();
		col4.pack();

		budgetflag = true;
		return BudgetResult;
	}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
