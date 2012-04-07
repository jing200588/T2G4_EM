package emanComparator;

import java.util.Comparator;

import participant.Participant;

public class ParticipantContactComparator implements Comparator<Participant> {

//	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getContact().compareToIgnoreCase(objTwo.getContact());
	}
	

}
