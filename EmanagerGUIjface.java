import java.awt.event.ActionEvent;
import java.awt.Color;

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
import org.eclipse.swt.widgets.Shell;
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


public class EmanagerGUIjface extends ApplicationWindow {
	private Action exitaction;
	private Action swtguiaction;
	private Composite maincomposite;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Composite c1;
	private Table table;
	private Table table_1;
	private Composite c2;

	/**
	 * Create the application window.
	 */
	public EmanagerGUIjface() {
		super(null);

		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
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
				/*	Button testbutton = new Button(c2, SWT.NONE);
					testbutton.setBounds(0, 2, 187, 25);
					testbutton.setText("Test Button");
				 */
					
					CreateEventGUI CreatePage = new CreateEventGUI(c2, SWT.NONE);
					CreatePage.setBounds(c2.getBounds());
					formToolkit.adapt(CreatePage);
					formToolkit.paintBordersFor(CreatePage);
	//				CreateEventGUI CreatePage = new CreateEventGUI(c2, SWT.NONE);
		//			c2 = CreatePage;
					c2.layout(true);
					
				}
			});
			GridData gd_btnCreateEvent = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
			gd_btnCreateEvent.widthHint = 294;
			btnCreateEvent.setLayoutData(gd_btnCreateEvent);
			
			TableViewer tableViewer = new TableViewer(c1, SWT.BORDER | SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			GridData gd_table = new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1);
			gd_table.heightHint = 179;
			gd_table.widthHint = 270;
			table.setLayoutData(gd_table);
			formToolkit.paintBordersFor(table);
			
			table_1 = new Table(c1, SWT.BORDER | SWT.FULL_SELECTION);
			table_1.setTouchEnabled(true);
			GridData gd_table_1 = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
			gd_table_1.widthHint = 270;
			table_1.setLayoutData(gd_table_1);
			formToolkit.adapt(table_1);
			formToolkit.paintBordersFor(table_1);
			table_1.setHeaderVisible(true);
			table_1.setLinesVisible(true);
			
			
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
