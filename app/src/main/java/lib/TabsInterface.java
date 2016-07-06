package lib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.campusid.idboard.R;
import com.idboard.campusid.LoginActivity.userSingleton;

public interface TabsInterface {

	
	
	/* List of different tab's layouts */
	 public static final int[] LAYOUTS = {
     	R.layout.fragment_planning,
     	R.layout.fragment_history,
     	R.layout.fragment_late_and_absence
     };
	 
	/* List of different tab's id */
	public static final int 
		PLANNING = 0,
		HISTORY = 1,
		LATE_AND_ABSENCE = 2;
	
	/* List of different tab's name */
	public static final String 
		LATE = "Retard",
		ABSENCE = "Absence",
		MESSAGE = "Messages",
		OFFER = "Offres";
	
	/* List of different progress messages */
	public static final String
		PROGRESS_MESSAGE = "Tentative de connexion. Veuillez patienter...",
		PROGRESS_ERROR = "Echec de la connexion. Veuillez réessayer.";
	/* Informations for sending email */
	public static final String 
		TO = "scolarite@ieid.eu",
		INFORMATIONS_FOR_EMAIL_SUBJECT = userSingleton.INSTANCE.getName() + " " + userSingleton.INSTANCE.getfName() + " le " +  new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date()),
		LATE_EMAIL_SUBJECT = "Retard de " + INFORMATIONS_FOR_EMAIL_SUBJECT,
		ABSENCE_EMAIL_SUBJECT = "Absence de " + INFORMATIONS_FOR_EMAIL_SUBJECT,
		EMAIL_CLIENT = "Veuillez choisir un client mail :";

}
