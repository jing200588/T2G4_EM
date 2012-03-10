/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */
import java.io.IOException;
import java.util.*;

import org.eclipse.ui.internal.handlers.WizardHandler.New;

public class ControllerBudget{

	private int number; //store the number of item to undergo permutation algorithm
	private double budget, recursive_fnc_budget; 
	private Solution soln;
	private int differentResult; //differentiate between the different cases that will happen in result printing
	private boolean hasSolnSet; //check if the current list undergo permutation algorithm
	private boolean hasEmptySet;
	private Eventitem event_object;

	private Vector<Item> compute_list; //store the item(s) to undergo permutation algorithm
	private Vector<Item> compulsory_list;
	private Vector<Item> item_list; //store the initial item list input by the user
	private int temp_budget, non_compulsory_cost, compulsory_cost, compulsory_satisfaction, non_compulsory_satisfaction;
	private int totalCombination; 
	private Vector<Item> db_list;//store the list to be send to database

	private ModelBudget bm = new ModelBudget();

	public ControllerBudget() {

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

	public ControllerBudget(String input, int budget, int type_c, int satisfaction_c, Eventitem ei) throws Exception {

		if(input.length() == 0) throw new IOException("Input list must not be empty.");
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

		if(type_c == 1) { //has type
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				if(line.length() > 0) {
					component = line.split(" ");
					name = component[0];
					cost = Double.parseDouble(component[1]);
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
			}
		}
		else { //no type
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				if(line.length() > 0) {
					component = line.split(" ");
					name = component[0];
					cost = Double.parseDouble(component[1]);
					if(cost < 0) 
						throw new IOException("Cost should not be negative");
					if(satisfaction_c == 1) //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
					else  //no satisfaction
						satisfaction = -1;
					item_list.add(new Item(name, cost, satisfaction));
				}
			}

		}
		bm.received_item_list(event_object.getID(), item_list); //SEND TO DATABASE
	}

	/**
	 * Description: Return item list.
	 * @return
	 */

	public Vector<Item> getItemList() {
		return item_list;
	}

