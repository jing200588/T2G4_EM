package participant;

import java.util.Comparator;

public class ParticipantRemarkComparator implements Comparator<Participant> {

	//@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getRemark().compareToIgnoreCase(objTwo.getRemark());
	}
	

}
