
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;


/**
 * 
 * Database Extended Class - Budget
 * @author JunZhi
 *
 */
class EMDBBudget extends EMDBBase{

	private DbTable 	masterTable = this.schema.addTable(EMDBSettings.TABLE_MASTER);
	private DbColumn	masterName	= this.masterTable.addColumn("name");
    
    private DbTable 	budgetTable;  
    private DbColumn 	budgetID;
    private DbColumn 	budgetEventID;
    private DbColumn 	budgetName;
    private DbColumn 	budgetPrice;
    private DbColumn 	budgetSatisfaction;
    private DbColumn 	budgetType;
    private DbColumn    budgetQuantity;
    
    
    private DbTable 	optBudgetTable;
    private DbColumn 	optBudgetID;
    private DbColumn 	optBudgetEventID;
    private DbColumn 	optBudgetName;
    private DbColumn 	optBudgetPrice;
    private DbColumn 	optBudgetSatisfaction;
    private DbColumn 	optBudgetType;  
    private DbColumn    optBudgetQuantity;
    
    
    
     
    /**
     * Constructor
     */
	public EMDBBudget(){
		super();
		this.setTable();
	}

	public EMDBBudget(String aName){
		super(aName);
		this.setTable();
	}
	
	public EMDBBudget(String aName, boolean aDebugState){
		super(aName, aDebugState);
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
		this.budgetEventID				= 	this.budgetTable.addColumn("event_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.budgetName					= 	this.budgetTable.addColumn("name", "TEXT", null);
		this.budgetPrice				= 	this.budgetTable.addColumn("price", "INTEGER", null);
		this.budgetSatisfaction			= 	this.budgetTable.addColumn("satisfaction", "INTEGER", null);
		this.budgetType					= 	this.budgetTable.addColumn("type", "TEXT", null);
		this.budgetQuantity				= 	this.budgetTable.addColumn("quantity", "INTEGER DEFAULT (1)", null);

		
		/*
		 * Budget (Optimized)
		 */
		this.optBudgetTable 			= 	this.schema.addTable(EMDBSettings.TABLE_BUDGET_OPTIMIZED);
		this.optBudgetID 				= 	this.optBudgetTable.addColumn("budget_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.optBudgetEventID 			= 	this.optBudgetTable.addColumn("event_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.optBudgetName 				= 	this.optBudgetTable.addColumn("name", "TEXT", null);
		this.optBudgetPrice 			= 	this.optBudgetTable.addColumn("price", "INTEGER", null);
		this.optBudgetSatisfaction 		= 	this.optBudgetTable.addColumn("satisfaction", "INTEGER", null);
		this.optBudgetType 				= 	this.optBudgetTable.addColumn("type", "TEXT", null);
		this.optBudgetQuantity 			= 	this.optBudgetTable.addColumn("quantity", "INTEGER DEFAULT (1)", null);
	}
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * Setup and Management
	 * 
	 * ******************************************************
	 */

	
	
	
	/**
	 * CREATE the database tables.
	 */
	public void setup(){
		String sql 	=	new CreateTableQuery(this.budgetTable, true)
						.validate().toString();
		
		String sql2 =	new CreateTableQuery(this.optBudgetTable, true)
						.validate().toString();	
		
		
		if (this.dbDebug){
			this.dMsg(""+ sql);
			this.dMsg(""+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	
	
	
	/**
	 * DROP the database tables.
	 */
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.budgetTable)
						.validate().toString();
		String sql2 =	DropQuery.dropTable(this.optBudgetTable)
						.validate().toString();
		
		if (this.dbDebug){
			this.dMsg(""+ sql);
			this.dMsg(""+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	/**
	 * "TRUNCATE" the database tables
	 */
	public void truncate(){
		String sql 	=	new DeleteQuery(this.budgetTable)
							.validate().toString();
		String sql2 =	new DeleteQuery(this.optBudgetTable)
							.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(""+ sql);
			this.dMsg(""+ sql2);
		}
		
		this.connect();
		this.runQuery(sql);
		this.runQuery(sql2);
		this.disconnect();
	}
	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		if (this.dbDebug){
			this.dMsg(EMDBSettings.TABLE_BUDGET);
			this.dMsg(EMDBSettings.TABLE_BUDGET_OPTIMIZED);
		}
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.masterTable)
						.addCondition(
								ComboCondition.or(
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_BUDGET),
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_BUDGET_OPTIMIZED)
								)
						)
						.validate().toString();

		return this.verification(sql, 2);
		
	}
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD Options
	 * 
	 * ******************************************************
	 */
	
	
	/**
	 * Adding budget (SINGLE).
	 * @param aEventID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @return
	 */
	public int addBudget(int aEventID, String aName, int aPrice, int aSatisfaction, String aType, int aQuantity){
		return this.addBudgetGateway(aEventID, aName, aPrice, aSatisfaction, aType, "normal", aQuantity);
	}
	
	
	
	/**
	 * Adding optimized budget (SINGLE).
	 * @param aEventID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @return
	 */
	public int addBudgetOptimized(int aEventID, String aName, int aPrice, int aSatisfaction, String aType, int aQuantity){
		return this.addBudgetGateway(aEventID, aName, aPrice, aSatisfaction, aType, "optimized", aQuantity);
	}	
	

	
	/**
	 * Gateway for Adding budget (SINGLE).
	 * @param aEventID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @param aTableType
     * @param aQuantity
	 * @return
	 */
	private int addBudgetGateway(
			int aEventID, 
			String aName, 
			int aPrice, 
			int aSatisfaction, 
			String aType, 
			String aTableType,
            int aQuantity
			)
	{
		String sql = "";
		
		if (aTableType.compareTo("optimized") == 0){
				sql = new InsertQuery(this.optBudgetTable)
							.addColumn(this.optBudgetEventID, aEventID)
							.addColumn(this.optBudgetName, aName)
							.addColumn(this.optBudgetPrice, aPrice)
							.addColumn(this.optBudgetSatisfaction, aSatisfaction)
							.addColumn(this.optBudgetType, aType)
							.addColumn(this.optBudgetQuantity, aQuantity)
							.validate().toString();
		}else{
				sql = new InsertQuery(this.budgetTable)
							.addColumn(this.budgetEventID, aEventID)
							.addColumn(this.budgetName, aName)
							.addColumn(this.budgetPrice, aPrice)
							.addColumn(this.budgetSatisfaction, aSatisfaction)
							.addColumn(this.budgetType, aType)
							.addColumn(this.budgetQuantity, aQuantity)
							.validate().toString();
		}
			
		

		

		if (this.dbDebug){
			this.dMsg("ADD BUDGET");
			this.dMsg(sql);
		}
		
		Vector<Object[]> result = this.runQueryResults(sql);
		int id = 0;
		
		if (result.size() > 0){
			id = Integer.parseInt(result.get(0)[0].toString());
		}
		
		return id;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Adding budget list.
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int addBudgetList(Vector<Item> list, int aEventID){
		return this.addBudgetListGateway(list, aEventID, "normal");
	}
	
	
	/**
	 * Adding optimized budget list.
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int addBudgetListOptimized(Vector<Item> list, int aEventID){
		return this.addBudgetListGateway(list, aEventID, "optimized");
	}
	
	
	
	/**
	 * Gateway for Adding budget in bulk.
	 * @param list
	 * @param aEventID
	 * @param aTableType
	 * @return
	 */
	private int addBudgetListGateway(
			Vector<Item> list, 
			int aEventID, 
			String aTableType){
		
		int size = list.size();
		for (int i=0; i< size; i++){
			
			Item current = list.get(i);
			
			String sql = "";
			if (aTableType.compareTo("optimized") == 0){
					sql = new InsertQuery(this.optBudgetTable)
								.addColumn(this.optBudgetEventID, aEventID)
								.addColumn(this.optBudgetName, current.getItem())
								.addColumn(this.optBudgetPrice, current.getPrice())
								.addColumn(this.optBudgetSatisfaction, current.getSatisfactionValue())
								.addColumn(this.optBudgetType, current.getType())
								.addColumn(this.optBudgetQuantity, current.getQuantity())
								.validate().toString();
			}else{
					sql = new InsertQuery(this.budgetTable)
								.addColumn(this.budgetEventID, aEventID)
								.addColumn(this.budgetName, current.getItem())
								.addColumn(this.budgetPrice, current.getPrice())
								.addColumn(this.budgetSatisfaction, current.getSatisfactionValue())
								.addColumn(this.budgetType, current.getType())
								.addColumn(this.budgetQuantity, current.getQuantity())
								.validate().toString();
			}
			
			
			if (this.dbDebug){
				this.dMsg(sql);
			}
			this.queue(sql);
		}
		
		this.connect();
		int result = this.commit();
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Getting the list of budget.
	 * @return
	 */
	public Vector<Item> getBudgetList(int aEventID){
		return this.getBudgetListGateway(aEventID, "normal");
	}
	
	
	
	/**
	 * Getting the optimized list of budget.
	 * @return
	 */
	public Vector<Item> getBudgetListOptimized(int aEventID){
		return this.getBudgetListGateway(aEventID, "optimized");
	}
	
	
	
	/**
	 * Gateway for Getting a list of budgets.
	 * @param aTableType
	 * @return
	 */
	private Vector<Item> getBudgetListGateway(int aEventID, String aTableType){	
		String sql = "";

		if (aTableType.compareTo("optimized") == 0){
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.optBudgetTable)
							.addCondition(BinaryCondition.equalTo(this.optBudgetEventID, aEventID))
							.validate().toString();
		}else{
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.budgetTable)
							.addCondition(BinaryCondition.equalTo(this.budgetEventID, aEventID))
							.validate().toString();
		}
		

		if (this.dbDebug){
			this.dMsg("GET BUDGET LIST " + aTableType);
			this.dMsg(sql);
		}
		
		Vector<Item> list = new Vector<Item>();

		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			Item current = new Item(
								Integer.parseInt(row[0].toString()), 
								(row[2] == null) ? "" : row[2].toString(), 
								Integer.parseInt(row[3].toString()), 
								Integer.parseInt(row[4].toString()), 
								(row[5] == null) ? "" : row[5].toString()
							);
            current.setQuantity(Integer.parseInt(row[6].toString()));
		    list.add(current);
			
		}
		this.disconnect();
	
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Updating a budget.
	 * @param aBudgetID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @return
	 */
	public int updateBudget(
			int aBudgetID, 
			String aName, 
			int aPrice, 
			int aSatisfaction, 
			String aType,
			int aQuantity)
	{
		return this.updateBudgetGateway(aBudgetID, aName, aPrice, aSatisfaction, aType, "normal", aQuantity);
	}
	
	
	/**
	 * Updating an optimized budget.
	 * @param aBudgetID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @return
	 */
	public int updateBudgetOptimized(
			int aBudgetID,
			String aName, 
			int aPrice, 
			int aSatisfaction, 
			String aType,
			int aQuantity
			)
	{
		return this.updateBudgetGateway(aBudgetID, aName, aPrice, aSatisfaction, aType, "optimized", aQuantity);
	}
	
	
	
	
	/**
	 * Gateway for Updating budget.
	 * @param aBudgetID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @param aTableType
	 * @return
	 */
	private int updateBudgetGateway(
			int aBudgetID, 
			String aName, 
			int aPrice, 
			int aSatisfaction, 
			String aType, 
			String aTableType,
            int aQuantity
            )
    {

		String sql = "";

		if (aTableType.compareTo("optimized") == 0){
				sql = new UpdateQuery(this.optBudgetTable)
							.addSetClause(this.optBudgetName, aName)
							.addSetClause(this.optBudgetPrice, aPrice)
							.addSetClause(this.optBudgetSatisfaction, aSatisfaction)
							.addSetClause(this.optBudgetType, aType)
							.addSetClause(this.optBudgetQuantity, aQuantity)
							.addCondition(BinaryCondition.equalTo(this.optBudgetID, aBudgetID))
							.validate().toString();
		}else{
				sql = new UpdateQuery(this.budgetTable)
							.addSetClause(this.budgetName, aName)
							.addSetClause(this.budgetPrice, aPrice)
							.addSetClause(this.budgetSatisfaction, aSatisfaction)
							.addSetClause(this.budgetType, aType)
							.addSetClause(this.budgetQuantity, aQuantity)
							.addCondition(BinaryCondition.equalTo(this.budgetID, aBudgetID))
							.validate().toString();
		}
		
		
		if (this.dbDebug){
			this.dMsg("Update Budget #"+aBudgetID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Updates the budget list in bulk.
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int updateBudgetList(Vector<Item> list, int aEventID){
		return this.updateBudgetListGateway(list, aEventID, "normal");
	}
	
	
	
	/**
	 * Updates the Optimized budget list in bulk.
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int updateBudgetListOptimized(Vector<Item> list, int aEventID){
		return this.updateBudgetListGateway(list, aEventID, "optimized");
	}	
	
	

	/**
	 * Updating in bulk. Removes the selected budget list and adding a new given list.
	 * @param list
	 * @param aEventID
	 * @param aTableType
	 * @return
	 */
	private int updateBudgetListGateway(Vector<Item> list, int aEventID, String aTableType){
		if (this.dbDebug){
			this.dMsg("Update Budget");
			this.dMsg( " - Event ID #" + aEventID);
			this.dMsg( " - Type " + aTableType);
		}
		
		//Clear the entire list
		this.deleteBudgetListGateway(aEventID, aTableType);
		
		//add it back
		return this.addBudgetListGateway(list, aEventID, aTableType);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Delete Budget item
	 * @param aBudgetID
	 * @return
	 */
	public int deleteBudget(int aBudgetID){
		return this.deleteBudgetGateway(aBudgetID, "normal");
	}
	
	
	/**
	 * Delete optimized budget item
	 * @param aBudgetID
	 * @return
	 */
	public int deleteBudgetOptimized(int aBudgetID){
		return this.deleteBudgetGateway(aBudgetID, "optimized");
	}
	
	
	/**
	 * Deleting Budget Entries (SINGLE).
	 * @param aBudgetID
	 * @return
	 */
	private int deleteBudgetGateway(int aBudgetID, String aTableType){
		String sql = "";
		
		if (aTableType.compareTo("optimized") == 0){
			sql = new DeleteQuery(this.optBudgetTable)
						.addCondition(BinaryCondition.equalTo(this.optBudgetID, aBudgetID))
						.validate().toString();
		}else{
			sql = new DeleteQuery(this.budgetTable)
						.addCondition(BinaryCondition.equalTo(this.budgetID, aBudgetID))
						.validate().toString();
			
		}
		if (this.dbDebug){
			this.dMsg("DELETE BUDGET ID #" + aBudgetID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	/**
	 * Delete Budget List Entries
	 * @param aEventID
	 * @return
	 */
	public int deleteBudgetList(int aEventID){
		return this.deleteBudgetListGateway(aEventID, "normal");
	}
	
	
	
	/**
	 * Delete Optimized Budget Entries
	 * @param aEventID
	 * @return
	 */
	public int deleteBudgetListOptimized(int aEventID){
		return this.deleteBudgetListGateway(aEventID, "optimized");
	}
		
	
	
	
	/**
	 * Deleting Budget Entries (BULK).
	 * @param aEventID
	 * @param aTableType
	 * @return
	 */
	private int deleteBudgetListGateway(int aEventID, String aTableType){
		String sql = "";

		if (aTableType.compareTo("optimized") == 0){
				sql =	new DeleteQuery(this.optBudgetTable)
								.addCondition(BinaryCondition.equalTo(this.optBudgetEventID, aEventID))
								.validate().toString();
		}else{
				sql =	new DeleteQuery(this.budgetTable)
								.addCondition(BinaryCondition.equalTo(this.budgetEventID, aEventID))
								.validate().toString();
		}
		

		if (this.dbDebug){
			this.dMsg("DELETE BUDGET LIST " + aTableType);
			this.dMsg(sql);
		}
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}	

	
	
}
