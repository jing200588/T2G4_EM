package venue;

import event.*;
import dialog.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;

import java.util.Vector;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

import java.lang.NumberFormatException;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class ViewBookingSystem extends Composite {
	private final StackLayout sl_functionContentCompo = new StackLayout();
	private final StackLayout stackLayoutCriteria = new StackLayout();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Text lowerBoundCapacityText;
	private Text upperBoundCapacityText;
	private Text nameToSearchText;
	private Text lowerBoundCostText;
	private Text upperBoundCostText;
	private Composite searchNameCompo;
	private Composite searchCriteriaCompo;
	private Composite functionContentCompo;
	private Table DisplayTable;
	private TableViewer tableViewer;
	private boolean flagCapacityChoice;			
	private boolean flagCostChoice;				
	private boolean flagTimeSlotChoice;
	private Button TimeChoiceButton;
	private Button CostChoiceButton;
	private Button CapacityChoiceButton;
	private Button btnCriteriaConfirm;
	private Button btnFindCriteria;
	private int[] capacityChoiceInput;
	private int[] costChoiceInput;
	private TimeSlot timeSlotChoiceInput;
	private Composite functionOptionCompo;

	private Vector<Venue> searchResultList;
	private Button btnBookVenue;
	private Text venueDetailText;
	private Composite viewCompo;
	private int chosenVenueID;
	private EventItem event;
	private ControllerBookingSystem bookingSystem;
	private InputDateTimeComposite dtSearchCriteria;
	private InputDateTimeComposite dtSearchResult;
	private Composite capacityCompo;
	private boolean hasTimeSlotChecked;
	private Button SearchNameButton;
	private Button SearchCriteriaButton;
	private Composite resultPageCompo;
	private Composite compoCriteriaFilled;

	private int currentCompo;					// 0 for time slot, 1 for capacity, 2 for cost
	private Composite costCompo;
	private Composite timeSlotCompo;
	private Button btnNextCriteria;
	private Button btnBackCriteria;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewBookingSystem(Composite parent, int style, EventItem eventObj) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		// Initialize some variables
		event = eventObj;				// Assign reference
		flagCapacityChoice = false;
		flagCostChoice = false;
		flagTimeSlotChoice = false;
		timeSlotChoiceInput = null;
		capacityChoiceInput = null;
		costChoiceInput = null;
		hasTimeSlotChecked = false;
		searchResultList = new Vector<Venue>();
		chosenVenueID = -1;
		bookingSystem = new ControllerBookingSystem();
		currentCompo = -1;
		
		// Continue with the GUI
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Form VenueViewForm = toolkit.createForm(this);
//		VenueViewForm.getBody().setBackgroundMode(SWT.INHERIT_DEFAULT);
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Lithos Pro Regular", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Book Venue");
		VenueViewForm.getBody().setLayout(new FormLayout());

		Composite mainCompo = new Composite(VenueViewForm.getBody(), SWT.NONE);
		mainCompo.setLayout(new FormLayout());
		FormData fd_mainCompo = new FormData();
		fd_mainCompo.bottom = new FormAttachment(100);
		fd_mainCompo.left = new FormAttachment(0);
		fd_mainCompo.top = new FormAttachment(0);
		fd_mainCompo.right = new FormAttachment(100);
		mainCompo.setLayoutData(fd_mainCompo);
		toolkit.adapt(mainCompo);
		toolkit.paintBordersFor(mainCompo);


		functionOptionCompo = new Composite(mainCompo, SWT.NONE);
		functionOptionCompo.setLayout(new GridLayout(3, false));
		FormData fd_functionOptionCompo = new FormData();
		fd_functionOptionCompo.right = new FormAttachment(90);
		//fd_FunctionOptionCompo.bottom = new FormAttachment(0, 47);
		fd_functionOptionCompo.top = new FormAttachment(10);
		fd_functionOptionCompo.left = new FormAttachment(10);
		functionOptionCompo.setLayoutData(fd_functionOptionCompo);
		toolkit.adapt(functionOptionCompo);
		toolkit.paintBordersFor(functionOptionCompo);

		SearchNameButton = new Button(functionOptionCompo, SWT.RADIO);
		SearchNameButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		SearchNameButton.setSelection(true);
		SearchNameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sl_functionContentCompo.topControl = searchNameCompo;
				functionContentCompo.layout();
			}
		});
		toolkit.adapt(SearchNameButton, true, true);
		SearchNameButton.setText("Default Search");

		SearchCriteriaButton = new Button(functionOptionCompo, SWT.RADIO);
		SearchCriteriaButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		SearchCriteriaButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnCriteriaConfirm.setText("Confirm");
				btnNextCriteria.setEnabled(false);
				btnBackCriteria.setEnabled(false);
				btnFindCriteria.setEnabled(false);
				
				// Enable all the choice button
				CostChoiceButton.setEnabled(true);
				TimeChoiceButton.setEnabled(true);
				CapacityChoiceButton.setEnabled(true);
				
				sl_functionContentCompo.topControl = searchCriteriaCompo;
				functionContentCompo.layout();
				
				stackLayoutCriteria.topControl = timeSlotCompo;
				dtSearchCriteria.setEnabled(false);
				compoCriteriaFilled.layout();
			}
		});
		toolkit.adapt(SearchCriteriaButton, true, true);
		SearchCriteriaButton.setText("Advanced Search");

		Label labelEventTime = new Label(functionOptionCompo, SWT.NONE);
		labelEventTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(labelEventTime, true, true);
		String strEventTime = "Event time: FROM " + event.getStartDateTime().getDateRepresentation() +
				" " + event.getStartDateTime().getTimeRepresentation() + " TO " +
				event.getEndDateTime().getDateRepresentation() + " " + event.getEndDateTime().getTimeRepresentation();
		labelEventTime.setText(strEventTime);

		functionContentCompo = new Composite(mainCompo, SWT.NONE);
		FormData fd_functionContentCompo = new FormData();
		fd_functionContentCompo.top = new FormAttachment(functionOptionCompo, 20);
		fd_functionContentCompo.right = new FormAttachment(90);
		fd_functionContentCompo.bottom = new FormAttachment(90);
		fd_functionContentCompo.left = new FormAttachment(10);
		functionContentCompo.setLayoutData(fd_functionContentCompo);
	//	functionContentCompo.setBackgroundMode(SWT.INHERIT_DEFAULT);
		toolkit.adapt(functionContentCompo);
		toolkit.paintBordersFor(functionContentCompo);
		functionContentCompo.setLayout(sl_functionContentCompo);

		searchNameCompo = new Composite(functionContentCompo, SWT.NONE);
		searchNameCompo.setBounds(0, 5, 675, 280);
		toolkit.adapt(searchNameCompo);
		toolkit.paintBordersFor(searchNameCompo);
		searchNameCompo.setLayout(new FormLayout());
		sl_functionContentCompo.topControl = searchNameCompo;
		functionContentCompo.layout();
		
		Label lblSearchVenueBy = new Label(searchNameCompo, SWT.NONE);
		FormData fd_lblSearchVenueBy = new FormData();
		fd_lblSearchVenueBy.left = new FormAttachment(0, 5);
		lblSearchVenueBy.setLayoutData(fd_lblSearchVenueBy);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		Composite nameCompo = new Composite(searchNameCompo, SWT.NONE);
		FormData fd_nameCompo = new FormData();
		fd_nameCompo.right = new FormAttachment(100);
		fd_nameCompo.top = new FormAttachment(0, 95);
		fd_nameCompo.left = new FormAttachment(0);
		nameCompo.setLayoutData(fd_nameCompo);
		toolkit.adapt(nameCompo);
		toolkit.paintBordersFor(nameCompo);
		nameCompo.setLayout(new GridLayout(4, false));

		Label lblEnterTheName = new Label(nameCompo, SWT.NONE);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");

		nameToSearchText = new Text(nameCompo, SWT.BORDER);
		nameToSearchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(nameToSearchText, true, true);

		Button btnSearchName = new Button(nameCompo, SWT.NONE);
		btnSearchName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String venueName = HelperFunctions.removeRedundantWhiteSpace(nameToSearchText.getText());
				nameToSearchText.setText(venueName);
				if(venueName == null || venueName.equals("") == true)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), "You have not entered a venue name to search yet!");
					errorBoard.open();
				}
				else
				{
					// Ask the logic component to do the searching
					searchResultList = bookingSystem.findVenueByName(venueName);

					if(searchResultList.size() == 0)
					{
						ErrorMessageDialog messageBoard = new ErrorMessageDialog(new Shell(),
								"There is no result satisfying your criteria. Please do another searching",
								"Message");
						messageBoard.open();
						return;
					}
					else
					{
						// Reset chosenVenueID variable
						chosenVenueID = -1;

						tableViewer.setInput(searchResultList);	

						// GUI Setting
						// Note that if user chooses this option, he / she has not decided the time slot
						SearchNameButton.setSelection(false);
						resetResultPageView(false);
						
						sl_functionContentCompo.topControl = resultPageCompo;
						functionContentCompo.layout();
					}
				}
			}
		});
		toolkit.adapt(btnSearchName, true, true);
		btnSearchName.setText("Search and view information");

		searchCriteriaCompo = new Composite(functionContentCompo, SWT.NONE);
		searchCriteriaCompo.setBounds(0, 129, 331, 318);
		toolkit.adapt(searchCriteriaCompo);
		toolkit.paintBordersFor(searchCriteriaCompo);
		searchCriteriaCompo.setLayout(new FormLayout());
		
		Composite compoCriteria = new Composite(searchCriteriaCompo, SWT.NONE);
		compoCriteria.setLayout(new GridLayout(4, false));
		FormData fd_compoCriteria = new FormData();
		compoCriteria.setLayoutData(fd_compoCriteria);
		toolkit.adapt(compoCriteria);
		toolkit.paintBordersFor(compoCriteria);
		
				Label lblPleaseChooseYour = new Label(compoCriteria, SWT.NONE);
				lblPleaseChooseYour.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				toolkit.adapt(lblPleaseChooseYour, true, true);
				lblPleaseChooseYour.setText("Please choose your preferred criteria:");
						new Label(compoCriteria, SWT.NONE);
				
						CapacityChoiceButton = new Button(compoCriteria, SWT.CHECK);
						CapacityChoiceButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						CapacityChoiceButton.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								flagCapacityChoice = CapacityChoiceButton.getSelection();

								if(flagCapacityChoice == true)
									btnCriteriaConfirm.setEnabled(true);
								else
								{
									if(flagTimeSlotChoice == false && flagCostChoice == false)
										btnCriteriaConfirm.setEnabled(false);
								}
							}
						});
						toolkit.adapt(CapacityChoiceButton, true, true);
						CapacityChoiceButton.setText("Capacity");
								
										TimeChoiceButton = new Button(compoCriteria, SWT.CHECK);
										TimeChoiceButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
										TimeChoiceButton.addSelectionListener(new SelectionAdapter() {
											@Override
											public void widgetSelected(SelectionEvent e) {
												flagTimeSlotChoice = TimeChoiceButton.getSelection();

												if(flagTimeSlotChoice == true)
													btnCriteriaConfirm.setEnabled(true);
												else
												{
													if(flagCapacityChoice == false && flagCostChoice == false)
														btnCriteriaConfirm.setEnabled(false);
												}
											}
										});
										toolkit.adapt(TimeChoiceButton, true, true);
										TimeChoiceButton.setText("Time");
										
												CostChoiceButton = new Button(compoCriteria, SWT.CHECK);
												CostChoiceButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
												CostChoiceButton.addSelectionListener(new SelectionAdapter() {
													@Override
													public void widgetSelected(SelectionEvent e) {
														flagCostChoice = CostChoiceButton.getSelection();

														if(flagCostChoice == true)
															btnCriteriaConfirm.setEnabled(true);
														else
														{
															if(flagTimeSlotChoice == false && flagCapacityChoice == false)
																btnCriteriaConfirm.setEnabled(false);
														}
													}
												});
												toolkit.adapt(CostChoiceButton, true, true);
												CostChoiceButton.setText("Cost");
										
												btnCriteriaConfirm = new Button(compoCriteria, SWT.NONE);		
												GridData gd_btnCriteriaConfirm = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
												gd_btnCriteriaConfirm.widthHint = 80;
												btnCriteriaConfirm.setLayoutData(gd_btnCriteriaConfirm);
												btnCriteriaConfirm.setEnabled(false);
												btnCriteriaConfirm.addSelectionListener(new SelectionAdapter() {
													@Override
													public void widgetSelected(SelectionEvent e) {
														String commandType = btnCriteriaConfirm.getText();

														if(commandType.equals("Confirm") == true)
														{
															// Change the command type from "Confirm" to "Edit"
															btnCriteriaConfirm.setText("Edit");
															// Disable all the choice button
															CostChoiceButton.setEnabled(false);
															TimeChoiceButton.setEnabled(false);
															CapacityChoiceButton.setEnabled(false);
															
															// Display the following composites in order if they are chosen by the user
															if(flagTimeSlotChoice == true)
															{
																stackLayoutCriteria.topControl = timeSlotCompo;
																dtSearchCriteria.setEnabled(true);
																compoCriteriaFilled.layout();
																currentCompo = 0;
															}
															else
															{
																if(flagCapacityChoice == true)
																{
																	stackLayoutCriteria.topControl = capacityCompo;
																	compoCriteriaFilled.layout();
																	currentCompo = 1;
																}
																else
																{
																	if(flagCostChoice == true)
																	{
																		stackLayoutCriteria.topControl = costCompo;
																		compoCriteriaFilled.layout();
																		currentCompo = 2;
																	}
																}
															}
															
															btnNextCriteria.setEnabled(true);
															if(hasNextCriteria() <= 2)
															{
																btnNextCriteria.setText("Next");
															}
															else
																btnNextCriteria.setText("Confirm");
														}
														else
														{
															// Change command type to "Confirm"
															btnCriteriaConfirm.setText("Confirm");
															// Enable all the choice button
															CostChoiceButton.setEnabled(true);
															TimeChoiceButton.setEnabled(true);
															CapacityChoiceButton.setEnabled(true);
															
															// Change the layout
															stackLayoutCriteria.topControl = timeSlotCompo;
															dtSearchCriteria.setEnabled(false);
															compoCriteriaFilled.layout();
															
															// Reset the button next and back.
															btnNextCriteria.setText("Next");
															btnNextCriteria.setEnabled(false);
															btnBackCriteria.setEnabled(false);
															// Disable the button to execute the searching
															btnFindCriteria.setEnabled(false);
														}
													}
												});
												toolkit.adapt(btnCriteriaConfirm, true, true);
												btnCriteriaConfirm.setText("Confirm");

		compoCriteriaFilled = new Composite(searchCriteriaCompo, SWT.NONE);
		fd_compoCriteria.left = new FormAttachment(10,0);
		FormData fd_compoCriteriaFilled = new FormData();
		fd_compoCriteriaFilled.top = new FormAttachment(compoCriteria, 30);
		fd_compoCriteriaFilled.right = new FormAttachment(90);
		fd_compoCriteriaFilled.left = new FormAttachment(10);
		compoCriteriaFilled.setLayoutData(fd_compoCriteriaFilled);
		toolkit.adapt(compoCriteriaFilled);
		toolkit.paintBordersFor(compoCriteriaFilled);
		compoCriteriaFilled.setLayout(stackLayoutCriteria);

		capacityCompo = new Composite(compoCriteriaFilled, SWT.NONE);
		toolkit.adapt(capacityCompo);
		toolkit.paintBordersFor(capacityCompo);
		capacityCompo.setLayout(new FormLayout());

		Label lblEstimatedCapacity = new Label(capacityCompo, SWT.NONE);
		FormData fd_lblEstimatedCapacity = new FormData();
		fd_lblEstimatedCapacity.top = new FormAttachment(0, 5);
		fd_lblEstimatedCapacity.left = new FormAttachment(0, 5);
		lblEstimatedCapacity.setLayoutData(fd_lblEstimatedCapacity);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		Composite inputCapacityCompo = new Composite(capacityCompo, SWT.NONE);
		inputCapacityCompo.setLayout(new GridLayout(4, false));
		FormData fd_inputCapacityCompo = new FormData();
		fd_inputCapacityCompo.right = new FormAttachment(100);
		fd_inputCapacityCompo.top = new FormAttachment(50);
		fd_inputCapacityCompo.left = new FormAttachment(10);
		inputCapacityCompo.setLayoutData(fd_inputCapacityCompo);
		toolkit.adapt(inputCapacityCompo);
		toolkit.paintBordersFor(inputCapacityCompo);
		
		Label lblCapacityFrom = new Label(inputCapacityCompo, SWT.NONE);
		lblCapacityFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lblCapacityFrom, true, true);
		lblCapacityFrom.setText("Capacity from:");

		lowerBoundCapacityText = new Text(inputCapacityCompo, SWT.BORDER);
		lowerBoundCapacityText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lowerBoundCapacityText, true, true);
		
				Label lblNewLabel = new Label(inputCapacityCompo, SWT.NONE);
				lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				toolkit.adapt(lblNewLabel, true, true);
				lblNewLabel.setText("to");

		upperBoundCapacityText = new Text(inputCapacityCompo, SWT.BORDER);
		upperBoundCapacityText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(upperBoundCapacityText, true, true);

		costCompo = new Composite(compoCriteriaFilled, SWT.NONE);
		toolkit.adapt(costCompo);
		toolkit.paintBordersFor(costCompo);
		costCompo.setLayout(new FormLayout());

		Label lblPreferredCost = new Label(costCompo, SWT.NONE);
		FormData fd_lblPreferredCost = new FormData();
		fd_lblPreferredCost.top = new FormAttachment(0, 5);
		fd_lblPreferredCost.left = new FormAttachment(0, 5);
		lblPreferredCost.setLayoutData(fd_lblPreferredCost);
		lblPreferredCost.setText("Preferred cost:");
		toolkit.adapt(lblPreferredCost, true, true);
		
		Composite inputCostCompo = new Composite(costCompo, SWT.NONE);
		inputCostCompo.setLayout(new GridLayout(4, false));
		FormData fd_inputCostCompo = new FormData();
		fd_inputCostCompo.right = new FormAttachment(100);
		fd_inputCostCompo.top = new FormAttachment(50);
		fd_inputCostCompo.left = new FormAttachment(10);
		inputCostCompo.setLayoutData(fd_inputCostCompo);
		toolkit.adapt(inputCostCompo);
		toolkit.paintBordersFor(inputCostCompo);
						
						Label lblCostFrom = new Label(inputCostCompo,SWT.NONE);
						lblCostFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(lblCostFrom, true, true);
						lblCostFrom.setText("Cost from:");
				
						lowerBoundCostText = new Text(inputCostCompo, SWT.BORDER);
						lowerBoundCostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(lowerBoundCostText, true, true);
								
										Label label_5 = new Label(inputCostCompo, SWT.NONE);
										label_5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
										label_5.setText("to");
										toolkit.adapt(label_5, true, true);
												
														upperBoundCostText = new Text(inputCostCompo, SWT.BORDER);
														upperBoundCostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
														toolkit.adapt(upperBoundCostText, true, true);
		fd_compoCriteria.right = new FormAttachment(90, 0);

		resultPageCompo = new Composite(functionContentCompo, SWT.NONE);
		resultPageCompo.setBounds(0, 70, 330, 238);
		toolkit.adapt(resultPageCompo);
		toolkit.paintBordersFor(resultPageCompo);
		resultPageCompo.setLayout(new FormLayout());
		btnBookVenue = new Button(resultPageCompo, SWT.NONE);
		FormData fd_btnBookVenue = new FormData();
		fd_btnBookVenue.width = 100;
		fd_btnBookVenue.bottom = new FormAttachment(100);
		fd_btnBookVenue.right = new FormAttachment(100, -5);
		btnBookVenue.setLayoutData(fd_btnBookVenue);
		btnBookVenue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					// Check the logical validity of the chosen time slot (i.e. the user cannot
					// book a past time slot.
					if(chosenVenueID < 0)
						throw new Exception("You have not chosen a venue yet!");
					
					// Read time slot
					timeSlotChoiceInput = dtSearchResult.readInputFields();
					
					MyDateTime currentTime = MyDateTime.getCurrentDateTime();
					if(timeSlotChoiceInput.happenBefore(currentTime) == true)
						throw new Exception("You cannot book a venue with the starting date and time in the past!");

					// Check if the book time slot is within the event time slot.
					if(timeSlotChoiceInput.isContained(new TimeSlot(event.getStartDateTime(),
							event.getEndDateTime())) == false)
					{
						TwoChoiceDialog warningBoard = new TwoChoiceDialog(new Shell(), "Warning", 
								"Warning: Your preferred time slot is not within the event's time slot. Do you still want to book it?",
								"Yes", "No");
						String choice = (String) warningBoard.open();
						if(choice.equals("Yes") == false)
						{
							return;
						}
					}

					if(hasTimeSlotChecked == false)
					{
						// Have to check if there is any clashes
						if(bookingSystem.isAvailable(chosenVenueID, timeSlotChoiceInput) == false)
							throw new Exception("The venue is not available at your preferred time slot. Please choose another time slot!");
					}

					btnBookVenue.setText("Book Venue");

					// Do the booking (The chosen time slot is legal)
					bookingSystem.bookVenue(event, chosenVenueID, timeSlotChoiceInput);

					// Return to the main GUI
					ViewMain.ReturnView();
				}
				catch(Exception exception)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
				}
			}
		});
		toolkit.adapt(btnBookVenue, true, true);
		btnBookVenue.setText("Book Venue");
		
		Composite searchResultTableCompo = new Composite(resultPageCompo, SWT.NONE);
		FormData fd_searchResultTableCompo = new FormData();
		fd_searchResultTableCompo.bottom = new FormAttachment(70);
		fd_searchResultTableCompo.top = new FormAttachment(0);
		fd_searchResultTableCompo.left = new FormAttachment(0);
		searchResultTableCompo.setLayoutData(fd_searchResultTableCompo);
		toolkit.adapt(searchResultTableCompo);
		toolkit.paintBordersFor(searchResultTableCompo);
		searchResultTableCompo.setLayout(new GridLayout(1, false));

		Label lblSearchResult = new Label(searchResultTableCompo, SWT.NONE);
		lblSearchResult.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSearchResult.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search Result:");

		/***************************************************************************************
		 * 
		 * Table selection listener
		 * 
		 ***************************************************************************************/
		Composite tableCompo = new Composite(searchResultTableCompo, SWT.NONE);
		TableColumnLayout tcl_tableCompo = new TableColumnLayout();
		tableCompo.setLayout(tcl_tableCompo);
		tableCompo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(tableCompo);
		toolkit.paintBordersFor(tableCompo);
		
		tableViewer = new TableViewer(tableCompo, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		DisplayTable = tableViewer.getTable();
		DisplayTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					TableItem item = tableViewer.getTable().getItem(tableViewer.getTable().getSelectionIndex());
					String idString = item.getText(0);
					chosenVenueID = Integer.parseInt(idString);

					String detail = bookingSystem.getVenueDetail(chosenVenueID);
					venueDetailText.setText(detail);
				}
				catch(Exception exception)
				{
					// There should not be an exception here thanks 
					// to the way we design how users interact with the system.
				}

			}
		});
		DisplayTable.setHeaderVisible(true);
		toolkit.paintBordersFor(DisplayTable);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnVenueid = tableViewerColumn_3.getColumn();
		tblclmnVenueid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.VENUEID);
				tableViewer.refresh();
			}
		});
		tcl_tableCompo.setColumnData(tblclmnVenueid, new ColumnWeightData(20));
		//tblclmnVenueid.setWidth(57);
		tblclmnVenueid.setText("Venue ID");
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getVenueID()).toString();
			}
		});
		tblclmnVenueid.pack();
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnVenuename = tableViewerColumn_2.getColumn();
		tblclmnVenuename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.NAME);
				tableViewer.refresh();
			}
		});
		tcl_tableCompo.setColumnData(tblclmnVenuename, new ColumnWeightData(30));
		//tblclmnVenuename.setWidth(192);
		tblclmnVenuename.setText("Venue Name");
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return venue.getName();
			}
		});

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCapacity = tableViewerColumn_1.getColumn();
		tblclmnCapacity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.CAPACITY);
				tableViewer.refresh();
			}
		});
		tcl_tableCompo.setColumnData(tblclmnCapacity, new ColumnWeightData(20));
		tblclmnCapacity.setText("Capacity");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getMaxCapacity()).toString();
			}
		});
		tblclmnCapacity.pack();
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCost = tableViewerColumn.getColumn();
		tblclmnCost.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.COST);
				tableViewer.refresh();
			}
		});
		tcl_tableCompo.setColumnData(tblclmnCost, new ColumnWeightData(15));
		//tblclmnCost.setWidth(70);
		tblclmnCost.setText("Cost");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Double) (venue.getCost() / 100.0)).toString();
			}
		});
		//tblclmnCost.pack();
		
		viewCompo = new Composite(resultPageCompo, SWT.NONE);
		FormData fd_viewCompo = new FormData();
		//	fd_ViewCompo.left = new FormAttachment(composite_4);
		fd_viewCompo.bottom = new FormAttachment(btnBookVenue);
		fd_searchResultTableCompo.right = new FormAttachment(viewCompo);
		fd_viewCompo.left = new FormAttachment(65);
		fd_viewCompo.right = new FormAttachment(100);
		fd_viewCompo.top = new FormAttachment(0);
		viewCompo.setLayoutData(fd_viewCompo);
		toolkit.adapt(viewCompo);
		toolkit.paintBordersFor(viewCompo);
		viewCompo.setLayout(new GridLayout(1, false));
		
				Label lblVenueDetails = new Label(viewCompo, SWT.NONE);
				lblVenueDetails.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
				lblVenueDetails.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
				toolkit.adapt(lblVenueDetails, true, true);
				lblVenueDetails.setText("Venue Details:");

		venueDetailText = new Text(viewCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_venueDetailText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_venueDetailText.heightHint = 65;
		venueDetailText.setLayoutData(gd_venueDetailText);
		venueDetailText.setEditable(false);
		toolkit.adapt(venueDetailText, true, true);

		dtSearchResult = new InputDateTimeComposite(resultPageCompo, SWT.NONE);
		FormData fd_dtSearchResult = new FormData();
		fd_dtSearchResult.top = new FormAttachment(searchResultTableCompo, 5);
		fd_dtSearchResult.right = new FormAttachment(searchResultTableCompo, 0, SWT.RIGHT);
		fd_dtSearchResult.bottom = new FormAttachment(100);
		fd_dtSearchResult.left = new FormAttachment(0);
		dtSearchResult.setLayoutData(fd_dtSearchResult);
		toolkit.adapt(dtSearchResult);
		toolkit.paintBordersFor(dtSearchResult);

		timeSlotCompo = new Composite(compoCriteriaFilled, SWT.NONE);
		FormData fd_TimeSlotCompo = new FormData();
		fd_TimeSlotCompo.left = new FormAttachment(0, 190);
		timeSlotCompo.setLayoutData(fd_TimeSlotCompo);
		timeSlotCompo.setBackgroundImage(searchCriteriaCompo.getBackgroundImage());
//		timeSlotCompo.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(timeSlotCompo);
		toolkit.paintBordersFor(timeSlotCompo);
		timeSlotCompo.setLayout(new FormLayout());

		dtSearchCriteria = new InputDateTimeComposite(timeSlotCompo, SWT.NONE);
		FormData fd_dtSearchCriteria = new FormData();
		fd_dtSearchCriteria.bottom = new FormAttachment(100);
		fd_dtSearchCriteria.right = new FormAttachment(100);
		fd_dtSearchCriteria.top = new FormAttachment(0);
		fd_dtSearchCriteria.left = new FormAttachment(0);
		dtSearchCriteria.setLayoutData(fd_dtSearchCriteria);
//		dtSearchCriteria.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(dtSearchCriteria);
		toolkit.paintBordersFor(dtSearchCriteria);
		
				Label lblSearchVenuesWith = new Label(searchCriteriaCompo, SWT.NONE);
				fd_compoCriteria.top = new FormAttachment(lblSearchVenuesWith, 11);
				FormData fd_lblSearchVenuesWith = new FormData();
				fd_lblSearchVenuesWith.left = new FormAttachment(0, 5);
				lblSearchVenuesWith.setLayoutData(fd_lblSearchVenuesWith);
				lblSearchVenuesWith.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
				toolkit.adapt(lblSearchVenuesWith, true, true);
				lblSearchVenuesWith.setText("Search venues with specified criteria:");
				
				Composite buttonCompo = new Composite(searchCriteriaCompo, SWT.NONE);
				buttonCompo.setLayout(new GridLayout(3, false));
				FormData fd_buttonCompo = new FormData();
				fd_buttonCompo.top = new FormAttachment(compoCriteriaFilled, 20);
				fd_buttonCompo.left = new FormAttachment(10);
				fd_buttonCompo.right = new FormAttachment(90);
				buttonCompo.setLayoutData(fd_buttonCompo);
				toolkit.adapt(buttonCompo);
				toolkit.paintBordersFor(buttonCompo);
								
										btnBackCriteria = new Button(buttonCompo, SWT.NONE);
										GridData gd_btnBackCriteria = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
										gd_btnBackCriteria.widthHint = 80;
										btnBackCriteria.setLayoutData(gd_btnBackCriteria);
										btnBackCriteria.addSelectionListener(new SelectionAdapter() {
											@Override
											public void widgetSelected(SelectionEvent e) {
												int previousCompo = hasPreviousCriteria();
												if(previousCompo >= 0)
													currentCompo = previousCompo;
												switch(currentCompo)
												{
													case 0:
														stackLayoutCriteria.topControl = timeSlotCompo;
														break;
													case 1:
														stackLayoutCriteria.topControl = capacityCompo;
														break;
												}
												compoCriteriaFilled.layout();
												btnNextCriteria.setText("Next");
												btnNextCriteria.setEnabled(true);
												if(hasNextCriteria() < 0)
													btnBackCriteria.setEnabled(false);
												
												btnFindCriteria.setEnabled(false);
											}
										});
										btnBackCriteria.setEnabled(false);
										toolkit.adapt(btnBackCriteria, true, true);
										btnBackCriteria.setText("Previous");
						
								btnNextCriteria = new Button(buttonCompo, SWT.NONE);
								GridData gd_btnNextCriteria = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
								gd_btnNextCriteria.widthHint = 80;
								btnNextCriteria.setLayoutData(gd_btnNextCriteria);
								btnNextCriteria.setEnabled(false);
								btnNextCriteria.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										try
										{
											// First read input from the user
											switch(currentCompo)
											{
												case 0:
													readTimeSlotInput();
													break;
												case 1:
													readCapacityInput();
													break;
												case 2:
													readCostInput();
													break;
											}
											
											if(btnNextCriteria.getText().equals("Confirm") == true)
											{
												// This is the last composite the user has to type in
												btnFindCriteria.setEnabled(true);
												btnNextCriteria.setEnabled(false);
											}
											
											int nextCompo = hasNextCriteria();
											if(nextCompo <= 2)
												currentCompo = nextCompo;
											switch(currentCompo)
											{
												case 1:
													stackLayoutCriteria.topControl = capacityCompo;
													break;
												case 2:
													stackLayoutCriteria.topControl = costCompo;
													break;
											}
											compoCriteriaFilled.layout();
											if(hasPreviousCriteria() >= 0)
												btnBackCriteria.setEnabled(true);
											if(hasNextCriteria() >= 3)
												btnNextCriteria.setText("Confirm");
										}
										catch(Exception exception)
										{
											ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), exception.getMessage());
											errorBoard.open();
										}
									}
								});
								toolkit.adapt(btnNextCriteria, true, true);
								btnNextCriteria.setText("Next");
				
						btnFindCriteria = new Button(buttonCompo, SWT.NONE);
						btnFindCriteria.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
						btnFindCriteria.setEnabled(false);
						btnFindCriteria.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								// Ask the logic component to do the searching
								// Determine the criteria chosen
								ControllerBookingSystem.SearchCriteria type = determineCriteriaChoice();
								searchResultList = bookingSystem.findVenueByCriteria(costChoiceInput, 
										capacityChoiceInput, timeSlotChoiceInput, type);

								if(searchResultList.size() == 0)
								{
									ErrorMessageDialog messageBoard = new ErrorMessageDialog(new Shell(),
											"There is no result satisfying your criteria. Please do another searching!",
											"Message");
									messageBoard.open();
								}
								else
								{
									// Reset chosenVenueID variable
									chosenVenueID = -1;

									tableViewer.setInput(searchResultList);
									// GUI Settings
									SearchCriteriaButton.setSelection(false);
									tableViewer.refresh();

									resetResultPageView(flagTimeSlotChoice);

									sl_functionContentCompo.topControl = resultPageCompo;
									functionContentCompo.layout();
								}
							}
						});
						toolkit.adapt(btnFindCriteria, true, true);
						btnFindCriteria.setText("Find All Suitable Venues");
						
	Button btnBack = new Button(nameCompo, SWT.NONE);
	btnBack.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			ViewMain.ReturnView();
		}
	});
	GridData gd_btnBack = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	gd_btnBack.widthHint = 80;
	btnBack.setLayoutData(gd_btnBack);
	toolkit.adapt(btnBack, true, true);
	btnBack.setText("Back");
	
	btnBack.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	functionOptionCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	mainCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	functionContentCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	VenueViewForm.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
	VenueViewForm.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchNameButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchCriteriaButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	labelEventTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	searchNameCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenueBy.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	nameCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEnterTheName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnSearchName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	searchCriteriaCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblPleaseChooseYour.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CapacityChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	TimeChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CostChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnCriteriaConfirm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	capacityCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEstimatedCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	inputCapacityCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCapacityFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnBookVenue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	searchResultTableCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	viewCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblVenueDetails.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenuesWith.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	buttonCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnBackCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnNextCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnFindCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	costCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	inputCostCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCostFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	resultPageCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	
	Composite composite = new Composite(mainCompo, SWT.NONE);
	//composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	FormData fd_composite = new FormData();
	fd_composite.top = new FormAttachment(0);
	fd_composite.left = new FormAttachment(0);
	fd_composite.bottom = new FormAttachment(100);
	fd_composite.right = new FormAttachment(100);
	composite.setLayoutData(fd_composite);
	toolkit.adapt(composite);
	toolkit.paintBordersFor(composite);
	composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	}	
	
	/**
	 * Read time slot input from the user
	 * @throws Exception if the user forgets to enter some fields or the information entered is not correct.
	 */
	private void readTimeSlotInput() throws Exception
	{
		timeSlotChoiceInput = dtSearchCriteria.readInputFields();
	}
	
	/**
	 * Read the capacity input from the user
	 * @throws Exception if the user forgets to enter some fields or the information entered is not correct.
	 */
	private void readCapacityInput() throws Exception
	{
		try
		{
			String lowerCapacity = HelperFunctions.removeAllWhiteSpace(lowerBoundCapacityText.getText());
			lowerBoundCapacityText.setText(lowerCapacity);
			if(lowerCapacity == null || lowerCapacity.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the capacity!");
			}
			String upperCapacity = HelperFunctions.removeAllWhiteSpace(upperBoundCapacityText.getText());
			upperBoundCapacityText.setText(upperCapacity);
			if(upperCapacity == null || upperCapacity.equals("") == true)
			{
				throw new Exception("You have not entered the upper bound of the capacity!");
			}

			int lower = Integer.parseInt(lowerCapacity);
			int upper = Integer.parseInt(upperCapacity);
			if(lower > upper)
			{
				throw new Exception("Lower bound value is larger than upper bound value!");
			}
			if(lower <= 0 || upper <= 0)
			{
				throw new Exception("Capacity must be positive!");
			}
		
			capacityChoiceInput = new int[2];
			capacityChoiceInput[0] = lower;
			capacityChoiceInput[1] = upper;
		}
		catch(NumberFormatException exception)
		{
			throw new Exception("The bounds must be integers!");
		}
		catch(Exception exception)
		{
			throw exception;
		}
	}
	
	/**
	 * Read the cost input from the user
	 * @throws Exception if the user forgets to enter some fields or the information entered is not correct.
	 */
	private void readCostInput() throws Exception
	{
		try
		{	
			String lowerCost = HelperFunctions.removeAllWhiteSpace(lowerBoundCostText.getText());
			lowerBoundCostText.setText(lowerCost);
			if(lowerCost == null || lowerCost.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the cost!");
			}
			
			String upperCost = HelperFunctions.removeAllWhiteSpace(upperBoundCostText.getText());
			upperBoundCostText.setText(upperCost);
			if(upperCost == null || upperCost.equals("") == true)
			{
				throw new Exception("You have not entered the upper bound of the cost!");
			}
			if(correctMoneyFormat(upperCost) == false || correctMoneyFormat(lowerCost) == false)
			{
				throw new Exception("Cost must have at most 2 decimal digits!");
			}
			int lower = (int) (Double.parseDouble(lowerCost) * 100);
			int upper = (int) (Double.parseDouble(upperCost) * 100);
			if(lower > upper)
			{
				throw new Exception("The lower bound value is larger than the upperbound value!");
			}
			if(lower < 0 || upper < 0)
				throw new Exception("Cost must be a nonnegative real number.");
			
			costChoiceInput = new int[2];
			costChoiceInput[0] = lower;
			costChoiceInput[1] = upper;
		}
		catch(NumberFormatException exception)
		{
			throw new Exception("Cost values must be real numbers!");
		}
		catch(Exception exception)
		{
			throw exception;
		}
	}
	
	/**
	 * Checks if the given string representing an amount of money (in dollars) is valid. The string
	 * is in the correct format of currency if and only if whenever it has a decimal point, the
	 * number of decimal digits must be at least 1 and at most 2.
	 * 
	 * @param money - String
	 * @return true if money is in the correct format of currency.
	 * @return false otherwise.
	 */	
	private boolean correctMoneyFormat(String money)
	{
		int decimalPointIndex = -1;

		for(int i = 0; i < money.length(); i++)
			if(money.charAt(i) == '.')
			{
				decimalPointIndex = i;
				break;
			}

		if(decimalPointIndex < 0)
			return true;
		if(money.length() - decimalPointIndex == 1 || money.length() - decimalPointIndex > 2)
			return false;
		return true;
	}

	/**
	 * Determine the search criteria that a user chooses. 
	 * 
	 * @return searchType - ControllerBookingSystem.SearchCriteria 
	 */
	private ControllerBookingSystem.SearchCriteria determineCriteriaChoice()
	{
		if(flagCapacityChoice == true)
		{
			if(flagCostChoice == true)
			{
				if(flagTimeSlotChoice == true)
				{
					return ControllerBookingSystem.SearchCriteria.ALL_THREE;
				}

				// Now flagTimeSlotChoice == false
				return ControllerBookingSystem.SearchCriteria.COST_CAPACITY;
			}

			// Reach this point. That means flagCostChoice == false
			if(flagTimeSlotChoice == true)
			{
				return ControllerBookingSystem.SearchCriteria.CAPACITY_TIME;
			}

			// Now flagTimeSlotChoice == false
			return ControllerBookingSystem.SearchCriteria.CAPACITY;

		}

		// Now flagCapacityChoice == false
		if(flagCostChoice == true)
		{
			if(flagTimeSlotChoice == true)
			{
				return ControllerBookingSystem.SearchCriteria.COST_TIME;
			}

			// Now flagTimeSlotChoice == false
			return ControllerBookingSystem.SearchCriteria.COST;
		}

		// Reach this point. That means flagCostChoice == false = flagCostChoice
		// Thus flagTimeSlotChoice == true (since at least 1 criterion made)
		return ControllerBookingSystem.SearchCriteria.TIME;
	}
	
	/**
	 * This method is to return the serial number (1, 2) of the next criteria composite
	 * 		a user can type in. If there is no composite left for the user to type in, the method
	 * 		returns 3.
	 */
	private int hasNextCriteria()
	{
		int temp = currentCompo + 1;
		while(temp < 3)
		{
			if(temp == 1 && flagCapacityChoice == true)
			{
				break;
			}
			
			if(temp == 2 && flagCostChoice == true)
			{
				break;
			}
			
			temp++;
		}
		return temp;
	}
	
	/**
	 * This method is to return the serial number (0, 1) of the previous criteria composite
	 * 		a user can type in. If there is no composite left for the user to type in, the method
	 * 		returns -1.
	 */
	private int hasPreviousCriteria()
	{
		int temp = currentCompo - 1;
		while(temp >= 0)
		{
			if(temp == 1 && flagCapacityChoice == true)
			{
				break;
			}
			
			if(temp == 0 && flagTimeSlotChoice == true)
			{
				break;
			}
			
			temp--;
		}
		return temp;
	}
	
	/**
	 * This method is to clear content of all text-boxes in the result page
	 * (since user has requested another query) and to make the ViewBookCompo
	 * invisible temporarily 
	 */
	private void resetResultPageView(boolean chooseTimeSlotYet)
	{
		tableViewer.refresh();
		venueDetailText.setText("");
		dtSearchResult.setEnabled(true);
		
		if(chooseTimeSlotYet == false)
		{
			dtSearchResult.reset();
		}
		else
		{
			// Time slot has been chosen
			dtSearchResult.displayInputTimeSlot(timeSlotChoiceInput);
			hasTimeSlotChecked = true;
		}
	}
}