	/**
	 * Description: Change the compulsory field in item that has been checked.
	 * @param com
	 */
	public void compulsory(Vector<Integer> com) {

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

			bm.update_item_list(event_object.getID(), item_list); //update with compulsory flag
		}
		number = compute_list.size();
	}

	/**
	 * Description: Return budget remain after deducting the compulsory items cost.
	 * @return
	 */
	public String budgetleft() {
		double left = ((double) temp_budget)/100;
		return ""+left;
	}

	/**
	 * Description: Differentiate the item list into compulsory list and compute list.
	 * @param satisfaction_choice
	 */

	public void differentiateCompulsory(int satisfaction_choice) {
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
	 * Description: Calling the respective method according to type and satisfaction option.
	 * @param type
	 * @param satisfaction_choice
	 * @return
	 */
	public String analysis(int type, int satisfaction_choice) {
		String text = "";
		double price;
		int compulsory_num = 1;
		int num;
		Collections.sort(compute_list);
		differentResult = 0;
		hasSolnSet = false;
		hasEmptySet = false;
		int non_compulsory_num=1;
		if(compulsory_list.size() == item_list.size()){ //All items checked as compulsory and budget is enough to buy all.
			differentResult = 1; // (Whole item list)
			totalCombination = 1;
			if(satisfaction_choice == 1)
				text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + compulsory_satisfaction + "\nTotal cost: \t$" + ((double) (compulsory_cost))/100 +"\n";
			else
				text = "Total combination:\t"+totalCombination+"\nTotal cost: \t$" + ((double) (compulsory_cost))/100 +"\n";	
			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				text +=non_compulsory_num+"\t";
				text +=item_list.get(i).getItem()+" for $";
				price = ((double) item_list.get(i).getPrice())/100;
				if(satisfaction_choice == 1) {
					text += price + " for ";
					text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
				}
				else {
					text += price+"\n";
				}
				non_compulsory_num++;
			}
		}
		else if(non_compulsory_cost <= temp_budget) { //cost of all non compulsory items is lower then budget, buy all.
			differentResult = 1; // (Whole item list)
			totalCombination = 1;
			if(satisfaction_choice == 1)
				text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + (compulsory_satisfaction+non_compulsory_satisfaction) + "\nTotal cost: \t$" + ((double) (compulsory_cost+non_compulsory_cost))/100 +"\n";
			else
				text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + "\nTotal cost: \t$" + ((double) (compulsory_cost+non_compulsory_cost))/100 +"\n";

			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				text +=non_compulsory_num+"\t";
				text +=item_list.get(i).getItem()+" for $";
				price = ((double) item_list.get(i).getPrice())/100;
				if(satisfaction_choice == 1) {
					text += price + " for ";
					text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
				}
				else {
					text += price+"\n";
				}

				non_compulsory_num++;
			}
		}
		else if(compute_list.get(compute_list.size()-1).getPrice() > temp_budget) { //budget left is not enough to buy the cheapest items left
			if (compute_list.size() == item_list.size()) {//no compulsory item
				differentResult = 0; // (Take nothing)
				totalCombination = 0;
				text = "The budget is not enough to buy anything. \nIncrease your budget and try again.";
			}
			else { //there is compulsory item
				differentResult = 2; // (Take Compulsory List only)
				totalCombination = 1;
				if (satisfaction_choice == 1) 
					text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + compulsory_satisfaction + "\nTotal cost: $\t" + ((double) compulsory_cost)/100 +"\n";
				else
					text = "Total combination:\t"+totalCombination+"\nTotal cost: $\t" + ((double) compulsory_cost)/100 +"\n";
				text +="***********Combination 1***********\n";
				for(int i=0; i<item_list.size(); i++) {
					if(item_list.get(i).getCompulsory() == 'Y') 
					{
						text +=compulsory_num+"\t";
						text +=item_list.get(i).getItem()+" for $";
						price = ((double) item_list.get(i).getPrice())/100;
						if(satisfaction_choice == 1) {
							text += price + " for ";
							text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
						}
						else {
							text += price+"\n";
						}
						compulsory_num++;
					}
				}
			}
		}
		else {
			differentResult = 3;
			if(type == 1)
				generateType(satisfaction_choice);
			else
				generate(satisfaction_choice);

			if(soln.getSolnSetSize() == 0)
				totalCombination = 0;
			else {
				totalCombination = soln.getSolnSetSize();
				for(int i=0; i<soln.getSolnSetSize(); i++) {
					BitSet bitmask = soln.getSolnSet().get(i);
					if(bitmask.isEmpty() == true) {
						totalCombination--;
						hasEmptySet = true;
					}
						
				}
				hasSolnSet = true; //There is combination.
			}

			if (satisfaction_choice == 1)
				text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + (soln.getSvalue()+compulsory_satisfaction) +"\n";
			else
				text = "Total combination:\t"+totalCombination+"\n";
			int index=0;
			for(int i=0; i<soln.getSolnSetSize(); i++) {
				BitSet bitmask = soln.getSolnSet().get(i);
				if(bitmask.isEmpty() == false) {

					text +="***********Combination "+(index+1)+"***********\n";
					num = 1;
					text +="Total cost: \t$" + (soln.getSolnCostSet().get(i)+((double) compulsory_cost)/100) +"\n";
					for(int k=0; k<compulsory_list.size();k++) {
						text+= num+"\t";
						text+= compulsory_list.get(k).getItem() + " for $";
						price = ((double) compulsory_list.get(k).getPrice())/100;
						if(satisfaction_choice == 1) {
							text+=price + " for ";
							text+= compulsory_list.get(k).getSatisfaction_value() + " satisfaction.\n";
						}
						else {
							text+=price+"\n";
						}
						num++;
					}
					for(int j=0; j<number; j++) {
						if(bitmask.get(j)) {
							text+= num+"\t";
							text+= compute_list.get(j).getItem() + " for $";
							price = ((double) compute_list.get(j).getPrice())/100;
							if(satisfaction_choice == 1) {
								text+= price + " for ";
								text+= compute_list.get(j).getSatisfaction_value() + " satisfaction.\n";
							}
							else {
								text+= price+"\n";
							}
							num++;
						}
					}
					text+="\n\n";//here
					index++;
				}
			}
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
	 * Description: Return the total combination generated.
	 * @return
	 */
	public int noOfCombination() {

		return totalCombination;
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
	 * Description: Send the confirm combination list to database.
	 * @param select
	 */

	public void sendDBList(int select) {
		if(hasEmptySet == true) {
			select++;
		}
		db_list = new Vector<Item>();
		// int budgetLeftToDisplay = (int) (budget*100); To be use in v0.2

		if(differentResult == 1) { //Budget is enough to buy all item (Regardless is compulsory or not). *Take whole item list*
			for(int i=0; i<item_list.size(); i++) {
				// budgetLeftToDisplay = budgetLeftToDisplay - item_list.get(i).getPrice(); To be use in v0.2
				db_list.add(item_list.get(i));
			}
		}
		else if (differentResult == 2) { //Budget is only enough to buy compulsory item. Take compulsory list*
			for(int i=0; i<compulsory_list.size(); i++) {
				// budgetLeftToDisplay = budgetLeftToDisplay - item_list.get(i).getPrice(); To be use in v0.2
				db_list.add(compulsory_list.get(i));
			}
		}
		else if (differentResult == 3) { //There is solution set
			for(int i=0; i<compulsory_list.size(); i++) {
				// budgetLeftToDisplay = budgetLeftToDisplay - item_list.get(i).getPrice(); To be use in v0.2
				db_list.add(compulsory_list.get(i));
			}

			if(hasSolnSet == true) {
				BitSet bitmask = soln.getSolnSet().get(select);
				for(int i=0; i<number; i++) {
					if(bitmask.get(i)) {
						db_list.add(compute_list.get(i));
						//budgetLeftToDisplay = budgetLeftToDisplay - compute_list.get(i).getPrice(); To be use in v0.2
					}

				}
			}
		}

		bm.recevied_combination_list(event_object.getID(), db_list);
		//bm.update_budget(event_object.getID(), ((double)budgetLeftToDisplay)/100); To be use in v0.2
		event_object.setitem_list(db_list);
		//event_object.setBudget(((double)budgetLeftToDisplay)/100); To be use in v0.2
	}
}