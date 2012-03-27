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
	private static Table BudgetResult, VenueResult;
	private static boolean budgetflag, venueflag;
	private static Eventitem cevent;
	private static Composite Budgetcomp, Bookvenuecomp;
	private static Label Edescription, Startdate, Starttime, Enddate, Endtime, Ename;
	private static ScrolledComposite sc1;
	private static Composite maincomp;
	private static int budgetTableSelectedIndex;
	private static ControllerBudget bc;
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
		/*
		Epartedit.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				ViewMain.
			}
		});*/
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
		Startdate.setText(cevent.getStartDateTime().getDateRepresentation());

		Starttime = new Label(EparticularsComp, SWT.NONE);
		Starttime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Starttime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Starttime.setText(cevent.getStartDateTime().getTimeRepresentation()+"HRS");

		Label Enddntlabel = new Label(EparticularsComp, SWT.NONE);
		Enddntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enddntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Enddntlabel.setText("End Date and Time:");

		Enddate = new Label(EparticularsComp, SWT.NONE);
		Enddate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Enddate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Enddate.setText(cevent.getEndDateTime().getDateRepresentation());

		Endtime = new Label(EparticularsComp, SWT.NONE);
		Endtime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Endtime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Endtime.setText(cevent.getEndDateTime().getTimeRepresentation()+"HRS");

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
		GridData gd_dummy = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
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
				ViewMain.EventFlow();
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
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ParticipantList(cevent);
			}
		});
		GridData gd_btnEdit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnEdit.widthHint = 85;
		btnEdit.setLayoutData(gd_btnEdit);
		btnEdit.setText("Edit");
		/**********************************************************************************************
		 * END OF PARTICIPANT LIST
		 *********************************************************************************************/
		//Divider 5
		Label divider5 = createdivider(maincomp, Plistcomp);
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
		Button Advertemail = new Button(Advertcomp, SWT.NONE);
		Advertemail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.EmailAds(cevent);
			}
		});
		GridData gd_Advertedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Advertedit.widthHint = 85;
		Advertemail.setLayoutData(gd_Advertedit);
		Advertemail.setText("E-Mail");

		new Label(Advertcomp, SWT.NONE);
		new Label(Advertcomp, SWT.NONE);
		Button Advertfb = new Button(Advertcomp, SWT.NONE);
		Advertfb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.FaceBookAds();
			}
		});
		GridData gd_Advertfb = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Advertfb.widthHint = 85;
		Advertfb.setLayoutData(gd_Advertfb);
		Advertfb.setText("Facebook");
		new Label(Advertcomp, SWT.NONE);
		new Label(Advertcomp, SWT.NONE);
		
		Button AdvertSMS = new Button(Advertcomp, SWT.NONE);
		AdvertSMS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.SMSAds(cevent);
			}
		});
		GridData gd_AdvertSMS = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_AdvertSMS.widthHint = 85;
		AdvertSMS.setLayoutData(gd_AdvertSMS);
		AdvertSMS.setText("SMS");
		new Label(Advertcomp, SWT.NONE);
		new Label(Advertcomp, SWT.NONE);
		new Label(Advertcomp, SWT.NONE);
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
				deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", cevent.getName());
				if ((Integer) confirm.open() == 1) {
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
		Bookvenuecomp.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = Bookvenuecomp.getClientArea();
				Point preferredSize = VenueResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2*VenueResult.getBorderWidth();
				if (preferredSize.y > area.height + VenueResult.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = VenueResult.getVerticalBar().getSize();
					width -= vBarSize.x;

				}
				Point oldSize = VenueResult.getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns 
					// smaller first and then resize the table to
					// match the client area width
					col0.setWidth(width/5-10);
					col1.setWidth((width - col0.getWidth())/6-10);
					col3.setWidth((width - col0.getWidth())/6+4);
					col4.setWidth((width - col0.getWidth())/6-4);
					col5.setWidth((width - col0.getWidth())/6+4);
					col6.setWidth((width - col0.getWidth())/6-4);
					col2.setWidth(width - col0.getWidth() - col1.getWidth() - col3.getWidth() - col4.getWidth() -col5.getWidth() -col6.getWidth()-10);
					//    System.out.println("width "+ width);
					//BudgetResult.setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table 
					// bigger first and then make the columns wider
					// to match the client area width
					//BudgetResult.setSize(area.width, area.height);
					col0.setWidth(width/5-10);
					col1.setWidth((width - col0.getWidth())/6-10);
					col3.setWidth((width - col0.getWidth())/6+4);
					col4.setWidth((width - col0.getWidth())/6-4);
					col5.setWidth((width - col0.getWidth())/6+4);
					col6.setWidth((width - col0.getWidth())/6-4);
					col2.setWidth(width - col0.getWidth() - col1.getWidth() - col3.getWidth() - col4.getWidth() -col5.getWidth() -col6.getWidth()-10);
				}
			}
		});

		//Table Tooltip
		VenueResult.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				TableItem item = VenueResult.getItem(new Point(e.x, e.y));
				//	 BudgetResult.setToolTipText(item.getText(0));

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
		});


		venueflag = true;
		return VenueResult;
	}

	/**
	 * Description: Creates a sortable table that is populated with items of the event's optimized item list
	 * @return A table is returned if there are entries in the list, else null is returned
	 */
	public static Table OptimizedTable() {
		final Vector<Item> item_list = cevent.getitem_list();

		//Checks if there is any entry before creating the table
		if (item_list.isEmpty()) {
			budgetflag = false;	
			return null;
		}


		BudgetResult = new Table(Budgetcomp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);		
		BudgetResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		BudgetResult.setHeaderVisible(true);

		final TableColumn col0 = new TableColumn(BudgetResult, SWT.LEFT);
		final TableColumn col1 = new TableColumn(BudgetResult, SWT.LEFT);
		final TableColumn col2 = new TableColumn(BudgetResult, SWT.NULL);
		final TableColumn col3 = new TableColumn(BudgetResult, SWT.NULL);
		final TableColumn col4 = new TableColumn(BudgetResult, SWT.NULL);
		final TableColumn col5 = new TableColumn(BudgetResult, SWT.NULL);

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
				sortColumn(4, item_list);
			}
		});
		refreshBudgetTable(item_list);


		//Column Resize with table fix
		Budgetcomp.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = Budgetcomp.getClientArea();
				Point preferredSize = BudgetResult.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2*BudgetResult.getBorderWidth();
				if (preferredSize.y > area.height + BudgetResult.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = BudgetResult.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = BudgetResult.getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns 
					// smaller first and then resize the table to
					// match the client area width
					col1.setWidth(width/2-50);
					col0.setWidth((width - col1.getWidth())/5);
					col2.setWidth((width - col1.getWidth())/5);
					col3.setWidth((width - col1.getWidth())/4);
					col4.setWidth((width - col1.getWidth())/5);
					col5.setWidth(width - col0.getWidth() - col1.getWidth() - col2.getWidth() - col3.getWidth() -col4.getWidth() + 30);
					//BudgetResult.setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table 
					// bigger first and then make the columns wider
					// to match the client area width
					//BudgetResult.setSize(area.width, area.height);
					col1.setWidth(width/2-50);
					col0.setWidth((width - col1.getWidth())/5);
					col2.setWidth((width - col1.getWidth())/5);
					col3.setWidth((width - col1.getWidth())/4);
					col4.setWidth((width - col1.getWidth())/5 + 10);
					col5.setWidth(width - col0.getWidth() - col1.getWidth() - col2.getWidth() - col3.getWidth() -col4.getWidth() + 30);

				}
			}
		});

		//Table Tooltip
		BudgetResult.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				TableItem item = BudgetResult.getItem(new Point(e.x, e.y));
				//	 BudgetResult.setToolTipText(item.getText(0));

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
				deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", tb.getText(1));
				if ((Integer) confirm.open() == 1) {
					deleteBudgetItem(item_list);
				}
			}
		});
		mntmDeleteEvent.setText("Delete Item");

				budgetflag = true;
		return BudgetResult;
	}

	public static void deleteBudgetItem(Vector<Item> item_list) {

		bc = new ControllerBudget();
		bc.deleteBudgetItem(cevent.getID(), item_list.get(budgetTableSelectedIndex).getID());
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
			if(item_list.get(loopIndex).getSatisfaction_value() == -1)
				item.setText(3, "");
			else
				item.setText(3, ""+item_list.get(loopIndex).getSatisfaction_value());
			if(item_list.get(loopIndex).getType() == null)
				item.setText(4, "");
			else
				item.setText(4, ""+item_list.get(loopIndex).getType());					
			item.setText(5, ""+item_list.get(loopIndex).getQuantity());
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
