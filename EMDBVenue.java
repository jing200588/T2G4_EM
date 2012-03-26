
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;


/**
 * 
 * Database Extended Class - Venue
 * @author JunZhi
 *
 */
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

	public EMDBVenue(String aName){
		super(aName);
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
	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		int tableTotal = 2;
		
		String sql = new SelectQuery()
						.addCustomColumns("rowid")
						.addCustomFromTable("sqlite_master")
						.addCondition(
								ComboCondition.or(
									BinaryCondition.equalTo("name", EMDBSettings.TABLE_VENUE),
									BinaryCondition.equalTo("name", EMDBSettings.TABLE_VENUE_BOOKINGS)
								)
						)
						.validate().toString();

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("VERIFICATION - VENUE TABLES");
			this.dMsg(sql);
		}
		
		try {
			
			ResultSet result = this.runQueryResults(sql);
			int count = 0;
			
			if (result.next())
				count++;

			if (count == tableTotal)
				return true;
			
			return false;
			
		} catch (SQLException e) {
			return false;
		}
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
	public int addVenue(
			String aName, 
			String aAddress, 
			String aDescription, 
			int aCapacity, 
			int aCost
			)
	{
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
	public int addBooking(
			int aEventID, 
			int aVenueID, 
			String aTimeStart , 
			String aTimeEnd
			)
	{
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
			
			this.connect();
			
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
			
			result.close();
			this.disconnect();
			
			return place;
			
		} catch (SQLException e) {
			return null;
		}
		
	}

	

	

	
	
	
	
	
	
	/**
	 * Get List of Venues with Limiting Parameters
	 * @param clause
	 * @param upper
	 * @param lower
	 * @return
	 */
	public Vector<Venue> getVenueList(
			String aSearchType, 
			int aUpperLimit, 
			int aLowerLimit
			)
	{
		String sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.venueTable)
							.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET VENUE LIST BY " + aSearchType);
			this.dMsg(sql);
		}
			
		try{
			Vector<Venue> list = new Vector<Venue>();
			
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			
			while (result.next()) {
				
				int cost = result.getInt("cost");
				int capacity = result.getInt("capacity");
				
				if ( (aUpperLimit == 0 && aLowerLimit == 0) 
						|| (aSearchType.compareTo("cost") == 0 && cost <= aUpperLimit && cost >= aLowerLimit)
						|| (aSearchType.compareTo("capacity") == 0 && capacity <= aUpperLimit && capacity >= aLowerLimit )
						)
				{	
					
					Venue place = new Venue();
					
					place.updateID(result.getInt("venue_id"));
					place.updateName(result.getString("name"));
					place.updateAddress(result.getString("address"));
					place.updateDescription(result.getString("description"));
					place.updateCost(result.getInt("cost"));
					place.updateMaxCapacity(result.getInt("capacity"));
					
					list.add(place);
				}
			}
			
			result.close();
			this.disconnect();
			
			return list;
			
			
			
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	


	
	
	
	
	/**
	 * Get a single booking by BookingID
	 * @param aBookingID
	 * @return
	 */
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
			
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			TimeSlot slot = null;
			
			while(result.next()){
				slot = new TimeSlot(
						result.getInt("booking_id"),
						new DateHour(result.getString("time_start")),
						new DateHour(result.getString("time_end"))
						);
			}
	
			result.close();
			this.disconnect();
			
			return slot;
			
			
		} catch (SQLException e) {
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get a list of bookings by criteria
	 * @param aID
	 * @param aSearchType
	 * @return
	 */
	public Vector<TimeSlot> getBookingList(int aID, String aSearchType){
		String sql = "";
		
		switch (aSearchType){
			case "event":
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.bookingTable)
							.addCondition(BinaryCondition.equalTo(this.bookingEventID, aID))
							.validate().toString();
				break;
					
			case "venue":
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.bookingTable)
							.addCondition(BinaryCondition.equalTo(this.venueID, aID))
							.validate().toString();
				break;

			case "all":
			default:
				sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.bookingTable)
							.validate().toString();
				break;
		}
		
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET BOOKING BY " + aSearchType);
			this.dMsg(sql);
		}
		
		
		try{
			
			Vector<TimeSlot> list= new Vector<TimeSlot>();
			
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			
			while(result.next()){
				list.add(
						new TimeSlot(
							result.getInt("booking_id"),
							new DateHour(result.getString("time_start")),
							new DateHour(result.getString("time_end"))
							)
						);
			}

			
			result.close();
			this.disconnect();
			return list;
		
		
		} catch (SQLException e) {
			return null;
		}
			
		
		
	}
	
	
	
	/**
	 * Get Bookings attached to an Event
	 * @param aEventID
	 * @return
	 */
	public Vector<BookedVenueInfo> getEventBookings(int aEventID){
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.addCondition(BinaryCondition.equalTo(this.bookingEventID, aEventID))
						.validate().toString();
						
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET BOOKING OF #"+aEventID);
			this.dMsg(sql);
		}
		
		
		try{
			Vector<BookedVenueInfo> list = new Vector<BookedVenueInfo>();
			
			this.connect();
			ResultSet result = this.runQueryResults(sql);

			while (result.next()){
				TimeSlot timing = new TimeSlot(
										new DateHour(result.getString("time_start")),
										new DateHour(result.getString("time_end"))
										);
				Venue venue = this.getVenue(result.getInt("venue_id"));
				BookedVenueInfo info = new BookedVenueInfo(venue, timing);
				
				list.add(info);
			}
	
			result.close();
			this.disconnect();
			
			return list;
			
		} catch (SQLException e) {
			return null;
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * Finding a Venue by Name
	 * @param aSearchTerm
	 * @return
	 */
	public Vector<Venue> findVenue(String aSearchTerm){
		
		//A little sanitizing at work
		aSearchTerm = aSearchTerm.replaceAll("[^\\w]", " ");
		
		String sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.venueTable)
							.addCondition(BinaryCondition.like(this.venueName, "%"+aSearchTerm+"%"))
							.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("FIND VENUE");
			this.dMsg(sql);
		}
		
		
		try{
			Vector<Venue> list = new Vector<Venue>();
		
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			
			while(result.next()){
				Venue place = new Venue();
				place.updateID(result.getInt("venue_id"));
				place.updateName(result.getString("name"));
				place.updateAddress(result.getString("address"));
				place.updateDescription(result.getString("description"));
				place.updateCost(result.getInt("cost"));
				place.updateMaxCapacity(result.getInt("capacity"));
				
				list.add(place);
			}
			
			result.close();
			this.disconnect();
			
			return list;
			
		} catch (SQLException e) {
			return null;
		}
		
		
		
	}
	
	
	
	
	
	
	/**
	 * Update Venue Details.
	 * @param aVenueID
	 * @param aName
	 * @param aAddress
	 * @param aDescription
	 * @param aCapacity
	 * @param aCost
	 * @return
	 */
	public int updateVenue(
			int aVenueID, 
			String aName, 
			String aAddress, 
			String aDescription, 
			int aCapacity, 
			int aCost
			)
	{
		String sql	=	new UpdateQuery(this.venueTable)
								.addSetClause(this.venueName, aName)
								.addSetClause(this.venueAddress, aDescription)
								.addSetClause(this.venueDescription, aDescription)
								.addSetClause(this.venueCapacity, aCapacity)
								.addSetClause(this.venueCost, aCost)
								.addCondition(BinaryCondition.equalTo(this.venueID, aVenueID))
								.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("UPDATE VENUE #"+aVenueID);
			this.dMsg(sql);
		}
		
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	/**
	 * Update Booking Details.
	 * @param aBookingID
	 * @param aEventID
	 * @param aVenueID
	 * @param aTimeStart
	 * @param aTimeEnd
	 * @return
	 */
	public int updateBooking(
			int aBookingID,
			int aEventID, 
			int aVenueID, 
			String aTimeStart , 
			String aTimeEnd
			)
	{
		String sql	=	new UpdateQuery(this.bookingTable)
								.addSetClause(this.bookingEventID, aEventID)
								.addSetClause(this.bookingVenueID, aVenueID)
								.addSetClause(this.bookingTimeStart, aTimeStart)
								.addSetClause(this.bookingTimeEnd, aTimeEnd)
								.addCondition(BinaryCondition.equalTo(this.bookingID, aBookingID))
								.validate().toString();
					
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("UPDATE BOOKING #"+aBookingID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;	
	}
	
	
	
	
	
	
	/**
	 * Delete a venue.
	 * @param aVenueID
	 * @return
	 */
	public int deleteVenue(int aVenueID){
		String sql 	=	new DeleteQuery(this.venueTable)
								.addCondition(BinaryCondition.equalTo(this.venueID, aVenueID))
								.validate().toString();

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE VENUE #"+aVenueID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;	
	}
	
	
	
	
	
	/**
	 * Delete a single Booking.
	 * @param aBookingID
	 * @return
	 */
	public int deleteBooking(int aBookingID){
		String sql 	=	new DeleteQuery(this.bookingTable)
								.addCondition(BinaryCondition.equalTo(this.bookingID, aBookingID))
								.validate().toString();

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE BOOKING #"+aBookingID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;	
	}
	
	
	
	/**
	 * Delete All booking related to an Event
	 * @param aEventID
	 * @return
	 */
	public int deleteBookingAll(int aEventID){
		String sql 	=	new DeleteQuery(this.bookingTable)
								.addCondition(BinaryCondition.equalTo(this.bookingEventID, aEventID))
								.validate().toString();

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE BOOKING FOR EVENT #"+aEventID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;	
	}
	
	
	
}