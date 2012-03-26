
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;



class EMDBVenue extends EMDBBase{
	

	
    private DbTable 	venueTable;
    private DbColumn 	venueID;
    private DbColumn 	venueName;
    private DbColumn 	venueAddress;
    private DbColumn 	venueDescription;
    private DbColumn 	venueCapacity;
    private DbColumn	venueCost;
	
	
    private DbTable 	bookingTable;
    private DbColumn 	bookingID;
    private DbColumn 	bookingEventID;
    private DbColumn 	bookingVenueID;
    private DbColumn 	bookingTimeStart;
    private DbColumn 	bookingTimeEnd;

    
    
    

	
	
	
    /**
     * Constructor
     */
	public EMDBVenue(){
		super();
		this.setTable();
	}

	
	
	
	/**
	 * Set up the table structure.
	 */
	private void setTable(){
		this.venueTable 		= 	this.schema.addTable(EMDBSettings.TABLE_VENUE);
		this.venueID			=	this.venueTable.addColumn("venue_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.venueName			=	this.venueTable.addColumn("name", "TEXT NOT NULL", null);
		this.venueAddress		=	this.venueTable.addColumn("address", "TEXT", null);
		this.venueDescription	=	this.venueTable.addColumn("description", "TEXT", null);
		this.venueCapacity		=	this.venueTable.addColumn("capacity", "INTEGER NOT NULL DEFAULT (0)", 11);
		this.venueCost			=	this.venueTable.addColumn("cost", "INTEGER NOT NULL DEFAULT (0)", 11);
		
		
		this.bookingTable 		= 	this.schema.addTable(EMDBSettings.TABLE_VENUE_BOOKINGS);
	   	this.bookingID			=	this.bookingTable.addColumn("booking_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
	   	this.bookingEventID		=	this.bookingTable.addColumn("event_id", "INEGER NOT NULL", null);
	   	this.bookingVenueID		=	this.bookingTable.addColumn("venue_id", "INTEGER NOT NULL", null);
	   	this.bookingTimeStart	=	this.bookingTable.addColumn("time_start", "TEXT", null);
	   	this.bookingTimeEnd		=	this.bookingTable.addColumn("time_end", "TEXT", null);
	}
	
	
	/*
	 * ******************************************************
	 * 
	 * Setup and Management
	 * 
	 * ******************************************************
	 */
	
	/**
	 * CREATE the database tables
	 */
	public void setup(){
		String sql 	=	new CreateTableQuery(this.venueTable, true)
						.validate().toString();
		
		String sql2 =	new CreateTableQuery(this.bookingTable, true)
						.validate().toString();	
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Setup Venue: "+ sql);
			this.dMsg("Setup Booking: "+ sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	/**
	 * DROP the database tables
	 */
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.venueTable)
						.validate().toString();
		String sql2 =	DropQuery.dropTable(this.bookingTable)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("Cleanup Venue: "+ sql);
			this.dMsg("Cleanup Booking: "+ sql2);
		}
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD Options
	 * 
	 * ******************************************************
	 */
	
	
	
	/**
	 * Add a Venue.
	 * @param aName
	 * @param aAddress
	 * @param aDescription
	 * @param aCapacity
	 * @param aCost
	 * @return
	 */
	public int addVenue(String aName, String aAddress, String aDescription, int aCapacity, int aCost){
		String sql	=	new InsertQuery(this.venueTable)
					  		.addColumn(this.venueName, aName)
					  		.addColumn(this.venueAddress, aAddress)
					  		.addColumn(this.venueDescription, aDescription)
					  		.addColumn(this.venueCapacity, aCapacity)
					  		.addColumn(this.venueCost, aCost)
					  		.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("ADD A VENUE");
			this.dMsg(sql);
		}
		

		try {
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			int id = 0;
			
			if (result.next()){
				id = result.getInt("venue_id");
			}

			result.close();
			this.disconnect();
			return id;
			
			
			
		} catch (SQLException e) {
			return 0;
		}

		
	}
	
	
	
	
	
	/**
	 * Booking of a Venue.
	 * @param aEventID
	 * @param aVenueID
	 * @param aTimeStart
	 * @param aTimeEnd
	 * @return
	 */
	public int addBooking(int aEventID, int aVenueID, String aTimeStart , String aTimeEnd){
		String sql	=	new InsertQuery(this.bookingTable)
					  		.addColumn(this.bookingEventID, aEventID)
					  		.addColumn(this.bookingVenueID, aVenueID)
					  		.addColumn(this.bookingTimeStart, aTimeStart)
					  		.addColumn(this.bookingTimeEnd, aTimeEnd)
					  		.validate().toString();	
		
		
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("BOOK A VENUE");
			this.dMsg(sql);
		}
		

		try {
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			int id = 0;
			
			if (result.next()){
				id = result.getInt("booking_id");
			}

			result.close();
			this.disconnect();
			return id;
			

		} catch (SQLException e) {
			return 0;
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get Venue Details by VenueID
	 * @param aVenueID
	 * @return
	 */
	public Venue getVenue(int aVenueID){
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.venueTable)
						.addCondition(BinaryCondition.equalTo(this.venueID, aVenueID))
						.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET VENUE #"+aVenueID);
			this.dMsg(sql);
		}
		
		try{
			ResultSet result = this.runQueryResults(sql);
			
			Venue place = new Venue();
			while(result.next()){
				place.updateID(aVenueID);
				place.updateName(result.getString("name"));
				place.updateAddress(result.getString("address"));
				place.updateDescription(result.getString("description"));
				place.updateCost(result.getInt("cost"));
				place.updateMaxCapacity(result.getInt("capacity"));
			}
			
			return place;
			
		} catch (SQLException e) {
			return null;
		}
		
	}

	
	
	
	
	public TimeSlot getBooking(int aBookingID){
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.addCondition(BinaryCondition.equalTo(this.bookingID, aBookingID))
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET BOOKING #"+aBookingID);
			this.dMsg(sql);
		}
		
		try{
			ResultSet result = this.runQueryResults(sql);
			TimeSlot slot = null;
			
			while(result.next()){
				slot = new TimeSlot(
						result.getInt("booking_id"),
						new DateHour(result.getString("time_start")),
						new DateHour(result.getString("time_end"))
						);
			}
	
			return slot;
			
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	public void getVenueList(){
		
		
		
	}
	
	public Vector<TimeSlot> getBookingList(){
		Vector<TimeSlot> list= new Vector<TimeSlot>();
		
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	public void findVenue(){
		
	}
	
	
	
	
	
	
	
	public void updateVenue(){
		
	}
	
	public void updateBooking(){
		
	}
	
	
	
	
	
	
	
	public void deleteVenue(){
		
	}
	
	public void deleteBooking(){
		
	}
	
	
	
	
	
}