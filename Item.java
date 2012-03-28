/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */

public class Item implements Comparable {
	private int id;
	private String item;
	private int price;
	private int satisfaction_value;
	private char compulsory;
	private String type;
	private int quantity;
	

	/**
	 * Description: Create item object with no type.
	 * @param input_item
	 * @param input_price
	 * @param input_value
	 */
	public Item(String input_item, double input_price, int input_value) {
		this.item = input_item;
		this.price = (int) (input_price*100);
		this.satisfaction_value = input_value;
		this.compulsory = 'N';
		this.quantity = 1;
	}
	
	/**
	 * Description: Create item object with type.
	 * @param input_item
	 * @param input_price
	 * @param input_value
	 * @param type
	 */
	public Item(String input_item, double input_price, int input_value, String type) {
		this.item = input_item;
		this.price = (int) (input_price*100);
		this.satisfaction_value = input_value;
		this.type = type;
		this.compulsory = 'N';
		this.quantity = 1;
	}
	
	/**
	 * Description: Create item object with type for database manipulation.
	 * @param id
	 * @param input_item
	 * @param input_price
	 * @param input_value
	 * @param type
	 */
	public Item(int id, String input_item, int input_price, int input_value, String type) {
	this.id = id;
	this.item = input_item;
	this.price = input_price;
	this.satisfaction_value = input_value;
	this.type = type;
	this.compulsory = 'N';
	this.quantity = 1;
	}
	
	
	/**
	 * Description: Return item id.
	 * @return
	 */
	public int getID(){
		return this.id;
	}

	/**
	 * Description: Return item compulsory status.
	 * @return
	 */
	public char getCompulsory() {
		return compulsory;
	}
	
	/**
	 * Description: Set item compulsory status.
	 * @param compulsory
	 */

	public void setCompulsory(char compulsory) {
		this.compulsory = compulsory;
	}

	/**
	 * Description: Return item name.
	 * @return
	 */
	public String getItem() {
		return item;
	}
	
	/**
	 * Description: Set item name.
	 * @param item
	 */

	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * Decription: Return price of item.
	 * @return
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Decription: Set price of item.
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * Decription: Return satisfaction value of item.
	 * @return
	 */
	public int getSatisfaction_value() {
		return satisfaction_value;
	}
	
	/**
	 * Decription: Set Satisfaction value of item
	 * @param satisfaction_value
	 */
	public void setSatisfaction_value(int satisfaction_value) {
		this.satisfaction_value = satisfaction_value;
	}
	
	/**
	 * Description: Return item type.
	 * @return
	 */
	
	public String getType() {
		return type;
	}
	
	/**
	 * Description: Set item type.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Description: Get item quantity
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Description: Set item quantity
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	/**
	 * Decription: Compare price follow by satisfaction between 2 item.
	 */
	
	public int compareTo(Object o) {
		if(this.price != ((Item) o).getPrice())
			return ((Item) o).getPrice() - this.price;
		else if (this.satisfaction_value != ((Item) o).getSatisfaction_value())
			return ((Item) o).getSatisfaction_value() - this.satisfaction_value;
		else
			return this.getItem().hashCode() - ((Item) o).getItem().hashCode();
			
	}
}
