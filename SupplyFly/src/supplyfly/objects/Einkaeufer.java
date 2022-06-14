package supplyfly.objects;

public class Einkaeufer {
	
	private Integer nutzerId;
	private String nutzername;
	private String nutzerRolle;
	
	public Einkaeufer(String nutzername, Integer nutzerId, String nutzerRolle) {
		this.nutzername = nutzername;
		this.nutzerId = nutzerId;
		this.nutzerRolle = nutzerRolle;
	}
	
	
	//Getters & Setters

	public String getNutzerRolle() {
		return nutzerRolle;
	}


	public void setNutzerRolle(String nutzerRolle) {
		this.nutzerRolle = nutzerRolle;
	}


	public String getNutzername() {
		return nutzername;
	}

	public void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}

	public Integer getNutzerId() {
		return nutzerId;
	}

	public void setNutzerId(Integer nutzerId) {
		this.nutzerId = nutzerId;
	}
	
	

}
