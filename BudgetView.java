import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;


public class BudgetView extends ApplicationWindow {
	/*My declaration start here.*/
	private IBudgetAssistant budgetPersonalAssistant;
	private int budget;
	private int choice;
	/*My declaration end here.*/

	private Text textInputBudget;
	private Text Input_Field;
	private Button btnWithType;
	private Button btnWithoutType;
	private Button btnStep_1;
	private Composite Step2;
	private Composite Step1;
	private Button btnNext;
	private Label lblErrorMsg;
	private Text Output_Table;
	private Text txtInput_Compulsory;
	private Composite Step3;
	private Text Output_confirm;
	private Text Output_Left;
	private Text txtBudgetLeft;
	private Label lblbudgetIsNot;
	private Button btnGenerate;
	private Composite Step4;
	private Text OutputPermu;
	private Button btnConfirm_Select;
	private Text txtDB;
	private Combo combo;
	private Button btnFinalConfirm;



	/**
	 * Create the application window.
	 */
	public BudgetView() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			Step1 = new Composite(container, SWT.NONE);
			Step1.setBounds(0, 21, 561, 214);
			{
				Label lblEventBudget = new Label(Step1, SWT.NONE);
				lblEventBudget.setBounds(10, 51, 78, 15);
				lblEventBudget.setText("Event Budget $");
			}

			textInputBudget = new Text(Step1, SWT.BORDER);
			textInputBudget.setBounds(94, 48, 90, 21);

			Label lblNewLabel = new Label(Step1, SWT.NONE);
			lblNewLabel.setBounds(10, 72, 70, 15);
			lblNewLabel.setText("Please Select:");

			Composite composite_1 = new Composite(Step1, SWT.NONE);
			composite_1.setBounds(94, 72, 90, 38);

			btnWithoutType = new Button(composite_1, SWT.RADIO);
			btnWithoutType.setSelection(true);
			btnWithoutType.setBounds(0, 0, 90, 16);
			btnWithoutType.setText("Without Type");

			btnWithType = new Button(composite_1, SWT.RADIO);
			btnWithType.setBounds(0, 22, 90, 16);
			btnWithType.setText("With Type");

			{
				Input_Field = new Text(Step1, SWT.BORDER | SWT.MULTI);
				Input_Field.setEditable(false);
				Input_Field.setBounds(271, 35, 280, 143);
			}

			{
				Button btnStep = new Button(Step1, SWT.NONE);
				btnStep.setBounds(10, 4, 75, 25);
				btnStep.setEnabled(false);
				btnStep.setText("Step 1");
			}

			{
				lblErrorMsg = new Label(Step1, SWT.NONE);
				lblErrorMsg.setBounds(10, 127, 255, 15);
				lblErrorMsg.setVisible(false);
			}

