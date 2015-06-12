package lib;

import java.util.ArrayList;

public class Message {

	/******************partie Message envoyé pas le WB********************/
	private String Title;
	private String BBCode;
	private String DateStart;
	private Boolean isPriority;
	
	
	/******************Getter & Setter************************/
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getBBCode() {
		return BBCode;
	}
	public void setBBCode(String bBCode) {
		BBCode = bBCode;
	}
	public String getDateStart() {
		return DateStart;
	}
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}
	public Boolean getIsPriority() {
		return isPriority;
	}
	public void setIsPriority(String isPriority) {
		if(isPriority == "true"){
			this.isPriority = true;
		}
		else{
			this.isPriority = false;
		}
	}
	
	public Message(){
		
	}
	
	public Message(String title, String bBCode, String dateStart, String isPriority){
		setTitle(title);
		setBBCode(bBCode);
		setDateStart(dateStart);
		setIsPriority(isPriority);
	}
}
