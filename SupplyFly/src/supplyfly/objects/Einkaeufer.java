package supplyfly.objects;

public class Einkaeufer {
	
	private String nutzername;
	private Integer nutzerId;
	
	public Einkaeufer(String nutzername, Integer nutzerId) {
		this.nutzername = nutzername;
		this.nutzerId = nutzerId;
	}
	
	
	//Getters & Setters

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
