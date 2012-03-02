import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class BookingSystemGUI extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text LowBoundCapacity;
	private Text UpBoundCapacity;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;

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
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setEnabled(false);
		composite.setBounds(0, 333, 331, 43);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		Label lblEstimatedCapacity = new Label(composite, SWT.NONE);
		lblEstimatedCapacity.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblEstimatedCapacity, true, true);
		lblEstimatedCapacity.setText("Estimated capacity:");
		
		LowBoundCapacity = new Text(composite, SWT.BORDER);
		LowBoundCapacity.setBounds(120, 7, 76, 21);
		toolkit.adapt(LowBoundCapacity, true, true);
		
		UpBoundCapacity = new Text(composite, SWT.BORDER);
		UpBoundCapacity.setBounds(227, 7, 76, 21);
		toolkit.adapt(UpBoundCapacity, true, true);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(206, 10, 15, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("to");
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setBounds(0, 382, 331, 95);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		
		Label lblPreferredTimeSlot = new Label(composite_1, SWT.NONE);
		lblPreferredTimeSlot.setBounds(10, 10, 104, 15);
		toolkit.adapt(lblPreferredTimeSlot, true, true);
		lblPreferredTimeSlot.setText("Preferred Time Slot:");
		
		DateTime dateTime = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTime.setBounds(112, 30, 85, 24);
		toolkit.adapt(dateTime);
		toolkit.paintBordersFor(dateTime);
		
		Label lblFrom = new Label(composite_1, SWT.NONE);
		lblFrom.setBounds(65, 31, 41, 15);
		toolkit.adapt(lblFrom, true, true);
		lblFrom.setText("From:");
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(224, 30, 67, 21);
		toolkit.adapt(text, true, true);
		
		Label lblAt = new Label(composite_1, SWT.NONE);
		lblAt.setText("at");
		lblAt.setBounds(203, 30, 15, 15);
		toolkit.adapt(lblAt, true, true);
		
		Label lblTo = new Label(composite_1, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(65, 60, 41, 15);
		toolkit.adapt(lblTo, true, true);
		
		DateTime dateTime_1 = new DateTime(composite_1, SWT.DROP_DOWN);
		dateTime_1.setBounds(112, 59, 85, 24);
		toolkit.adapt(dateTime_1);
		toolkit.paintBordersFor(dateTime_1);
		
		Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setText("at");
		label_4.setBounds(203, 59, 15, 15);
		toolkit.adapt(label_4, true, true);
		
		text_2 = new Text(composite_1, SWT.BORDER);
		text_2.setBounds(224, 59, 67, 21);
		toolkit.adapt(text_2, true, true);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(193, 533, 138, 25);
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Find All Suitable Venues");
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(410, 0, -17, 192);
		toolkit.adapt(label, true, true);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(433, 0, -8, 440);
		toolkit.adapt(label_1, true, true);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_2.setBounds(449, 0, -6, 153);
		toolkit.adapt(label_2, true, true);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setBounds(379, 0, 350, 526);
		toolkit.adapt(sashForm);
		toolkit.paintBordersFor(sashForm);
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setBounds(0, 0, 331, 113);
		toolkit.adapt(composite_2);
		toolkit.paintBordersFor(composite_2);
		
		Label lblChooseFunctionality = new Label(composite_2, SWT.NONE);
		lblChooseFunctionality.setBounds(10, 10, 124, 15);
		toolkit.adapt(lblChooseFunctionality, true, true);
		lblChooseFunctionality.setText("Choose functionality:");
		
		Button btnRadioButton = new Button(composite_2, SWT.RADIO);
		btnRadioButton.setBounds(30, 31, 269, 15);
		toolkit.adapt(btnRadioButton, true, true);
		btnRadioButton.setText("Search venue by name and view information");
		
		Button btnRadioButton_1 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_1.setBounds(30, 60, 221, 16);
		toolkit.adapt(btnRadioButton_1, true, true);
		btnRadioButton_1.setText("Search venues with specified criteria");
		
		Button btnRadioButton_2 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_2.setBounds(30, 87, 90, 16);
		toolkit.adapt(btnRadioButton_2, true, true);
		btnRadioButton_2.setText("Radio Button");
		
		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setBounds(0, 119, 331, 113);
		toolkit.adapt(composite_3);
		toolkit.paintBordersFor(composite_3);
		
		Label lblSearchVenueBy = new Label(composite_3, SWT.NONE);
		lblSearchVenueBy.setBounds(10, 10, 238, 15);
		toolkit.adapt(lblSearchVenueBy, true, true);
		lblSearchVenueBy.setText("Search venue by name: ");
		
		Label lblEnterTheName = new Label(composite_3, SWT.NONE);
		lblEnterTheName.setBounds(20, 31, 160, 15);
		toolkit.adapt(lblEnterTheName, true, true);
		lblEnterTheName.setText("Enter the name of the venue:");
		
		text_1 = new Text(composite_3, SWT.BORDER);
		text_1.setBounds(49, 52, 243, 21);
		toolkit.adapt(text_1, true, true);
		
		Button btnSearchAndView = new Button(composite_3, SWT.NONE);
		btnSearchAndView.setBounds(154, 79, 177, 25);
		toolkit.adapt(btnSearchAndView, true, true);
		btnSearchAndView.setText("Search and view information");
		
		Composite composite_4 = new Composite(this, SWT.NONE);
		composite_4.setBounds(0, 248, 331, 79);
		toolkit.adapt(composite_4);
		toolkit.paintBordersFor(composite_4);
		
		Label lblSearchVenuesWith = new Label(composite_4, SWT.NONE);
		lblSearchVenuesWith.setBounds(10, 10, 203, 15);
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
		
		Composite composite_5 = new Composite(this, SWT.NONE);
		composite_5.setEnabled(false);
		composite_5.setBounds(0, 483, 331, 43);
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
		

	}
}
