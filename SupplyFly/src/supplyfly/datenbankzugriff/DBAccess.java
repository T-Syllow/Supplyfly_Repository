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
	
	public static void getConnectionToDatabase() {
		System.out.println("Die Datenbank wird versucht abzurufen..");
		
		/*
		 * Die folgenden Konstanten kÃ¶nnten auch in ein Property-File
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
	public static ArrayList<Bestellung> getAlleBestellung() {	//ruft alle Bestellungen aus Datebank ab und speichert sie in ArrayList
		if(conn == null) {
			getConnectionToDatabase();
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
	
	//Diese Methode fügt alle produkte in unsere Tabelle hinzu.
	public static void getAlleProdukte(DefaultTableModel m) throws Exception{
		try {
			getConnectionToDatabase();
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
			System.out.println("Produkte erfolgreich der Tabelle hinzugefügt");
		}
	}
	

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
	
	//Diese Methode fügt ein Lieferanten in unsere Datenbank hinzu.
	public void insertLieferantInDatabase(int lieferantennr, String bezeichnung, String ansprechpartner, String strasse, int hausnummer, int PLZ, String ort) throws Exception{
		try{
			getConnectionToDatabase();
//			PreparedStatement posted = conn.prepareStatement("INSERT INTO lieferant (LieferantenNr, Lieferantenbezeichnung, Ansprechpartner, Strasse, Hausnummer, PLZ, Ort) "
//					+ "VALUES ('"+l.getIdNr()+"', '"+l.getName()+"', '"+l.getAnsprechpartner()+"', '"+l.getStrasse()+"','"+l.getHausnummer()+"' ,'"+l.getPLZ()+"','"+l.getOrt()+"')");
//			
			PreparedStatement posted = conn.prepareStatement("INSERT INTO lieferant (LieferantenNr, Lieferantenbezeichnung, Ansprechpartner, Strasse, Hausnummer, PLZ, Ort)"
					+ " VALUES('"+lieferantennr+"','"+bezeichnung+"','"+ansprechpartner+"','"+strasse+"','"+hausnummer+"','"+PLZ+"','"+ort+"')");
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Lieferant' has been added.");
		};
	}
	//Diese Methode fügt ein Produkt in unsere Datenbank hinzu.
	public void insertProduktInDatabase(int artikelnr, String bezeichnung, int mindestbestand, int menge, String produktspezifikation) throws Exception{
		try{
			getConnectionToDatabase();
//			PreparedStatement posted = conn.prepareStatement("INSERT INTO produkt (ProduktID, Produktbezeichnung, Mindestbestand, Menge) "
//					+ "VALUES ('"+p.getArtikelNr()+"', '"+p.getBezeichnung()+"', '"+p.getMindestbestand()+"' , '"+p.getMenge()+"')");
			
			PreparedStatement posted = conn.prepareStatement("INSERT INTO produkt (ProduktID, Produktbezeichnung, Mindestbestand, Menge, Produktspezifikation) "
					+ "VALUES ('"+artikelnr+"', '"+bezeichnung+"', '"+mindestbestand+"' , '"+menge+"','"+produktspezifikation+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Produkt' has been added.");
		};
	}
	
	public void deleteLieferantInDatabase(int lieferantenId) throws Exception{
		try{
			getConnectionToDatabase();
			
			PreparedStatement posted = conn.prepareStatement("DELETE FROM lieferant WHERE (LieferantenNr = '"+lieferantenId+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Completed, 'Lieferant' with ID: " +lieferantenId+ " has been deleted.");
		};
	}
	
	//Löscht ein ausgewähltes Produkt aus der Datenbank.
	public void deleteProduktInDatabase(int artikelNr) throws Exception{
		try{
			getConnectionToDatabase();
			
			PreparedStatement posted = conn.prepareStatement("DELETE FROM produkt WHERE ( ProduktID = '"+artikelNr+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Completed, 'Produkt' with ID: " +artikelNr+ " has been deleted.");
		};
	}
	
	//Bearbeitet dem ausgewählten Lieferanten in der Datenbank.
	public void lieferantBearbeiten(int lieferantennr, String bezeichnung, String ansprechpartner, String strasse, int hausnummer, int PLZ, String ort) throws Exception{
		try{
			getConnectionToDatabase();

			PreparedStatement posted = conn.prepareStatement("UPDATE lieferant SET Lieferantenbezeichnung = '"+bezeichnung+"', Ansprechpartner = '"+ansprechpartner+"'"
					+ ", Strasse = '"+strasse+"', Hausnummer = '"+hausnummer+"', PLZ = '"+PLZ+"', Ort = '"+ort+"' WHERE (LieferantenNr = '"+lieferantennr+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Lieferant' with the ID: " +lieferantennr+ " has been updated.");
		};
	}
	
	//Bearbeitet das ausgewählte Produkt in der Datenbank. 
	public void produktBearbeiten(int artikelnr, String bezeichnung, int mindestbestand, int menge, String produktspezifikation) throws Exception{
		try{
			getConnectionToDatabase();

			PreparedStatement posted = conn.prepareStatement("UPDATE produkt SET Produktbezeichnung = '"+bezeichnung+"', Mindestbestand = '"+mindestbestand+"', Menge = '"+menge+"', Produktspezifikation = '"+produktspezifikation+"' WHERE (ProduktID = '"+artikelnr+"')");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Complete, 'Produkt' with the ArtikelNr.: " +artikelnr+" has been updated.");
		};
	}

	public String getProduktInfo(int proID, String info) {
//		getConnectionToDatabase();
		
		
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
