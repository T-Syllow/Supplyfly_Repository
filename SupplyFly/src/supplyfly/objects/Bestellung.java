package supplyfly.objects;

import java.sql.Statement;
import java.util.ArrayList;

import supplyfly.datenbankzugriff.DBAccess;

public class Bestellung {

	private Integer bestellnummer;
	private String bestellart;
	private Double bestellwert;
	private String name;
	private String datum;
	private String status;
	private Integer produkt;
	private Double menge;
	private Integer lieferantenNr;
	
	public Bestellung() {
		
	}
	
	public Bestellung(Integer bestellnummer, String bestellart, Double bestellwert, String name, String datum, String status, Integer produkt, Double menge) {
		this.bestellnummer=bestellnummer;
		this.bestellart = bestellart;
		this.bestellwert = bestellwert;
		this.name = name;
		this.datum = datum;
		this.status = status;
		this.produkt = produkt;
		this.menge = menge;
	}
	
	public Bestellung(Integer bestellnummer, String bestellart, Double bestellwert, String name, String datum, String status, Integer produkt, Double menge, Integer lieferantenNr) {
		this.bestellnummer=bestellnummer;
		this.bestellart = bestellart;
		this.bestellwert = bestellwert;
		this.name = name;
		this.datum = datum;
		this.status = status;
		this.produkt = produkt;
		this.menge = menge;
		this.lieferantenNr = lieferantenNr;
	}

	//Essentiell, damit ProduktIDs wieder richtig abespeichert werden. => naemlich als String..in der Form "1002,1003,1005,..."
	public String convertArrayListToString(ArrayList<Integer> produkte) {
		String produkteAsString = "";
		
		for (Integer integer : produkte) {
			produkteAsString += String.valueOf(integer)+",";
		}
		System.out.println(produkteAsString);
		
		return produkteAsString;
	}
	
	public static Double bestellwert(Double menge, Integer produkt, Integer lieferantenNr) {
		Double produktpreis = DBAccess.getProduktpreis(produkt.toString(), lieferantenNr.toString());
		System.out.println("Menge: " + menge);
		System.out.println("Preis: " + produktpreis);
		System.out.println(menge * produktpreis);
		return menge * produktpreis;
	}
	
//	//Diese Methode soll den WERT = (produktID * menge) ausrechnen und ihn unbedingt als String return.
//	public static String calculateBestellwert(String produktID, String menge) {
//		
//		return "2000";
//	}
	
	public Integer getLieferantenNr() {
		return lieferantenNr;
	}

	public void setLieferantenNr(Integer lieferantenNr) {
		this.lieferantenNr = lieferantenNr;
	}
	
	public Double getMenge() {
		return menge;
	}

	public void setMenge(Double menge) {
		this.menge = menge;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProdukt() {
		return produkt;
	}

	public void setProdukt(Integer produkte) {
		this.produkt = produkte;
	}

	public Integer getBestellnummer() {
		return bestellnummer;
	}

	public void setBestellnummer(Integer bestellnummer) {
		this.bestellnummer = bestellnummer;
	}

	public String getBestellart() {
		return bestellart;
	}

	public void setBestellart(String bestellart) {
		this.bestellart = bestellart;
	}

	public Double getBestellwert() {
		return bestellwert;
	}

	public void setBestellwert(Double bestellwert) {
		this.bestellwert = bestellwert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Bestellung [bestellnummer=" + bestellnummer + ", bestellart=" + bestellart + ", bestellwert="
				+ bestellwert + ", name=" + name + ", datum=" + datum + ", status=" + status + ", produkte=" + produkt
				+ "]";
	}
	
}
