/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */

import java.util.Vector;

public class Triple implements Comparable<Object> {
	int satisfaction;
	int cost;
	Vector<Item> combination;

	public Triple(int f, int s, Vector<Item> t) {
		this.satisfaction = f;
		this.cost = s;
		this.combination = t;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Vector<Item> getCombination() {
		return combination;
	}

	public void setCombination(Vector<Item> combination) {
		this.combination = combination;
	}
	
	public int compareTo(Object o) {
		if(this.satisfaction != ((Triple) o).getSatisfaction())
			return ((Triple) o).getSatisfaction() - this.satisfaction;
		else
			return this.cost - ((Triple) o).getCost();

	}
}