			{
				btnStep_1 = new Button(Step1, SWT.NONE);
				btnStep_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(true);
						Step3.setVisible(false);
						Step4.setVisible(false);
					}
				});
				btnStep_1.setBounds(94, 4, 75, 25);
				btnStep_1.setText("Step 2");
			}
			{
				Button btnStep_2 = new Button(Step1, SWT.NONE);
				btnStep_2.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(true);
						Step4.setVisible(false);
					}
				});
				btnStep_2.setBounds(190, 4, 75, 25);
				btnStep_2.setText("Step 3");
			}
			{
				Button btnStep_3 = new Button(Step1, SWT.NONE);
				btnStep_3.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(false);
						Step4.setVisible(true);

					}
				});
				btnStep_3.setBounds(281, 4, 75, 25);
				btnStep_3.setText("Step 4");
			}
		}

		{
			btnNext = new Button(Step1, SWT.NONE);
			btnNext.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						Step1.setVisible(false);
						Step2.setVisible(true);
						Step3.setVisible(false);
						Step4.setVisible(false);
						budgetPersonalAssistant  = new BudgetController(Input_Field.getText(), budget, choice);
						Output_Table.setText(budgetPersonalAssistant.displayList());
					}
					catch (Exception ie) {
						Input_Field.setText(ie.getMessage());
					}

				}
			});
			btnNext.setEnabled(false);
			btnNext.setBounds(476, 179, 75, 25);
			btnNext.setText("Next");
		}

		Button btnChange = new Button(Step1, SWT.NONE);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textInputBudget.setEditable(true);
				btnWithoutType.setEnabled(true);
				btnWithType.setEnabled(true);
				Input_Field.setEditable(false);
				btnNext.setEnabled(false);
				lblErrorMsg.setVisible(false);
			}
		});
		btnChange.setBounds(190, 47, 75, 25);
		btnChange.setText("Change");
		{
			Button btnConfirm = new Button(Step1, SWT.NONE);
			btnConfirm.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try 
					{
						textInputBudget.setEditable(false);
						btnWithoutType.setEnabled(false);
						btnWithType.setEnabled(false);

						budget = (int) (Double.parseDouble(textInputBudget.getText().toString()) * 100);
						if(btnWithoutType.getSelection() == true)
							choice = 0;
						else if(btnWithType.getSelection() == true)
							choice = 1;					
						Input_Field.setEditable(true);
						btnNext.setEnabled(true);

					}
					catch (Exception ie) {
						if(ie.getMessage().equals("empty String")) 
							lblErrorMsg.setText("***Please input an event budget.***");
						else
							lblErrorMsg.setText("***Event Budget can only be numeric.***");
						lblErrorMsg.setVisible(true);
					}

				}
			});
			btnConfirm.setBounds(190, 85, 75, 25);
			btnConfirm.setText("Confirm");
		}
		{
			Button btnClear = new Button(Step1, SWT.NONE);
			btnClear.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Input_Field.setText("");
				}
			});
			btnClear.setBounds(382, 179, 75, 25);
			btnClear.setText("Clear");
		}


		/* End of Step 1 Composite
		 * 
		 * 
		 * 
		 */
		{
			Step2 = new Composite(container, SWT.NONE);
			Step2.setBounds(0, 26, 561, 219);
			//Step2.setVisible(false);
			{
				Button btnStep2_1 = new Button(Step2, SWT.NONE);
				btnStep2_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(true);
						Step2.setVisible(false);
						Step3.setVisible(false);
						Step4.setVisible(false);
					}
				});
				btnStep2_1.setText("Step 1");
				btnStep2_1.setBounds(10, 10, 75, 25);
			}
			{
				Button btnStep2_2 = new Button(Step2, SWT.NONE);
				btnStep2_2.setEnabled(false);
				btnStep2_2.setText("Step 2");
				btnStep2_2.setBounds(94, 10, 75, 25);
			}
			{
				Button btnStep2_3 = new Button(Step2, SWT.NONE);
				btnStep2_3.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(true);
						Step4.setVisible(false);
					}
				});
				btnStep2_3.setText("Step 3");
				btnStep2_3.setBounds(190, 10, 75, 25);
			}
			{
				Button btnStep2_4 = new Button(Step2, SWT.CENTER);
				btnStep2_4.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(false);
						Step4.setVisible(true);
					}
				});
				btnStep2_4.setBounds(291, 10, 75, 25);
				btnStep2_4.setText("Step 4");
			}
			{
				Label lblTableOutput = new Label(Step2, SWT.NONE);
				lblTableOutput.setBounds(10, 54, 82, 15);
				lblTableOutput.setText("Table Output:");
			}
			{
				Output_Table = new Text(Step2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
				Output_Table.setEditable(false);
				Output_Table.setBounds(9, 70, 398, 139);

			}
			{
				Label lblCompulsoryItem = new Label(Step2, SWT.NONE);
				lblCompulsoryItem.setBounds(431, 54, 103, 15);
				lblCompulsoryItem.setText("Compulsory Item:");
			}
			{
				txtInput_Compulsory = new Text(Step2, SWT.BORDER);
				txtInput_Compulsory.setEditable(false);
				txtInput_Compulsory.setBounds(432, 70, 119, 21);
			}
			{
				Button btnChange_1 = new Button(Step2, SWT.NONE);
				btnChange_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						txtInput_Compulsory.setEditable(true);
					}
				});
				btnChange_1.setBounds(431, 97, 75, 25);
				btnChange_1.setText("Change");
			}
			{
				Button btnNext_1 = new Button(Step2, SWT.NONE);
				btnNext_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						txtInput_Compulsory.setEditable(false);
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(true);
						Step4.setVisible(false);
						budgetPersonalAssistant.compulsory(txtInput_Compulsory.getText());

						Output_confirm.setText(budgetPersonalAssistant.displaySplit(1)); //1 denote compulsory display
						double budget_left = Double.parseDouble(budgetPersonalAssistant.budgetleft());
						txtBudgetLeft.setText(budgetPersonalAssistant.budgetleft());
						Output_Left.setText(budgetPersonalAssistant.displaySplit(0));
						if(budget_left < 0) {
							lblbudgetIsNot.setVisible(true);
							btnGenerate.setEnabled(false);
						}
						else {
							lblbudgetIsNot.setVisible(false); //budget is enough, don't have to display error message.
							
							if(Output_Left.getText().equals("") == false) 
								btnGenerate.setEnabled(true); //have items left to generate
							else
								btnGenerate.setEnabled(false); //no items left to generate so disable the button
						}



					}
				});
				btnNext_1.setBounds(431, 128, 75, 25);
				btnNext_1.setText("Next");
			}

		}

		/* End of Step 2 Composite
		 * 
		 * 
		 * 
		 */

		{
			Label lblOptimizeBudget = new Label(container, SWT.NONE);
			lblOptimizeBudget.setBounds(10, 5, 159, 15);
			lblOptimizeBudget.setText("Optimize Budget");
		}

		{
			Step3 = new Composite(container, SWT.NONE);
			Step3.setBounds(0, 26, 561, 219);
			//Step3.setVisible(false);
			{
				Button btnStep3_3 = new Button(Step3, SWT.NONE);
				btnStep3_3.setEnabled(false);
				btnStep3_3.setText("Step 3");
				btnStep3_3.setBounds(190, 10, 75, 25);
			}
			{
				Button btnStep3_2 = new Button(Step3, SWT.NONE);
				btnStep3_2.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(true);
						Step3.setVisible(false);
						Step4.setVisible(false);
					}
				});
				btnStep3_2.setText("Step 2");
				btnStep3_2.setBounds(94, 10, 75, 25);
			}
			{
				Button btnStep3_1 = new Button(Step3, SWT.NONE);
				btnStep3_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(true);
						Step2.setVisible(false);
						Step3.setVisible(false);
						Step4.setVisible(false);
					}
				});
				btnStep3_1.setText("Step 1");
				btnStep3_1.setBounds(10, 10, 75, 25);
			}
			{
				Button btnStep3_4 = new Button(Step3, SWT.NONE);
				btnStep3_4.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Step1.setVisible(false);
						Step2.setVisible(false);
						Step3.setVisible(false);
						Step4.setVisible(true);
					}
				});
				btnStep3_4.setBounds(300, 10, 75, 25);
				btnStep3_4.setText("Step 4");
			}
			{
				Label lblConfirmedItems = new Label(Step3, SWT.NONE);
				lblConfirmedItems.setBounds(10, 59, 97, 15);
				lblConfirmedItems.setText("Confirmed Item(s):");
			}
			{
				Output_confirm = new Text(Step3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
				Output_confirm.setEditable(false);
				Output_confirm.setBounds(10, 80, 250, 130);
			}
			{
				Label lblBudgetLeft = new Label(Step3, SWT.NONE);
				lblBudgetLeft.setBounds(10, 41, 97, 15);
				lblBudgetLeft.setText("Budget Left:        $");
			}
			{
				Label lblItemsLeft = new Label(Step3, SWT.NONE);
				lblItemsLeft.setBounds(285, 59, 67, 15);
				lblItemsLeft.setText("Item(s) Left:");
			}
			{
				Output_Left = new Text(Step3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
				Output_Left.setEditable(false);
				Output_Left.setBounds(286, 80, 250, 130);
			}
			{
				txtBudgetLeft = new Text(Step3, SWT.BORDER);
				txtBudgetLeft.setEditable(false);
				txtBudgetLeft.setBounds(113, 41, 76, 21);
			}
			{
				btnGenerate = new Button(Step3, SWT.NONE);
				btnGenerate.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							Step1.setVisible(false);
							Step2.setVisible(false);
							Step3.setVisible(false);
							Step4.setVisible(true);
							OutputPermu.setText(budgetPersonalAssistant.findOptimalShopList(choice));
							if(budgetPersonalAssistant.noOfCombination() == 0) { //not enough money
								combo.setEnabled(false);
								btnConfirm_Select.setEnabled(false);
							}
							else {
								combo.setEnabled(true);
								combo.removeAll();
								btnConfirm_Select.setEnabled(true);
								for(int i=1; i<=budgetPersonalAssistant.noOfCombination(); i++) {
									combo.add("Combination " + i);
								}
							}
						
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							OutputPermu.setText(e1.getMessage());
						}
					}
				});
				btnGenerate.setEnabled(false);
				btnGenerate.setBounds(461, 31, 75, 25);
				btnGenerate.setText("Generate");
			}
			{
				lblbudgetIsNot = new Label(Step3, SWT.NONE);
				lblbudgetIsNot.setBounds(200, 41, 255, 15);
				lblbudgetIsNot.setText("*Budget is not enough! You can't generate!*");
				lblbudgetIsNot.setVisible(false);
			}

		}

		/* End of Step 3 Composite
		 * 
		 * 
		 * 
		 */
	

		{
			Step4 = new Composite(container, SWT.NONE);
			Step4.setBounds(0, 0, 561, 245);
		}
		{
			Button btnStep4_3 = new Button(Step4, SWT.NONE);
			btnStep4_3.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Step1.setVisible(false);
					Step2.setVisible(false);
					Step3.setVisible(true);
					Step4.setVisible(false);
				}
			});
			btnStep4_3.setText("Step 3");
			btnStep4_3.setBounds(190, 10, 75, 25);
		}
		{
			Button btnStep4_2 = new Button(Step4, SWT.NONE);
			btnStep4_2.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Step1.setVisible(false);
					Step2.setVisible(true);
					Step3.setVisible(false);
					Step4.setVisible(false);
				}
			});
			btnStep4_2.setText("Step 2");
			btnStep4_2.setBounds(94, 10, 75, 25);
		}
		{
			Button btnStep4_1 = new Button(Step4, SWT.NONE);
			btnStep4_1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Step1.setVisible(true);
					Step2.setVisible(false);
					Step3.setVisible(false);
					Step4.setVisible(false);
				}
			});
			btnStep4_1.setText("Step 1");
			btnStep4_1.setBounds(10, 10, 75, 25);
		}
		{
			Button btnStep4_4 = new Button(Step4, SWT.NONE);
			btnStep4_4.setEnabled(false);
			btnStep4_4.setBounds(300, 10, 75, 25);
			btnStep4_4.setText("Step 4");
		}
		{
			Label lblGeneratedOutput = new Label(Step4, SWT.NONE);
			lblGeneratedOutput.setBounds(10, 49, 103, 15);
			lblGeneratedOutput.setText("Generated Output:");
		}
		{
			OutputPermu = new Text(Step4, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
			OutputPermu.setEditable(false);
			OutputPermu.setBounds(10, 70, 197, 165);
		}
		{
			Label lblSelectedCombination = new Label(Step4, SWT.NONE);
			lblSelectedCombination.setBounds(232, 49, 120, 15);
			lblSelectedCombination.setText("Selected Combination:");
		}
		{
			btnConfirm_Select = new Button(Step4, SWT.NONE);
			btnConfirm_Select.setEnabled(false);
			btnConfirm_Select.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					btnFinalConfirm.setEnabled(true);
					int option = combo.getSelectionIndex();
					txtDB.setText(budgetPersonalAssistant.sendDBList(option));
				}
			});
			btnConfirm_Select.setBounds(460, 39, 75, 25);
			btnConfirm_Select.setText("Select");
		}
		{
			Label lblSelectedCombination_1 = new Label(Step4, SWT.NONE);
			lblSelectedCombination_1.setBounds(232, 82, 126, 15);
			lblSelectedCombination_1.setText("Final Combination:");
		}
		{
			txtDB = new Text(Step4, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
			txtDB.setEditable(false);
			txtDB.setBounds(232, 103, 208, 132);
		}
		{
			btnFinalConfirm = new Button(Step4, SWT.NONE);
			btnFinalConfirm.setEnabled(false);
			btnFinalConfirm.setBounds(460, 210, 75, 25);
			btnFinalConfirm.setText("Confirm");
		}
		
		combo = new Combo(Step4, SWT.NONE);
		combo.setBounds(352, 46, 91, 23);

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			BudgetView window = new BudgetView();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(577, 356);
	}
}
