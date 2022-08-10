package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Event;
import model.Record;
import utils.Global;

public class RecordDao {
	public Database db = new Database();
	public EventDao eventDao = new EventDao();

	public List<Record> GetRecords() {
		List<Record> result = new ArrayList<Record>();

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RECORDS ORDER BY BEGIN_DATE DESC;");

			while (rs.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Global.getDateTimePattern());
				LocalDateTime beginDate = LocalDateTime.parse(rs.getString("BEGIN_DATE"),formatter);
				LocalDateTime endDate = LocalDateTime.parse(rs.getString("END_DATE"),formatter);
				
				List<Event> events = eventDao.GetEvents(rs.getString("ID"));				

				Record record = new Record(rs.getString("ID"), beginDate, endDate, events);
				result.add(record);
			}
			rs.close();
			stmt.close();
			c.close();
			System.out.println("Records retrieved from database succesfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return result;
	}
	
	public void AddRecord(Record record) {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.prepareStatement("INSERT INTO RECORDS(ID, BEGIN_DATE, END_DATE) VALUES (?,?,?)");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Global.getDateTimePattern());
			String formattedBeginDateTime = record.getBeginDate().format(formatter);
			String formattedEndDateTime = record.getEndDate().format(formatter);
			
			stmt.setString(1, record.getId());
			stmt.setString(2, formattedEndDateTime);
			stmt.setString(3, formattedBeginDateTime);
			stmt.executeUpdate();
			stmt.close();
			c.close();
			
			for(int i=0; i<record.getEvents().size(); i++) {
				eventDao.AddEvent(record.getEvents().get(i));
			}
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}
