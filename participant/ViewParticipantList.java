package participant;

import event.*;
import dialog.*;
import emdb.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
/*
import galang.research.jface.RowContentProvider;
import galang.research.jface.RowLabelProvider;
*/
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.part.ViewPart;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.layout.TableColumnLayout;

public class ViewParticipantList extends Composite {
	public static String[] HEADERS = {"Name", "Matric No.", "Contact", "Email Address", "Home Address", "Remarks"};
	private Eventitem cevent;
	private Table table;
	private Text txtimportfile;
	private TableViewer tableViewer;
	private Text txtexportfile;
	private Button btnDelete;
	private int index = 0;
	private static List<Participant> tempEntries;
	private TableViewerColumn[] tvc= new TableViewerColumn[6];
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewParticipantList(Composite parent, int style, Eventitem curevent) {
		super(parent, style);
		setLayout(new FormLayout());
		cevent = curevent;
		tempEntries = new Vector<Participant>();
		
		//Event Particulars label
		Label lblEventParticulars = new Label(this, SWT.NONE);
		lblEventParticulars.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
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

		tableViewer = new TableViewer(TableViewerComp, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		
		//clone the list
		for (int i=0; i<cevent.getParticipantList().size(); i++)
			tempEntries.add(cevent.getParticipantList().get(i));
		
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewer, SWT.NONE);
			tvc[i].getColumn().setText(HEADERS[i]);
		}
		

		tcl_TableViewerComp.setColumnData(tvc[0].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[1].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[2].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[3].getColumn(), new ColumnWeightData(20));
		tcl_TableViewerComp.setColumnData(tvc[4].getColumn(), new ColumnWeightData(30));
		tcl_TableViewerComp.setColumnData(tvc[5].getColumn(), new ColumnWeightData(10));
		
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
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

			tableViewer.setInput(tempEntries);
			tvc[1].getColumn().pack();
			tvc[2].getColumn().pack();
			
			CellEditor[] editors = new CellEditor[HEADERS.length];
			//set headers for the table. set cell editors for each column.
			for (int i=0; i<HEADERS.length; i++) 	
				editors[i] = new TextCellEditor(table);
	
