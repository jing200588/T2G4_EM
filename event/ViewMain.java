package event;

import budget.*;
import participant.*;
import emdb.*;
import emserver.AppContextBuilder;
import emserver.EMSService;
import emserver.ViewServer;
import advertise.sms.*;
import advertise.facebook.*;
import advertise.email.*;
import dialog.*;
import program.*;
import venue.*;


import com.ibm.icu.text.Collator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;



public class ViewMain extends ApplicationWindow {
	private static enum EVENT_ITEM_SORT_CRITERIA {NAME, STARTDATETIME}
	
	private ModelEvent mm = new ModelEvent();
	private Action exitAction;
	private Action serverControl;
	private static Composite mainComposite, leftComp, rightComp;
	private static Table eventListTable, expiredTable;
	private static Vector<EventItem> eventList, expiredEventList;
	private static TableColumn eventTC1, eventTC2;
    private static TableColumn expiredEventTC1, expiredEventTC2;
    private static TableViewerColumn eventTVC1, eventTVC2;
    private static TableViewerColumn expiredEventTVC1, expiredEventTVC2;
    private static TableViewer tableViewerEventList, tableViewerExpiredEventList;
	private static StackLayout layout = new StackLayout();
    private static ViewEvent viewPage;
    private static ViewHomepage homePage;
    private static EMDBII db;
   
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
    private final EMSService server;
    private Table table;
    
	/**
	 * Create the application window.
	 */
	public ViewMain() {

		super(null);
		setShellStyle(SWT.CLOSE | SWT.MIN);
		
		eventList = new Vector<EventItem>();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();

		
		WebAppContext context = new WebAppContext();
		context.setDescriptor(context + "/WEB-INF/web.xml");
		context.setResourceBase(".");
		context.setContextPath("/");

	    ContextHandlerCollection contexts = new ContextHandlerCollection();
	    Handler[] handleSet = new Handler[1];
	    handleSet[0] = new AppContextBuilder().build();
		contexts.setHandlers(handleSet);
		
		server = new EMSService();
	    server.setHandler(contexts);

		
		
	}
	
	

	/************************************************************
	 * DELETE ITEM
	 ***********************************************************/
	/**
	 * Description: Deletes the selected item from the database and set the current page to homePage
	 */
	public static void DeleteItem() {
		//eventListTable.remove(eventListTable.getSelectionIndices());
		tableViewerEventList.refresh();
		Homepage();
	}
	
	/************************************************************
	 * DELETE EXPIRED ITEM
	 ***********************************************************/
	/**
	 * Description: Deletes the selected expired item from the database and set the current page to homePage
	 */
	public static void DeleteExpiredItem() {
		expiredTable.remove(expiredTable.getSelectionIndices());
		Homepage();
	}
	
	/************************************************************
	 * DELETE ALL EXPIRED ITEM
	 ***********************************************************/
	/**
	 * Description: Deletes all expired event items from the database and set the current page to homePage
	 */
	public static void DeleteAllExpiredItems() {
		expiredTable.removeAll();
		Homepage();
	}
	
	/************************************************************
	 * UPDATE TABLE
	 ***********************************************************/
	/**
	 * Description: Pulls the list from ModelEvent and updates the eventListTable in leftComp
	 */
	public static void UpdateTable () {
	/*	eventList = ModelEvent.PullList();

		TableItem item;
		int i;
		if (eventListTable.getItemCount() == 0)	//adds whole vector if table empty 
			i=0;
		
		else if (eventListTable.getItemCount() == eventList.size()) {						//updates vector list (done when returning view)
			item = eventListTable.getItem(eventListTable.getSelectionIndex());						//Gets current selected row in table
			item.setText(0, eventList.get(eventListTable.getSelectionIndex()).getName());	//Replace the String with new updated string 
			layout.topControl = viewPage;
			rightComp.layout(true);
			return;
		}
		else
			i = eventList.size()-1;		//add the last item into table
		
		for (; i<eventList.size(); i++) {			
			item = new TableItem(eventListTable,SWT.NONE);
			item.setText(eventList.get(i).getName());
			item.setText(1,eventList.get(i).getStartDateTime().getDateRepresentation());
		}*/
		tableViewerEventList.refresh();
	}
	
