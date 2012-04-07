package emanComparator;

import java.util.Comparator;

import participant.Participant;

public class ParticipantAddressComparator implements Comparator<Participant> {

//	@Override
	public int compare(Participant objOne, Participant objTwo) {
		// TODO Auto-generated method stub
		return objOne.getAddress().compareToIgnoreCase(objTwo.getAddress());
	}
	

}