package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Settings;

public class SettingsDao {
	public Database db = new Database();
	
	public Settings GetSettings() {
		List<Settings> result = new ArrayList<Settings>();
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM SETTINGS;" );
	      
			while ( rs.next() ) {
				Settings settings = new Settings(rs.getString("EMAIL"), rs.getInt("THRESHOLD"), rs.getBoolean("NOTIFY"), rs.getString("EMAIL_KEY"));
				result.add(settings);
			}
			rs.close();
			stmt.close();
			c.close();
			System.out.println("Settings retrieved from database succesfully");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		return result.size() == 0 ? null : result.get(0);
	}
	
	public void SaveSettings(Settings settings) {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			
			// If not settings are set yet, we insert a row. Otherwise we rewrite the row.
			if (GetSettings() == null) {
				stmt = c.prepareStatement("INSERT INTO SETTINGS(EMAIL, THRESHOLD, NOTIFY, EMAIL_KEY) VALUES (?,?,?,?)");
			} 
			else {
				stmt = c.prepareStatement("UPDATE SETTINGS SET EMAIL = ?, THRESHOLD = ?, NOTIFY = ?, EMAIL_KEY = ? WHERE 1=1;");
			}
			
			stmt.setString(1, settings.getEmailAddress());
			stmt.setFloat(2, settings.getAmplitudeThreshold());
			stmt.setBoolean(3, settings.isNotifyByEmail());
			stmt.setString(4, settings.getEmailKey());
			stmt.executeUpdate();
			
			System.out.println("Settings have been added to the database.");
			
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}