	/************************************************************
	 * UPDATE EXPIRED TABLE
	 ***********************************************************/
	/**
	 * Description: Pulls the expired list from ModelEvent and updates the expiredEventTable in leftComp
	 */
/*	public static void UpdateExpiredTable() {
		expiredEventList = ModelEvent.PullExpiredList();

		TableItem item;
		int i;
		if (expiredTable.getItemCount() == 0)	//adds whole vector if table empty 
			i=0;

		else
			i = expiredTable.getItemCount() + 1;		//add the last item into table
		
		for (; i<expiredEventList.size(); i++) {			
			item = new TableItem(expiredTable,SWT.NONE);
			item.setText(expiredEventList.get(i).getName());
			item.setText(1,expiredEventList.get(i).getEndDateTime().getDateRepresentation());
		}
	}
	*/
	/************************************************************
	 * SHIFT EXPIRED
	 ***********************************************************/
	/**
	 * Description: Checks the eventList for expired events and updates the eventList, expiredEventlists and database. 
	 * It will then update the eventListTable and expiredListTable by calling their respective update functions.
	 */
	public static void ShiftExpired() {
	//	expiredEventlist = ModelEvent.PullExpiredList();
		int prevExpIndex = expiredEventList.size();
		
		for (int i=0; i<eventList.size(); i++) {
			if (eventList.get(i).isExpired()) {
				expiredEventList.add(eventList.get(i));
			}
		}
		
		List<EventItem> subList = new Vector<EventItem>(expiredEventList.subList(prevExpIndex, expiredEventList.size()));
		ModelEvent.UpdateExpiredList(subList);

	/*	eventListTable.removeAll();
		expiredTable.removeAll();
		UpdateTable();
		UpdateExpiredTable();
		System.out.println("RAN");
		*/
		while (!subList.isEmpty()) {
			eventList.remove(subList.get(0));
			subList.remove(0);
		}
		tableViewerEventList.refresh();
		tableViewerExpiredEventList.refresh();
	}
	
	/************************************************************
	 * CHECK EXPIRY
	 ***********************************************************/
	/**
	 * Description: Checks the individual events in the eventList if it is past the current time (expired). If it is, it will call
	 * the ShiftExpired() function.
	 */
	public static void CheckExpiry() {

    	MyDateTime currentDT = MyDateTime.getCurrentDateTime();
    	boolean flag = false;
    	for (int i=0; i<eventList.size(); i++) {
    		if (eventList.get(i).getEndDateTime().compareTo(currentDT) <= 0) {
    			eventList.get(i).setIsExpired(true);
    			flag = true;
    		}
    	}
    	if (flag)
    		ShiftExpired();
	}
	
