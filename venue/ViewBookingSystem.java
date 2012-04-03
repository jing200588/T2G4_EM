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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
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
	private InputDateTimeComposite dt_SearchCriteria;
	private InputDateTimeComposite dt_SearchResult;
	private Composite CapacityCompo;
	private Button ButtonConfirmBookTime;
	private boolean hasTimeSlotChecked;
	private Button SearchNameButton;
	private Button SearchCriteriaButton;
	private Composite ResultPageCompo;
	private Composite compoCriteriaFilled;

	private int currentCompo;					// 0 for time slot, 1 for capacity, 2 for cost
	private Composite CostCompo;
	private Composite TimeSlotCompo;
	private Composite composite;
	private Button btnNextCriteria;
	private Button btnBackCriteria;
	private Composite composite_1;
	private Composite composite_2;
	private Label lblCapacityFrom;
	private Composite composite_3;
	private Label lblCostFrom;
	private Composite ButtonComp;
	
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
				dt_SearchCriteria.setEnabled(false);
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
		fd_lblSearchVenueBy.bottom = new FormAttachment(0, 31);
		fd_lblSearchVenueBy.right = new FormAttachment(0, 180);
		fd_lblSearchVenueBy.top = new FormAttachment(0, 10);
		fd_lblSearchVenueBy.left = new FormAttachment(0, 5);
		lblSearchVenueBy.setLayoutData(fd_lblSearchVenueBy);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		composite_1 = new Composite(SearchNameCompo, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(100);
		fd_composite_1.top = new FormAttachment(0, 95);
		fd_composite_1.left = new FormAttachment(0);
		composite_1.setLayoutData(fd_composite_1);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(3, false));

		Label lblEnterTheName = new Label(composite_1, SWT.NONE);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");

		NameToSearch = new Text(composite_1, SWT.BORDER);
		NameToSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(NameToSearch, true, true);

		Button ButtonSearchName = new Button(composite_1, SWT.NONE);
		ButtonSearchName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String venueName = HelperFunctions.removeRedundantWhiteSpace(NameToSearch.getText());
				NameToSearch.setText(venueName);
				if(venueName == null || venueName.equals("") == true)
				{
					errormessageDialog errorBoard = new errormessageDialog(new Shell(), "You have not entered a venue name to search yet!");
					errorBoard.open();
				}
				else
				{
					// Ask the logic component to do the searching
					searchResultList = bookingSystem.findVenueByName(venueName);

					if(searchResultList.size() == 0)
					{
						errormessageDialog messageBoard = new errormessageDialog(new Shell(),
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
		
		composite = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		FormData fd_composite = new FormData();
		composite.setLayoutData(fd_composite);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
				Label lblPleaseChooseYour = new Label(composite, SWT.NONE);
				lblPleaseChooseYour.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				toolkit.adapt(lblPleaseChooseYour, true, true);
				lblPleaseChooseYour.setText("Please choose your preferred criteria:");
						new Label(composite, SWT.NONE);
				
						CapacityChoiceButton = new Button(composite, SWT.CHECK);
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
								
										TimeChoiceButton = new Button(composite, SWT.CHECK);
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
										
												CostChoiceButton = new Button(composite, SWT.CHECK);
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
										
												CriteriaConfirmButton = new Button(composite, SWT.NONE);
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
																dt_SearchCriteria.setEnabled(true);
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
															dt_SearchCriteria.setEnabled(false);
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
		fd_composite.left = new FormAttachment(10,0);
		FormData fd_compoCriteriaFilled = new FormData();
		fd_compoCriteriaFilled.top = new FormAttachment(composite, 30);
		fd_compoCriteriaFilled.right = new FormAttachment(90);
		fd_compoCriteriaFilled.left = new FormAttachment(10);
		compoCriteriaFilled.setLayoutData(fd_compoCriteriaFilled);
		compoCriteriaFilled.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"));
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
		
		composite_2 = new Composite(CapacityCompo, SWT.NONE);
		composite_2.setLayout(new GridLayout(4, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(100);
		fd_composite_2.top = new FormAttachment(50);
		fd_composite_2.left = new FormAttachment(10);
		composite_2.setLayoutData(fd_composite_2);
		toolkit.adapt(composite_2);
		toolkit.paintBordersFor(composite_2);
		
		lblCapacityFrom = new Label(composite_2, SWT.NONE);
		lblCapacityFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lblCapacityFrom, true, true);
		lblCapacityFrom.setText("Capacity from:");

		LowerBoundCapacity = new Text(composite_2, SWT.BORDER);
		LowerBoundCapacity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(LowerBoundCapacity, true, true);
		
				Label lblNewLabel = new Label(composite_2, SWT.NONE);
				lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				toolkit.adapt(lblNewLabel, true, true);
				lblNewLabel.setText("to");

		UpperBoundCapacity = new Text(composite_2, SWT.BORDER);
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
		
		composite_3 = new Composite(CostCompo, SWT.NONE);
		composite_3.setLayout(new GridLayout(4, false));
		FormData fd_composite_3 = new FormData();
		fd_composite_3.right = new FormAttachment(100);
		fd_composite_3.top = new FormAttachment(50);
		fd_composite_3.left = new FormAttachment(10);
		composite_3.setLayoutData(fd_composite_3);
		toolkit.adapt(composite_3);
		toolkit.paintBordersFor(composite_3);
						
						lblCostFrom = new Label(composite_3,SWT.NONE);
						lblCostFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(lblCostFrom, true, true);
						lblCostFrom.setText("Cost from:");
				
						LowerBoundCost = new Text(composite_3, SWT.BORDER);
						LowerBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(LowerBoundCost, true, true);
								
										Label label_5 = new Label(composite_3, SWT.NONE);
										label_5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
										label_5.setText("to");
										toolkit.adapt(label_5, true, true);
												
														UpperBoundCost = new Text(composite_3, SWT.BORDER);
														UpperBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
														toolkit.adapt(UpperBoundCost, true, true);
		fd_composite.right = new FormAttachment(90, 0);

		ResultPageCompo = new Composite(FunctionContentPage, SWT.NONE);
		ResultPageCompo.setBounds(0, 70, 330, 238);
		toolkit.adapt(ResultPageCompo);
		toolkit.paintBordersFor(ResultPageCompo);
		BookVenueButton = new Button(ResultPageCompo, SWT.NONE);
		BookVenueButton.setEnabled(false);
		BookVenueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					// Check the logical validity of the chosen time slot (i.e. the user cannot
					// book a past time slot.
					if(chosenVenueID < 0)
						throw new Exception("You have not chosen a venue yet!");

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
							BookVenueButton.setEnabled(false);
							ButtonConfirmBookTime.setText("Confirm");
							dt_SearchResult.setEnabled(true);
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
					errormessageDialog errorBoard = new errormessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
					BookVenueButton.setEnabled(false);
					ButtonConfirmBookTime.setText("Confirm");
					dt_SearchResult.setEnabled(true);
				}
			}
		});
		BookVenueButton.setBounds(531, 240, 139, 25);
		toolkit.adapt(BookVenueButton, true, true);
		BookVenueButton.setText("Book Venue");

		Label lblVenueDetails = new Label(ResultPageCompo, SWT.NONE);
		lblVenueDetails.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblVenueDetails.setBounds(450, 0, 100, 20);
		toolkit.adapt(lblVenueDetails, true, true);
		lblVenueDetails.setText("Venue Details:");

		Label lblSearchResult = new Label(ResultPageCompo, SWT.NONE);
		lblSearchResult.setBounds(10, 0, 94, 20);
		lblSearchResult.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search Result:");

		/***************************************************************************************
		 * 
		 * Table selection listener
		 * 
		 ***************************************************************************************/
		tableViewer = new TableViewer(ResultPageCompo, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
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
					VenueDetailTextbox.setText(detail);

					if(ButtonConfirmBookTime.getText().equals("Edit") == true)
						BookVenueButton.setEnabled(true);

				}
				catch(Exception exception)
				{
					// There should not be an exception here thanks 
					// to the way we design how users interact with the system.
				}

			}
		});
		DisplayTable.setHeaderVisible(true);
		DisplayTable.setBounds(10, 26, 411, 160);
		toolkit.paintBordersFor(DisplayTable);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnVenueid = tableViewerColumn_3.getColumn();
		tblclmnVenueid.setWidth(57);
		tblclmnVenueid.setText("Venue ID");
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getVenueID()).toString();
			}
		});

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnVenuename = tableViewerColumn_2.getColumn();
		tblclmnVenuename.setWidth(192);
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
		tblclmnCapacity.setWidth(69);
		tblclmnCapacity.setText("Capacity");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getMaxCapacity()).toString();
			}
		});

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCost = tableViewerColumn.getColumn();
		tblclmnCost.setWidth(70);
		tblclmnCost.setText("Cost");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Double) (venue.getCost() / 100.0)).toString();
			}
		});

		ViewCompo = new Composite(ResultPageCompo, SWT.NONE);
		ViewCompo.setBounds(439, 14, 241, 226);
		toolkit.adapt(ViewCompo);
		toolkit.paintBordersFor(ViewCompo);

		VenueDetailTextbox = new Text(ViewCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		VenueDetailTextbox.setEditable(false);
		VenueDetailTextbox.setBounds(10, 10, 221, 212);
		toolkit.adapt(VenueDetailTextbox, true, true);

		dt_SearchResult = new InputDateTimeComposite(ResultPageCompo, SWT.NONE);
		dt_SearchResult.setBounds(20, 170, 313, 100);
		toolkit.adapt(dt_SearchResult);
		toolkit.paintBordersFor(dt_SearchResult);

		ButtonConfirmBookTime = new Button(ResultPageCompo, SWT.NONE);
		ButtonConfirmBookTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					if(ButtonConfirmBookTime.getText().equals("Confirm") == true)
					{
						// Since users have just entered / modified the time slot.
						hasTimeSlotChecked = false;

						// Read users' inputs
						timeSlotChoiceInput = dt_SearchResult.readInputFields();

						// If the input is correct, then we reach this point.
						// GUI Settings
						BookVenueButton.setEnabled(true);
						dt_SearchResult.setEnabled(false);
						ButtonConfirmBookTime.setText("Edit");
					}
					else
					{
						// The command type is "Edit"
						dt_SearchResult.setEnabled(true);
						ButtonConfirmBookTime.setText("Confirm");
						BookVenueButton.setText("Book Venue");
					}
				}
				catch(Exception exception)
				{
					errormessageDialog errorBoard = new errormessageDialog(new Shell(),
							exception.getMessage());
					errorBoard.open();
				}
			}
		});
		ButtonConfirmBookTime.setBounds(339, 240, 75, 25);
		toolkit.adapt(ButtonConfirmBookTime, true, true);
		ButtonConfirmBookTime.setText("Confirm");

		TimeSlotCompo = new Composite(compoCriteriaFilled, SWT.NONE);
