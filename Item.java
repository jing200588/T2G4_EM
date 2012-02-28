/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */

public class Item implements Comparable {
	private String item;
	private int price;
	private int satisfaction_value;
	private char compulsory;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Item(String input_item, double input_price, int input_value) {
		this.item = input_item;
		this.price = (int) (input_price*100);
		this.satisfaction_value = input_value;
		this.compulsory = 'N';
	}
	
	public Item(String input_item, double input_price, int input_value, String type) {
		this.item = input_item;
		this.price = (int) (input_price*100);
		this.satisfaction_value = input_value;
		this.type = type;
		this.compulsory = 'N';
	}
	
	public char getCompulsory() {
		return compulsory;
	}

	public void setCompulsory(char compulsory) {
		this.compulsory = compulsory;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSatisfaction_value() {
		return satisfaction_value;
	}

	public void setSatisfaction_value(int satisfaction_value) {
		this.satisfaction_value = satisfaction_value;
	}
	
	public int compareTo(Object o) {
		if(this.price != ((Item) o).getPrice())
			return ((Item) o).getPrice() - this.price;
		else if (this.satisfaction_value != ((Item) o).getSatisfaction_value())
			return ((Item) o).getSatisfaction_value() - this.satisfaction_value;
		else
			return this.getItem().hashCode() - ((Item) o).getItem().hashCode();
			
	}
}
