import java.util.Vector;


public class BudgetModel {
	private int event_id;
	private Vector<Item> item_list;
	private Vector<Item> confirm_list;
	
	public void received_item_list(int id, Vector<Item> i_list) { //saving to DB the initial item list
		event_id = id;
		item_list = i_list;
	}
	
	public Vector<Item> return_item_list(int id) {
		//db get id get list
		return item_list;
	}
	
	public void update_item_list(int id, Vector<Item> i_list) {
		//db get id update list
		item_list = i_list;
	}
	
	public void recevied_combination_list(int id, Vector<Item> i_list) { //saving to DB the combination list
		event_id = id;
		confirm_list = i_list;
	}
	
	public Vector<Item> displayConfirm() { //for testing
		return confirm_list;
	}

}
