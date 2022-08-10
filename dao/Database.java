package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class Database {
	private SQLiteDataSource ds;

    public Database() {
        ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:Max.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println( "Opened database successfully" );
    }
    
    public void CreateTableSounds() {
    	String query = "CREATE TABLE IF NOT EXISTS SOUNDS ( FILEPATH TEXT NOT NULL PRIMARY KEY, DATE TEXT NOT NULL, DURATION REAL )";

		try ( Connection conn = ds.getConnection();
		     Statement stmt = conn.createStatement(); ) {
		   int rv = stmt.executeUpdate( query );
		} catch ( SQLException e ) {
		   e.printStackTrace();
		   System.exit( 0 );
		}
		System.out.println( "Created table SOUNDS successfully" );
    }
    
    public void CreateTableRecords() {
    	String query = "CREATE TABLE IF NOT EXISTS RECORDS ( ID TEXT PRIMARY KEY, BEGIN_DATE TEXT NOT NULL, END_DATE TEXT NOT NULL )";

		try ( Connection conn = ds.getConnection();
		     Statement stmt = conn.createStatement(); ) {
		   int rv = stmt.executeUpdate( query );
		} catch ( SQLException e ) {
		   e.printStackTrace();
		   System.exit( 0 );
		}
		System.out.println( "Created table RECORDS successfully" );
    }
    
    public void CreateTableEvents() {
    	String query = "CREATE TABLE IF NOT EXISTS EVENTS "
    			+ "( ID TEXT PRIMARY KEY, "
    			+ "RECORD_ID TEXT, "
    			+ "DATE TEXT NOT NULL, "
    			+ "CONSTRAINT FK_RECORDS "
    			+ "		FOREIGN KEY (RECORD_ID)"
    			+ " 	REFERENCES RECORDS(ID)"
    			+ ")";

		try ( Connection conn = ds.getConnection();
		     Statement stmt = conn.createStatement(); ) {
		   int rv = stmt.executeUpdate( query );
		} catch ( SQLException e ) {
		   e.printStackTrace();
		   System.exit( 0 );
		}
		System.out.println( "Created table EVENTS successfully" );
    }
    
    public void CreateTableSettings() {
    	String query = "CREATE TABLE IF NOT EXISTS SETTINGS ( EMAIL TEXT, THRESHOLD REAL, NOTIFY INT, EMAIL_KEY TEXT )";

		try ( Connection conn = ds.getConnection();
		     Statement stmt = conn.createStatement(); ) {
		   int rv = stmt.executeUpdate( query );	   
		} catch ( SQLException e ) {
		   e.printStackTrace();
		   System.exit( 0 );
		}
		System.out.println( "Created table SETTINGS successfully" );
    }

}