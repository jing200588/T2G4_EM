package participant;

import event.*;
import dialog.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.layout.TableColumnLayout;

import venue.MyDateTime;

public class ViewParticipantList extends Composite {
	private EventItem currentEvent;
	private Table tableParticipant;
	private TableViewer tableParticipantViewer;
	private Text txtImportFile;
	private Text txtExportFile;
	private Label lblSave;
	private Button btnDelete;
	private int index = 0;
	private static ModelParticipantList modelPL = new ModelParticipantList();
	private static List<Participant> tempEntries;

	private static final String[] HEADERS = {"Name", "Matric No.", "Contact", "Email Address", "Home Address", "Remarks"};
	
	private static final int TOTAL = 6;
	
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewParticipantList(Composite parent, int style, EventItem curevent) {
		super(parent, style);
		setLayout(new FormLayout());
		currentEvent = curevent;
		tempEntries = new Vector<Participant>();
		
		//Event Particulars label
		Label lblEventParticulars = new Label(this, SWT.NONE);
		lblEventParticulars.setFont(SWTResourceManager.getFont("Showcard Gothic", 20, SWT.NORMAL));
		FormData fd_lblEventParticulars = new FormData();
		fd_lblEventParticulars.top = new FormAttachment(0, 10);
		fd_lblEventParticulars.left = new FormAttachment(0, 10);
		lblEventParticulars.setLayoutData(fd_lblEventParticulars);
		lblEventParticulars.setText("Participant List");
		
		//main composite
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(90);
		fd_composite.left = new FormAttachment(0, 0);
		fd_composite.top = new FormAttachment(15);
		fd_composite.right = new FormAttachment(100, 0);
		composite.setLayoutData(fd_composite);
		
		//TableViewerComp
		Composite TableViewerComp = new Composite(composite, SWT.NONE);
		TableColumnLayout tcl_TableViewerComp = new TableColumnLayout();
		TableViewerComp.setLayout(tcl_TableViewerComp);
		TableViewerComp.setLayoutData(new FormData());

		tableParticipantViewer = new TableViewer(TableViewerComp, SWT.BORDER | SWT.FULL_SELECTION);
		tableParticipant = tableParticipantViewer.getTable();
		tableParticipant.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		TableViewerColumn[] tvc= new TableViewerColumn[TOTAL];
		
		//clone the list
		for (int i=0; i<currentEvent.getParticipantList().size(); i++)
			tempEntries.add(currentEvent.getParticipantList().get(i));
		
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableParticipantViewer, SWT.NONE);
			tvc[i].getColumn().setText(HEADERS[i]);
		}
		
		//set column weight data
		tcl_TableViewerComp.setColumnData(tvc[0].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[1].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[2].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[3].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[4].getColumn(), new ColumnWeightData(30));
		tcl_TableViewerComp.setColumnData(tvc[5].getColumn(), new ColumnWeightData(10));
		
		//populating table
		tableParticipantViewer.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<HEADERS.length; i++) {
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (index == HEADERS.length)
						index = 0;						
					Participant parti = (Participant) element;
					switch (index)	{
						case 0:
							index++;
							return parti.getName();
						case 1:
							index++;
							return parti.getMatric();
						case 2:
							index++;
							return parti.getContact();
						case 3:
							index++;
							return parti.getEmail();
						case 4:
							index++;
							return parti.getAddress();
						case 5:
							index++;
							return parti.getRemark();
						default:
							return null;
					}
				}
			});
			}

		//////////////////////////////////////////////////////////////////////////////
		// Add listeners for table columns
		/////////////////////////////////////////////////////////////////////////////
		
		// Column 0: Name
		tvc[0].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.NAME);
				tableParticipantViewer.refresh();
			}
		});
		
		// Column 1: Matric
		tvc[1].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.MATRIC);
				tableParticipantViewer.refresh();
			}
		});
		
		// Column 2: Contact
		tvc[2].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.CONTACT);
				tableParticipantViewer.refresh();
			}
		});
		
		// Column 3: Email
		tvc[3].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.EMAIL);
				tableParticipantViewer.refresh();
			}
		});
		
		// Column 4: Address
		tvc[4].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.ADDRESS);
				tableParticipantViewer.refresh();
			}
		});
		
		// Column 5: Remark
		tvc[5].getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Participant.columnSort(tempEntries, Participant.COLUMNSORTCRITERIA.REMARK);
				tableParticipantViewer.refresh();
			}
		});
		
			tableParticipantViewer.setInput(tempEntries);
			tvc[1].getColumn().pack();
			tvc[2].getColumn().pack();
			
			CellEditor[] editors = new CellEditor[HEADERS.length];
			//set headers for the table. set cell editors for each column.
			for (int i=0; i<HEADERS.length; i++) 	
				editors[i] = new TextCellEditor(tableParticipant);
	
			tableParticipantViewer.setColumnProperties(HEADERS);
			tableParticipantViewer.setCellModifier(new ParticipantListCellModifier(tableParticipantViewer));
			tableParticipantViewer.setCellEditors(editors);
		
		//tableParticipant selection listener
		tableParticipant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnDelete.setEnabled(true);
			}
		});
	
		
		FormData fd_TableViewerComp = new FormData();
		fd_TableViewerComp.bottom = new FormAttachment(75, -8);
		fd_TableViewerComp.right = new FormAttachment(85);
		TableViewerComp.setLayoutData(fd_TableViewerComp);
		tableParticipant.setHeaderVisible(true);
		
		//Bottom composite
		Composite compositebottom = new Composite(composite, SWT.NONE);
		compositebottom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		fd_TableViewerComp.left = new FormAttachment(compositebottom, 0, SWT.LEFT);
		FormData fd_compositebottom = new FormData();
		fd_compositebottom.top = new FormAttachment(TableViewerComp, 8);
		fd_compositebottom.right = new FormAttachment(85, 5);
		fd_compositebottom.left = new FormAttachment(0, 10);
		compositebottom.setLayoutData(fd_compositebottom);
		compositebottom.setLayout(new GridLayout(4, false));
		
		lblSave = new Label(compositebottom, SWT.NONE);
		lblSave.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		lblSave.setText("The list is not saved.");
		
		Label lblInputFile = new Label(compositebottom, SWT.NONE);
		lblInputFile.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblInputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInputFile.setText("Input File:");
		
		txtImportFile = new Text(compositebottom, SWT.BORDER);
		txtImportFile.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtImportFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtImportFile.setMessage("C:\\");

		/************************************************************
		 * 
		 * IMPORT BROWSE EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnInputBrowse = new Button(compositebottom, SWT.NONE);
		btnInputBrowse.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnInputBrowse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnInputBrowse.widthHint = 80;
		btnInputBrowse.setLayoutData(gd_btnInputBrowse);
		btnInputBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);
				
				String input = fsd.open();
				if (input != null)
					txtImportFile.setText(input);
			}
		});
		btnInputBrowse.setText("Browse");
		
		/************************************************************
		 * 
		 * IMPORT CSV EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnImportFile = new Button(compositebottom, SWT.NONE);
		btnImportFile.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnImportFile = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnImportFile.widthHint = 80;
		btnImportFile.setLayoutData(gd_btnImportFile);
		btnImportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					if (txtImportFile.getText().isEmpty())
						throw new Exception("Path name is empty. Please input the path name of the .csv file u wish to import.");
					
					if (!txtImportFile.getText().endsWith(".csv"))
						txtImportFile.append(".csv");
					
					if (tableParticipant.getItemCount() != 0) {
						DeleteConfirmDialog dialog = new DeleteConfirmDialog(new Shell(), "confirm",
								"Importing a new file will replace the current table. Do you want to continue?");
						if ((Integer) dialog.open() == 1) {
							//Clears the tables b4 import.
							ImportCSV(txtImportFile.getText());
							tableParticipant.setRedraw(false);
							tableParticipant.removeAll();
							tableParticipant.setRedraw(true);
						}
					}
					
					else {
						ImportCSV(txtImportFile.getText());
					}
					tableParticipantViewer.refresh();

				} catch (FileNotFoundException exception) {
					System.out.println("File not found.");
					new ErrorMessageDialog(new Shell(), "The file specified cannot be found.").open();
					exception.printStackTrace();
					
				} catch (ArrayIndexOutOfBoundsException exception) {
					new ErrorMessageDialog(new Shell(), "Invalid .csv file. Make sure the file follows the correct format.").open();
				} catch (Exception exception) {
					if (exception.getMessage().compareTo("Path name is empty. Please input the path name of the .csv file u wish to import.") == 0)
						new ErrorMessageDialog(new Shell(), exception.getMessage()).open();
					else 
						new ErrorMessageDialog(new Shell(), "There was an error importing the file").open();
					exception.printStackTrace();
				}
				
				
			}
		});
		btnImportFile.setText("Import File");
		
		Label lblOutputFile = new Label(compositebottom, SWT.NONE);
		lblOutputFile.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblOutputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputFile.setText("Output File:");
		
		txtExportFile = new Text(compositebottom, SWT.BORDER);
		txtExportFile.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtExportFile.setMessage("C:\\");
		txtExportFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		/************************************************************
		 * 
		 * EXPORT BROWSE EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnOutputBrowse = new Button(compositebottom, SWT.NONE);
		btnOutputBrowse.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnOutputBrowse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOutputBrowse.widthHint = 80;
		btnOutputBrowse.setLayoutData(gd_btnOutputBrowse);
		btnOutputBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);
				
				String input = fsd.open();
				if (input != null) {
					if (!input.toLowerCase().endsWith(".csv"))
						input+= ".csv";
					txtExportFile.setText(input);
				}
			}
			
		});
		btnOutputBrowse.setText("Browse");
		
		/************************************************************
		 * 
		 * EXPORT CSV EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnExportFile = new Button(compositebottom, SWT.NONE);
		btnExportFile.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnExportFile = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnExportFile.widthHint = 80;
		btnExportFile.setLayoutData(gd_btnExportFile);
		btnExportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportCSV(txtExportFile.getText());
			}
		});
		btnExportFile.setText("Export File");
		
		//Side Composite
		Composite compositeside = new Composite(composite, SWT.NONE);
		fd_TableViewerComp.top = new FormAttachment(0);
		compositeside.setLayout(new GridLayout(1, false));
		FormData fd_compositeside = new FormData();
		fd_compositeside.left = new FormAttachment(TableViewerComp, 6);
		fd_compositeside.right = new FormAttachment(100, 47);
		fd_compositeside.bottom = new FormAttachment(75, -4);
		fd_compositeside.top = new FormAttachment(0, -5);
		compositeside.setLayoutData(fd_compositeside);
		
		//Add New button
		Button btnAddNew = new Button(compositeside, SWT.NONE);
		btnAddNew.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnAddNew = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnAddNew.widthHint = 100;
		btnAddNew.setLayoutData(gd_btnAddNew);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				AddParticipantDialog Participant = new AddParticipantDialog(new Shell());
				Participant.open();
				tableParticipantViewer.refresh();
			
			}
		});
		btnAddNew.setText("Add Participant");
		btnDelete = new Button(compositeside, SWT.NONE);
		btnDelete.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnDelete = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnDelete.widthHint = 100;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Delete row
				int currentindex = tableParticipant.getSelectionIndex();
				tableParticipant.remove(currentindex);	
				tempEntries.remove(currentindex);	
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setEnabled(false);
		
		Button btnSaveList = new Button(compositeside, SWT.NONE);
		btnSaveList.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		GridData gd_btnSaveList = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveList.widthHint = 100;
		btnSaveList.setLayoutData(gd_btnSaveList);
		btnSaveList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//save list into DB and event item
				currentEvent.setParticipantList(tempEntries);
				ModelParticipantList.UpdateDB(currentEvent);
				showSaveStatus();
			}
		});
		btnSaveList.setText("Save List");
		
		//Go Back button
		Button btnGoBack = new Button(compositeside, SWT.NONE);
		btnGoBack.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnGoBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TwoChoiceDialog saveDialog = new TwoChoiceDialog(new Shell(), "Message", 
						"Do you want to save your table of participant list?", "Yes", "No");
				String choice = (String) saveDialog.open();
				
				if(choice == null)
					return;
				
				// Update the event flow in the database as well as in the event itself
				if(choice.equals("Yes") == true)
				{
					currentEvent.setParticipantList(tempEntries);
					ModelParticipantList.UpdateDB(currentEvent);
				}
				
				// Return to the main GUI
				ModelParticipantList.PullParticipantList(currentEvent);
				ViewMain.ReturnView();
			}
		});
		GridData gd_btnGoBack = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_btnGoBack.widthHint = 100;
		btnGoBack.setLayoutData(gd_btnGoBack);
		btnGoBack.setText("Back");
		
		Label lbldummy = new Label(compositeside, SWT.NONE);
		lbldummy.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 6));
		

		/************************************************************
		 * 
		 * SAVE LIST EVENT LISTENER
		 * 
		 ***********************************************************/
			
		
	}
	
