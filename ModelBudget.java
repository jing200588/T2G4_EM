/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */
import java.util.Iterator;
import java.util.Vector;


public class ModelBudget {
	private int event_id;
	private Vector<Item> item_list;
	private Vector<Item> confirm_list;

	EMDB db;
	
	/**
	 * Description: Initialize database.
	 */

	public ModelBudget() {
		db = new EMDB();
		db.set_name(EMSettings.DATABASE_NAME);
	}

	/**
	 * Description: Saving non-optimized list to database.
	 * @param id
	 * @param i_list
	 */
	public void received_item_list(int id, Vector<Item> i_list) { 
		event_id = id;
		item_list = i_list;

		db.connect();

		db.delete_budget_list(id);


		int count = i_list.size();
		db.add_prepare("budget");
		for (int i=0; i < count; i++){
			Item current = i_list.get(i);
			db.add_budget(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), true);		
		}
		db.add_batch_commit();
		db.disconnect();
	}

	/**
	 * Description: Saving optimized list to database.
	 * @param id
	 * @param i_list
	 */
	public void recevied_combination_list(int id, Vector<Item> i_list) { 
		event_id = id;
		confirm_list = i_list;

		db.connect();

		int count = i_list.size();
		db.add_prepare("budgetOptimized");
		for (int i=0; i < count; i++){
			Item current = i_list.get(i);
			db.add_budget_optimized(event_id, current.getItem(), current.getPrice(), current.getSatisfaction_value(), current.getType(), true);	
		}
		db.add_batch_commit();
		db.disconnect();	
	}

	/**
	 * Description: Update the non-optimize item list.
	 * @param id
	 * @param i_list
	 */
	public void update_item_list(int id, Vector<Item> i_list) {

		item_list = i_list;

		this.delete_item_list(id);
		this.received_item_list(id, i_list);

	}
	
	/**
	 * Description: Delete the non-optimize item list.
	 * @param id
	 */

	public void delete_item_list(int id){
		db.connect();
		db.delete_budget_list(id);
		db.disconnect();

	}
	
	/**
	 * Description: Retrieve the non-optimize item list.
	 * @param id
	 * @return
	 */
	public Vector<Item> return_item_list(int id) {

		db.connect();
		item_list = db.get_budget_list(id, false);
		db.disconnect();

		return item_list;
	}
	
	/* To be use in v0.2
	public void update_budget(int id, double budget) {
		//event_object.getID(), ((double)budget)/100);
	}
	*/

}
