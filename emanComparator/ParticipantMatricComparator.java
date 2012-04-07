package emanComparator;

import java.util.Comparator;

import participant.Participant;

public class ParticipantMatricComparator implements Comparator<Participant> {

//	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getMatric().compareToIgnoreCase(objTwo.getMatric());
	}
	

}