	/************************************************************
	 * HOMEPAGE
	 ***********************************************************/
	/**
	 * Description: Initialize ViewHomepage and set the page of rightComp to ViewHomepage
	 */
	public static void Homepage () {		
		homePage = new ViewHomepage(rightComp, SWT.NONE);
		layout.topControl = homePage;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * CALCULATE BUDGET
	 ***********************************************************/
	/**
	 * Description: Initialize ViewBudget and set the page of rightComp to ViewBudget
	 */
	public static void CalcBudget () {		
		ViewBudget bv = new ViewBudget(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = bv;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * BOOK VENUE
	 ***********************************************************/
	/**
	 * Description: Initialize ViewBookingSystem and set the page of rightComp to ViewBookingSystem
	 */
	public static void BookVenue() {
		ViewBookingSystem bookgui = new ViewBookingSystem(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = bookgui;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * EVENT PARTICULARS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEventParticulars and set the page of rightComp to ViewEventParticulars
	 */
	public static void EventParticulars() {
		ViewEditEventParticulars ep = new ViewEditEventParticulars(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = ep;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * Program Flow
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEventFlow and set the page of rightComp to ViewEventFlow
	 */
	public static void EventFlow () {		
		ViewEventFlow ef = new ViewEventFlow(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = ef;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * PARTICIPANT LIST
	 ***********************************************************/
	/**
	 * Description: Initialize ViewParticipantList and set the page of rightComp to ViewParticipantList
	 */
	public static void ParticipantList() {
		ViewParticipantList pl = new ViewParticipantList(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = pl;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * EMAIL ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEmailAds and set the page of rightComp to ViewEmailAds
	 * @param aName - Username
	 * @param aDomain - NUSSTU or NUSSTF 
	 * @param aPass - Password
	 */
	public static void EmailAds(String aName, String aDomain, String aPass) {
		ViewEmailAds ea = new ViewEmailAds(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()), aName, aDomain, aPass);
		layout.topControl = ea;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * FACEBOOK ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewFaceBookAds and set the page of rightComp to ViewFaceBookAds
	 */
	public static void FaceBookAds() {
		ViewFaceBookAds fba = new ViewFaceBookAds(rightComp);
		layout.topControl = fba;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * SMS ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewSmsAds and set the page of rightComp to ViewSmsAds
	 */
	public static void SMSAds() {
		ViewSmsAds smsa = new ViewSmsAds(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = smsa;
		rightComp.layout(true);
	}
	
	/************************************************************
	 * RETURN VIEW
	 ***********************************************************/
	/**
	 * Description: Sets the page of rightComp to ViewEvent
	 */
	public static void ReturnView() {
		viewPage = new ViewEvent(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		layout.topControl = viewPage;
		rightComp.layout(true);
		
	}
	
	/**
	 * Description: Create contents of the application window.
	 * @param parent
	 */
	protected Control createContents(Composite parent) {
		setStatus("Welcome!");
		ScrolledComposite container = new ScrolledComposite(parent, SWT.V_SCROLL);
		
		container.setExpandHorizontal(true);
		container.setExpandVertical(true);
		container.setEnabled(true);
		container.setLayout(null);
		{
			mainComposite = new Composite(container, SWT.NONE);
			mainComposite.setBounds(0, 0, 400, 415);
			mainComposite.setSize(container.getSize());
			mainComposite.setLayout(new GridLayout(3, false));
			
			leftComp = new Composite(mainComposite, SWT.NONE);
			leftComp.setLayout(new GridLayout(1, false));
			GridData gd_c1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_c1.heightHint = 415;
			gd_c1.widthHint = 300;
			
			gd_c1.grabExcessVerticalSpace = true;
			gd_c1.verticalAlignment = SWT.FILL;
			gd_c1.horizontalAlignment = SWT.FILL;
			
			leftComp.setLayoutData(gd_c1);
			formToolkit.adapt(leftComp);
			formToolkit.paintBordersFor(leftComp);
			leftComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			
			/************************************************************
			 * CREATE EVENT BUTTON EVENT LISTENER
			 ***********************************************************/
			Button btnCreateEvent = formToolkit.createButton(leftComp, "Create Event", SWT.NONE);
			btnCreateEvent.setForeground(SWTResourceManager.getColor(0, 0, 0));
			btnCreateEvent.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
			btnCreateEvent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			btnCreateEvent.setFont(SWTResourceManager.getFont("Maiandra GD", 16, SWT.NORMAL));
			btnCreateEvent.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					
					//dispose of all children that currently is in c2
					if (rightComp != null && !rightComp.isDisposed()) {
					Object[] children = rightComp.getChildren();
					for (int i=0; i<children.length; i++)
						((Composite)children[i]).dispose();
					}
					
					ViewCreateEvent CreatePage = new ViewCreateEvent(rightComp, SWT.NONE);
					layout.topControl = CreatePage;
					rightComp.layout(true);	//refreshes c2
					
				}
			});
			GridData gd_btnCreateEvent = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
			gd_btnCreateEvent.widthHint = 294;
			btnCreateEvent.setLayoutData(gd_btnCreateEvent);
			
			//Column Resize with table fix
/*			 leftComp.addControlListener(new ControlAdapter() {
				    public void controlResized(ControlEvent e) {
				      Rectangle area = leftComp.getClientArea();
				      Point preferredSize = eventListTable.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				      int width = area.width - 2*eventListTable.getBorderWidth();
				      
				      if (preferredSize.y > area.height + eventListTable.getHeaderHeight()) {
				        // Subtract the scrollbar width from the total column width
				        // if a vertical scrollbar will be required
				        Point vBarSize = eventListTable.getVerticalBar().getSize();
				        width -= vBarSize.x;
				      }
				      eventTC1.setWidth(width/3*2);
				      eventTC2.setWidth((width - eventTC1.getWidth()) -24);        
				      expiredEventTC1.setWidth(width/3*2);
				      expiredEventTC2.setWidth((width - expiredEventTC1.getWidth()) -24);  

				    }
				    
			 });
			
	*/		
			TabFolder tabFolder = new TabFolder(leftComp, SWT.NONE);
			tabFolder.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
			GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
			gd_tabFolder.widthHint = 284;
			tabFolder.setLayoutData(gd_tabFolder);
			formToolkit.adapt(tabFolder);
			formToolkit.paintBordersFor(tabFolder);
			
			TabItem tbtmUpcomingEvents = new TabItem(tabFolder, SWT.NONE);
			tbtmUpcomingEvents.setText("    Upcoming Events     ");
			
			//Event List Table
			eventList = ModelEvent.PullList();
			expiredEventList = ModelEvent.PullExpiredList();
			
			Composite tableComposite = new Composite(tabFolder, SWT.NONE);
			TableColumnLayout tcl_tableComposite = new TableColumnLayout();
			tableComposite.setLayout(tcl_tableComposite);
			tbtmUpcomingEvents.setControl(tableComposite);
			
			tableViewerEventList = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
			eventListTable = tableViewerEventList.getTable();
			eventListTable.setTouchEnabled(true);
			eventListTable.getHorizontalBar().setVisible(true);
			eventListTable.getHorizontalBar().setEnabled(false);
			eventListTable.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
			
			eventTVC1 = new TableViewerColumn(tableViewerEventList, SWT.LEFT);
			eventTVC2 = new TableViewerColumn(tableViewerEventList,SWT.CENTER);
			eventTVC1.getColumn().setText("Event Title");
		    eventTVC2.getColumn().setText("Start Date");
			
		    tcl_tableComposite.setColumnData(eventTVC1.getColumn(), new ColumnWeightData(50));
			tcl_tableComposite.setColumnData(eventTVC2.getColumn(), new ColumnWeightData(30));

			//Populating the table.
			tableViewerEventList.setContentProvider(ArrayContentProvider.getInstance());
			eventTVC1.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element)
				{			
					EventItem eItem = (EventItem) element;
					return eItem.getName();
				}
				
			});
			
			eventTVC2.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element)
				{			
					EventItem eItem = (EventItem) element;
					return eItem.getStartDateTime().getDateRepresentation();
				}
				
			});
			
			eventTVC1.getColumn().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sortEventItemList(eventList, EVENT_ITEM_SORT_CRITERIA.NAME);
					tableViewerEventList.refresh();
				}
			});
			
			eventTVC2.getColumn().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sortEventItemList(eventList, EVENT_ITEM_SORT_CRITERIA.STARTDATETIME);
					tableViewerEventList.refresh();
				}
			});
			
			tableViewerEventList.setInput(eventList);
	
			//Event List Table MouseOver Tooltip
			eventListTable.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = eventListTable.getItem(new Point(e.x, e.y));
					 if (item != null)
						 eventListTable.setToolTipText(item.getText(0));
				}
			});
			
			
			/************************************************************
			 * EVENT LIST TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
			eventListTable.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in rightComp
				if (rightComp != null && !rightComp.isDisposed()) {
				Object[] children = rightComp.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				viewPage = new ViewEvent(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		        layout.topControl = viewPage;
				rightComp.layout(true);
		     }
			});
			
			Menu eventListTableMenu = new Menu(eventListTable);
			eventListTable.setMenu(eventListTableMenu);
			
			/************************************************************
			 * DELETE EVENT LISTENER (EVENT LIST TABLE)
			 ***********************************************************/
			
			MenuItem mntmDeleteEvent = new MenuItem(eventListTableMenu, SWT.PUSH);
			mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						TableItem tb = eventListTable.getItem(eventListTable.getSelectionIndex());
						DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(0));
						if ((Integer) confirm.open() == 1) {
							ModelEvent.DeleteEvent(eventList.get(eventListTable.getSelectionIndex()));	//Finds the selected event and deletes it from vector
							DeleteItem();
						}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
				
			});
			mntmDeleteEvent.setText("Delete Event");
		
			formToolkit.adapt(tableComposite);
			formToolkit.paintBordersFor(tableComposite);
			eventListTable.setHeaderVisible(true);
			eventListTable.setLinesVisible(false);
		
			//old table code
