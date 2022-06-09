package supplyfly.objects;

import java.util.ArrayList;

public class Bestellung {

	private Integer bestellnummer;
	private String bestellart;
	private Double bestellwert;
	private String name;
	private String datum;
	private String status;
	private ArrayList<Integer> produkte;
	
	public Bestellung(Integer bestellnummer, String bestellart, Double bestellwert, String name, String datum, String status, ArrayList<Integer> produkte) {
		this.bestellnummer=bestellnummer;
		this.bestellart = bestellart;
		this.bestellwert = bestellwert;
		this.name = name;
		this.datum = datum;
		this.status = status;
		this.produkte = produkte;
	}

	//Essentiell, damit ProduktIDs wieder richtig abespeichert werden. => naemlich als String..in der Form "1002,1003,1005,..."
	public String convertArrayListToString(ArrayList<Integer> produkte) {
		String produkteAsString = "";
		
		for (Integer integer : produkte) {
			produkteAsString += String.valueOf(integer);
		}
		System.out.println(produkteAsString);
		
		return produkteAsString;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Integer> getProdukte() {
		return produkte;
	}

	public void setProdukte(ArrayList<Integer> produkte) {
		this.produkte = produkte;
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
				+ bestellwert + ", name=" + name + ", datum=" + datum + ", status=" + status + ", produkte=" + produkte
				+ "]";
	}
	
}
