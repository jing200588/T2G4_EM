import java.util.Vector;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;


public class ViewEventGUI3 extends Composite {
	private Table BudgetResult;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventGUI3(Composite parent, int style, final Eventitem curevent) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite sc1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc1.setExpandHorizontal(true);
		sc1.setExpandVertical(true);
		
		Composite maincomp = new Composite(sc1, SWT.NONE);
		maincomp.setLayout(new FormLayout());
		
		
		
		Label Title = new Label(maincomp, SWT.NONE);
		
		Title.setText("View Event:");
		Title.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		FormData fd_Title = new FormData();
		fd_Title.top = new FormAttachment(0, 10);
		fd_Title.left = new FormAttachment(0, 10);
		Title.setLayoutData(fd_Title);
		
		//Event Particulars
		Composite EparticularsComp = new Composite(maincomp, SWT.NONE);
		FormData fd_EparticularsComp = new FormData();
		fd_EparticularsComp.right = new FormAttachment(80, 0);
		fd_EparticularsComp.top = new FormAttachment(Title, 30);
		fd_EparticularsComp.left = new FormAttachment(20, 0);
		EparticularsComp.setLayoutData(fd_EparticularsComp);
		EparticularsComp.setLayout(new GridLayout(3, false));

		Label Eparticulars = new Label(EparticularsComp, SWT.NONE);
		Eparticulars.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Eparticulars.setFont(SWTResourceManager.getFont("Courier New Baltic", 16, SWT.BOLD));
		Eparticulars.setText("Event Particulars");
		new Label(EparticularsComp, SWT.NONE);
		