/*			eventListTable = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
			eventListTable.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
			tbtmUpcomingEvents.setControl(eventListTable);
			//Event List Table tooltip
			eventListTable.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = eventListTable.getItem(new Point(e.x, e.y));
					 if (item != null)
						 eventListTable.setToolTipText(item.getText(0));
				}
			});
			eventListTable.setToolTipText("");
			eventListTable.setTouchEnabled(true);
			
			/************************************************************
			 * EVENT LIST TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
/*			eventListTable.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in rightComp
				if (rightComp != null && !rightComp.isDisposed()) {
				Object[] children = rightComp.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				viewPage = new ViewEvent(rightComp, SWT.NONE, eventList.get(eventListTable.getSelectionIndex()));
		        layout.topControl = viewPage;
				rightComp.layout(true);
		     }
			});
			eventTC1 = new TableColumn(eventListTable, SWT.LEFT);
			eventTC2 = new TableColumn(eventListTable,SWT.CENTER);
			
		    eventTC1.setText("Event Title");
		    eventTC2.setText("Start Date");

		     	    eventTC1.setResizable(false);
		     	    eventTC2.setResizable(false);

		    //Sorting Algorithm for Event List Table 	    
			eventTC1.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					//sort name
					TableItem[] items = eventListTable.getItems();
					Collator collator = Collator.getInstance(Locale.getDefault());
					for (int i = 1; i<items.length; i++) {
						String value1 = items[i].getText(0);
						for(int j=0; j < i; j++) {
							String value2 = items[j].getText(0);
							if(collator.compare(value1, value2) < 0) {
								String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)};
								items[i].dispose();
								TableItem item = new TableItem(eventListTable, SWT.NONE, j);	//j could be the final index
								item.setText(values);
								items = eventListTable.getItems();
								break;
							}
						}
					}
				}
			});
			
			eventListTable.getHorizontalBar().setVisible(true);
			eventListTable.getHorizontalBar().setEnabled(false);
			formToolkit.adapt(eventListTable);
			formToolkit.paintBordersFor(eventListTable);
			eventListTable.setHeaderVisible(true);
			eventListTable.setLinesVisible(false);
			UpdateTable();
			Menu eventListTableMenu = new Menu(eventListTable);
			eventListTable.setMenu(eventListTableMenu);
			
			/************************************************************
			 * DELETE EVENT LISTENER (EVENT LIST TABLE)
			 ***********************************************************/
	/*		
			MenuItem mntmDeleteEvent = new MenuItem(eventListTableMenu, SWT.PUSH);
			mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						TableItem tb = eventListTable.getItem(eventListTable.getSelectionIndex());
						DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(0));
						if ((Integer) confirm.open() == 1) {
							ModelEvent.DeleteEvent(eventList.get(eventListTable.getSelectionIndex()));	//Finds the selected event and deletes it from vector
							DeleteItem();
						}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
				
			});
			mntmDeleteEvent.setText("Delete Event");
*/
			TabItem tbtmPastEvents = new TabItem(tabFolder, SWT.NONE);
			tbtmPastEvents.setText("        Past Events        ");
			
			//Expired Event Table 
			Composite expiredTableComposite = new Composite(tabFolder, SWT.NONE);
			TableColumnLayout tcl_expiredTableComposite = new TableColumnLayout();
			expiredTableComposite.setLayout(tcl_expiredTableComposite);
			tbtmPastEvents.setControl(expiredTableComposite);
			
			tableViewerExpiredEventList = new TableViewer(expiredTableComposite, SWT.BORDER | SWT.FULL_SELECTION);
			expiredTable = tableViewerExpiredEventList.getTable();
			expiredTable.setHeaderVisible(true);
			expiredTable.getHorizontalBar().setVisible(true);
			expiredTable.getHorizontalBar().setEnabled(false);
			expiredTable.setLinesVisible(false);
			expiredTable.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
			
			expiredEventTVC1 = new TableViewerColumn(tableViewerExpiredEventList, SWT.LEFT);
			expiredEventTVC2 = new TableViewerColumn(tableViewerExpiredEventList,SWT.CENTER);
			expiredEventTVC1.getColumn().setText("Event Title");
		    expiredEventTVC2.getColumn().setText("End Date");
			
		    tcl_expiredTableComposite.setColumnData(expiredEventTVC1.getColumn(), new ColumnWeightData(50));
			tcl_expiredTableComposite.setColumnData(expiredEventTVC2.getColumn(), new ColumnWeightData(30));

			//Populating the table.
			tableViewerExpiredEventList.setContentProvider(ArrayContentProvider.getInstance());
			expiredEventTVC1.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element)
				{			
					EventItem eItem = (EventItem) element;
					return eItem.getName();
				}
				
			});
			
			expiredEventTVC2.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element)
				{			
					EventItem eItem = (EventItem) element;
					return eItem.getEndDateTime().getDateRepresentation();
				}
				
			});
			
			expiredEventTVC1.getColumn().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sortEventItemList(expiredEventList, EVENT_ITEM_SORT_CRITERIA.NAME);
					tableViewerExpiredEventList.refresh();
				}
			});
			
			expiredEventTVC2.getColumn().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sortEventItemList(expiredEventList, EVENT_ITEM_SORT_CRITERIA.STARTDATETIME);
					tableViewerExpiredEventList.refresh();
				}
			});
			
			tableViewerExpiredEventList.setInput(expiredEventList);
	
			//Event List Table MouseOver Tooltip
			expiredTable.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = expiredTable.getItem(new Point(e.x, e.y));
					 if (item != null)
						 expiredTable.setToolTipText(item.getText(0));
				}
			});
			
			
			/************************************************************
			 * EXPIRED EVENT LIST TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
			expiredTable.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in c2
				if (rightComp != null && !rightComp.isDisposed()) {
				Object[] children = rightComp.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				viewPage = new ViewEvent(rightComp, SWT.NONE, expiredEventList.get(expiredTable.getSelectionIndex()));
		        layout.topControl = viewPage;
				rightComp.layout(true);
		     }
			});
			
			Menu expiredEventListTableMenu = new Menu(expiredTable);
			expiredTable.setMenu(expiredEventListTableMenu);
			/************************************************************
			 * DELETE EVENT LISTENER (EXPIRED EVENT LIST TABLE)
			 ***********************************************************/
			
			MenuItem mntmDeletePastEvent = new MenuItem(expiredEventListTableMenu, SWT.PUSH);
			mntmDeletePastEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
					TableItem tb = expiredTable.getItem(expiredTable.getSelectionIndex());
					DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(0));
					if ((Integer) confirm.open() == 1) {
						ModelEvent.DeleteExpiredEvent(expiredEventList.get(expiredTable.getSelectionIndex()));
						DeleteExpiredItem();
					}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
			});
			mntmDeletePastEvent.setText("Delete Event");
			
			/************************************************************
			 * DELETE ALL EVENT LISTENER (EXPIRED EVENT LIST TABLE)
			 ***********************************************************/
			MenuItem mntmDeleteAllPastEvent = new MenuItem(expiredEventListTableMenu, SWT.PUSH);
			mntmDeleteAllPastEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
					DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", "ALL past events");
					if ((Integer) confirm.open() == 1) {
						ModelEvent.DeleteAllExpiredEvents();
						DeleteAllExpiredItems();
					}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
			});
			mntmDeleteAllPastEvent.setText("Delete All Past Events");
			
			//checks for expired events
			CheckExpiry();
			
			
			
			
			//old Expired event list table
