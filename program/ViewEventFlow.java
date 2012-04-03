package program;

import event.*;
import dialog.*;
import venue.*;

import java.io.FileWriter;
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
import org.eclipse.jface.viewers.ColumnWeightData;
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

import au.com.bytecode.opencsv.CSVWriter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;

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
		
		/////////////////////////////////////////////////////////////////////////////////////
		/// 
		////////////////////////////////////////////////////////////////////////////////////
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// User presses Ctrl + S
				System.out.println(e.keyCode);
				if((e.stateMask & SWT.CTRL) != 0 && e.keyCode == (int) 's')
				{
					// Update in the database
					System.out.println("Press Crl + S");
					ModelEventFlow.saveEventFlow(eventObj.getID(), listEventFlow);
				}
			}
		});
		
		// Continue with the GUI
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
				
		Form VenueViewForm = toolkit.createForm(this);
		VenueViewForm.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		VenueViewForm.getBody().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Event Flow");

		VenueViewForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(VenueViewForm.getBody(), SWT.NONE);
		
		toolkit.adapt(mainComposite);
		toolkit.paintBordersFor(mainComposite);
		mainComposite.setLayout(new FormLayout());
		
		Composite tableComposite = new Composite(mainComposite, SWT.NONE);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);
		FormData fd_tableComposite = new FormData();
		fd_tableComposite.bottom = new FormAttachment(80);
		fd_tableComposite.right = new FormAttachment(80);
		fd_tableComposite.top = new FormAttachment(0, 10);
		fd_tableComposite.left = new FormAttachment(5);
		tableComposite.setLayoutData(fd_tableComposite);
		toolkit.adapt(tableComposite);
		toolkit.paintBordersFor(tableComposite);
		
		tableViewEventFlow = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
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
		tableViewEventFlow.setContentProvider(ArrayContentProvider.getInstance());
		toolkit.paintBordersFor(tableEventFlow);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColStartDateTime = tableViewerColumn_4.getColumn();
		ColStartDateTime.setAlignment(SWT.CENTER);
		tcl_tableComposite.setColumnData(ColStartDateTime, new ColumnWeightData(15));
		//ColStartDateTime.pack();
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
		ColEndDateTime.setAlignment(SWT.CENTER);
		tcl_tableComposite.setColumnData(ColEndDateTime, new ColumnWeightData(15));
		//ColEndDateTime.pack();
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
		tcl_tableComposite.setColumnData(ColActivity, new ColumnWeightData(20));
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
		tcl_tableComposite.setColumnData(ColVenue, new ColumnWeightData(20));
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
		tcl_tableComposite.setColumnData(ColNote, new ColumnWeightData(20));
		ColNote.setText(NOTE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getUserNote();
			}
		});
		
		Button btnReturnToEvent = new Button(mainComposite, SWT.NONE);
		FormData fd_btnReturnToEvent = new FormData();
		fd_btnReturnToEvent.width = 100;
		fd_btnReturnToEvent.left = new FormAttachment(tableComposite, 10);

		fd_btnReturnToEvent.right = new FormAttachment(95);
	//	fd_btnReturnToEvent.left = new FormAttachment(0, 545);
		btnReturnToEvent.setLayoutData(fd_btnReturnToEvent);
		btnReturnToEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Update the event flow in the database as well as in the event itself
				ModelEventFlow.saveEventFlow(eventObj.getID(), listEventFlow);
				eventObj.setEventFlow(listEventFlow);
				
				// Return to the main GUI
				ViewMain.ReturnView();
			}
		});
		toolkit.adapt(btnReturnToEvent, true, true);
		btnReturnToEvent.setText("Save and Return");
		
		Button btnAdd = new Button(mainComposite, SWT.NONE);
		fd_btnReturnToEvent.top = new FormAttachment(btnAdd, 10);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.right = new FormAttachment(95);
		fd_btnAdd.left = new FormAttachment(tableComposite, 10);
		fd_btnAdd.width = 100;
		fd_btnAdd.top = new FormAttachment(0, 10);
