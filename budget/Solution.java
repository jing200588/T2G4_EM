package budget;

import java.util.BitSet;
import java.util.Vector;

public class Solution {
	Vector<BitSet> bitmask;
	Vector<Double> cost;
	int satisfactionValue;
	
	/**
	 * Description: Initialize solution property.
	 */
	
	public Solution () {
		bitmask = new Vector<BitSet>();
		cost = new Vector<Double>();
		satisfactionValue = 0;
	}
	
	/**
	 * Description: Add a new solution set to result.
	 * @param items
	 * @param totalcost
	 */
	public void addSet (BitSet items, double totalcost) {
		bitmask.add(items);
		cost.add(totalcost);
	}
	
	/**
	 * Description: Return solution set satisfaction level.
	 * @return
	 */
	public int getSatisfactionvalue () {
		return satisfactionValue;
	}
	
	/**
	 * Description: Set solution set satisfaction level.
	 * @param value
	 */
	
	public void setSatisfactionvalue (int value) {
		satisfactionValue = value;
	}
	
	/**
	 * Description: Return the most optimize solution set.
	 * @return
	 */
	
	public BitSet getOptimalSet () {
		double min = cost.get(0);
		int mini = 0;
		for (int i=1; i<cost.size(); i++) {
			if (cost.get(i) < min) {
				min = cost.get(i);
				mini = i;
			}
		}
		return bitmask.get(mini);	
	}
	
	/**
	 * Description: Return the most optimize solution set cost.
	 * @return
	 */
	public double getOptimalCost () {
		double min = cost.get(0);
		for (int i=1; i<cost.size(); i++) {
			if (cost.get(i) < min)
				min = cost.get(i);
		}
		return min;	
	}
	
	/**
	 * Description: Return solution set.
	 * @return
	 */
	public Vector<BitSet> getSolnSet() {
		return bitmask;
	}
	
	/**
	 * Description: Return solution set cost.
	 * @return
	 */
	public Vector<Double> getSolnCostSet() {
		return cost;
	}
	
	/**
	 * Description: Return number of solution set in the result.
	 * @return
	 */
	public int getSolnSetSize() {
		return bitmask.size();
	}
	
	/**
	 * Description: Remove all data.
	 */
	public void clearAll () {
		bitmask.clear();
		cost.clear();
	}
	
	
}
