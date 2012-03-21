import java.io.IOException;
import java.text.DecimalFormat;
import java.util.BitSet;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;


public class ControllerBudget {
	private DecimalFormat myFormatter = new DecimalFormat("##.##");
	private int number; //store the number of item to undergo permutation algorithm
	private double budget, recursive_fnc_budget; 
	private Solution soln;
	private Eventitem event_object;
	private int typeOfResult; //this will be use to determine how the database list will be manipulated.

	private Vector<Item> compute_list; //store the item(s) to undergo permutation algorithm
	private Vector<Item> compulsory_list;
	private Vector<Item> item_list; //store the initial item list input by the user
	private int temp_budget, non_compulsory_cost, compulsory_cost, compulsory_satisfaction, non_compulsory_satisfaction;
	private int totalCombination; 
	private Vector<Item> db_list;//store the list to be send to database

	private ModelBudget bm = new ModelBudget();

	/**
	 * Description: Create a constructor that take in the input and process them into item list.
	 * @param input
	 * @param budget
	 * @param type_c
	 * @param satisfaction_c
	 * @param ei
	 * @throws Exception
	 */
	public ControllerBudget(String input, int budget, int type_c, int satisfaction_c, Eventitem ei) throws Exception {

		item_list = new Vector<Item>();
		event_object = ei;
		Scanner sc = new Scanner(input);
		String line;
		String[] component;
		String name;
		double cost;
		int satisfaction;
		String type;
		this.budget = ((double)budget)/100;

		while(sc.hasNextLine()) {
			line = sc.nextLine();
			if(line.length() > 0) {
				component = line.split(" ");
				name = component[0];
				cost = Double.parseDouble(component[1]);
				if(cost < 0) throw new IOException("Cost should not be negative");
				if (type_c == 1) { //has type
					if(satisfaction_c == 1) { //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
						type = component[3];
					}
					else { //no satisfaction
						satisfaction = -1;
						type = component[2];
					}
					item_list.add(new Item(name, cost, satisfaction, type));
				}
				else { //no type
					if(satisfaction_c == 1) //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
					else  //no satisfaction
						satisfaction = -1;
					item_list.add(new Item(name, cost, satisfaction));
				}
			}
		}
		bm.saveNonOptimizedList(event_object.getID(), item_list); //save to database non optimized item list
	}

	/**
	 * Description: Return item list.
	 * @return
	 */

	public Vector<Item> getItemList() {
		return item_list;		
	}

	/**
	 * Description: Differentiate the item list into compulsory list and compute list.
	 * @param com
	 * @param satisfaction_choice
	 */
	public void differentiateCompulsory(Vector<Integer> com, int satisfaction_choice) {
		temp_budget = (int) (budget*100);
		compute_list = (Vector<Item>) item_list.clone();
		compulsory_list = new Vector<Item>();
		if(com.size() > 0) {
			for(int i=0; i<item_list.size(); i++) {
				item_list.get(i).setCompulsory('N');
			}
			for(int i=com.size()-1; i>=0; i--) {
				compute_list.remove(com.get(i)-1);
				item_list.get(com.get(i)-1).setCompulsory('Y');
				compulsory_list.add(item_list.get(com.get(i)-1));
				temp_budget = temp_budget - item_list.get(com.get(i)-1).getPrice();
			}

			bm.updateNonOptimizeItemListwithCompulsory(event_object.getID(), item_list); //update with compulsory flag
		}
		number = compute_list.size();

		differentiateCostandSatisfaction(satisfaction_choice);
	}

	/**
	 * Description: Differentiate the cost and satisfaction between compulsory list and compute list.
	 * @param satisfaction_choice
	 */

	public void differentiateCostandSatisfaction(int satisfaction_choice) {
		compulsory_cost = 0;
		compulsory_satisfaction = 0;
		non_compulsory_cost = 0;
		non_compulsory_satisfaction = 0;

		for(int i=0; i<item_list.size(); i++) {
			if(item_list.get(i).getCompulsory() == 'Y') 
			{
				compulsory_cost = compulsory_cost + item_list.get(i).getPrice();
				if(satisfaction_choice == 1)
					compulsory_satisfaction = compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
			}
			else {
				non_compulsory_cost = non_compulsory_cost + item_list.get(i).getPrice();
				if(satisfaction_choice == 1)
					non_compulsory_satisfaction = non_compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
			}
		}	
	}

