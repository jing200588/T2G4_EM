import java.util.Vector;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Color;


public class ViewMain extends ApplicationWindow {
	private Action exitaction;
	private Action swtguiaction;
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
    private static EMDB db;
    
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
	public static void DeleteItem() {
		table.remove(table.getSelectionIndices());
		layout.topControl = hp;
		c2.layout(true);
	}
	
	/************************************************************
	 * Method to update the table in c1
	 ***********************************************************/
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
			item.setText(1, "TEST");
			item.setBackground(1, red);
	    	item.setText(2, "C3");
	    	item.setBackground(2,blue);
		}
	}

	/************************************************************
	 * CALCULATE BUDGET
	 ***********************************************************/
	public static void CalcBudget () {		
		ViewBudget bv = new ViewBudget(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = bv;
		c2.layout(true);
	}
	
	/************************************************************
	 * BOOK VENUE
	 ***********************************************************/
	public static void BookVenue() {
		ViewBookingSystem bookgui = new ViewBookingSystem(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		layout.topControl = bookgui;
		c2.layout(true);
	}
	
	/************************************************************
	 * EVENT PARTICULARS
	 ***********************************************************/
	public static void EventParticulars(Eventitem curevent) {
		ViewEventParticulars ep = new ViewEventParticulars(c2, SWT.NONE, curevent, table.getSelectionIndex());
		layout.topControl = ep;
		c2.layout(true);
	}
	
	/************************************************************
	 * RETURN VIEW
	 ***********************************************************/
	public static void ReturnView() {	//for returning to view event page
		ViewEvent newview = new ViewEvent(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		System.out.println("test");
		layout.topControl = newview;
		c2.layout(true);
	}
	
	/**
	 * Create contents of the application window.
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
			
			Button btnCreateEvent = formToolkit.createButton(c1, "Create Event", SWT.NONE);
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
			
			table = new Table(c1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
			table.setToolTipText("All Events");
			table.setTouchEnabled(true);
			
			/************************************************************
			 * TABLE ITEM SELECTION EVENT LISTENER
			 ***********************************************************/
			table.addListener(SWT.Selection, new Listener() {
		     public void handleEvent(Event event) {
		       
		/*        //extract event name
		        String itemname = "";
		        itemname += event.item;
		        itemname = itemname.substring(11, itemname.length() -1);
		 */
		      //dispose of all children that currently is in c2
				if (c2 != null && !c2.isDisposed()) {
				Object[] children = c2.getChildren();
				for (int i=0; i<children.length; i++)
					((Composite)children[i]).dispose();
				}
			
				//	view = new ViewEvent(c2, SWT.NONE, getEvent(itemname));
				view = new ViewEvent(c2, SWT.NONE, eventlist.get(table.getSelectionIndex()));
		        layout.topControl = view;
				c2.layout(true);
		     }
			});
				
			
			GridData gd_table = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
			gd_table.widthHint = 270;
			TableColumn tc1 = new TableColumn(table, SWT.LEFT);
			TableColumn tc2 = new TableColumn(table,SWT.CENTER);
			TableColumn tc3 = new TableColumn(table,SWT.CENTER);
		    tc1.setText("Event List");
	 	    tc2.setText("DL");
	 	    tc3.setText("Undone");
	 	    tc1.setWidth(206);
	 	    tc2.setWidth(40);
	 	    tc3.setWidth(40);
	 	    tc1.setResizable(false);
	 	    tc2.setResizable(false);
	 	    tc3.setResizable(false);
			table.setLayoutData(gd_table);
			formToolkit.adapt(table);
			formToolkit.paintBordersFor(table);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			UpdateTable();
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
					deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), SWT.APPLICATION_MODAL, tb.getText(0));
					if (confirm.open() == 1) {
//						System.out.println(table.getSelectionIndex());
					//	ModelEvent.DeleteEvent(getEvent(tb.getText(0)));	//Finds the selected event and deletes it from vector
						ModelEvent.DeleteEvent(eventlist.get(table.getSelectionIndex()));	//Finds the selected event and deletes it from vector
						DeleteItem();
					}
				}
			});
			mntmDeleteEvent.setText("Delete Event");
			
			DateTime Calender = new DateTime(c1, SWT.BORDER | SWT.CALENDAR | SWT.LONG);
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
		container.setContent(maincomposite);
		container.setMinSize(maincomposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));


		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
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
		{
			new Action("New Action") {
			};
		}
		//swtguiaction
		{
			swtguiaction = new Action("SWT") {				public void run() {
					EmanagerGUI window = new EmanagerGUI();
					window.open();
				}
			};
			swtguiaction.setAccelerator(SWT.CTRL | SWT.F1);
		}
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		{
			MenuManager FileMenu = new MenuManager("File");
			menuManager.add(FileMenu);
			FileMenu.add(swtguiaction);
			FileMenu.add(exitaction);
		}
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			
			db = new EMDB();
			db.system_check();
			
			ViewMain window = new ViewMain();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		newShell.setMinimumSize(new Point(1035, 526));
		super.configureShell(newShell);
		newShell.setText("E-Man");
		newShell.setMaximized(true);
	}

	/**
	 * Return the initial size of the window.
	 */
	protected Point getInitialSize() {
		return new Point(1034, 526);
	}
}
