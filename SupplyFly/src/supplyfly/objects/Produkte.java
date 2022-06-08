package supplyfly.objects;

public class Produkte {

	private Integer menge;
	private Integer artikelNr;
	private String bezeichnung;
	private Integer mindestbestand;
	private String produktspezifikation;
	//private static Integer zaehler = 1006;
	
	public Produkte(Integer menge, Integer artikelNr, String bezeichnung,Integer mindestbestand, String spezifikation) {
		this.menge = menge;
		//zaehler++;
		this.artikelNr = artikelNr;
		this. bezeichnung =  bezeichnung;
		this.mindestbestand = mindestbestand;
		this.produktspezifikation = spezifikation;
		
	}
	
	//Getter & Setter

	public String getProduktspezifikation() {
		return produktspezifikation;
	}

	public void setProduktspezifikation(String produktspezifikation) {
		this.produktspezifikation = produktspezifikation;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Integer getMindestbestand() {
		return mindestbestand;
	}

	public void setMindestbestand(Integer mindestbestand) {
		this.mindestbestand = mindestbestand;
	}

	public Integer getMenge() {
		return menge;
	}

	public void setMenge(Integer menge) {
		this.menge = menge;
	}

	public Integer getArtikelNr() {
		return artikelNr;
	}

	public void setArtikelNr(Integer artikelNr) {
		this.artikelNr = artikelNr;
	}

}
