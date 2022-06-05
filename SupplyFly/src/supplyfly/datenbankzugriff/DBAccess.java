package supplyfly.datenbankzugriff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import supplyfly.objects.Bestellung;
import supplyfly.objects.Lieferant;
import supplyfly.objects.Produkte;

public class DBAccess {
	
	private static Connection conn = null;
	private static String url = "jdbc:mysql://3.69.96.96:3306/";		//hier steht aktuell der HOSTNAME + PORT
	private static String dbName = "db2";
	private static String driver = "com.mysql.cj.jdbc.Driver";	
	private static String userName = "db2";
	private static String password = "!db2.java22?";	//Das Passwort sollte eigentlich in args[0] gespeichert werden.
	
	public DBAccess() {
		try {
			if(conn == null) { 	// Es soll nur eine Verbindung hergestellt werden, wenn noch keine vorhanden ist.
			Class.forName(supplyfly.datenbankzugriff.DBAccess.getDriver());
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			}
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
	public static ArrayList<Bestellung> getAlleBestellung() {	//ruft alle Bestellungen aus Datebank ab und speichert sie in ArrayList
		if(conn == null) {
//			getConnectionToDatabase();
		}
		Bestellung bestellung = null;
		ArrayList<Bestellung> alleBestellungen = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT BestellNr, Bestellart, Bestellwert, Mitarbeiter, Datum FROM bestellung");
			
			while (rs.next()) {
				Integer bestellNr = rs.getInt("BestellNr");
				String bestellArt = rs.getString("Bestellart");
				Double bestellwert = Double.parseDouble(rs.getString("Bestellwert"));
				String mitarbeiter = rs.getString("Mitarbeiter");
				String datum = rs.getString("Datum");
				alleBestellungen.add(new Bestellung(bestellNr, bestellArt, bestellwert, mitarbeiter, datum));
			}
			System.out.println(alleBestellungen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alleBestellungen;
	}
	
	//Diese Methode f�gt alle produkte in unsere Tabelle hinzu.
	public static void getAlleProdukte(DefaultTableModel m) throws Exception{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ProduktID, Produktbezeichnung FROM produkt");
			
			while(rs.next()) {
				Integer produktID = rs.getInt("ProduktID");
				String produktbezeichnung = rs.getString("Produktbezeichnung");
				m.addRow(new Object[] {produktID, produktbezeichnung});
			}
		}catch(Exception e){
			System.out.println(e);
		}
		finally {
			System.out.println("Produkte erfolgreich der Tabelle hinzugef�gt");
		}
	}
	
	//Diese Methode f�gt alle Lieferanten in unsere Tabelle hinzu.
	public static void getAlleLieferanten(DefaultTableModel m) throws Exception{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LieferantenNr, Lieferantenbezeichnung FROM lieferant");
			
			while(rs.next()) {
				Integer lieferantenNr = rs.getInt("LieferantenNr");
				String lieferantenBezeichnung = rs.getString("Lieferantenbezeichnung");
				m.addRow(new Object[] {lieferantenNr, lieferantenBezeichnung});
			}
		}catch(Exception e){
			System.out.println(e);
		}
		finally {
			System.out.println("Lieferanten erfolgreich der Tabelle hinzugef�gt");
		}
	}
	

	//Connection methods der Nutzer
	
	public boolean checkForLogin(String username, String password) {
//		getConnectionToDatabase();
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
	
	//Diese Methode f�gt ein Lieferanten in unsere Datenbank hinzu.
	public void insertLieferantInDatabase(Lieferant l) throws Exception{
		try{
			PreparedStatement posted = conn.prepareStatement("INSERT INTO lieferant (LieferantenNr, Lieferantenbezeichnung, Ansprechpartner, Strasse, Hausnummer, PLZ, Ort) "
					+ "VALUES ('"+l.getIdNr()+"', '"+l.getName()+"', '"+l.getAnsprechpartner()+"', '"+l.getStrasse()+"','"+l.getHausnummer()+"' ,'"+l.getPLZ()+"','"+l.getOrt()+"')");
		
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Lieferant' has been added.");
		};
	}
	//Diese Methode f�gt ein Produkt in unsere Datenbank hinzu.
	public static void insertProduktInDatabase(Produkte p) throws Exception{
		try{
			PreparedStatement posted = conn.prepareStatement("INSERT INTO produkt (ProduktID, Produktbezeichnung, Mindestbestand, Menge, Produktspezifikation) "
					+ "VALUES ('"+p.getArtikelNr()+"', '"+p.getBezeichnung()+"', '"+p.getMindestbestand()+"' , '"+p.getMenge()+"','"+p.getProduktspezifikation()+"')");
			
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Produkt' has been added.");
		};
	}
	
	//L�scht Lieferanten von der Tabelle.
	public static void deleteLieferantInDatabase(Lieferant l) throws Exception{
		try{
			
			PreparedStatement posted = conn.prepareStatement("DELETE FROM lieferant WHERE (LieferantenNr = '"+l.getIdNr()+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Completed, 'Lieferant' with ID: " +l.getIdNr()+ " has been deleted.");
		};
	}
	
	//L�scht ein ausgew�hltes Produkt aus der Datenbank.
	public static void deleteProduktInDatabase(Produkte p) throws Exception{
		try{
			
			PreparedStatement posted = conn.prepareStatement("DELETE FROM produkt WHERE ( ProduktID = '"+p.getArtikelNr()+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Completed, 'Produkt' with ID: " +p.getArtikelNr()+ " has been deleted.");
		};
	}
	
	//Bearbeitet dem ausgew�hlten Lieferanten in der Datenbank.
	public static void lieferantBearbeiten(Lieferant l) throws Exception{
		try{

			PreparedStatement posted = conn.prepareStatement("UPDATE lieferant SET Lieferantenbezeichnung = '"+l.getName()+"', Ansprechpartner = '"+l.getAnsprechpartner()+"'"
					+ ", Strasse = '"+l.getStrasse()+"', Hausnummer = '"+l.getHausnummer()+"', PLZ = '"+l.getPLZ()+"', Ort = '"+l.getOrt()+"' WHERE (LieferantenNr = '"+l.getIdNr()+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Lieferant' with the ID: " +l.getIdNr()+ " has been updated.");
		};
	}
	
	//Bearbeitet das ausgew�hlte Produkt in der Datenbank. 
	public static void produktBearbeiten(Produkte p) throws Exception{
		try{

			PreparedStatement posted = conn.prepareStatement("UPDATE produkt SET Produktbezeichnung = '"+p.getBezeichnung()+"', Mindestbestand = '"+p.getMindestbestand()+"', Menge = '"+p.getMenge()+"', Produktspezifikation = '"+p.getProduktspezifikation()+"' WHERE (ProduktID = '"+p.getArtikelNr()+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Produkt' with the ArtikelNr.: " +p.getArtikelNr()+" has been updated.");
		};
	}

	public static String getProduktInfo(int proID, String info) {
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT "+info+" FROM produkt WHERE ProduktID='"+proID+"'");
			
			if(rs.next()) {
				if(info.equals("Standardlieferant")) {
					System.out.println(rs.getInt(info));
					return String.valueOf(rs.getInt(info));
				}
				return rs.getString(info);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println(info+" wurde weitergegeben");
		}
		return "Error";

	}
	
}
