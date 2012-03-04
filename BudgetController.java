import java.io.IOException;
import java.util.*;

import org.eclipse.ui.internal.handlers.WizardHandler.New;

public class BudgetController implements BudgetInterface{

	//private Vector<Item> list = new Vector<Item>();
	private int number;
	private double budget, recursive_fnc_budget;
	private Solution soln;
	private int event_id;

	private Vector<Item> compute_list;
	private Vector<Item> compulsory_list;
	private Vector<Item> item_list; //for storing the input item
	private int temp_budget, non_compulsory_cost, compulsory_cost, compulsory_satisfaction, non_compulsory_satisfaction;
	private int totalCombination;
	private Vector<Item> db_list;

	private BudgetModel bm = new BudgetModel();


	public String findOptimalShopList(int hastype, int hassatisfaction) {
		soln = new Solution();
		recursive_fnc_budget = ((double) temp_budget)/100;	
		return analysis(hastype);
	}

	public BudgetController(String input, int budget, int type_c, int satisfaction_c, int id) throws Exception {

		if(input.equals("")) throw new IOException("***Input list must not be empty!***");
		item_list = new Vector<Item>();
		event_id=id;
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
					if(cost < 0) 
						throw new IOException("Cost should not be negative");
					if(satisfaction_c == 1) { //has satisfaction
						satisfaction = Integer.parseInt(component[2]);
						type = component[3];
					}
					else {
						satisfaction = -1;
						type = component[2];
					}
					item_list.add(new Item(name, cost, satisfaction, type));
				}
			}
		}
		else {
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
					else 
						satisfaction = -1;
					item_list.add(new Item(name, cost, satisfaction));
				}
			}

		}
		bm.received_item_list(event_id, item_list); //SEND TO DATABASE
	}

	public Vector<Item> getItemList(int id) {
		//return bm.return_item_list(id);
		return item_list;
	}

	/*flag compulsory items*/
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

			bm.update_item_list(event_id, item_list); //update with compulsory flag
		}
		number = compute_list.size();
	}

	/*Return budget remain after checking the compulsory items*/
	public String budgetleft() {
		double left = ((double) temp_budget)/100;
		return ""+left;
	}

	/*Return to user if all items is checked*/
	public boolean emptyComputeList() {
		if (compute_list.size() == 0)
			return true;
		else
			return false;
	}

	public void differentiateCompulsory() {
		compulsory_cost = 0;
		compulsory_satisfaction = 0;
		non_compulsory_cost = 0;
		non_compulsory_satisfaction = 0;

		for(int i=0; i<item_list.size(); i++) {
			if(item_list.get(i).getCompulsory() == 'Y') 
			{
				compulsory_cost = compulsory_cost + item_list.get(i).getPrice();
				compulsory_satisfaction = compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
			}
			else {
				non_compulsory_cost = non_compulsory_cost + item_list.get(i).getPrice();
				non_compulsory_satisfaction = non_compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
			}
		}	
	}


	public String analysis(int type) {

		String text = "Test";
		double price;
		int compulsory_num = 1;
		int num;
		Collections.sort(compute_list);


		int non_compulsory_num=1;
		System.out.println("Temp: " + temp_budget + " Non: " + non_compulsory_cost + " Com: " + compulsory_cost );
		if(compulsory_list.size() == item_list.size()){ //All items checked as compulsory and budget is enough to buy all.
			totalCombination = 1;
			text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + compulsory_satisfaction + "\nTotal cost: \t$" + ((double) (compulsory_cost))/100 +"\n";
			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				text +=non_compulsory_num+"\t";
				text +=item_list.get(i).getItem()+" for $";
				price = ((double) item_list.get(i).getPrice())/100;
				text += price + " for ";
				text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
				non_compulsory_num++;
			}
		}
		else if(non_compulsory_cost <= temp_budget) {
			totalCombination = 1;
			text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + (compulsory_satisfaction+non_compulsory_satisfaction) + "\nTotal cost: \t$" + ((double) (compulsory_cost+non_compulsory_cost))/100 +"\n";
			text +="***********Combination 1***********\n";
			for(int i=0; i<item_list.size(); i++) {
				text +=non_compulsory_num+"\t";
				text +=item_list.get(i).getItem()+" for $";
				price = ((double) item_list.get(i).getPrice())/100;
				text += price + " for ";
				text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
				non_compulsory_num++;
			}
		}
		else if(compute_list.get(compute_list.size()-1).getPrice() > temp_budget) { //budget left is not enough to buy the cheapest items left
			if (compute_list.size() == item_list.size()) {//no compulsory item
				totalCombination = 0;
				text = "The budget is not enough to buy anything.";
			}
			else {
				totalCombination = 1;
				text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + compulsory_satisfaction + "\nTotal cost: $\t" + ((double) compulsory_cost)/100 +"\n";
				text +="***********Combination 1***********\n";
				for(int i=0; i<item_list.size(); i++) {
					if(item_list.get(i).getCompulsory() == 'Y') 
					{
						text +=compulsory_num+"\t";
						text +=item_list.get(i).getItem()+" for $";
						price = ((double) item_list.get(i).getPrice())/100;
						text += price + " for ";
						text +=item_list.get(i).getSatisfaction_value()+" satisfaction.\n";
						compulsory_num++;
					}
				}
			}
		}
		else {
			
			System.out.println("Executing this");
			if(type == 1)
				generateType();
			else
				generate();

			if(soln.getSolnSetSize() == 0)
				totalCombination = 0;
			else
				totalCombination = soln.getSolnSetSize();
			text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + (soln.getSvalue()+compulsory_satisfaction) +"\n";

			for(int i=0; i<soln.getSolnSetSize(); i++) {
				BitSet bitmask = soln.getSolnSet().get(i);

				text +="***********Combination "+(i+1)+"***********\n";
				num = 1;
				text +="Total cost: \t$" + (soln.getSolnCostSet().get(i)+((double) compulsory_cost)/100) +"\n";
				for(int k=0; k<compulsory_list.size();k++) {
					text+= num+"\t";
					text+= compulsory_list.get(k).getItem() + " for $";
					price = ((double) compulsory_list.get(k).getPrice())/100;
					text+=price + " for ";
					text+= compulsory_list.get(k).getSatisfaction_value() + " satisfaction.\n";
					num++;
				}
				for(int j=0; j<number; j++) {
					if(bitmask.get(j)) {
						text+= num+"\t";
						text+= compute_list.get(j).getItem() + " for $";
						price = ((double) compute_list.get(j).getPrice())/100;
						text+= price + " for ";
						text+= compute_list.get(j).getSatisfaction_value() + " satisfaction.\n";
						num++;
					}
				}
				text+="\n\n";
			}
		}
		return text;
	}

	public  void generate() {
		BitSet bitmask = new BitSet(number);
		for (int i=0; i<number; i++)
			recurse(i, bitmask, recursive_fnc_budget, 0);
	}

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
	public  int DupCheck(BitSet bitmask) {
		for (int i=0; i<soln.getSolnSetSize(); i++) {
			if (soln.getSolnSet().get(i).hashCode() == bitmask.hashCode())
				return 1;
		}
		return 0;
	}

	public int noOfCombination() {
		return totalCombination;
	}

	/*Swee Khoon's CE code*/

	public void generateType() {
		BitSet bitmask = new BitSet(number);
		for (int i=0; i<number; i++) {
			recurseType(i, bitmask, recursive_fnc_budget, 0);
		}
	}

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

	public  int TypeChecker (BitSet bitmask, int cur) {
		for (int i=0; i<number; i++) {
			if (bitmask.get(i)) {
				if (compute_list.get(i).getType().compareTo(compute_list.get(cur).getType()) == 0) 
					return 1;
			}
		}
		return 0;
	}

	public void sendDBList(int select) {
		db_list = new Vector<Item>();

		for(int i=0; i<compulsory_list.size(); i++) {
			db_list.add(compulsory_list.get(i));
		}

		BitSet bitmask = soln.getSolnSet().get(select);
		for(int i=0; i<number; i++) {
			if(bitmask.get(i)) {
				db_list.add(compute_list.get(i));
			}
		}
		
		bm.recevied_combination_list(event_id, db_list);
		
		System.out.println("TESTING START");
		for(int i=0; i<bm.displayConfirm().size(); i++) {
			System.out.print(bm.displayConfirm().get(i).getItem() + " " + bm.displayConfirm().get(i).getPrice());
			System.out.println();
		}
	}

}