		Button Epartedit = new Button(EparticularsComp, SWT.NONE);
		Epartedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EmanagerGUIjface.EventParticulars();
			}
		});
		GridData gd_Epartedit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_Epartedit.widthHint = 85;
		Epartedit.setLayoutData(gd_Epartedit);
		Epartedit.setText("Edit");
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);
		new Label(EparticularsComp, SWT.NONE);
		
		Label Enamelabel = new Label(EparticularsComp, SWT.NONE);
		Enamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enamelabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Enamelabel.setText("Event Name:");
		
		Label Ename = new Label(EparticularsComp, SWT.WRAP);
		Ename.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		Ename.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Ename.setText(curevent.getName());
		
		Label Startdntlabel = new Label(EparticularsComp, SWT.NONE);
		Startdntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Startdntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Startdntlabel.setText("Start Date and Time:");
		
		Label Startdate = new Label(EparticularsComp, SWT.NONE);
		Startdate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Startdate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Startdate.setText(curevent.getStartDate());
		
		Label Starttime = new Label(EparticularsComp, SWT.NONE);
		Starttime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Starttime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Starttime.setText(curevent.getStartTime()+"HRS");
		
		Label Enddntlabel = new Label(EparticularsComp, SWT.NONE);
		Enddntlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Enddntlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Enddntlabel.setText("End Date and Time:");
		
		Label Enddate = new Label(EparticularsComp, SWT.NONE);
		Enddate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Enddate.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Enddate.setText(curevent.getEndDate());
		
		Label Endtime = new Label(EparticularsComp, SWT.NONE);
		Endtime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		Endtime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Endtime.setText(curevent.getEndTime()+"HRS");
		
		Label Edescriptionlabel = new Label(EparticularsComp, SWT.NONE);
		Edescriptionlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		Edescriptionlabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		Edescriptionlabel.setText("Description:");
		
		CLabel Edescription = new CLabel(EparticularsComp, SWT.NONE);
		Edescription.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		Edescription.setText("New Label");
	
		//Divider1
		/*
		Label divider1 = new Label(maincomp, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider1 = new FormData();
		fd_divider1.top = new FormAttachment(EparticularsComp, 30);
		fd_divider1.bottom = new FormAttachment(EparticularsComp, 55, SWT.BOTTOM);
		fd_divider1.left = new FormAttachment(5, 0);
		fd_divider1.right = new FormAttachment(95, 0);
		divider1.setLayoutData(fd_divider1);*/
		Label divider1 = createdivider(maincomp, EparticularsComp);
		
		//Event Program Flow
		Composite Eprogflowcomp = new Composite(maincomp, SWT.NONE);
		Eprogflowcomp.setLayout(new GridLayout(3, false));
		FormData fd_Eprogflowcomp = new FormData();
		fd_Eprogflowcomp.right = new FormAttachment(80, 0);
		fd_Eprogflowcomp.left = new FormAttachment(20, 0);
		fd_Eprogflowcomp.top = new FormAttachment(divider1, 30);
		Eprogflowcomp.setLayoutData(fd_Eprogflowcomp);
		
		Label EProgramFlow = new Label(Eprogflowcomp, SWT.NONE);
		EProgramFlow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		EProgramFlow.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		EProgramFlow.setText("Event Program Flow");
		
		Label lblNewLabel_1 = new Label(Eprogflowcomp, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 38;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText(" ");
		
		Button Eprogflowedit = new Button(Eprogflowcomp, SWT.NONE);
		Eprogflowedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
	
			}
		});
		GridData gd_Eprogflowedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Eprogflowedit.widthHint = 85;
		Eprogflowedit.setLayoutData(gd_Eprogflowedit);
		Eprogflowedit.setText("Edit");
		
		//Divider 2
		/*
		Label Divider2 = new Label(maincomp, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_Divider2 = new FormData();
		fd_Divider2.top = new FormAttachment(Eprogflowcomp, 30);
		fd_Divider2.left = new FormAttachment(5, 0);
		fd_Divider2.right = new FormAttachment(95, 0);
		Divider2.setLayoutData(fd_Divider2);*/
		Label divider2 = createdivider(maincomp, Eprogflowcomp);
		
		//Book Venue 
		Label lblNewLabel = new Label(maincomp, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		lblNewLabel.setText("View Event:");
		
		Composite Bookvenuecomp = new Composite(maincomp, SWT.NONE);
		Bookvenuecomp.setLayout(new GridLayout(3, false));
		FormData fd_Bookvenuecomp = new FormData();
		fd_Bookvenuecomp.right = new FormAttachment(80, 0);
		fd_Bookvenuecomp.left = new FormAttachment(20, 0);
		fd_Bookvenuecomp.top = new FormAttachment(divider2, 30);
		Bookvenuecomp.setLayoutData(fd_Bookvenuecomp);
		
		Label lblBookVenue = new Label(Bookvenuecomp, SWT.NONE);
		lblBookVenue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblBookVenue.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblBookVenue.setText("Book Venue");
		
		Label dummy = new Label(Bookvenuecomp, SWT.NONE);
		GridData gd_dummy = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_dummy.widthHint = 27;
		dummy.setLayoutData(gd_dummy);
		
		Button Bookvenueedit = new Button(Bookvenuecomp, SWT.NONE);
		Bookvenueedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EmanagerGUIjface.BookVenue();
			}
		});
		GridData gd_Bookvenueedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Bookvenueedit.widthHint = 85;
		Bookvenueedit.setLayoutData(gd_Bookvenueedit);
		Bookvenueedit.setText("Edit");
		
		//Divider 3
		
		Label divider3 = new Label(maincomp, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider3 = new FormData();
		fd_divider3.top = new FormAttachment(Bookvenuecomp, 30);
		fd_divider3.left = new FormAttachment(5, 0);
		fd_divider3.right = new FormAttachment(95, 0);
		divider3.setLayoutData(fd_divider3);
	//	Label divider3 = createdivider(maincomp, Bookvenuecomp);
		
		//Calculate Budget
		Composite Budgetcomp = new Composite(maincomp, SWT.NONE);
		Budgetcomp.setLayout(new GridLayout(3, false));
		FormData fd_Budgetcomp = new FormData();
		fd_Budgetcomp.right = new FormAttachment(80, 0);
		fd_Budgetcomp.left = new FormAttachment(20, 0);
		fd_Budgetcomp.top = new FormAttachment(divider3, 30);
		Budgetcomp.setLayoutData(fd_Budgetcomp);
		
		Label Cbudget = new Label(Budgetcomp, SWT.NONE);
		Cbudget.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Cbudget.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		Cbudget.setText("Optimal Purchase");
		
		Label dummy3 = new Label(Budgetcomp, SWT.NONE);
		GridData gd_dummy3 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_dummy3.widthHint = 49;
		dummy3.setLayoutData(gd_dummy3);
		
		Button btnCalculate = new Button(Budgetcomp, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EmanagerGUIjface.CalcBudget();
			}
		});
		GridData gd_btnCalculate = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnCalculate.widthHint = 85;
		btnCalculate.setLayoutData(gd_btnCalculate);
		
		btnCalculate.setText("Calculate");
		
		//Divider 4
		/*
		Label divider4 = new Label(maincomp, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider4 = new FormData();
		fd_divider4.top = new FormAttachment(Budgetcomp, 30);
		fd_divider4.left = new FormAttachment(5, 0);
		fd_divider4.right = new FormAttachment(95, 0);
		divider4.setLayoutData(fd_divider4);*/
		Label divider4 = createdivider(maincomp, Budgetcomp);
		
	//	if ()
	//		Table BudgetTable = OptimizedTable(Eventitem curevent, Composite Budgetcomp);
		//Budget Table
	/*	BudgetResult = new Table(Budgetcomp, SWT.BORDER | SWT.FULL_SELECTION);		
		BudgetResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		BudgetResult.setHeaderVisible(true);
		BudgetResult.setBounds(25, 25, 645, 200);
		
		TableColumn col0 = new TableColumn(BudgetResult, SWT.NULL);
		col0.setText("No.");
		col0.setResizable(false);

		TableColumn col1 = new TableColumn(BudgetResult, SWT.NULL);
		col1.setText("Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		col1.setResizable(false);
		
		TableColumn col2 = new TableColumn(BudgetResult, SWT.NULL);
		col2.setText("Price\t\t\t\t");
		col2.setResizable(false);
		
		TableColumn col3 = new TableColumn(BudgetResult, SWT.NULL);
		col3.setText("Satisfaction");
		col3.setResizable(false);
		

		TableColumn col4 = new TableColumn(BudgetResult, SWT.NULL);
		col4.setText("Type\t\t\t\t\t\t\t\t\t\t");
		col4.setResizable(false);
		

		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			BudgetResult.getColumn(loopIndex).pack();
		}	

		*/
		
		//Participant List
		Composite Plistcomp = new Composite(maincomp, SWT.NONE);
		Plistcomp.setLayout(new GridLayout(3, false));
		FormData fd_Plistcomp = new FormData();
		fd_Plistcomp.left = new FormAttachment(20, 0);
		fd_Plistcomp.right = new FormAttachment(80, 0);
		fd_Plistcomp.top = new FormAttachment(divider4, 30);
		Plistcomp.setLayoutData(fd_Plistcomp);
		
		Label ParticipantList = new Label(Plistcomp, SWT.NONE);
		ParticipantList.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		ParticipantList.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		ParticipantList.setText("Participant List");
		
		Label dummy4 = new Label(Plistcomp, SWT.NONE);
		dummy4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Button btnEdit = new Button(Plistcomp, SWT.NONE);
		GridData gd_btnEdit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnEdit.widthHint = 85;
		btnEdit.setLayoutData(gd_btnEdit);
		btnEdit.setText("Edit");
		
		//Divider 5
		/*
		Label divider5 = new Label(maincomp, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider5 = new FormData();
		fd_divider5.top = new FormAttachment(Plistcomp, 30);
		fd_divider5.left = new FormAttachment(5, 0);
		fd_divider5.right = new FormAttachment(95, 0);
		divider5.setLayoutData(fd_divider5);*/
		Label divider5 = createdivider(maincomp, Plistcomp);
		
		//Advertising
		Composite Advertcomp = new Composite(maincomp, SWT.NONE);
		Advertcomp.setLayout(new GridLayout(3, false));
		FormData fd_Advertcomp = new FormData();
		fd_Advertcomp.top = new FormAttachment(divider5, 30);
		fd_Advertcomp.left = new FormAttachment(20, 0);
		fd_Advertcomp.right = new FormAttachment(80, 0);
		Advertcomp.setLayoutData(fd_Advertcomp);
		
		Label Advertising = new Label(Advertcomp, SWT.NONE);
		Advertising.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		Advertising.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		Advertising.setText("Advertising");
		
		Label dummy5 = new Label(Advertcomp, SWT.NONE);
		dummy5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Button Advertedit = new Button(Advertcomp, SWT.NONE);
		GridData gd_Advertedit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_Advertedit.widthHint = 85;
		Advertedit.setLayoutData(gd_Advertedit);
		Advertedit.setText("Edit");
		
		Button btnDeleteEvent = new Button(maincomp, SWT.NONE);
		btnDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteconfirmDialog confirm = new deleteconfirmDialog(new Shell(), SWT.APPLICATION_MODAL, curevent.getName());
				if (confirm.open() == 1) {
					MainModel.DeleteEvent(curevent);
					EmanagerGUIjface.DeleteItem();
				}
					
			}
		});
		FormData fd_btnDeleteEvent = new FormData();
		fd_btnDeleteEvent.bottom = new FormAttachment(Title, 0, SWT.BOTTOM);
		fd_btnDeleteEvent.right = new FormAttachment(divider1, 0, SWT.RIGHT);
		btnDeleteEvent.setLayoutData(fd_btnDeleteEvent);
		btnDeleteEvent.setText("DELETE EVENT");
		
		
		sc1.setContent(maincomp);
		sc1.setMinSize(maincomp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public Label createdivider (Composite container, Composite object) {
		Label divider = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_divider = new FormData();
		fd_divider.top = new FormAttachment(object, 30);
		fd_divider.left = new FormAttachment(5, 0);
		fd_divider.right = new FormAttachment(95, 0);
		divider.setLayoutData(fd_divider);
		
		return divider;
	}
	
	public Table OptimizedTable(Eventitem curevent, Composite Budgetcomp) {
		BudgetController bc = new BudgetController();
		Vector<Item> item_list = bc.getCombinationList(0);
		
		BudgetResult = new Table(Budgetcomp, SWT.BORDER | SWT.FULL_SELECTION);		
		BudgetResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		BudgetResult.setHeaderVisible(true);
		BudgetResult.setBounds(25, 25, 645, 200);
		
		TableColumn col0 = new TableColumn(BudgetResult, SWT.NULL);
		col0.setText("No.");
		col0.setResizable(false);

		TableColumn col1 = new TableColumn(BudgetResult, SWT.NULL);
		col1.setText("Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		col1.setResizable(false);
		
		TableColumn col2 = new TableColumn(BudgetResult, SWT.NULL);
		col2.setText("Price\t\t\t\t");
		col2.setResizable(false);
		
		TableColumn col3 = new TableColumn(BudgetResult, SWT.NULL);
		col3.setText("Satisfaction");
		col3.setResizable(false);
		

		TableColumn col4 = new TableColumn(BudgetResult, SWT.NULL);
		col4.setText("Type\t\t\t\t\t\t\t\t\t\t");
		col4.setResizable(false);
		
		BudgetResult.removeAll();
		for (int loopIndex = 0; loopIndex < item_list.size(); loopIndex++) {
			TableItem item = new TableItem(BudgetResult, SWT.NULL);
			item.setText(0, "Item " + (loopIndex+1));
			item.setText(1, item_list.get(loopIndex).getItem());
			item.setText(2, "$"+((double) item_list.get(loopIndex).getPrice())/100);
			item.setText(3, ""+item_list.get(loopIndex).getSatisfaction_value());
			item.setText(4, ""+item_list.get(loopIndex).getType());					
		}
		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
			BudgetResult.getColumn(loopIndex).pack();
		}							

		return null;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
