import java.util.Locale;
import java.util.Vector;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.util.Collections;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;

import com.ibm.icu.text.Collator;

public class ViewBudget extends Composite {
	/*My declaration start here.*/
	private final StackLayout stackLayout = new StackLayout();
	private final String[] titles = { "No.", "Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t", "Price\t\t\t\t", "Satisfaction", "Type\t\t\t\t\t\t\t\t\t\t"};
	private ControllerBudget budgetPersonalAssistant;
	private Eventitem event_object;
	private Vector<Item> item_list;
	private Vector<Integer> selected_compulsory;
	private int budget;
	private int type_choice;
	private int satisfaction_choice;
	/*My declaration end here.*/

	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Button btnStepInputDetails;
	private Button btnStepSelectCompulsoy;
	private Button btnStepConfirmResult;
	private Composite Step1;
	private Composite Step2;
	private Composite Step3;
	private Composite BigContent;
	private Composite type_option;
	private Composite satisfaction_option;
	private Text txtBudget;
	private Text txt_input_list;
	private Button btnWithoutType;
	private Button btnWithType;
	private Button btnWithoutSatisfaction;
	private Button btnWithSatisfaction;
	private Button btnChange;
	private Button btnResetInputList;
	private Button btnConfirm_S1;
	private Button btnConfirm_S1_1;
	private Text txt_error_S1;
	private Label lblError_S2;
	private Table table;
	private Label lblListOfItems;
	private Button btnNext;
	private Label label;
	private Text txt_result;
	private Combo combo_selection;
	private Button btnFinish;
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewBudget(Composite parent, int style, final Eventitem ei) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});
		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		event_object = ei;

		Form BudgetViewForm = formToolkit.createForm(this);
		BudgetViewForm.setBounds(0, 0, 700, 400);
		BudgetViewForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(BudgetViewForm);
		BudgetViewForm.setText("Budget Optimization");
		BudgetViewForm.getBody().setLayout(new FormLayout());

		Composite mainComposite = new Composite(BudgetViewForm.getBody(), SWT.NONE);
		FormData fd_mainComposite = new FormData();
		fd_mainComposite.top = new FormAttachment(50, -160);
		fd_mainComposite.bottom = new FormAttachment (50, 180);
		fd_mainComposite.left = new FormAttachment(50, -350);
		fd_mainComposite.right = new FormAttachment(50, 350);
		mainComposite.setLayoutData(fd_mainComposite);
		formToolkit.adapt(mainComposite);
		formToolkit.paintBordersFor(mainComposite);

		BigContent = new Composite(mainComposite, SWT.NONE);
		BigContent.setBounds(10, 76, 680, 264);
		formToolkit.adapt(BigContent);
		formToolkit.paintBordersFor(BigContent);
		BigContent.setLayout(stackLayout);

		Composite btnComposite = new Composite(mainComposite, SWT.NONE);
		btnComposite.setBounds(10, 10, 680, 57);
		formToolkit.adapt(btnComposite);
		formToolkit.paintBordersFor(btnComposite);

		btnStepInputDetails = new Button(btnComposite, SWT.NONE);
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

		btnStepSelectCompulsoy = new Button(btnComposite, SWT.NONE);
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

		btnStepConfirmResult = new Button(btnComposite, SWT.NONE);
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

		Step1 = new Composite(BigContent, SWT.NONE);
		Step1.setBounds(0, 0, 675, 260);
		formToolkit.adapt(Step1);
		formToolkit.paintBordersFor(Step1);

		Label lblEventBudget = new Label(Step1, SWT.NONE);
		lblEventBudget.setBounds(10, 10, 82, 15);
		formToolkit.adapt(lblEventBudget, true, true);
		lblEventBudget.setText("Event Budget: $");

		satisfaction_option = new Composite(Step1, SWT.NONE);
		satisfaction_option.setBounds(10, 103, 148, 59);
		formToolkit.adapt(satisfaction_option);
		formToolkit.paintBordersFor(satisfaction_option);

		btnWithSatisfaction = new Button(satisfaction_option, SWT.RADIO);
		btnWithSatisfaction.setBounds(10, 38, 115, 16);
		formToolkit.adapt(btnWithSatisfaction, true, true);
		btnWithSatisfaction.setText("With Satisfaction");

		btnWithoutSatisfaction = new Button(satisfaction_option, SWT.RADIO);
		btnWithoutSatisfaction.setSelection(true);
		btnWithoutSatisfaction.setBounds(10, 16, 128, 16);
		formToolkit.adapt(btnWithoutSatisfaction, true, true);
		btnWithoutSatisfaction.setText("Without Satisfaction");

		Label lblSelectSatisfactionOption = new Label(satisfaction_option, SWT.NONE);
		lblSelectSatisfactionOption.setSize(148, 15);
		formToolkit.adapt(lblSelectSatisfactionOption, true, true);
		lblSelectSatisfactionOption.setText("Select Satisfaction Option:");

		type_option = new Composite(Step1, SWT.NONE);
		type_option.setBounds(10, 31, 148, 66);
		formToolkit.adapt(type_option);
		formToolkit.paintBordersFor(type_option);

		btnWithoutType = new Button(type_option, SWT.RADIO);
		btnWithoutType.setLocation(10, 20);
		btnWithoutType.setSize(90, 16);
		btnWithoutType.setSelection(true);
		formToolkit.adapt(btnWithoutType, true, true);
		btnWithoutType.setText("Without Type");

		btnWithType = new Button(type_option, SWT.RADIO);
		btnWithType.setLocation(10, 43);
		btnWithType.setSize(90, 16);
		formToolkit.adapt(btnWithType, true, true);
		btnWithType.setText("With Type");

		Label lblSelectTypeOption = new Label(type_option, SWT.NONE);
		lblSelectTypeOption.setSize(128, 15);
		formToolkit.adapt(lblSelectTypeOption, true, true);
		lblSelectTypeOption.setText("Select Type Option:");

		btnChange = new Button(Step1, SWT.NONE);
		btnChange.setEnabled(false);
		btnChange.setBounds(10, 168, 75, 25);
		btnChange.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				txtBudget.setEnabled(true);
				btnWithoutType.setEnabled(true);
				btnWithType.setEnabled(true);
				btnWithoutSatisfaction.setEnabled(true);
				btnWithSatisfaction.setEnabled(true);
				btnConfirm_S1.setEnabled(true);
				btnConfirm_S1_1.setEnabled(false);
				txt_input_list.setEnabled(false);
				btnResetInputList.setEnabled(false);
				txt_error_S1.setVisible(false);
			}
		});
		formToolkit.adapt(btnChange, true, true);
		btnChange.setText("Change");

		btnConfirm_S1 = new Button(Step1, SWT.NONE);
		btnConfirm_S1.setBounds(108, 168, 75, 25);
		btnConfirm_S1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try 
				{
					budget = (int) (Double.parseDouble(txtBudget.getText().toString()) * 100);

					/*Disable all option on the left*/
					txtBudget.setEnabled(false);
					btnWithoutType.setEnabled(false);
					btnWithType.setEnabled(false);
					btnWithoutSatisfaction.setEnabled(false);
					btnWithSatisfaction.setEnabled(false);
					btnConfirm_S1.setEnabled(false);


					//Enable some button
					btnChange.setEnabled(true);

					if(btnWithoutType.getSelection() == true)
						type_choice = 0;
					else if(btnWithType.getSelection() == true)
						type_choice = 1;

					if(btnWithoutSatisfaction.getSelection() == true)
						satisfaction_choice = 0;
					else if(btnWithSatisfaction.getSelection() == true)
						satisfaction_choice = 1;

					/*Enable all option on the right*/
					txt_error_S1.setVisible(false);
					txt_input_list.setEnabled(true);
					btnResetInputList.setEnabled(true);
					btnConfirm_S1_1.setEnabled(true);
				}
				catch (Exception ie) {
					if(ie.getMessage().equals("empty String")) 
						txt_error_S1.setText("***Please input an event budget.***");
					else
						txt_error_S1.setText("***Event Budget can only be numeric.***");
					txt_error_S1.setVisible(true);
				}
			}
		});
		formToolkit.adapt(btnConfirm_S1, true, true);
		btnConfirm_S1.setText("Confirm");

		txtBudget = new Text(Step1, SWT.BORDER);
		txtBudget.setBounds(96, 4, 76, 21);
		formToolkit.adapt(txtBudget, true, true);

		txt_input_list = new Text(Step1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txt_input_list.setBounds(229, 55, 441, 163);
		txt_input_list.setEnabled(false);
		formToolkit.adapt(txt_input_list, true, true);

		Label lblInputList = new Label(Step1, SWT.NONE);
		lblInputList.setBounds(229, 34, 55, 15);
		formToolkit.adapt(lblInputList, true, true);
		lblInputList.setText("Input List:");

		btnResetInputList = new Button(Step1, SWT.NONE);
		btnResetInputList.setBounds(418, 229, 128, 25);
		btnResetInputList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txt_input_list.setText("");
			}
		});
		btnResetInputList.setEnabled(false);
		formToolkit.adapt(btnResetInputList, true, true);
		btnResetInputList.setText("Reset Input List");

		txt_error_S1 = new Text(Step1, SWT.READ_ONLY | SWT.MULTI);

		txt_error_S1.setBounds(229, 4, 441, 29);
		txt_error_S1.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		txt_error_S1.setEnabled(false);
		formToolkit.adapt(txt_error_S1, true, true);

		btnConfirm_S1_1 = new Button(Step1, SWT.NONE);
		btnConfirm_S1_1.setBounds(595,  229, 75, 25);
		btnConfirm_S1_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				selected_compulsory = new Vector<Integer>();

				try {	
					txt_error_S1.setVisible(false);
					budgetPersonalAssistant  = new ControllerBudget(txt_input_list.getText(), budget, type_choice, satisfaction_choice, ei);
					item_list = budgetPersonalAssistant.getItemList();
					lblError_S2.setVisible(false);
					stackLayout.topControl = Step2;
					BigContent.layout();

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
					}
					for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
						table.getColumn(loopIndex).pack();
					}							
					btnStepInputDetails.setEnabled(true);	
				}
				catch (Exception ie) {
					if(ie.getMessage().equals("***Input list must not be empty.***"))
						txt_error_S1.setText("***Input list must not be empty.***");
					else
						txt_error_S1.setText("***Incorrect input format.***");
					txt_error_S1.setVisible(true);
				}
			}
		});

		btnConfirm_S1_1.setEnabled(false);
		formToolkit.adapt(btnConfirm_S1_1, true, true);
		btnConfirm_S1_1.setText("Confirm");



		Step2 = new Composite(BigContent, SWT.NONE);
		Step2.setBounds(0, 0, 675, 260);
		formToolkit.adapt(Step2);
		formToolkit.paintBordersFor(Step2);

		table = new Table(Step2, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);			
		table.setHeaderVisible(true);

		/*for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[loopIndex]);
			column.setResizable(false);
		}*/

		TableColumn col0 = new TableColumn(table, SWT.NULL);
		col0.setText("No.");
		col0.setResizable(false);
		col0.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = table.getItems();
				for (int i = 1; i<items.length; i++) {
					int value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
					for(int j=0; j < i; j++) {
						int value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
						if(value1 - value2 < 0) {
							System.out.println(value1 + " " + value2);
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			}
			});

		TableColumn col1 = new TableColumn(table, SWT.NULL);
		col1.setText("Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		col1.setResizable(false);
		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = table.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				for (int i = 1; i<items.length; i++) {
					String value1 = items[i].getText(1);
					for(int j=0; j < i; j++) {
						String value2 = items[j].getText(1);
						if(collator.compare(value1, value2) < 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			}
			});

		TableColumn col2 = new TableColumn(table, SWT.NULL);
		col2.setText("Price\t\t\t\t");
		col2.setResizable(false);
		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort price
				TableItem[] items = table.getItems();
				for (int i = 1; i<items.length; i++) {
					double value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
					for(int j=0; j < i; j++) {
						double value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
						if((value1 - value2) > 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			}
			});

		TableColumn col3 = new TableColumn(table, SWT.NULL);
		col3.setText("Satisfaction");
		col3.setResizable(false);
		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort satisfaction
				TableItem[] items = table.getItems();
				for (int i = 1; i<items.length; i++) {
					int value1 = Integer.parseInt(items[i].getText(3));
					for(int j=0; j < i; j++) {
						int value2 = Integer.parseInt(items[j].getText(3));
						if((value1 - value2) > 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			}
			});

		TableColumn col4 = new TableColumn(table, SWT.NULL);
		col4.setText("Type\t\t\t\t\t\t\t\t\t\t");
		col4.setResizable(false);
		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				//sort name
				TableItem[] items = table.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				for (int i = 1; i<items.length; i++) {
					String value1 = items[i].getText(4);
					for(int j=0; j < i; j++) {
						String value2 = items[j].getText(4);
						if(collator.compare(value1, value2) < 0) {
							String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4)};
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			}
			});


		table.setBounds(25, 25, 645, 200);
		selected_compulsory = new Vector<Integer>();
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					int select = Integer.parseInt(event.item.toString().substring(16, event.item.toString().length()-1));
					if(selected_compulsory.contains(select) == false)
						selected_compulsory.add(select);
					else
						selected_compulsory.removeElement(select);
				} 
			}
		});	

		lblError_S2 = new Label(Step2, SWT.NONE);
		lblError_S2.setEnabled(false);
		lblError_S2.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblError_S2.setBounds(25, 226, 512, 28);
		formToolkit.adapt(lblError_S2, true, true);

		lblListOfItems = new Label(Step2, SWT.NONE);
		lblListOfItems.setBounds(25, 4, 234, 15);
		formToolkit.adapt(lblListOfItems, true, true);
		lblListOfItems.setText("Check the compulsory item(s):");

		btnNext = new Button(Step2, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Collections.sort(selected_compulsory);

				try {	
					btnFinish.setEnabled(true);
					combo_selection.setEnabled(true);
					lblError_S2.setVisible(false);
					budgetPersonalAssistant.compulsory(selected_compulsory);
					double budget_left = Double.parseDouble(budgetPersonalAssistant.budgetleft());
					System.out.println("Budget left: " + budget_left);
					if(budget_left < 0) throw new Exception("***Not enough money to buy all compulsory item(s).***");
					stackLayout.topControl = Step3;
					BigContent.layout();
					budgetPersonalAssistant.differentiateCompulsory(satisfaction_choice);
					txt_result.setText(budgetPersonalAssistant.findOptimalShopList(type_choice, satisfaction_choice));	

					btnStepInputDetails.setEnabled(true);
					btnStepSelectCompulsoy.setEnabled(true);
					btnStepConfirmResult.setEnabled(false);

					combo_selection.removeAll();
					for(int i=1; i<=budgetPersonalAssistant.noOfCombination(); i++) {
						combo_selection.add("Combination " + i);
					}

					if(budgetPersonalAssistant.noOfCombination() > 0) {
						combo_selection.select(0);
					}
					else {
						combo_selection.setEnabled(false);
						btnFinish.setEnabled(false);
					}

				} catch(Exception ie) {
					lblError_S2.setText(ie.getMessage());
					lblError_S2.setVisible(true);
				} catch(OutOfMemoryError outofmemo)
				{
					combo_selection.setEnabled(false);
					btnFinish.setEnabled(false);
					txt_result.setText("There is not enough memory space to perform the task.\n"
							+ "Please restart the program and reduce your input size.\n");
					btnStepInputDetails.setEnabled(false);
					btnStepSelectCompulsoy.setEnabled(false);
					btnStepConfirmResult.setEnabled(false);
				}
			}
		});
		btnNext.setBounds(595, 232, 75, 25);
		formToolkit.adapt(btnNext, true, true);
		btnNext.setText("Next");

		Step3 = new Composite(BigContent, SWT.NONE);
		Step3.setBounds(0, 80, 700, 300);
		formToolkit.adapt(Step3);
		formToolkit.paintBordersFor(Step3);

		txt_result = new Text(Step3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txt_result.setEditable(false);
		txt_result.setBounds(10, 32, 664, 182);
		formToolkit.adapt(txt_result, true, true);

		combo_selection = new Combo(Step3, SWT.NONE);
		combo_selection.setBounds(448, 236, 126, 23);
		formToolkit.adapt(combo_selection);
		formToolkit.paintBordersFor(combo_selection);

		btnFinish = new Button(Step3, SWT.NONE);
		btnFinish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int option = combo_selection.getSelectionIndex();
				budgetPersonalAssistant.sendDBList(option);
				
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

	
}
