package budget;

import java.util.BitSet;
import java.util.Vector;

public class Solution {
	Vector<BitSet> bitmask;
	Vector<Double> cost;
	int satisfactionValue;
	
	public Solution () {
		bitmask = new Vector<BitSet>();
		cost = new Vector<Double>();
		satisfactionValue = 0;
	}
	public void addSet (BitSet items, double totalcost) {
		bitmask.add(items);
		cost.add(totalcost);
	}
	
	public int getSvalue () {
		return satisfactionValue;
	}
	
	public void setSvalue (int value) {
		satisfactionValue = value;
	}
	
	public BitSet getOptimalSet () {
		double min = cost.get(0);
		int mini = 0;
		for (int i=1; i<cost.size(); i++) {
			if (cost.get(i) < min) {
				min = cost.get(i);
				mini = i;
			}
		}
	//	for (int i=0; i<cost.size(); i++)
	//	System.out.println(cost.get(i));
		return bitmask.get(mini);	
	}
	
	public double getOptimalCost () {
		double min = cost.get(0);
		for (int i=1; i<cost.size(); i++) {
			if (cost.get(i) < min)
				min = cost.get(i);
		}
		return min;	
	}
	
	public Vector<BitSet> getSolnSet() {
		return bitmask;
	}
	
	public Vector<Double> getSolnCostSet() {
		return cost;
	}
	
	public int getSolnSetSize() {
		return bitmask.size();
	}
	
	public void clearAll () {
		bitmask.clear();
		cost.clear();
	}
	
	
}
