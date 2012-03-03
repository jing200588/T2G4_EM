import java.util.Vector;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.swt.widgets.Spinner;
import java.lang.NumberFormatException;

public class BookingSystemGUI extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private final StackLayout stackLayout = new StackLayout();
	
	private Text LowBoundCapacity;
	private Text UpBoundCapacity;
	private Text text;
	private Text NameToSearch;
	private Text text_2;
	private Text text_3;
	private Text text_4;
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

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BookingSystemGUI(Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(410, 0, -17, 192);
		toolkit.adapt(label, true, true);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(433, 0, -8, 440);
		toolkit.adapt(label_1, true, true);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_2.setBounds(449, 0, -6, 153);
		toolkit.adapt(label_2, true, true);
		
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setBounds(0, 0, 331, 113);
		toolkit.adapt(composite_2);
		toolkit.paintBordersFor(composite_2);
		
		Label lblChooseFunctionality = new Label(composite_2, SWT.NONE);
		lblChooseFunctionality.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblChooseFunctionality.setBounds(10, 10, 148, 20);
		toolkit.adapt(lblChooseFunctionality, true, true);
		lblChooseFunctionality.setText("Choose functionality:");
		
		Button SearchNameButton = new Button(composite_2, SWT.RADIO);
		SearchNameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = SearchNameCompo;
				FunctionContentPage.layout();
			}
		});
		SearchNameButton.setBounds(30, 31, 269, 15);
		toolkit.adapt(SearchNameButton, true, true);
		SearchNameButton.setText("Search venue by name and view information");
		
		Button SearchCriteriaButton = new Button(composite_2, SWT.RADIO);
		SearchCriteriaButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = SearchCriteriaCompo;
				FunctionContentPage.layout();
			}
		});
		SearchCriteriaButton.setBounds(30, 60, 221, 16);
		toolkit.adapt(SearchCriteriaButton, true, true);
		SearchCriteriaButton.setText("Search venues with specified criteria");
		
		Button AddOutsideVenueButton = new Button(composite_2, SWT.RADIO);
		AddOutsideVenueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = AddOutsideVenue;
				FunctionContentPage.layout();
			}
		});
		AddOutsideVenueButton.setBounds(30, 87, 169, 16);
		toolkit.adapt(AddOutsideVenueButton, true, true);
		AddOutsideVenueButton.setText("Add an event outside NUS");
		
		FunctionContentPage = new Composite(this, SWT.NONE);
		FunctionContentPage.setBounds(0, 119, 331, 468);
		toolkit.adapt(FunctionContentPage);
		toolkit.paintBordersFor(FunctionContentPage);
		FunctionContentPage.setLayout(stackLayout);
		
		SearchNameCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchNameCompo.setBounds(0, 10, 331, 113);
		toolkit.adapt(SearchNameCompo);
		toolkit.paintBordersFor(SearchNameCompo);
		
		Label lblSearchVenueBy = new Label(SearchNameCompo, SWT.NONE);
		lblSearchVenueBy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblSearchVenueBy.setBounds(10, 10, 238, 21);
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		Label lblEnterTheName = new Label(SearchNameCompo, SWT.NONE);
		lblEnterTheName.setBounds(20, 31, 160, 15);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");
		
		SearchNameErrorBoard = new Text(SearchNameCompo, SWT.BORDER);
		SearchNameErrorBoard.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		SearchNameErrorBoard.setEditable(false);
		SearchNameErrorBoard.setBounds(20, 110, 272, 80);
		toolkit.adapt(SearchNameErrorBoard, true, true);
		SearchNameErrorBoard.setVisible(false);
		
		NameToSearch = new Text(SearchNameCompo, SWT.BORDER);
		NameToSearch.setBounds(49, 52, 243, 21);
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
					Venue searchResult = BookingSystem.findVenueByName(venueName);
					Vector<Venue> listVenue = new Vector<Venue>();
					listVenue.add(searchResult);
					// For testing: listVenue.add(new Venue("A", "B", "C", 10, 100));
					tableViewer.setInput(listVenue);
				}
			}
		});
		ButtonSearchName.setBounds(154, 79, 177, 25);
		toolkit.adapt(ButtonSearchName, true, true);
		ButtonSearchName.setText("Search and view information");
		
		
		
		SearchCriteriaCompo = new Composite(FunctionContentPage, SWT.NONE);
		SearchCriteriaCompo.setBounds(0, 129, 331, 318);
		toolkit.adapt(SearchCriteriaCompo);
		toolkit.paintBordersFor(SearchCriteriaCompo);
		
		Composite composite_4 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_4.setBounds(10, 10, 321, 79);
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
		
		Button btnCapacity = new Button(composite_4, SWT.CHECK);
		btnCapacity.setBounds(30, 52, 93, 16);
		toolkit.adapt(btnCapacity, true, true);
		btnCapacity.setText("Capacity");
		
		Button btnTime = new Button(composite_4, SWT.CHECK);
		btnTime.setBounds(129, 52, 66, 16);
		toolkit.adapt(btnTime, true, true);
		btnTime.setText("Time");
		
		Button btnCost = new Button(composite_4, SWT.CHECK);
		btnCost.setBounds(207, 52, 93, 16);
		toolkit.adapt(btnCost, true, true);
		btnCost.setText("Cost");
		
		Composite Capacity = new Composite(SearchCriteriaCompo, SWT.NONE);
		Capacity.setBounds(10, 95, 321, 43);
		toolkit.adapt(Capacity);
		toolkit.paintBordersFor(Capacity);
		
		Label lblEstimatedCapacity = new Label(Capacity, SWT.NONE);
		lblEstimatedCapacity.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		LowBoundCapacity = new Text(Capacity, SWT.BORDER);
		LowBoundCapacity.setBounds(120, 7, 76, 21);
		toolkit.adapt(LowBoundCapacity, true, true);
		
		UpBoundCapacity = new Text(Capacity, SWT.BORDER);
		UpBoundCapacity.setBounds(227, 7, 76, 21);
		toolkit.adapt(UpBoundCapacity, true, true);
		
		Label lblNewLabel = new Label(Capacity, SWT.NONE);
		lblNewLabel.setBounds(206, 10, 15, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("to");
		
		Composite composite_1 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_1.setBounds(10, 198, 321, 116);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		
		Label lblPreferredTimeSlot = new Label(composite_1, SWT.NONE);
		lblPreferredTimeSlot.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredTimeSlot, true, true);
		lblPreferredTimeSlot.setText("Preferred Time Slot:");
		
		DateTime dateTime = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTime.setBounds(89, 53, 85, 24);
		toolkit.adapt(dateTime);
		toolkit.paintBordersFor(dateTime);
		
		Label lblFrom = new Label(composite_1, SWT.NONE);
		lblFrom.setBounds(42, 54, 41, 15);
		toolkit.adapt(lblFrom, true, true);
		lblFrom.setText("From:");
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(201, 53, 67, 21);
		toolkit.adapt(text, true, true);
		
		Label lblAt = new Label(composite_1, SWT.NONE);
		lblAt.setText("at");
		lblAt.setBounds(180, 53, 15, 15);
		toolkit.adapt(lblAt, true, true);
		
		Label lblTo = new Label(composite_1, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(42, 83, 41, 15);
		toolkit.adapt(lblTo, true, true);
		
		DateTime dateTime_1 = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTime_1.setBounds(89, 82, 85, 24);
		toolkit.adapt(dateTime_1);
		toolkit.paintBordersFor(dateTime_1);
		
		Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setText("at");
		label_4.setBounds(180, 82, 15, 15);
		toolkit.adapt(label_4, true, true);
		
		text_2 = new Text(composite_1, SWT.BORDER);
		text_2.setBounds(201, 82, 67, 21);
		toolkit.adapt(text_2, true, true);
		
		Label lblhourShouldBe = new Label(composite_1, SWT.NONE);
		lblhourShouldBe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		lblhourShouldBe.setText("(Hour should be from 0 to 23)");
		lblhourShouldBe.setBounds(10, 32, 161, 15);
		toolkit.adapt(lblhourShouldBe, true, true);
		
		Composite composite_5 = new Composite(SearchCriteriaCompo, SWT.NONE);
		composite_5.setBounds(10, 331, 321, 43);
		toolkit.adapt(composite_5);
		toolkit.paintBordersFor(composite_5);
		
		Label lblPreferredCost = new Label(composite_5, SWT.NONE);
		lblPreferredCost.setText("Preferred cost:");
		lblPreferredCost.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredCost, true, true);
		
		text_3 = new Text(composite_5, SWT.BORDER);
		text_3.setBounds(120, 7, 76, 21);
		toolkit.adapt(text_3, true, true);
		
		text_4 = new Text(composite_5, SWT.BORDER);
		text_4.setBounds(227, 7, 76, 21);
		toolkit.adapt(text_4, true, true);
		
		Label label_5 = new Label(composite_5, SWT.NONE);
		label_5.setText("to");
		label_5.setBounds(206, 10, 15, 15);
		toolkit.adapt(label_5, true, true);
		
		Button btnNewButton = new Button(SearchCriteriaCompo, SWT.NONE);
		btnNewButton.setBounds(193, 380, 138, 25);
		btnNewButton.setEnabled(true);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Find All Suitable Venues");
		
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
		composite.setBounds(10, 210, 321, 116);
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
					
					double cost = Double.parseDouble(CostField.getText());
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
		OutsideVenueButton.setBounds(198, 332, 99, 25);
		toolkit.adapt(OutsideVenueButton, true, true);
		OutsideVenueButton.setText("Add Venue");
		
		txtIndicatesA = new Text(AddOutsideVenue, SWT.BORDER);
		txtIndicatesA.setEditable(false);
		txtIndicatesA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtIndicatesA.setText("(*) indicates a compulsory field");
		txtIndicatesA.setBounds(20, 366, 172, 21);
		toolkit.adapt(txtIndicatesA, true, true);
		
		
		
		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setBounds(363, 0, 421, 587);
		toolkit.adapt(composite_3);
		toolkit.paintBordersFor(composite_3);
		
		tableViewer = new TableViewer(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		DisplayTable = tableViewer.getTable();
		DisplayTable.setBounds(10, 36, 411, 293);
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
				return ((Double) venue.getCost()).toString();
			}
		});
		
		Label lblSearchResult = new Label(composite_3, SWT.NONE);
		lblSearchResult.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblSearchResult.setBounds(10, 10, 94, 20);
		toolkit.adapt(lblSearchResult, true, true);
		lblSearchResult.setText("Search result:");

	}
	
	public static void main(String[] args){
		Display display = new Display();

		Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE);
		shell.setText("Budget Calc");
		BookingSystemGUI calc = new BookingSystemGUI(shell, SWT.NONE);
		calc.pack();

		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}

	} 
}
