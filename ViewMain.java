import com.ibm.icu.text.Collator;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;



public class ViewMain extends ApplicationWindow {
	private Action exitaction;
	private Composite maincomposite;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Composite c1;
	private static Table table;
	private static Composite c2;
	private static Vector<Eventitem> eventlist;
	private static StackLayout layout = new StackLayout();
	private ModelEvent mm = new ModelEvent();
	private static Display display = new Display();
    private static Color red = display.getSystemColor(SWT.COLOR_RED);
    private static Color blue = display.getSystemColor(SWT.COLOR_BLUE);
    private static ViewEvent view;
    private static ViewHomepage hp;
    private static EMDBII db;
  //  private static boolean vBarOn;
    private static TableColumn tc1, tc2, tc3;
    private static TableColumn etc1, etc2, etc3;
    private static Table table_1;
    private static Vector<Eventitem> expiredlist;
  //  private static int tc2vBarOnWidth, tc2vBarOffWidth;
  //  private static int firstruncheck = 0;
    
	/**
	 * Create the application window.
	 */
	public ViewMain() {

		super(null);
		
		eventlist = new Vector<Eventitem>();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		

		
	}

	/************************************************************
	 * DELETE ITEM
	 ***********************************************************/
	/**
	 * Description: Deletes the selected item from the table and set the current page to Homepage by calling Homepage()
	 */
	public static void DeleteItem() {
		table.remove(table.getSelectionIndices());
		Homepage();
/*		
        if (vBarOn && tc2.getWidth() != tc2vBarOnWidth) {
        	System.out.println("running");
        	tc1.setWidth(tc1.getWidth());
        	tc2.setWidth(tc2.getWidth() -30);
        	tc3.setWidth(tc2.getWidth() -30);
        }
        
        else if (!vBarOn && tc2.getWidth() != tc2vBarOffWidth) {
      	  	tc1.setWidth(tc1.getWidth());
      	  	tc2.setWidth(tc2.getWidth() +30);
      	  	tc3.setWidth(tc2.getWidth() +30);
      }
  */      
	}
	
	/************************************************************
	 * Method to update the table in c1
	 ***********************************************************/
	/**
	 * Description: Pulls the list from ModelEvent and updates the event table in composite c1
	 */
	public static void UpdateTable () {
		eventlist = ModelEvent.PullList();

		TableItem item;
		int i;
		if (table.getItemCount() == 0)	//adds whole vector if table empty 
			i=0;
		
		else if (table.getItemCount() == eventlist.size()) {						//updates vector list (done when returning view)
			item = table.getItem(table.getSelectionIndex());						//Gets current selected row in table
			item.setText(0, eventlist.get(table.getSelectionIndex()).getName());	//Replace the String with new updated string 
			layout.topControl = view;
			c2.layout(true);
			return;
		}
		else
			i = eventlist.size()-1;		//add the last item into table
		
		for (; i<eventlist.size(); i++) {			
			item = new TableItem(table,SWT.NONE);
			item.setText(eventlist.get(i).getName());
			item.setText(1, "5");
			item.setBackground(1, red);
	    	item.setText(2, "X");
	    	item.setBackground(2,blue);
		}
		/*
        if (vBarOn && tc2.getWidth() != tc2vBarOnWidth) {
        	System.out.println("running");
        	tc1.setWidth(tc1.getWidth());
        	tc2.setWidth(tc2.getWidth() -30);
        	tc3.setWidth(tc2.getWidth() -30);
        }
        
        else if (!vBarOn && tc2.getWidth() != tc2vBarOffWidth) {
      	  	tc1.setWidth(tc1.getWidth());
      	  	tc2.setWidth(tc2.getWidth() +30);
      	  	tc3.setWidth(tc2.getWidth() +30);
      }*/
        
	}
	
	/************************************************************
	 * Method to update the expired table in c1
	 ***********************************************************/
	/**
	 * Description: Pulls the expired list from ModelEvent and updates the expired event table in composite c1
	 */
	public static void UpdateExpiredTable() {
		expiredlist = ModelEvent.PullExpiredList();

		TableItem item;
		int i;
		if (table_1.getItemCount() == 0)	//adds whole vector if table empty 
			i=0;

		else
			i = table_1.getItemCount() + 1;		//add the last item into table
		
		for (; i<expiredlist.size(); i++) {			
			item = new TableItem(table_1,SWT.NONE);
			item.setText(expiredlist.get(i).getName());
/*			item.setText(1, "5");
			item.setBackground(1, red);
	    	item.setText(2, "X");
	    	item.setBackground(2,blue);
	    	*/
		}
	}
	
