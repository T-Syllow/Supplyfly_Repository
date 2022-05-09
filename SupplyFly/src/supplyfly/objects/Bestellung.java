package supplyfly.objects;

public class Bestellung {

	Integer bestellnummer;
	String bestellart;
	Double bestellwert;
	String name;
	String datum;
	String lieferzeit;
	String Rechentempo;
	
	public Bestellung(int bestellnummer, String bestellart, double bestellwert, String name, String datum) {
		bestellnummer = this.bestellnummer;
		bestellart = this.bestellart;
		bestellwert = this.bestellwert;
		name = this.name;
		datum = this.datum;
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
	
	
	
}
