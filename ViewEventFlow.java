import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ViewEventFlow extends Composite {
	
	// Table column names/properties
	public static final String STARTDATETIME = "Start Date Time";
	public static final String ENDDATETIME = "End Date Time";
	public static final String ACTIVITY = "Activity";
	public static final String VENUE = "Venue";
	public static final String NOTE = "Further notes";
	public static final String[] COLUMN_PROPS = {STARTDATETIME, ENDDATETIME, ACTIVITY, VENUE, NOTE};
	  
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	

	private Table tableEventFlow;
	private TableViewer tableViewEventFlow;
	
	private Eventitem eventObj;
	private Vector<EventFlowEntry> listEventFlow;
	private Text textFilePath;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventFlow(Composite parent, int style, Eventitem event) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		// Initialize some variables 
		eventObj = event;
		listEventFlow = eventObj.getEventFlow();
		
		// Continue with the GUI
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
				
		Form VenueViewForm = toolkit.createForm(this);
		VenueViewForm.getBody().setBackgroundMode(SWT.INHERIT_DEFAULT);
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Event Flow");
		VenueViewForm.getBody().setLayout(new FormLayout());

		/////////////////////////////////////////////////////////////////////////////////////
		/// 
		////////////////////////////////////////////////////////////////////////////////////
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// User presses Ctrl + S
				if((e.stateMask & SWT.CTRL) != 0 && e.keyCode == java.awt.event.KeyEvent.VK_S)
				{
					// Update in the database
					ModelEventFlow.saveEventFlow(listEventFlow);
				}
			}
		});
		
		Composite mainComposite = new Composite(VenueViewForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.bottom = new FormAttachment(100, -24);
		fd_mainComposite.left = new FormAttachment(0, 28);
		fd_mainComposite.top = new FormAttachment(50, -159);
		fd_mainComposite.right = new FormAttachment(50, 349);
		mainComposite.setLayoutData(fd_mainComposite);
		toolkit.adapt(mainComposite);
		toolkit.paintBordersFor(mainComposite);
		
		tableViewEventFlow = new TableViewer(mainComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableEventFlow = tableViewEventFlow.getTable();
		tableEventFlow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				listenToViewAction();
			}
		});
		tableEventFlow.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == java.awt.event.KeyEvent.VK_DELETE)
				{
					int chosenIndex = tableEventFlow.getSelectionIndex();
					if(chosenIndex >= 0)
					{
						deleteconfirmDialog deleteDialog = new deleteconfirmDialog(new Shell(),
								deleteconfirmDialog.STATE_TYPE, "this entry");
						int decision = (Integer) deleteDialog.open();
						if(decision == 1)
						{
							// Delete the entry
							listEventFlow.remove(chosenIndex);
							tableViewEventFlow.refresh();
						}
					}
				}
				else
				{
					if(e.keyCode == java.awt.event.KeyEvent.VK_ENTER)
					{
						listenToViewAction();
					}
				}
			}
		}); 
	
		tableEventFlow.setTouchEnabled(true);
		tableEventFlow.setLinesVisible(true);
		tableEventFlow.setHeaderVisible(true);
		tableEventFlow.setBounds(10, 10, 505, 268);
		tableViewEventFlow.setContentProvider(ArrayContentProvider.getInstance());
		// Set input
		toolkit.paintBordersFor(tableEventFlow);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColStartDateTime = tableViewerColumn_4.getColumn();
		ColStartDateTime.setWidth(100);
		ColStartDateTime.setText(STARTDATETIME);
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getStartDateTime().getDateRepresentation() + " " +
						obj.getDuration().getStartDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColEndDateTime = tableViewerColumn_3.getColumn();
		ColEndDateTime.setWidth(100);
		ColEndDateTime.setText(ENDDATETIME);
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getEndDateTime().getDateRepresentation() + " " + 
						obj.getDuration().getEndDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColActivity = tableViewerColumn_2.getColumn();
		ColActivity.setWidth(100);
		ColActivity.setText(ACTIVITY);
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getActivityName();
			}
		});
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColVenue = tableViewerColumn_1.getColumn();
		ColVenue.setWidth(100);
		ColVenue.setText(VENUE);
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getVenueName();
			}
		});
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColNote = tableViewerColumn.getColumn();
		ColNote.setWidth(100);
		ColNote.setText(NOTE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getUserNote();
			}
		});
		
		Button btnReturnToEvent = new Button(mainComposite, SWT.NONE);
		btnReturnToEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Update the event flow in the database as well as in the event itself
				ModelEventFlow.saveEventFlow(listEventFlow);
				eventObj.setEventFlow(listEventFlow);
				
				// Return to the main GUI
				ViewMain.ReturnView();
			}
		});
		btnReturnToEvent.setBounds(545, 299, 129, 25);
		toolkit.adapt(btnReturnToEvent, true, true);
		btnReturnToEvent.setText("Return to Event page");
		
		Button btnAdd = new Button(mainComposite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputEventFlowDialog inputDialog = new InputEventFlowDialog(new Shell(), null,
						eventObj.getBVI_list());
				EventFlowEntry newEntry = inputDialog.open();
				
				// User might press 'Cancel' because they do not want to create a new entry anymore!
				if(newEntry != null)
				{
					EventFlowEntry.insertSortedList(listEventFlow, newEntry);
					tableViewEventFlow.refresh();
				}
			}
		});
		btnAdd.setBounds(571, 10, 75, 25);
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		
		Label lblNewLabel = new Label(mainComposite, SWT.NONE);
		lblNewLabel.setBounds(10, 304, 66, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("Output file:");
		
		textFilePath = new Text(mainComposite, SWT.BORDER);
		textFilePath.setBounds(76, 301, 189, 21);
		toolkit.adapt(textFilePath, true, true);

		Button btnNewButton_1 = new Button(mainComposite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fileDialog.setFilterExtensions(extension);
				
				String filePath = fileDialog.open();
				if (filePath != null) 
				{
					if (filePath.toLowerCase().endsWith(".csv") == false);
						filePath += ".csv";
					textFilePath.setText(filePath);
				}
			}
		});
		btnNewButton_1.setBounds(279, 299, 75, 25);
		toolkit.adapt(btnNewButton_1, true, true);
		btnNewButton_1.setText("Browse");
		
		Button btnExport = new Button(mainComposite, SWT.NONE);
		btnExport.setBounds(360, 299, 75, 25);
		toolkit.adapt(btnExport, true, true);
		btnExport.setText("Export");
		
		tableViewEventFlow.setInput(listEventFlow);
		tableViewEventFlow.refresh();
	}
	
	/**
	 * This is part of a listen when a user double-click or enter an entry
	 */
	private void listenToViewAction()
	{
		int chosenIndex = tableEventFlow.getSelectionIndex();
		if(chosenIndex >= 0)
		{
			EventFlowEntry chosenEntry = listEventFlow.get(chosenIndex);
			
			InputEventFlowDialog inputDialog = new InputEventFlowDialog(new Shell(),
					chosenEntry, eventObj.getBVI_list());
			EventFlowEntry newEntry = inputDialog.open();
			
			// There may be modification in the chosen EventFlowEntry object
			if(newEntry != null)
			{
				chosenEntry = newEntry;
				tableViewEventFlow.refresh();
			}
		}
	}
	public static void main(String[] args)
	{
		Display display = new Display();
		display = Display.getDefault();
		Shell shell = new Shell();
		
		Eventitem event = new Eventitem("Fundraising", 2012, 3, 20, 9, 17, 2012, 4, 19, 5, 25, "Fundraising Event");
		event.addBVI(new BookedVenueInfo("SoC", 15, "NUS", "Cool", 100, 10, 
				new TimeSlot(new MyDateTime(2012, 5, 15, 7, 0), new MyDateTime(2012, 5, 15, 9, 0))));
		event.addBVI(new BookedVenueInfo("Biz", 19, "NUS", "Cool", 100, 10, 
				new TimeSlot(new MyDateTime(2012, 5, 15, 7, 0), new MyDateTime(2012, 5, 15, 9, 0))));
		event.addBVI(new BookedVenueInfo("The Deck", 25, "NUS", "Cool", 100, 10, 
				new TimeSlot(new MyDateTime(2012, 5, 15, 7, 0), new MyDateTime(2012, 5, 15, 9, 0))));
		
		ViewEventFlow viewFlow = new ViewEventFlow(shell, SWT.NONE, event);
		viewFlow.pack();
		shell.open();
		while (!shell.isDisposed()) { 
		if (!display.readAndDispatch()) display.sleep(); 
		} 
	}
}
