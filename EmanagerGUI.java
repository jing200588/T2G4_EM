import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CLabel;


public class EmanagerGUI {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EmanagerGUI window = new EmanagerGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(629, 606);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout());
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile_1 = new MenuItem(menu, SWT.CASCADE);
		mntmFile_1.setText("File");
		
		Menu menu_1 = new Menu(mntmFile_1);
		mntmFile_1.setMenu(menu_1);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		mntmExit.setText("Exit");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(0, 0, 434, 159);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new FormLayout());
		scrolledComposite.setContent(composite);
		Label label_12 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_12 = new FormData();
		fd_label_12.right = new FormAttachment(95);
		fd_label_12.left = new FormAttachment(5);
		label_12.setLayoutData(fd_label_12);
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		fd_label_12.bottom = new FormAttachment(composite_2, -30);
		composite_2.setLayout(new GridLayout(3, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(0, 475);
		fd_composite_2.right = new FormAttachment(80);
		fd_composite_2.left = new FormAttachment(20);
		composite_2.setLayoutData(fd_composite_2);
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		// Experiment
		System.out.println(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		Label label_13 = new Label(composite_2, SWT.NONE);
		label_13.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_13.setText("Event Program Flow");
		label_13.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		
		Label label_14 = new Label(composite_2, SWT.NONE);
		GridData gd_label_14 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_14.widthHint = 38;
		label_14.setLayoutData(gd_label_14);
		label_14.setText(" ");
		
		Button button_1 = new Button(composite_2, SWT.NONE);
		GridData gd_button_1 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_button_1.widthHint = 85;
		button_1.setLayoutData(gd_button_1);
		button_1.setText("Edit");
		
		Label label_15 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_15 = new FormData();
		fd_label_15.top = new FormAttachment(composite_2, 30);
		fd_label_15.right = new FormAttachment(95);
		fd_label_15.left = new FormAttachment(5);
		label_15.setLayoutData(fd_label_15);
		
		Label label_16 = new Label(composite, SWT.NONE);
		label_16.setImage(SWTResourceManager.getImage("C:\\Users\\Lacryia\\Pictures\\b34wl.jpg"));
		fd_label_12.top = new FormAttachment(label_16, 395);
		label_16.setText("View Event:");
		label_16.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		FormData fd_label_16 = new FormData();
		fd_label_16.top = new FormAttachment(0, 10);
		fd_label_16.left = new FormAttachment(0, 10);
		label_16.setLayoutData(fd_label_16);
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new GridLayout(3, false));
		FormData fd_composite_3 = new FormData();
		fd_composite_3.top = new FormAttachment(label_15, 30);
		fd_composite_3.right = new FormAttachment(80);
		fd_composite_3.left = new FormAttachment(20);
		composite_3.setLayoutData(fd_composite_3);
		
		Label label_17 = new Label(composite_3, SWT.NONE);
		label_17.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_17.setText("Book Venue");
		label_17.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		
		Label label_18 = new Label(composite_3, SWT.NONE);
		GridData gd_label_18 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_18.widthHint = 27;
		label_18.setLayoutData(gd_label_18);
		
		// Experiment
		System.out.println(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button button_2 = new Button(composite_3, SWT.NONE);
		GridData gd_button_2 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_button_2.widthHint = 85;
		button_2.setLayoutData(gd_button_2);
		button_2.setText("Edit");
		
		Label label_19 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_19 = new FormData();
		fd_label_19.top = new FormAttachment(composite_3, 30);
		fd_label_19.right = new FormAttachment(95);
		fd_label_19.left = new FormAttachment(5);
		label_19.setLayoutData(fd_label_19);
		// Experiment
		System.out.println(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
		Composite composite_4 = new Composite(composite, SWT.NONE);
		composite_4.setLayout(new GridLayout(3, false));
		FormData fd_composite_4 = new FormData();
		fd_composite_4.top = new FormAttachment(label_19, 30);
		fd_composite_4.right = new FormAttachment(80);
		fd_composite_4.left = new FormAttachment(20);
		composite_4.setLayoutData(fd_composite_4);
		
		Label label_20 = new Label(composite_4, SWT.NONE);
		label_20.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_20.setText("Optimal Purchase");
		label_20.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		
		Label label_21 = new Label(composite_4, SWT.NONE);
		GridData gd_label_21 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_21.widthHint = 49;
		label_21.setLayoutData(gd_label_21);
		
		Button button_3 = new Button(composite_4, SWT.NONE);
		GridData gd_button_3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_button_3.widthHint = 85;
		button_3.setLayoutData(gd_button_3);
		button_3.setText("Calculate");
		// Experiment
		System.out.println(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	//	ViewEventGUI view = new ViewEventGUI(shell, SWT.NONE, EmanagerGUIjface.getEvent("abc"));
		BudgetView bv = new BudgetView(shell, SWT.NONE, 0);
	}
}