			tableViewer.setColumnProperties(HEADERS);
			tableViewer.setCellModifier(new ParticipantListCellModifier(tableViewer));
			tableViewer.setCellEditors(editors);
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnDelete.setEnabled(true);
			}
		});
	
		
		FormData fd_TableViewerComp = new FormData();
		fd_TableViewerComp.bottom = new FormAttachment(75, -8);
		fd_TableViewerComp.right = new FormAttachment(85);
		TableViewerComp.setLayoutData(fd_TableViewerComp);
		table.setHeaderVisible(true);
		
		//Bottom composite
		Composite compositebottom = new Composite(composite, SWT.NONE);
		fd_TableViewerComp.left = new FormAttachment(compositebottom, 0, SWT.LEFT);
		FormData fd_compositebottom = new FormData();
		fd_compositebottom.top = new FormAttachment(TableViewerComp, 8);
		fd_compositebottom.right = new FormAttachment(85, 5);
		fd_compositebottom.left = new FormAttachment(0, 10);
		compositebottom.setLayoutData(fd_compositebottom);
		compositebottom.setLayout(new GridLayout(4, false));
		
		Label lblInputFile = new Label(compositebottom, SWT.NONE);
		lblInputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInputFile.setText("Input File:");
		
		txtimportfile = new Text(compositebottom, SWT.BORDER);
		txtimportfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtimportfile.setText("/temp/myfile.csv");

		/************************************************************
		 * 
		 * IMPORT BROWSE EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnInputBrowse = new Button(compositebottom, SWT.NONE);
		GridData gd_btnInputBrowse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnInputBrowse.widthHint = 70;
		btnInputBrowse.setLayoutData(gd_btnInputBrowse);
		btnInputBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fsd = new FileDialog(new Shell());
				String[] extension = {"*.csv"};
				fsd.setFilterExtensions(extension);
				
				String input = fsd.open();
				if (input != null)
					txtimportfile.setText(input);
			}
		});
		btnInputBrowse.setText("Browse");
		
		/************************************************************
		 * 
		 * IMPORT CSV EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnImportFile = new Button(compositebottom, SWT.NONE);
		GridData gd_btnImportFile = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnImportFile.widthHint = 70;
		btnImportFile.setLayoutData(gd_btnImportFile);
		btnImportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (!txtimportfile.getText().endsWith(".csv"))
						throw new Exception("Import file must be in .csv format");
					
					if (table.getItemCount() != 0) {
						DeleteConfirmDialog dialog = new DeleteConfirmDialog(new Shell(), "confirm",
								"Importing a new file will replace the current table. Do you want to continue?");
						if ((Integer) dialog.open() == 1) {
							//Clears the tables b4 import.
							ImportCSV(txtimportfile.getText());
							table.setRedraw(false);
							table.removeAll();
							table.setRedraw(true);
						}
					}
					
					else {
						ImportCSV(txtimportfile.getText());
					}
					tableViewer.refresh();

				} catch (FileNotFoundException exception) {
					System.out.println("File not found.");
					new ErrorMessageDialog(new Shell(), "The file specified cannot be found.").open();
					exception.printStackTrace();
					
				} catch (ArrayIndexOutOfBoundsException exception) {
					new ErrorMessageDialog(new Shell(), "Invalid .csv file. Make sure the file follows the correct format.").open();
				} catch (Exception exception) {
					new ErrorMessageDialog(new Shell(), exception.getMessage()).open();
					exception.printStackTrace();
				}
				
				
			}
		});
		btnImportFile.setText("Import File");
		
		Label lblOutputFile = new Label(compositebottom, SWT.NONE);
		lblOutputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputFile.setText("Output File:");
		
		txtexportfile = new Text(compositebottom, SWT.BORDER);
		txtexportfile.setMessage("/temp/myfile.csv");
		txtexportfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		/************************************************************
		 * 
		 * EXPORT BROWSE EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnOutputBrowse = new Button(compositebottom, SWT.NONE);
		GridData gd_btnOutputBrowse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOutputBrowse.widthHint = 70;
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
					txtexportfile.setText(input);
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
		GridData gd_btnExportFile = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnExportFile.widthHint = 70;
		btnExportFile.setLayoutData(gd_btnExportFile);
		btnExportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportCSV(txtexportfile.getText());
			}
		});
		btnExportFile.setText("Export File");
		
		//Side Composite
		Composite compositeside = new Composite(composite, SWT.NONE);
		fd_TableViewerComp.top = new FormAttachment(compositeside, 0, SWT.TOP);
		compositeside.setLayout(new GridLayout(1, false));
		FormData fd_compositeside = new FormData();
		fd_compositeside.left = new FormAttachment(TableViewerComp, 6);
		fd_compositeside.right = new FormAttachment(100, 47);
		fd_compositeside.bottom = new FormAttachment(75);
		fd_compositeside.top = new FormAttachment(1, 0);
		compositeside.setLayoutData(fd_compositeside);
		
		//Add New button
		Button btnAddNew = new Button(compositeside, SWT.NONE);
		GridData gd_btnAddNew = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnAddNew.widthHint = 100;
		btnAddNew.setLayoutData(gd_btnAddNew);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				AddParticipantDialog Participant = new AddParticipantDialog(new Shell());
				Participant.open();
				tableViewer.refresh();
			
			}
		});
		btnAddNew.setText("Add Participant");
		btnDelete = new Button(compositeside, SWT.NONE);
		GridData gd_btnDelete = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnDelete.widthHint = 100;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Delete row
				int currentindex = table.getSelectionIndex();
				table.remove(currentindex);	
				tempEntries.remove(currentindex);	
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setEnabled(false);
		
		//Go Back button
		Button btnGoBack = new Button(compositeside, SWT.NONE);
		btnGoBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ModelParticipantList.PullParticipantList(cevent);
				ViewMain.ReturnView();
			}
		});
		GridData gd_btnGoBack = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_btnGoBack.widthHint = 100;
		btnGoBack.setLayoutData(gd_btnGoBack);
		btnGoBack.setText("Go Back");
		
		Label lbldummy = new Label(compositeside, SWT.NONE);
		lbldummy.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 6));
		

		/************************************************************
		 * 
		 * SAVE LIST EVENT LISTENER
		 * 
		 ***********************************************************/
		
		Button btnSaveList = new Button(compositeside, SWT.NONE);
		GridData gd_btnSaveList = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnSaveList.heightHint = 45;
		gd_btnSaveList.widthHint = 100;
		btnSaveList.setLayoutData(gd_btnSaveList);
		btnSaveList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//save list into DB and event item
				cevent.setParticipantList(tempEntries);
				ModelParticipantList.UpdateDB(cevent);
			}
		});
		btnSaveList.setText("Save List");
			
		
	}
	
public void ExportCSV (String filepath) {
	try {
		CSVWriter writer = new CSVWriter(new FileWriter(filepath));
		String[] headers = new String[table.getColumnCount()];
		for (int i=0; i<table.getColumnCount(); i++) {
			headers[i] = table.getColumns()[i].getText();
		}
		
		writer.writeNext(headers);
		
		for (int i=0; i<table.getItemCount(); i++) {
			String[] entries = new String[table.getColumnCount()];
			for (int j=0; j<table.getColumnCount(); j++) 
				entries[j] = table.getItem(i).getText(j);
			writer.writeNext(entries);
		}
		writer.close();
		new ErrorMessageDialog(new Shell(), "The file was exported successfully!", "Success!").open();
		         		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Error exporting");
		new ErrorMessageDialog(new Shell(), "There was an error exporting the file.").open();
		e.printStackTrace();
	}
}

public void ImportCSV (String filepath) throws Exception {
	try {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		
		List<String[]> entries = reader.readAll();
		entries.remove(0);	//remove the header row
		
		if (entries.get(0).length < 6)
			throw new ArrayIndexOutOfBoundsException();
		for (int i=0; i<entries.size(); i++) {
			tempEntries.clear();
			tempEntries.add(new Participant(entries.get(i)[0], entries.get(i)[1], entries.get(i)[2], entries.get(i)[3], entries.get(i)[4], entries.get(i)[5]));
		}		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		throw e;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
		e.printStackTrace();
	}

}
	public static void addParticipant(Participant newparticipant) {
		tempEntries.add(newparticipant);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