/*			expiredTable = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
			expiredTable.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
			tbtmPastEvents.setControl(expiredTable);			
			//Expired event list table tooltip
			expiredTable.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = expiredTable.getItem(new Point(e.x, e.y));
					 if (item != null)
						 expiredTable.setToolTipText(item.getText(0));
				}
			});
			expiredTable.setToolTipText("");
			expiredTable.setTouchEnabled(true);
			
			/************************************************************
			 * EXPIRED EVENT LIST TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
/*			expiredTable.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in c2
				if (rightComp != null && !rightComp.isDisposed()) {
				Object[] children = rightComp.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				viewPage = new ViewEvent(rightComp, SWT.NONE, expiredEventList.get(expiredTable.getSelectionIndex()));
		        layout.topControl = viewPage;
				rightComp.layout(true);
		     }
			});
			expiredEventTC1 = new TableColumn(expiredTable, SWT.LEFT);
			expiredEventTC2 = new TableColumn(expiredTable,SWT.CENTER);
		    expiredEventTC1.setText("Event Title");
		    expiredEventTC2.setText("End Date");

    	    expiredEventTC1.pack();
     	    expiredEventTC2.pack();
   	    
     	    expiredEventTC1.setResizable(false);
     	    expiredEventTC2.setResizable(false);
     	    
		    //Sorting algo for expired event list table
			expiredEventTC1.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					//sort name
					TableItem[] items = expiredTable.getItems();
					Collator collator = Collator.getInstance(Locale.getDefault());
					for (int i = 1; i<items.length; i++) {
						String value1 = items[i].getText(0);
						for(int j=0; j < i; j++) {
							String value2 = items[j].getText(0);
							if(collator.compare(value1, value2) < 0) {
								String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)};
								items[i].dispose();
								TableItem item = new TableItem(expiredTable, SWT.NONE, j);	//j could be the final index
								item.setText(values);
								items = expiredTable.getItems();
								break;
							}
						}
					}
				}
			});
			
			expiredTable.getHorizontalBar().setVisible(true);
			expiredTable.getHorizontalBar().setEnabled(false);
			formToolkit.adapt(expiredTable);
			formToolkit.paintBordersFor(expiredTable);
			expiredTable.setHeaderVisible(true);
			expiredTable.setLinesVisible(false);
			
			UpdateExpiredTable();
			CheckExpiry();
			Menu expiredEventListTableMenu = new Menu(expiredTable);
			expiredTable.setMenu(expiredEventListTableMenu);
			
			/************************************************************
			 * DELETE EVENT LISTENER (EXPIRED EVENT LIST TABLE)
			 ***********************************************************/
