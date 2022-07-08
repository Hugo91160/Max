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
            ds.setUrl("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println( "Opened database successfully" );        
    }
    
    public void CreateTableSounds() {
    	String query = "CREATE TABLE IF NOT EXISTS SOUNDS ( FILEPATH TEXT NOT NULL PRIMARY KEY, DATE TEXT NOT NULL )";

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
    	String query = "CREATE TABLE IF NOT EXISTS RECORDS ( ID INTEGER PRIMARY KEY, BEGIN_DATE TEXT NOT NULL, END_DATE TEXT NOT NULL )";

		try ( Connection conn = ds.getConnection();
		     Statement stmt = conn.createStatement(); ) {
		   int rv = stmt.executeUpdate( query );
		} catch ( SQLException e ) {
		   e.printStackTrace();
		   System.exit( 0 );
		}
		System.out.println( "Created table RECORDS successfully" );
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
		System.out.println( "Created table RECORDS successfully" );
    }

}