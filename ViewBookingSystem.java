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


public class ViewBookingSystem extends Composite {
	private final StackLayout stackLayout = new StackLayout();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private Text LowerBoundCapacity;
	private Text UpperBoundCapacity;
	private Text NameToSearch;
	private Text LowerBoundCost;
	private Text UpperBoundCost;
	private Composite SearchNameCompo;
	private Composite SearchCriteriaCompo;
	private Composite AddOutsideVenue;
	private Text OutsideNameField;
	private Text AddressField;
	private Text CapacityField;
	private Text CostField;
	private Text DescriptionField;
	private Composite FunctionContentPage;
	private Text txtIndicatesA;
	private Table DisplayTable;
	private TableViewer tableViewer;
	private Text OutsideVenueErrorBoard;
	private boolean flagCapacityChoice;			
	private boolean flagCostChoice;				
	private boolean flagTimeSlotChoice;
	private Button CapacityConfirmButton;
	private Button TimeChoiceButton;
	private Button CostChoiceButton;
	private Button CapacityChoiceButton;
	private Button CriteriaConfirmButton;
	private Button TimeConfirmButton;
	private Button CostConfirmButton;
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
	private Text text;
	private ControllerBookingSystem bookingSystem;
	private InputDateTimeComposite dt_SearchCriteria;
	private InputDateTimeComposite dt_AddOutside;
	private InputDateTimeComposite dt_SearchResult;
	private Composite CapacityCompo;
	private Button ButtonConfirmBookTime;
	private boolean hasTimeSlotChecked;
	private Button SearchNameButton;
	private Button SearchCriteriaButton;
	private Composite ResultPageCompo;
	
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
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.bottom = new FormAttachment(100, -24);
		fd_mainComposite.left = new FormAttachment(0, 28);
		fd_mainComposite.top = new FormAttachment(50, -159);
		fd_mainComposite.right = new FormAttachment(50, 349);
		mainComposite.setLayoutData(fd_mainComposite);
		toolkit.adapt(mainComposite);
		toolkit.paintBordersFor(mainComposite);


		FunctionOptionCompo = new Composite(mainComposite, SWT.NONE);
		FunctionOptionCompo.setBounds(10, 10, 680, 37);
		toolkit.adapt(FunctionOptionCompo);
		toolkit.paintBordersFor(FunctionOptionCompo);
		
		Label lblChooseFunctionality = new Label(FunctionOptionCompo, SWT.NONE);
		//lblChooseFunctionality.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblChooseFunctionality.setBounds(0, 10, 113, 20);
		toolkit.adapt(lblChooseFunctionality, true, true);
		lblChooseFunctionality.setText("Choose functionality:");
		
