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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
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

public class ViewParticipantList extends Composite {
	private Eventitem cevent;
	private Table table;
	private Text txtimportfile;
	private TableViewer tableViewer;
	private Text txtexportfile;
	private Button btnDelete;
	
	
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
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100, 0);
		fd_composite.left = new FormAttachment(0, 0);
		fd_composite.top = new FormAttachment(15, 0);
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
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(0, 282);
		fd_table.right = new FormAttachment(0, 727);
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		
		//Add New button
		Button btnAddNew = new Button(composite, SWT.NONE);
		FormData fd_btnAddNew = new FormData();
		fd_btnAddNew.right = new FormAttachment(0, 808);
		fd_btnAddNew.top = new FormAttachment(0, 10);
		fd_btnAddNew.left = new FormAttachment(0, 733);
		btnAddNew.setLayoutData(fd_btnAddNew);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = new TableItem(table, SWT.NONE);
				table.setSelection(item);
				
			}
		});
		btnAddNew.setText("Add New");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(table, 0, SWT.RIGHT);
		fd_composite_1.bottom = new FormAttachment(0, 352);
		fd_composite_1.top = new FormAttachment(0, 288);
		fd_composite_1.left = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new GridLayout(3, false));
		
		Label lblInputFile = new Label(composite_1, SWT.NONE);
		lblInputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInputFile.setText("Input File:");
		
		txtimportfile = new Text(composite_1, SWT.BORDER);
		txtimportfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtimportfile.setText("/temp/myfile.csv");
		
		Button btnImportFile = new Button(composite_1, SWT.NONE);
		btnImportFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnImportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportCSV(txtimportfile.getText());
			}
		});
		btnImportFile.setText("Import File");
		
		Label lblOutputFile = new Label(composite_1, SWT.NONE);
		lblOutputFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputFile.setText("Output File:");
		
		txtexportfile = new Text(composite_1, SWT.BORDER);
		txtexportfile.setMessage("/temp/myfile.csv");
		txtexportfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnExportFile = new Button(composite_1, SWT.NONE);
		GridData gd_btnExportFile = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnExportFile.widthHint = 68;
		btnExportFile.setLayoutData(gd_btnExportFile);
		btnExportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportCSV(txtexportfile.getText());
			}
		});
		btnExportFile.setText("Export File");
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Delete row
				table.remove(table.getSelectionIndices());
			}
		});
		FormData fd_btnDelete = new FormData();
		fd_btnDelete.right = new FormAttachment(btnAddNew, 0, SWT.RIGHT);
		fd_btnDelete.top = new FormAttachment(btnAddNew, 6);
		fd_btnDelete.left = new FormAttachment(table, 6);
		btnDelete.setLayoutData(fd_btnDelete);
		btnDelete.setText("Delete");
		btnDelete.setEnabled(false);
		
		
		
		
		
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
		
		/*
	     String[] entries = "first#second#third".split("#");
	     writer.writeNext(entries);
		writer.close();
	      */         		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Error exporting");
		e.printStackTrace();
	}
}

public void ImportCSV (String filepath) {
	try {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		
		List<String[]> myEntries = reader.readAll();
		Vector<TableColumn> tc = new Vector<TableColumn>();
		
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
