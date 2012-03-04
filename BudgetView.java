import java.util.Collections;
import java.util.Vector;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;

public class BudgetView extends Composite {
	/*My declaration start here.*/
	private final StackLayout stackLayout = new StackLayout();
	private final String[] titles = { "No.", "Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t", "Price\t\t\t\t", "Satisfaction", "Type\t\t\t\t\t\t\t\t\t\t"};
	private BudgetInterface budgetPersonalAssistant;
	private int event_id;
	private Vector<Item> item_list;
	private Vector<Integer> selected_compulsory;
	private int budget;
	private int type_choice;
	private int satisfaction_choice;
	/*My declaration end here.*/

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtBudget;
	private Text txt_input_list;
	private Composite type_option;
	private Button btnWithoutType;
	private Button btnWithType;
	private Composite satisfaction_option;
	private Button btnWithoutSatisfaction;
	private Button btnWithSatisfaction;
	private Button btnChange;
	private Button btnResetInputList;
	private Button btnConfirm_S1;
	private Composite budgetBtn_Composite;
	private Button btnStep;
	private Button btnStep_1;
	private Button btnStep_2;
	private Composite Step1;
	private Composite Step3;
	private Composite BigContent;
	private Text txt_error_S1;
	private Composite Step2;
	private Table table;
	private Label lblError_S2;
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
	public BudgetView(final Composite parent, int style, int id) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		event_id = id;
		
		BigContent = new Composite(this, SWT.NONE);
		BigContent.setBounds(0, 80, 700, 300);
		toolkit.adapt(BigContent);
		toolkit.paintBordersFor(BigContent);
		BigContent.setLayout(stackLayout);
		
		

		Label lblOptimizeBudgetSystem = new Label(this, SWT.NONE);
		lblOptimizeBudgetSystem.setBounds(0, 0, 130, 15);
		toolkit.adapt(lblOptimizeBudgetSystem, true, true);
		lblOptimizeBudgetSystem.setText("Optimize Budget System");

		budgetBtn_Composite = new Composite(this, SWT.NONE);
		budgetBtn_Composite.setBounds(0, 21, 613, 48);
		toolkit.adapt(budgetBtn_Composite);
		toolkit.paintBordersFor(budgetBtn_Composite);

