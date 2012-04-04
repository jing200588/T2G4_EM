package unittest.budgettest;

import java.util.Vector;

import event.*;
import budget.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Chua Hong Jing
 *
 */

public class NoSatisfactionNoTypeTest {

	private Vector<Integer> compulsoryList;
	private String input;

	public NoSatisfactionNoTypeTest() throws Exception{

		input = "Apple_Ipad 600\n" +
				"ACER_NETBOOK 1300.8\n" +
				"Nikon_Cam 634.5\n" +
				"Monster_Earpiece 64.7\n" +
				"OCZ_120GB_SSD 320.88\n" +
				"Canon_DSLR 1000.5\n" +
				"Western_Digital_HD_50GB 180.6\n" +
				"Windows_7_Basic 200\n" +
				"World_of_Warcraft 49.99\n" +
				"Software_Engineering_For_Dummies 419.4\n";	
	}
	/**
	 * This test case will cover the budget is zero dollar and no compulsory item are checked.
	 * The test cases that cover the part where budget is zero dollar but there is at least one item
	 * checked as compulsory item will be covered by the GUI where it will flag error immediately after
	 * the user choose to proceed.
	 * 
	 * Expected output: The item list will be empty.
	 * @throws Exception
	 */

	@Test
	public void noBudgetNoCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 0, 0, 0, currentEvent); //Budget = $0

		cb.differentiateCompulsory(compulsoryList, 0); // compulsory list is empty

		cb.findOptimalShopList(0, 0);
		
		boolean compareResult = true;
		if(cb.noOfCombination() != 0) 
			compareResult = false;

		assertEquals(true, compareResult); //can't buy anything, budget is $0

	}

	/**
	 * This test case will cover the situation where the budget is more than or equal to the sum of total items.
	 * No items are marked as compulsory.
	 * 
	 * Expected output: All items taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void exceededBudgetNoCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 477137, 0, 0, currentEvent); //Budget = $4771.37 

		cb.differentiateCompulsory(compulsoryList, 0); // compulsory list is empty

		cb.findOptimalShopList(0, 0);
		
		boolean compareResult = true;
		if(cb.noOfCombination() != 1) 
			compareResult = false;
		if(cb.getAllItemCost() != 477137)
			compareResult = false;
		
		assertEquals(true, compareResult);
	}

	/**
	 * This test case will cover the situation where the budget is more than or equal to the sum of total items.
	 * Some items are marked as compulsory.
	 * 
	 * Expected output: All items taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void exceededBudgetSomeCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();
		
		ControllerBudget cb = new ControllerBudget(input, 477137, 0, 0, currentEvent); //Budget = $4771.37 
		
		compulsoryList.add(3); // flag Nikon_Cam as compulsory, cost is $634.5
		compulsoryList.add(8); // flag Windows_7_Basic as compulsory, cost is $200.8

		cb.differentiateCompulsory(compulsoryList, 0); // compulsory list is not empty

		cb.findOptimalShopList(0, 0);

		boolean compareResult = true;
		if(cb.noOfCombination() != 1) 
			compareResult = false;
		if(cb.getAllItemCost() != 477137)
			compareResult = false;
		
		assertEquals(true, compareResult);
	}

	/**
	 * This test case will cover the situation where the budget is more than or equal to the sum of total items.
	 * All items are marked as compulsory.
	 * 
	 * Expected output: All items taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void exceededBudgetAllCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();
		
		ControllerBudget cb = new ControllerBudget(input, 477137, 0, 0, currentEvent); //Budget = $4771.37 
		
		//Add everything to compulsory list.
		for(int i=1; i<=10; i++)
			compulsoryList.add(i);

		cb.differentiateCompulsory(compulsoryList, 0); // all item is compulsory

		cb.findOptimalShopList(0, 0);
		
		boolean compareResult = true;
		if(cb.noOfCombination() != 1) 
			compareResult = false;
		if(cb.getAllItemCost() != 477137)
			compareResult = false;
		
		assertEquals(true, compareResult);
	}

	/**
	 * This test case will cover the situation where the budget is not enough to buy even the cheapest item. No item are compulsory.
	 * The test cases that cover the part where budget is not enough to buy the cheapest item but there is at least one item
	 * checked as compulsory item will be covered by the GUI where it will flag error immediately after
	 * the user choose to proceed.
	 * 
	 * Expected output: No item taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void lowestBudgetNoCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 4998, 0, 0, currentEvent); //Budget = $49.98; cheapest item = $49.99 

		cb.differentiateCompulsory(compulsoryList, 0); 

		cb.findOptimalShopList(0, 0);
		
		boolean compareResult = true;
		if(cb.noOfCombination() != 0) 
			compareResult = false;
		
		assertEquals(true, compareResult);
	}

	/**
	 * This test case will cover the situation where the budget is only enough to buy compulsory item.
	 * The situation where budget is only enough to buy certain item will be cover in other test case.
	 * The situation where budget is only enough to buy certain item but user checked more items
	 * as compulsory than what the budget can buy will be flagged in GUI.
	 * 
	 * Expected output: Only compulsory item taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void BudgetEnoughForCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 115618, 0, 0, currentEvent); //Budget = $1156.18; Compulsory item cost = $1156.18 

		compulsoryList.add(3); // flag Nikon_Cam as compulsory, cost is $634.5 for 9 satisfaction
		compulsoryList.add(5); //flag OCZ_120GB_SSD as compulsory, cost is $320.88 for 8 satisfaction
		compulsoryList.add(8); // flag Windows_7_Basic as compulsory, cost is $200 for 8 satisfaction
		
		//Total cost will be = $1156.18
		
		cb.differentiateCompulsory(compulsoryList, 0); // all item is compulsory

		cb.findOptimalShopList(0, 0);

		boolean compareResult = true;
		if(cb.noOfCombination() != 1) 
			compareResult = false;
		if(cb.getCompulsoryItemCost() != 115538)
			compareResult = false;
		
		assertEquals(true, compareResult);
	}
	
	/**
	 * This test case will cover the situation where the budget is only enough to buy some item.
	 * 
	 * Expected output: Only some item taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void BudgetEnoughForSomeItemWithoutCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 50000, 0, 0, currentEvent); //Budget = $500.00
		
		cb.differentiateCompulsory(compulsoryList, 0); // all item is compulsory

		cb.findOptimalShopList(0, 0);
		
		boolean compareResult = true;
		if(cb.getSolutionSize() != 23) //inclusive of empty set 
			compareResult = false;
		
		assertEquals(true, compareResult);
	}
	
	/**
	 * This test case will cover the situation where the budget is only enough to buy some item, inclusive of compulsory items.
	 * 
	 * Expected output: Only some item taken.
	 * 
	 * @throws Exception
	 */

	@Test
	public void BudgetEnoughForSomeItemWithCompulsoryItem() throws Exception {
		Eventitem currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();

		ControllerBudget cb = new ControllerBudget(input, 50000, 0, 0, currentEvent); //Budget = $500.00
		
		compulsoryList.add(5); //flag OCZ_120GB_SSD as compulsory, cost is $320.88 for 8 satisfaction

		cb.differentiateCompulsory(compulsoryList, 0); // all item is compulsory

		cb.findOptimalShopList(0, 0);
		
		/*This portion of the test data do not consist of any compulsory item*/
		boolean compareResult = true;
		if(cb.getSolutionSize() != 4) //inclusive of empty set
			compareResult = false;

		assertEquals(true, compareResult);
	}

	
}