	/************************************************************
	 * Method to shift expired events between tables in c1
	 ***********************************************************/
	/**
	 * Description:
	 */
	public static void ShiftExpired() {
		expiredlist = ModelEvent.PullExpiredList();
		int prevExpIndex = expiredlist.size();
		
		//shifts expired events into expired list & remove from event list table
		for (int i=0; i<eventlist.size(); i++) {
			if (eventlist.get(i).isExpired()) {
				table.remove(i);
				expiredlist.add(eventlist.get(i));
			}
		}
		
		//Remove the expired events from event list
		for (int i=0; i<eventlist.size(); i++) {
			if (eventlist.get(i).isExpired()) {
				eventlist.remove(i);
				i--;
			}
		}
		
		UpdateExpiredTable();
		ModelEvent.UpdateExpiredList(expiredlist.subList(prevExpIndex, expiredlist.size()));
	}
	/************************************************************
	 * HOMEPAGE
	 ***********************************************************/
	/**
	 * Description: Initialize ViewHomepage and set the page of composite c2 to ViewHomepage
	 */
	public static void Homepage () {		
		hp = new ViewHomepage(c2, SWT.NONE);
		layout.topControl = hp;
		c2.layout(true);
	}
	
	/************************************************************
	 * CALCULATE BUDGET
	 ***********************************************************/
	/**
	 * Description: Initialize ViewBudget and set the page of composite c2 to ViewBudget
	 */
	public static void CalcBudget () {		
		ViewBudget bv = new ViewBudget(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = bv;
		c2.layout(true);
	}
	
	/************************************************************
	 * BOOK VENUE
	 ***********************************************************/
	/**
	 * Description: Initialize ViewBookingSystem and set the page of composite c2 to ViewBookingSystem
	 */
	public static void BookVenue() {
		ViewBookingSystem bookgui = new ViewBookingSystem(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = bookgui;
		c2.layout(true);
	}
	
	/************************************************************
	 * EVENT PARTICULARS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEventParticulars and set the page of composite c2 to ViewEventParticulars
	 * @param curevent The event item of the particulars that is to be edited
	 */
	public static void EventParticulars(Eventitem curevent) {
		ViewEditEventParticulars ep = new ViewEditEventParticulars(c2, SWT.NONE, curevent);
		layout.topControl = ep;
		c2.layout(true);
	}
	
	/************************************************************
	 * Program Flow
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEventFlow and set the page of composite c2 to ViewEventFlow
	 */
	public static void EventFlow () {		
		ViewEventFlow ef = new ViewEventFlow(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = ef;
		c2.layout(true);
	}
	
	/************************************************************
	 * PARTICIPANT LIST
	 ***********************************************************/
	/**
	 * Description: Initialize ViewParticipantList and set the page of composite c2 to ViewParticipantList
	 * @param curevent The event item of the particulars that is to be edited
	 */
	public static void ParticipantList(Eventitem curevent) {
		ViewParticipantList pl = new ViewParticipantList(c2, SWT.NONE, curevent);
		layout.topControl = pl;
		c2.layout(true);
	}
	
	/************************************************************
	 * EMAIL ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewEmailAds and set the page of composite c2 to ViewEmailAds
	 * @param curevent The event item of the particulars that is to be edited
	 */
	public static void EmailAds(Eventitem curevent) {
		ViewEmailAds ea = new ViewEmailAds(c2, SWT.NONE, curevent);
		layout.topControl = ea;
		c2.layout(true);
	}
	
	/************************************************************
	 * FACEBOOK ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewFaceBookAds and set the page of composite c2 to ViewFaceBookAds
	 * @param curevent The event item of the particulars that is to be edited
	 */
	public static void FaceBookAds() {
		ViewFaceBookAds fba = new ViewFaceBookAds(c2);
		layout.topControl = fba;
		c2.layout(true);
	}
	
	/************************************************************
	 * SMS ADS
	 ***********************************************************/
	/**
	 * Description: Initialize ViewSmsAds and set the page of composite c2 to ViewSmsAds
	 * @param curevent The event item of the particulars that is to be edited
	 */
	public static void SMSAds(Eventitem curevent) {
		ViewSmsAds smsa = new ViewSmsAds(c2, SWT.NONE, curevent);
		layout.topControl = smsa;
		c2.layout(true);
	}
	
	/************************************************************
	 * RETURN VIEW
	 ***********************************************************/
	/**
	 * Description: Sets the page of composite c2 to ViewEvent
	 */
	public static void ReturnView() {
		ViewEvent newview = new ViewEvent(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = newview;
		c2.layout(true);
		
	}
	
	public static void CheckExpiry() {
        // Task here ...
/*	        	GregorianCalendar date = new GregorianCalendar();
    	date.setTimeInMillis(System.currentTimeMillis());
    	date.set(GregorianCalendar.MILLISECOND, 0);
    	SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
    	String curtime = sdf.format(date.getTime());	//gets the current time in string
    	     	
    	sdf = new SimpleDateFormat("YYYYMMdd");
    	*/
    	MyDateTime currentDT = MyDateTime.getCurrentDateTime();
    	boolean flag = false;
    	for (int i=0; i<eventlist.size(); i++) {
    		if (eventlist.get(i).getEndDateTime().compareTo(currentDT) <= 0) {
    			eventlist.get(i).setIsExpired(true);
    			flag = true;
    			System.out.println("FLAGGED");
    		}
    	}
    	if (flag)
    		ShiftExpired();
    	System.out.println("Checking !!");
	}
	
	/**
	 * Description: Create contents of the application window.
	 * @param parent
	 */
	protected Control createContents(Composite parent) {
		setStatus("Welcome!");
		ScrolledComposite container = new ScrolledComposite(parent, SWT.V_SCROLL);
		
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setExpandHorizontal(true);
		container.setExpandVertical(true);
		container.setMinHeight(415);
		container.setMinWidth(1000);
		container.setEnabled(true);
		container.setLayout(null);
		{
			maincomposite = new Composite(container, SWT.NONE);
			maincomposite.setBounds(0, 0, 400, 415);
			maincomposite.setSize(container.getSize());
			maincomposite.setLayout(new GridLayout(3, false));
			
			c1 = new Composite(maincomposite, SWT.NONE);
		//	maincomposite.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		//	c1.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
			c1.setLayout(new GridLayout(1, false));
			GridData gd_c1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_c1.heightHint = 415;
			gd_c1.widthHint = 300;
			
			gd_c1.grabExcessVerticalSpace = true;
			gd_c1.verticalAlignment = SWT.FILL;
			gd_c1.horizontalAlignment = SWT.FILL;
			
			c1.setLayoutData(gd_c1);
			formToolkit.adapt(c1);
			formToolkit.paintBordersFor(c1);
			c1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			
			/************************************************************
			 * CREATE EVENT BUTTON EVENT LISTENER
			 ***********************************************************/
			Button btnCreateEvent = formToolkit.createButton(c1, "Create Event", SWT.NONE);
			btnCreateEvent.setForeground(SWTResourceManager.getColor(0, 0, 0));
			btnCreateEvent.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
			btnCreateEvent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			btnCreateEvent.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 16, SWT.BOLD));
			btnCreateEvent.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					
					//dispose of all children that currently is in c2
					if (c2 != null && !c2.isDisposed()) {
					Object[] children = c2.getChildren();
					for (int i=0; i<children.length; i++)
						((Composite)children[i]).dispose();
					}
					
					ViewCreateEvent CreatePage = new ViewCreateEvent(c2, SWT.NONE);
					layout.topControl = CreatePage;
					c2.layout(true);	//refreshes c2
					
				}
			});
			GridData gd_btnCreateEvent = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
			gd_btnCreateEvent.widthHint = 294;
			btnCreateEvent.setLayoutData(gd_btnCreateEvent);
			
			/************************************************************
			 * TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
			
			//Column Resize with table fix
			 c1.addControlListener(new ControlAdapter() {
				    public void controlResized(ControlEvent e) {
				      Rectangle area = c1.getClientArea();
				      Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				      int width = area.width - 2*table.getBorderWidth();
				      
				      if (preferredSize.y > area.height + table.getHeaderHeight()) {
				        // Subtract the scrollbar width from the total column width
				        // if a vertical scrollbar will be required
				        Point vBarSize = table.getVerticalBar().getSize();
				        width -= vBarSize.x;
				      }
				      Point oldSize = c1.getSize();
				      if (oldSize.x > area.width) {
				        // table is getting smaller so make the columns 
				        // smaller first and then resize the table to
				        // match the client area width
				    	  tc1.setWidth(width/3*2);
					      tc2.setWidth((width - tc1.getWidth())/2 -12);
					      tc3.setWidth((width - tc1.getWidth())/2 -12);
				   //       table.setSize(area.width, area.height);
				      } else {
				        // table is getting bigger so make the table 
				        // bigger first and then make the columns wider
				        // to match the client area width
				    //	  table.setSize(area.width, area.height);
				    	  tc1.setWidth(width/3*2);
					      tc2.setWidth((width - tc1.getWidth())/2 -12);
					      tc3.setWidth((width - tc1.getWidth())/2 -12);			        
				      }
			/*
				        table.addPaintListener(new PaintListener() {
				            public void paintControl(PaintEvent e) {
				                Rectangle rect = table.getClientArea ();
				                int itemHeight = table.getItemHeight ();
				                int headerHeight = table.getHeaderHeight ();
				                int visibleCount = (rect.height - headerHeight + itemHeight - 1) / itemHeight;
				                System.out.println("Vertical Scroll Visible - [" + (table.getItemCount()>= visibleCount)+"]");

				                vBarOn = table.getItemCount() >= visibleCount;
				                	
				            }
				        });
				        
				        if (firstruncheck == 0) {
				        	System.out.println("first run");
				            if (vBarOn) {
				            	System.out.println("first running");
				            	tc1.setWidth(tc1.getWidth());
				            	tc2.setWidth(tc2.getWidth() -30);
				            	tc3.setWidth(tc2.getWidth() -30);
				            	tc2vBarOnWidth = tc2.getWidth();
				            	tc2vBarOffWidth = tc2.getWidth() + 30;
				            }
				            
				            else {
				            	tc2vBarOnWidth = tc2.getWidth() - 30;
				            	tc2vBarOffWidth = tc2.getWidth();
				            }
				            	

				           firstruncheck = 1;
				        }
				        else {
				        	System.out.println("first else running");
				        	System.out.println(tc2.getWidth()+", "+ tc2vBarOnWidth);
					        if (vBarOn && tc2.getWidth() != tc2vBarOnWidth) {
					        	System.out.println("running");
					        	tc1.setWidth(tc1.getWidth());
					        	tc2.setWidth(tc2.getWidth() -30);
					        	tc3.setWidth(tc2.getWidth() -30);
					        }
					        
					        else if (!vBarOn && tc2.getWidth() != tc2vBarOffWidth) {
					        	System.out.println("running2");
					      	  	tc1.setWidth(tc1.getWidth());
					      	  	tc2.setWidth(tc2.getWidth() +30);
					      	  	tc3.setWidth(tc2.getWidth() +30);
					      }
				        }*/
				    }
				    
			 });
			
			
			TabFolder tabFolder = new TabFolder(c1, SWT.NONE);
			GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
			gd_tabFolder.widthHint = 284;
			tabFolder.setLayoutData(gd_tabFolder);
			formToolkit.adapt(tabFolder);
			formToolkit.paintBordersFor(tabFolder);
			
			TabItem tbtmUpcomingEvents = new TabItem(tabFolder, SWT.NONE);
			tbtmUpcomingEvents.setText("       Upcoming Events       ");
			
			//Event List Table
			table = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
			tbtmUpcomingEvents.setControl(table);
			table.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = table.getItem(new Point(e.x, e.y));
					 if (item != null)
						 table.setToolTipText(item.getText(0));
				}
			});
			table.setToolTipText("");
			table.setTouchEnabled(true);
			table.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in c2
				if (c2 != null && !c2.isDisposed()) {
				Object[] children = c2.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				//tc1.setToolTipText(eventlist.get(table.getSelectionIndex()).getName());
				view = new ViewEvent(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		        layout.topControl = view;
				c2.layout(true);
		     }
			});
			tc1 = new TableColumn(table, SWT.LEFT);
			tc2 = new TableColumn(table,SWT.CENTER);
			tc3 = new TableColumn(table,SWT.CENTER);
			
		    tc1.setText("Event Title");
		    tc2.setText("DL");
		    tc3.setText("Undone");
		    // 	    tc1.setWidth(206);
		    // 	    tc2.setWidth(40);
		    // 	    tc3.setWidth(40);
		     //	    tc1.pack();
		     //	    tc2.pack();
		     //	    tc3.pack();
		     	    
		     	    tc1.setResizable(false);
		     	    tc2.setResizable(false);
		     	    tc3.setResizable(false);
		     	    
			tc1.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					//sort name
					TableItem[] items = table.getItems();
					Collator collator = Collator.getInstance(Locale.getDefault());
					for (int i = 1; i<items.length; i++) {
						String value1 = items[i].getText(0);
						for(int j=0; j < i; j++) {
							String value2 = items[j].getText(0);
							if(collator.compare(value1, value2) < 0) {
								String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)};
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);	//j could be the final index
								item.setText(values);
								items = table.getItems();
								break;
							}
						}
					}
				}
			});
			
			table.getHorizontalBar().setVisible(true);
			table.getHorizontalBar().setEnabled(false);
			formToolkit.adapt(table);
			formToolkit.paintBordersFor(table);
			table.setHeaderVisible(true);
			table.setLinesVisible(false);
			UpdateTable();
	//		CheckExpiry();
			Menu menu = new Menu(table);
			table.setMenu(menu);
			
			/************************************************************
			 * DELETE EVENT
			 ***********************************************************/
			
			MenuItem mntmDeleteEvent = new MenuItem(menu, SWT.PUSH);
			mntmDeleteEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TableItem tb = table.getItem(table.getSelectionIndex());
					deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", tb.getText(0));
					if ((Integer) confirm.open() == 1) {
						ModelEvent.DeleteEvent(eventlist.get(table.getSelectionIndex()));	//Finds the selected event and deletes it from vector
						DeleteItem();
					}
				}
			});
			mntmDeleteEvent.setText("Delete Event");
			
			TabItem tbtmPastEvents = new TabItem(tabFolder, SWT.NONE);
			tbtmPastEvents.setText("          Past Events          ");
			
			//Past Event List Table
			table_1 = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
			tbtmPastEvents.setControl(table_1);			
			table_1.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					 TableItem item = table_1.getItem(new Point(e.x, e.y));
					 if (item != null)
						 table_1.setToolTipText(item.getText(0));
				}
			});
			table_1.setToolTipText("");
			table_1.setTouchEnabled(true);
			table_1.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {

		      //dispose of all children that currently is in c2
				if (c2 != null && !c2.isDisposed()) {
				Object[] children = c2.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				//tc1.setToolTipText(eventlist.get(table.getSelectionIndex()).getName());
				view = new ViewEvent(c2, SWT.NONE, expiredlist.get(table_1.getSelectionIndex()));
		        layout.topControl = view;
				c2.layout(true);
		     }
			});
			etc1 = new TableColumn(table_1, SWT.LEFT);
			etc2 = new TableColumn(table_1,SWT.CENTER);
			etc3 = new TableColumn(table_1,SWT.CENTER);
			
		    etc1.setText("Event Title");
		    etc2.setText("DL");
		    etc3.setText("Undone");
		    // 	    tc1.setWidth(206);
		    // 	    tc2.setWidth(40);
		    // 	    tc3.setWidth(40);
		    	    etc1.pack();
		     	    etc2.pack();
		     	    etc3.pack();
		     	    
		     	    etc1.setResizable(false);
		     	    etc2.setResizable(false);
		     	    etc3.setResizable(false);
		     	    
			etc1.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					//sort name
					TableItem[] items = table_1.getItems();
					Collator collator = Collator.getInstance(Locale.getDefault());
					for (int i = 1; i<items.length; i++) {
						String value1 = items[i].getText(0);
						for(int j=0; j < i; j++) {
							String value2 = items[j].getText(0);
							if(collator.compare(value1, value2) < 0) {
								String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)};
								items[i].dispose();
								TableItem item = new TableItem(table_1, SWT.NONE, j);	//j could be the final index
								item.setText(values);
								items = table_1.getItems();
								break;
							}
						}
					}
				}
			});
			
			table_1.getHorizontalBar().setVisible(true);
			table_1.getHorizontalBar().setEnabled(false);
			formToolkit.adapt(table_1);
			formToolkit.paintBordersFor(table_1);
			table_1.setHeaderVisible(true);
			table_1.setLinesVisible(false);
			UpdateExpiredTable();
			Menu menu2 = new Menu(table_1);
			table_1.setMenu(menu2);
			
			/************************************************************
			 * DELETE PAST EVENT
			 ***********************************************************/
			
			MenuItem mntmDeletePastEvent = new MenuItem(menu2, SWT.PUSH);
			mntmDeletePastEvent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TableItem tb = table_1.getItem(table_1.getSelectionIndex());
					deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), "delconfirm", tb.getText(0));
					if ((Integer) confirm.open() == 1) {
				//		ModelEvent.DeleteEvent(eventlist.get(table.getSelectionIndex()));	//Finds the selected event and deletes it from vector
					//	DeleteItem();
					}
				}
			});
			mntmDeletePastEvent.setText("Delete Event");
			
			DateTime Calender = new DateTime(c1, SWT.CALENDAR | SWT.LONG);
			Calender.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
			formToolkit.adapt(Calender);
			formToolkit.paintBordersFor(Calender);
			
			
			Label Vseparator = new Label(maincomposite, SWT.SEPARATOR | SWT.VERTICAL);
			GridData gd_Vseparator = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_Vseparator.heightHint = 415;
			gd_Vseparator.verticalAlignment = SWT.FILL;
			Vseparator.setLayoutData(gd_Vseparator);
			
			
			formToolkit.adapt(Vseparator, true, true);
			{
				c2 = new Composite(maincomposite, SWT.NONE);
				c2.setLayout(layout);
				GridData gd_c2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_c2.heightHint = 415;
				gd_c2.widthHint = 700;
				gd_c2.grabExcessVerticalSpace = true;
				gd_c2.verticalAlignment = SWT.FILL;
				gd_c2.grabExcessHorizontalSpace = true;
				gd_c2.horizontalAlignment = SWT.FILL;
				c2.setLayoutData(gd_c2);
				formToolkit.adapt(c2);
				formToolkit.paintBordersFor(c2);
				hp = new ViewHomepage(c2, SWT.NONE);
				layout.topControl = hp;
				
			
			}
	
		}
		
	