//		TimeSlotCompo.setLayoutDeferred(true);
		FormData fd_TimeSlotCompo = new FormData();
		fd_TimeSlotCompo.left = new FormAttachment(0, 190);
		TimeSlotCompo.setLayoutData(fd_TimeSlotCompo);
		TimeSlotCompo.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		TimeSlotCompo.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(TimeSlotCompo);
		toolkit.paintBordersFor(TimeSlotCompo);
		TimeSlotCompo.setLayout(new FormLayout());

		dt_SearchCriteria = new InputDateTimeComposite(TimeSlotCompo, SWT.NONE);
		FormData fd_dt_SearchCriteria = new FormData();
		fd_dt_SearchCriteria.bottom = new FormAttachment(100);
		fd_dt_SearchCriteria.right = new FormAttachment(100);
		fd_dt_SearchCriteria.top = new FormAttachment(0);
		fd_dt_SearchCriteria.left = new FormAttachment(0);
		dt_SearchCriteria.setLayoutData(fd_dt_SearchCriteria);
		dt_SearchCriteria.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(dt_SearchCriteria);
		toolkit.paintBordersFor(dt_SearchCriteria);
		
				Label lblSearchVenuesWith = new Label(SearchCriteriaCompo, SWT.NONE);
				fd_composite.top = new FormAttachment(lblSearchVenuesWith, 11);
				FormData fd_lblSearchVenuesWith = new FormData();
				fd_lblSearchVenuesWith.left = new FormAttachment(0, 5);
				lblSearchVenuesWith.setLayoutData(fd_lblSearchVenuesWith);
				lblSearchVenuesWith.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
				toolkit.adapt(lblSearchVenuesWith, true, true);
				lblSearchVenuesWith.setText("Search venues with specified criteria:");
				
				ButtonComp = new Composite(SearchCriteriaCompo, SWT.NONE);
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
										btnBackCriteria.setText("Back");
						
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
											errormessageDialog errorBoard = new errormessageDialog(new Shell(), exception.getMessage());
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
									errormessageDialog messageBoard = new errormessageDialog(new Shell(),
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
	}	
	
	/**
	 * Read time slot input from the user
	 * @throws Exception if the user forgets to enter some fields or the information entered is not correct.
	 */
	private void readTimeSlotInput() throws Exception
	{
		timeSlotChoiceInput = dt_SearchCriteria.readInputFields();
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
		BookVenueButton.setEnabled(false);

		if(chooseTimeSlotYet == false)
		{
			dt_SearchResult.setEnabled(true);
			dt_SearchResult.reset();
			ButtonConfirmBookTime.setText("Confirm");
		}
		else
		{
			// Time slot has been chosen
			dt_SearchResult.displayInputTimeSlot(timeSlotChoiceInput);
			dt_SearchResult.setEnabled(false);
			ButtonConfirmBookTime.setText("Edit");
			hasTimeSlotChecked = true;
		}
	}
	
	
}
