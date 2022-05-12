package supplyfly.objects;

public class Lieferant {
	
	private String name;
	private String ansprechpartner;
	private Integer idNr;
	private String standort;
	
	public Lieferant(String name, String ansprechpartner, Integer idNr, String standort) {
		this.name = name;
		this.ansprechpartner = ansprechpartner;
		this.idNr = idNr;
		this.standort = standort;
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

	public String getStandort() {
		return standort;
	}

	public void setStandort(String standort) {
		this.standort = standort;
	}
	
	

}
