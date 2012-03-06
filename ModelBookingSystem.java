import java.util.Vector;

public class ModelBookingSystem {
	EMDB db;

	//Construct
	public ModelBookingSystem() {
		db = new EMDB();
		db.set_name("test.sqlite");
	}
	
	
	
	public Vector<Venue> get_venue_by_cost(int upper, int lower){
		db.connect();
		Vector<Venue> venues = db.get_venue_list("cost", upper, lower);
		db.disconnect();
		return venues;
	}
	

	public Vector<Venue> get_venue_by_capacity(int upper, int lower){
		db.connect();
		Vector<Venue> venues = db.get_venue_list("capacity", upper, lower);
		db.disconnect();
		return venues;
	}	
	
	
	public Vector<Venue> get_venue_with_timeslot(){
		
		db.connect();
		Vector<Venue> venues = db.get_venue_list("all", 0,0);
		
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.get_bookings(current.getVenueID(), "venue"));
		}
		
		
		db.disconnect();
		
		
		return venues;

	}
	
	public void add_booking_to_db(int eventID, int venueID, TimeSlot wantedTimeSlot){
		db.connect();
		db.add_booking(eventID, venueID, wantedTimeSlot.getStartDateHour().toString(), wantedTimeSlot.getEndDateHour().toString());
		db.disconnect();
	}
	
	
}