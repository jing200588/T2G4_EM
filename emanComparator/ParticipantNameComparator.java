package emanComparator;

import java.util.Comparator;

import participant.Participant;

public class ParticipantNameComparator implements Comparator<Participant>{

//	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
}

