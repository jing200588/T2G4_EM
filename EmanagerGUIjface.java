import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Vector;

import org.eclipse.jface.action.AbstractAction;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.widgets.DateTime;


public class EmanagerGUIjface extends ApplicationWindow {
	private Action exitaction;
	private Action swtguiaction;
	private Composite maincomposite;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Composite c1;
	private static Table table;
	private Composite c2;
	private static Vector<Eventitem> eventlist;
	private static int count = 0;

	/**
	 * Create the application window.
	 */
	public EmanagerGUIjface() {
		super(null);
		eventlist = new Vector<Eventitem>();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	public static void addEvent(Eventitem newevent) {
		eventlist.add(newevent);
		TableItem it1 = new TableItem(table,SWT.NONE);
		 it1.setText(eventlist.get(count++).getName());

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
			maincomposite = new Composite(container, SWT.V_SCROLL);
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
			btnCreateEvent.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					
					//dispose of all children that currently is in c2
					if (c2 != null && !c2.isDisposed()) {
					Object[] children = c2.getChildren();
					for (int i=0; i<children.length; i++)
						((Composite)children[i]).dispose();
					}
					
					CreateEventGUI CreatePage = new CreateEventGUI(c2, SWT.NONE);
					CreatePage.setBounds(c2.getBounds());
					formToolkit.adapt(CreatePage);
					formToolkit.paintBordersFor(CreatePage);
	//				CreateEventGUI CreatePage = new CreateEventGUI(c2, SWT.NONE);
		//			c2 = CreatePage;
					c2.layout(true);	//refreshes c2
					
				}
			});
			GridData gd_btnCreateEvent = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
			gd_btnCreateEvent.widthHint = 294;
			btnCreateEvent.setLayoutData(gd_btnCreateEvent);
			
			table = new Table(c1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
			table.setToolTipText("All Events");
			table.setTouchEnabled(true);
			GridData gd_table = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
			gd_table.widthHint = 270;
			TableColumn tc1 = new TableColumn(table, SWT.LEFT);
			TableColumn tc2 = new TableColumn(table,SWT.CENTER);
			TableColumn tc3 = new TableColumn(table,SWT.CENTER);
		    tc1.setText("Event List");
	 	    tc2.setText("Alert");
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
/*			
			table.setItemCount(100);
			table.addListener (SWT.SetData, new Listener () {
				public void handleEvent (Event event) {
					TableItem item = (TableItem) event.item;
					int index = table.indexOf (item);
					item.setText (eventlist.get(0).getName());
					System.out.println (item.getText ());
				}

				
			});
			
			
			
			
	*/		
			
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
				c2.setLayout(new FillLayout(SWT.HORIZONTAL | SWT.VERTICAL));
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
			EmanagerGUIjface window = new EmanagerGUIjface();
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