//		int delay = 5000;   // delay for 5 sec.
//		int period = 1000;  // repeat every sec.
//		Timer timer = new Timer();
		
//		checkthread chkthread = new checkthread();
//		chkthread.start();
		
//		timer.scheduleAtFixedRate(new TimerTask() {
//		        public void run() {
/*		        	MyDateTime currentDT = MyDateTime.getCurrentDateTime();
		        	boolean flag = false;
		        	for (int i=0; i<eventlist.size(); i++) {
		        		if (eventlist.get(i).getEndDateTime().compareTo(currentDT) <= 0) {
		        			eventlist.get(i).setIsExpired(true);
		        			flag = true;
		        		}
		        	}
		        	if (flag)
		        		ShiftExpired();
		        	System.out.println("Checking !!");
		        }
		    }, delay, period);
	*/	
		/****************************************************************
		 * 
		 * THREAD
		 * 
		 ****************************************************************/
	/*	new Thread(new Runnable() {
		      public void run() {
		         while (true) {
		            try { Thread.sleep(1000); } catch (Exception e) {Thread.yield();}
		            Display.getDefault().asyncExec(new Runnable() {
		               public void run() {
		                  CheckExpiry();
		                  
		               }
		            });
		         }
		      }
		   }).start();
	*/
		container.setContent(maincomposite);
		container.setMinSize(maincomposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return container;
	}

	/**
	 * Description: Create the actions for the menu bar.
	 */
	private void createActions() {
		
		//exit action
		{
			exitaction = new Action("Exit") {					public void run() {
						System.exit(0);
					}
			};
			exitaction.setAccelerator(SWT.ALT | SWT.F4);
			exitaction.setToolTipText("Exits E-MAN");
			exitaction.addPropertyChangeListener(new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					System.exit(0);
				}
			});
		
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
			FileMenu.add(exitaction);
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
		newShell.setImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\workspace\\E-MAN\\Images\\thumbnail.jpg"));
	//	newShell.setMinimumSize(new Point(1035, 526));
		newShell.setMinimumSize(new Point(1200, 526));
		super.configureShell(newShell);
		newShell.setText("E-Man");
		newShell.setSize(getInitialSize());
		//newShell.setMaximized(true);

	}

	/**
	 * Description: Return the initial size of the window.
	 */
	protected Point getInitialSize() {
		return new Point(1200, 526);
	}
}
