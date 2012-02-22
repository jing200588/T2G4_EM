import java.util.Vector;


public interface IBudgetAssistant {
		
	public String findOptimalShopList(int hastype) throws Exception;
	
	public String sendDBList(int select);
	
	public void compulsory(String com);
	
	public String budgetleft();
	
	public String displayList();
	
	public String displaySplit(int split);
	
	public int noOfCombination();

	//public void sendDBList(int option);
}
