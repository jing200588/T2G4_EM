package budget;

/**
 * 
 * @author Chua Hong Jing
 *
 */
public class Item implements Comparable {
	private int id;
	private String item;
	private int price;
	private int satisfactionValue;
	private char compulsory;
	private String type;
	private int quantity;
	

	/**
	 * Description: Create item object with no type.
	 * @param inputItem
	 * @param inputPrice
	 * @param inputValue
	 */
	public Item(String inputItem, double inputPrice, int inputValue) {
		this.item = inputItem;
		this.price = (int) (inputPrice*100);
		this.satisfactionValue = inputValue;
		this.compulsory = 'N';
		this.quantity = 1;
	}
	
	/**
	 * Description: Create item object with type.
	 * @param inputItem
	 * @param inputPrice
	 * @param inputValue
	 * @param inputType
	 */
	public Item(String inputItem, double inputPrice, int inputValue, String inputType) {
		this.item = inputItem;
		this.price = (int) (inputPrice*100);
		this.satisfactionValue = inputValue;
		this.type = inputType;
		this.compulsory = 'N';
		this.quantity = 1;
	}
	
	/**
	 * Description: Create item object with type for database manipulation.
	 * @param id
	 * @param inputItem
	 * @param inputPrice
	 * @param inputValue
	 * @param inputType
	 */
	public Item(int id, String inputItem, int inputPrice, int inputValue, String inputType) {
	this.id = id;
	this.item = inputItem;
	this.price = inputPrice;
	this.satisfactionValue = inputValue;
	this.type = inputType;
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
	public int getSatisfactionValue() {
		return satisfactionValue;
	}
	
	/**
	 * Decription: Set Satisfaction value of item
	 * @param satisfactionValue
	 */
	public void setSatisfactionValue(int satisfactionValue) {
		this.satisfactionValue = satisfactionValue;
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
		else if (this.satisfactionValue != ((Item) o).getSatisfactionValue())
			return ((Item) o).getSatisfactionValue() - this.satisfactionValue;
		else
			return this.getItem().hashCode() - ((Item) o).getItem().hashCode();
			
	}
}