		SearchNameButton = new Button(FunctionOptionCompo, SWT.RADIO);
		SearchNameButton.setSelection(true);
		SearchNameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = SearchNameCompo;
				FunctionContentPage.layout();
			}
		});
		SearchNameButton.setBounds(117, 9, 140, 15);
		toolkit.adapt(SearchNameButton, true, true);
		SearchNameButton.setText("Search venue by name");
		
		SearchCriteriaButton = new Button(FunctionOptionCompo, SWT.RADIO);
		SearchCriteriaButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = SearchCriteriaCompo;
				FunctionContentPage.layout();
			}
		});
		SearchCriteriaButton.setBounds(274, 9, 221, 16);
		toolkit.adapt(SearchCriteriaButton, true, true);
		SearchCriteriaButton.setText("Search venues with specified criteria");
		
		Button AddOutsideVenueButton = new Button(FunctionOptionCompo, SWT.RADIO);
		AddOutsideVenueButton.setEnabled(false);
		AddOutsideVenueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = AddOutsideVenue;
				FunctionContentPage.layout();
			}
		});
		AddOutsideVenueButton.setBounds(501, 9, 169, 16);
		toolkit.adapt(AddOutsideVenueButton, true, true);
		AddOutsideVenueButton.setText("Add an event outside NUS");

		FunctionContentPage = new Composite(mainComposite, SWT.NONE);
		FunctionContentPage.setBackgroundMode(SWT.INHERIT_DEFAULT);
		FunctionContentPage.setBounds(10, 51, 680, 284);
		toolkit.adapt(FunctionContentPage);
		toolkit.paintBordersFor(FunctionContentPage);
		FunctionContentPage.setLayout(stackLayout);

		SearchNameCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchNameCompo.setBounds(0, 5, 675, 280);
		toolkit.adapt(SearchNameCompo);
		toolkit.paintBordersFor(SearchNameCompo);
		
		Label lblSearchVenueBy = new Label(SearchNameCompo, SWT.NONE);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblSearchVenueBy.setBounds(10, 10, 170, 21);
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		Label lblEnterTheName = new Label(SearchNameCompo, SWT.NONE);
		lblEnterTheName.setBounds(20, 31, 160, 15);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");
		
		NameToSearch = new Text(SearchNameCompo, SWT.BORDER);
		NameToSearch.setBounds(186, 28, 243, 21);
		toolkit.adapt(NameToSearch, true, true);
		
		Button ButtonSearchName = new Button(SearchNameCompo, SWT.NONE);
		ButtonSearchName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String venueName = NameToSearch.getText();
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
		ButtonSearchName.setBounds(252, 55, 177, 25);
		toolkit.adapt(ButtonSearchName, true, true);
		ButtonSearchName.setText("Search and view information");
		
		SearchCriteriaCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchCriteriaCompo.setBounds(0, 129, 331, 318);
		toolkit.adapt(SearchCriteriaCompo);
		toolkit.paintBordersFor(SearchCriteriaCompo);
		
		Composite composite_4 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_4.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		composite_4.setBounds(10, 10, 321, 112);
		toolkit.adapt(composite_4);
		toolkit.paintBordersFor(composite_4);
		
		Label lblSearchVenuesWith = new Label(composite_4, SWT.NONE);
		lblSearchVenuesWith.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblSearchVenuesWith.setBounds(0, 9, 266, 25);
		toolkit.adapt(lblSearchVenuesWith, true, true);
		lblSearchVenuesWith.setText("Search venues with specified criteria:");
		
		Label lblPleaseChooseYour = new Label(composite_4, SWT.NONE);
		lblPleaseChooseYour.setBounds(20, 31, 209, 15);
		toolkit.adapt(lblPleaseChooseYour, true, true);
		lblPleaseChooseYour.setText("Please choose your preferred criteria:");
		
		CapacityChoiceButton = new Button(composite_4, SWT.CHECK);
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
		
		CapacityChoiceButton.setBounds(102, 52, 93, 16);
		toolkit.adapt(CapacityChoiceButton, true, true);
		CapacityChoiceButton.setText("Capacity");
		
		TimeChoiceButton = new Button(composite_4, SWT.CHECK);
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
		TimeChoiceButton.setBounds(30, 52, 66, 16);
		toolkit.adapt(TimeChoiceButton, true, true);
		TimeChoiceButton.setText("Time");
		
		CostChoiceButton = new Button(composite_4, SWT.CHECK);
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
		CostChoiceButton.setBounds(207, 52, 93, 16);
		toolkit.adapt(CostChoiceButton, true, true);
		CostChoiceButton.setText("Cost");
		
		CriteriaConfirmButton = new Button(composite_4, SWT.NONE);
		CriteriaConfirmButton.setEnabled(false);
		CriteriaConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String commandType = CriteriaConfirmButton.getText();
				
				if(commandType.equals("Confirm") == true)
				{
					// Disable all the criteria choice buttons
					CapacityChoiceButton.setEnabled(false);
					CostChoiceButton.setEnabled(false);
					TimeChoiceButton.setEnabled(false);
				
					// Change the command type from "Confirm" to "Edit"
					CriteriaConfirmButton.setText("Edit");
					
					// Enable the corresponding choice section
					if(flagCapacityChoice == true)
					{
						LowerBoundCapacity.setEnabled(true);
						UpperBoundCapacity.setEnabled(true);
						CapacityConfirmButton.setEnabled(true);
					}
					else
					{
						LowerBoundCapacity.setText("");
						UpperBoundCapacity.setText("");
						
					}
					CapacityConfirmButton.setText("Confirm");
					
					if(flagCostChoice == true)
					{
						LowerBoundCost.setEnabled(true);
						UpperBoundCost.setEnabled(true);
						CostConfirmButton.setEnabled(true);
					}
					else
					{
						LowerBoundCost.setText("");
						UpperBoundCost.setText("");
					}
					CostConfirmButton.setText("Confirm");
					
					if(flagTimeSlotChoice == true)
					{
						dt_SearchCriteria.setEnabled(true);
						TimeConfirmButton.setEnabled(true);
					}
					else
					{
						dt_SearchCriteria.reset();	
					}
					TimeConfirmButton.setText("Confirm");
				}
				else
				{
					// The case when the command is "Edit"
					// Enable the choice buttons
					CapacityChoiceButton.setEnabled(true);
					TimeChoiceButton.setEnabled(true);
					CostChoiceButton.setEnabled(true);
					// Change command type to "Confirm"
					CriteriaConfirmButton.setText("Confirm");
					
					// Disable all the text-boxes and buttons in all three choice sections
					LowerBoundCapacity.setEnabled(false);
					UpperBoundCapacity.setEnabled(false);
					CapacityConfirmButton.setEnabled(false);
					LowerBoundCost.setEnabled(false);
					UpperBoundCost.setEnabled(false);
					CostConfirmButton.setEnabled(false);
					dt_SearchCriteria.setEnabled(false);
					TimeConfirmButton.setEnabled(false);
					FindCriteriaButton.setEnabled(false);
					
					// Disable all the error board text-boxes
					
				}
			}
		});
		CriteriaConfirmButton.setBounds(236, 77, 75, 25);
		toolkit.adapt(CriteriaConfirmButton, true, true);
		CriteriaConfirmButton.setText("Confirm");
		
		CapacityCompo = new Composite(SearchCriteriaCompo, SWT.NONE);
		CapacityCompo.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		CapacityCompo.setBounds(349, 10, 321, 79);
		toolkit.adapt(CapacityCompo);
		toolkit.paintBordersFor(CapacityCompo);
		
		Label lblEstimatedCapacity = new Label(CapacityCompo, SWT.NONE);
		lblEstimatedCapacity.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		LowerBoundCapacity = new Text(CapacityCompo, SWT.BORDER);
		LowerBoundCapacity.setEnabled(false);
		LowerBoundCapacity.setBounds(120, 7, 76, 21);
		toolkit.adapt(LowerBoundCapacity, true, true);
		
		UpperBoundCapacity = new Text(CapacityCompo, SWT.BORDER);
		UpperBoundCapacity.setEnabled(false);
		UpperBoundCapacity.setBounds(227, 7, 76, 21);
		toolkit.adapt(UpperBoundCapacity, true, true);
		
		Label lblNewLabel = new Label(CapacityCompo, SWT.NONE);
		lblNewLabel.setBounds(206, 10, 15, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("to");
		
		CapacityConfirmButton = new Button(CapacityCompo, SWT.NONE);
		CapacityConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(CapacityConfirmButton.getText().equals("Confirm") == true)
				{
					try
					{	
						String lowerCapacity = LowerBoundCapacity.getText();
						if(lowerCapacity == null || lowerCapacity.equals("") == true)
						{
							throw new Exception("You have not entered the lower bound of the capacity!");
						}
						String upperCapacity = UpperBoundCapacity.getText();
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
						// Everything is OK by now. Disable all the text-boxes and change command
						// type to "Modify"
						UpperBoundCapacity.setEnabled(false);
						LowerBoundCapacity.setEnabled(false);
						CapacityConfirmButton.setText("Edit");
						
						// Try turning the button "Search All Suitable Venues" on
						turnOnFindCriteriaButton();
					}
					catch(NumberFormatException exception)
					{
						errormessageDialog errorBoard = new errormessageDialog(new Shell(), 
								"The bounds must be integers!");
						errorBoard.open();
					}
					catch(Exception exception)
					{
						errormessageDialog errorBoard = new errormessageDialog(new Shell(), 
								exception.getMessage());
						errorBoard.open();
					}
				}
				else
				{
					// It is the case when the command type is "Edit"
					LowerBoundCapacity.setEnabled(true);
					UpperBoundCapacity.setEnabled(true);
					
					// Turn off the button "Find All Suitable Venues"
					FindCriteriaButton.setEnabled(false);
					
					CapacityConfirmButton.setText("Confirm");
				}
			}
		});
		CapacityConfirmButton.setEnabled(false);
		CapacityConfirmButton.setBounds(236, 44, 75, 25);
		toolkit.adapt(CapacityConfirmButton, true, true);
		CapacityConfirmButton.setText("Confirm");
		
		Composite CostCompo = new Composite(SearchCriteriaCompo, SWT.NONE);
		CostCompo.setLayoutDeferred(true);
		CostCompo.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		CostCompo.setBounds(349, 95, 321, 66);
		toolkit.adapt(CostCompo);
		toolkit.paintBordersFor(CostCompo);
		
		Label lblPreferredCost = new Label(CostCompo, SWT.NONE);
		lblPreferredCost.setText("Preferred cost:");
		lblPreferredCost.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredCost, true, true);
		
		LowerBoundCost = new Text(CostCompo, SWT.BORDER);
		LowerBoundCost.setEnabled(false);
		LowerBoundCost.setBounds(120, 7, 76, 21);
		toolkit.adapt(LowerBoundCost, true, true);
		
		UpperBoundCost = new Text(CostCompo, SWT.BORDER);
		UpperBoundCost.setEnabled(false);
		UpperBoundCost.setBounds(227, 7, 76, 21);
		toolkit.adapt(UpperBoundCost, true, true);
		
		Label label_5 = new Label(CostCompo, SWT.NONE);
		label_5.setText("to");
		label_5.setBounds(206, 10, 15, 15);
		toolkit.adapt(label_5, true, true);
		
		CostConfirmButton = new Button(CostCompo, SWT.NONE);
		CostConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(CostConfirmButton.getText().equals("Confirm") == true)
				{
					try
					{	
						String lowerCost = LowerBoundCost.getText();
						if(lowerCost == null || lowerCost.equals("") == true)
						{
							throw new Exception("You have not entered the lower bound of the cost!");
						}
						String upperCost = UpperBoundCost.getText();
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
						// Everything is OK by now. Disable all the text-boxes and change command
						// type to "Edit"
						UpperBoundCost.setEnabled(false);
						LowerBoundCost.setEnabled(false);
						CostConfirmButton.setText("Edit");
						
						// Try turning the button "Search All Suitable Venues" on
						turnOnFindCriteriaButton();
					}
					catch(NumberFormatException exception)
					{
						errormessageDialog errorBoard = new errormessageDialog(new Shell(),
								"Cost values must be real numbers!");
						errorBoard.open();
					}
					catch(Exception exception)
					{
						errormessageDialog errorBoard = new errormessageDialog(new Shell(), 
								exception.getMessage());
						errorBoard.open();
					}
				}
				else
				{
					// It is the case when the command type is "Edit"
					LowerBoundCost.setEnabled(true);
					UpperBoundCost.setEnabled(true);
					
					// Turn off the button "Find All Suitable Venues"
					FindCriteriaButton.setEnabled(false);
					
					CostConfirmButton.setText("Confirm");
				}
			}
		});
		CostConfirmButton.setEnabled(false);
		CostConfirmButton.setBounds(236, 41, 75, 25);
		toolkit.adapt(CostConfirmButton, true, true);
		CostConfirmButton.setText("Confirm");
		
		FindCriteriaButton = new Button(SearchCriteriaCompo, SWT.NONE);
		FindCriteriaButton.setBounds(521, 189, 138, 25);
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
		
		Composite TimeSlotCompo = new Composite(SearchCriteriaCompo, SWT.NONE);
		TimeSlotCompo.setBackgroundImage(SearchCriteriaCompo.getBackgroundImage());
		TimeSlotCompo.setBackgroundMode(SWT.INHERIT_FORCE);
		TimeSlotCompo.setBounds(10, 128, 321, 152);
		toolkit.adapt(TimeSlotCompo);
		toolkit.paintBordersFor(TimeSlotCompo);
		
		TimeConfirmButton = new Button(TimeSlotCompo, SWT.NONE);
		TimeConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(TimeConfirmButton.getText().equals("Confirm") == true)
				{
					try
					{	
						timeSlotChoiceInput = dt_SearchCriteria.readInputFields();
						// Change command type to 'Edit'
						TimeConfirmButton.setText("Edit");
						dt_SearchCriteria.setEnabled(false);
						
						// Try turning the button "Search All Suitable Venues" on
						turnOnFindCriteriaButton();
					}
					catch(Exception exception)
					{
						errormessageDialog errorBoard = new errormessageDialog(new Shell(),
								exception.getMessage());
						errorBoard.open();
					}
				}
				else
				{
					// The case when the command type is "Edit"
					dt_SearchCriteria.setEnabled(true);
					
					// Turn off the button "Find All Suitable Venues"
					FindCriteriaButton.setEnabled(false);
					
					TimeConfirmButton.setText("Confirm");
				}
			}
		});
		TimeConfirmButton.setEnabled(false);
		TimeConfirmButton.setBounds(236, 117, 75, 25);
		toolkit.adapt(TimeConfirmButton, true, true);
		TimeConfirmButton.setText("Confirm");
		
		dt_SearchCriteria = new InputDateTimeComposite(TimeSlotCompo, SWT.NONE);
		dt_SearchCriteria.setBackgroundMode(SWT.INHERIT_FORCE);
		dt_SearchCriteria.setBounds(0, 0, 311, 127);
		toolkit.adapt(dt_SearchCriteria);
		toolkit.paintBordersFor(dt_SearchCriteria);
		
		AddOutsideVenue = new Composite(FunctionContentPage, SWT.NONE);
		AddOutsideVenue.setBounds(0, 70, 330, 238);
		toolkit.adapt(AddOutsideVenue);
		toolkit.paintBordersFor(AddOutsideVenue);
		
		Label lblAddAnEvent = new Label(AddOutsideVenue, SWT.NONE);
		lblAddAnEvent.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblAddAnEvent.setBounds(10, 10, 203, 20);
		toolkit.adapt(lblAddAnEvent, true, true);
		lblAddAnEvent.setText("Add an event outside NUS:");
		
		Label lblEnterAName = new Label(AddOutsideVenue, SWT.NONE);
		lblEnterAName.setBounds(20, 36, 99, 15);
		toolkit.adapt(lblEnterAName, true, true);
		lblEnterAName.setText("Enter venue name: ");
		
		OutsideNameField = new Text(AddOutsideVenue, SWT.BORDER);
		OutsideNameField.setBounds(125, 33, 172, 21);
		toolkit.adapt(OutsideNameField, true, true);
		
		Label lblNewLabel_1 = new Label(AddOutsideVenue, SWT.NONE);
		lblNewLabel_1.setBounds(20, 71, 99, 15);
		toolkit.adapt(lblNewLabel_1, true, true);
		lblNewLabel_1.setText("Enter address:");
		
		AddressField = new Text(AddOutsideVenue, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		AddressField.setBounds(125, 68, 172, 21);
		toolkit.adapt(AddressField, true, true);
		
		CapacityField = new Text(AddOutsideVenue, SWT.BORDER);
		CapacityField.setBounds(125, 147, 76, 21);
		toolkit.adapt(CapacityField, true, true);
		
		Label lblNewLabel_2 = new Label(AddOutsideVenue, SWT.NONE);
		lblNewLabel_2.setBounds(20, 150, 84, 15);
		toolkit.adapt(lblNewLabel_2, true, true);
		lblNewLabel_2.setText("Enter capacity:");
		
		CostField = new Text(AddOutsideVenue, SWT.BORDER);
		CostField.setBounds(125, 183, 76, 21);
		toolkit.adapt(CostField, true, true);
		
		Label label_3 = new Label(AddOutsideVenue, SWT.NONE);
		label_3.setText("Enter description:");
		label_3.setBounds(20, 108, 99, 15);
		toolkit.adapt(label_3, true, true);
		
		DescriptionField = new Text(AddOutsideVenue, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		DescriptionField.setBounds(125, 105, 172, 21);
		toolkit.adapt(DescriptionField, true, true);
		
		Label lblEnterCost = new Label(AddOutsideVenue, SWT.NONE);
		lblEnterCost.setText("Enter cost:");
		lblEnterCost.setBounds(20, 186, 84, 15);
		toolkit.adapt(lblEnterCost, true, true);
		
		Label label_6 = new Label(AddOutsideVenue, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		label_6.setText("(*)");
		label_6.setBounds(305, 36, 25, 15);
		toolkit.adapt(label_6, true, true);
		
		OutsideVenueErrorBoard = new Text(AddOutsideVenue, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		OutsideVenueErrorBoard.setEditable(false);
		OutsideVenueErrorBoard.setBounds(20, 399, 277, 69);
		toolkit.adapt(OutsideVenueErrorBoard, true, true);
		OutsideVenueErrorBoard.setVisible(false);
		
		Button OutsideVenueButton = new Button(AddOutsideVenue, SWT.NONE);
		OutsideVenueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Check if the user has entered the name of a venue
				String venueName = OutsideNameField.getText();
				if(venueName == null || venueName.equals("") == true)
				{
					OutsideVenueErrorBoard.setText("You have not entered the name of a venue yet!");
					OutsideVenueErrorBoard.setVisible(true);
					return;
				}
				
				// Read other information and check the validity of inputs
				try
				{
					OutsideVenueErrorBoard.setVisible(false);
					
					// Check hour
					TimeSlot wanted = dt_AddOutside.readInputFields();
					
					String address = AddressField.getText();
					String description = AddressField.getText();
					
					int capacity = Integer.parseInt(CapacityField.getText());
					if(capacity <= 0)
						throw new Exception("Capacity must be a positive integer.");
					
					if(correctMoneyFormat(CostField.getText()) == false)
						throw new Exception("The cost should have at most 2 decimal digits!");
					int cost = (int) (Double.parseDouble(CostField.getText()) * 100);
					if(cost < 0)
						throw new Exception("Cost must be a nonnegative real number.");
					
					Venue outside = new Venue(address, address, description, capacity, cost);
					
					// ADD DUMMY VALUE 0. NEED TO CHANGE WHEN YOU HAVE EVENT ID!
					ControllerBookingSystem.chooseOutsideVenue(0, outside, wanted);
					// DO WE NEED TO INFORM THAT THE ADDING IS SUCCESSFUL?
				}
				catch(NumberFormatException exception)
				{
					OutsideVenueErrorBoard.setText("Hour and Capacity must be integers. Cost must be a real number.");
					OutsideVenueErrorBoard.setVisible(true);
				}
				catch(Exception exception)
				{
					OutsideVenueErrorBoard.setText(exception.getMessage());
					OutsideVenueErrorBoard.setVisible(true);
				}
			}
		});
		OutsideVenueButton.setBounds(214, 224, 99, 25);
		toolkit.adapt(OutsideVenueButton, true, true);
		OutsideVenueButton.setText("Add Venue");

		txtIndicatesA = new Text(AddOutsideVenue, SWT.BORDER);
		txtIndicatesA.setEditable(false);
		txtIndicatesA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtIndicatesA.setText("(*) indicates a compulsory field");
		txtIndicatesA.setBounds(20, 366, 172, 21);
		toolkit.adapt(txtIndicatesA, true, true);
		
		text = new Text(AddOutsideVenue, SWT.BORDER);
		text.setText("(*) indicates a compulsory field");
		text.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		text.setEditable(false);
		text.setBounds(20, 253, 172, 21);
		toolkit.adapt(text, true, true);
		
		dt_AddOutside = new InputDateTimeComposite(AddOutsideVenue, SWT.NONE);
		dt_AddOutside.setBounds(336, 16, 313, 100);
		toolkit.adapt(dt_AddOutside);
		toolkit.paintBordersFor(dt_AddOutside);
		
		Label label = new Label(AddOutsideVenue, SWT.NONE);
		label.setBounds(655, 36, 25, 15);
		label.setText("(*)");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		toolkit.adapt(label, true, true);
		
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
					if(BookVenueButton.getText().equals("Book Venue") &&
							timeSlotChoiceInput.isContained(new TimeSlot(event.getStartDateTime(),
									event.getEndDateTime())) == false)
					{
						errormessageDialog warningBoard = new errormessageDialog(new Shell(), 
								"Warning: Your preferred time slot is not within the event's time slot. Do you still want to book it?"
								+ " If no, please edit your time slot. If yes, please press the button \"Continute to book\".",
								"Message");
						warningBoard.open();
						BookVenueButton.setText("Continue to Book");
						ButtonConfirmBookTime.setText("Confirm");
						dt_SearchResult.setEnabled(true);
						return;
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
		BookVenueButton.setBounds(531, 253, 139, 25);
		toolkit.adapt(BookVenueButton, true, true);
		BookVenueButton.setText("Book Venue");
		
		Label lblVenueDetails = new Label(ResultPageCompo, SWT.NONE);
		lblVenueDetails.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblVenueDetails.setBounds(450, 12, 100, 20);
		toolkit.adapt(lblVenueDetails, true, true);
		lblVenueDetails.setText("Venue Details:");
		
		Label lblSearchResult = new Label(ResultPageCompo, SWT.NONE);
		lblSearchResult.setBounds(10, 10, 94, 20);
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
		DisplayTable.setBounds(10, 36, 411, 146);
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
		ViewCompo.setBounds(439, 26, 241, 226);
		toolkit.adapt(ViewCompo);
		toolkit.paintBordersFor(ViewCompo);
		
		VenueDetailTextbox = new Text(ViewCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		VenueDetailTextbox.setEditable(false);
		VenueDetailTextbox.setBounds(10, 10, 221, 212);
		toolkit.adapt(VenueDetailTextbox, true, true);
		
		dt_SearchResult = new InputDateTimeComposite(ResultPageCompo, SWT.NONE);
		dt_SearchResult.setBounds(20, 182, 313, 100);
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
		ButtonConfirmBookTime.setBounds(339, 252, 75, 25);
		toolkit.adapt(ButtonConfirmBookTime, true, true);
		ButtonConfirmBookTime.setText("Confirm");
	}	
	
	
	/**
	 * This method is to decide if the button "Find All Suitable Venues" can be turned on. If
	 * so, then the method turns it on so that users can request the service. Otherwise, nothing
	 * is done.
	 */
	private void turnOnFindCriteriaButton()
	{
		boolean isCapacityConfirmed = !CapacityConfirmButton.getText().equals("Confirm");
		boolean isCostConfirmed = !CostConfirmButton.getText().equals("Confirm");
		boolean isTimeSlotConfirmed = !TimeConfirmButton.getText().equals("Confirm");
		
		if(flagCapacityChoice == isCapacityConfirmed && flagCostChoice == isCostConfirmed &&
				flagTimeSlotChoice == isTimeSlotConfirmed)
		{
			FindCriteriaButton.setEnabled(true);
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
			dt_SearchResult.reset();
			dt_SearchResult.setEnabled(true);
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
	/*
	public static void main(String[] args){
	
		Display display = new Display();

		Shell shell = new Shell();
		
		shell.setLayout(new FillLayout());
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setText("Booking System");
		shell.setSize(629, 606);
		shell.setLayout(new FillLayout());
		shell.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg"));
		
		ViewBookingSystem calc = new ViewBookingSystem(shell, SWT.NONE, null);
		calc.pack();
		

		shell.pack();
		shell.open();

		while(!shell.isDisposed()){

			if(!display.readAndDispatch()) display.sleep();
		} 

	}*/ 
}