/**
 * Description: Method to export participant list into CSV file
 * @param filepath - the location to store the exported file
 */
public void ExportCSV (String filepath) {
	try {
		if (txtImportFile.getText().isEmpty())
			throw new Exception("Path name is empty. Please input the path name of the .csv file u wish to export.");
		
		CSVWriter writer = new CSVWriter(new FileWriter(filepath));
		String[] headers = new String[tableParticipant.getColumnCount()];
		for (int i=0; i<tableParticipant.getColumnCount(); i++) {
			headers[i] = tableParticipant.getColumns()[i].getText();
		}
		
		writer.writeNext(headers);
		
		for (int i=0; i<tableParticipant.getItemCount(); i++) {
			String[] entries = new String[tableParticipant.getColumnCount()];
			for (int j=0; j<tableParticipant.getColumnCount(); j++) 
				entries[j] = tableParticipant.getItem(i).getText(j);
			writer.writeNext(entries);
		}
		writer.close();
		new ErrorMessageDialog(new Shell(), "The file was exported successfully!", "Success!").open();
		         		
	} catch (Exception e) {
		if (e.getMessage().compareTo("Path name is empty. Please input the path name of the .csv file u wish to export.") == 0)
			new ErrorMessageDialog(new Shell(), e.getMessage()).open();
		else
			new ErrorMessageDialog(new Shell(), "There was an error exporting the file.").open();
		e.printStackTrace();
	}
}

