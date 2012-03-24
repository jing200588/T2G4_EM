import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnLabelProvider;

public class ViewEventFlow extends Composite {
	
	// Table column names/properties
	public static final String STARTDATETIME = "Start Date Time";
	public static final String ENDDATETIME = "End Date Time";
	public static final String ACTIVITY = "Activity";
	public static final String VENUE = "Venue";
	public static final String NOTE = "Further notes";
	public static final String[] COLUMN_PROPS = {STARTDATETIME, ENDDATETIME, ACTIVITY, VENUE, NOTE};
	  
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private Eventitem eventObj;
	private Table tableEventFlow;
	
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
		
		// Continue with the GUI
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
				
		Form VenueViewForm = toolkit.createForm(this);
		VenueViewForm.getBody().setBackgroundMode(SWT.INHERIT_DEFAULT);
		VenueViewForm.setBounds(0, 0, 700, 400);
		VenueViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		toolkit.paintBordersFor(VenueViewForm);
		VenueViewForm.setText("Event Flow");
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
		
		TableViewer tableViewEventFlow = new TableViewer(mainComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableEventFlow = tableViewEventFlow.getTable();
		tableEventFlow.setTouchEnabled(true);
		tableEventFlow.setLinesVisible(true);
		tableEventFlow.setHeaderVisible(true);
		tableEventFlow.setBounds(10, 10, 504, 268);
		tableViewEventFlow.setContentProvider(ArrayContentProvider.getInstance());
		toolkit.paintBordersFor(tableEventFlow);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColStartDateTime = tableViewerColumn_4.getColumn();
		ColStartDateTime.setWidth(100);
		ColStartDateTime.setText(STARTDATETIME);
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getStartDateTime().getDateRepresentation() + " " +
						obj.getDuration().getStartDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColEndDateTime = tableViewerColumn_3.getColumn();
		ColEndDateTime.setWidth(100);
		ColEndDateTime.setText(ENDDATETIME);
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getDuration().getEndDateTime().getDateRepresentation() + " " + 
						obj.getDuration().getEndDateTime().getTimeRepresentation();
			}
		});
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColActivity = tableViewerColumn_2.getColumn();
		ColActivity.setWidth(100);
		ColActivity.setText(ACTIVITY);
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getActivityName();
			}
		});
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColVenue = tableViewerColumn_1.getColumn();
		ColVenue.setWidth(100);
		ColVenue.setText(VENUE);
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventFlowEntry obj = (EventFlowEntry) element;
				return obj.getVenueName();
			}
		});
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewEventFlow, SWT.NONE);
		TableColumn ColNote = tableViewerColumn.getColumn();
		ColNote.setWidth(100);
		ColNote.setText(NOTE);
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// Create a table for users to input their information
		/////////////////////////////////////////////////////////////////////////////////////////////////
	//	createTableViewEventFlow();
		
		Button btnReturnToEvent = new Button(mainComposite, SWT.NONE);
		btnReturnToEvent.setBounds(545, 299, 129, 25);
		toolkit.adapt(btnReturnToEvent, true, true);
		btnReturnToEvent.setText("Return to Event page");
		
		Button btnAdd = new Button(mainComposite, SWT.NONE);
		btnAdd.setBounds(10, 299, 75, 25);
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		
		Button btnDelete = new Button(mainComposite, SWT.NONE);
		btnDelete.setBounds(97, 299, 75, 25);
		toolkit.adapt(btnDelete, true, true);
		btnDelete.setText("Delete");

	}
}
