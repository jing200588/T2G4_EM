import java.awt.event.ActionEvent;

import org.eclipse.jface.action.AbstractAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;


public class EmanagerGUIjface extends ApplicationWindow {
	private Action exitaction;
	private Action swtguiaction;
	//private ExitAction exitAction;
	/**
	 * Create the application window.
	 */
	public EmanagerGUIjface() {
		super(null);
		//exitAction = new ExitAction(this);
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
		Composite container = new Composite(parent, SWT.NONE);

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
		super.configureShell(newShell);
		newShell.setText("E-Man");
	}

	/**
	 * Return the initial size of the window.
	 */
	protected Point getInitialSize() {
		return new Point(1000, 800);
	}

}