	/**
	 * Description: Return budget remain after deducting the compulsory items cost.
	 * @return
	 */

	public double budgetleft() {
		double left = ((double) temp_budget)/100;
		return left;
	}

	/**
	 * Description: Initial various variable required for permutation algorithm.
	 * @param hastype
	 * @param hassatisfaction
	 * @return
	 */
	public String findOptimalShopList(int hastype, int hassatisfaction) {
		soln = new Solution();
		recursive_fnc_budget = ((double) temp_budget)/100;	
		return analysis(hastype, hassatisfaction);
	}

	/**
	 * Description: Calling the respective method according to type and satisfaction option.
	 * @param type
	 * @param satisfaction_choice
	 * @return
	 */
	public String analysis(int type, int satisfaction_choice) {
		String resultText = "";
		typeOfResult = 0;
		int noOfCombination = 0;
		Collections.sort(compute_list);

		if(compulsory_list.size() == item_list.size()){ //All items checked as compulsory and budget is enough to buy all.
			typeOfResult = 1;
			noOfCombination = 1;	
		}
		else if(non_compulsory_cost <= temp_budget) { //cost of all non compulsory items is lower then budget, buy all.
			typeOfResult = 1;
			noOfCombination = 1;	
		}
		else if(compute_list.get(compute_list.size()-1).getPrice() > temp_budget) { //budget left is not enough to buy the cheapest items left
			if (compute_list.size() == item_list.size()) {//no compulsory item
				typeOfResult = 0; // take nothing
				noOfCombination = 0;

			}
			else {
				typeOfResult = 2; // take compulsory items only
				noOfCombination = 1;
			}
		}
		else { //use solution set algorithm
			typeOfResult = 3;
			if(type == 1)
				generateType(satisfaction_choice);
			else
				generate(satisfaction_choice);

			noOfCombination = soln.getSolnSetSize();
			for(int i=0 ;i<soln.getSolnSetSize(); i++) {
				if(soln.getSolnSet().get(i).isEmpty() == true)
					noOfCombination--;
			}
			if(noOfCombination == 0)
				typeOfResult = 4; //No solution set.
		}

		totalCombination = noOfCombination;
		resultText = displayResultNoSolutionSet(satisfaction_choice, typeOfResult, noOfCombination); 
		return resultText;
	}

