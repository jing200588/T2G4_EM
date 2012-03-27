
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

    
    
    private DbTable 	optBudgetTable;
    private DbColumn 	optBudgetID;
    private DbColumn 	optBudgetEventID;
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

	public EMDBBudget(String aName){
		super(aName);
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
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Setup Budget: "+ sql);
			this.dMsg("Setup Budget Optimized: "+ sql2);
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
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Cleanup Budget: "+ sql);
			this.dMsg("Cleanup Budget Optimized: "+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		int tableTotal = 2;
		
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

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("VERIFICATION - BUDGET TABLES");
			this.dMsg(sql);
		}
		
		this.connect();
		
		Vector<Object[]> result = this.runQueryResults(sql);
		int count = result.size();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("VERIFIED SIZE: "+count);
		}
		
		
		this.disconnect();
		
		if (count == tableTotal)
			return true;
		
		return false;
		
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
	public int addBudget(int aEventID, String aName, int aPrice, int aSatisfaction, String aType){
		return this.addBudgetGateway(aEventID, aName, aPrice, aSatisfaction, aType, "normal");
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
	public int addBudgetOptimized(int aEventID, String aName, int aPrice, int aSatisfaction, String aType){
		return this.addBudgetGateway(aEventID, aName, aPrice, aSatisfaction, aType, "optimized");
	}	
	

	
	/**
	 * Gateway for Adding budget (SINGLE).
	 * @param aEventID
	 * @param aName
	 * @param aPrice
	 * @param aSatisfaction
	 * @param aType
	 * @param aTableType
	 * @return
	 */
	private int addBudgetGateway(int aEventID, String aName, int aPrice, int aSatisfaction, String aType, String aTableType){
		String sql = "";
		
		switch (aTableType){
			case "optimized":
				sql = new InsertQuery(this.optBudgetTable)
							.addColumn(this.optBudgetEventID, aEventID)
							.addColumn(this.optBudgetName, aName)
							.addColumn(this.optBudgetPrice, aPrice)
							.addColumn(this.optBudgetSatisfaction, aSatisfaction)
							.addColumn(this.optBudgetType, aType)
							.validate().toString();
				break;
			case "normal":
			default:
				sql = new InsertQuery(this.budgetTable)
							.addColumn(this.budgetEventID, aEventID)
							.addColumn(this.budgetName, aName)
							.addColumn(this.budgetPrice, aPrice)
							.addColumn(this.budgetSatisfaction, aSatisfaction)
							.addColumn(this.budgetType, aType)
							.validate().toString();
				break;
		}
			
		

		

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("ADD BUDGET");
			this.dMsg(sql);
		}
		
		Vector<Object[]> result = this.runQueryResults(sql);
		int id = 0;
		
		if (result.size() > 0){
			id = (int) result.get(0)[0];
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
		return this.addBudgetListGatway(list, aEventID, "normal");
	}
	
	
	/**
	 * Adding optimized budget list.
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int addBudgetListOptimized(Vector<Item> list, int aEventID){
		return this.addBudgetListGatway(list, aEventID, "optimized");
	}
	
	
	
	/**
	 * Gateway for Adding budget in bulk.
	 * @param list
	 * @param aEventID
	 * @param aTableType
	 * @return
	 */
	private int addBudgetListGatway(Vector<Item> list, int aEventID, String aTableType){
		
		int size = list.size();
		for (int i=0; i< size; i++){
			
			Item current = list.get(i);
			
			String sql = "";
			switch (aTableType){
				case "optimized":
					sql = new InsertQuery(this.optBudgetTable)
								.addColumn(this.optBudgetEventID, aEventID)
								.addColumn(this.optBudgetName, current.getItem())
								.addColumn(this.optBudgetPrice, current.getPrice())
								.addColumn(this.optBudgetSatisfaction, current.getSatisfaction_value())
								.addColumn(this.optBudgetType, current.getType())
								.validate().toString();
					break;
				case "normal":
				default:
					sql = new InsertQuery(this.budgetTable)
								.addColumn(this.budgetEventID, aEventID)
								.addColumn(this.budgetName, current.getItem())
								.addColumn(this.budgetPrice, current.getPrice())
								.addColumn(this.budgetSatisfaction, current.getSatisfaction_value())
								.addColumn(this.budgetType, current.getType())
								.validate().toString();
					break;
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
	public Vector<Item> getBudget(int aEventID){
		return this.getBudgetGateway(aEventID, "normal");
	}
	
	
	
	/**
	 * Getting the optimized list of budget.
	 * @return
	 */
	public Vector<Item> getBudgetOptimized(int aEventID){
		return this.getBudgetGateway(aEventID, "optimized");
	}
	
	
	
	/**
	 * Gateway for Getting a list of budgets.
	 * @param aTableType
	 * @return
	 */
	private Vector<Item> getBudgetGateway(int aEventID, String aTableType){	
		String sql = "";

		switch (aTableType){
			case "optimized":
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.optBudgetTable)
							.addCondition(BinaryCondition.equalTo(this.optBudgetEventID, aEventID))
							.validate().toString();
				break;
			case "normal":
			default:
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.budgetTable)
							.addCondition(BinaryCondition.equalTo(this.budgetEventID, aEventID))
							.validate().toString();
				break;
		}
		

		if (EMDBSettings.DEVELOPMENT){
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
								(int) 		row[0], 
								(String) 	row[2], 
								(int)	 	row[3], 
								(int) 		row[4], 
								(String) 	row[5]
							);
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
	public int updateBudget(int aBudgetID, String aName, int aPrice, int aSatisfaction, String aType){
		return this.updateBudgetGateway(aBudgetID, aName, aPrice, aSatisfaction, aType, "normal");
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
	public int updateBudgetOptimized(int aBudgetID, String aName, int aPrice, int aSatisfaction, String aType){
		return this.updateBudgetGateway(aBudgetID, aName, aPrice, aSatisfaction, aType, "optimized");
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
	private int updateBudgetGateway(int aBudgetID, String aName, int aPrice, int aSatisfaction, String aType, String aTableType){

		String sql = "";

		switch (aTableType){
			case "optimized":
				sql = new UpdateQuery(this.optBudgetTable)
							.addSetClause(this.optBudgetName, aName)
							.addSetClause(this.optBudgetPrice, aPrice)
							.addSetClause(this.optBudgetSatisfaction, aSatisfaction)
							.addSetClause(this.optBudgetType, aType)
							.addCondition(BinaryCondition.equalTo(this.optBudgetID, aBudgetID))
							.validate().toString();
				break;
			case "normal":
			default:
				sql = new UpdateQuery(this.budgetTable)
							.addSetClause(this.budgetName, aName)
							.addSetClause(this.budgetPrice, aPrice)
							.addSetClause(this.budgetSatisfaction, aSatisfaction)
							.addSetClause(this.budgetType, aType)
							.addCondition(BinaryCondition.equalTo(this.budgetID, aBudgetID))
							.validate().toString();
				break;
		}
		
		
		if (EMDBSettings.DEVELOPMENT){
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
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Update Budget");
			this.dMsg( " - " + aEventID);
			this.dMsg( " - " + aTableType);
		}
		
		//Clear the entire list
		this.deleteBudgetList(aEventID, aTableType);
		
		//add it back
		return this.addBudgetListGatway(list, aEventID, aTableType);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Deleting Budget Entries (SINGLE).
	 * @param aBudgetID
	 * @return
	 */
	public int deleteBudget(int aBudgetID){
		String sql =	new DeleteQuery(this.budgetTable)
								.addCondition(BinaryCondition.equalTo(this.budgetID, aBudgetID))
								.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE BUDGET ID #" + aBudgetID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Deleting Budget Entries (BULK).
	 * @param aEventID
	 * @param aTableType
	 * @return
	 */
	public int deleteBudgetList(int aEventID, String aTableType){
		String sql = "";

		switch (aTableType){
			case "optimized":
				sql =	new DeleteQuery(this.optBudgetTable)
								.addCondition(BinaryCondition.equalTo(this.optBudgetEventID, aEventID))
								.validate().toString();
				break;
			case "normal":
			default:
				sql =	new DeleteQuery(this.budgetTable)
								.addCondition(BinaryCondition.equalTo(this.budgetEventID, aEventID))
								.validate().toString();
				break;
		}
		

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE BUDGET LIST");
			this.dMsg(" - "+ aTableType);
			this.dMsg(sql);
		}
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}	

	
	
}