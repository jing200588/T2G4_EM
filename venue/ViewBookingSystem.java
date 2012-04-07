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
	private final StackLayout sl_compFunctionContent = new StackLayout();
	private final StackLayout stackLayoutCriteria = new StackLayout();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Text txtLowerBoundCapacity;
	private Text txtUpperBoundCapacity;
	private Text txtNameToSearch;
	private Text txtLowerBoundCost;
	private Text txtUpperBoundCost;
	private Composite compSearchName;
	private Composite compSearchCriteria;
	private Composite compFunctionContent;
	private Table tableDisplayResult;
	private TableViewer tableViewerDisplay;
	private boolean flagCapacityChoice;			
	private boolean flagCostChoice;				
	private boolean flagTimeSlotChoice;
	private Button btnTimeChoice;
	private Button btnCostChoice;
	private Button btnCapacityChoice;
	private Button btnCriteriaConfirm;
	private Button btnFindCriteria;
	private int[] capacityChoiceInput;
	private int[] costChoiceInput;
	private TimeSlot timeSlotChoiceInput;
	private Composite compFunctionOption;

	private Vector<Venue> searchResultList;
	private Button btnBookVenue;
	private Text txtVenueDetail;
	private Composite viewCompo;
	private int chosenVenueID;
	private EventItem event;
	private ControllerBookingSystem bookingSystem;
	private InputDateTimeComposite dtSearchCriteria;
	private InputDateTimeComposite dtSearchResult;
	private Composite compCapacity;
	private boolean hasTimeSlotChecked;
	private Button btnSearchName_1;
	private Button btnSearchCriteria;
	private Composite compResultPage;
	private Composite compoCriteriaFilled;

	private int currentCompo;					// 0 for time slot, 1 for capacity, 2 for cost
	private Composite costCompo;
	private Composite compTimeSlot;
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
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Showcard Gothic", 20, SWT.NORMAL));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Book Venue");
		VenueViewForm.getBody().setLayout(new FormLayout());

		Composite compMain = new Composite(VenueViewForm.getBody(), SWT.NONE);
		compMain.setLayout(new FormLayout());
		FormData fd_compMain = new FormData();
		fd_compMain.bottom = new FormAttachment(100);
		fd_compMain.left = new FormAttachment(0);
		fd_compMain.top = new FormAttachment(0);
		fd_compMain.right = new FormAttachment(100);
		compMain.setLayoutData(fd_compMain);
		toolkit.adapt(compMain);
		toolkit.paintBordersFor(compMain);


		compFunctionOption = new Composite(compMain, SWT.NONE);
		compFunctionOption.setLayout(new GridLayout(3, false));
		FormData fd_compFunctionOption = new FormData();
		fd_compFunctionOption.right = new FormAttachment(90);
		//fd_FunctionOptionCompo.bottom = new FormAttachment(0, 47);
		fd_compFunctionOption.top = new FormAttachment(10);
		fd_compFunctionOption.left = new FormAttachment(10);
		compFunctionOption.setLayoutData(fd_compFunctionOption);
		toolkit.adapt(compFunctionOption);
		toolkit.paintBordersFor(compFunctionOption);

		btnSearchName_1 = new Button(compFunctionOption, SWT.RADIO);
		btnSearchName_1.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		btnSearchName_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnSearchName_1.setSelection(true);
		btnSearchName_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sl_compFunctionContent.topControl = compSearchName;
				compFunctionContent.layout();
			}
		});
		toolkit.adapt(btnSearchName_1, true, true);
		btnSearchName_1.setText("Default Search");

		btnSearchCriteria = new Button(compFunctionOption, SWT.RADIO);
		btnSearchCriteria.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		btnSearchCriteria.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnSearchCriteria.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnCriteriaConfirm.setText("Confirm");
				btnNextCriteria.setEnabled(false);
				btnBackCriteria.setEnabled(false);
				btnFindCriteria.setEnabled(false);
				
				// Enable all the choice button
				btnCostChoice.setEnabled(true);
				btnTimeChoice.setEnabled(true);
				btnCapacityChoice.setEnabled(true);
				
				sl_compFunctionContent.topControl = compSearchCriteria;
				compFunctionContent.layout();
				
				stackLayoutCriteria.topControl = compTimeSlot;
				dtSearchCriteria.setEnabled(false);
				compoCriteriaFilled.layout();
			}
		});
		toolkit.adapt(btnSearchCriteria, true, true);
		btnSearchCriteria.setText("Advanced Search");

		Label labelEventTime = new Label(compFunctionOption, SWT.NONE);
		labelEventTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		labelEventTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(labelEventTime, true, true);
		String strEventTime = "Event time: FROM " + event.getStartDateTime().getDateRepresentation() +
				" " + event.getStartDateTime().getTimeRepresentation() + " TO " +
				event.getEndDateTime().getDateRepresentation() + " " + event.getEndDateTime().getTimeRepresentation();
		labelEventTime.setText(strEventTime);

		compFunctionContent = new Composite(compMain, SWT.NONE);
		FormData fd_compFunctionContent = new FormData();
		fd_compFunctionContent.top = new FormAttachment(compFunctionOption, 20);
		fd_compFunctionContent.right = new FormAttachment(90);
		fd_compFunctionContent.bottom = new FormAttachment(90);
		fd_compFunctionContent.left = new FormAttachment(10);
		compFunctionContent.setLayoutData(fd_compFunctionContent);
	//	functionContentCompo.setBackgroundMode(SWT.INHERIT_DEFAULT);
		toolkit.adapt(compFunctionContent);
		toolkit.paintBordersFor(compFunctionContent);
		compFunctionContent.setLayout(sl_compFunctionContent);

		compSearchName = new Composite(compFunctionContent, SWT.NONE);
		compSearchName.setBounds(0, 5, 675, 280);
		toolkit.adapt(compSearchName);
		toolkit.paintBordersFor(compSearchName);
		compSearchName.setLayout(new FormLayout());
		sl_compFunctionContent.topControl = compSearchName;
		compFunctionContent.layout();
		
		Label lblSearchVenueBy = new Label(compSearchName, SWT.NONE);
		FormData fd_lblSearchVenueBy = new FormData();
		fd_lblSearchVenueBy.left = new FormAttachment(0, 5);
		lblSearchVenueBy.setLayoutData(fd_lblSearchVenueBy);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.BOLD));
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name / address: ");
		
		Composite compName = new Composite(compSearchName, SWT.NONE);
		FormData fd_compName = new FormData();
		fd_compName.right = new FormAttachment(100);
		fd_compName.top = new FormAttachment(0, 95);
		fd_compName.left = new FormAttachment(0);
		compName.setLayoutData(fd_compName);
		toolkit.adapt(compName);
		toolkit.paintBordersFor(compName);
		compName.setLayout(new GridLayout(4, false));

		Label lblEnterTheName = new Label(compName, SWT.NONE);
		lblEnterTheName.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");

		txtNameToSearch = new Text(compName, SWT.BORDER);
		txtNameToSearch.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtNameToSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtNameToSearch, true, true);

		Button btnSearchName = new Button(compName, SWT.NONE);
		btnSearchName.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnSearchName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String venueName = HelperFunctions.removeRedundantWhiteSpace(txtNameToSearch.getText());
				txtNameToSearch.setText(venueName);
				if(venueName == null || venueName.equals("") == true)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), "You have not entered a venue name or address to search yet!");
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

						tableViewerDisplay.setInput(searchResultList);	

						// GUI Setting
						// Note that if user chooses this option, he / she has not decided the time slot
						btnSearchName_1.setSelection(false);
						resetResultPageView(false);
						
						sl_compFunctionContent.topControl = compResultPage;
						compFunctionContent.layout();
					}
				}
			}
		});
		toolkit.adapt(btnSearchName, true, true);
		btnSearchName.setText("Search and view information");

		compSearchCriteria = new Composite(compFunctionContent, SWT.NONE);
		compSearchCriteria.setBounds(0, 129, 331, 318);
		toolkit.adapt(compSearchCriteria);
		toolkit.paintBordersFor(compSearchCriteria);
		compSearchCriteria.setLayout(new FormLayout());
		
		Composite compoCriteria = new Composite(compSearchCriteria, SWT.NONE);
		compoCriteria.setLayout(new GridLayout(4, false));
		FormData fd_compoCriteria = new FormData();
		compoCriteria.setLayoutData(fd_compoCriteria);
		toolkit.adapt(compoCriteria);
		toolkit.paintBordersFor(compoCriteria);
		
				Label lblPleaseChooseYour = new Label(compoCriteria, SWT.NONE);
				lblPleaseChooseYour.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
				lblPleaseChooseYour.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				toolkit.adapt(lblPleaseChooseYour, true, true);
				lblPleaseChooseYour.setText("Please choose your preferred criteria:");
						new Label(compoCriteria, SWT.NONE);
				
						btnCapacityChoice = new Button(compoCriteria, SWT.CHECK);
						btnCapacityChoice.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
						btnCapacityChoice.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						btnCapacityChoice.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								flagCapacityChoice = btnCapacityChoice.getSelection();

								if(flagCapacityChoice == true)
									btnCriteriaConfirm.setEnabled(true);
								else
								{
									if(flagTimeSlotChoice == false && flagCostChoice == false)
										btnCriteriaConfirm.setEnabled(false);
								}
							}
						});
						toolkit.adapt(btnCapacityChoice, true, true);
						btnCapacityChoice.setText("Capacity");
								
										btnTimeChoice = new Button(compoCriteria, SWT.CHECK);
										btnTimeChoice.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
										btnTimeChoice.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
										btnTimeChoice.addSelectionListener(new SelectionAdapter() {
											@Override
											public void widgetSelected(SelectionEvent e) {
												flagTimeSlotChoice = btnTimeChoice.getSelection();

												if(flagTimeSlotChoice == true)
													btnCriteriaConfirm.setEnabled(true);
												else
												{
													if(flagCapacityChoice == false && flagCostChoice == false)
														btnCriteriaConfirm.setEnabled(false);
												}
											}
										});
										toolkit.adapt(btnTimeChoice, true, true);
										btnTimeChoice.setText("Time");
										
												btnCostChoice = new Button(compoCriteria, SWT.CHECK);
												btnCostChoice.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
												btnCostChoice.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
												btnCostChoice.addSelectionListener(new SelectionAdapter() {
													@Override
													public void widgetSelected(SelectionEvent e) {
														flagCostChoice = btnCostChoice.getSelection();

														if(flagCostChoice == true)
															btnCriteriaConfirm.setEnabled(true);
														else
														{
															if(flagTimeSlotChoice == false && flagCapacityChoice == false)
																btnCriteriaConfirm.setEnabled(false);
														}
													}
												});
												toolkit.adapt(btnCostChoice, true, true);
												btnCostChoice.setText("Cost");
										
												btnCriteriaConfirm = new Button(compoCriteria, SWT.NONE);		
												btnCriteriaConfirm.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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
															btnCostChoice.setEnabled(false);
															btnTimeChoice.setEnabled(false);
															btnCapacityChoice.setEnabled(false);
															
															// Set enabled for capacityCompo and costCompo
															setEnabledCapacityCompo(true);
															setEnabledCostCompo(true);
															
															// Display the following composites in order if they are chosen by the user
															if(flagTimeSlotChoice == true)
															{
																stackLayoutCriteria.topControl = compTimeSlot;
																dtSearchCriteria.setEnabled(true);
																compoCriteriaFilled.layout();
																currentCompo = 0;
															}
															else
															{
																if(flagCapacityChoice == true)
																{
																	stackLayoutCriteria.topControl = compCapacity;
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
															btnCostChoice.setEnabled(true);
															btnTimeChoice.setEnabled(true);
															btnCapacityChoice.setEnabled(true);
															
															// Change the layout
															stackLayoutCriteria.topControl = compTimeSlot;
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

		compoCriteriaFilled = new Composite(compSearchCriteria, SWT.NONE);
		fd_compoCriteria.left = new FormAttachment(10,0);
		FormData fd_compoCriteriaFilled = new FormData();
		fd_compoCriteriaFilled.top = new FormAttachment(compoCriteria, 30);
		fd_compoCriteriaFilled.right = new FormAttachment(90);
		fd_compoCriteriaFilled.left = new FormAttachment(10);
		compoCriteriaFilled.setLayoutData(fd_compoCriteriaFilled);
		toolkit.adapt(compoCriteriaFilled);
		toolkit.paintBordersFor(compoCriteriaFilled);
		compoCriteriaFilled.setLayout(stackLayoutCriteria);

		compCapacity = new Composite(compoCriteriaFilled, SWT.NONE);
		toolkit.adapt(compCapacity);
		toolkit.paintBordersFor(compCapacity);
		compCapacity.setLayout(new FormLayout());

		Label lblEstimatedCapacity = new Label(compCapacity, SWT.NONE);
		lblEstimatedCapacity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		FormData fd_lblEstimatedCapacity = new FormData();
		fd_lblEstimatedCapacity.top = new FormAttachment(0, 5);
		fd_lblEstimatedCapacity.left = new FormAttachment(0, 5);
		lblEstimatedCapacity.setLayoutData(fd_lblEstimatedCapacity);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		Composite compInputCapacity = new Composite(compCapacity, SWT.NONE);
		compInputCapacity.setLayout(new GridLayout(4, false));
		FormData fd_compInputCapacity = new FormData();
		fd_compInputCapacity.right = new FormAttachment(100);
		fd_compInputCapacity.top = new FormAttachment(50);
		fd_compInputCapacity.left = new FormAttachment(10);
		compInputCapacity.setLayoutData(fd_compInputCapacity);
		toolkit.adapt(compInputCapacity);
		toolkit.paintBordersFor(compInputCapacity);
		
		Label lblCapacityFrom = new Label(compInputCapacity, SWT.NONE);
		lblCapacityFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblCapacityFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lblCapacityFrom, true, true);
		lblCapacityFrom.setText("Capacity from:");

		txtLowerBoundCapacity = new Text(compInputCapacity, SWT.BORDER);
		txtLowerBoundCapacity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtLowerBoundCapacity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtLowerBoundCapacity, true, true);
		
				Label lblCapcityTo = new Label(compInputCapacity, SWT.NONE);
				lblCapcityTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
				lblCapcityTo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				toolkit.adapt(lblCapcityTo, true, true);
				lblCapcityTo.setText("to");

		txtUpperBoundCapacity = new Text(compInputCapacity, SWT.BORDER);
		txtUpperBoundCapacity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtUpperBoundCapacity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtUpperBoundCapacity, true, true);

		costCompo = new Composite(compoCriteriaFilled, SWT.NONE);
		toolkit.adapt(costCompo);
		toolkit.paintBordersFor(costCompo);
		costCompo.setLayout(new FormLayout());

		Label lblPreferredCost = new Label(costCompo, SWT.NONE);
		lblPreferredCost.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		FormData fd_lblPreferredCost = new FormData();
		fd_lblPreferredCost.top = new FormAttachment(0, 5);
		fd_lblPreferredCost.left = new FormAttachment(0, 5);
		lblPreferredCost.setLayoutData(fd_lblPreferredCost);
		lblPreferredCost.setText("Preferred cost:");
		toolkit.adapt(lblPreferredCost, true, true);
		
		Composite compInputCost = new Composite(costCompo, SWT.NONE);
		compInputCost.setLayout(new GridLayout(4, false));
		FormData fd_compInputCost = new FormData();
		fd_compInputCost.right = new FormAttachment(100);
		fd_compInputCost.top = new FormAttachment(50);
		fd_compInputCost.left = new FormAttachment(10);
		compInputCost.setLayoutData(fd_compInputCost);
		toolkit.adapt(compInputCost);
		toolkit.paintBordersFor(compInputCost);
						
						Label lblCostFrom = new Label(compInputCost,SWT.NONE);
						lblCostFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
						lblCostFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(lblCostFrom, true, true);
						lblCostFrom.setText("Cost from:");
				
						txtLowerBoundCost = new Text(compInputCost, SWT.BORDER);
						txtLowerBoundCost.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
						txtLowerBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
						toolkit.adapt(txtLowerBoundCost, true, true);
								
										Label lblCostTo = new Label(compInputCost, SWT.NONE);
										lblCostTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
										lblCostTo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
										lblCostTo.setText("to");
										toolkit.adapt(lblCostTo, true, true);
												
														txtUpperBoundCost = new Text(compInputCost, SWT.BORDER);
														txtUpperBoundCost.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
														txtUpperBoundCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
														toolkit.adapt(txtUpperBoundCost, true, true);
		fd_compoCriteria.right = new FormAttachment(90, 0);

		compResultPage = new Composite(compFunctionContent, SWT.NONE);
		compResultPage.setBounds(0, 70, 330, 238);
		toolkit.adapt(compResultPage);
		toolkit.paintBordersFor(compResultPage);
		compResultPage.setLayout(new FormLayout());
		btnBookVenue = new Button(compResultPage, SWT.NONE);
		btnBookVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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
						if(choice == null || choice.equals("Yes") == false)
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
		
		Composite compSearchResultTable = new Composite(compResultPage, SWT.NONE);
		FormData fd_compSearchResultTable = new FormData();
		fd_compSearchResultTable.bottom = new FormAttachment(70);
		fd_compSearchResultTable.top = new FormAttachment(0);
		fd_compSearchResultTable.left = new FormAttachment(0);
		compSearchResultTable.setLayoutData(fd_compSearchResultTable);
		toolkit.adapt(compSearchResultTable);
		toolkit.paintBordersFor(compSearchResultTable);
		compSearchResultTable.setLayout(new GridLayout(1, false));

		Label lblSearchResult = new Label(compSearchResultTable, SWT.NONE);
		lblSearchResult.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSearchResult.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.BOLD));
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search Result:");

		/***************************************************************************************
		 * 
		 * Table selection listener
		 * 
		 ***************************************************************************************/
		Composite compTable = new Composite(compSearchResultTable, SWT.NONE);
		TableColumnLayout tcl_compTable = new TableColumnLayout();
		compTable.setLayout(tcl_compTable);
		compTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(compTable);
		toolkit.paintBordersFor(compTable);
		
		tableViewerDisplay = new TableViewer(compTable, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableDisplayResult = tableViewerDisplay.getTable();
		tableDisplayResult.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		tableDisplayResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					TableItem item = tableViewerDisplay.getTable().getItem(tableViewerDisplay.getTable().getSelectionIndex());
					String idString = item.getText(0);
					chosenVenueID = Integer.parseInt(idString);

					String detail = bookingSystem.getVenueDetail(chosenVenueID);
					txtVenueDetail.setText(detail);
				}
				catch(Exception exception)
				{
					// There should not be an exception here thanks 
					// to the way we design how users interact with the system.
				}

			}
		});
		tableDisplayResult.setHeaderVisible(true);
		toolkit.paintBordersFor(tableDisplayResult);
		tableViewerDisplay.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn tableViewerColumnVenueId = new TableViewerColumn(tableViewerDisplay, SWT.NONE);
		TableColumn tableColumnVenueId = tableViewerColumnVenueId.getColumn();
		tableColumnVenueId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.VENUEID);
				tableViewerDisplay.refresh();
			}
		});
		tcl_compTable.setColumnData(tableColumnVenueId, new ColumnWeightData(20));
		//tblclmnVenueid.setWidth(57);
		tableColumnVenueId.setText("Venue ID");
		tableViewerColumnVenueId.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getVenueID()).toString();
			}
		});
		tableColumnVenueId.pack();
		
		TableViewerColumn tableViewerColumnVenueName = new TableViewerColumn(tableViewerDisplay, SWT.NONE);
		TableColumn tableColumnVenuename = tableViewerColumnVenueName.getColumn();
		tableColumnVenuename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.NAME);
				tableViewerDisplay.refresh();
			}
		});
		tcl_compTable.setColumnData(tableColumnVenuename, new ColumnWeightData(30));
		//tblclmnVenuename.setWidth(192);
		tableColumnVenuename.setText("Venue Name");
		tableViewerColumnVenueName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return venue.getName();
			}
		});

		TableViewerColumn tableViewerColumnCapacity = new TableViewerColumn(tableViewerDisplay, SWT.NONE);
		TableColumn tableColumnCapacity = tableViewerColumnCapacity.getColumn();
		tableColumnCapacity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.CAPACITY);
				tableViewerDisplay.refresh();
			}
		});
		tcl_compTable.setColumnData(tableColumnCapacity, new ColumnWeightData(20));
		tableColumnCapacity.setText("Capacity");
		tableViewerColumnCapacity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Integer) venue.getMaxCapacity()).toString();
			}
		});
		tableColumnCapacity.pack();
		
		TableViewerColumn tableViewerColumnCost = new TableViewerColumn(tableViewerDisplay, SWT.NONE);
		TableColumn tableColumnCost = tableViewerColumnCost.getColumn();
		tableColumnCost.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ControllerBookingSystem.sortVenueList(searchResultList, ControllerBookingSystem.SortCriteria.COST);
				tableViewerDisplay.refresh();
			}
		});
		tcl_compTable.setColumnData(tableColumnCost, new ColumnWeightData(15));
		//tblclmnCost.setWidth(70);
		tableColumnCost.setText("Cost");
		tableViewerColumnCost.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Double) (venue.getCost() / 100.0)).toString();
			}
		});
		//tblclmnCost.pack();
		
		viewCompo = new Composite(compResultPage, SWT.NONE);
		FormData fd_viewCompo = new FormData();
		//	fd_ViewCompo.left = new FormAttachment(composite_4);
		fd_viewCompo.bottom = new FormAttachment(btnBookVenue);
		fd_compSearchResultTable.right = new FormAttachment(viewCompo);
		fd_viewCompo.left = new FormAttachment(65);
		fd_viewCompo.right = new FormAttachment(100);
		fd_viewCompo.top = new FormAttachment(0);
		viewCompo.setLayoutData(fd_viewCompo);
		toolkit.adapt(viewCompo);
		toolkit.paintBordersFor(viewCompo);
		viewCompo.setLayout(new GridLayout(1, false));
		
				Label lblVenueDetails = new Label(viewCompo, SWT.NONE);
				lblVenueDetails.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
				lblVenueDetails.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.BOLD));
				toolkit.adapt(lblVenueDetails, true, true);
				lblVenueDetails.setText("Venue Details:");

		txtVenueDetail = new Text(viewCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtVenueDetail.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		GridData gd_txtVenueDetail = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtVenueDetail.heightHint = 65;
		txtVenueDetail.setLayoutData(gd_txtVenueDetail);
		txtVenueDetail.setEditable(false);
		toolkit.adapt(txtVenueDetail, true, true);

		dtSearchResult = new InputDateTimeComposite(compResultPage, SWT.NONE);
		dtSearchResult.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		FormData fd_dtSearchResult = new FormData();
		fd_dtSearchResult.top = new FormAttachment(compSearchResultTable, 5);
		fd_dtSearchResult.right = new FormAttachment(compSearchResultTable, 0, SWT.RIGHT);
		fd_dtSearchResult.bottom = new FormAttachment(100);
		fd_dtSearchResult.left = new FormAttachment(0);
		dtSearchResult.setLayoutData(fd_dtSearchResult);
		toolkit.adapt(dtSearchResult);
		toolkit.paintBordersFor(dtSearchResult);

		compTimeSlot = new Composite(compoCriteriaFilled, SWT.NONE);
		FormData fd_TimeSlotCompo = new FormData();
		fd_TimeSlotCompo.left = new FormAttachment(0, 190);
		compTimeSlot.setLayoutData(fd_TimeSlotCompo);
		compTimeSlot.setBackgroundImage(compSearchCriteria.getBackgroundImage());
//		timeSlotCompo.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(compTimeSlot);
		toolkit.paintBordersFor(compTimeSlot);
		compTimeSlot.setLayout(new FormLayout());

		dtSearchCriteria = new InputDateTimeComposite(compTimeSlot, SWT.NONE);
		dtSearchCriteria.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		FormData fd_dtSearchCriteria = new FormData();
		fd_dtSearchCriteria.bottom = new FormAttachment(100);
		fd_dtSearchCriteria.right = new FormAttachment(100);
		fd_dtSearchCriteria.top = new FormAttachment(0);
		fd_dtSearchCriteria.left = new FormAttachment(0);
		dtSearchCriteria.setLayoutData(fd_dtSearchCriteria);
//		dtSearchCriteria.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(dtSearchCriteria);
		toolkit.paintBordersFor(dtSearchCriteria);
		
				Label lblSearchVenuesWith = new Label(compSearchCriteria, SWT.NONE);
				fd_compoCriteria.top = new FormAttachment(lblSearchVenuesWith, 11);
				FormData fd_lblSearchVenuesWith = new FormData();
				fd_lblSearchVenuesWith.left = new FormAttachment(0, 5);
				lblSearchVenuesWith.setLayoutData(fd_lblSearchVenuesWith);
				lblSearchVenuesWith.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.BOLD));
				toolkit.adapt(lblSearchVenuesWith, true, true);
				lblSearchVenuesWith.setText("Search venues with specified criteria:");
				
				Composite compButton = new Composite(compSearchCriteria, SWT.NONE);
				compButton.setLayout(new GridLayout(3, false));
				FormData fd_compButton = new FormData();
				fd_compButton.top = new FormAttachment(compoCriteriaFilled, 20);
				fd_compButton.left = new FormAttachment(10);
				fd_compButton.right = new FormAttachment(90);
				compButton.setLayoutData(fd_compButton);
				toolkit.adapt(compButton);
				toolkit.paintBordersFor(compButton);
								
										btnBackCriteria = new Button(compButton, SWT.NONE);
										GridData gd_btnBackCriteria = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
										gd_btnBackCriteria.widthHint = 80;
										btnBackCriteria.setLayoutData(gd_btnBackCriteria);
										btnBackCriteria.addSelectionListener(new SelectionAdapter() {
											@Override
											public void widgetSelected(SelectionEvent e) {
												// Set enabled for the capacityCompo and costCompo
												setEnabledCapacityCompo(true);
												setEnabledCostCompo(true);
												
												int previousCompo = hasPreviousCriteria();
												if(previousCompo >= 0)
													currentCompo = previousCompo;
												switch(currentCompo)
												{
													case 0:
														stackLayoutCriteria.topControl = compTimeSlot;
														break;
													case 1:
														stackLayoutCriteria.topControl = compCapacity;
														break;
												}
												compoCriteriaFilled.layout();
												btnNextCriteria.setText("Next");
												btnNextCriteria.setEnabled(true);
												if(hasPreviousCriteria() < 0)
													btnBackCriteria.setEnabled(false);
												
												btnFindCriteria.setEnabled(false);
											}
										});
										btnBackCriteria.setEnabled(false);
										toolkit.adapt(btnBackCriteria, true, true);
										btnBackCriteria.setText("Previous");
						
								btnNextCriteria = new Button(compButton, SWT.NONE);
								GridData gd_btnNextCriteria = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
								gd_btnNextCriteria.widthHint = 80;
								btnNextCriteria.setLayoutData(gd_btnNextCriteria);
								btnNextCriteria.setEnabled(false);
								btnNextCriteria.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										try
										{
											if(btnNextCriteria.getText().equals("Edit") == true)
											{
												// This is the last composite the user has to type in
												btnFindCriteria.setEnabled(false);
												btnNextCriteria.setText("Confirm");
												switch(currentCompo)
												{
													case 0:
														dtSearchCriteria.setEnabled(true);
														break;
													case 1:
														setEnabledCapacityCompo(true);
														break;
													case 2:
														setEnabledCostCompo(true);
												}
												return;
											}
											
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
												btnNextCriteria.setText("Edit");
												switch(currentCompo)
												{
													case 0:
														dtSearchCriteria.setEnabled(false);
														break;
													case 1:
														setEnabledCapacityCompo(false);
														break;
													case 2:
														setEnabledCostCompo(false);
												}
												return;
											}
											
											int nextCompo = hasNextCriteria();
											if(nextCompo <= 2)
												currentCompo = nextCompo;
											switch(currentCompo)
											{
												case 1:
													stackLayoutCriteria.topControl = compCapacity;
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
				
						btnFindCriteria = new Button(compButton, SWT.NONE);
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

									tableViewerDisplay.setInput(searchResultList);
									// GUI Settings
									btnSearchCriteria.setSelection(false);
									tableViewerDisplay.refresh();

									resetResultPageView(flagTimeSlotChoice);

									sl_compFunctionContent.topControl = compResultPage;
									compFunctionContent.layout();
								}
							}
						});
						toolkit.adapt(btnFindCriteria, true, true);
						btnFindCriteria.setText("Find All Suitable Venues");
						
	Button btnBack = new Button(compName, SWT.NONE);
	btnBack.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
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
	compFunctionOption.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compMain.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compFunctionContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	VenueViewForm.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
	VenueViewForm.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnSearchName_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnSearchCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	labelEventTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compSearchName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenueBy.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEnterTheName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnSearchName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compSearchCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compoCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblPleaseChooseYour.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnCapacityChoice.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnTimeChoice.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnCostChoice.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnCriteriaConfirm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblEstimatedCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compInputCapacity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCapacityFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCapcityTo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnBookVenue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compSearchResultTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	viewCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblVenueDetails.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblSearchVenuesWith.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnBackCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnNextCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	btnFindCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	costCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compInputCost.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCostFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	lblCostTo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	compResultPage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	dtSearchCriteria.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	
	Composite compOverall = new Composite(compMain, SWT.NONE);
	//composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	FormData fd_compOverall = new FormData();
	fd_compOverall.top = new FormAttachment(0);
	fd_compOverall.left = new FormAttachment(0);
	fd_compOverall.bottom = new FormAttachment(100);
	fd_compOverall.right = new FormAttachment(100);
	compOverall.setLayoutData(fd_compOverall);
	toolkit.adapt(compOverall);
	toolkit.paintBordersFor(compOverall);
	compOverall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
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
			String lowerCapacity = HelperFunctions.removeAllWhiteSpace(txtLowerBoundCapacity.getText());
			txtLowerBoundCapacity.setText(lowerCapacity);
			if(lowerCapacity == null || lowerCapacity.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the capacity!");
			}
			String upperCapacity = HelperFunctions.removeAllWhiteSpace(txtUpperBoundCapacity.getText());
			txtUpperBoundCapacity.setText(upperCapacity);
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
			String lowerCost = HelperFunctions.removeAllWhiteSpace(txtLowerBoundCost.getText());
			txtLowerBoundCost.setText(lowerCost);
			if(lowerCost == null || lowerCost.equals("") == true)
			{
				throw new Exception("You have not entered the lower bound of the cost!");
			}
			
			String upperCost = HelperFunctions.removeAllWhiteSpace(txtUpperBoundCost.getText());
			txtUpperBoundCost.setText(upperCost);
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
		if(money.length() - decimalPointIndex == 1 || money.length() - decimalPointIndex > 3)
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
		tableViewerDisplay.refresh();
		txtVenueDetail.setText("");
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
	
	/**
	 * Enable / disable components in the capacityCompo
	 * @param isEnabled
	 */
	private void setEnabledCapacityCompo(boolean isEnabled)
	{
		txtLowerBoundCapacity.setEnabled(isEnabled);
		txtUpperBoundCapacity.setEnabled(isEnabled);
	}
	
	/**
	 * Enable / disable components in the costCompo
	 * @param isEnabled
	 */
	private void setEnabledCostCompo(boolean isEnabled)
	{
		txtLowerBoundCost.setEnabled(isEnabled);
		txtUpperBoundCost.setEnabled(isEnabled);
	}
}
