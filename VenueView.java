import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import java.util.Vector;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.swt.widgets.Spinner;
import java.lang.NumberFormatException;


public class VenueView extends Composite {
	private final StackLayout stackLayout = new StackLayout();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private final StackLayout resultStackLayout = new StackLayout();
	
	private Text LowerBoundCapacity;
	private Text UpperBoundCapacity;
	private Text HourFromChoice;
	private Text NameToSearch;
	private Text HourToChoice;
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
	private Text SearchNameErrorBoard;
	private TableViewer tableViewer;
	private Text OutsideVenueErrorBoard;
	private Text HourFromOutside;
	private Text HourToOutside;
	private DateTime dateTimeFromOutside;
	private DateTime dateTimeToOutside;
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
	private DateTime dateTimeFrom;
	private DateTime dateTimeTo;
	private Button FindCriteriaButton;
	private Text CapacityChoiceErrorBoard;
	private int[] capacityChoiceInput;
	private int[] costChoiceInput;
	private TimeSlot timeSlotChoiceInput;
	private Composite FunctionOptionCompo;
	private Composite ResultColumnCompo;
	private Text TimeSlotChoiceErrorBoard;
	private Text CostChoiceErrorBoard;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public VenueView(Composite parent, int style, int id) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Form VenueViewForm = toolkit.createForm(this);
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Book Venue");
		VenueViewForm.getBody().setLayout(new FormLayout());

