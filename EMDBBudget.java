import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;



class EMDBBudget extends EMDBBase{


    
    private DbTable 	budgetTable;
    private DbTable 	budgetTableOpt;
    
    private DbColumn 	budgetID;
    private DbColumn 	eventsID;
    private DbColumn 	budgetName;
    private DbColumn 	budgetPrice;
    private DbColumn 	budgetSatisfaction;
    private DbColumn 	budgetType;

    private DbColumn 	budgetIDOpt;
    private DbColumn 	eventsIDOpt;
    private DbColumn 	budgetNameOpt;
    private DbColumn 	budgetPriceOpt;
    private DbColumn 	budgetSatisfactionOpt;
    private DbColumn 	budgetTypeOpt;  
	
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
		
		this.budgetTable 				= 	this.schema.addTable(EMDBSettings.TABLE_BUDGET);
		this.budgetTableOpt 			= 	this.schema.addTable(EMDBSettings.TABLE_BUDGET_OPTIMIZED);
		
		/*
		 * Budget (Normal)
		 */
		this.budgetID 					= 	this.budgetTable.addColumn("budget_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.eventsID					= 	this.budgetTable.addColumn("events_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.budgetName					= 	this.budgetTable.addColumn("name", "TEXT", null);
		this.budgetPrice				= 	this.budgetTable.addColumn("price", "INTEGER", null);
		this.budgetSatisfaction			= 	this.budgetTable.addColumn("satisfaction", "INTEGER", null);
		this.budgetType					= 	this.budgetTable.addColumn("type", "TEXT", null);

		
		/*
		 * Budget (Optimized)
		 */
		this.budgetIDOpt 				= 	this.budgetTableOpt.addColumn("budget_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.eventsIDOpt 				= 	this.budgetTableOpt.addColumn("events_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.budgetNameOpt 				= 	this.budgetTableOpt.addColumn("name", "TEXT", null);
		this.budgetPriceOpt 			= 	this.budgetTableOpt.addColumn("price", "INTEGER", null);
		this.budgetSatisfactionOpt 		= 	this.budgetTableOpt.addColumn("satisfaction", "INTEGER", null);
		this.budgetTypeOpt 				= 	this.budgetTableOpt.addColumn("type", "TEXT", null);
		
	}
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * Setup and Management
	 * 
	 * ******************************************************
	 */
	
	public void setup(){
		String sql 	=	new CreateTableQuery(this.budgetTable, true)
						.validate().toString();
		
		String sql2 =	new CreateTableQuery(this.budgetTableOpt, true)
						.validate().toString();	
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Setup Budget: "+ sql);
			this.dMsg("Setup Budget Optimized: "+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.budgetTable)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT)
			this.dMsg("Budget Cleanup: "+ sql);
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