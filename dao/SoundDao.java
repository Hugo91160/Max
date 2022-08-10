package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Sound;

public class SoundDao {
	public static int id = 0;
	public Database db = new Database();
	
	public void AddSound(Sound sound) {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.prepareStatement("INSERT INTO SOUNDS(FILEPATH, DATE, DURATION) VALUES (?,?,?)");
			stmt.setString(1, sound.getFilePath());
			stmt.setString(2, sound.getDate().toLocaleString());
			stmt.setDouble(3, sound.getDurationInSeconds());
			stmt.executeUpdate();
			
			System.out.println("File " + sound.getFilePath() + " has been added to the database.");
			
			id++;
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public void DeleteSound(String filePath) {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");

			stmt = c.prepareStatement("DELETE FROM SOUNDS WHERE FILEPATH = ?;");
			stmt.setString(1, filePath);
			stmt.executeUpdate();
			
			System.out.println("Sound " + filePath + " has been deleted");
			
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	
	public List<Sound> GetSounds(){
		List<Sound> result = new ArrayList<Sound>();
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Max.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM SOUNDS;" );
	      
			while ( rs.next() ) {
				Sound sound = new Sound(rs.getString("FILEPATH"));
				result.add(sound);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return result;
	}
	
}
