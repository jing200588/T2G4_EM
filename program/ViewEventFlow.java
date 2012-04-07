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

	private EventItem eventObj;
	private Vector<EventFlowEntry> listEventFlow;
	private Vector<EventFlowEntry> filterList;
	private Vector<Integer> filterIndices;
	private Text txtOuputTextFilePath;
	private Text txtImportTextFilePath;
	private Button btnFilter;
	private Label txtSave;
	private boolean isEntireListShowed;		// True if the table displays the whole list. False otherwise.

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventFlow(Composite parent, int style, EventItem event) {
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
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Showcard Gothic", 20, SWT.NORMAL));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Event Flow");

		VenueViewForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite compMain = new Composite(VenueViewForm.getBody(), SWT.NONE);
		compMain.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));

		toolkit.adapt(compMain);
		toolkit.paintBordersFor(compMain);
		compMain.setLayout(new FormLayout());

		Composite compTableComposite = new Composite(compMain, SWT.NONE);
		TableColumnLayout tcl_compTableComposite = new TableColumnLayout();
		compTableComposite.setLayout(tcl_compTableComposite);
		FormData fd_compTableComposite = new FormData();
		fd_compTableComposite.bottom = new FormAttachment(80, -13);
		fd_compTableComposite.right = new FormAttachment(80);
		fd_compTableComposite.top = new FormAttachment(0, 10);
		fd_compTableComposite.left = new FormAttachment(5);
		compTableComposite.setLayoutData(fd_compTableComposite);
		toolkit.adapt(compTableComposite);
		toolkit.paintBordersFor(compTableComposite);

		tableViewEventFlow = new TableViewer(compTableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableEventFlow = tableViewEventFlow.getTable();
		tableEventFlow.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
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
		TableColumn tableColumnStartDateTime = tableViewerColumnStartDT.getColumn();
		tableColumnStartDateTime.setAlignment(SWT.CENTER);
		tcl_compTableComposite.setColumnData(tableColumnStartDateTime, new ColumnWeightData(15));
		//ColStartDateTime.pack();
		tableColumnStartDateTime.setText(STARTDATETIME);
		tableViewerColumnStartDT.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getStartDateTime().getDateRepresentation() + " " +
				obj.getDuration().getStartDateTime().getTimeRepresentation();
			}
		});

		TableViewerColumn tableViewerColumnEndDT = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn tableColumnEndDateTime = tableViewerColumnEndDT.getColumn();
		tableColumnEndDateTime.setAlignment(SWT.CENTER);
		tcl_compTableComposite.setColumnData(tableColumnEndDateTime, new ColumnWeightData(15));
		//ColEndDateTime.pack();
		tableColumnEndDateTime.setText(ENDDATETIME);
		tableViewerColumnEndDT.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getEndDateTime().getDateRepresentation() + " " + 
				obj.getDuration().getEndDateTime().getTimeRepresentation();
			}
		});

		TableViewerColumn tableViewerColumnActivity = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn tableColumnActivity = tableViewerColumnActivity.getColumn();
		tcl_compTableComposite.setColumnData(tableColumnActivity, new ColumnWeightData(20));
		tableColumnActivity.setText(ACTIVITY);
		tableViewerColumnActivity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getActivityName();
			}
		});

		TableViewerColumn tableViewerColumnVenue = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn tableColumnVenue = tableViewerColumnVenue.getColumn();
		tcl_compTableComposite.setColumnData(tableColumnVenue, new ColumnWeightData(20));
		tableColumnVenue.setText(VENUE);
		tableViewerColumnVenue.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getVenueName();
			}
		});

		TableViewerColumn tableViewerColumnNote = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn tableColumnNote = tableViewerColumnNote.getColumn();
		tcl_compTableComposite.setColumnData(tableColumnNote, new ColumnWeightData(20));
		tableColumnNote.setText(NOTE);
		tableViewerColumnNote.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getUserNote();
			}
		});

		Button btnBack = new Button(compMain, SWT.NONE);
		btnBack.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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

				if(choice == null)
					return;

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

		Button btnAdd = new Button(compMain, SWT.NONE);
		btnAdd.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		fd_btnBack.left = new FormAttachment(btnAdd, 0, SWT.LEFT);
		fd_btnBack.right = new FormAttachment(btnAdd, 0, SWT.RIGHT);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.right = new FormAttachment(95);
		fd_btnAdd.left = new FormAttachment(compTableComposite, 10);
		fd_btnAdd.width = 100;
		fd_btnAdd.top = new FormAttachment(0, 10);
		//		fd_btnAdd.left = new FormAttachment(0, 571);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputEventFlowDialog inputDialog = new InputEventFlowDialog(new Shell(), null,
						eventObj.getBVIList());
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

		Composite compBelow = new Composite(compMain, SWT.NONE);
		FormData fd_compBelow = new FormData();
		fd_compBelow.top = new FormAttachment(compTableComposite);
		fd_compBelow.bottom = new FormAttachment(100);
		fd_compBelow.left = new FormAttachment(compTableComposite, 0, SWT.LEFT);
		fd_compBelow.right = new FormAttachment(80, 10);
		compBelow.setLayoutData(fd_compBelow);
		toolkit.adapt(compBelow);
		toolkit.paintBordersFor(compBelow);
		compBelow.setLayout(new GridLayout(4, false));

		txtSave = new Label(compBelow, SWT.NONE);
		txtSave.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		txtSave.setText("The list is not saved.");

		Label lblInput = new Label(compBelow, SWT.NONE);
		lblInput.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblInput.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInput.setText("Input file:");
		toolkit.adapt(lblInput, true, true);

		txtImportTextFilePath = new Text(compBelow, SWT.BORDER);
		txtImportTextFilePath.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtImportTextFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtImportTextFilePath, true, true);

		Button btnBrowseTop = new Button(compBelow, SWT.NONE);
		btnBrowseTop.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnBrowseTop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fileDialog.setFilterExtensions(extension);

				String filePath = fileDialog.open();
				if(filePath != null)
					txtImportTextFilePath.setText(filePath);
			}
		});
		GridData gd_btnBrowseTop = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnBrowseTop.widthHint = 60;
		btnBrowseTop.setLayoutData(gd_btnBrowseTop);
		btnBrowseTop.setText("Browse");
		toolkit.adapt(btnBrowseTop, true, true);

		Button btnImportTop = new Button(compBelow, SWT.NONE);
		btnImportTop.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnImportTop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					if(txtImportTextFilePath.getText().equals("") == true)
						throw new Exception("You have not specified the path of the imported file");

					boolean overwrite = false;

					if(listEventFlow.isEmpty() == false)
					{
						TwoChoiceDialog message = new TwoChoiceDialog(new Shell(), "Message",
								"Do you want to append the existing list or overwrite it?", 
								"Append", "Overwrite");
						String decision = (String) message.open();

						if(decision == null)
							return;

						if(decision.equals("Append") == true)
							overwrite = false;
						else
							overwrite = true;
					}

					Vector<EventFlowEntry> newList = importCSVFile(txtImportTextFilePath.getText());

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
		GridData gd_btnImportTop = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnImportTop.widthHint = 60;
		btnImportTop.setLayoutData(gd_btnImportTop);
		btnImportTop.setText("Import");
		toolkit.adapt(btnImportTop, true, true);	

		Label lblOutputfile = new Label(compBelow, SWT.NONE);
		lblOutputfile.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblOutputfile, true, true);
		lblOutputfile.setText("Output file:");

		txtOuputTextFilePath = new Text(compBelow, SWT.BORDER);
		txtOuputTextFilePath.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtOuputTextFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtOuputTextFilePath, true, true);

		Button btnBrowseBottom = new Button(compBelow, SWT.NONE);
		btnBrowseBottom.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnBrowseBottom = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnBrowseBottom.widthHint = 60;
		btnBrowseBottom.setLayoutData(gd_btnBrowseBottom);
		btnBrowseBottom.addSelectionListener(new SelectionAdapter() {
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
					txtOuputTextFilePath.setText(filePath);
				}
			}
		});
		toolkit.adapt(btnBrowseBottom, true, true);
		btnBrowseBottom.setText("Browse");

		Button btnExportBottom = new Button(compBelow, SWT.NONE);
		btnExportBottom.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnExportBottom = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnExportBottom.widthHint = 60;
		btnExportBottom.setLayoutData(gd_btnExportBottom);
		btnExportBottom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try 
				{
					if(txtOuputTextFilePath.getText().equals("") == true)
						throw new Exception("You have not specified the path of the exported file");

					Vector<EventFlowEntry> exportList = listEventFlow;
					if(isEntireListShowed == false)
					{
						TwoChoiceDialog message = new TwoChoiceDialog(new Shell(), "Message", 
								"Do you want to export the filtered list or the entire list?",
								"Filtered List", "Entire List");
						String choice = (String) message.open();

						if(choice == null)
							return;

						if(choice.equals("Filtered List") == true)
							exportList = filterList;
					}

					CSVWriter writer = new CSVWriter(new FileWriter(txtOuputTextFilePath.getText()));

					writer.writeNext(COLUMN_PROPS);

					String[] line = new String[COLUMN_PROPS.length];

					for (int index = 0; index < exportList.size(); index++) 
					{
						line[0] = exportList.get(index).getDuration().getStartDateTime().getDateRepresentation()
								+ " " + exportList.get(index).getDuration().getStartDateTime().getTimeRepresentation();
						line[1] = exportList.get(index).getDuration().getEndDateTime().getDateRepresentation()
								+ " " + exportList.get(index).getDuration().getEndDateTime().getTimeRepresentation();
						line[2] = exportList.get(index).getActivityName();
						line[3] = exportList.get(index).getVenueName();
						line[4] = exportList.get(index).getUserNote();

						writer.writeNext(line);
					}

					writer.close();
					new ErrorMessageDialog(new Shell(), "The file was exported successfully!", "Success").open();  		
				} 
				catch (Exception exception) 
				{
					ErrorMessageDialog errorBoard = null;
					// TODO Auto-generated catch block
					if(exception.getMessage().equals("") == true)
						errorBoard = new ErrorMessageDialog(new Shell(), "There was an error exporting the file.");
					else
						errorBoard = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
				}
			}
		});
		toolkit.adapt(btnExportBottom, true, true);
		btnExportBottom.setText("Export");

		// Set input for table viewer at the beginning
		tableViewEventFlow.setInput(listEventFlow);
		toolkit.paintBordersFor(tableEventFlow);	

		Button btnSave = new Button(compMain, SWT.NONE);
		btnSave.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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
		new Label(compBelow, SWT.NONE);
		new Label(compBelow, SWT.NONE);
		new Label(compBelow, SWT.NONE);
		new Label(compBelow, SWT.NONE);


		btnFilter = new Button(compMain, SWT.NONE);
		btnFilter.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					if(btnFilter.getText().equals("Filter") == true)
					{
						InputFilterDialog filterDialog = new InputFilterDialog(new Shell(), SWT.NONE,
								eventObj.getBVIList());
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
		btnBrowseTop.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnImportTop.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		compMain.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnBack.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		compBelow.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnAdd.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnBrowseBottom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnExportBottom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblOutputfile.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
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
					chosenEntry, eventObj.getBVIList());
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

		txtSave.setText("Last saved by " + currentTime.getDateRepresentation() + " " + 
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
				String [] startDateTimeArr = HelperFunctions.convertMultiToSingleLine(
						allRows.get(index)[0]).split(delimiter);
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
				String [] endDateTimeArr = HelperFunctions.convertMultiToSingleLine(
						allRows.get(index)[1]).split(delimiter);
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
				String activityName = HelperFunctions.convertMultiToSingleLine(
						allRows.get(index)[2]);
				// Read the user's note
				String note = HelperFunctions.convertMultiToSingleLine(
						allRows.get(index)[4]);
				// Read the venue
				String venueName = HelperFunctions.convertMultiToSingleLine(
						allRows.get(index)[3]);
				int venueIndex = getVenueID(eventObj.getBVIList(), venueName);
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
					venueName = eventObj.getBVIList().get(venueIndex).getName();
					venueID = eventObj.getBVIList().get(venueIndex).getVenueID();
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
}
