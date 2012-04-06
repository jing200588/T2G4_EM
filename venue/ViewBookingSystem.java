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
	private final StackLayout stackLayout = new StackLayout();
	private final StackLayout stackLayoutCriteria = new StackLayout();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Text LowerBoundCapacity;
	private Text UpperBoundCapacity;
	private Text NameToSearch;
	private Text LowerBoundCost;
	private Text UpperBoundCost;
	private Composite SearchNameCompo;
	private Composite SearchCriteriaCompo;
	private Composite FunctionContentPage;
	private Table DisplayTable;
	private TableViewer tableViewer;
	private boolean flagCapacityChoice;			
	private boolean flagCostChoice;				
	private boolean flagTimeSlotChoice;
	private Button TimeChoiceButton;
	private Button CostChoiceButton;
	private Button CapacityChoiceButton;
	private Button CriteriaConfirmButton;
	private Button FindCriteriaButton;
	private int[] capacityChoiceInput;
	private int[] costChoiceInput;
	private TimeSlot timeSlotChoiceInput;
	private Composite FunctionOptionCompo;

	private Vector<Venue> searchResultList;
	private Button BookVenueButton;
	private Text VenueDetailTextbox;
	private Composite ViewCompo;
	private int chosenVenueID;
	private Eventitem event;
	private ControllerBookingSystem bookingSystem;
	private InputDateTimeComposite dtSearchCriteria;
	private InputDateTimeComposite dtSearchResult;
	private Composite CapacityCompo;
	private boolean hasTimeSlotChecked;
	private Button SearchNameButton;
	private Button SearchCriteriaButton;
	private Composite ResultPageCompo;
	private Composite compoCriteriaFilled;

	private int currentCompo;					// 0 for time slot, 1 for capacity, 2 for cost
	private Composite CostCompo;
	private Composite TimeSlotCompo;
	private Button btnNextCriteria;
	private Button btnBackCriteria;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewBookingSystem(Composite parent, int style, Eventitem eventObj) {
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
		VenueViewForm.getBody().setBackgroundMode(SWT.INHERIT_DEFAULT);
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Book Venue");
		VenueViewForm.getBody().setLayout(new FormLayout());

		Composite mainComposite = new Composite(VenueViewForm.getBody(), SWT.NONE);
		mainComposite.setLayout(new FormLayout());
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.bottom = new FormAttachment(100);
		fd_mainComposite.left = new FormAttachment(0);
		fd_mainComposite.top = new FormAttachment(0);
		fd_mainComposite.right = new FormAttachment(100);
		mainComposite.setLayoutData(fd_mainComposite);
		toolkit.adapt(mainComposite);
		toolkit.paintBordersFor(mainComposite);


		FunctionOptionCompo = new Composite(mainComposite, SWT.NONE);
		FunctionOptionCompo.setLayout(new GridLayout(3, false));
		FormData fd_FunctionOptionCompo = new FormData();
		fd_FunctionOptionCompo.right = new FormAttachment(90);
		//fd_FunctionOptionCompo.bottom = new FormAttachment(0, 47);
		fd_FunctionOptionCompo.top = new FormAttachment(10);
		fd_FunctionOptionCompo.left = new FormAttachment(10);
		FunctionOptionCompo.setLayoutData(fd_FunctionOptionCompo);
		toolkit.adapt(FunctionOptionCompo);
		toolkit.paintBordersFor(FunctionOptionCompo);

		SearchNameButton = new Button(FunctionOptionCompo, SWT.RADIO);
		SearchNameButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		SearchNameButton.setSelection(true);
		SearchNameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = SearchNameCompo;
				FunctionContentPage.layout();
			}
		});
		toolkit.adapt(SearchNameButton, true, true);
		SearchNameButton.setText("Default Search");

		SearchCriteriaButton = new Button(FunctionOptionCompo, SWT.RADIO);
		SearchCriteriaButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		SearchCriteriaButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CriteriaConfirmButton.setText("Confirm");
				btnNextCriteria.setEnabled(false);
				btnBackCriteria.setEnabled(false);
				FindCriteriaButton.setEnabled(false);
				
				// Enable all the choice button
				CostChoiceButton.setEnabled(true);
				TimeChoiceButton.setEnabled(true);
				CapacityChoiceButton.setEnabled(true);
				
				stackLayout.topControl = SearchCriteriaCompo;
				FunctionContentPage.layout();
				
				stackLayoutCriteria.topControl = TimeSlotCompo;
				dtSearchCriteria.setEnabled(false);
				compoCriteriaFilled.layout();
			}
		});
		toolkit.adapt(SearchCriteriaButton, true, true);
		SearchCriteriaButton.setText("Advanced Search");

		Label labelEventTime = new Label(FunctionOptionCompo, SWT.NONE);
		labelEventTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(labelEventTime, true, true);
		String strEventTime = "Event time: FROM " + event.getStartDateTime().getDateRepresentation() +
				" " + event.getStartDateTime().getTimeRepresentation() + " TO " +
				event.getEndDateTime().getDateRepresentation() + " " + event.getEndDateTime().getTimeRepresentation();
		labelEventTime.setText(strEventTime);

		FunctionContentPage = new Composite(mainComposite, SWT.NONE);
		FormData fd_FunctionContentPage = new FormData();
		fd_FunctionContentPage.top = new FormAttachment(FunctionOptionCompo, 20);
		fd_FunctionContentPage.right = new FormAttachment(90);
		fd_FunctionContentPage.bottom = new FormAttachment(90);
		fd_FunctionContentPage.left = new FormAttachment(10);
		FunctionContentPage.setLayoutData(fd_FunctionContentPage);
		FunctionContentPage.setBackgroundMode(SWT.INHERIT_DEFAULT);
		toolkit.adapt(FunctionContentPage);
		toolkit.paintBordersFor(FunctionContentPage);
		FunctionContentPage.setLayout(stackLayout);

		SearchNameCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchNameCompo.setBounds(0, 5, 675, 280);
		toolkit.adapt(SearchNameCompo);
		toolkit.paintBordersFor(SearchNameCompo);
		SearchNameCompo.setLayout(new FormLayout());
		stackLayout.topControl = SearchNameCompo;
		FunctionContentPage.layout();
		
		Label lblSearchVenueBy = new Label(SearchNameCompo, SWT.NONE);
		FormData fd_lblSearchVenueBy = new FormData();
		fd_lblSearchVenueBy.left = new FormAttachment(0, 5);
		lblSearchVenueBy.setLayoutData(fd_lblSearchVenueBy);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		Composite compoName = new Composite(SearchNameCompo, SWT.NONE);
		FormData fd_compoName = new FormData();
		fd_compoName.right = new FormAttachment(100);
		fd_compoName.top = new FormAttachment(0, 95);
		fd_compoName.left = new FormAttachment(0);
		compoName.setLayoutData(fd_compoName);
		toolkit.adapt(compoName);
		toolkit.paintBordersFor(compoName);
		compoName.setLayout(new GridLayout(4, false));

		Label lblEnterTheName = new Label(compoName, SWT.NONE);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");

		NameToSearch = new Text(compoName, SWT.BORDER);
		NameToSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(NameToSearch, true, true);

		Button ButtonSearchName = new Button(compoName, SWT.NONE);
		ButtonSearchName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String venueName = HelperFunctions.removeRedundantWhiteSpace(NameToSearch.getText());
				NameToSearch.setText(venueName);
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
						
						stackLayout.topControl = ResultPageCompo;
						FunctionContentPage.layout();
					}
				}
			}
		});
		toolkit.adapt(ButtonSearchName, true, true);
		ButtonSearchName.setText("Search and view information");

		SearchCriteriaCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchCriteriaCompo.setBounds(0, 129, 331, 318);
		toolkit.adapt(SearchCriteriaCompo);
		toolkit.paintBordersFor(SearchCriteriaCompo);
		SearchCriteriaCompo.setLayout(new FormLayout());
		
		Composite compoCriteria = new Composite(SearchCriteriaCompo, SWT.NONE);
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
									CriteriaConfirmButton.setEnabled(true);
								else
								{
									if(flagTimeSlotChoice == false && flagCostChoice == false)
										CriteriaConfirmButton.setEnabled(false);
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
													CriteriaConfirmButton.setEnabled(true);
												else
												{
													if(flagCapacityChoice == false && flagCostChoice == false)
														CriteriaConfirmButton.setEnabled(false);
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
															CriteriaConfirmButton.setEnabled(true);
														else
														{
															if(flagTimeSlotChoice == false && flagCapacityChoice == false)
																CriteriaConfirmButton.setEnabled(false);
														}
													}
												});
												toolkit.adapt(CostChoiceButton, true, true);
												CostChoiceButton.setText("Cost");
										
												CriteriaConfirmButton = new Button(compoCriteria, SWT.NONE);		
												GridData gd_CriteriaConfirmButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
												gd_CriteriaConfirmButton.widthHint = 80;
												CriteriaConfirmButton.setLayoutData(gd_CriteriaConfirmButton);
												CriteriaConfirmButton.setEnabled(false);
												CriteriaConfirmButton.addSelectionListener(new SelectionAdapter() {
													@Override
													public void widgetSelected(SelectionEvent e) {
														String commandType = CriteriaConfirmButton.getText();

														if(commandType.equals("Confirm") == true)
														{
															// Change the command type from "Confirm" to "Edit"
															CriteriaConfirmButton.setText("Edit");
															// Disable all the choice button
															CostChoiceButton.setEnabled(false);
															TimeChoiceButton.setEnabled(false);
															CapacityChoiceButton.setEnabled(false);
															
															// Display the following composites in order if they are chosen by the user
															if(flagTimeSlotChoice == true)
															{
																stackLayoutCriteria.topControl = TimeSlotCompo;
																dtSearchCriteria.setEnabled(true);
																compoCriteriaFilled.layout();
																currentCompo = 0;
															}
															else
															{
																if(flagCapacityChoice == true)
																{
																	stackLayoutCriteria.topControl = CapacityCompo;
																	compoCriteriaFilled.layout();
																	currentCompo = 1;
																}
																else
																{
																	if(flagCostChoice == true)
																	{
																		stackLayoutCriteria.topControl = CostCompo;
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
															CriteriaConfirmButton.setText("Confirm");
															// Enable all the choice button
															CostChoiceButton.setEnabled(true);
															TimeChoiceButton.setEnabled(true);
															CapacityChoiceButton.setEnabled(true);
															
															// Change the layout
															stackLayoutCriteria.topControl = TimeSlotCompo;
															dtSearchCriteria.setEnabled(false);
															compoCriteriaFilled.layout();
															
															// Reset the button next and back.
															btnNextCriteria.setText("Next");
															btnNextCriteria.setEnabled(false);
															btnBackCriteria.setEnabled(false);
															// Disable the button to execute the searching
															FindCriteriaButton.setEnabled(false);
														}
													}
												});
												toolkit.adapt(CriteriaConfirmButton, true, true);
												CriteriaConfirmButton.setText("Confirm");

		compoCriteriaFilled = new Composite(SearchCriteriaCompo, SWT.NONE);
		fd_compoCriteria.left = new FormAttachment(10,0);
		FormData fd_compoCriteriaFilled = new FormData();
		fd_compoCriteriaFilled.top = new FormAttachment(compoCriteria, 30);
		fd_compoCriteriaFilled.right = new FormAttachment(90);
		fd_compoCriteriaFilled.left = new FormAttachment(10);
		compoCriteriaFilled.setLayoutData(fd_compoCriteriaFilled);
		toolkit.adapt(compoCriteriaFilled);
		toolkit.paintBordersFor(compoCriteriaFilled);
		compoCriteriaFilled.setLayout(stackLayoutCriteria);

		CapacityCompo = new Composite(compoCriteriaFilled, SWT.NONE);
	//	CapacityCompo.setLayoutDeferred(true);
	//	CapacityCompo.setBackgroundMode(SWT.INHERIT_DEFAULT);
	//	CapacityCompo.setBounds(349, 10, 321, 79);
		toolkit.adapt(CapacityCompo);
		toolkit.paintBordersFor(CapacityCompo);
		CapacityCompo.setLayout(new FormLayout());

		Label lblEstimatedCapacity = new Label(CapacityCompo, SWT.NONE);
		FormData fd_lblEstimatedCapacity = new FormData();
		fd_lblEstimatedCapacity.top = new FormAttachment(0, 5);
		fd_lblEstimatedCapacity.left = new FormAttachment(0, 5);
		lblEstimatedCapacity.setLayoutData(fd_lblEstimatedCapacity);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		Composite compoInputCapacity = new Composite(CapacityCompo, SWT.NONE);
		compoInputCapacity.setLayout(new GridLayout(4, false));
		FormData fd_compoInputCapacity = new FormData();
		fd_compoInputCapacity.right = new FormAttachment(100);
		fd_compoInputCapacity.top = new FormAttachment(50);
		fd_compoInputCapacity.left = new FormAttachment(10);
		compoInputCapacity.setLayoutData(fd_compoInputCapacity);
		toolkit.adapt(compoInputCapacity);
		toolkit.paintBordersFor(compoInputCapacity);
		
		Label lblCapacityFrom = new Label(compoInputCapacity, SWT.NONE);
		lblCapacityFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lblCapacityFrom, true, true);
		lblCapacityFrom.setText("Capacity from:");

		LowerBoundCapacity = new Text(compoInputCapacity, SWT.BORDER);
		LowerBoundCapacity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(LowerBoundCapacity, true, true);
		
				Label lblNewLabel = new Label(compoInputCapacity, SWT.NONE);
				lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				toolkit.adapt(lblNewLabel, true, true);
				lblNewLabel.setText("to");

		UpperBoundCapacity = new Text(compoInputCapacity, SWT.BORDER);
		UpperBoundCapacity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(UpperBoundCapacity, true, true);

		CostCompo = new Composite(compoCriteriaFilled, SWT.NONE);
//		CostCompo.setLayoutDeferred(true);
//		CostCompo.setBounds(349, 95, 321, 66);
		toolkit.adapt(CostCompo);
		toolkit.paintBordersFor(CostCompo);
		CostCompo.setLayout(new FormLayout());

		Label lblPreferredCost = new Label(CostCompo, SWT.NONE);
		FormData fd_lblPreferredCost = new FormData();
		fd_lblPreferredCost.top = new FormAttachment(0, 5);
		fd_lblPreferredCost.left = new FormAttachment(0, 5);
		lblPreferredCost.setLayoutData(fd_lblPreferredCost);
		lblPreferredCost.setText("Preferred cost:");
		toolkit.adapt(lblPreferredCost, true, true);
		
		Composite compoInputCost = new Composite(CostCompo, SWT.NONE);
		compoInputCost.setLayout(new GridLayout(4, false));
		FormData fd_compoInputCost = new FormData();
		fd_compoInputCost.right = new FormAttachment(100);
		fd_compoInputCost.top = new FormAttachment(50);
		fd_compoInputCost.left = new FormAttachment(10);
		compoInputCost.setLayoutData(fd_compoInputCost);
		toolkit.adapt(compoInputCost);
		toolkit.paintBordersFor(compoInputCost);
						
						Label lblCostFrom = new Label(compoInputCost,SWT.NONE);
						lblCostFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(lblCostFrom, true, true);
						lblCostFrom.setText("Cost from:");
				
						LowerBoundCost = new Text(compoInputCost, SWT.BORDER);
						LowerBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(LowerBoundCost, true, true);
								
										Label label_5 = new Label(compoInputCost, SWT.NONE);
										label_5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
										label_5.setText("to");
										toolkit.adapt(label_5, true, true);
												
														UpperBoundCost = new Text(compoInputCost, SWT.BORDER);
														UpperBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
														toolkit.adapt(UpperBoundCost, true, true);
		fd_compoCriteria.right = new FormAttachment(90, 0);

		ResultPageCompo = new Composite(FunctionContentPage, SWT.NONE);
		ResultPageCompo.setBounds(0, 70, 330, 238);
		toolkit.adapt(ResultPageCompo);
		toolkit.paintBordersFor(ResultPageCompo);
		ResultPageCompo.setLayout(new FormLayout());
		BookVenueButton = new Button(ResultPageCompo, SWT.NONE);
		FormData fd_BookVenueButton = new FormData();
		fd_BookVenueButton.width = 100;
		fd_BookVenueButton.bottom = new FormAttachment(100);
		fd_BookVenueButton.right = new FormAttachment(100, -5);
		BookVenueButton.setLayoutData(fd_BookVenueButton);
		BookVenueButton.addSelectionListener(new SelectionAdapter() {
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

					BookVenueButton.setText("Book Venue");

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
		toolkit.adapt(BookVenueButton, true, true);
		BookVenueButton.setText("Book Venue");
		
		Composite compoSearchResultTable = new Composite(ResultPageCompo, SWT.NONE);
		FormData fd_compoSearchResultTable = new FormData();
		fd_compoSearchResultTable.bottom = new FormAttachment(70);
		fd_compoSearchResultTable.top = new FormAttachment(0);
		fd_compoSearchResultTable.left = new FormAttachment(0);
		compoSearchResultTable.setLayoutData(fd_compoSearchResultTable);
		toolkit.adapt(compoSearchResultTable);
		toolkit.paintBordersFor(compoSearchResultTable);
		compoSearchResultTable.setLayout(new GridLayout(1, false));

		Label lblSearchResult = new Label(compoSearchResultTable, SWT.NONE);
		lblSearchResult.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSearchResult.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search Result:");

		/***************************************************************************************
		 * 
		 * Table selection listener
		 * 
		 ***************************************************************************************/
		Composite tableComposite = new Composite(compoSearchResultTable, SWT.NONE);
		TableColumnLayout tcl_tableComposite = new TableColumnLayout();
		tableComposite.setLayout(tcl_tableComposite);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(tableComposite);
		toolkit.paintBordersFor(tableComposite);
		
		tableViewer = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		DisplayTable = tableViewer.getTable();
	//	GridData gd_DisplayTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
	//	gd_DisplayTable.heightHint = 148;
	//	DisplayTable.setLayoutData(gd_DisplayTable);
		DisplayTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					TableItem item = tableViewer.getTable().getItem(tableViewer.getTable().getSelectionIndex());
					String idString = item.getText(0);
					chosenVenueID = Integer.parseInt(idString);

					String detail = bookingSystem.getVenueDetail(chosenVenueID);
					VenueDetailTextbox.setText(detail);
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
		tcl_tableComposite.setColumnData(tblclmnVenueid, new ColumnWeightData(20));
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
		tcl_tableComposite.setColumnData(tblclmnVenuename, new ColumnWeightData(30));
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
		tcl_tableComposite.setColumnData(tblclmnCapacity, new ColumnWeightData(20));
		//tblclmnCapacity.setWidth(69);
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
		tcl_tableComposite.setColumnData(tblclmnCost, new ColumnWeightData(15));
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
		
		ViewCompo = new Composite(ResultPageCompo, SWT.NONE);
		FormData fd_ViewCompo = new FormData();
		//	fd_ViewCompo.left = new FormAttachment(composite_4);
		fd_ViewCompo.bottom = new FormAttachment(BookVenueButton);
		fd_compoSearchResultTable.right = new FormAttachment(ViewCompo);
		fd_ViewCompo.left = new FormAttachment(65);
		fd_ViewCompo.right = new FormAttachment(100);
		fd_ViewCompo.top = new FormAttachment(0);
		ViewCompo.setLayoutData(fd_ViewCompo);
		toolkit.adapt(ViewCompo);
		toolkit.paintBordersFor(ViewCompo);
		ViewCompo.setLayout(new GridLayout(1, false));
		
				Label lblVenueDetails = new Label(ViewCompo, SWT.NONE);
				lblVenueDetails.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
				lblVenueDetails.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
				toolkit.adapt(lblVenueDetails, true, true);
				lblVenueDetails.setText("Venue Details:");

		VenueDetailTextbox = new Text(ViewCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_VenueDetailTextbox = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_VenueDetailTextbox.heightHint = 65;
		VenueDetailTextbox.setLayoutData(gd_VenueDetailTextbox);
		VenueDetailTextbox.setEditable(false);
		toolkit.adapt(VenueDetailTextbox, true, true);

		dtSearchResult = new InputDateTimeComposite(ResultPageCompo, SWT.NONE);
		FormData fd_dtSearchResult = new FormData();
		fd_dtSearchResult.top = new FormAttachment(compoSearchResultTable, 5);
		fd_dtSearchResult.right = new FormAttachment(compoSearchResultTable, 0, SWT.RIGHT);
		fd_dtSearchResult.bottom = new FormAttachment(100);
		fd_dtSearchResult.left = new FormAttachment(0);
		dtSearchResult.setLayoutData(fd_dtSearchResult);
		toolkit.adapt(dtSearchResult);
		toolkit.paintBordersFor(dtSearchResult);

		TimeSlotCompo = new Composite(compoCriteriaFilled, SWT.NONE);
		FormData fd_TimeSlotCompo = new FormData();
		fd_TimeSlotCompo.left = new FormAttachment(0, 190);
		TimeSlotCompo.setLayoutData(fd_TimeSlotCompo);
		TimeSlotCompo.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		TimeSlotCompo.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(TimeSlotCompo);
		toolkit.paintBordersFor(TimeSlotCompo);
		TimeSlotCompo.setLayout(new FormLayout());

		dtSearchCriteria = new InputDateTimeComposite(TimeSlotCompo, SWT.NONE);
		FormData fd_dtSearchCriteria = new FormData();
		fd_dtSearchCriteria.bottom = new FormAttachment(100);
		fd_dtSearchCriteria.right = new FormAttachment(100);
		fd_dtSearchCriteria.top = new FormAttachment(0);
		fd_dtSearchCriteria.left = new FormAttachment(0);
		dtSearchCriteria.setLayoutData(fd_dtSearchCriteria);
		dtSearchCriteria.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(dtSearchCriteria);
		toolkit.paintBordersFor(dtSearchCriteria);
		
				Label lblSearchVenuesWith = new Label(SearchCriteriaCompo, SWT.NONE);
				fd_compoCriteria.top = new FormAttachment(lblSearchVenuesWith, 11);
				FormData fd_lblSearchVenuesWith = new FormData();
				fd_lblSearchVenuesWith.left = new FormAttachment(0, 5);
				lblSearchVenuesWith.setLayoutData(fd_lblSearchVenuesWith);
				lblSearchVenuesWith.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
				toolkit.adapt(lblSearchVenuesWith, true, true);
				lblSearchVenuesWith.setText("Search venues with specified criteria:");
				
				Composite ButtonComp = new Composite(SearchCriteriaCompo, SWT.NONE);
				ButtonComp.setLayout(new GridLayout(3, false));
				FormData fd_ButtonComp = new FormData();
				fd_ButtonComp.top = new FormAttachment(compoCriteriaFilled, 20);
				fd_ButtonComp.left = new FormAttachment(10);
				fd_ButtonComp.right = new FormAttachment(90);
				ButtonComp.setLayoutData(fd_ButtonComp);
				toolkit.adapt(ButtonComp);
				toolkit.paintBordersFor(ButtonComp);
								
										btnBackCriteria = new Button(ButtonComp, SWT.NONE);
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
														stackLayoutCriteria.topControl = TimeSlotCompo;
														break;
													case 1:
														stackLayoutCriteria.topControl = CapacityCompo;
														break;
												}
												compoCriteriaFilled.layout();
												btnNextCriteria.setText("Next");
												btnNextCriteria.setEnabled(true);
												if(hasNextCriteria() < 0)
													btnBackCriteria.setEnabled(false);
												
												FindCriteriaButton.setEnabled(false);
											}
										});
										btnBackCriteria.setEnabled(false);
										toolkit.adapt(btnBackCriteria, true, true);
										btnBackCriteria.setText("Previous");
						
								btnNextCriteria = new Button(ButtonComp, SWT.NONE);
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
												FindCriteriaButton.setEnabled(true);
												btnNextCriteria.setEnabled(false);
											}
											
											int nextCompo = hasNextCriteria();
											if(nextCompo <= 2)
												currentCompo = nextCompo;
											switch(currentCompo)
											{
												case 1:
													stackLayoutCriteria.topControl = CapacityCompo;
													break;
												case 2:
													stackLayoutCriteria.topControl = CostCompo;
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
				
						FindCriteriaButton = new Button(ButtonComp, SWT.NONE);
						FindCriteriaButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
						FindCriteriaButton.setEnabled(false);
						FindCriteriaButton.addSelectionListener(new SelectionAdapter() {
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

									stackLayout.topControl = ResultPageCompo;
									FunctionContentPage.layout();
								}
							}
						});
						toolkit.adapt(FindCriteriaButton, true, true);
						FindCriteriaButton.setText("Find All Suitable Venues");
						
	Button btnBack = new Button(compoName, SWT.NONE);
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
	FunctionOptionCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	mainComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	FunctionContentPage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	VenueViewForm.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
	VenueViewForm.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchNameButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchCriteriaButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	labelEventTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchNameCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenueBy.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEnterTheName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	ButtonSearchName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	SearchCriteriaCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblPleaseChooseYour.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CapacityChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	TimeChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CostChoiceButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CriteriaConfirmButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CapacityCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEstimatedCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoInputCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCapacityFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	BookVenueButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoSearchResultTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	ViewCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblVenueDetails.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenuesWith.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	ButtonComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnBackCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnNextCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	FindCriteriaButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	CostCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoInputCost.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCostFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	ResultPageCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	
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
			String lowerCapacity = HelperFunctions.removeAllWhiteSpace(LowerBoundCapacity.getText());
			LowerBoundCapacity.setText(lowerCapacity);
			if(lowerCapacity == null || lowerCapacity.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the capacity!");
			}
			String upperCapacity = HelperFunctions.removeAllWhiteSpace(UpperBoundCapacity.getText());
			UpperBoundCapacity.setText(upperCapacity);
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
			String lowerCost = HelperFunctions.removeAllWhiteSpace(LowerBoundCost.getText());
			LowerBoundCost.setText(lowerCost);
			if(lowerCost == null || lowerCost.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the cost!");
			}
			
			String upperCost = HelperFunctions.removeAllWhiteSpace(UpperBoundCost.getText());
			UpperBoundCost.setText(upperCost);
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
		VenueDetailTextbox.setText("");
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
