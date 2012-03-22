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
	public static String[] HEADERS = new String[0];
	private Eventitem cevent;
	private Table table;
	private Text txtimportfile;
	private TableViewer tableViewer;
	private Text txtexportfile;
	private Button btnDelete;
	private int columnNo;
	private int index;
	private List<String[]> myEntries;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewParticipantList(Composite parent, int style, Eventitem curevent) {
		super(parent, style);
		setLayout(new FormLayout());
		cevent = curevent;

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
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.left = new FormAttachment(0, 0);
		fd_composite.top = new FormAttachment(15);
		fd_composite.right = new FormAttachment(100, 0);
		composite.setLayoutData(fd_composite);
		
		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnDelete.setEnabled(true);
			}
		});
	//	tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(0, 282);
		fd_table.right = new FormAttachment(0, 727);
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		
		//Bottom composite
		Composite compositebottom = new Composite(composite, SWT.NONE);
		FormData fd_compositebottom = new FormData();
		fd_compositebottom.right = new FormAttachment(100, -131);
		fd_compositebottom.left = new FormAttachment(0, 10);
		fd_compositebottom.bottom = new FormAttachment(0, 352);
		fd_compositebottom.top = new FormAttachment(0, 288);
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
				if (table.getColumnCount() != 0) {
					deleteconfirmDialog dialog = new deleteconfirmDialog(new Shell(), "confirm",
							"Importing a new file will replace the current table. Do you want to continue?");
					if ((Integer) dialog.open() == 1) {
						//Clears the tables b4 import.
						table.setRedraw(false);
						table.removeAll();
						while (table.getColumnCount() > 0)
							table.getColumns()[0].dispose();
						table.setRedraw(true);
						
						//ImportCSV(txtimportfile.getText());
					}
				}
				
				else
					ImportCSV(txtimportfile.getText());
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
		fd_compositeside.right = new FormAttachment(table, 96, SWT.RIGHT);
		fd_compositeside.bottom = new FormAttachment(table, 0, SWT.BOTTOM);
		fd_compositeside.top = new FormAttachment(table, 0, SWT.TOP);
		fd_compositeside.left = new FormAttachment(table, 6);
		compositeside.setLayoutData(fd_compositeside);
		
		//Add New button
		Button btnAddNew = new Button(compositeside, SWT.NONE);
		GridData gd_btnAddNew = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddNew.widthHint = 80;
		btnAddNew.setLayoutData(gd_btnAddNew);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = new TableItem(table, SWT.NONE);
				table.setSelection(item);
				
			}
		});
		btnAddNew.setText("Add New");
		
		/************************************************************
		 * 
		 * DELETE ROW EVENT LISTENER
		 * 
		 ***********************************************************/
		btnDelete = new Button(compositeside, SWT.NONE);
		GridData gd_btnDelete = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnDelete.widthHint = 60;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Delete row
				int currentindex = table.getSelectionIndex();
				table.remove(currentindex);	
				myEntries.remove(currentindex);	
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setEnabled(false);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		new Label(compositeside, SWT.NONE);
		
		/************************************************************
		 * 
		 * SAVE LIST EVENT LISTENER
		 * 
		 ***********************************************************/
		Button btnSaveList = new Button(compositeside, SWT.NONE);
		btnSaveList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSaveList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//save list into DB and event item
				
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
		
		myEntries = reader.readAll();
		String[] headers = myEntries.get(0);
		TableViewerColumn[] tvc= new TableViewerColumn[headers.length];
		HEADERS = headers;
		columnNo = headers.length;

		index =0;
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		for (int i=0; i<headers.length; i++) {
			tvc[i] = new TableViewerColumn (tableViewer, SWT.NONE);
			tvc[i].getColumn().setText(headers[i]);
			tvc[i].setLabelProvider(new ColumnLabelProvider() {
				public String getText(Object element)
				{
					if (index == columnNo)
						index = 0;
					System.out.println(index);
					String[] arr = (String[]) element;
					return arr[index++];
				}
			});
			tvc[i].getColumn().pack();
		}
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
		myEntries.remove(0);
		tableViewer.setInput(myEntries);
		
		CellEditor[] editors = new CellEditor[headers.length];
		//set headers for the table. set cell editors for each column.
				for (int i=0; i<headers.length; i++) 	
					editors[i] = new TextCellEditor(table);
		
		tableViewer.setColumnProperties(headers);
		tableViewer.setCellModifier(new ParticipantListCellModifier(tableViewer));
		tableViewer.setCellEditors(editors);
		
		Vector<TableColumn> tc = new Vector<TableColumn>();
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
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
