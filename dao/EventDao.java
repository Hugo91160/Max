package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Event;
import model.Record;
import utils.Global;

public class EventDao {
	public Database db = new Database();
	
	public List<Event> GetEvents(String recordId) {
		List<Event> result = new ArrayList<Event>();
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM EVENTS WHERE RECORD_ID = '" + recordId + "';" );
	      
			while ( rs.next() ) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Global.getDateTimePattern());
				LocalDateTime date = LocalDateTime.parse(rs.getString("DATE"),formatter);
				
				Event event = new Event(rs.getString("ID"), rs.getString("RECORD_ID"), date);
				
				result.add(event);
			}
			rs.close();
			stmt.close();
			c.close();
			System.out.println("Events retrieved from database succesfully");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		return result;
	}
	
	public void AddEvent(Event event) {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.prepareStatement("INSERT INTO EVENTS(ID, RECORD_ID, DATE) VALUES (?,?,?)");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Global.getDateTimePattern());
			String formattedDateTime = event.getDate().format(formatter);
			
			stmt.setString(1, event.getId());
			stmt.setString(2, event.getRecordId());
			stmt.setString(3, formattedDateTime);
			stmt.executeUpdate();
			
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
	}
}