//		fd_btnAdd.left = new FormAttachment(0, 571);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputEventFlowDialog inputDialog = new InputEventFlowDialog(new Shell(), null,
						eventObj.getBVI_list());
				EventFlowEntry newEntry = inputDialog.open();
				
				// User might press 'Cancel' because they do not want to create a new entry anymore!
				if(newEntry != null)
				{
					ControllerEventFlow.insertSortedList(listEventFlow, newEntry);
					tableViewEventFlow.refresh();
				}
			}
		});
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		
		Composite composite = new Composite(mainComposite, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(tableComposite, 10);
		fd_composite.right = new FormAttachment(80, 5);
		fd_composite.left = new FormAttachment(5, -5);
		composite.setLayoutData(fd_composite);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(4, false));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("Output file:");
		
		textFilePath = new Text(composite, SWT.BORDER);
		textFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(textFilePath, true, true);

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 60;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
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
		toolkit.adapt(btnNewButton_1, true, true);
		btnNewButton_1.setText("Browse");
		
		Button btnExport = new Button(composite, SWT.NONE);
		GridData gd_btnExport = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnExport.widthHint = 60;
		btnExport.setLayoutData(gd_btnExport);
		btnExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try 
				{
					CSVWriter writer = new CSVWriter(new FileWriter(textFilePath.getText()));
					
					writer.writeNext(COLUMN_PROPS);
					
					String[] line = new String[COLUMN_PROPS.length];
					
					for (int index = 0; index < listEventFlow.size(); index++) 
					{
						line[0] = listEventFlow.get(index).getDuration().getStartDateTime().getDateRepresentation()
								+ " " + listEventFlow.get(index).getDuration().getStartDateTime().getTimeRepresentation();
						line[1] = listEventFlow.get(index).getDuration().getEndDateTime().getDateRepresentation()
								+ " " + listEventFlow.get(index).getDuration().getEndDateTime().getTimeRepresentation();
						line[2] = listEventFlow.get(index).getActivityName();
						line[3] = listEventFlow.get(index).getVenueName();
						line[4] = listEventFlow.get(index).getUserNote();
						
						writer.writeNext(line);
					}
					
					writer.close();
					new errormessageDialog(new Shell(), "The file was exported successfully!").open();  		
				} 
				catch (Exception excetion) 
				{
					// TODO Auto-generated catch block
					errormessageDialog errorBoard = new errormessageDialog(new Shell(), 
							"There was an error exporting the file.");
					errorBoard.open();
				}
			}
		});
		toolkit.adapt(btnExport, true, true);
		btnExport.setText("Export");
		
		// Set input for table viewer at the beginning
		tableViewEventFlow.setInput(listEventFlow);
		toolkit.paintBordersFor(tableEventFlow);
		
		mainComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnReturnToEvent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnAdd.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnNewButton_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnExport.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		Button btnNewButton = new Button(mainComposite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Update in the database
				ModelEventFlow.saveEventFlow(eventObj.getID(), listEventFlow);
			}
		});
		btnNewButton.setSelection(true);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.width = 100;
		fd_btnNewButton.right = new FormAttachment(btnReturnToEvent, 0, SWT.RIGHT);
		fd_btnNewButton.top = new FormAttachment(btnReturnToEvent, 11);
		fd_btnNewButton.left = new FormAttachment(tableComposite, 10);
		btnNewButton.setLayoutData(fd_btnNewButton);
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Save");
		

		
/*		tableViewEventFlow = new TableViewer(mainComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableEventFlow = tableViewEventFlow.getTable();
		FormData fd_tableEventFlow = new FormData();
		fd_tableEventFlow.bottom = new FormAttachment(80);
		fd_tableEventFlow.right = new FormAttachment(80);
		fd_tableEventFlow.top = new FormAttachment(0, 10);
		fd_tableEventFlow.left = new FormAttachment(5);
		tableEventFlow.setLayoutData(fd_tableEventFlow);*/
	
			
			


	
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
				listEventFlow.remove(chosenIndex);
				ControllerEventFlow.insertSortedList(listEventFlow, newEntry);
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
