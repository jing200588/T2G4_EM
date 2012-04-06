package program;

import event.*;
import dialog.*;
import venue.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
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

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.layout.TableColumnLayout;

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
	private Vector<EventFlowEntry> filterList;
	private Vector<Integer> filterIndices;
	private Text textFilePath;
	private Text importTextFilePath;
	private Button btnFilter;
	private Label textSave;
	private boolean isEntireListShowed;		// True if the table displays the whole list. False otherwise.
	
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
		// Note that listEventFlow and eventObj.eventFlow are different lists
		listEventFlow = eventObj.getEventFlow();
		isEntireListShowed = true;
		
		// Continue with the GUI
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
				
		Form VenueViewForm = toolkit.createForm(this);
		VenueViewForm.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
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
		fd_tableComposite.bottom = new FormAttachment(80, -13);
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
						DeleteConfirmDialog deleteDialog = new DeleteConfirmDialog(new Shell(),
								DeleteConfirmDialog.STATE_TYPE, "this entry");
						int decision = (Integer) deleteDialog.open();
						if(decision == 1)
						{
							// Delete the entry
							if(isEntireListShowed == true)
								listEventFlow.remove(chosenIndex);
							else
							{
								listEventFlow.remove((int) filterIndices.get(chosenIndex));
								
								// Delete in the filter list as well as update new indices
								int wholeTableIndex = filterIndices.get(chosenIndex);
								filterList.remove(chosenIndex);
								filterIndices.remove(chosenIndex);
								for(int index = 0; index < filterIndices.size(); index++)
									if(filterIndices.get(index) > wholeTableIndex)
									{
										filterIndices.set(index, filterIndices.get(index) - 1);
									}
										
							}
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
		
		TableViewerColumn tableViewerColumnStartDT = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColStartDateTime = tableViewerColumnStartDT.getColumn();
		ColStartDateTime.setAlignment(SWT.CENTER);
		tcl_tableComposite.setColumnData(ColStartDateTime, new ColumnWeightData(15));
		//ColStartDateTime.pack();
		ColStartDateTime.setText(STARTDATETIME);
		tableViewerColumnStartDT.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getStartDateTime().getDateRepresentation() + " " +
						obj.getDuration().getStartDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumnEndDT = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColEndDateTime = tableViewerColumnEndDT.getColumn();
		ColEndDateTime.setAlignment(SWT.CENTER);
		tcl_tableComposite.setColumnData(ColEndDateTime, new ColumnWeightData(15));
		//ColEndDateTime.pack();
		ColEndDateTime.setText(ENDDATETIME);
		tableViewerColumnEndDT.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getEndDateTime().getDateRepresentation() + " " + 
						obj.getDuration().getEndDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumnActivity = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColActivity = tableViewerColumnActivity.getColumn();
		tcl_tableComposite.setColumnData(ColActivity, new ColumnWeightData(20));
		ColActivity.setText(ACTIVITY);
		tableViewerColumnActivity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getActivityName();
			}
		});
		
		TableViewerColumn tableViewerColumnVenue = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColVenue = tableViewerColumnVenue.getColumn();
		tcl_tableComposite.setColumnData(ColVenue, new ColumnWeightData(20));
		ColVenue.setText(VENUE);
		tableViewerColumnVenue.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getVenueName();
			}
		});
		
		TableViewerColumn tableViewerColumnNote = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColNote = tableViewerColumnNote.getColumn();
		tcl_tableComposite.setColumnData(ColNote, new ColumnWeightData(20));
		ColNote.setText(NOTE);
		tableViewerColumnNote.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getUserNote();
			}
		});
		
		Button btnBack = new Button(mainComposite, SWT.NONE);
		FormData fd_btnBack = new FormData();
		fd_btnBack.width = 100;
	//	fd_btnReturnToEvent.left = new FormAttachment(0, 545);
		btnBack.setLayoutData(fd_btnBack);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Ask user whether you want to save data or not
				TwoChoiceDialog saveDialog = new TwoChoiceDialog(new Shell(), "Message", 
						"Do you want to save your table of event flow entries?", "Yes", "No");
				String choice = (String) saveDialog.open();
				
				// Update the event flow in the database as well as in the event itself
				if(choice.equals("Yes") == true)
				{
					ModelEventFlow.saveEventFlow(eventObj.getID(), listEventFlow);
					eventObj.setEventFlow(listEventFlow);
				}
				
				// Return to the main GUI
				ViewMain.ReturnView();
			}
		});
		toolkit.adapt(btnBack, true, true);
		btnBack.setText("Back");
		
		Button btnAdd = new Button(mainComposite, SWT.NONE);
		fd_btnBack.left = new FormAttachment(btnAdd, 0, SWT.LEFT);
		fd_btnBack.right = new FormAttachment(btnAdd, 0, SWT.RIGHT);
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
						eventObj.getBviList());
				EventFlowEntry newEntry = inputDialog.open();
				
				// User might press 'Cancel' because they do not want to create a new entry anymore!
				if(newEntry != null)
				{
					ControllerEventFlow.insertSortedList(listEventFlow, newEntry);
					// Show the whole list again.
					tableViewEventFlow.setInput(listEventFlow);
					tableViewEventFlow.refresh();
					
					btnFilter.setText("Filter");
					
					isEntireListShowed = true;
				}
			}
		});
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		
		Composite composite = new Composite(mainComposite, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(tableComposite);
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.left = new FormAttachment(tableComposite, 0, SWT.LEFT);
		fd_composite.right = new FormAttachment(80, 10);
		composite.setLayoutData(fd_composite);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(4, false));
		
		textSave = new Label(composite, SWT.NONE);
		textSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		textSave.setText("The list is not saved.");
		
		Label lblInput = new Label(composite, SWT.NONE);
		lblInput.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInput.setText("Input file:");
		toolkit.adapt(lblInput, true, true);
		
		importTextFilePath = new Text(composite, SWT.BORDER);
		importTextFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(importTextFilePath, true, true);
		
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fileDialog.setFilterExtensions(extension);
				
				String filePath = fileDialog.open();
				if(filePath != null)
					importTextFilePath.setText(filePath);
			}
		});
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 60;
		button.setLayoutData(gd_button);
		button.setText("Browse");
		toolkit.adapt(button, true, true);
		
		Button btnImport = new Button(composite, SWT.NONE);
		btnImport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					boolean overwrite = false;
					
					if(listEventFlow.isEmpty() == false)
					{
						TwoChoiceDialog message = new TwoChoiceDialog(new Shell(), "Message",
								"Do you want to append the existing list or overwrite it?", 
								"Append", "Overwrite");
						String decision = (String) message.open();
						if(decision.equals("Append") == true)
							overwrite = false;
						else
							overwrite = true;
					}
					
					Vector<EventFlowEntry> newList = importCSVFile(importTextFilePath.getText());
					
					if(overwrite == true)
					{
						listEventFlow = newList;
						eventObj.setEventFlow(newList);
					}
					else
					{
						listEventFlow.addAll(newList);
					}
					
					Collections.sort(listEventFlow);
					
					tableViewEventFlow.setInput(listEventFlow);
					tableViewEventFlow.refresh();
				}
				catch(Exception exception)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), 
							exception.getMessage());
					errorBoard.open();
				}
			}
		});
		GridData gd_btnImport = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnImport.widthHint = 60;
		btnImport.setLayoutData(gd_btnImport);
		btnImport.setText("Import");
		toolkit.adapt(btnImport, true, true);	
		
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
					if (filePath.toLowerCase().endsWith(".csv") == false)
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
					new ErrorMessageDialog(new Shell(), "The file was exported successfully!").open();  		
				} 
				catch (Exception excetion) 
				{
					// TODO Auto-generated catch block
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), 
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
		
		Button btnSave = new Button(mainComposite, SWT.NONE);
		fd_btnBack.top = new FormAttachment(btnSave, 10);
		
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Update in the database
				ModelEventFlow.saveEventFlow(eventObj.getID(), listEventFlow);
				eventObj.setEventFlow(listEventFlow);
				
				showSaveStatus();
			}
		});
		btnSave.setSelection(true);
		FormData fd_btnSave = new FormData();
		fd_btnSave.right = new FormAttachment(btnAdd, 0, SWT.RIGHT);
		fd_btnSave.left = new FormAttachment(btnAdd, 0, SWT.LEFT);
		fd_btnSave.width = 100;
		btnSave.setLayoutData(fd_btnSave);
		toolkit.adapt(btnSave, true, true);
		btnSave.setText("Save");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		
		btnFilter = new Button(mainComposite, SWT.NONE);
		btnFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					if(btnFilter.getText().equals("Filter") == true)
					{
						InputFilterDialog filterDialog = new InputFilterDialog(new Shell(), SWT.NONE,
								eventObj.getBviList());
						String[] outputStr = (String []) filterDialog.open();
						
						if(outputStr == null)
							return;			// Nothing to do.
						// Check if all elements in outputStr are null
						boolean allNull = true;
						for(int index = 0; index < 4; index++)
							if(outputStr[index] != null)
							{
								allNull = false;
								break;
							}
						if(allNull == true)
							return;
						
						// Filter by time slot first
						Vector<Integer> indexVector = new Vector<Integer>();
						if(outputStr[0] != null && outputStr[1] != null)
						{
							TimeSlot inputTimeSlot = new TimeSlot(new MyDateTime(outputStr[0]),
									new MyDateTime(outputStr[1]));
							indexVector = ControllerEventFlow.filterByTimeSlot(listEventFlow, inputTimeSlot);
						}
						
						// Next, filter by activity name
						if(outputStr[2] != null)
						{
							if(indexVector.isEmpty() == true)
								indexVector = ControllerEventFlow.filterByActivityName(listEventFlow, outputStr[2]);
							else
								// If after filtering by time slot, we have some candidates. Then we filter
								// in the result of the first step.s
								indexVector = ControllerEventFlow.filterByActivityName(listEventFlow, 
										indexVector, outputStr[2]);
						}
						
						// Finally, filter by venue ID.
						if(outputStr[3] != null)
						{
							int venueID = Integer.parseInt(outputStr[3]);
							if(indexVector.isEmpty() == true)
								indexVector = ControllerEventFlow.filterByVenue(listEventFlow, venueID);
							else
								indexVector = ControllerEventFlow.filterByVenue(listEventFlow, indexVector, venueID);
						}
						
						filterIndices = indexVector;
						filterList = ControllerEventFlow.selectEntry(listEventFlow, filterIndices);
						
						if(filterList.isEmpty() == true)
						{
							ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(),
									"There is no result satisfying your criteria", "Message");
							errorBoard.open();
						}
						else
						{
							// Show the result
							tableViewEventFlow.setInput(filterList);
							tableViewEventFlow.refresh();
							btnFilter.setText("Show All");
							
							isEntireListShowed = false;
						}
					}
					else
					{
						// The option is "Show All"
						tableViewEventFlow.setInput(listEventFlow);
						tableViewEventFlow.refresh();
						btnFilter.setText("Filter");
						
						isEntireListShowed = true;
					}
				}
				catch(Exception exception)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
				}
			}
		});
		FormData fd_btnFilter = new FormData();
		fd_btnFilter.left = new FormAttachment(btnAdd, 0, SWT.LEFT);
		fd_btnSave.top = new FormAttachment(btnFilter, 10);
		fd_btnFilter.width = 100;
		fd_btnFilter.right = new FormAttachment(btnAdd, 0, SWT.RIGHT);
		fd_btnFilter.top = new FormAttachment(btnAdd, 10);
		btnFilter.setLayoutData(fd_btnFilter);
		toolkit.adapt(btnFilter, true, true);
		btnFilter.setText("Filter");

		VenueViewForm.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		VenueViewForm.getBody().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblInput.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		button.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnImport.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		mainComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnBack.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnAdd.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnNewButton_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnExport.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnFilter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnSave.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			
	}
	
	/**
	 * This is part of a listen when a user double-click or enter an entry
	 */
	private void listenToViewAction()
	{
		int chosenIndex = tableEventFlow.getSelectionIndex();
		if(chosenIndex >= 0)
		{
			EventFlowEntry chosenEntry = null;		// Dummy value
			if(isEntireListShowed == true)
				chosenEntry = listEventFlow.get(chosenIndex);
			else
				chosenEntry = filterList.get(chosenIndex);
			
			InputEventFlowDialog inputDialog = new InputEventFlowDialog(new Shell(),
					chosenEntry, eventObj.getBviList());
			EventFlowEntry newEntry = inputDialog.open();
			
			// There may be modification in the chosen EventFlowEntry object
			if(newEntry != null)
			{
				// First determine which list is displayed on the table (the whole list or filtered list)
				if(isEntireListShowed == true)
				{
					listEventFlow.remove(chosenIndex);
					System.out.println("chosenIndex = " + chosenIndex);
				}
				else
				{
					listEventFlow.remove((int) filterIndices.get(chosenIndex));
					for(int i = 0; i < listEventFlow.size(); i++)
						System.out.println(listEventFlow.get(i).getActivityName());
					System.out.println("delete at index = " + filterIndices.get(chosenIndex));
				}
				ControllerEventFlow.insertSortedList(listEventFlow, newEntry);
				
				// Show the whole list again
				tableViewEventFlow.setInput(listEventFlow);
				tableViewEventFlow.refresh();
				
				btnFilter.setText("Filter");
				
				isEntireListShowed = true;
			}
		}
	}
	
	/**
	 * This method is to update the last time the user saves his list.
	 */
	private void showSaveStatus()
	{
		MyDateTime currentTime = MyDateTime.getCurrentDateTime();
		
		textSave.setText("Last saved by " + currentTime.getDateRepresentation() + " " + 
				currentTime.getTimeRepresentation());
	}
	
	/**
	 * Imports a list of EventFlowEntry objects from a CSV file.
	 * 
	 * @param filePath - String
	 * @return listEntry - Vector<EventFlowEntry>
	 * @throws Exception if the input file is not in the correct format.
	 */
	private Vector<EventFlowEntry> importCSVFile(String filePath) throws Exception
	{
		try
		{
			CSVReader readerFile = new CSVReader(new FileReader(filePath));
		
			List<String[]> allRows = readerFile.readAll();
			
			if(allRows.get(0).length != COLUMN_PROPS.length)
				throw new Exception("The number of columns in the CSV file does not match! It should have "
						+ COLUMN_PROPS.length + " columns");
			
			Vector<EventFlowEntry> newList = new Vector<EventFlowEntry>();
			String delimiter = "[ /:]+";
			// allRows.get(0) is the headers
			for(int index = 1; index < allRows.size(); index++)
			{
				// Read the start date time
				String [] startDateTimeArr = HelperFunctions.removeRedundantWhiteSpace(
						HelperFunctions.replaceNewLine(allRows.get(index)[0])).split(delimiter);
				if(startDateTimeArr.length != 5)
					throw new Exception("The starting date time of entry at row " + (index + 1) +
							"is not in the correct format. It should be '<day>/<month>/<year> <hour>:<minute>'");
				int day = Integer.parseInt(startDateTimeArr[0]);
				int month = Integer.parseInt(startDateTimeArr[1]);
				int year = Integer.parseInt(startDateTimeArr[2]);
				int hour = Integer.parseInt(startDateTimeArr[3]);
				int minute = Integer.parseInt(startDateTimeArr[4]);
				if(MyDateTime.isValidDateTime(year, month, day, hour, minute) == false)
					throw new Exception("The starting date time of entry at row " + (index + 1) +
							"is not a valid date time");
				MyDateTime startDateTime = new MyDateTime(year, month, day, hour, minute);
				
				// Read the end date time
				String [] endDateTimeArr = HelperFunctions.removeRedundantWhiteSpace(
						HelperFunctions.replaceNewLine(allRows.get(index)[1])).split(delimiter);
				if(endDateTimeArr.length != 5)
					throw new Exception("The endinging date time of entry at row " + (index + 1) +
							"is not in the correct format. It should be '<day>/<month>/<year> <hour>:<minute>'");
				day = Integer.parseInt(endDateTimeArr[0]);
				month = Integer.parseInt(endDateTimeArr[1]);
				year = Integer.parseInt(endDateTimeArr[2]);
				hour = Integer.parseInt(endDateTimeArr[3]);
				minute = Integer.parseInt(endDateTimeArr[4]);
				if(MyDateTime.isValidDateTime(year, month, day, hour, minute) == false)
					throw new Exception("The ending date time of entry at row " + (index + 1) +
							"is not a valid date time");
				MyDateTime endDateTime = new MyDateTime(year, month, day, hour, minute);
				
				// Read the activity name
				String activityName = HelperFunctions.removeRedundantWhiteSpace(
						HelperFunctions.replaceNewLine(allRows.get(index)[2]));
				
				// Read the user's note
				String note = HelperFunctions.removeRedundantWhiteSpace(
						HelperFunctions.replaceNewLine(allRows.get(index)[4]));
				
				// Read the venue
				String venueName = HelperFunctions.removeRedundantWhiteSpace(
						HelperFunctions.replaceNewLine(allRows.get(index)[3]));
				int venueIndex = getVenueID(eventObj.getBviList(), venueName);
				int venueID = 0;		// Dummny value
				if(venueIndex < 0)
				{
					if(venueName.equalsIgnoreCase(InputEventFlowEntry.OTHERVENUE) == false)
						note = "Note on venue: " + venueName + ". " + note; 
					venueName = InputEventFlowEntry.OTHERVENUE;
					venueID = -1;
				}
				else
				{
					venueName = eventObj.getBviList().get(venueIndex).getName();
					venueID = eventObj.getBviList().get(venueIndex).getVenueID();
				}
				
				newList.add(new EventFlowEntry(new TimeSlot(startDateTime, endDateTime),
						activityName, venueName, venueID, note));
			}
			
			return newList;
		}
		catch(FileNotFoundException exception)
		{
			throw new Exception("The file path provided is not valid!");
		}
		catch(Exception exception)
		{
			throw exception;
		}
		
		
	}
	
	/**
	 * Returns the index of the venue whose name is venueName in the list of BookedVenueInfo objects.
	 * 
	 * @param listVenue - Vector<BookedVenueInfo>
	 * @param venueName - String
	 * @return index - Integer 
	 * @return -1 if there is no venue whose name is venueName in the list.
	 * 
	 * Assumption: There are two venues with the same name in the list.
	 */
	private int getVenueID(Vector<BookedVenueInfo> listVenue, String venueName)
	{
		venueName = venueName.toUpperCase();
		
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getName().toUpperCase().equals(venueName) == true)
				return index;
		
		return -1;
	}
	/*
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
	*/
}