/*			
			MenuItem mntmDeletePastEvent = new MenuItem(expiredEventListTableMenu, SWT.PUSH);
			mntmDeletePastEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
					TableItem tb = expiredTable.getItem(expiredTable.getSelectionIndex());
					DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", tb.getText(0));
					if ((Integer) confirm.open() == 1) {
						ModelEvent.DeleteExpiredEvent(expiredEventList.get(expiredTable.getSelectionIndex()));
						DeleteExpiredItem();
					}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
			});
			mntmDeletePastEvent.setText("Delete Event");
			
			/************************************************************
			 * DELETE ALL EVENT LISTENER (EXPIRED EVENT LIST TABLE)
			 ***********************************************************/
/*			MenuItem mntmDeleteAllPastEvent = new MenuItem(expiredEventListTableMenu, SWT.PUSH);
			mntmDeleteAllPastEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
					DeleteConfirmDialog confirm = new DeleteConfirmDialog(new Shell(), "delconfirm", "ALL past events");
					if ((Integer) confirm.open() == 1) {
						ModelEvent.DeleteAllExpiredEvents();
						DeleteAllExpiredItems();
					}
					} catch (Exception ex) {
						ErrorMessageDialog errormsg = new ErrorMessageDialog(new Shell(), "There was nothing selected!");
						errormsg.open();
					}
				}
			});
			mntmDeleteAllPastEvent.setText("Delete All Past Events");
	*/		
			//Calendar Widget
			DateTime Calender = new DateTime(leftComp, SWT.CALENDAR | SWT.LONG);
			Calender.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
			formToolkit.adapt(Calender);
			formToolkit.paintBordersFor(Calender);
			
			
			Label Vseparator = new Label(mainComposite, SWT.SEPARATOR | SWT.VERTICAL);
			GridData gd_Vseparator = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_Vseparator.heightHint = 415;
			gd_Vseparator.verticalAlignment = SWT.FILL;
			Vseparator.setLayoutData(gd_Vseparator);
			
			
			formToolkit.adapt(Vseparator, true, true);
			{
				rightComp = new Composite(mainComposite, SWT.NONE);
				rightComp.setLayout(layout);
				GridData gd_c2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_c2.heightHint = 415;
				gd_c2.widthHint = 700;
				gd_c2.grabExcessVerticalSpace = true;
				gd_c2.verticalAlignment = SWT.FILL;
				gd_c2.grabExcessHorizontalSpace = true;
				gd_c2.horizontalAlignment = SWT.FILL;
				rightComp.setLayoutData(gd_c2);
				formToolkit.adapt(rightComp);
				formToolkit.paintBordersFor(rightComp);
				homePage = new ViewHomepage(rightComp, SWT.NONE);
				layout.topControl = homePage;
			
			}
	
		}
		
		container.setContent(mainComposite);
		container.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return container;
	}

	/**
	 * Description: Create the actions for the menu bar.
	 */
	private void createActions() {
		
		//exitAction
		{
			exitAction = new Action("Exit") {					public void run() {
						System.exit(0);
					}
			};
			exitAction.setAccelerator(SWT.ALT | SWT.F4);
			exitAction.setToolTipText("Exits E-MAN");
			exitAction.addPropertyChangeListener(new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					System.exit(0);
				}
			});
		
		}
		
		//serverControl
		{
			serverControl = new Action("Start/Stop Server") {
					public void run() {
						ViewServer vs = new ViewServer(rightComp, SWT.NONE, server);
						layout.topControl = vs;
						rightComp.layout(true);
					}
			};
			serverControl.setAccelerator(SWT.ALT | SWT.F10);
			serverControl.setToolTipText("Start the Server");
		}	
	}

	/**
	 * Description: Create the menu manager.
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		{
			MenuManager FileMenu = new MenuManager("File");
			menuManager.add(FileMenu);
			FileMenu.add(exitAction);
			
			
			MenuManager ServerMenu = new MenuManager("Server");
			menuManager.add(ServerMenu);
			ServerMenu.add(serverControl);
		}
		return menuManager;
	}

	
	
	
	
	/**
	 * Description: Create the toolbar manager.
	 * @return the toolbar manager
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Description: Create the status line manager.
	 * @return the status line manager
	 */
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Description: Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {

		try {
			
			db = new EMDBII();
			db.systemCheck();

			
			ViewMain window = new ViewMain();
			window.setBlockOnOpen(true);
			window.open();
			
			
			Display.getCurrent().dispose();

		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: Configure the shell.
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		newShell.setImage(SWTResourceManager.getImage(ViewMain.class, "/Images/thumbnail.jpg"));
		newShell.setMinimumSize(new Point(1200, 526));
		super.configureShell(newShell);
		newShell.setText("E-Man");
		newShell.setSize(getInitialSize());
		newShell.setFullScreen(false);


	}

	/**
	 * Description: Return the initial size of the window.
	 */
	protected Point getInitialSize() {
		return new Point(1200, 526);
	}
	
	
	/**
	 * Handle shell close
	 */
	protected void handleShellCloseEvent() {
		try{
			server.stop();
		}catch(Exception e){}
		System.exit(0);
	}
	
	/**
	 * Sort the list of EventItem objects base on the specified criterion.
	 * @param inputList - List<EventItem>
	 * @param type - EVENT_ITEM_SORT_CRITERIA
	 */
	private void sortEventItemList(List<EventItem> inputList, EVENT_ITEM_SORT_CRITERIA type)
	{
		if(inputList == null)
			return;
		
		Comparator<EventItem> comparator = null;		// Dummy value
		switch(type)
		{
			case NAME:
				comparator = new EventItemNameComparator();
				break;
			case STARTDATETIME:
				comparator = new EventItemStartDateComparator();
		}
		
		if(comparator != null)
			Collections.sort(inputList, comparator);
	}
}