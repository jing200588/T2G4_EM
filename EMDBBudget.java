import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;



class EMDBBudget extends EMDBBase{


    
    private DbTable 	budgetTable;  
    private DbColumn 	budgetID;
    private DbColumn 	budgetEventsID;
    private DbColumn 	budgetName;
    private DbColumn 	budgetPrice;
    private DbColumn 	budgetSatisfaction;
    private DbColumn 	budgetType;

    
    
    private DbTable 	optBudgetTable;
    private DbColumn 	optBudgetID;
    private DbColumn 	optBudgetEventsID;
    private DbColumn 	optBudgetName;
    private DbColumn 	optBudgetPrice;
    private DbColumn 	optBudgetSatisfaction;
    private DbColumn 	optBudgetType;  
	
    
    
    
     
    /**
     * Constructor
     */
	public EMDBBudget(){
		super();
		this.setTable();
	}

	
	
	/**
	 * Set up the table structure.
	 */
	private void setTable(){
		
		/*
		 * Budget (Normal)
		 */
		this.budgetTable 				= 	this.schema.addTable(EMDBSettings.TABLE_BUDGET);
		this.budgetID 					= 	this.budgetTable.addColumn("budget_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.budgetEventsID				= 	this.budgetTable.addColumn("events_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.budgetName					= 	this.budgetTable.addColumn("name", "TEXT", null);
		this.budgetPrice				= 	this.budgetTable.addColumn("price", "INTEGER", null);
		this.budgetSatisfaction			= 	this.budgetTable.addColumn("satisfaction", "INTEGER", null);
		this.budgetType					= 	this.budgetTable.addColumn("type", "TEXT", null);

		
		/*
		 * Budget (Optimized)
		 */
		this.optBudgetTable 			= 	this.schema.addTable(EMDBSettings.TABLE_BUDGET_OPTIMIZED);
		this.optBudgetID 				= 	this.optBudgetTable.addColumn("budget_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.optBudgetEventsID 			= 	this.optBudgetTable.addColumn("events_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.optBudgetName 				= 	this.optBudgetTable.addColumn("name", "TEXT", null);
		this.optBudgetPrice 			= 	this.optBudgetTable.addColumn("price", "INTEGER", null);
		this.optBudgetSatisfaction 		= 	this.optBudgetTable.addColumn("satisfaction", "INTEGER", null);
		this.optBudgetType 				= 	this.optBudgetTable.addColumn("type", "TEXT", null);
		
	}
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * Setup and Management
	 * 
	 * ******************************************************
	 */

	
	/**
	 * CREATE the database tables
	 */
	public void setup(){
		String sql 	=	new CreateTableQuery(this.budgetTable, true)
						.validate().toString();
		
		String sql2 =	new CreateTableQuery(this.optBudgetTable, true)
						.validate().toString();	
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Setup Budget: "+ sql);
			this.dMsg("Setup Budget Optimized: "+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	
	/**
	 * DROP the database tables
	 */
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.budgetTable)
						.validate().toString();
		String sql2 =	DropQuery.dropTable(this.optBudgetTable)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Cleanup Budget: "+ sql);
			this.dMsg("Cleanup Budget Optimized: "+ sql2);
		}
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD for budget
	 * 
	 * ******************************************************
	 */
	
	/**
	 * Adding budget. (SINGLE)
	 */
	public void addBudget(){
		
	}
	
	
	/**
	 * Adding budget in bulk.
	 */
	public void addBudgetList(){	
	}
	
	
	
	
	/**
	 * Getting various combination of budget.
	 */
	public void getBudget(){	
	}
	
	
	/**
	 * Getting a list of budget items
	 */
	public void getBudgetList(){
		
	}
	
	
	/**
	 * Updating budget.
	 */
	private void updateBudget(){
		
	}
	

	/**
	 * Updating in bulk
	 */
	private void updateBudgetList(){
		
	}
	
	
	
	/**
	 * Deleting Budget Entries.
	 */
	private void deleteBudget(){
		
	}
	
	
	/**
	 * Deleting Budget Entries in bulk.
	 */
	private void deleteBudgetList(){
		
	}	
	
}