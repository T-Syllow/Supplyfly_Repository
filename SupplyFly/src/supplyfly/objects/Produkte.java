package supplyfly.objects;

public class Produkte {

	private Integer menge;
	private Integer artikelNr;
	private Double preis;
	
	public Produkte(Integer menge, Integer artikelNr, Double preis) {
		this.menge = menge;
		this.artikelNr = artikelNr;
		this.preis = preis;
	}
	
	//Getter & Setter

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

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}
	
	
	
	
	
}
