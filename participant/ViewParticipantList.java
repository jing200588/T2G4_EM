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

public class ViewParticipantList extends Composite {
	public static String[] HEADERS = {"Name", "Matric No.", "Contact", "Email Address", "Home Address", "Remarks"};
	private Eventitem cevent;
	private Table table;
	private Text txtimportfile;
	private TableViewer tableViewer;
	private Text txtexportfile;
	private Button btnDelete;
	private int columnNo = 0;
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
		
		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		
		//clone the list
		for (int i=0; i<cevent.getParticipantList().size(); i++)
			tempEntries.add(cevent.getParticipantList().get(i));
		
		//setting the column headers
		for (int i=0; i<tvc.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewer, SWT.NONE);
			tvc[i].getColumn().setText(HEADERS[i]);
			tvc[i].getColumn().pack();
		}
		
//		if (!cevent.getParticipantList().isEmpty()) {
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
			for (int i=0; i<HEADERS.length; i++)
				tvc[i].getColumn().pack();
			
			CellEditor[] editors = new CellEditor[HEADERS.length];
			//set headers for the table. set cell editors for each column.
					for (int i=0; i<HEADERS.length; i++) 	
						editors[i] = new TextCellEditor(table);
			
			tableViewer.setColumnProperties(HEADERS);
			tableViewer.setCellModifier(new ParticipantListCellModifier(tableViewer));
			tableViewer.setCellEditors(editors);
			
//		}
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnDelete.setEnabled(true);
			}
		});
	//	tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(75);
		fd_table.right = new FormAttachment(85);
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		
		//Bottom composite
		Composite compositebottom = new Composite(composite, SWT.NONE);
		FormData fd_compositebottom = new FormData();
		fd_compositebottom.top = new FormAttachment(table);
		fd_compositebottom.right = new FormAttachment(85, 5);
		fd_compositebottom.left = new FormAttachment(0, 10);
	//	fd_compositebottom.bottom = new FormAttachment(0, 352);
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
				if (table.getItemCount() != 0) {
					deleteconfirmDialog dialog = new deleteconfirmDialog(new Shell(), "confirm",
							"Importing a new file will replace the current table. Do you want to continue?");
					if ((Integer) dialog.open() == 1) {
						//Clears the tables b4 import.
						table.setRedraw(false);
						table.removeAll();
						table.setRedraw(true);
						tempEntries.clear();
						ImportCSV(txtimportfile.getText());
					}
				}
				
				else {
					ImportCSV(txtimportfile.getText());
				}
				tableViewer.refresh();
				for (int i=0; i<HEADERS.length; i++)
					tvc[i].getColumn().pack();
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
		compositeside.setLayout(new GridLayout(1, false));
		FormData fd_compositeside = new FormData();
		fd_compositeside.bottom = new FormAttachment(75);
		fd_compositeside.right = new FormAttachment(table, 115, SWT.RIGHT);
//		fd_compositeside.bottom = new FormAttachment(75, 5);
		fd_compositeside.top = new FormAttachment(1, 0);
		fd_compositeside.left = new FormAttachment(table, 6);
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
				//TableItem item = new TableItem(table, SWT.NONE);
		/*		String[] newarr = new String[HEADERS.length];
				myEntries.add(newarr);
				for (int i=0; i<newarr.length; i++)
					newarr[i] = "";
				tableViewer.refresh();
				table.setSelection(table.getItemCount()-1);
				//table.setSelection(item);
			*/	
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
		
		Button btnGoBack = new Button(compositeside, SWT.NONE);
		btnGoBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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

		
		
		
		
/*		
 		//XML
		tableViewer.setContentProvider(new RowContentProvider());

		RowLabelProvider labelProvider = new RowLabelProvider();

		labelProvider.createColumns(tableViewer);

		tableViewer.setLabelProvider(labelProvider);

	//	tableViewer.setInput(getViewSite());
		
		tableViewer.getTable().setBounds(10, 10, 717, 272);
	 
*/
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
		new errormessageDialog(new Shell(), "The file was exported successfully!").open();
		/*
	     String[] entries = "first#second#third".split("#");
	     writer.writeNext(entries);
		writer.close();
	      */         		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Error exporting");
		new errormessageDialog(new Shell(), "There was an error exporting the file.").open();
		e.printStackTrace();
	}
}

public void ImportCSV (String filepath) {
	try {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		
		List<String[]> entries = reader.readAll();
		entries.remove(0);	//remove the header row
		
		for (int i=0; i<entries.size(); i++) {
			tempEntries.add(new Participant(entries.get(i)[0], entries.get(i)[1], entries.get(i)[2], entries.get(i)[3], entries.get(i)[4], entries.get(i)[5]));
		}
//		String[] headers = myEntries.get(0);
//		TableViewerColumn[] tvc= new TableViewerColumn[headers.length];
//		HEADERS = headers;
//		columnNo = headers.length;
/*
		index =0;
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<HEADERS.length; i++) {
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (index == HEADERS.length)
						index = 0;
				/*	System.out.println(index);
					String[] arr = (String[]) element;
					return arr[index++];
					*/
	/*				
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
			//tvc[i].getColumn().pack();
		}
		
		for (int i=0; i<HEADERS.length; i++)
			tvc[i].getColumn().pack();
		*/
	/*	
		TableViewerColumn tvc2 = new TableViewerColumn (tableViewer, SWT.NONE);
		tvc2.getColumn().setText(myEntries.get(0)[1]);
		tvc2.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element)
			{
				String[] arr = (String[]) element;
				return arr[1];
			}
		});
		tvc2.getColumn().pack();
		*/
/*		tempEntries.remove(0);
		tableViewer.setInput(tempEntries);
		
		CellEditor[] editors = new CellEditor[HEADERS.length];
		//set headers for the table. set cell editors for each column.
				for (int i=0; i<HEADERS.length; i++) 	
					editors[i] = new TextCellEditor(table);
		
		tableViewer.setColumnProperties(HEADERS);
		tableViewer.setCellModifier(new ParticipantListCellModifier(tableViewer));
		tableViewer.setCellEditors(editors);
		
		Vector<TableColumn> tc = new Vector<TableColumn>();*/
		/*
		String[] headers = myEntries.get(0);
		
	    CellEditor[] editors = new CellEditor[headers.length];
		
		//set headers for the table. set cell editors for each column.
		for (int i=0; i<headers.length; i++) {
			tc.add(new TableColumn(table, SWT.CENTER));
			tc.get(i).setText(headers[i]);
			
			editors[i] = new TextCellEditor(table);
		}
		
		//populating entries.
		for (int i=1; i<myEntries.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(myEntries.get(i));
		}
		
		for (int i=0; i<headers.length; i++)
			tc.get(i).pack();
		
		tableViewer.setCellModifier(new ParticipantListModifier(tableViewer));
		tableViewer.setCellEditors(editors);
		*/
	/*	
		ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
		strat.setType(String.class);
		String[] columns = new String[] {"name", "orderNumber", "id"}; // the fields to bind do in your JavaBean
		strat.setColumnMapping(columns);

		CsvToBean csv = new CsvToBean();
		List list = csv.parse(strat, reader);*/
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("File not found.");
		new errormessageDialog(new Shell(), "The file specified cannot be found.").open();
		e.printStackTrace();
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
