import java.util.Vector;


public class ModelBookingSystem {
	EMDBII db;

	//Construct
	public ModelBookingSystem() {
		db = new EMDBII();
		//db.set_name(EMDBSettings.DATABASE_NAME);
	}
	
	
	
	/**
	 * Description: Gets a list of available venues within the cost range. (includes booked timeslots)
	 * @param upper
	 * @param lower
	 * @return
	 */
	public Vector<Venue> get_venue_by_cost(int upper, int lower){
		/*
		db.connect();
		Vector<Venue> venues = db.get_venue_list("cost", upper, lower);
		
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.get_bookings(current.getVenueID(), "venue"));
		}
		
		
		db.disconnect();
		*/
		Vector<Venue> venues = db.venueDB().getVenueList("cost", upper, lower);
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.venueDB().getBookingList(current.getVenueID(), "venue"));
		}
		
		
		return venues;
	}
	

	
	/**
	 * Description: Gets a list of venues within the capacity range. (includes booked timeslots)
	 * @param upper
	 * @param lower
	 * @return
	 */
	public Vector<Venue> get_venue_by_capacity(int upper, int lower){
		/*
		db.connect();
		Vector<Venue> venues = db.get_venue_list("capacity", upper, lower);
		
		
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.get_bookings(current.getVenueID(), "venue"));
		}
		
		db.disconnect();*/
		
		Vector<Venue> venues = db.venueDB().getVenueList("capacity", upper, lower);
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.venueDB().getBookingList(current.getVenueID(), "venue"));
		}
		
		return venues;
	}	
	
	
	
	/**
	 * Description: Gets a list of venues without limitations. (includes booked timeslots)
	 * @return
	 */
	public Vector<Venue> get_all_venue(){
		
		/*
		db.connect();
		Vector<Venue> venues = db.get_venue_list("all", 0,0);
		
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.get_bookings(current.getVenueID(), "venue"));
		}
		
		
		db.disconnect();
		*/
		Vector<Venue> venues = db.venueDB().getVenueList("all", 0, 0);
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.venueDB().getBookingList(current.getVenueID(), "venue"));
		}
		return venues;

	}
	
	
	/**
	 * Description: Saving a booking to database
	 * @param eventID
	 * @param venueID
	 * @param wantedTimeSlot
	 */
	public void add_booking_to_db(int eventID, int venueID, TimeSlot wantedTimeSlot){
		//db.out("booking now");
		/*
		db.connect();
		db.add_booking(eventID, venueID, wantedTimeSlot.getStartDateTime().getDateTimeRepresentation(), wantedTimeSlot.getEndDateTime().getDateTimeRepresentation());
		db.disconnect();
		*/
		
		db.venueDB().addBooking(
				eventID, 
				venueID, 
				wantedTimeSlot.getStartDateTime().getDateTimeRepresentation(), 
				wantedTimeSlot.getEndDateTime().getDateTimeRepresentation()
				);
		
	}
	

	
	/**
	 * Description: Finding a venue by its name (Allows substrings of names).
	 * @param name
	 * @return
	 */
	public Vector<Venue> find_venue_by_name(String name){
		Vector<Venue> venues;
		
		/*
		db.connect();
		venues = db.find_venue_by_name(name);
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.get_bookings(current.getVenueID(), "venue"));
		}
		db.disconnect();
		*/
		
		venues = db.venueDB().findVenue(name);
		int count = venues.size();
		for (int i=0; i< count; i++){
			Venue current = venues.get(i);
			current.bookTimeSlotBlock(db.venueDB().getBookingList(current.getVenueID(), "venue"));
		}
		
		
		return venues;
	}
	
}