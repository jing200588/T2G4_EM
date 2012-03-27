/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */
import java.util.Iterator;
import java.util.Vector;



public class ModelBudget {
	private int event_id;
	private Vector<Item> item_list;
	private Vector<Item> confirm_list;

	EMDBII db;
	
	/**
	 * Description: Initialize database.
	 */

	public ModelBudget() {
		db = new EMDBII();
	}

	/**
	 * Description: Saving non-optimized list to database.
	 * @param id
	 * @param i_list
	 */
	public void saveNonOptimizedList(int id, Vector<Item> i_list) { 
		event_id = id;
		item_list = i_list;
/*
		db.connect();

		db.delete_budget_list(id);


		int count = i_list.size();
		db.add_prepare("budget");
		for (int i=0; i < count; i++){
			Item current = i_list.get(i);
			db.add_budget(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), true);		
			//db.add_budget(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), current.getQuantity(), true);		
		}
		db.add_batch_commit();
		db.disconnect();
		*/
		
		db.budgetDB().deleteBudgetList(id);
		db.budgetDB().addBudgetList(i_list, id);
		
		
	}

	/**
	 * Description: Saving optimized list to database.
	 * @param id
	 * @param i_list
	 */
	public void saveOptimizedList(int id, Vector<Item> i_list) { 
		event_id = id;
		confirm_list = i_list;
		
		/*
		db.connect();

		int count = i_list.size();
		db.add_prepare("budgetOptimized");
		for (int i=0; i < count; i++){
			Item current = i_list.get(i);
			db.add_budget_optimized(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), true);	
			//db.add_budget(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), current.getQuantity(), true);
		}
		db.add_batch_commit();
		db.disconnect();	
		*/
		db.budgetDB().deleteBudgetListOptimized(id);
		db.budgetDB().addBudgetListOptimized(i_list, id);
		
	}

	/**
	 * Description: Update the non-optimize item list.
	 * @param id
	 * @param i_list
	 */
	public void updateNonOptimizeItemListwithCompulsory(int id, Vector<Item> i_list) {

		item_list = i_list;

		this.deleteNonOptimizeItemList(id);
		this.saveNonOptimizedList(id, i_list);

	}
	
	/**
	 * Description: Delete the non-optimize item list.
	 * @param id
	 */

	public void deleteNonOptimizeItemList(int id){
		/*
		db.connect();
		db.delete_budget_list(id);
		db.disconnect();*/
		db.budgetDB().deleteBudgetList(id);

	}
	
	/**
	 * Description: Retrieve the non-optimize item list.
	 * @param id
	 * @return
	 */
	public Vector<Item> getNonOptimizeItemList(int id) {
/*
		db.connect();
		item_list = db.get_budget_list(id, false);
		db.disconnect();
*/
		
		item_list = db.budgetDB().getBudgetList(id);
		return item_list;
	}
	
	/* To be use in v0.2
	public void update_budget(int id, double budget) {
		//event_object.getID(), ((double)budget)/100);
	}
	*/

}
