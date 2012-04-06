package participant;

import java.util.Comparator;

public class ParticipantEmailComparator implements Comparator<Participant> {

	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getEmail().compareToIgnoreCase(objTwo.getEmail());
	}
	

}
