package supplyfly.datenbankzugriff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import supplyfly.objects.Bestellung;
import supplyfly.objects.Einkaeufer;
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
			ResultSet rs1 = stmt1.executeQuery("SELECT db2.bestellung.BestellNr, db2.bestellung.Bestellart, db2.bestellung_produkt.zwischenBestellwert,"
					+ " db2.bestellung.Mitarbeiter, db2.bestellung.Datum, db2.bestellung.Status,"
					+ " db2.bestellung_produkt.produktID, db2.bestellung_produkt.Menge\n"
					+ "FROM db2.bestellung INNER JOIN db2.bestellung_produkt ON db2.bestellung.BestellNr = db2.bestellung_produkt.BestellNr\n"
					+ "WHERE db2.bestellung.BestellNr = db2.bestellung_produkt.BestellNr");
			
			
			
			
			while (rs1.next()) {
				Integer bestellNr = rs1.getInt("bestellung.BestellNr");
				String bestellArt = rs1.getString("bestellung.Bestellart");
				//Bestellwert muss beim Anlegen einer Bestellung errechnet und in 'db2.bestellung.bestellwert' gespeichert werden.
				String bestellwert = rs1.getString("bestellung_produkt.zwischenBestellwert"); // getBestellwert(rs1.getString("bestellung_produkt.Menge"), rs1.getString("bestellung_produkt"));
				String mitarbeiter = rs1.getString("bestellung.Mitarbeiter");
				String datum = rs1.getString("bestellung.Datum");
				String status = rs1.getString("bestellung.Status");
				String produkt = rs1.getString("bestellung_produkt.ProduktID");
				String menge = rs1.getString("bestellung_produkt.Menge");
				
				
				m.addRow(new Object[] {bestellNr, bestellArt, bestellwert, mitarbeiter, datum, status, produkt, menge});
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
	 * Diese Funktion berechnet den Produktpreis eines ProduktID.
	 * 
	 */
	public static Double getProduktpreis(String produktID, String lieferantenID) {	
		Double preis = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT db2.lief_produkt.Preis\n"
					+ "FROM db2.lief_produkt\n"
					+ "WHERE db2.lief_produkt.ProduktID = '"+produktID+"' AND db2.lief_produkt.LieferantenNr = '"+lieferantenID+"'");
			
			while(rs1.next()) {
				preis = Double.valueOf(rs1.getString("lief_produkt.Preis"));
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return preis;
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
	public static void getSelectedBestellung(String bestellNr, String produkt, DefaultTableModel m) {
		try {
		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("SELECT db2.bestellung.BestellNr, db2.bestellung.Bestellart, db2.bestellung.Bestellwert, db2.bestellung.Mitarbeiter,"
				+ " db2.bestellung.Datum, db2.bestellung.Status, db2.bestellung_produkt.ProduktID, db2.bestellung_produkt.Menge, db2.bestellung.LieferantenNr FROM bestellung \n"
				+ " INNER JOIN db2.bestellung_produkt ON db2.bestellung_produkt.BestellNr = db2.bestellung.BestellNr \n"
				+ " WHERE db2.bestellung.BestellNr='"+bestellNr+"' AND db2.bestellung_produkt.ProduktID ='"+produkt+"'");
		
		
		while(rs1.next()) {
		String bestellNrData = bestellNr;
		String bestellArt = rs1.getString("bestellung.Bestellart");
		String bestellwert = rs1.getString("bestellung.bestellwert");
		String mitarbeiter = rs1.getString("bestellung.mitarbeiter");
		String datum = rs1.getString("bestellung.datum");
		String status = rs1.getString("bestellung.status");
		String produktID = rs1.getString("bestellung_produkt.ProduktID");
		String menge = rs1.getString("bestellung_produkt.Menge");
		String lieferantenNr = rs1.getString("bestellung.LieferantenNR");
		
		m.addRow(new Object[] {bestellNrData, bestellArt, bestellwert, mitarbeiter, datum, status, produktID, menge, lieferantenNr});
		}
		
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//(Philipp) Diese Methode f�gt alle produkte in unsere Tabelle hinzu.
	public static void getAlleProdukte(DefaultTableModel m) throws Exception{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT db2.produkt.ProduktID, db2.produkt.Produktbezeichnung, db2.lief_produkt.Preis, db2.lieferant.Lieferantenbezeichnung\n"
					+ "FROM ((db2.produkt LEFT JOIN db2.lief_produkt ON db2.produkt.ProduktID = db2.lief_produkt.ProduktID)\n"
					+ "LEFT JOIN db2.lieferant ON db2.lief_produkt.LieferantenNr = db2.lieferant.LieferantenNr)\n"
					+ "WHERE db2.produkt.WirdAngezeigt = 1"); // AND db2.produkt.Standardlieferant = db2.lief_produkt.LieferantenNr");

			
			while(rs.next()) {
				Integer produktID = rs.getInt("produkt.ProduktID");
				String produktbezeichnung = rs.getString("produkt.Produktbezeichnung");
				String preis = rs.getString("lief_produkt.Preis");
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
			ResultSet rs = stmt.executeQuery("SELECT LieferantenNr, Lieferantenbezeichnung, Ansprechpartner, Strasse, Hausnummer, PLZ, Ort FROM lieferant WHERE WirdAngezeigt = '"+1+"'");
			
			while(rs.next()) {
				Integer lieferantenNr = rs.getInt("LieferantenNr");
				String lieferantenBezeichnung = rs.getString("Lieferantenbezeichnung");
				String ansprechpartner = rs.getString("Ansprechpartner");
				String strasse = rs.getString("Strasse");
				Integer hausnummer = rs.getInt("Hausnummer");
				Integer plz = rs.getInt("PLZ");
				String ort = rs.getString("Ort");
				
				m.addRow(new Object[] {lieferantenNr, lieferantenBezeichnung, ansprechpartner, strasse, hausnummer, plz, ort});
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

	public static Connection getConn() {
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
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			JOptionPane.showMessageDialog(null, "*FEHLER*\nDie LieferantenNr ist bereits vorhanden!\n\nWaehlen Sie bitte eine andere Artikelnummer.");
		} catch(Exception e){
			System.out.println(e);
		};
	}
	
	//Diese Methode f�gt ein Produkt in unsere Datenbank hinzu.
	public static void insertProduktInDatabase(Produkte p) throws Exception{
		try{
			PreparedStatement posted = conn.prepareStatement("INSERT INTO produkt (ProduktID, Produktbezeichnung, Mindestbestand, Menge, Produktspezifikation, Standardlieferant) "
					+ "VALUES ('"+p.getArtikelNr()+"', '"+p.getBezeichnung()+"', '"+p.getMindestbestand()+"' , '"+p.getMenge()+"','"+p.getProduktspezifikation()+"', '"+p.getLieferantenNr()+"')");
			
			
			posted.executeUpdate();
			JOptionPane.showMessageDialog(null, "Produkt wurde erfolgreich hinzugef�gt!");
			System.out.println("Complete, 'Produkt' has been added.");
		} catch (SQLIntegrityConstraintViolationException sqle) {
			JOptionPane.showMessageDialog(null, "*FEHLER*\nDie Artikelnummer ist bereits vorhanden!\n\nWaehlen Sie bitte eine andere Artikelnummer.");
		} catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	//L�scht Lieferanten von der Tabelle.
	public static void deleteLieferantInDatabase(int lieferantenNr) throws Exception{
		try{
			
			PreparedStatement posted = conn.prepareStatement("UPDATE lieferant SET WirdAngezeigt = 0 WHERE LieferantenNr = '"+lieferantenNr+"'");
			
			posted.executeUpdate();
		}catch(Exception e){System.out.println(e);
	}
		finally {
			System.out.println("Completed, 'Lieferant' with ID: " +lieferantenNr+ " has been deleted.");
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
	
	public static Double getBestellwerteFromDB (Bestellung b) {
		Double gesamtBestellWert = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT zwischenBestellwert FROM bestellung_produkt WHERE BestellNr='"+b.getBestellnummer()+"'");
			
			while(rs.next()) {
				gesamtBestellWert += rs.getDouble("zwischenBestellwert");
			}
		} catch (Exception e) {
			System.out.println("gesamtBestellwert= "+gesamtBestellWert);
			e.printStackTrace();
		}
		return gesamtBestellWert;
	}

	
	public static void ueberschreibeDatenInDatabase(Bestellung b, DefaultTableModel m) {
		try{
			Statement stmt = conn.createStatement();
			
//			Integer rs1 = stmt.executeUpdate("UPDATE db2.bestellung,db2.bestellung_produkt SET db2.bestellung.BestellNr='"+b.getBestellnummer()+"',db2.bestellung.Bestellart='"+b.getBestellart()+""
//					+ "',db2.bestellung_produkt.zwischenBestellwert='"+b.getBestellwert()+"',db2.bestellung.Mitarbeiter='"+b.getName()+"',db2.bestellung.Datum='"+b.getDatum()+
//					"',db2.bestellung.Status='"+b.getStatus()+"',db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"', db2.bestellung_produkt.Menge='"+b.getMenge()+"'"
//					+" WHERE db2.bestellung.BestellNr='"+b.getBestellnummer()+"' AND db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"' AND db2.bestellung.lieferantenNR='"+String.valueOf(b.getLieferantenNr())+"'");
			
			Integer rs1 = stmt.executeUpdate("UPDATE bestellung SET Bestellart='"+b.getBestellart()+"', Mitarbeiter='"+b.getName()+"', Datum='"+b.getDatum()+"', bestellung.Status='"+b.getStatus()+"'"
					+ "WHERE (BestellNr='"+b.getBestellnummer()+"' AND LieferantenNr='"+b.getLieferantenNr()+"')");	//AND db2.bestellung.lieferantenNR='"+String.valueOf(b.getLieferantenNr())+"'"
			
			Integer rs2 = stmt.executeUpdate("UPDATE db2.bestellung_produkt SET db2.bestellung_produkt.zwischenBestellwert='"+b.getBestellwert()+"'"
					+ ",db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"', db2.bestellung_produkt.Menge='"+b.getMenge()+"'"
					+" WHERE (db2.bestellung_produkt.BestellNr='"+b.getBestellnummer()+"' AND db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"')");	//AND db2.bestellung.lieferantenNR='"+String.valueOf(b.getLieferantenNr())+"'"
			
			Integer rs3 = stmt.executeUpdate("UPDATE bestellung SET Bestellwert='"+getBestellwerteFromDB(b)+"' "
					+ "WHERE BestellNr='"+b.getBestellnummer()+"'");
			
//			Integer rs2 = stmt.executeUpdate("UPDATE db2.bestellung,db2.bestellung_produkt SET db2.bestellung_produkt.BestellNr='"+b.getBestellnummer()+"',db2.bestellung.Bestellart='"+b.getBestellart()+""
//					+ "',db2.bestellung_produkt.zwischenBestellwert='"+b.getBestellwert()+"',db2.bestellung.Mitarbeiter='"+b.getName()+"',db2.bestellung.Datum='"+b.getDatum()+
//					"',db2.bestellung.Status='"+b.getStatus()+"',db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"', db2.bestellung_produkt.Menge='"+b.getMenge()+"'"
//					+" WHERE (db2.bestellung_produkt.BestellNr='"+b.getBestellnummer()+"' AND db2.bestellung_produkt.ProduktID='"+b.getProdukt()+"')");	//AND db2.bestellung.lieferantenNR='"+String.valueOf(b.getLieferantenNr())+"'"
//			
			
			//Das soll gemacht werden: Der Name soll von "Jordan" -> "Gordon" geändert werden & dann in die DB 'bestellung' ueberschrieben werden.
			//							Die Bestellung 2002 soll also vom Mitarbeiter ueberarbeitet werden..
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Diese Methode soll eine NEUE BESTELLUNG in der Datenbank "bestellung" HINZUFUEGEN.
	public static void speichereBestellungInDatabase(Bestellung b, DefaultTableModel m) {
		
	}
	
	
	
	
	
	
	
	//--------------------------Philipps Zeug (Versuch ohne Objekte, eventuell einfacher--------------------------------------------------------------------------
	//(Philipp) Methode, die ein StringArray der Lieferanten ausgibt (um sie sp�ter in einer ComboBox auszuw�hlen
		public static ArrayList<String> getLieferanten(){
			ArrayList<String> lieferantenliste = new ArrayList<>();	
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT LieferantenNr, Lieferantenbezeichnung FROM lieferant");
							
				while(rs.next()) {
					String lieferantenNr = rs.getString("LieferantenNr");
					String lieferantenBezeichnung = rs.getString("Lieferantenbezeichnung");
					lieferantenliste.add(lieferantenNr + "," + lieferantenBezeichnung); 
				}
				System.out.println("Lieferanten erfolgreich der Liste hinzugef\u00FCgt");
			}catch(Exception e){
				System.out.println(e);
			}
			return lieferantenliste;
		}
		
		//(Philipp) Methode, die aus der DB die h�chste Bestellnummer holt
		public static Integer getAktuelleBestellNr() {
			ArrayList<Integer> nummern = new ArrayList<Integer>();
			Integer maxNummer = 0; 
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT BestellNr FROM db2.bestellung");	
				
				while(rs.next()) {
					Integer nummer = rs.getInt("BestellNr");
					nummern.add(nummer);
				}
				for (int i = 0; i < nummern.size()-1; i++) {
					if(nummern.get(i) < nummern.get(i+1)) {
						maxNummer = nummern.get(i+1);
					}
					else {
						maxNummer = nummern.get(i);
					}
				}
			
			}catch(Exception e){
				System.out.println(e);
			}
			return maxNummer;		
		}
		
		//loescht die ausgewaehlte Bestellung zuerst in DB 'bestellung_produkt' und danach in DB 'bestellung'
		public static void loescheBestellungInDB(Integer bestellNr) {
			try {
				Statement stmt = conn.createStatement();
				Integer rs1 = stmt.executeUpdate("DELETE FROM bestellung_produkt WHERE BestellNr='"+bestellNr+"'");
				
				Integer rs2 = stmt.executeUpdate("DELETE FROM bestellung WHERE BestellNr='"+bestellNr+"'");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		//(Philipp) Legt Bestellung direkt, ohne Objekt in der DB an und speichert gesamtBestellwert in DB 'bestellung'
		public static void legeBestellungInDBan(Integer bestellNr, String bestellArt, String mitarbeiter, String datum, String status, String lieferant, String kommentar, String bestellwert) {
			System.out.println("Bestellung wird angelegt...");
			try{
				PreparedStatement posted = conn.prepareStatement("INSERT INTO bestellung (BestellNr, BestellArt, Bestellwert, Mitarbeiter, Datum, Status, LieferantenNr , Kommentar) "
						+ "VALUES ('"+bestellNr+"', '"+bestellArt+"','"+bestellwert+"', '"+mitarbeiter+"' , '"+datum+"','"+status+"', '"+lieferant+"', '"+kommentar+"' )");
				posted.executeUpdate();
				Statement stmt = conn.createStatement();
				Integer rs = stmt.executeUpdate("UPDATE bestellung SET Bestellwert='"+bestellwert+"' "
						+ "WHERE BestellNr='"+bestellNr+"'");
				
			}catch(Exception e){System.out.println(e);
		}
			finally {
				System.out.println("Complete, 'Bestellung' has been added.");
			};
		}

		//(Philipp) Legt Positionen aus der GUI in der DB an (wird mit Bestellung �ber Fremdschl�ssel) verkn�pft
		public static void legePositionenInDBan(Integer aktuelleBestellNr, String produktID, String menge, String preis) {
			System.out.println("Position wird angelegt...");
			try{
				PreparedStatement posted = conn.prepareStatement("INSERT INTO bestellung_produkt (BestellNr, ProduktID, Menge, zwischenBestellwert) "
						+ "VALUES ('"+aktuelleBestellNr+"', '"+produktID+"', '"+menge+"', '"+preis+"')");
				posted.executeUpdate();
			}catch(Exception e){System.out.println(e);
		}
			finally {
				System.out.println("Complete, 'Position' has been added.");
			};
		}
			
		
		//(Philipp) Gibt ArrayList aller g�ltigen ProduktID�s zur�ck
		public static ArrayList<String> getProduktliste(){
			ArrayList<String> dbProduktliste = new ArrayList<>();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT ProduktID, WirdAngezeigt, Standardlieferant FROM db2.produkt");	
				
				while(rs.next()) {
					String geloeschtJaNein = rs.getString("WirdAngezeigt");
					String lieferant = rs.getString("Standardlieferant");
					if(geloeschtJaNein.equals("1") && !lieferant.equals("")){
						String dbProduktID = rs.getString("ProduktID");
						dbProduktliste.add(dbProduktID);
					}
				}			
			}catch(Exception e){
				System.out.println(e);
			}
			return dbProduktliste;
		}

		//(Philipp) Gibt in einem ResultSet alle Produkte mit Preis zur�ck, die der �bergebene Lieferant liefert
		public static ResultSet wasLiefertLieferantMitPreisUndMenge(String lieferantenNr){
			ResultSet lieferantenMitPreis = null;
			try {
				Statement stmt = conn.createStatement();
				lieferantenMitPreis = stmt.executeQuery("SELECT ProduktID, Preis, Lieferzeit FROM lief_produkt WHERE LieferantenNr='"+lieferantenNr+"'");
			}catch (Exception e) {
				System.out.println(e);
			}
			return lieferantenMitPreis;
		}
		
		//(Philipp) gibt den Preis des Produkts beim Lieferanen als Integer zur�ck
		public static Integer getProduktpreisfuerLieferant(String lieferantenNr, String produkt) {
			Integer preis = null;
				try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT Preis FROM lief_produkt WHERE LieferantenNr='"+lieferantenNr+"' AND ProduktID = '"+produkt+"'");	
					
					while(rs.next()) {
						preis = rs.getInt("Preis");
					}			
				}catch(Exception e){
					System.out.println(e);
				}	
			return preis;
		}
		
		//(Philipp) Produkt l�schen (nicht richtig l�schen, nur "nicht sichtbar" setzten
		public static void loescheProdukt(String id) {
			try {
				Statement stmt = conn.createStatement();
				Integer rs1 = stmt.executeUpdate("UPDATE produkt SET WirdAngezeigt='0' WHERE ProduktID = '"+id+"'");
				
			}catch(Exception e){
				System.out.println(e);
			}	
		}
		
		//(Philipp) txt f�r Bestellung anlegen
				public static boolean erstelleBestellungsTXT(String bestellID) {
					boolean geklappt = false;
					
					try(PrintWriter writer = new PrintWriter(new File("newBestellung.txt"))){

						try {
							Statement stmt = conn.createStatement(); 
							ResultSet rs = stmt.executeQuery("SELECT bestellung.BestellNr, bestellung.Bestellart, bestellung.Datum, bestellung.LieferantenNr\n, bestellung.Bestellwert"
									+ ", lieferant.Lieferantenbezeichnung\n"
									+ "FROM (((bestellung INNER JOIN bestellung_produkt ON bestellung.BestellNr = bestellung_produkt.BestellNr)\n"
									+ "INNER JOIN produkt ON bestellung_produkt.ProduktID = produkt.ProduktID)\n"
									+ "INNER JOIN lieferant ON bestellung.LieferantenNr = lieferant.LieferantenNr)\n"
									+ "WHERE bestellung.BestellNr = '"+ bestellID+"'");
							
							String liefBezeichnung = null;
							String bestellart = null;
							String date = null;
							String gesamtbetrag = null;
							while(rs.next()) {
								liefBezeichnung = rs.getString("lieferant.Lieferantenbezeichnung");
								bestellart = rs.getString("bestellung.Bestellart");
								date = rs.getString("bestellung.Datum");
								gesamtbetrag = rs.getString("bestellung.Bestellwert");
							}
							
							writer.write("Bestellung: " + bestellID + " | F�r: " + liefBezeichnung + "\n" + "---------------------------------------------------" + "\n");
							writer.write("Bestellart: " + bestellart + " | Bestellt am: " + date + "\n" + "---------------------------------------------------" + "\n" + "\n");
							
							System.out.println("weiter");
							
							ResultSet rs2 = stmt.executeQuery("SELECT bestellung.BestellNr, bestellung.Bestellart, bestellung.Datum, bestellung.LieferantenNr\n"
									+ ", bestellung_produkt.Menge, bestellung_produkt.zwischenBestellwert, produkt.Produktbezeichnung, lieferant.Lieferantenbezeichnung\n"
									+ "FROM (((bestellung INNER JOIN bestellung_produkt ON bestellung.BestellNr = bestellung_produkt.BestellNr)\n"
									+ "INNER JOIN produkt ON bestellung_produkt.ProduktID = produkt.ProduktID)\n"
									+ "INNER JOIN lieferant ON bestellung.LieferantenNr = lieferant.LieferantenNr)\n"
									+ "WHERE bestellung.BestellNr = '"+ bestellID+"'");
							
							while(rs2.next()) {
								writer.write(rs2.getString("produkt.produktbezeichnung") + " | " + rs2.getString("bestellung_produkt.Menge") + " | " + rs2.getString("bestellung_produkt.zwischenBestellwert") + "�" + "\n");
							}	
							writer.write("\n" + "---------------------------------------------------" + "\n" + "Gesamtwert: " + gesamtbetrag + "�");
						}catch(Exception e){
							System.out.println(e);
						}	
						
					} catch (FileNotFoundException e1) {
						System.out.println("Problem beim Erstellen der Datei");
						e1.printStackTrace();
					}
					
				return geklappt;
			}
				
				public void refreshLieferantenTable(DefaultTableModel model) {
					
					model.setRowCount(0);
					
					try {
						DBAccess.getAlleLieferanten(model);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
}
