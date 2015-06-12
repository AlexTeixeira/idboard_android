package lib;

public class InternShips {

	/******************Variable*******************/
	private String idReference;
	private String Title;
	private String CompanyName;
	private String Duration;
	
	
	/****************Setter & Getter*****************/
	public String getReference() {
		return idReference;
	}
	public void setReference(String reference) {
		idReference = reference;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	
	public InternShips(){
		
	}
	
	public InternShips(String reference, String title, String companyName, String duration){
		setReference(reference);
		setTitle(title);
		setCompanyName(companyName);
		setDuration(duration);
	}
}
