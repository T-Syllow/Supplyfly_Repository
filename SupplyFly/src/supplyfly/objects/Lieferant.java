package supplyfly.objects;

public class Lieferant {
	
	private String name;
	private String ansprechpartner;
	private Integer idNr;
	private String strasse;
	private int hausnummer;
	private int PLZ;
	private String ort;
	
	public Lieferant(String name, String ansprechpartner, Integer idNr, String strasse, int hausnummer,  int PLZ, String ort) {
		this.name = name;
		this.ansprechpartner = ansprechpartner;
		this.idNr = idNr;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.PLZ = PLZ;
		this.ort = ort;
	}

	//Getter und Setter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnsprechpartner() {
		return ansprechpartner;
	}

	public void setAnsprechpartner(String ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

	public Integer getIdNr() {
		return idNr;
	}

	public void setIdNr(Integer idNr) {
		this.idNr = idNr;
	}
	
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}

	public int getPLZ() {
		return PLZ;
	}

	public void setPLZ(int pLZ) {
		PLZ = pLZ;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}



}
