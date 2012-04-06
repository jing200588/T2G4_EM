package unittest.budgettest;

import event.*;
import budget.*;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class BudgetDatabaseTest {

	private Vector<Item> itemList;
	private Vector<Integer> compulsoryList;
	private String input;
	private Eventitem currentEvent;
	private ControllerBudget cb;

	public BudgetDatabaseTest() throws Exception {
		input = "Apple_Ipad 600 7 Tablet\n" +
				"ACER_NETBOOK 1300.8 10 Tablet\n" +
				"Nikon_Cam 634.5 9 Camera\n" +
				"Monster_Earpiece 64.7 7 Earpiece\n" +
				"OCZ_120GB_SSD 320.88 8 Harddisk\n" +
				"Canon_DSLR 1000.5 11 Camera\n" +
				"Western_Digital_HD_50GB 180.6 4 Harddisk\n" +
				"Windows_7_Basic 200 8 Software\n" +
				"World_of_Warcraft 49.99 4 Software\n" +
				"Software_Engineering_For_Dummies 419.4 7 Book\n";

		currentEvent = new Eventitem("BudgetTestEvent", 2012, 4, 2, 2012, 4, 28, 8, 30, 20, 30, "Test Case Event");
		compulsoryList = new Vector<Integer>();
	}

	/**
	 * Description: This function will check when the type result is being set to 1, it will
	 * save the entire item list into the database.
	 * @throws Exception
	 */
	@Test
	public void typeResult1Test() throws Exception {
		
		//total cost = $1155.38, setting budget to $4771.37 (Actual total item cost)
		
		cb = new ControllerBudget(input, 477137, 1, 1, currentEvent); //Budget = $4771.37

		runPackage();

		Vector<Item> expectedItemList = cb.getItemList();

		Vector<Item> actualItemList = runPackage2(0);

		boolean sameResult = true;

		for(int i=0; i<expectedItemList.size(); i++) {
			if(expectedItemList.get(i).getItem().equals(actualItemList.get(i).getItem()) == false)
				sameResult = false;	
		}

		assertEquals(true, sameResult);
	}

	/**
	 * Description: This function will check when the type result is being set to 2, it will
	 * save the compulsory item list into the database only.
	 * 
	 * This method test the upper limit.
	 * 
	 * @throws Exception
	 */
	@Test
	public void typeResult2UpperLimitTest() throws Exception {

		//total cost = $1155.38, setting budget to $1205.36 (to test the upper limit before taking the next cheapest item ($49.99).

		cb = new ControllerBudget(input, 120536, 1, 1, currentEvent); //Budget = $1205.36

		runPackage();
		
		Vector<Item> inputItemList = cb.getItemList();
		Vector<Item> expectedItemList = new Vector<Item>();

		/*Item is added in reverse added due to differentiateCompulsory method reverse the order 
		 * when adding item into compulsory list */
		expectedItemList.add(inputItemList.get(7)); //Windows_7_Basic
		expectedItemList.add(inputItemList.get(4)); //OCZ_120GB_SSD
		expectedItemList.add(inputItemList.get(2)); //Nikon_Cam

		Vector<Item> actualItemList = runPackage2(0);

		boolean sameResult = true;

		if(expectedItemList.size() != actualItemList.size()) //if the size is different, obviously there are not the same
			sameResult = false;

		if(sameResult == true) { //we only run this test if the lists have the same size. We will check the item one by one.
			for(int i=0; i<expectedItemList.size(); i++) {
				if(expectedItemList.get(i).getItem().equals(actualItemList.get(i).getItem()) == false)
					sameResult = false;	
			}
		}

		assertEquals(true, sameResult);
	}
	
	/**
	 * Description: This function will check when the type result is being set to 2, it will
	 * save the compulsory item list into the database only.
	 * 
	 * This method test the lower limit.
	 * 
	 * @throws Exception
	 */
	@Test
	public void typeResult2LowerLimitTest() throws Exception {

		//total cost = $1155.38, setting budget to $1155.38 (to test the lower limit)

		cb = new ControllerBudget(input, 115538, 1, 1, currentEvent); //Budget = $1155.38

		runPackage();
		
		Vector<Item> inputItemList = cb.getItemList();
		Vector<Item> expectedItemList = new Vector<Item>();

		/*Item is added in reverse added due to differentiateCompulsory method reverse the order 
		 * when adding item into compulsory list */
		expectedItemList.add(inputItemList.get(7)); //Windows_7_Basic
		expectedItemList.add(inputItemList.get(4)); //OCZ_120GB_SSD
		expectedItemList.add(inputItemList.get(2)); //Nikon_Cam

		Vector<Item> actualItemList = runPackage2(0);

		boolean sameResult = true;
		
		if(expectedItemList.size() != actualItemList.size()) //if the size is different, obviously there are not the same
			sameResult = false;
		
		if(sameResult == true) { //we only run this test if the lists have the same size. We will check the item one by one.
			for(int i=0; i<expectedItemList.size(); i++) {
				if(expectedItemList.get(i).getItem().equals(actualItemList.get(i).getItem()) == false)
					sameResult = false;	
			}
		}

		assertEquals(true, sameResult);
	}
	
	/**
	 * Description: This function will check when the type result is being set to 3, it will
	 * save the compulsory item list together with the selected combination item list into the database.
	 * 
	 * @throws Exception
	 */
	@Test
	public void typeResult3Test() throws Exception {

		//total cost = $1155.38, setting budget to $2000

		cb = new ControllerBudget(input, 200000, 1, 1, currentEvent); //Budget = $2000

		runPackage();
		
		Vector<Item> inputItemList = cb.getItemList();
		Vector<Item> expectedItemList1 = new Vector<Item>();
		Vector<Item> expectedItemList2 = new Vector<Item>();

 		/*Item is added in reverse added due to differentiateCompulsory method reverse the order 
		 * when adding item into compulsory list */
		expectedItemList1.add(inputItemList.get(7)); //Windows_7_Basic
		expectedItemList1.add(inputItemList.get(4)); //OCZ_120GB_SSD
		expectedItemList1.add(inputItemList.get(2)); //Nikon_Cam
				
		/*Add the expected computed item*/
		expectedItemList1.add(inputItemList.get(0)); //Apple_Ipad
		expectedItemList1.add(inputItemList.get(3)); //Monster_Earpiece
		
		/*This is the expected combination list if the second combination was choosen.*/
		expectedItemList2.add(inputItemList.get(7)); //Windows_7_Basic
		expectedItemList2.add(inputItemList.get(4)); //OCZ_120GB_SSD
		expectedItemList2.add(inputItemList.get(2)); //Nikon_Cam
		expectedItemList2.add(inputItemList.get(9)); //Software_Engineering_For_Dummies
		expectedItemList2.add(inputItemList.get(3)); //Monster_Earpiece

		Vector<Item> actualItemList = runPackage2(0); //Simply change this to 1 if wishes to test for second combination.

		boolean sameResult = true;
				
		if(expectedItemList1.size() != actualItemList.size()) //if the size is different, obviously there are not the same
			sameResult = false;
		
		if(sameResult == true) { //we only run this test if the lists have the same size. We will check the item one by one.
			for(int i=0; i<expectedItemList1.size(); i++) {
				if(expectedItemList1.get(i).getItem().equals(actualItemList.get(i).getItem()) == false)
					sameResult = false;	
			}
		}

		assertEquals(true, sameResult);
	}
	
	public void runPackage() {
		compulsoryList.add(3); // flag Nikon_Cam as compulsory, cost is $634.5
		compulsoryList.add(5); // flag OCZ_120GB_SSD as compulsory cost is $320.88
		compulsoryList.add(8); // flag Windows_7_Basic as compulsory, cost is $200
		
		cb.differentiateCompulsory(compulsoryList, 1); // compulsory list is not empty
	}
	
	public Vector<Item> runPackage2(int selectionIndex) {
		cb.findOptimalShopList(1, 1);

		cb.saveOptimizeOption(selectionIndex);

		return cb.getOptimizeItemList(currentEvent.getID());
	}

}
