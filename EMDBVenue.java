import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
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
	}
	
	
	
	
	
	
	
}