		Composite mainComposite = new Composite(VenueViewForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.top = new FormAttachment(50, -160);
		fd_mainComposite.bottom = new FormAttachment (50, 180);
		fd_mainComposite.left = new FormAttachment(50, -350);
		fd_mainComposite.right = new FormAttachment(50, 350);
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
		
		Button SearchNameButton = new Button(FunctionOptionCompo, SWT.RADIO);
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
		
		Button SearchCriteriaButton = new Button(FunctionOptionCompo, SWT.RADIO);
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
		
		SearchNameErrorBoard = new Text(SearchNameCompo, SWT.BORDER);
		SearchNameErrorBoard.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		SearchNameErrorBoard.setEditable(false);
		SearchNameErrorBoard.setBounds(20, 99, 409, 80);
		toolkit.adapt(SearchNameErrorBoard, true, true);
		SearchNameErrorBoard.setVisible(false);
		
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
					SearchNameErrorBoard.setText("You have not entered a venue name to search yet!");
					SearchNameErrorBoard.setVisible(true);
				}
				else
				{
					SearchNameErrorBoard.setVisible(false);
//					Venue searchResult = BookingSystem.findVenueByName(venueName);
					Vector<Venue> listVenue = new Vector<Venue>();
//					listVenue.add(searchResult);
					
					// For testing: 
					listVenue.add(new Venue("A", "B", "C", 10, 100));
					tableViewer.setInput(listVenue);
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
		
		CapacityChoiceButton.setBounds(30, 52, 93, 16);
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
		TimeChoiceButton.setBounds(129, 52, 66, 16);
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
					
					if(flagTimeSlotChoice == true)
					{
						dateTimeFrom.setEnabled(true);
						dateTimeTo.setEnabled(true);
						HourFromChoice.setEnabled(true);
						HourToChoice.setEnabled(true);
						TimeConfirmButton.setEnabled(true);
					}
					else
					{
						HourFromChoice.setText("");
						HourToChoice.setText("");
					}
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
					dateTimeFrom.setEnabled(false);
					dateTimeTo.setEnabled(false);
					HourFromChoice.setEnabled(false);
					HourToChoice.setEnabled(false);
					TimeConfirmButton.setEnabled(false);
					FindCriteriaButton.setEnabled(false);
					
					// Disable all the error board text-boxes
					
				}
			}
		});
		CriteriaConfirmButton.setBounds(236, 77, 75, 25);
		toolkit.adapt(CriteriaConfirmButton, true, true);
		CriteriaConfirmButton.setText("Confirm");
		
		Composite CapacityCompo = new Composite(SearchCriteriaCompo, SWT.NONE);
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
						// Make the error board invisible at the beginning
						CapacityChoiceErrorBoard.setVisible(false);
						
						String lowerCapacity = LowerBoundCapacity.getText();
						if(lowerCapacity == null || lowerCapacity.equals("") == true)
						{
							throw new Exception("Lower bound of the capacity missing!");
						}
						else
						{
							CapacityChoiceErrorBoard.setVisible(false);
						}
						String upperCapacity = UpperBoundCapacity.getText();
						if(upperCapacity == null || upperCapacity.equals("") == true)
						{
							throw new Exception("Upper bound of the capacity missing!");
						}
						else
						{
							CapacityChoiceErrorBoard.setVisible(false);
						}
						int lower = Integer.parseInt(lowerCapacity);
						int upper = Integer.parseInt(upperCapacity);
						if(lower > upper)
						{
							throw new Exception("Lowerbound larger than upperbound!");
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
						CapacityChoiceErrorBoard.setText("The bounds must be integers!");
						CapacityChoiceErrorBoard.setVisible(true);
					}
					catch(Exception exception)
					{
						CapacityChoiceErrorBoard.setText(exception.getMessage());
						CapacityChoiceErrorBoard.setVisible(true);
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
		
		CapacityChoiceErrorBoard = new Text(CapacityCompo, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		CapacityChoiceErrorBoard.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		CapacityChoiceErrorBoard.setEditable(false);
		CapacityChoiceErrorBoard.setBounds(10, 48, 211, 21);
		toolkit.adapt(CapacityChoiceErrorBoard, true, true);
		CapacityChoiceErrorBoard.setVisible(false);
		
		Composite composite_5 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_5.setBounds(349, 95, 321, 66);
		toolkit.adapt(composite_5);
		toolkit.paintBordersFor(composite_5);
		
		Label lblPreferredCost = new Label(composite_5, SWT.NONE);
		lblPreferredCost.setText("Preferred cost:");
		lblPreferredCost.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredCost, true, true);
		
		LowerBoundCost = new Text(composite_5, SWT.BORDER);
		LowerBoundCost.setBounds(120, 7, 76, 21);
		toolkit.adapt(LowerBoundCost, true, true);
		
		UpperBoundCost = new Text(composite_5, SWT.BORDER);
		UpperBoundCost.setBounds(227, 7, 76, 21);
		toolkit.adapt(UpperBoundCost, true, true);
		
		Label label_5 = new Label(composite_5, SWT.NONE);
		label_5.setText("to");
		label_5.setBounds(206, 10, 15, 15);
		toolkit.adapt(label_5, true, true);
		
		CostConfirmButton = new Button(composite_5, SWT.NONE);
		CostConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(CostConfirmButton.getText().equals("Confirm") == true)
				{
					try
					{
						// Make the error board invisible at the beginning
						CostChoiceErrorBoard.setVisible(false);
						
						String lowerCost = LowerBoundCost.getText();
						if(lowerCost == null || lowerCost.equals("") == true)
						{
							throw new Exception("Lower bound of the cost missing!");
						}
						String upperCost = UpperBoundCost.getText();
						if(upperCost == null || upperCost.equals("") == true)
						{
							throw new Exception("Upper bound of the cost missing!");
						}
						if(correctMoneyFormat(upperCost) == false || correctMoneyFormat(lowerCost) == false)
						{
							throw new Exception("Cost must have at most 2 decimal digits!");
						}
						int lower = (int) (Double.parseDouble(lowerCost) * 100);
						int upper = (int) (Double.parseDouble(upperCost) * 100);
						if(lower > upper)
						{
							throw new Exception("Lowerbound larger than upperbound!");
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
						CostChoiceErrorBoard.setText("Cost must be real numbers!");
						CostChoiceErrorBoard.setVisible(true);
					}
					catch(Exception exception)
					{
						CostChoiceErrorBoard.setText(exception.getMessage());
						CostChoiceErrorBoard.setVisible(true);
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
		
		CostChoiceErrorBoard = new Text(composite_5, SWT.BORDER);
		CostChoiceErrorBoard.setEnabled(false);
		CostChoiceErrorBoard.setEditable(false);
		CostChoiceErrorBoard.setVisible(false);
		CostChoiceErrorBoard.setBounds(10, 45, 211, 21);
		toolkit.adapt(CostChoiceErrorBoard, true, true);
		
		FindCriteriaButton = new Button(SearchCriteriaCompo, SWT.NONE);
		FindCriteriaButton.setBounds(532, 191, 138, 25);
		FindCriteriaButton.setEnabled(false);
		FindCriteriaButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		toolkit.adapt(FindCriteriaButton, true, true);
		FindCriteriaButton.setText("Find All Suitable Venues");
		
		Composite composite_1 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_1.setBounds(10, 128, 321, 152);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		
		Label lblPreferredTimeSlot = new Label(composite_1, SWT.NONE);
		lblPreferredTimeSlot.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredTimeSlot, true, true);
		lblPreferredTimeSlot.setText("Preferred Time Slot:");
		
		dateTimeFrom = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTimeFrom.setEnabled(false);
		dateTimeFrom.setBounds(89, 53, 85, 24);
		toolkit.adapt(dateTimeFrom);
		toolkit.paintBordersFor(dateTimeFrom);
		
		Label lblFrom = new Label(composite_1, SWT.NONE);
		lblFrom.setBounds(42, 54, 41, 15);
		toolkit.adapt(lblFrom, true, true);
		lblFrom.setText("From:");
		
		HourFromChoice = new Text(composite_1, SWT.BORDER);
		HourFromChoice.setEnabled(false);
		HourFromChoice.setBounds(201, 53, 67, 21);
		toolkit.adapt(HourFromChoice, true, true);
		
		Label lblAt = new Label(composite_1, SWT.NONE);
		lblAt.setText("at");
		lblAt.setBounds(180, 53, 15, 15);
		toolkit.adapt(lblAt, true, true);
		
		Label lblTo = new Label(composite_1, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(42, 83, 41, 15);
		toolkit.adapt(lblTo, true, true);
		
		dateTimeTo = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTimeTo.setEnabled(false);
		dateTimeTo.setBounds(89, 82, 85, 24);
		toolkit.adapt(dateTimeTo);
		toolkit.paintBordersFor(dateTimeTo);
		
		Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setText("at");
		label_4.setBounds(180, 82, 15, 15);
		toolkit.adapt(label_4, true, true);
		
		HourToChoice = new Text(composite_1, SWT.BORDER);
		HourToChoice.setEnabled(false);
		HourToChoice.setBounds(201, 82, 67, 21);
		toolkit.adapt(HourToChoice, true, true);
		
		Label lblhourShouldBe = new Label(composite_1, SWT.NONE);
		lblhourShouldBe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		lblhourShouldBe.setText("(Hour should be from 0 to 23)");
		lblhourShouldBe.setBounds(10, 32, 161, 15);
		toolkit.adapt(lblhourShouldBe, true, true);
		
		TimeConfirmButton = new Button(composite_1, SWT.NONE);
		TimeConfirmButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(TimeConfirmButton.getText().equals("Confirm") == true)
				{
					try
					{
						// Invisible the error board textbox.
						TimeSlotChoiceErrorBoard.setVisible(false);
					
						// Read and check user's inputs
						String hourFromString = HourFromChoice.getText();
						if(hourFromString == null || hourFromString.equals("") == true)
						{
							throw new Exception("Starting hour is missing!");
						}
						String hourToString = HourToChoice.getText();
						if(hourToString == null || hourToString.equals("") == true)
						{
							throw new Exception("Ending hour is missing!");
						}
						int hourFrom = Integer.parseInt(hourFromString);
						int hourTo = Integer.parseInt(hourToString);
						if(hourFrom < 0 || hourFrom > 23 || hourTo < 0 || hourTo > 23)
						{
							throw new Exception("Hour should be from 0 to 23!");
						}
						DateHour dateHourFrom = new DateHour(dateTimeFrom.getYear(),
								dateTimeFrom.getMonth(), dateTimeFrom.getDay(), hourFrom);
						DateHour dateHourTo = new DateHour(dateTimeTo.getYear(),
								dateTimeTo.getMonth(), dateTimeTo.getDay(), hourTo);
						if(dateHourFrom.compareTo(dateHourTo) >= 0)
						{
							throw new Exception("Dates are not in chronological order!");
						}
						timeSlotChoiceInput = new TimeSlot(dateHourFrom, dateHourTo);
						
						// Change command type to 'Edit' and disable all the text boxes
						// and calendar
						TimeConfirmButton.setText("Edit");
						dateTimeFrom.setEnabled(false);
						dateTimeTo.setEnabled(false);
						HourToChoice.setEnabled(false);
						HourFromChoice.setEnabled(false);
						
						// Try turning the button "Search All Suitable Venues" on
						turnOnFindCriteriaButton();
					}
					catch(NumberFormatException exception)
					{
						TimeSlotChoiceErrorBoard.setText("Hour should be an integer!");
						TimeSlotChoiceErrorBoard.setVisible(true);
					}
					catch(Exception exception)
					{
						TimeSlotChoiceErrorBoard.setText(exception.getMessage());
						TimeSlotChoiceErrorBoard.setVisible(true);
					}
				}
				else
				{
					// The case when the command type is "Edit"
					dateTimeFrom.setEnabled(true);
					dateTimeTo.setEnabled(true);
					HourToChoice.setEnabled(true);
					HourFromChoice.setEnabled(true);
					
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
		
		TimeSlotChoiceErrorBoard = new Text(composite_1, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		TimeSlotChoiceErrorBoard.setVisible(false);
		TimeSlotChoiceErrorBoard.setEditable(false);
		TimeSlotChoiceErrorBoard.setBounds(10, 121, 211, 21);
		toolkit.adapt(TimeSlotChoiceErrorBoard, true, true);
		
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
		
		Composite composite = new Composite(AddOutsideVenue, SWT.NONE);
		composite.setBounds(329, 20, 321, 116);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		Label label_7 = new Label(composite, SWT.NONE);
		label_7.setText("Preferred Time Slot:");
		label_7.setBounds(10, 10, 104, 15);
		toolkit.adapt(label_7, true, true);
		
		dateTimeFromOutside = new DateTime(composite, SWT.DROP_DOWN);
		dateTimeFromOutside.setBounds(89, 53, 85, 24);
		toolkit.adapt(dateTimeFromOutside);
		toolkit.paintBordersFor(dateTimeFromOutside);
		
		Label label_8 = new Label(composite, SWT.NONE);
		label_8.setText("From:");
		label_8.setBounds(42, 54, 41, 15);
		toolkit.adapt(label_8, true, true);
		
		HourFromOutside = new Text(composite, SWT.BORDER);
		HourFromOutside.setBounds(201, 53, 67, 21);
		toolkit.adapt(HourFromOutside, true, true);
		
		Label label_9 = new Label(composite, SWT.NONE);
		label_9.setText("at");
		label_9.setBounds(180, 53, 15, 15);
		toolkit.adapt(label_9, true, true);
		
		Label label_10 = new Label(composite, SWT.NONE);
		label_10.setText("To:");
		label_10.setBounds(42, 83, 41, 15);
		toolkit.adapt(label_10, true, true);
		
		dateTimeToOutside = new DateTime(composite, SWT.DROP_DOWN);
		dateTimeToOutside.setBounds(89, 82, 85, 24);
		toolkit.adapt(dateTimeToOutside);
		toolkit.paintBordersFor(dateTimeToOutside);
		
		Label label_11 = new Label(composite, SWT.NONE);
		label_11.setText("at");
		label_11.setBounds(180, 82, 15, 15);
		toolkit.adapt(label_11, true, true);
		
		HourToOutside = new Text(composite, SWT.BORDER);
		HourToOutside.setBounds(201, 82, 67, 21);
		toolkit.adapt(HourToOutside, true, true);
		
		Label label_12 = new Label(composite, SWT.NONE);
		label_12.setText("(Hour should be from 0 to 23)");
		label_12.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		label_12.setBounds(10, 32, 161, 15);
		toolkit.adapt(label_12, true, true);
		
		Label label_13 = new Label(composite, SWT.NONE);
		label_13.setText("(*)");
		label_13.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		label_13.setBounds(296, 10, 25, 15);
		toolkit.adapt(label_13, true, true);
		
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
				
				// Check if the user has entered hours of the time slot.
				String hourFromString = HourFromOutside.getText();
				if(hourFromString == null || hourFromString.equals("") == true)
				{
					OutsideVenueErrorBoard.setText("You have not entered the starting hour yet!");
					OutsideVenueErrorBoard.setVisible(true);
					return;
				}
				String hourToString = HourToOutside.getText();
				if(hourToString == null || hourToString.equals("") == true)
				{
					OutsideVenueErrorBoard.setText("You have not entered the ending hour yet!");
					OutsideVenueErrorBoard.setVisible(true);
					return;
				}
				
				// Read other information and check the validity of inputs
				try
				{
					OutsideVenueErrorBoard.setVisible(false);
					
					// Check hour
					int hourFrom = Integer.parseInt(hourFromString);
					int hourTo = Integer.parseInt(hourToString);
					if(hourFrom < 0 || hourTo < 0 || hourFrom >= 24 || hourTo >= 24)
						throw new Exception("Hour must be an integer from 0 to 23.");
					DateHour startDateHour = new DateHour(dateTimeFromOutside.getDay(),
							dateTimeFromOutside.getMonth() + 1, dateTimeFromOutside.getYear(),
							hourFrom);
					DateHour endDateHour = new DateHour(dateTimeToOutside.getDay(),
							dateTimeToOutside.getMonth() + 1, dateTimeToOutside.getYear(),
							hourTo);
					if(startDateHour.compareTo(endDateHour) >= 0)
						throw new Exception("The starting date time and ending date time are not correct in chronological order!");
					TimeSlot wanted = new TimeSlot(startDateHour, endDateHour);
					
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
					BookingSystem.chooseOutsideVenue(0, outside, wanted);
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
		
		
		
		ResultColumnCompo = new Composite(FunctionContentPage, SWT.NONE);
		ResultColumnCompo.setBounds(0, 70, 330, 238);
		toolkit.adapt(ResultColumnCompo);
		toolkit.paintBordersFor(ResultColumnCompo);
		
		
		
		Composite TableSearchResultCompo = new Composite(ResultColumnCompo, SWT.NONE);
		TableSearchResultCompo.setBounds(10, 10, 414, 580);
		toolkit.adapt(TableSearchResultCompo);
		toolkit.paintBordersFor(TableSearchResultCompo);
		
		ResultColumnCompo.setLayout(resultStackLayout);
		resultStackLayout.topControl = TableSearchResultCompo;
		ResultColumnCompo.layout();
		
		Composite SearchResult = new Composite(TableSearchResultCompo, SWT.NONE);
		SearchResult.setBounds(0, 0, 680, 339);
		toolkit.adapt(SearchResult);
		toolkit.paintBordersFor(SearchResult);
		
		Label lblSearchResult = new Label(SearchResult, SWT.NONE);
		lblSearchResult.setBounds(10, 10, 94, 20);
		lblSearchResult.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search result:");
		
		tableViewer = new TableViewer(SearchResult, SWT.BORDER | SWT.FULL_SELECTION);
		DisplayTable = tableViewer.getTable();
		DisplayTable.setBounds(10, 36, 411, 104);
		toolkit.paintBordersFor(DisplayTable);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnVenueid = tableViewerColumn_3.getColumn();
		tblclmnVenueid.setWidth(100);
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
		tblclmnVenuename.setWidth(100);
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
		tblclmnCapacity.setWidth(100);
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
		tblclmnCost.setWidth(100);
		tblclmnCost.setText("Cost");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Venue venue = (Venue) element;
				return ((Double) (venue.getCost() / 100.0)).toString();
			}
		});
		
		Composite ViewDetails = new Composite(SearchResult, SWT.NONE);
		ViewDetails.setBounds(10, 160, 411, 104);
		toolkit.adapt(ViewDetails);
		toolkit.paintBordersFor(ViewDetails);
		
		Label lblViewVenueDetails = new Label(ViewDetails, SWT.NONE);
		lblViewVenueDetails.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblViewVenueDetails.setBounds(10, 10, 141, 20);
		toolkit.adapt(lblViewVenueDetails, true, true);
		lblViewVenueDetails.setText("View Venue Details:");
	}	
	
	
	/**
	 * This method is to decide if the button "Find All Suitable Venues" can be turned on. If
	 * 	so, then the method turns it on so that users can request the service. Otherwise, nothing
	 *  is done.
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
	 * 
	 * @param money
	 * @return true if money is in the correct format of currency.
	 * @return false otherwise.
	 * 
	 * money is in the correct format of currency if and only if
	 * 	+ It may not have a decimal point.
	 *  + If it has a decimal point, the number of decimal digits must be at least 1
	 *  	and at most 2.
	 * 
	 * Other cases will be checked by the function Double.parseDouble().
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
	
	
	public static void main(String[] args){
		Display display = new Display();
		display = Display.getDefault();
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setText("Booking System");
		shell.setSize(629, 606);
		VenueView calc = new VenueView(shell, SWT.NONE, 0);
		calc.pack();

		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}

	} 
}