	public String displayResultNoSolutionSet(int satisfaction_choice, int type, int combination) {
		String text = "";

		int index;

		if(type == 0) 
			text = "The budget is not enough to buy anything. \nIncrease your budget and try again.";
		else if(type == 1) {
			index = 1;
			if(satisfaction_choice == 1)
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + (compulsory_satisfaction+non_compulsory_satisfaction) + "\nTotal cost: \t$" + myFormatter.format(((double) (compulsory_cost+non_compulsory_cost))/100) +"\n";
			else
				text = "Total combination:\t"+combination+"\nTotal cost: \t$" + myFormatter.format(((double) (compulsory_cost+non_compulsory_cost))/100) +"\n";	
			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				text += concatItemListResult(index, i, satisfaction_choice, 1); // 1 refer to take initial item list
				index++;
			}
		}
		else if(type == 2) {
			index = 1;
			if (satisfaction_choice == 1) 
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + compulsory_satisfaction + "\nTotal cost: $\t" + myFormatter.format(((double) compulsory_cost)/100) +"\n";
			else
				text = "Total combination:\t"+combination+"\nTotal cost: $\t" + myFormatter.format(((double) compulsory_cost)/100) +"\n";
			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				if(item_list.get(i).getCompulsory() == 'Y') 
				{
					text += concatItemListResult(index, i, satisfaction_choice, 1); // 1 refer to take initial item list
					index++;
				}
			}
		}
		else if (type == 3) {
			if (satisfaction_choice == 1)
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + (soln.getSvalue()+compulsory_satisfaction) +"\n";
			else
				text = "Total combination:\t"+combination+"\n";

			int solutionCounter = 1;
			for(int i=0; i<soln.getSolnSetSize(); i++) {
				BitSet bitmask = soln.getSolnSet().get(i);
				if(bitmask.isEmpty() == false) {
					text +="***********Combination "+solutionCounter+"***********\n";
					index = 1;
					text +="Total cost: \t$" + myFormatter.format((soln.getSolnCostSet().get(i)+((double) compulsory_cost)/100)) +"\n";
					for(int k=0; k<compulsory_list.size();k++) {
						text += concatItemListResult(index, k, satisfaction_choice, 2); // 2 refer to take compulsory item list
						index++;
					}	
					for(int j=0; j<number; j++) {
						if(bitmask.get(j)) {
							text += concatItemListResult(index, j, satisfaction_choice, 3); // 2 refer to take compulsory item list
							index++;
						}
					}
					solutionCounter++;
				}
			}
		}
		else if (type == 4) {
			text = "No solution found.";
		}
		text+="\n\n";
		return text;
	}

	public String concatItemListResult(int index, int currentItem, int satisfaction_choice, int type) {
		String text = "";
		double price;
		Vector<Item> concat_list = null;
		if(type == 1)
			concat_list = item_list;
		else if (type == 2)
			concat_list = compulsory_list;
		else if(type == 3)
			concat_list = compute_list;

		text +=index+"\t";
		text +=concat_list.get(currentItem).getItem()+" for $";
		price = ((double) concat_list.get(currentItem).getPrice())/100;
		if(satisfaction_choice == 1) {
			text += price + " for ";
			text +=concat_list.get(currentItem).getSatisfaction_value()+" satisfaction.\n";
		}
		else {
			text += price+"\n";
		}
		return text;
	}

	/**
	 * Description: Type choice is flag as false and it will call the respective method according to satisfaction option.
	 * @param satisfaction_choice
	 */
	public  void generate(int satisfaction_choice) {
		BitSet bitmask = new BitSet(number);
		if(satisfaction_choice == 1) {
			for (int i=0; i<number; i++)
				recurse(i, bitmask, recursive_fnc_budget, 0);
		}
		else {
			for (int i=0; i<number; i++)
				recurseNoSatisfaction(i, bitmask, recursive_fnc_budget);
		}
	}

	/**
	 * Description: Algorithm that will generate the list of item combination with no satisfaction and no type option.
	 * @param cur
	 * @param bitmask
	 * @param subbudget
	 */

	public  void recurseNoSatisfaction (int cur, BitSet bitmask, double subbudget) {

		BitSet bitmask2 = (BitSet) bitmask.clone();
		if (((double) compute_list.get(cur).getPrice())/100 <= subbudget) {
			subbudget -= ((double) compute_list.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseNoSatisfaction(i, bitmask2 , subbudget);
		}

		if (DupCheck(bitmask2) == 0) {
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
		}
	}

	/**
	 * Description: Algorithm that will generate the list of item combination with satisfaction but no type option.
	 * @param cur
	 * @param bitmask
	 * @param subbudget
	 * @param max
	 */

	public  void recurse (int cur, BitSet bitmask, double subbudget, int max) {

		BitSet bitmask2 = (BitSet) bitmask.clone();
		if (((double) compute_list.get(cur).getPrice())/100 <= subbudget) {
			max += compute_list.get(cur).getSatisfaction_value();
			subbudget -= ((double) compute_list.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurse(i, bitmask2 , subbudget, max);
		}

		//by right only the deepest level will perform this
		if (max > soln.getSvalue()) {
			soln.setSvalue(max);
			soln.clearAll();
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
		}

		else if (max == soln.getSvalue() && DupCheck(bitmask2) == 0) {
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
		}
	}

	/**
	 * Description: Prevent duplicate item.
	 * @param bitmask
	 * @return
	 */
	public  int DupCheck(BitSet bitmask) {
		for (int i=0; i<soln.getSolnSetSize(); i++) {
			if (soln.getSolnSet().get(i).hashCode() == bitmask.hashCode())
				return 1;
		}
		return 0;
	}
	/**
	 * Description: Type choice is flag as true and it will call the respective method according to satisfaction choice.
	 * @param satisfaction_choice
	 */

	public void generateType(int satisfaction_choice) {
		BitSet bitmask = new BitSet(number);
		if(satisfaction_choice == 1) {
			for (int i=0; i<number; i++) {
				recurseType(i, bitmask, recursive_fnc_budget, 0);
			}
		}
		else {
			for (int i=0; i<number; i++) {
				recurseTypeNoSatisfaction(i, bitmask, recursive_fnc_budget);
			}
		}
	}

	/**
	 * Description: Algorithm that will generate the list of item combination with type but no satisfaction option.
	 * @param cur
	 * @param bitmask
	 * @param subbudget
	 */

	public  void recurseTypeNoSatisfaction (int cur, BitSet bitmask, double subbudget) {

		BitSet bitmask2 = (BitSet) bitmask.clone();
		if (((double) compute_list.get(cur).getPrice())/100 <= subbudget && TypeChecker(bitmask2, cur) == 0) {
			subbudget -= ((double) compute_list.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseTypeNoSatisfaction(i, bitmask2 , subbudget);
		}
		if (DupCheck(bitmask2) == 0)
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
	}

	/**
	 * Description: Algorithm that will generate the list of item combination with type and satisfaction option.
	 * @param cur
	 * @param bitmask
	 * @param subbudget
	 * @param max
	 */

	public  void recurseType (int cur, BitSet bitmask, double subbudget, int max) {

		BitSet bitmask2 = (BitSet) bitmask.clone();
		if (((double) compute_list.get(cur).getPrice())/100 <= subbudget && TypeChecker(bitmask2, cur) == 0) {
			max += compute_list.get(cur).getSatisfaction_value();
			subbudget -= ((double) compute_list.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseType(i, bitmask2 , subbudget, max);
		}

		//by right only the deepest level will perform this
		if (max > soln.getSvalue()) {
			soln.setSvalue(max);
			soln.clearAll();
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
		}
		else if (max == soln.getSvalue() && DupCheck(bitmask2) == 0)
			soln.addSet(bitmask2, recursive_fnc_budget - subbudget);
	}

	/**
	 * Description: Prevent duplicated type.
	 * @param bitmask
	 * @param cur
	 * @return
	 */

	public  int TypeChecker (BitSet bitmask, int cur) {
		for (int i=0; i<number; i++) {
			if (bitmask.get(i)) {
				if (compute_list.get(i).getType().compareTo(compute_list.get(cur).getType()) == 0) 
					return 1;
			}
		}
		return 0;
	}

	/**
	 * Description: Return the total combination generated.
	 * @return
	 */
	public int noOfCombination() {

		return totalCombination;
	}

	/**
	 * Description: Send the confirm combination list to database.
	 * @param select
	 */

	public void saveOptimizeOption(int select) {
		db_list = new Vector<Item>();

		if(typeOfResult == 1) { //take all item. Budget is enough to buy everything.
			for(int i=0; i<item_list.size(); i++) {
				db_list.add(item_list.get(i));
			}
		}
		else if (typeOfResult == 2) {//Budget is only enough to buy compulsory item. Take compulsory list*
			for(int i=0; i<compulsory_list.size(); i++) {
				db_list.add(compulsory_list.get(i));
			}
		}
		else if (typeOfResult == 3) { //There is solution set

			//If there is compulsory list we will add them first.
			for(int i=0; i<compulsory_list.size(); i++) {
				db_list.add(compulsory_list.get(i));
			}


			BitSet bitmask = soln.getSolnSet().get(select);
			for(int i=0; i<number; i++) {
				if(bitmask.get(i)) {
					db_list.add(compute_list.get(i));
				}
			}
		}

		for(int i =0; i<db_list.size(); i++)
		{
			System.out.println(db_list.get(i).getItem());
		}

		bm.saveOptimizedList(event_object.getID(), db_list);
		event_object.setitem_list(db_list);
	}
}
