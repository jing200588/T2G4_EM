package emserver;

import java.util.Vector;

import emdb.EMDBII;
import event.Eventitem;

public class BeanMain {

	public String getEventList(){
		String result = "";
		
		
		EMDBII db = new EMDBII();
		
		Vector<Eventitem> list = db.eventDB().getEventList();
		int size = list.size();
		
		
		
		for (int i=0; i<size; i++){
			int pid = list.get(i).getID();
			list.get(i).addBVI(db.venueDB().getEventBookings(pid));	
			list.get(i).setItemList(db.budgetDB().getBudgetListOptimized(pid));
			list.get(i).setParticipantList(db.participantDB().getParticipantList(pid));
		}
		
		
		if (size > 0){
			result += "<ul>";
		}
		
		for (int i=0; i<size; i++){
			Eventitem ei = list.get(i);
			result += "<li>";
				result += "<span class=\"title\">"+ei.getName() +"</span>";
				result += "<ul>";
					result += "<li class=\"ei_daye\">"+ei.getStartDateTime().getDateRepresentation() + " - " + ei.getEndDateTime().getDateRepresentation() +"</li>";
					result += "<li class=\"ei_des\">"+ei.getDescription()+"</li>";
				result += "</ul>";		
			result += "</li>";
			
		}
		
		
		if (size > 0){
			result += "</ul>";
		}
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getEventListArchive(){
		String result = "";
		
		
		EMDBII db = new EMDBII();
		
		Vector<Eventitem> list = db.eventDB().getArchiveEventList();
		int size = list.size();
		
		
		
		for (int i=0; i<size; i++){
			int pid = list.get(i).getID();
			//list.get(i).addBVI(db.venueDB().getEventBookings(pid));	
			//list.get(i).setitem_list(db.budgetDB().getBudgetListOptimized(pid));
			//list.get(i).setParticipantList(db.participantDB().getParticipantList(pid));
		}
		
		
		if (size > 0){
			result += "<ul>";
		}
		
		for (int i=0; i<size; i++){
			Eventitem ei = list.get(i);
			result += "<li>";
				result += ei.getName();
				result += "<ul>";
					result += "<li class=\"ei_daye\">"+ei.getStartDateTime().getDateRepresentation() + " - " + ei.getEndDateTime().getDateRepresentation() +"</li>";
				result += "</ul>";		
			result += "</li>";
			
		}
		
		
		if (size > 0){
			result += "</ul>";
		}
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
}
