package budget;

import java.util.Iterator;
import java.util.Vector;
import emdb.*;

/**
 * 
 * @author Chua Hong Jing/Yeo Junzhi
 *
 */

public class ModelBudget {
	private int eventId;
	private Vector<Item> itemList;
	private Vector<Item> confirmList;

	EMDBII db;
	
	/**
	 * Description: Initialize database.
	 */

	public ModelBudget() {
		db = new EMDBII();
	}

	/**
	 * Description: Saving non-optimized list to database.
	 * @param inputId
	 * @param inputItemList
	 */
	public void saveNonOptimizedList(int inputId, Vector<Item> inputItemList) { 
		eventId = inputId;
		itemList = inputItemList;
		
		db.budgetDB().deleteBudgetList(inputId);
		db.budgetDB().addBudgetList(inputItemList, inputId);
		
		
	}

	/**
	 * Description: Saving optimized list to database.
	 * @param inputId
	 * @param inputItemList
	 */
	public void saveOptimizedList(int inputId, Vector<Item> inputItemList) { 
		eventId = inputId;
		confirmList = inputItemList;
		
		db.budgetDB().deleteBudgetListOptimized(inputId);
		db.budgetDB().addBudgetListOptimized(inputItemList, inputId);
		
	}

	/**
	 * Description: Update the non-optimize item list.
	 * @param inputId
	 * @param inputItemList
	 */
	public void updateNonOptimizeItemListwithCompulsory(int inputId, Vector<Item> inputItemList) {

		itemList = inputItemList;

		this.deleteNonOptimizeItemList(inputId);
		this.saveNonOptimizedList(inputId, inputItemList);

	}
	
	/**
	 * Description: Delete the non-optimize item list.
	 * @param inputId
	 */

	public void deleteNonOptimizeItemList(int inputId){

		db.budgetDB().deleteBudgetList(inputId);

	}
	
	/**
	 * Description: Retrieve the non-optimize item list.
	 * @param inputId
	 * @return
	 */
	public Vector<Item> getNonOptimizeItemList(int inputId) {
		
		itemList = db.budgetDB().getBudgetList(inputId);
		return itemList;
	}
	
	/**
	 * Description: Delete a single item from database.
	 * @param inputEvenId
	 * @param inputItemId
	 */
	public void deleteBudgetItem(int inputEvenId, int inputItemId) {
		
		db.budgetDB().deleteBudgetOptimized(inputItemId);
	}
	
	/**
	 * Description: Return the optimize item list upon request.
	 * @param inputId
	 * @return
	 */
	public Vector<Item> getOptimizeItemList(int inputId) {

		return db.budgetDB().getBudgetListOptimized(inputId);
	}

}
