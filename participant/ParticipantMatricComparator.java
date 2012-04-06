package participant;

import java.util.Comparator;

public class ParticipantMatricComparator implements Comparator<Participant> {

//	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getMatric().compareToIgnoreCase(objTwo.getMatric());
	}
	

}
