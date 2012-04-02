package budget;

import event.*;
import dialog.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

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
import au.com.bytecode.opencsv.CSVWriter;

import com.ibm.icu.text.Collator;

/**
 * 
 * @author Chua Hong Jing
 *
 */
public class ViewBudget extends Composite {
	/*My declaration start here.*/
	private final StackLayout stackLayout = new StackLayout();
	private ControllerBudget budgetPersonalAssistant;
	private Eventitem currentEvent;
	private Vector<Item> itemList;
	private Vector<Integer> selectedCompulsoryIndexList = new Vector<Integer>();;
	private int budget;
	private int typeChoice;
	private int satisfactionChoice;
	protected errormessageDialog errordiag;
	/*My declaration end here.*/

	private Composite compBigContent;
	private Button btnStepInputDetails;
	private Button btnStepSelectCompulsoy;
	private Button btnStepConfirmResult;
	private Composite compStep1;
	private Composite compStep2;
	private Composite compStep3;
	private Composite compTypeOption;
	private Composite compSatisfactionOption;
	private Text txtBudget;
	private Text txtInputList;
	private Button btnWithoutType;
	private Button btnWithType;
	private Button btnWithoutSatisfaction;
	private Button btnWithSatisfaction;
	private Button btnResetInputList;
	private Button btnConfirm;
	private Table tableListItem;
	private Combo dropdownSelection;
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
	public ViewBudget(Composite parent, int style, Eventitem inputEventitem) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});

		currentEvent = inputEventitem;

		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Form ViewBudgetForm = formToolkit.createForm(this);
		ViewBudgetForm.setBounds(0, 0, 700, 400);
		ViewBudgetForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(ViewBudgetForm);
		ViewBudgetForm.setText("Budget Optimization");
		ViewBudgetForm.getBody().setLayout(new FormLayout());

		/**********************************************************************************
		 * Overview Budget optimization composite 
		 **********************************************************************************/
		Composite compMain = new Composite(ViewBudgetForm.getBody(), SWT.NONE);
		FormData formDataMain = new FormData();
		formDataMain.top = new FormAttachment(50, -160);
		formDataMain.bottom = new FormAttachment (50, 180);
		formDataMain.left = new FormAttachment(50, -350);
		formDataMain.right = new FormAttachment(50, 350);
		compMain.setLayoutData(formDataMain);
		formToolkit.adapt(compMain);
		formToolkit.paintBordersFor(compMain);

		/**********************************************************************************
		 * Composite that will display the content 
		 **********************************************************************************/
		compBigContent = new Composite(compMain, SWT.NONE);
		compBigContent.setBounds(10, 76, 680, 264);
		formToolkit.adapt(compBigContent);
		formToolkit.paintBordersFor(compBigContent);
		compBigContent.setLayout(stackLayout);

		/**********************************************************************************
		 * Composite that will display the global navigation button
		 **********************************************************************************/
		Composite compButtonComposite = new Composite(compMain, SWT.NONE);
		compButtonComposite.setBounds(10, 10, 680, 57);
		formToolkit.adapt(compButtonComposite);
		formToolkit.paintBordersFor(compButtonComposite);

		btnStepInputDetails = new Button(compButtonComposite, SWT.NONE);
		btnStepInputDetails.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = compStep1;
				compBigContent.layout();
				btnStepInputDetails.setEnabled(false);
				btnStepSelectCompulsoy.setEnabled(false);
				btnStepConfirmResult.setEnabled(false);
			}
		});
		btnStepInputDetails.setEnabled(false);
		btnStepInputDetails.setBounds(0, 10, 170, 25);
		formToolkit.adapt(btnStepInputDetails, true, true);
		btnStepInputDetails.setText("Step1: Input Details");

		btnStepSelectCompulsoy = new Button(compButtonComposite, SWT.NONE);
		btnStepSelectCompulsoy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = compStep2;
				compBigContent.layout();
				btnStepInputDetails.setEnabled(true);
				btnStepSelectCompulsoy.setEnabled(false);
				btnStepConfirmResult.setEnabled(false);
			}
		});
		btnStepSelectCompulsoy.setEnabled(false);
		btnStepSelectCompulsoy.setBounds(255, 10, 170, 25);
		formToolkit.adapt(btnStepSelectCompulsoy, true, true);
		btnStepSelectCompulsoy.setText("Step2: Select Compulsoy");

		btnStepConfirmResult = new Button(compButtonComposite, SWT.NONE);
		btnStepConfirmResult.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = compStep3;
				compBigContent.layout();
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
		compStep1 = new Composite(compBigContent, SWT.NONE);
		compStep1.setBounds(0, 0, 675, 260);
		formToolkit.adapt(compStep1);
		formToolkit.paintBordersFor(compStep1);

		Label lblEventBudget = new Label(compStep1, SWT.NONE);
		lblEventBudget.setBounds(10, 10, 82, 15);
		formToolkit.adapt(lblEventBudget, true, true);
		lblEventBudget.setText("Event Budget: $");

		compSatisfactionOption = new Composite(compStep1, SWT.NONE);
		compSatisfactionOption.setBounds(10, 103, 148, 59);
		formToolkit.adapt(compSatisfactionOption);
		formToolkit.paintBordersFor(compSatisfactionOption);

		btnWithSatisfaction = new Button(compSatisfactionOption, SWT.RADIO);
		btnWithSatisfaction.setBounds(10, 38, 115, 16);
		formToolkit.adapt(btnWithSatisfaction, true, true);
		btnWithSatisfaction.setText("With Satisfaction");

		btnWithoutSatisfaction = new Button(compSatisfactionOption, SWT.RADIO);
		btnWithoutSatisfaction.setSelection(true);
		btnWithoutSatisfaction.setBounds(10, 16, 128, 16);
		formToolkit.adapt(btnWithoutSatisfaction, true, true);
		btnWithoutSatisfaction.setText("Without Satisfaction");

		Label lblSelectSatisfactionOption = new Label(compSatisfactionOption, SWT.NONE);
		lblSelectSatisfactionOption.setSize(148, 15);
		formToolkit.adapt(lblSelectSatisfactionOption, true, true);
		lblSelectSatisfactionOption.setText("Select Satisfaction Option:");

		compTypeOption = new Composite(compStep1, SWT.NONE);
		compTypeOption.setBounds(10, 31, 148, 66);
		formToolkit.adapt(compTypeOption);
		formToolkit.paintBordersFor(compTypeOption);

		btnWithoutType = new Button(compTypeOption, SWT.RADIO);
		btnWithoutType.setLocation(10, 20);
		btnWithoutType.setSize(90, 16);
		btnWithoutType.setSelection(true);
		formToolkit.adapt(btnWithoutType, true, true);
		btnWithoutType.setText("Without Type");

		btnWithType = new Button(compTypeOption, SWT.RADIO);
		btnWithType.setLocation(10, 43);
		btnWithType.setSize(90, 16);
		formToolkit.adapt(btnWithType, true, true);
		btnWithType.setText("With Type");

		Label lblSelectTypeOption = new Label(compTypeOption, SWT.NONE);
		lblSelectTypeOption.setSize(128, 15);
		formToolkit.adapt(lblSelectTypeOption, true, true);
		lblSelectTypeOption.setText("Select Type Option:");

		txtBudget = new Text(compStep1, SWT.BORDER);
		txtBudget.setBounds(96, 4, 76, 21);
		formToolkit.adapt(txtBudget, true, true);

		txtInputList = new Text(compStep1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtInputList.setBounds(229, 55, 441, 129);
		formToolkit.adapt(txtInputList, true, true);

		Label lblInputList = new Label(compStep1, SWT.NONE);
		lblInputList.setBounds(229, 34, 441, 15);
		formToolkit.adapt(lblInputList, true, true);
		lblInputList.setText("Input format: [itemname] [price] [satisfaction] [type]");

		btnResetInputList = new Button(compStep1, SWT.NONE);
		btnResetInputList.setBounds(355, 229, 128, 25);
		btnResetInputList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				txtInputList.setText("");
			}
		});
		formToolkit.adapt(btnResetInputList, true, true);
		btnResetInputList.setText("Reset Input List");

		btnConfirm = new Button(compStep1, SWT.NONE);
		btnConfirm.setBounds(512,  229, 75, 25);
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectedCompulsoryIndexList.removeAllElements();
				try {	

					txtBudget.getText().trim();
					if(txtBudget.getText().length() == 0) throw new IOException("Please enter a budget.");

					budget = (int) (Double.parseDouble(txtBudget.getText()) * 100);

					if(btnWithoutType.getSelection() == true)
						typeChoice = 0;
					else if(btnWithType.getSelection() == true)
						typeChoice = 1;

					if(btnWithoutSatisfaction.getSelection() == true)
						satisfactionChoice = 0;
					else if(btnWithSatisfaction.getSelection() == true)
						satisfactionChoice = 1;

					if(txtInputList.getText().length() == 0) throw new IOException("Input list must not be empty.");

					budgetPersonalAssistant  = new ControllerBudget(txtInputList.getText(), budget, typeChoice, satisfactionChoice, currentEvent);
					itemList = budgetPersonalAssistant.getItemList();

					stackLayout.topControl = compStep2;
					compBigContent.layout();	

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

		btnImport = new Button(compStep1, SWT.NONE);
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

		txtDirectory = new Text(compStep1, SWT.BORDER);
		txtDirectory.setEnabled(false);
		txtDirectory.setEditable(false);
		txtDirectory.setBounds(303, 190, 286, 21);
		formToolkit.adapt(txtDirectory, true, true);

		Button btnBack = new Button(compStep1, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ReturnView();
			}
		});
		btnBack.setBounds(595, 229, 75, 25);
		formToolkit.adapt(btnBack, true, true);
		btnBack.setText("Back");
		/**********************************************************************************
		 * Composite that will that display the input by the user for he/she to select compulsory items.
		 **********************************************************************************/
		compStep2 = new Composite(compBigContent, SWT.NONE);
		compStep2.setBounds(0, 0, 675, 260);
		formToolkit.adapt(compStep2);
		formToolkit.paintBordersFor(compStep2);

		tableListItem = new Table(compStep2, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);			
		tableListItem.setHeaderVisible(true);

		final TableEditor editor = new TableEditor(tableListItem);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
		final int EDITABLECOLUMN = 5;

		tableListItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();

				// Identify the selected row
				final TableItem item = (TableItem)e.item;
				if (item == null) return;

				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(tableListItem, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent me) {
						int newPrice=0;
						Text text = (Text)editor.getEditor();
						try {
							int quantityNo = Integer.parseInt(text.getText());
							int index = Integer.parseInt(item.getText().substring(5, item.getText().length()));
							if(itemList.get(index-1).getQuantity() == 1) {
								newPrice = itemList.get(index-1).getPrice()*quantityNo;
								itemList.get(index-1).setQuantity(quantityNo);
								itemList.get(index-1).setPrice(newPrice);
								editor.getItem().setText(2, ""+"$"+((double) newPrice)/100);
							}
							else if(itemList.get(index-1).getQuantity() > 1) {
								int singleItemPrice = itemList.get(index-1).getPrice()/itemList.get(index-1).getQuantity();
								newPrice = singleItemPrice*quantityNo;
								itemList.get(index-1).setPrice(newPrice);
								itemList.get(index-1).setQuantity(quantityNo);
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

		TableColumn tableColumnNo = new TableColumn(tableListItem, SWT.NULL);
		tableColumnNo.setText("No.");
		tableColumnNo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {	
				sortColumn(0);
			}
		});

		TableColumn tableColumnItemName = new TableColumn(tableListItem, SWT.NULL);
		tableColumnItemName.setText("Item Name\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tableColumnItemName.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(1);
			}
		});

		TableColumn tableColumnPrice = new TableColumn(tableListItem, SWT.NULL);
		tableColumnPrice.setText("Price\t\t");
		tableColumnPrice.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(2);
			}
		});

		TableColumn tableColumnSatisfaction = new TableColumn(tableListItem, SWT.NULL);
		tableColumnSatisfaction.setText("Satisfaction");
		tableColumnSatisfaction.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(3);
			}
		});

		TableColumn tableColumnType = new TableColumn(tableListItem, SWT.NULL);
		tableColumnType.setText("Type\t\t\t\t\t");
		tableColumnType.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(4);
			}
		});

		TableColumn tableColumnQuantity = new TableColumn(tableListItem, SWT.NULL);
		tableColumnQuantity.setText("Quantity\t\t");
		tableColumnQuantity.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				sortColumn(5);
			}
		});

		tableListItem.setBounds(25, 25, 645, 172);
		selectedCompulsoryIndexList.removeAllElements();
		tableListItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int select = Integer.parseInt(event.item.toString().substring(16, event.item.toString().length()-1));
				if (event.detail == SWT.CHECK) {
					if(selectedCompulsoryIndexList.contains(select) == false) 
						selectedCompulsoryIndexList.add(select);
					else 
						selectedCompulsoryIndexList.removeElement(select);	
				} 
			}
		});

		lblListOfItems = new Label(compStep2, SWT.NONE);
		lblListOfItems.setBounds(25, 4, 234, 15);
		formToolkit.adapt(lblListOfItems, true, true);
		lblListOfItems.setText("Check the compulsory item(s):");

		btnNext = new Button(compStep2, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				Collections.sort(selectedCompulsoryIndexList);

				try {	
					btnFinish.setEnabled(true);
					dropdownSelection.setEnabled(true);
					budgetPersonalAssistant.differentiateCompulsory(selectedCompulsoryIndexList, satisfactionChoice);
					double budgetLeft = budgetPersonalAssistant.budgetleft();
					if(budgetLeft < 0) throw new Exception("Not enough money to buy all compulsory item(s).");
					stackLayout.topControl = compStep3;
					compBigContent.layout();

					txtResult.setText(budgetPersonalAssistant.findOptimalShopList(typeChoice, satisfactionChoice));	

					btnStepInputDetails.setEnabled(true);
					btnStepSelectCompulsoy.setEnabled(true);
					btnStepConfirmResult.setEnabled(false);

					dropdownSelection.removeAll();

					for(int i=1; i<=budgetPersonalAssistant.noOfCombination(); i++) {
						dropdownSelection.add("Combination " + i);
					}

					if(budgetPersonalAssistant.noOfCombination() > 0) {
						dropdownSelection.select(0);
					}
					else {
						dropdownSelection.setEnabled(false);
						btnFinish.setEnabled(false);
					}

				} catch(Exception ie) {
					errordiag = new errormessageDialog(new Shell(), ie.getMessage());
					errordiag.open();
				} catch(OutOfMemoryError outofmemo)
				{
					dropdownSelection.setEnabled(false);
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

		Label lblNoteAllChanges = new Label(compStep2, SWT.NONE);
		lblNoteAllChanges.setBounds(25, 213, 404, 15);
		formToolkit.adapt(lblNoteAllChanges, true, true);
		lblNoteAllChanges.setText("Note: All changes to item quantity will be lost if you click back to Step 1!");

		/**********************************************************************************
		 * Composite that will that display all the different optimize list.
		 **********************************************************************************/
		compStep3 = new Composite(compBigContent, SWT.NONE);
		compStep3.setBounds(0, 80, 700, 300);
		formToolkit.adapt(compStep3);
		formToolkit.paintBordersFor(compStep3);

		txtResult = new Text(compStep3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtResult.setEditable(false);
		txtResult.setBounds(10, 32, 664, 182);
		formToolkit.adapt(txtResult, true, true);

		dropdownSelection = new Combo(compStep3, SWT.NONE);
		dropdownSelection.setBounds(377, 236, 126, 23);
		formToolkit.adapt(dropdownSelection);
		formToolkit.paintBordersFor(dropdownSelection);

		btnFinish = new Button(compStep3, SWT.NONE);
		btnFinish.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int option = dropdownSelection.getSelectionIndex();
				budgetPersonalAssistant.saveOptimizeOption(option);	

				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);

				String input = fsd.open();
				if (input != null) {
					if (!input.toLowerCase().endsWith(".csv"))
						input+= ".csv";
					ExportCSV(input);}
				ViewMain.ReturnView();
			}
		});
		btnFinish.setBounds(530, 234, 140, 25);
		formToolkit.adapt(btnFinish, true, true);
		btnFinish.setText("Finish and Export");

		Label lblSelectAnCombination = new Label(compStep3, SWT.NONE);
		lblSelectAnCombination.setBounds(250, 239, 121, 15);
		formToolkit.adapt(lblSelectAnCombination, true, true);
		lblSelectAnCombination.setText("Select an combination:");

		Label lblListOfAll = new Label(compStep3, SWT.NONE);
		lblListOfAll.setBounds(10, 10, 249, 15);
		formToolkit.adapt(lblListOfAll, true, true);
		lblListOfAll.setText("List of all possible combination:");
	}

	/**
	 * Description: Algorithm to sort the selected column.
	 * @param columnNo
	 */

	public void sortColumn(int columnNo) {

		selectedCompulsoryIndexList.removeAllElements();
		TableItem[] items = tableListItem.getItems();
		Collator collator = Collator.getInstance(Locale.getDefault());
		int col0Value1=0, col0Value2=0, col3Value1=0, col3Value2=0,col5Value1=0, col5Value2=0;
		double col2Value1=0, col2Value2=0;
		String col1Value1="", col1Value2="", col4Value1="", col4Value2="";
		boolean compareCorrect = false;
		for (int i = 1; i<items.length; i++) {
			if(columnNo == 0) 
				col0Value1 = Integer.parseInt(items[i].getText(0).substring(5, items[i].getText(0).length()));
			else if (columnNo == 1) 
				col1Value1 = items[i].getText(1);
			else if (columnNo == 2)
				col2Value1 = Double.parseDouble(items[i].getText(2).substring(1,items[i].getText(2).length()));
			else if (columnNo == 3)
				col3Value1 = Integer.parseInt(items[i].getText(3));
			else if (columnNo == 4)
				col4Value1 = items[i].getText(4);
			else if (columnNo == 5)
				col5Value1 = Integer.parseInt(items[i].getText(5));
			for(int j=0; j < i; j++) {
				compareCorrect = false;
				if(columnNo == 0)  {
					col0Value2 = Integer.parseInt(items[j].getText(0).substring(5, items[j].getText(0).length()));
					if(col0Value1 - col0Value2 < 0) 
						compareCorrect = true;
				}					
				else if (columnNo == 1) {
					col1Value2 = items[j].getText(1);
					if(collator.compare(col1Value1, col1Value2) < 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 2) {
					col2Value2 = Double.parseDouble(items[j].getText(2).substring(1,items[j].getText(2).length()));
					if((col2Value1 - col2Value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 3) {
					col3Value2 = Integer.parseInt(items[j].getText(3));
					if((col3Value1 - col3Value2) > 0) 
						compareCorrect = true;
				}	
				else if (columnNo == 4) {
					col4Value2 = items[j].getText(4);
					if(collator.compare(col4Value1, col4Value2) < 0) 
						compareCorrect = true;
				}
				else if (columnNo == 5) {
					col5Value2 = Integer.parseInt(items[j].getText(5));
					if((col5Value1 - col5Value2) > 0) 
						compareCorrect = true;
				}

				if(compareCorrect == true) {
					Item temp = itemList.get(i);
					itemList.remove(i);
					String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3), items[i].getText(4),items[i].getText(5)};
					items[i].dispose();
					TableItem item = new TableItem(tableListItem, SWT.NONE, j);
					itemList.add(j, temp);
					item.setText(values);
					items = tableListItem.getItems();
					break;
				}
			}	
		}

		refreshTable();
	}

	/**
	 * Description: Re-display the table.
	 */
	public void refreshTable() {

		tableListItem.removeAll();
		for (int loopIndex = 0; loopIndex < itemList.size(); loopIndex++) {
			TableItem item = new TableItem(tableListItem, SWT.NULL);
			item.setText(0, "Item " + (loopIndex+1));
			item.setText(1, itemList.get(loopIndex).getItem());
			item.setText(2, "$"+((double) itemList.get(loopIndex).getPrice())/100);
			if(satisfactionChoice == 1) 
				item.setText(3, ""+itemList.get(loopIndex).getSatisfactionValue());
			if(typeChoice == 1)
				item.setText(4, ""+itemList.get(loopIndex).getType());		
			item.setText(5, ""+itemList.get(loopIndex).getQuantity());
		}
		for (int loopIndex = 0; loopIndex < 6; loopIndex++) {
			tableListItem.getColumn(loopIndex).pack();
		}
	}

	/**
	 * Description: CSV importing.
	 * @param filepath
	 */

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

	/**
	 * Description: CSV Exporting
	 * @param filepath
	 */
	public void ExportCSV (String filepath) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(filepath));
			Vector<Item> optimizeList = budgetPersonalAssistant.getOptimizeItemList(currentEvent.getID());
			for(int i=0; i<optimizeList.size(); i++) {
				String[] entries = new String[4];
				entries[0] = itemList.get(i).getItem();
				entries[1] = " "+(((double) itemList.get(i).getPrice())/100);
				entries[2] = " "+itemList.get(i).getSatisfactionValue();
				entries[3] = " "+itemList.get(i).getType();

				writer.writeNext(entries);

			}

			writer.close();
			new errormessageDialog(new Shell(), "The file was exported successfully!").open();		
		} catch (IOException e) {
			System.out.println("Error exporting");
			new errormessageDialog(new Shell(), "There was an error exporting the file.").open();
			e.printStackTrace();
		}
	}
}