/**
 * Description: Method to import participant list from a CSV file 
 * @param filepath - the location of the CSV file to be imported
 * @throws Exception
 */
public void ImportCSV (String filepath) throws Exception {
	try {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		
		List<String[]> entries = reader.readAll();
		entries.remove(0);	//remove the header row
		
		if (entries.get(0).length < 6)
			throw new ArrayIndexOutOfBoundsException();
		tempEntries.clear();
		for (int i=0; i<entries.size(); i++) {
			tempEntries.add(new Participant(entries.get(i)[0], entries.get(i)[1], entries.get(i)[2], entries.get(i)[3], entries.get(i)[4], entries.get(i)[5]));
		}		
	} catch (FileNotFoundException e) {
		throw e;
	} catch (IOException e) {

		throw e;
	}

}

/**
 * This method is to update the last time the user saves his list.
 */
private void showSaveStatus()
{
	MyDateTime currentTime = MyDateTime.getCurrentDateTime();
	
	lblSave.setText("Last saved by " + currentTime.getDateRepresentation() + " " + 
			currentTime.getTimeRepresentation());
}
	/**
	 * Description: Adds a new participant object into the local list of participants.
	 * @param newParticipant - new participant to be added
	 */
	public static void addParticipant(Participant newParticipant) {
		tempEntries.add(newParticipant);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
