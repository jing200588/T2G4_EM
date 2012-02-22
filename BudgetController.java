import java.io.IOException;
import java.util.*;

import org.eclipse.ui.internal.handlers.WizardHandler.New;

public class BudgetController implements IBudgetAssistant{

	//private Vector<Item> list = new Vector<Item>();
	private int number;
	private double budget, recursive_fnc_budget;
	private Solution soln;
	private boolean hastype;

	private Vector<Item> compute_list;
	private Vector<Item> compulsory_list;
	private Vector<Item> item_list = new Vector<Item>()	; //for storing the input item
	private int temp_budget, non_compulsory_cost, compulsory_cost, compulsory_satisfaction, non_compulsory_satisfaction;
	private int totalCombination;
	private Vector<Item> db_list;


	public String findOptimalShopList(int hastype) {
		//run();
		soln = new Solution();
		recursive_fnc_budget = ((double) temp_budget)/100;
		return analysis(hastype);
	}

	public BudgetController(String input, int budget, int choice) throws IOException{
		// TODO Auto-generated constructor stub
		Scanner sc = new Scanner(input);
		String line;
		String[] component;
		String name;
		double cost;
		int satisfaction;
		String type;
		this.budget = ((double)budget)/100;

		if(choice == 1) { //has type
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				component = line.split(" ");
				name = component[0];
				cost = Double.parseDouble(component[1]);
				if(cost < 0) 
					throw new IOException("Cost should not be negative");
				satisfaction = Integer.parseInt(component[2]);
				type = component[3];
				item_list.add(new Item(name, cost, satisfaction, type));
			}
		}
		else {
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				component = line.split(" ");
				name = component[0];
				cost = Double.parseDouble(component[1]);
				if(cost < 0) 
					throw new IOException("Cost should not be negative");
				satisfaction = Integer.parseInt(component[2]);
				item_list.add(new Item(name, cost, satisfaction));
			}
		}
	}

	public void compulsory(String com) {
		temp_budget = (int) (budget*100);
		compute_list = (Vector<Item>) item_list.clone();
		compulsory_list = new Vector<Item>();

		for(int i=0; i<item_list.size(); i++) {
			item_list.get(i).setCompulsory('N');
		}

		if(com.length() > 0){
			String[] com_num = com.split(",");
			for(int i=0; i<com_num.length; i++) {
				compute_list.remove(Integer.parseInt(com_num[i])-1);
				item_list.get(Integer.parseInt(com_num[i])-1).setCompulsory('Y');
				compulsory_list.add(item_list.get(Integer.parseInt(com_num[i])-1));
				temp_budget = temp_budget - item_list.get(Integer.parseInt(com_num[i])-1).getPrice();
			}
		}

		number = compute_list.size();
	}

	public String budgetleft() {
		double left = ((double) temp_budget)/100;
		return ""+left;
	}

	/*Display all items*/
	public String displayList() {
		String text = "";
		int num=1;
		double price;
		for(int i=0; i<item_list.size(); i++) {
			text +=num+"\t";
			text +=item_list.get(i).getItem()+"\t\t$";
			price = ((double) item_list.get(i).getPrice())/100;
			text += price + "\t";
			text +=item_list.get(i).getSatisfaction_value()+"\n";
			num++;
		}

		return text;
	}

	/*Display items by differentiating them (Compulsory and Non-compulsory)*/
	public String displaySplit(int list) {
		String compulsory_text = "";
		String non_compulsory_text = "";
		int compulsory_num = 1;
		int non_compulsory_num =1;
		compulsory_cost = 0;
		compulsory_satisfaction = 0;
		non_compulsory_cost = 0;
		non_compulsory_satisfaction = 0;
		double price;

		for(int i=0; i<item_list.size(); i++) {
			if(item_list.get(i).getCompulsory() == 'Y') 
			{
				compulsory_cost = compulsory_cost + item_list.get(i).getPrice();
				compulsory_satisfaction = compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
				compulsory_text +=compulsory_num+"\t";
				compulsory_text +=item_list.get(i).getItem()+"\t\t$";
				price = ((double) item_list.get(i).getPrice())/100;
				compulsory_text += price + "\t";
				compulsory_text +=item_list.get(i).getSatisfaction_value()+"\n";
				compulsory_num++;
			}
			else {
				non_compulsory_cost = non_compulsory_cost + item_list.get(i).getPrice();
				non_compulsory_satisfaction = non_compulsory_satisfaction + item_list.get(i).getSatisfaction_value();
				non_compulsory_text +=non_compulsory_num+"\t";
				non_compulsory_text +=item_list.get(i).getItem()+"\t\t$";
				price = ((double) item_list.get(i).getPrice())/100;
				non_compulsory_text += price + "\t";
				non_compulsory_text +=item_list.get(i).getSatisfaction_value()+"\n";
				non_compulsory_num++;
			}
		}	

		if(list == 1)
			return compulsory_text;
		else
			return non_compulsory_text;
	}

	public String analysis(int type) {

		String text = "Test";
		double price;
		int compulsory_num = 1;
		int num;

		Collections.sort(compute_list);

		int lowest_cost_item = compute_list.get(compute_list.size()-1).getPrice();

		int non_compulsory_num=1;
		if(temp_budget >= non_compulsory_cost) {
			totalCombination = 1;
			text = "Total combination:\t"+totalCombination+"\nMax satisfaction:\t" + non_compulsory_satisfaction + "\nTotal cost: $\t" + ((double) (compulsory_cost+non_compulsory_cost))/100 +"\n";
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
		else if(lowest_cost_item > temp_budget) { //budget left is not enough to buy the cheapest items left
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
	
	public String sendDBList(int select) {
		db_list = new Vector<Item>();
		
		int db_total_cost=compulsory_cost;
		int db_total_satisfaction=compulsory_satisfaction;
		
		for(int i=0; i<compulsory_list.size(); i++) {
			db_list.add(compulsory_list.get(i));
		}
		
		BitSet bitmask = soln.getSolnSet().get(select);
		for(int i=0; i<number; i++) {
			if(bitmask.get(i)) {
				db_list.add(compute_list.get(i));
				db_total_cost = db_total_cost + compute_list.get(i).getPrice();
				db_total_satisfaction = db_total_satisfaction + compute_list.get(i).getSatisfaction_value();
			}
		}
		
		String text = "";
		int num = 1;
		double price; 
		
		text+="You have selected the following combination to be saved into the database.\n";
		text+="Total cost of $" + db_total_cost + " with a total satisfaction value of " + db_total_satisfaction +".\n\n";
		
		for(int i=0; i<db_list.size(); i++) {
			text+= num+"\t";
			text+= db_list.get(i).getItem() + " for $";
			price = ((double) db_list.get(i).getPrice())/100;
			text+=price + " for ";
			text+= db_list.get(i).getSatisfaction_value() + " satisfaction.\n";
			num++;
		}
		
		text+="\nClick on Confirm to finish.";

		
		return text;
	}


}