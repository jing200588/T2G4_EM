import java.util.Vector;


public interface BudgetInterface {
	
	public Vector<Item> getItemList(int id);
	
	public void compulsory(Vector<Integer> vec);
	
	public String budgetleft();
	
	public String findOptimalShopList(int hastype,int hassatisfaction) throws Exception;
	
	public void differentiateCompulsory(int hassatisfaction);
	
	public void sendDBList(int option);
	
	public int noOfCombination();
}
