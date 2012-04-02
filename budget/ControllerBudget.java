package budget;

import event.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.BitSet;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;


public class ControllerBudget {

	private int number; //store the number of item to undergo permutation algorithm
	private double budget, recursiveFunctionBudget; 
	private Solution soln;
	private Eventitem currentEvent;
	private int typeOfResult; //this will be use to determine how the database list will be manipulated.
	private boolean hasEmptySet = false;
	private Vector<Item> computeList; //store the item(s) to undergo permutation algorithm
	private Vector<Item> compulsoryList;
	private Vector<Item> itemList; //store the initial item list input by the user
	private int tempBudget, nonCompulsoryCost, compulsoryCost, compulsorySatisfaction, nonCompulsorySatisfaction;
	private int totalCombination; 


	private ModelBudget bm = new ModelBudget();

	public ControllerBudget() {

	}
	
	/**
	 * Description: Create a constructor that take in the input and process them into item list.
	 * @param input
	 * @param budget
	 * @param typeChoice
	 * @param satisfactionChoice
	 * @param inputEventitem
	 * @throws Exception
	 */
	public ControllerBudget(String input, int budget, int typeChoice, int satisfactionChoice, Eventitem inputEventitem) throws Exception {
		
		itemList = new Vector<Item>();
		currentEvent = inputEventitem;
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
				if (typeChoice == 1) { //has type
					if(satisfactionChoice == 1) { //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
						type = component[3];
					}
					else { //no satisfaction
						satisfaction = -1;
						type = component[2];
					}
					itemList.add(new Item(name, cost, satisfaction, type));
				}
				else { //no type
					if(satisfactionChoice == 1) //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
					else  //no satisfaction
						satisfaction = -1;
					itemList.add(new Item(name, cost, satisfaction));
				}
			}
		}
		bm.saveNonOptimizedList(currentEvent.getID(), itemList); //save to database non optimized item list
	}

	/**
	 * Description: Return item list.
	 * @return
	 */

	public Vector<Item> getItemList() {
		return itemList;		
	}

	/**
	 * Description: Differentiate the item list into compulsory list and compute list.
	 * @param com
	 * @param satisfactionChoice
	 */
	public void differentiateCompulsory(Vector<Integer> com, int satisfactionChoice) {
		tempBudget = (int) (budget*100);
		computeList = (Vector<Item>) itemList.clone();
		compulsoryList = new Vector<Item>();
		if(com.size() > 0) {
			for(int i=0; i<itemList.size(); i++) {
				itemList.get(i).setCompulsory('N');
			}
			for(int i=com.size()-1; i>=0; i--) {
				computeList.remove(com.get(i)-1);
				itemList.get(com.get(i)-1).setCompulsory('Y');
				compulsoryList.add(itemList.get(com.get(i)-1));
				tempBudget = tempBudget - itemList.get(com.get(i)-1).getPrice();
			}

			bm.updateNonOptimizeItemListwithCompulsory(currentEvent.getID(), itemList); //update with compulsory flag
		}
		number = computeList.size();

		differentiateCostandSatisfaction(satisfactionChoice);
	}

	/**
	 * Description: Differentiate the cost and satisfaction between compulsory list and compute list.
	 * @param satisfactionChoice
	 */

	public void differentiateCostandSatisfaction(int satisfactionChoice) {
		compulsoryCost = 0;
		compulsorySatisfaction = 0;
		nonCompulsoryCost = 0;
		nonCompulsorySatisfaction = 0;

		for(int i=0; i<itemList.size(); i++) {
			if(itemList.get(i).getCompulsory() == 'Y') 
			{
				compulsoryCost = compulsoryCost + itemList.get(i).getPrice();
				if(satisfactionChoice == 1)
					compulsorySatisfaction = compulsorySatisfaction + itemList.get(i).getSatisfactionValue();
			}
			else {
				nonCompulsoryCost = nonCompulsoryCost + itemList.get(i).getPrice();
				if(satisfactionChoice == 1)
					nonCompulsorySatisfaction = nonCompulsorySatisfaction + itemList.get(i).getSatisfactionValue();
			}
		}	
	}

	/**
	 * Description: Return budget remain after deducting the compulsory items cost.
	 * @return
	 */

	public double budgetleft() {
		double left = ((double) tempBudget)/100;
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
		recursiveFunctionBudget = ((double) tempBudget)/100;	
		return analysis(hastype, hassatisfaction);
	}

	/**
	 * Description: Calling the respective method according to type and satisfaction option.
	 * @param type
	 * @param satisfactionChoice
	 * @return
	 */
	public String analysis(int type, int satisfactionChoice) {
		String resultText = "";
		typeOfResult = 0;
		int noOfCombination = 0;
		Collections.sort(computeList);
		hasEmptySet = false;

		if(compulsoryList.size() == itemList.size()){ //All items checked as compulsory and budget is enough to buy all.
			typeOfResult = 1;
			noOfCombination = 1;	
		}
		else if(nonCompulsoryCost <= tempBudget) { //cost of all non compulsory items is lower then budget, buy all.
			typeOfResult = 1;
			noOfCombination = 1;	
		}
		else if(computeList.get(computeList.size()-1).getPrice() > tempBudget) { //budget left is not enough to buy the cheapest items left
			if (computeList.size() == itemList.size()) {//no compulsory item
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
				generateType(satisfactionChoice);
			else
				generate(satisfactionChoice);

			noOfCombination = soln.getSolnSetSize();
			for(int i=0 ;i<soln.getSolnSetSize(); i++) {
				if(soln.getSolnSet().get(i).isEmpty() == true) {
					hasEmptySet = true;
					noOfCombination--;
				}

			}
			if(noOfCombination == 0)
				typeOfResult = 4; //No solution set.
		}

		totalCombination = noOfCombination;
		resultText = displayResultNoSolutionSet(satisfactionChoice, typeOfResult, noOfCombination); 
		return resultText;
	}

	public String displayResultNoSolutionSet(int satisfactionChoice, int type, int combination) {
		DecimalFormat myFormatter = new DecimalFormat("##.##");
		String text = "";

		int index;

		if(type == 0) 
			text = "The budget is not enough to buy anything.\nIncrease your budget and try again.";
		else if(type == 1) {
			index = 1;
			if(satisfactionChoice == 1)
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + (compulsorySatisfaction+nonCompulsorySatisfaction) + "\nTotal cost: \t$" + myFormatter.format(((double) (compulsoryCost+nonCompulsoryCost))/100) +"\n";
			else
				text = "Total combination:\t"+combination+"\nTotal cost: \t$" + myFormatter.format(((double) (compulsoryCost+nonCompulsoryCost))/100) +"\n";	
			text +="***********Combination 1***********\n";
			for(int i=0; i<itemList.size(); i++) {
				text += concatItemListResult(index, i, satisfactionChoice, 1); // 1 refer to take initial item list
				index++;
			}
		}
		else if(type == 2) {
			index = 1;
			if (satisfactionChoice == 1) 
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + compulsorySatisfaction + "\nTotal cost: $\t" + myFormatter.format(((double) compulsoryCost)/100) +"\n";
			else
				text = "Total combination:\t"+combination+"\nTotal cost: $\t" + myFormatter.format(((double) compulsoryCost)/100) +"\n";
			text +="***********Combination 1***********\n";
			for(int i=0; i<itemList.size(); i++) {
				if(itemList.get(i).getCompulsory() == 'Y') 
				{
					text += concatItemListResult(index, i, satisfactionChoice, 1); // 1 refer to take initial item list
					index++;
				}
			}
		}
		else if (type == 3) {
			if (satisfactionChoice == 1)
				text = "Total combination:\t"+combination+"\nMax satisfaction:\t" + (soln.getSvalue()+compulsorySatisfaction) +"\n";
			else
				text = "Total combination:\t"+combination+"\n";

			int solutionCounter = 1;
			for(int i=0; i<soln.getSolnSetSize(); i++) {
				BitSet bitmask = soln.getSolnSet().get(i);
				if(bitmask.isEmpty() == false) {
					text +="***********Combination "+solutionCounter+"***********\n";
					index = 1;
					text +="Total cost: \t$" + myFormatter.format((soln.getSolnCostSet().get(i)+((double) compulsoryCost)/100)) +"\n";
					for(int k=0; k<compulsoryList.size();k++) {
						text += concatItemListResult(index, k, satisfactionChoice, 2); // 2 refer to take compulsory item list
						index++;
					}	
					for(int j=0; j<number; j++) {
						if(bitmask.get(j)) {
							text += concatItemListResult(index, j, satisfactionChoice, 3); // 2 refer to take compulsory item list
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

	public String concatItemListResult(int index, int currentItem, int satisfactionChoice, int type) {
		String text = "";
		double price;
		Vector<Item> concatList = null;
		if(type == 1)
			concatList = itemList;
		else if (type == 2)
			concatList = compulsoryList;
		else if(type == 3)
			concatList = computeList;

		text +=index+"\t";
		text +=concatList.get(currentItem).getQuantity() + "x of " + concatList.get(currentItem).getItem()+" for $";
		price = ((double) concatList.get(currentItem).getPrice())/100;
		if(satisfactionChoice == 1) {
			text += price + " for ";
			text +=concatList.get(currentItem).getSatisfactionValue()+" satisfaction.\n";
		}
		else {
			text += price+"\n";
		}
		return text;
	}

	/**
	 * Description: Type choice is flag as false and it will call the respective method according to satisfaction option.
	 * @param satisfactionChoice
	 */
	public  void generate(int satisfactionChoice) {
		BitSet bitmask = new BitSet(number);
		if(satisfactionChoice == 1) {
			for (int i=0; i<number; i++)
				recurse(i, bitmask, recursiveFunctionBudget, 0);
		}
		else {
			for (int i=0; i<number; i++)
				recurseNoSatisfaction(i, bitmask, recursiveFunctionBudget);
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
		if (((double) computeList.get(cur).getPrice())/100 <= subbudget) {
			subbudget -= ((double) computeList.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseNoSatisfaction(i, bitmask2 , subbudget);
		}

		if (DupCheck(bitmask2) == 0) {
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
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
		if (((double) computeList.get(cur).getPrice())/100 <= subbudget) {
			max += computeList.get(cur).getSatisfactionValue();
			subbudget -= ((double) computeList.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurse(i, bitmask2 , subbudget, max);
		}

		//by right only the deepest level will perform this
		if (max > soln.getSvalue()) {
			soln.setSvalue(max);
			soln.clearAll();
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
		}

		else if (max == soln.getSvalue() && DupCheck(bitmask2) == 0) {
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
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
	 * @param satisfactionChoice
	 */

	public void generateType(int satisfactionChoice) {
		BitSet bitmask = new BitSet(number);
		if(satisfactionChoice == 1) {
			for (int i=0; i<number; i++) {
				recurseType(i, bitmask, recursiveFunctionBudget, 0);
			}
		}
		else {
			for (int i=0; i<number; i++) {
				recurseTypeNoSatisfaction(i, bitmask, recursiveFunctionBudget);
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
		if (((double) computeList.get(cur).getPrice())/100 <= subbudget && TypeChecker(bitmask2, cur) == 0) {
			subbudget -= ((double) computeList.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseTypeNoSatisfaction(i, bitmask2 , subbudget);
		}
		if (DupCheck(bitmask2) == 0)
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
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
		if (((double) computeList.get(cur).getPrice())/100 <= subbudget && TypeChecker(bitmask2, cur) == 0) {
			max += computeList.get(cur).getSatisfactionValue();
			subbudget -= ((double) computeList.get(cur).getPrice())/100;
			bitmask2.set(cur);
			for (int i=cur+1 ; i<number; i++)
				recurseType(i, bitmask2 , subbudget, max);
		}

		//by right only the deepest level will perform this
		if (max > soln.getSvalue()) {
			soln.setSvalue(max);
			soln.clearAll();
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
		}
		else if (max == soln.getSvalue() && DupCheck(bitmask2) == 0)
			soln.addSet(bitmask2, recursiveFunctionBudget - subbudget);
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
				if (computeList.get(i).getType().compareTo(computeList.get(cur).getType()) == 0) 
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
		Vector<Item> databaseList = new Vector<Item>();//store the list to be send to database

		if(typeOfResult == 1) { //take all item. Budget is enough to buy everything.
			for(int i=0; i<itemList.size(); i++) {
				databaseList.add(itemList.get(i));
			}
		}
		else if (typeOfResult == 2) {//Budget is only enough to buy compulsory item. Take compulsory list*
			for(int i=0; i<compulsoryList.size(); i++) {
				databaseList.add(compulsoryList.get(i));
			}
		}
		else if (typeOfResult == 3) { //There is solution set

			//If there is compulsory list we will add them first.
			for(int i=0; i<compulsoryList.size(); i++) {
				databaseList.add(compulsoryList.get(i));
			}

			if(hasEmptySet == true)
				select++;

			BitSet bitmask = soln.getSolnSet().get(select);
			for(int i=0; i<number; i++) {
				if(bitmask.get(i)) {
					databaseList.add(computeList.get(i));
				}
			}
		}

		bm.saveOptimizedList(currentEvent.getID(), databaseList);

		currentEvent.setitem_list(bm.getOptimizeItemList(currentEvent.getID()));
	}

	public void deleteBudgetItem(int eventId, int itemId) {
		bm.deleteBudgetItem(eventId, itemId);
	}

	public Vector<Item> getOptimizeItemList(int eventId) {
		return bm.getOptimizeItemList(eventId);
	}
	
	/* The codes below are all use for Junit Test purpose*/
	public int getSolutionSize() {
		return soln.getSolnSetSize();
	}
	
	public int getSolutionSatisfaction() {
		return soln.getSvalue();
	}
	
	public double getSolutionCost(int inputNum) {
		DecimalFormat myFormatter = new DecimalFormat("##.##");
		return (Double.parseDouble(myFormatter.format(soln.getSolnCostSet().get(inputNum))));
	}
	
	public int getAllItemSatisfaction() {
		return compulsorySatisfaction + nonCompulsorySatisfaction;
	}
	
	public int getAllItemCost() {
		return compulsoryCost + nonCompulsoryCost ;
	}
	
	public int getCompulsoryItemSatisfaction() {
		return compulsorySatisfaction;
	}
	
	public int getCompulsoryItemCost() {
		return compulsoryCost;
	}
}
