package emdb;

import venue.*;

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
public class EMDBVenue extends EMDBBase{
	
	private DbTable 	masterTable = this.schema.addTable(EMDBSettings.TABLE_MASTER);
	private DbColumn	masterName	= this.masterTable.addColumn("name");

	
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
	
	public EMDBVenue(String aName, boolean aDebugState){
		super(aName, aDebugState);
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
		this.venueCapacity		=	this.venueTable.addColumn("capacity", "INTEGER NOT NULL DEFAULT", 11);
		this.venueCost			=	this.venueTable.addColumn("cost", "INTEGER NOT NULL DEFAULT", 11);
		
		
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
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	
	
	/**
	 * DROP the database tables
	 */
	public void drop(){
		String 	sql =	DropQuery.dropTable(this.venueTable)
							.validate().toString();
		String sql2 =	DropQuery.dropTable(this.bookingTable)
							.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	/**
	 * DROP the database, some tables
	 * @param aType
	 */
	public void drop(String aType){
		String sql = "";
		
		if (aType.compareTo("venue") == 0){
			sql 	=	DropQuery.dropTable(this.venueTable)
							.validate().toString();
				
		}else if (aType.compareTo("booking") == 0){
			sql =	DropQuery.dropTable(this.bookingTable)
					.validate().toString();
		}
		
		if (this.dbDebug){
			this.dMsg(sql);
		}
		
		if (!sql.isEmpty()){
			this.queue(sql);
			this.commit();
		}
			
		
	}
	
	
	
	
	
	/**
	 * "TRUNCATE" the database tables
	 */
	public void truncate(){
		String sql 	=	new DeleteQuery(this.venueTable)
							.validate().toString();
		String sql2 =	new DeleteQuery(this.bookingTable)
							.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}

		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	/**
	 * "TRUNCATE" some database tables
	 * @param aType
	 */
	public void truncate(String aType){
		String sql = "";
		
		if (aType.compareTo("venue") == 0){
			sql =	new DeleteQuery(this.venueTable)
							.validate().toString();
			
		}else if (aType.compareTo("booking") == 0){
			sql =	new DeleteQuery(this.bookingTable)
						.validate().toString();
		}
		
		if (this.dbDebug){
			this.dMsg(sql);
		}

		if (!sql.isEmpty()){
			this.queue(sql);
			this.commit();
		}

	}	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){

		if (this.dbDebug){
			this.dMsg(EMDBSettings.TABLE_VENUE);
			this.dMsg(EMDBSettings.TABLE_VENUE_BOOKINGS);
		}
		
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.masterTable)
						.addCondition(
								ComboCondition.or(
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_VENUE),
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_VENUE_BOOKINGS)
								)
						)
						.validate().toString();

		return this.verification(sql, 2);
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
						  		.addColumn(this.venueName, aName.replaceAll("[\']", ""))
						  		.addColumn(this.venueAddress, aAddress.replaceAll("[\']", ""))
						  		.addColumn(this.venueDescription, aDescription.replaceAll("[\']", ""))
						  		.addColumn(this.venueCapacity, aCapacity)
						  		.addColumn(this.venueCost, aCost)
						  		.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg("ADD A VENUE");
			this.dMsg(sql);
		}
		

		
		this.connect();	
		
		int id = this.runQueryKey(sql);
	
		if (this.dbDebug){
			this.dMsg("NEW VENUE ID #" + id);
		}
		
		
		this.disconnect();
		
		return id;

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
			
		
		
		
		if (this.dbDebug){
			this.dMsg("BOOK A VENUE");
			this.dMsg(sql);
		}
		
		this.connect();
	
		int id = this.runQueryKey(sql);
		
		if (this.dbDebug){
			this.dMsg("NEW BOOKING ID #" + id);
		}
		
		this.disconnect();
			
		return id;
	

		
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
		
		
		if (this.dbDebug){
			this.dMsg("GET VENUE #"+aVenueID);
			this.dMsg(sql);
		}
		
		this.connect();
		
		Vector<Object[]> result = this.runQueryResults(sql);
		
		Venue place = new Venue();
		
		if (result.size() > 0){
			Object[] row = result.get(0);
			place.updateName		( 	(row[1] == null) ? "" : row[1].toString()		);
			place.updateAddress		( 	(row[2] == null) ? "" : row[2].toString()		);
			place.updateDescription	(	(row[3] == null) ? "" : row[3].toString() 		);
			place.updateMaxCapacity	( 	new Integer(row[4].toString()) 					);	
			place.updateCost		( 	new Integer(row[5].toString()) 					);
		}


		this.disconnect();
		
		return place;
	
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
		
		
		if (this.dbDebug){
			this.dMsg("GET VENUE LIST BY " + aSearchType);
			this.dMsg(sql);
		}
			
		Vector<Venue> list = new Vector<Venue>();
			
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);

		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			int cost = Integer.parseInt(row[5].toString());
			int capacity = Integer.parseInt(row[4].toString());
			
			
			if ( (aUpperLimit == 0 && aLowerLimit == 0) 
					|| (aSearchType.compareTo("cost") == 0 && cost <= aUpperLimit && cost >= aLowerLimit)
					|| (aSearchType.compareTo("capacity") == 0 && capacity <= aUpperLimit && capacity >= aLowerLimit )
					)
			{	
				
				Venue place = new Venue();
				
				place.updateID			( 	new Integer(row[0].toString())	);
				place.updateName		( 	(row[1] == null) ? "" : row[1].toString()		);
				place.updateAddress		( 	(row[2] == null) ? "" : row[2].toString()		);
				place.updateDescription	(	(row[3] == null) ? "" : row[3].toString() 		);
				place.updateMaxCapacity	( 	new Integer(row[4].toString()) 					);	
				place.updateCost		( 	new Integer(row[5].toString()) 					);
				
				list.add(place);
			}
			
		}
		
		this.disconnect();
		
		return list;
		
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
			
		
		if (this.dbDebug){
			this.dMsg("GET BOOKING #"+aBookingID);
			this.dMsg(sql);
		}
		
	
			
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		TimeSlot slot = null;
		
		if (result.size() > 0){
			Object[] row = result.get(0);
			slot = new TimeSlot(
					new Integer(row[0].toString()),
					new MyDateTime( (row[3] == null) ? "" : row[3].toString() ),
					new MyDateTime( (row[4] == null) ? "" : row[4].toString() )
					);
		}
		
		this.disconnect();
		
		return slot;
			
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get a list of bookings by criteria
	 * @param aID
	 * @param aSearchType
	 * @return
	 */
	public Vector<TimeSlot> getBookingList(int aID, String aSearchType){
		String sql = "";
			

		
	
		if(aSearchType.compareTo("event") == 0){
			sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.addCondition(BinaryCondition.equalTo(this.bookingEventID, aID))
						.validate().toString();
			
		}else if(aSearchType.compareTo("venue") == 0){
			sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.addCondition(BinaryCondition.equalTo(this.bookingVenueID, aID))
						.validate().toString();
		}else if(aSearchType.compareTo("capacity") == 0){
			sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.addCondition(BinaryCondition.equalTo(this.bookingVenueID, aID))
						.validate().toString();
		}else {
			sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.bookingTable)
						.validate().toString();
		}
		
		
		
		if (this.dbDebug){
			this.dMsg("GET BOOKING BY " + aSearchType);
			this.dMsg(sql);
		}
		

			
		Vector<TimeSlot> list= new Vector<TimeSlot>();
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		for (int i=0; i < size; i++){
			Object[] row = result.get(i);
			list.add(
					new TimeSlot(
							new Integer(row[0].toString()),
							new MyDateTime( (row[3] == null) ? "" : row[3].toString() ),
							new MyDateTime( (row[4] == null) ? "" : row[4].toString() )
							)
					);
		}

		this.disconnect();
		return list;
			
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
						
		
		if (this.dbDebug){
			this.dMsg("GET BOOKING OF #"+aEventID);
			this.dMsg(sql);
		}
		
	
		Vector<BookedVenueInfo> list = new Vector<BookedVenueInfo>();
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		
		for(int i=0; i<size; i++){
			Object[] row = result.get(i);
			
			TimeSlot timing = new TimeSlot(
									new MyDateTime( (row[3] == null) ? "" : row[3].toString() ),
									new MyDateTime( (row[4] == null) ? "" : row[4].toString() )
									);
			Venue venue = this.getVenue( new Integer(row[2].toString()) );
			BookedVenueInfo info = new BookedVenueInfo(venue, timing);
			
			list.add(info);
		}
	
		this.disconnect();
		
		return list;
		
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
							.addCondition(
								ComboCondition.or(
										BinaryCondition.like(this.venueName, "%"+aSearchTerm+"%"),
										BinaryCondition.like(this.venueAddress, "%"+aSearchTerm+"%"),
										BinaryCondition.like(this.venueDescription, "%"+aSearchTerm+"%")
								)
							)
							.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg("FIND VENUE");
			this.dMsg(sql);
		}
		
		
	
		Vector<Venue> list = new Vector<Venue>();
	
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);

		int size = result.size();
		
		for(int i=0; i<size; i++){
			Object[] row = result.get(i);
			Venue place = new Venue();

			place.updateID			( 	new Integer(row[0].toString())	);
			place.updateName		( 	(row[1] == null) ? "" : row[1].toString()		);
			place.updateAddress		( 	(row[2] == null) ? "" : row[2].toString()		);
			place.updateDescription	(	(row[3] == null) ? "" : row[3].toString() 		);
			place.updateMaxCapacity	( 	new Integer(row[4].toString()) 					);	
			place.updateCost		( 	new Integer(row[5].toString()) 					);

			
			list.add(place);
		}
		
		this.disconnect();
		
		return list;
		
		
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
								.addSetClause(this.venueName, aName.replaceAll("[\']", ""))
								.addSetClause(this.venueAddress, aAddress.replaceAll("[\']", ""))
								.addSetClause(this.venueDescription, aDescription.replaceAll("[\']", ""))
								.addSetClause(this.venueCapacity, aCapacity)
								.addSetClause(this.venueCost, aCost)
								.addCondition(BinaryCondition.equalTo(this.venueID, aVenueID))
								.validate().toString();
		
		
		if (this.dbDebug){
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
					
		if (this.dbDebug){
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

		if (this.dbDebug){
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

		if (this.dbDebug){
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

		if (this.dbDebug){
			this.dMsg("DELETE BOOKING FOR EVENT #"+aEventID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;	
	}
	
	
	
}