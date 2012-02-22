/* Matric Number: A0074006R
 * Name: Chua Hong Jing
 */

import java.util.*;

public class Main {
	private int no_of_items, cut_off_point = 0; //for storing the number input items
	private int budget; 
	private Vector<Item> item_list; //for storing the input item
	private Vector<Triple> permutation_list; 
	private Vector<Item> item_combination;
	private Vector<Triple> result_list;
	private Vector<Integer> printed_combination;


	public static void main(String[] args) {
		Main pba = new Main();
		pba.run();
	}

	@SuppressWarnings("unchecked")
	public void run() {
		Scanner sc = new Scanner(System.in);		

		String item_name;
		int total_cost = 0;
		int total_satisfaction = 0;
		int satisfaction_value;
		budget = 0;
		double price;


		System.out.print("Enter no of item(s): ");
		no_of_items = sc.nextInt();
		System.out.println();

		printed_combination = new Vector<Integer>();
		item_list = new Vector<Item>();
		result_list = new Vector<Triple>();
		permutation_list = new Vector<Triple>();


		for(int i=0; i<no_of_items; i++) {
			item_name = sc.next();
			price = sc.nextDouble();
			satisfaction_value = sc.nextInt();
			item_list.add(new Item(item_name, price, satisfaction_value));
			total_cost += (int) (price*100);
			total_satisfaction += satisfaction_value;
		}

		//to ensure the user enter a budget lower than total cost of all items
		do {
			System.out.print("Please enter budget: ");
			budget = (int) (sc.nextDouble()*100);
			System.out.println();
			if(budget > total_cost) 
				System.out.println("Budget more than total cost of all item(s)! Please re-enter budget!");
		}while(budget > total_cost);

		Collections.sort(item_list);

		if(budget == total_cost) {
			displayAll(total_satisfaction, total_cost);
		}
		else {
			cut_off_point = findCutOff();
			if(cut_off_point == -1) 
				System.out.println("You can't buy anything with this amount of money.");
			else {
				for(int i=cut_off_point; i<item_list.size(); i++) {
					item_combination = new Vector<Item>();
					item_combination.add(item_list.get(i));
					getPBASubset(item_list, item_combination);
				}

				analyseResult();

				displayResult();
			}
		}
	}

	public void displayResult() {
		System.out.println("Note: Result are display in increasing cost price.");
		System.out.println("There are  "+result_list.size()+ " combination(s) with a max satisfaction of " + result_list.get(0).getSatisfaction() + ", and a minimum cost of $" + (double)  result_list.get(0).getCost()/100 +".");
		System.out.println();
		
		for(int i=0; i<result_list.size(); i++) {
			System.out.println("Buy: ");
			for(int j=0; j<result_list.get(i).getCombination().size(); j++) {
				System.out.printf("%d: %s for $%.2f", j+1, result_list.get(i).getCombination().get(j).getItem(), (double) result_list.get(i).getCombination().get(j).getPrice()/100);
				System.out.println();
			}
			System.out.printf("Satisfaction: %d   Cost: $%.2f\n", result_list.get(i).getSatisfaction(), (double) result_list.get(i).getCost()/100);
			System.out.println();
		}
	}

	@SuppressWarnings("unchecked")
	public void analyseResult() {
		Collections.sort(permutation_list);

		for(int i=0; i<permutation_list.size(); i++) {
			Collections.sort(permutation_list.get(i).getCombination());
		}
		int highest_satisfaction = permutation_list.get(0).getSatisfaction();
		result_list.add(permutation_list.get(0));
		printed_combination.add(permutation_list.get(0).getCombination().hashCode());

		for(int i=1; i<permutation_list.size(); i++) {
			if(permutation_list.get(i).getSatisfaction() == highest_satisfaction) {
				if(printed_combination.contains(permutation_list.get(i).getCombination().hashCode()) == false) {
					printed_combination.add(permutation_list.get(i).getCombination().hashCode());
					result_list.add(permutation_list.get(i));
				}
			}
			else
				break;
		}
	}

	@SuppressWarnings("unchecked")
	public void getPBASubset(Vector<Item> list, Vector<Item> item_combination) {
		boolean add_item = false;
		boolean exist;
		int total_item_cost=0;
		int total_satisfaction=0;
		for(int i=0; i<item_combination.size(); i++) {
			total_item_cost += item_combination.get(i).getPrice();
			total_satisfaction += item_combination.get(i).getSatisfaction_value();
		}
		for(int i=list.size()-1; i>=cut_off_point; i--) {
			exist = false;
			for(int j=0; j<item_combination.size(); j++) {
				if(item_combination.get(j).equals(list.get(i))) {
					exist = true;
				}
			}
			if (exist == false) {
				if((list.get(i).getPrice() + total_item_cost) <= budget) {
					total_item_cost += list.get(i).getPrice();
					total_satisfaction += list.get(i).getSatisfaction_value();
					item_combination.add(list.get(i));	
					getPBASubset(list, item_combination);
					item_combination.remove(list.get(i));
					total_item_cost -= list.get(i).getPrice();
					total_satisfaction -= list.get(i).getSatisfaction_value();
					add_item = true;
				}
				else
					break;
			}
		}
		if(add_item == false) {
			permutation_list.add(new Triple(total_satisfaction, total_item_cost, (Vector<Item>) item_combination.clone()));
		}
	}

	public void displayAll(int satisfaction, int cost) {
		System.out.println("Note: Result are display in increasing cost price.");
		System.out.println("There are 1 combination with a max satisfaction of " + satisfaction + ", and a minimum cost of $" + (double) cost/100 +".");
		System.out.println();
		System.out.println("Buy: ");
		for(int i=0; i<item_list.size(); i++) {
			System.out.printf("%d: %s for $%.2f", i+1, item_list.get(i).getItem(), (double)item_list.get(i).getPrice()/100);
			System.out.println();
		}
		System.out.printf("Satisfaction: %d   Cost: $%.2f\n", satisfaction, (double) cost/100);
		System.out.println();
	}

	public int findCutOff() {
		for(int i=0; i<item_list.size(); i++) {
			if(item_list.get(i).getPrice() <= budget) {
				return i;
			}
		}
		return -1;
	}
}