		btnStep = new Button(budgetBtn_Composite, SWT.NONE);
		btnStep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = Step1;
				BigContent.layout();
				btnStep.setEnabled(false);
				btnStep_1.setEnabled(false);
				btnStep_2.setEnabled(false);
			}
		});
		btnStep.setLocation(10, 10);
		btnStep.setSize(175, 25);
		btnStep.setEnabled(false);
		toolkit.adapt(btnStep, true, true);
		btnStep.setText("Step 1: Input Details");

		btnStep_1 = new Button(budgetBtn_Composite, SWT.NONE);
		btnStep_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = Step2;
				BigContent.layout();
				btnStep.setEnabled(true);
				btnStep_1.setEnabled(false);
				btnStep_2.setEnabled(false);
			}
		});
		btnStep_1.setLocation(200, 10);
		btnStep_1.setSize(175, 25);
		btnStep_1.setEnabled(false);
		toolkit.adapt(btnStep_1, true, true);
		btnStep_1.setText("Step 2: Select Compulsory");

		btnStep_2 = new Button(budgetBtn_Composite, SWT.NONE);
		btnStep_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//stackLayout.topControl = Step3;
				BigContent.layout();
				btnStep.setEnabled(true);
				btnStep_1.setEnabled(true);
				btnStep_2.setEnabled(false);
			}
		});
		btnStep_2.setLocation(390, 10);
		btnStep_2.setSize(175, 25);
		btnStep_2.setEnabled(false);
		toolkit.adapt(btnStep_2, true, true);
		btnStep_2.setText("Step 3: Confirm Result");
		
		/*
		 * 
		 * 
		 * Start of Step 1 Composite*/
		Step1 = new Composite(BigContent, SWT.NONE);
		Step1.setBounds(0, 0, 700, 300);
		
		toolkit.adapt(Step1);
		toolkit.paintBordersFor(Step1);

		Label lblEventBudget = new Label(Step1, SWT.NONE);
		lblEventBudget.setBounds(10, 10, 82, 15);
		toolkit.adapt(lblEventBudget, true, true);
		lblEventBudget.setText("Event Budget: $");

		satisfaction_option = new Composite(Step1, SWT.NONE);
		satisfaction_option.setBounds(10, 103, 148, 59);
		toolkit.adapt(satisfaction_option);
		toolkit.paintBordersFor(satisfaction_option);

		btnWithSatisfaction = new Button(satisfaction_option, SWT.RADIO);
		btnWithSatisfaction.setBounds(10, 38, 115, 16);
		toolkit.adapt(btnWithSatisfaction, true, true);
		btnWithSatisfaction.setText("With Satisfaction");

		btnWithoutSatisfaction = new Button(satisfaction_option, SWT.RADIO);
		btnWithoutSatisfaction.setSelection(true);
		btnWithoutSatisfaction.setBounds(10, 16, 128, 16);
		toolkit.adapt(btnWithoutSatisfaction, true, true);
		btnWithoutSatisfaction.setText("Without Satisfaction");

		Label lblSelectSatisfactionOption = new Label(satisfaction_option, SWT.NONE);
		lblSelectSatisfactionOption.setSize(148, 15);
		toolkit.adapt(lblSelectSatisfactionOption, true, true);
		lblSelectSatisfactionOption.setText("Select Satisfaction Option:");

		type_option = new Composite(Step1, SWT.NONE);
		type_option.setBounds(10, 31, 148, 66);
		toolkit.adapt(type_option);
		toolkit.paintBordersFor(type_option);

		btnWithoutType = new Button(type_option, SWT.RADIO);
		btnWithoutType.setLocation(10, 20);
		btnWithoutType.setSize(90, 16);
		btnWithoutType.setSelection(true);
		toolkit.adapt(btnWithoutType, true, true);
		btnWithoutType.setText("Without Type");

		btnWithType = new Button(type_option, SWT.RADIO);
		btnWithType.setLocation(10, 43);
		btnWithType.setSize(90, 16);
		toolkit.adapt(btnWithType, true, true);
		btnWithType.setText("With Type");

		Label lblSelectTypeOption = new Label(type_option, SWT.NONE);
		lblSelectTypeOption.setSize(128, 15);
		toolkit.adapt(lblSelectTypeOption, true, true);
		lblSelectTypeOption.setText("Select Type Option:");

		btnChange = new Button(Step1, SWT.NONE);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtBudget.setEditable(true);
				btnWithoutType.setEnabled(true);
				btnWithType.setEnabled(true);
				btnWithoutSatisfaction.setEnabled(true);
				btnWithSatisfaction.setEnabled(true);
				btnConfirm_S1.setEnabled(true);
				txt_input_list.setEnabled(false);
				btnResetInputList.setEnabled(false);
				txt_error_S1.setVisible(false);
			}
		});
		btnChange.setBounds(10, 168, 75, 25);
		toolkit.adapt(btnChange, true, true);
		btnChange.setText("Change");

		btnConfirm_S1 = new Button(Step1, SWT.NONE);
		btnConfirm_S1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try 
				{
					budget = (int) (Double.parseDouble(txtBudget.getText().toString()) * 100);

					/*Disable all option on the left*/
					txtBudget.setEditable(false);
					btnWithoutType.setEnabled(false);
					btnWithType.setEnabled(false);
					btnWithoutSatisfaction.setEnabled(false);
					btnWithSatisfaction.setEnabled(false);
					btnConfirm_S1.setEnabled(false);

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
					btnConfirm_S1.setEnabled(true);
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
		btnConfirm_S1.setBounds(108, 168, 75, 25);
		toolkit.adapt(btnConfirm_S1, true, true);
		btnConfirm_S1.setText("Confirm");

		txtBudget = new Text(Step1, SWT.BORDER);
		txtBudget.setBounds(96, 4, 76, 21);
		toolkit.adapt(txtBudget, true, true);

		txt_input_list = new Text(Step1, SWT.BORDER | SWT.MULTI);
		txt_input_list.setEnabled(false);
		txt_input_list.setBounds(229, 55, 461, 209);
		toolkit.adapt(txt_input_list, true, true);

		Label lblInputList = new Label(Step1, SWT.NONE);
		lblInputList.setBounds(229, 34, 55, 15);
		toolkit.adapt(lblInputList, true, true);
		lblInputList.setText("Input List:");

		btnResetInputList = new Button(Step1, SWT.NONE);
		btnResetInputList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txt_input_list.setText("");
			}
		});
		btnResetInputList.setEnabled(false);
		btnResetInputList.setBounds(455, 275, 128, 25);
		toolkit.adapt(btnResetInputList, true, true);
		btnResetInputList.setText("Reset Input List");

		btnConfirm_S1 = new Button(Step1, SWT.NONE);
		btnConfirm_S1.setBounds(615, 275, 75, 25);
		btnConfirm_S1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				selected_compulsory = new Vector<Integer>();
				
				try {	
					budgetPersonalAssistant  = new BudgetController(txt_input_list.getText(), budget, type_choice, satisfaction_choice, event_id);
					item_list = budgetPersonalAssistant.getItemList(event_id);
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
					btnStep.setEnabled(true);	
				}
				catch (Exception ie) {
					txt_error_S1.setText(ie.getMessage());
				}
			}
		});

		btnConfirm_S1.setEnabled(false);
		toolkit.adapt(btnConfirm_S1, true, true);
		btnConfirm_S1.setText("Confirm");

		txt_error_S1 = new Text(Step1, SWT.READ_ONLY | SWT.MULTI);
		txt_error_S1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txt_error_S1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txt_error_S1.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		txt_error_S1.setBounds(229, -1, 461, 34);
		toolkit.adapt(txt_error_S1, true, true);

		/*
		 * 
		 * 
		 * End of Step 1 Composite*/

		/*
		 * 
		 * 
		 * Start of Step 2 Composite*/
		Step2 = new Composite(BigContent, SWT.NONE);
		Step2.setBounds(0, 80, 700, 300);
		toolkit.adapt(Step2);
		toolkit.paintBordersFor(Step2);
		
		table = new Table(Step2, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);			
		table.setHeaderVisible(true);
		
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[loopIndex]);
			column.setResizable(false);
		}
		
		table.setBounds(25, 25, 665, 200);
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
		lblError_S2.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblError_S2.setBounds(25, 260, 555, 28);
		toolkit.adapt(lblError_S2, true, true);
		
		lblListOfItems = new Label(Step2, SWT.NONE);
		lblListOfItems.setBounds(25, 4, 234, 15);
		toolkit.adapt(lblListOfItems, true, true);
		lblListOfItems.setText("Check the compulsory item(s):");
		
		btnNext = new Button(Step2, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String t ="";
				Collections.sort(selected_compulsory);
				for(int i=0; i<selected_compulsory.size();i++) {
					t+=selected_compulsory.get(i) + "  ";
				}
				label.setText(t);
				try {	
					lblError_S2.setVisible(false);
					budgetPersonalAssistant.compulsory(selected_compulsory);
					double budget_left = Double.parseDouble(budgetPersonalAssistant.budgetleft());
					System.out.println("Budget left: " + budget_left);
					if(budget_left < 0) throw new Exception("***Not enough money to buy all compulsory item(s).***");
					//boolean empty = budgetPersonalAssistant.emptyComputeList();
					//if(empty == true) throw new Exception("***You have selected all item(s) and you can buy all item(s).***");
					stackLayout.topControl = Step3;
					BigContent.layout();
					budgetPersonalAssistant.differentiateCompulsory();
					txt_result.setText(budgetPersonalAssistant.findOptimalShopList(type_choice, satisfaction_choice));	
					btnStep.setEnabled(true);
					btnStep_1.setEnabled(true);
					btnStep_2.setEnabled(false);
					
					combo_selection.removeAll();
					for(int i=1; i<=budgetPersonalAssistant.noOfCombination(); i++) {
						combo_selection.add("Combination " + i);
					}
					
				} catch(Exception ie) {
					lblError_S2.setText(ie.getMessage());
					lblError_S2.setVisible(true);
				}		
			}
		});
		btnNext.setBounds(615, 265, 75, 25);
		toolkit.adapt(btnNext, true, true);
		btnNext.setText("Next");
		
		/*
		 * 
		 * End of Step2
		 */
		
		/*
		 * 
		 * Start of Step3
		 */
		
		Step3 = new Composite(BigContent, SWT.NONE);
		Step3.setBounds(0, 80, 700, 300);
		toolkit.adapt(Step3);
		toolkit.paintBordersFor(Step3);
		
		txt_result = new Text(Step3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txt_result.setBounds(10, 41, 664, 173);
		toolkit.adapt(txt_result, true, true);
		
		combo_selection = new Combo(Step3, SWT.NONE);
		combo_selection.setBounds(486, 246, 91, 23);
		toolkit.adapt(combo_selection);
		toolkit.paintBordersFor(combo_selection);
		
		btnFinish = new Button(Step3, SWT.NONE);
		btnFinish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int option = combo_selection.getSelectionIndex();
				budgetPersonalAssistant.sendDBList(option);
				//txtDB.setText(budgetPersonalAssistant.sendDBList(option));
			}
		});
		btnFinish.setBounds(599, 244, 75, 25);
		toolkit.adapt(btnFinish, true, true);
		btnFinish.setText("Finish");
		
		Label lblSelectAnCombination = new Label(Step3, SWT.NONE);
		lblSelectAnCombination.setBounds(359, 249, 121, 15);
		toolkit.adapt(lblSelectAnCombination, true, true);
		lblSelectAnCombination.setText("Select an combination:");
		
		label = new Label(this, SWT.NONE);
		label.setBounds(311, 78, 202, 15);
		toolkit.adapt(label, true, true);
		
		/*
		 * 
		 * End of Step3
		 */
		
	}
	
	public static void main(String[] args)
	{
		Display display = new Display();
		
		Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE);
		shell.setText("Budget Calc");
		BudgetView budView = new BudgetView(shell, SWT.NONE, 0);
		budView.pack();
		
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch()) display.sleep();
		}
		
	}
}