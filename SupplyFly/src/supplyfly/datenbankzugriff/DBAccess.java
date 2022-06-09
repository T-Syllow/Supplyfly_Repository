package supplyfly.datenbankzugriff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;
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
																
	public static void getAlleBestellung(DefaultTableModel m) throws Exception{	// Diese Methode fuegt alle Bestellung unserer Tabelle hinzu.
		if(conn == null) {
//			getConnectionToDatabase();
		}
		
		try {
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT BestellNr, Bestellart, Bestellwert, Mitarbeiter, Datum, Status, Produkte FROM bestellung");
			
			
			
			
			while (rs1.next()) {
				Integer bestellNr = rs1.getInt("BestellNr");
				String bestellArt = rs1.getString("Bestellart");
				String bestellwert = getProduktpreis(rs1.getString("Produkte"));
				String mitarbeiter = rs1.getString("Mitarbeiter");
				String datum = rs1.getString("Datum");
				String status = rs1.getString("Status");
				String produkte = rs1.getString("Produkte");
				ArrayList<String> produktNamen = compareProductID(produkte);
				
				
				m.addRow(new Object[] {bestellNr, bestellArt, bestellwert, mitarbeiter, datum, status, produktNamen});
			}
			System.out.println("Bestellungen erfolgreich der Tabelle hinzugef\u00FCgt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * Vergleicht die ProduktID's aus den zwei Datenbanken 'produkt' und 'bestellung'. 
	 * Sie bekommt die ProduktIDs einer Bestellung und gibt deren Namen zurueck.
	 */
	public static ArrayList<String> compareProductID(String produktID) {	
		// produktID aus 'bestellung' muss zuerst zerlegt werden. Sie liegt in der Form '1001,1003' vor & speichert die ProduktIDs in ein Array.
		String [] einzelneProduktIDs = produktID.split(",");	
		
		// die Zahl der zutreffenden ProduktIDs ist maximal der Anzahl der angegebenen ProduktIDs - logischerweise.
		String [] treffendeProduktIDs = new String [einzelneProduktIDs.length];
		
		ArrayList<String> produktNamen = new ArrayList<>();
		
		try {
		Statement stmt2 = conn.createStatement();	//WICHTIG fuer ResultSet rs2 --> Zeile 95.
		Statement stmt3 = conn.createStatement();	//WICHTIG fuer ResultSet rs3 --> Zeile 109.
		ResultSet rs2 = stmt2.executeQuery("SELECT ProduktID, Produktbezeichnung FROM produkt");
		
		//Prüfe für jeden String von 'rs2' die ProduktID aus Datenbank 'produkt' mit dem Parameter der Methode 'produktID' aus 'bestellung'.
		int i = 0; //Indexzähler für treffendeProduktIDs --> siehe Array in Zeile 87.
		while(rs2.next()) {
			String produktIDFromProdukteDatabase = rs2.getString("ProduktID"); //ProduktID aus 'Produkt' DB.
			
			//Prüfe für jedes Element in 'einzelneProduktIDs' --> siehe Array in Zeile 84. Beachte length von 'einzelneProduktIDs'!
			for (int j = 0; j < einzelneProduktIDs.length; j++) {
				String ProduktIDAusBestellung = einzelneProduktIDs[j];	//für die erste Iteration 1001, dann 1002 usw.
				if(ProduktIDAusBestellung.equals(produktIDFromProdukteDatabase)) {	//Wenn die ProduktID von 'bestellung' mit ProduktID von 'produkt' ubereinstimmt..
					
					//füge die ProduktID in Array treffendeProduktIDs zum WEITERVERARBEITEN ein..
					treffendeProduktIDs[i] = ProduktIDAusBestellung;	
					ResultSet rs3 = stmt3.executeQuery("SELECT Produktbezeichnung FROM produkt WHERE ProduktID='"+treffendeProduktIDs[i]+"'");
					while (rs3.next()) {
						produktNamen.add(rs3.getString("Produktbezeichnung"));	//fuegt RICHTIGEN ProduktNamen der ArrayList hinzu!
					}
					i++;
				}
			}
		}
	
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return produktNamen;
	}
	
	/*
	 * Diese Methode wird initial zur Erstellung einer Bestellungsinstanz erstellt. Genauer für das Attribut Produkte einer Bestellung in
	 * Bestellung.java!
	 * Sie bekommt den String produktIds aus der JTABLE mit und verwandelt ihn in eine ArrayList aus Integern.
	 */
	public static ArrayList<Integer> getProduktIdsAsIntegerArrayList(String produktIdAlsString) {
		String [] temp = produktIdAlsString.split(",");
		
		ArrayList<Integer> arr = new ArrayList<>(); 
		for (String string : temp) {
			arr.add(Integer.valueOf(string));
		}
		return arr;
	}
	
	/*
	 * Diese Funktion berechnet den Produktpreis bzw. Gesamtpreis einer Bestellung.
	 * Sie funktioniert nur, solange in der Datenbank lief_produkt jede PRODUKTID jeweils EINMAL vorkommt!
	 */
	public static String getProduktpreis(String produktID) {	
		String [] einzelneProduktIDs = produktID.split(",");
		
		String [] treffendeProduktIDs = new String [einzelneProduktIDs.length];
		
		ArrayList<String> produktPreise = new ArrayList<>();
		Double gesamtpreis = 0.0;
		String gesamtPreisAlsString = "[EMPTY]";
		try {
			Statement stmt1 = conn.createStatement();	//WICHTIG fuer ResultSet rs2 --> Zeile .
			Statement stmt2 = conn.createStatement();	//WICHTIG fuer ResultSet rs3 --> Zeile .
			ResultSet rs1 = stmt1.executeQuery("SELECT ProduktID, Preis FROM lief_produkt");
			
			//Prüfe für jeden String von 'rs1' die ProduktID aus Datenbank 'produkt' mit dem Parameter der Methode 'produktID' aus 'bestellung'.
			int i = 0; //Indexzähler für treffendeProduktIDs --> siehe Array treffendeProduktIDs.
			while(rs1.next()) {
				String produktIDFromProdukteDatabase = rs1.getString("ProduktID"); //ProduktID aus 'lief_produkt' DB.
				
				//Prüfe für jedes Element in 'einzelneProduktIDs' --> Beachte length von 'einzelneProduktIDs'!
				for (int j = 0; j < einzelneProduktIDs.length; j++) {
					String ProduktIDAusBestellung = einzelneProduktIDs[j];	//für die erste Iteration 1001, dann 1002 usw.
					if(ProduktIDAusBestellung.equals(produktIDFromProdukteDatabase)) {	//Wenn die ProduktID von 'bestellung' mit ProduktID von 'lief_produkt' ubereinstimmt..
						
						//füge die ProduktID in Array treffendeProduktIDs zum WEITERVERARBEITEN ein..
						treffendeProduktIDs[i] = ProduktIDAusBestellung;	
						ResultSet rs3 = stmt2.executeQuery("SELECT Preis FROM lief_produkt WHERE ProduktID='"+treffendeProduktIDs[i]+"'");
						while (rs3.next()) {
							produktPreise.add(rs3.getString("Preis"));	//fuegt korrektes Attribut Preis der ArrayList 'produktPreise' hinzu!
						}
						i++;
					}
				}
			}
			
			// Addiert die Produktpreise jeder Bestellung zum Gesamtwert der Bestellung
			for (String preisAlsString : produktPreise) {
				gesamtpreis += Double.parseDouble(preisAlsString);
			}
			
			//hier wird auf 2 Nachkommastellen gerundet.
			gesamtpreis = (gesamtpreis*100)/100;
			
			//konvertiert den gesamtpreis wieder in einen String namens gesamtPreisAlsString
			gesamtPreisAlsString = String.valueOf(gesamtpreis);
		
			} catch (Exception e) {
				System.out.println(e);
			}
		return gesamtPreisAlsString;
	}
//	/*
//	 * Diese Funktion berechnet den Produktpreis bzw. Gesamtpreis einer Bestellung.
//	 * Sie soll IMMER funktionieren, auch wenn in der Datenbank lief_produkt PRODUKTID�s MEHRMALS vorkommt!
//	 */
//	public static String getProduktpreis2(String produktID) {	
//		
//		ArrayList<String> produktPreise = new ArrayList<>();
//		Double gesamtpreis = 0.0;
//		String gesamtPreisAlsString = "[EMPTY]";
//		
//			
//			// Addiert die Produktpreise jeder Bestellung zum Gesamtwert der Bestellung
//			for (String preisAlsString : produktPreise) {
//				gesamtpreis += Double.parseDouble(preisAlsString);
//			}
//			
//			//hier wird auf 2 Nachkommastellen gerundet.
//			gesamtpreis = (gesamtpreis*100)/100;
//			
//			//konvertiert den gesamtpreis wieder in einen String namens gesamtPreisAlsString
//			gesamtPreisAlsString = String.valueOf(gesamtpreis);
//		return gesamtPreisAlsString;
//	}
	
	/*
	 * Diese Methode uebertraegt alle Attribute einer ausgewählten Bestellung anhand der bestellNr in eine beliebige JTable ein.
	 */
	public static void getSelectedBestellung(String bestellNr, DefaultTableModel m) {
		try {
		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("SELECT BestellNr, Bestellart, Bestellwert, Mitarbeiter, Datum, Status, Produkte FROM bestellung WHERE BestellNr='"+bestellNr+"'");
		
		
		while(rs1.next()) {
		String bestellNrData = bestellNr;
		String bestellArt = rs1.getString("Bestellart");
		String bestellwert = getProduktpreis(rs1.getString("Produkte"));
		String mitarbeiter = rs1.getString("Mitarbeiter");
		String datum = rs1.getString("Datum");
		String status = rs1.getString("Status");
		String produktNummern = rs1.getString("Produkte");
		
		m.addRow(new Object[] {bestellNrData, bestellArt, bestellwert, mitarbeiter, datum, status, produktNummern});
		}
		
		} 
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//Diese Methode f�gt alle produkte in unsere Tabelle hinzu.
	public static void getAlleProdukte(DefaultTableModel m) throws Exception{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT db2.produkt.ProduktID, db2.produkt.Produktbezeichnung, db2.lief_produkt.preis, db2.lieferant.Lieferantenbezeichnung\n"
					+ "FROM ((db2.produkt INNER JOIN db2.lief_produkt ON db2.produkt.ProduktID = db2.lief_produkt.ProduktID)\n"
					+ "INNER JOIN db2.lieferant ON db2.lief_produkt.LieferantenNr = db2.lieferant.LieferantenNr)\n"
					+ "WHERE db2.produkt.Standardlieferant = db2.lief_produkt.LieferantenNr\n");
			
			while(rs.next()) {
				Integer produktID = rs.getInt("produkt.ProduktID");
				String produktbezeichnung = rs.getString("produkt.Produktbezeichnung");
				Double preis = rs.getDouble("lief_produkt.Preis");
				String lieferantenbezeichnung = rs.getString("lieferant.Lieferantenbezeichnung");
				m.addRow(new Object[] {produktID, produktbezeichnung, preis, lieferantenbezeichnung});
			}
		}catch(Exception e){
			System.out.println(e);
		}
		finally {
			System.out.println("Produkte erfolgreich der Tabelle hinzugef\u00FCgt");
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
			System.out.println("Lieferanten erfolgreich der Tabelle hinzugef\u00FCgt");
		}catch(Exception e){
			System.out.println(e);
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
	//Diese Methode holt Produktinformationen aus der DB 'bestellung'.
	public static String getBestellInfo(String bestellNr, String info) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT "+info+" FROM bestellung WHERE BestellNr='"+bestellNr+"'");
			String zielwert = "EMPTY";
			while(rs1.next()) {
				zielwert = rs1.getString(info);
			}
			
			return zielwert;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "ERROR";
	}
	//Diese Methode soll eine Bestellung in unserer Datenbank 'bestellung' ueberschreiben.
	// Noch buggy
	/*
	 * HIER BRAUCHEN WIR: ein 
	 */
	public static void ueberschreibeDatenInDatabase(Bestellung b, DefaultTableModel m) {
		try{
			Statement stmt = conn.createStatement();
			
			Integer rs1 = stmt.executeUpdate("UPDATE bestellung SET BestellNr='"+b.getBestellnummer()+"',Bestellart='"+b.getBestellart()+"',Bestellwert"
					+ "='"+b.getBestellwert()+"',Mitarbeiter='"+b.getName()+"',Datum='"+b.getDatum()+"',Status='"+b.getStatus()+"',Produkte='"
					+b.convertArrayListToString(b.getProdukte())+"' WHERE BestellNr='"+b.getBestellnummer()+"'");
			
			//Das soll gemacht werden: Der Name soll von "Jordan" -> "Gordon" geändert werden & dann in die DB 'bestellung' ueberschrieben werden.
			//							Die Bestellung 2002 soll also vom Mitarbeiter ueberarbeitet werden..
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
