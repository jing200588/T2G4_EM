import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Listener;

import au.com.bytecode.opencsv.CSVReader;

import com.ibm.icu.text.Collator;


public class ViewBudget extends Composite {
	/*My declaration start here.*/
	private final StackLayout stackLayout = new StackLayout();
	private final String[] titles = { "No.", "Item Name", "Total Price", "Satisfaction", "Type","Quantity"};
	private ControllerBudget budgetPersonalAssistant;
	private Eventitem current_event;
	private Vector<Item> item_list;
	private Vector<Integer> selected_compulsory = new Vector<Integer>();;
	private int budget;
	private int type_choice;
	private int satisfaction_choice;
	protected errormessageDialog errordiag;
	/*My declaration end here.*/
	private Composite BigContent;
	private Button btnStepInputDetails;
	private Button btnStepSelectCompulsoy;
	private Button btnStepConfirmResult;
	private Composite Step1;
	private Composite Step2;
	private Composite Step3;
	private Composite TypeOptionComposite;
	private Composite SatisfactionOptionComposite;
	private Text txtBudget;
	private Text txtInputList;
	private Button btnWithoutType;
	private Button btnWithType;
	private Button btnWithoutSatisfaction;
	private Button btnWithSatisfaction;
	private Button btnResetInputList;
	private Button btnConfirm;
	private Table table;
	private Combo comboSelection;
	private Button btnFinish;
	private Label lblListOfItems;
	private Button btnNext;
	private Text txtResult;

	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Text txtDirectory;
	private Button btnImport;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewBudget(Composite parent, int style, Eventitem input_ei) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});

		current_event = input_ei;

		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Form BudgetViewForm = formToolkit.createForm(this);
		BudgetViewForm.setBounds(0, 0, 700, 400);
		BudgetViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(BudgetViewForm);
		BudgetViewForm.setText("Budget Optimization");
		BudgetViewForm.getBody().setLayout(new FormLayout());

		/**********************************************************************************
		 * Overview Budget optimization composite 
		 **********************************************************************************/
		Composite mainComposite = new Composite(BudgetViewForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.top = new FormAttachment(50, -160);
		fd_mainComposite.bottom = new FormAttachment (50, 180);
		fd_mainComposite.left = new FormAttachment(50, -350);
		fd_mainComposite.right = new FormAttachment(50, 350);
		mainComposite.setLayoutData(fd_mainComposite);
		formToolkit.adapt(mainComposite);
		formToolkit.paintBordersFor(mainComposite);

		/**********************************************************************************
		 * Composite that will display the content 
		 **********************************************************************************/
		BigContent = new Composite(mainComposite, SWT.NONE);
		BigContent.setBounds(10, 76, 680, 264);
		formToolkit.adapt(BigContent);
		formToolkit.paintBordersFor(BigContent);
		BigContent.setLayout(stackLayout);

		/**********************************************************************************
		 * Composite that will display the global navigation button
		 **********************************************************************************/
		Composite ButtonComposite = new Composite(mainComposite, SWT.NONE);
		ButtonComposite.setBounds(10, 10, 680, 57);
		formToolkit.adapt(ButtonComposite);
		formToolkit.paintBordersFor(ButtonComposite);

		btnStepInputDetails = new Button(ButtonComposite, SWT.NONE);
		btnStepInputDetails.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = Step1;
				BigContent.layout();
				btnStepInputDetails.setEnabled(false);
				btnStepSelectCompulsoy.setEnabled(false);
				btnStepConfirmResult.setEnabled(false);
			}
		});
		btnStepInputDetails.setEnabled(false);
		btnStepInputDetails.setBounds(0, 10, 170, 25);
		formToolkit.adapt(btnStepInputDetails, true, true);
		btnStepInputDetails.setText("Step1: Input Details");

		btnStepSelectCompulsoy = new Button(ButtonComposite, SWT.NONE);
		btnStepSelectCompulsoy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = Step2;
				BigContent.layout();
				btnStepInputDetails.setEnabled(true);
				btnStepSelectCompulsoy.setEnabled(false);
				btnStepConfirmResult.setEnabled(false);
			}
		});
		btnStepSelectCompulsoy.setEnabled(false);
		btnStepSelectCompulsoy.setBounds(255, 10, 170, 25);
		formToolkit.adapt(btnStepSelectCompulsoy, true, true);
		btnStepSelectCompulsoy.setText("Step2: Select Compulsoy");

		btnStepConfirmResult = new Button(ButtonComposite, SWT.NONE);
		btnStepConfirmResult.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = Step3;
				BigContent.layout();
				btnStepInputDetails.setEnabled(true);
				btnStepSelectCompulsoy.setEnabled(true);
				btnStepConfirmResult.setEnabled(false);
			}
		});
		btnStepConfirmResult.setEnabled(false);
		btnStepConfirmResult.setBounds(510, 10, 170, 25);
		formToolkit.adapt(btnStepConfirmResult, true, true);
		btnStepConfirmResult.setText("Step3: Confirm Result");

		/**********************************************************************************
		 * Composite that will that display fields for the user to input.
		 **********************************************************************************/
		Step1 = new Composite(BigContent, SWT.NONE);
		Step1.setBounds(0, 0, 675, 260);
		formToolkit.adapt(Step1);
		formToolkit.paintBordersFor(Step1);

		Label lblEventBudget = new Label(Step1, SWT.NONE);
		lblEventBudget.setBounds(10, 10, 82, 15);
		formToolkit.adapt(lblEventBudget, true, true);
		lblEventBudget.setText("Event Budget: $");

		SatisfactionOptionComposite = new Composite(Step1, SWT.NONE);
		SatisfactionOptionComposite.setBounds(10, 103, 148, 59);
		formToolkit.adapt(SatisfactionOptionComposite);
		formToolkit.paintBordersFor(SatisfactionOptionComposite);

		btnWithSatisfaction = new Button(SatisfactionOptionComposite, SWT.RADIO);
		btnWithSatisfaction.setBounds(10, 38, 115, 16);
		formToolkit.adapt(btnWithSatisfaction, true, true);
		btnWithSatisfaction.setText("With Satisfaction");

		btnWithoutSatisfaction = new Button(SatisfactionOptionComposite, SWT.RADIO);
		btnWithoutSatisfaction.setSelection(true);
		btnWithoutSatisfaction.setBounds(10, 16, 128, 16);
		formToolkit.adapt(btnWithoutSatisfaction, true, true);
		btnWithoutSatisfaction.setText("Without Satisfaction");

		Label lblSelectSatisfactionOption = new Label(SatisfactionOptionComposite, SWT.NONE);
		lblSelectSatisfactionOption.setSize(148, 15);
		formToolkit.adapt(lblSelectSatisfactionOption, true, true);
		lblSelectSatisfactionOption.setText("Select Satisfaction Option:");

		TypeOptionComposite = new Composite(Step1, SWT.NONE);
		TypeOptionComposite.setBounds(10, 31, 148, 66);
		formToolkit.adapt(TypeOptionComposite);
		formToolkit.paintBordersFor(TypeOptionComposite);

		btnWithoutType = new Button(TypeOptionComposite, SWT.RADIO);
		btnWithoutType.setLocation(10, 20);
		btnWithoutType.setSize(90, 16);
		btnWithoutType.setSelection(true);
		formToolkit.adapt(btnWithoutType, true, true);
		btnWithoutType.setText("Without Type");

		btnWithType = new Button(TypeOptionComposite, SWT.RADIO);
		btnWithType.setLocation(10, 43);
		btnWithType.setSize(90, 16);
		formToolkit.adapt(btnWithType, true, true);
		btnWithType.setText("With Type");

		Label lblSelectTypeOption = new Label(TypeOptionComposite, SWT.NONE);
		lblSelectTypeOption.setSize(128, 15);
		formToolkit.adapt(lblSelectTypeOption, true, true);
		lblSelectTypeOption.setText("Select Type Option:");

		txtBudget = new Text(Step1, SWT.BORDER);
		txtBudget.setBounds(96, 4, 76, 21);
		formToolkit.adapt(txtBudget, true, true);

		txtInputList = new Text(Step1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtInputList.setBounds(229, 55, 441, 129);
		formToolkit.adapt(txtInputList, true, true);

		Label lblInputList = new Label(Step1, SWT.NONE);
		lblInputList.setBounds(229, 34, 441, 15);
		formToolkit.adapt(lblInputList, true, true);
		lblInputList.setText("Input format: [itemname] [price] [satisfaction] [type]");

		btnResetInputList = new Button(Step1, SWT.NONE);
		btnResetInputList.setBounds(427, 229, 128, 25);
		btnResetInputList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				txtInputList.setText("");
			}
		});
		formToolkit.adapt(btnResetInputList, true, true);
		btnResetInputList.setText("Reset Input List");

		btnConfirm = new Button(Step1, SWT.NONE);
		btnConfirm.setBounds(595,  229, 75, 25);
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selected_compulsory.removeAllElements();
				try {	

					txtBudget.getText().trim();
					if(txtBudget.getText().length() == 0) throw new IOException("Please enter a budget.");

					budget = (int) (Double.parseDouble(txtBudget.getText()) * 100);

					if(btnWithoutType.getSelection() == true)
						type_choice = 0;
					else if(btnWithType.getSelection() == true)
						type_choice = 1;

					if(btnWithoutSatisfaction.getSelection() == true)
						satisfaction_choice = 0;
					else if(btnWithSatisfaction.getSelection() == true)
						satisfaction_choice = 1;

					if(txtInputList.getText().length() == 0) throw new IOException("Input list must not be empty.");

					budgetPersonalAssistant  = new ControllerBudget(txtInputList.getText(), budget, type_choice, satisfaction_choice, current_event);
					item_list = budgetPersonalAssistant.getItemList();

					stackLayout.topControl = Step2;
					BigContent.layout();	

					refreshTable();

					btnStepInputDetails.setEnabled(true);	
				}
				catch (Exception ie) {

					if(ie.getMessage().equals("Please enter a budget."))
						errordiag = new errormessageDialog(new Shell(), "Please enter a budget.");
					else if(ie.getMessage().equals("Input list must not be empty."))
						errordiag = new errormessageDialog(new Shell(), "Input list must not be empty.");
					else if(ie.getMessage().equals("Cost should not be negative")) 
						errordiag = new errormessageDialog(new Shell(), "Cost should not be negative");	
					else 
						errordiag = new errormessageDialog(new Shell(), "Incorrect input format.");

					errordiag.open();
				}
			}
		});
		formToolkit.adapt(btnConfirm, true, true);
		btnConfirm.setText("Confirm");

		btnImport = new Button(Step1, SWT.NONE);
		btnImport.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);
				txtDirectory.setText(fsd.open());

				ImportCSV(txtDirectory.getText());


			}
		});


		btnImport.setBounds(595, 190, 75, 25);
		formToolkit.adapt(btnImport, true, true);
		btnImport.setText("Import");

		txtDirectory = new Text(Step1, SWT.BORDER);
		txtDirectory.setEnabled(false);
		txtDirectory.setEditable(false);
		txtDirectory.setBounds(303, 190, 286, 21);
		formToolkit.adapt(txtDirectory, true, true);
		/**********************************************************************************
		 * Composite that will that display the input by the user for he/she to select compulsory items.
		 **********************************************************************************/
		Step2 = new Composite(BigContent, SWT.NONE);
		Step2.setBounds(0, 0, 675, 260);
		formToolkit.adapt(Step2);
		formToolkit.paintBordersFor(Step2);
		
		

		table = new Table(Step2, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);			
		table.setHeaderVisible(true);
		
		final TableEditor editor = new TableEditor(table);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
		final int EDITABLECOLUMN = 5;

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				final TableItem item = (TableItem)e.item;
				if (item == null) return;
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent me) {
						int newPrice=0;
						Text text = (Text)editor.getEditor();
						try {
							int quantityNo = Integer.parseInt(text.getText());
							int index = Integer.parseInt(item.getText().substring(5, item.getText().length()));
							if(item_list.get(index-1).getQuantity() == 1) {
								newPrice = item_list.get(index-1).getPrice()*quantityNo;
								item_list.get(index-1).setQuantity(quantityNo);
								item_list.get(index-1).setPrice(newPrice);
								editor.getItem().setText(2, ""+"$"+((double) newPrice)/100);
							}
							else if(item_list.get(index-1).getQuantity() > 1) {
								int singleItemPrice = item_list.get(index-1).getPrice()/item_list.get(index-1).getQuantity();
								newPrice = singleItemPrice*quantityNo;
								item_list.get(index-1).setPrice(newPrice);
								item_list.get(index-1).setQuantity(quantityNo);
								editor.getItem().setText(2, ""+"$"+((double) newPrice)/100);
							}
													
						}
						catch (Exception qe) {
							text.setText("1");
							errordiag = new errormessageDialog(new Shell(), "Quantity can only be integer.");
							errordiag.open();
						}
						editor.getItem().setText(EDITABLECOLUMN, text.getText());
						
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});
		
		TableColumn col0 = new TableColumn(table, SWT.NULL);
		col0.setText("No.");
		col0.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {	
				sortColumn(0);
			}
		});

		TableColumn col1 = new TableColumn(table, SWT.NULL);
		col1.setText("Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(1);
			}
		});

		TableColumn col2 = new TableColumn(table, SWT.NULL);
		col2.setText("Price\t\t");
		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(2);
			}
		});

		TableColumn col3 = new TableColumn(table, SWT.NULL);
		col3.setText("Satisfaction");
		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(3);
			}
		});

		TableColumn col4 = new TableColumn(table, SWT.NULL);
		col4.setText("Type\t\t\t\t\t");
		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(4);
			}
		});

		TableColumn col5 = new TableColumn(table, SWT.NULL);
		col5.setText("Quantity\t\t");
		col5.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(5);
			}
		});

		table.setBounds(25, 25, 645, 172);
		selected_compulsory.removeAllElements();
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int select = Integer.parseInt(event.item.toString().substring(16, event.item.toString().length()-1));
				if (event.detail == SWT.CHECK) {
					if(selected_compulsory.contains(select) == false) 
						selected_compulsory.add(select);
					else 
						selected_compulsory.removeElement(select);	
				} 
			}
		});

		lblListOfItems = new Label(Step2, SWT.NONE);
		lblListOfItems.setBounds(25, 4, 234, 15);
		formToolkit.adapt(lblListOfItems, true, true);
		lblListOfItems.setText("Check the compulsory item(s):");

		btnNext = new Button(Step2, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				Collections.sort(selected_compulsory);

				try {	
					btnFinish.setEnabled(true);
					comboSelection.setEnabled(true);
					budgetPersonalAssistant.differentiateCompulsory(selected_compulsory, satisfaction_choice);
					double budget_left = budgetPersonalAssistant.budgetleft();
					if(budget_left < 0) throw new Exception("Not enough money to buy all compulsory item(s).");
					stackLayout.topControl = Step3;
					BigContent.layout();

					txtResult.setText(budgetPersonalAssistant.findOptimalShopList(type_choice, satisfaction_choice));	

					btnStepInputDetails.setEnabled(true);
					btnStepSelectCompulsoy.setEnabled(true);
					btnStepConfirmResult.setEnabled(false);

					comboSelection.removeAll();

					for(int i=1; i<=budgetPersonalAssistant.noOfCombination(); i++) {
						comboSelection.add("Combination " + i);
					}

					if(budgetPersonalAssistant.noOfCombination() > 0) {
						comboSelection.select(0);
					}
					else {
						comboSelection.setEnabled(false);
						btnFinish.setEnabled(false);
					}

				} catch(Exception ie) {
					errordiag = new errormessageDialog(new Shell(), ie.getMessage());
					errordiag.open();
				} catch(OutOfMemoryError outofmemo)
				{
					comboSelection.setEnabled(false);
					btnFinish.setEnabled(false);
					errordiag = new errormessageDialog(new Shell(), "There is not enough memory space to perform the task.\n"
							+ "Please restart the program and reduce your input size.\n");
					errordiag.open();
					btnStepInputDetails.setEnabled(false);
					btnStepSelectCompulsoy.setEnabled(false);
					btnStepConfirmResult.setEnabled(false);
				}
			}
		});
		btnNext.setBounds(595, 213, 75, 25);
		formToolkit.adapt(btnNext, true, true);
		btnNext.setText("Next");
		
		Label lblNoteAllChanges = new Label(Step2, SWT.NONE);
		lblNoteAllChanges.setBounds(25, 213, 404, 15);
		formToolkit.adapt(lblNoteAllChanges, true, true);
		lblNoteAllChanges.setText("Note: All changes to item quantity will be lost if you click back to Step 1!");

		/**********************************************************************************
		 * Composite that will that display the all the different optimize list.
		 **********************************************************************************/
		Step3 = new Composite(BigContent, SWT.NONE);
		Step3.setBounds(0, 80, 700, 300);
		formToolkit.adapt(Step3);
		formToolkit.paintBordersFor(Step3);

		txtResult = new Text(Step3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtResult.setEditable(false);
		txtResult.setBounds(10, 32, 664, 182);
		formToolkit.adapt(txtResult, true, true);

		comboSelection = new Combo(Step3, SWT.NONE);
		comboSelection.setBounds(448, 236, 126, 23);
		formToolkit.adapt(comboSelection);
		formToolkit.paintBordersFor(comboSelection);

		btnFinish = new Button(Step3, SWT.NONE);
		btnFinish.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int option = comboSelection.getSelectionIndex();
				budgetPersonalAssistant.saveOptimizeOption(option);	
				ViewMain.ReturnView();
			}
		});
		btnFinish.setBounds(595, 234, 75, 25);
		formToolkit.adapt(btnFinish, true, true);
		btnFinish.setText("Finish");

		Label lblSelectAnCombination = new Label(Step3, SWT.NONE);
		lblSelectAnCombination.setBounds(321, 239, 121, 15);
		formToolkit.adapt(lblSelectAnCombination, true, true);
		lblSelectAnCombination.setText("Select an combination:");

		Label lblListOfAll = new Label(Step3, SWT.NONE);
		lblListOfAll.setBounds(10, 10, 249, 15);
		formToolkit.adapt(lblListOfAll, true, true);
		lblListOfAll.setText("List of all possible combination:");
	}

	public void sortColumn(int columnNo) {

		selected_compulsory.removeAllElements();
		TableItem[] items = table.getItems();
		Collator collator = Collator.getInstance(Locale.getDefault());
		int col0_value1=0, col0_value2=0, col3_value1=0, col3_value2=0,col5_value1=0, col5_value2=0;
		double col2_value1=0, col2_value2=0;
		String col1_value1="", col1_value2="", col4_value1="", col4_value2="";
		boolean compareCorrect = false;
		for (int i = 1; i<items.length; i++) {
			if(columnNo == 0) 
				col0_value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
			else if (columnNo == 1) 
				col1_value1 = items[i].getText(1);
			else if (columnNo == 2)
				col2_value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
			else if (columnNo == 3)
				col3_value1 = Integer.parseInt(items[i].getText(3));
			else if (columnNo == 4)
				col4_value1 = items[i].getText(4);
			else if (columnNo == 5)
				col5_value1 = Integer.parseInt(items[i].getText(5));
			for(int j=0; j < i; j++) {
				compareCorrect = false;
				if(columnNo == 0)  {
					col0_value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
					if(col0_value1 - col0_value2 < 0) 
						compareCorrect = true;
				}					
				else if (columnNo == 1) {
					col1_value2 = items[j].getText(1);
					if(collator.compare(col1_value1, col1_value2) < 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 2) {
					col2_value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
					if((col2_value1 - col2_value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 3) {
					col3_value2 = Integer.parseInt(items[j].getText(3));
					if((col3_value1 - col3_value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 4) {
					col4_value2 = items[j].getText(4);
					if(collator.compare(col4_value1, col4_value2) < 0) 
						compareCorrect = true;
				}
				else if (columnNo == 5) {
					col5_value2 = Integer.parseInt(items[j].getText(5));
					if((col5_value1 - col5_value2) > 0) 
						compareCorrect = true;
				}

				if(compareCorrect == true) {
					Item temp = item_list.get(i);
					item_list.remove(i);
					String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4),items[i].getText(5)};
					items[i].dispose();
					TableItem item = new TableItem(table, SWT.NONE, j);
					item_list.add(j, temp);
					item.setText(values);
					items = table.getItems();
					break;
				}
			}	
		}

		refreshTable();
	}

	public void refreshTable() {

		table.removeAll();
		for (int loopIndex = 0; loopIndex < item_list.size(); loopIndex++) {
			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(0, "Item " + (loopIndex+1));
			item.setText(1, item_list.get(loopIndex).getItem());
			item.setText(2, "$"+((double) item_list.get(loopIndex).getPrice())/100);
			if(satisfaction_choice == 1) 
				item.setText(3, ""+item_list.get(loopIndex).getSatisfaction_value());
			if(type_choice == 1)
				item.setText(4, ""+item_list.get(loopIndex).getType());		
			item.setText(5, ""+item_list.get(loopIndex).getQuantity());
		}
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			table.getColumn(loopIndex).pack();
		}
	}


	public void ImportCSV (String filepath) {
		try {
			String importedText = "";
			CSVReader reader = new CSVReader(new FileReader(filepath));

			List<String[]> myEntries = reader.readAll();

			//populating entries.
			for (int i=0; i<myEntries.size(); i++) {
				for(int j=0; j<myEntries.get(i).length; j++) {
					importedText += myEntries.get(i)[j];
				}
				importedText +="\n";
			}		
			txtInputList.setText(importedText);

		} catch (FileNotFoundException e) {
			errordiag = new errormessageDialog(new Shell(), "File not found.");
			errordiag.open();
		} catch (IOException e) {
			errordiag = new errormessageDialog(new Shell(), "Incorrect file format.");
			errordiag.open();
		}

	}
}
