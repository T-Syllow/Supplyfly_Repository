package supplyfly.datenbankzugriff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import supplyfly.objects.Bestellung;

public class DBAccess {
	
	private static Connection conn = null;
	private static String url = "jdbc:mysql://3.69.96.96:3306/";		//hier steht aktuell der HOSTNAME + PORT
	private static String dbName = "db2";
	private static String driver = "com.mysql.cj.jdbc.Driver";	
	private static String userName = "db2";
	private static String password = "!db2.java22?";	//Das Passwort sollte eigentlich in args[0] gespeichert werden.
	
	public static void getConnectionToDatabase() {
		System.out.println("Die Datenbank wird versucht abzurufen..");
		
		/*
		 * Die folgenden Konstanten könnten auch in ein Property-File
		 * ausgelagert werden.
		 */
		
		
		
		try {
			Class.forName(supplyfly.datenbankzugriff.DBAccess.getDriver());
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cutConnectionToDatabase() {
		//cut connection to the database before ending program.
	}
	
	

	//Connection Methods der Bestellungen.
	
	public static Bestellung getBestellungByNr() {
		return null;
	}
																//funktioniert noch nicht richtig..
//	public static ArrayList<Bestellung> getAlleBestellung() {	//ruft alle Bestellungen aus Datebank ab und speichert sie in ArrayList
//		if(conn == null) {
//			getConnectionToDatabase();
//		}
//		Bestellung bestellung = null;
//		ArrayList<Bestellung> alleBestellungen = new ArrayList<>();
//		
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT BestellNr, Bestellart, Bestellwert, Mitarbeiter, Datum FROM bestellungen");
//			
//			while (rs.next()) {
//				Integer bestellNr = rs.getInt("BestellNr");
//				String bestellArt = rs.getString("Bestellart");
//				Double bestellwert = Double.parseDouble(rs.getString("Bestellwert"));
//				String mitarbeiter = rs.getString("Mitarbeiter");
//				String datum = rs.getString("Datum");
//				alleBestellungen.add(new Bestellung(bestellNr, bestellArt, bestellwert, mitarbeiter, datum));
//			}
//			System.out.println(alleBestellungen);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return alleBestellungen;
//	}

	//Connection methods der Nutzer
	
	public boolean checkForLogin(String username, String password) {
		getConnectionToDatabase();
		boolean status = false;
		try {
			Statement stmt2 = conn.createStatement();
			ResultSet rs1 = stmt2.executeQuery("SELECT * FROM nutzer WHERE nutzername='"+username+"' AND nutzerpwd='"+password+"'");
			
			while(rs1.next()) {
				status = true;
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
	
	//Getters und Setters

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public static String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public static String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
