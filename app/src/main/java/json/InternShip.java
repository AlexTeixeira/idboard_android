package json;

import lib.JSONParser;
import lib.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.IOException;

public class InternShip {
	/*
	 * Json envoyé par le WB pour les entreprise
	 * 
	 * {
		  "Result": {
		    "ExitCode": 1,
		    "ExitMessage": "sample string 2"
		  },
		  "Internship": {
		    "Reference": "sample string 1",
		    "Title": "sample string 2",
		    "MissionSummary": "sample string 3",
		    "CompanyName": "sample string 4",
		    "Duration": "sample string 5",
		    "Contact": "sample string 6",
		    "ContactPhone": "sample string 7",
		    "ContactMail": "sample string 8",
		    "Address1": "sample string 9",
		    "Address2": "sample string 10",
		    "PostalCode": "sample string 11",
		    "City": "sample string 12",
		    "Country": "sample string 13"
		  }
		}
	 * 
	 * 
	 * 
	 */
	
	/*public InternShip(){
		getInternShip();
	}*/
	
	/**************Tags name pour chaque noeud du json*************/
	private static final String TAG_INTERNSHIP = "Internship";
	private static final String TAG_REF = "Reference";
	private static final String TAG_TITLE = "Title";
	private static final String TAG_MISSION = "MissionSummary";
	private static final String TAG_COMPANY = "CompanyName";
	private static final String TAG_DURATION = "Duration";
	private static final String TAG_CONTACT = "Contact";
	private static final String TAG_PHONE = "ContactPhone";
	private static final String TAG_MAIL = "ContactMail";
	private static final String TAG_ADDRESS1 = "Address1";
	private static final String TAG_ADDRESS2 = "Address2";
	private static final String TAG_POSTALCODE = "PostalCode";
	private static final String TAG_CITY = "City";
	private static final String TAG_COUNTRY = "Country";

	/********************AUTRES******************/
    private static String url = "http://idboard.net/idws/api/Internship";
    JSONObject internShip = null;

    private lib.InternShip intern;
    public lib.InternShip getIntership() {
        return intern;
    }
	/**************M�thode de r�cup�ration ****************/
	public void getInternShip(int ref){
		// Creating service handler class instance
        String jsonStr = "";

        /*appel au wb et renvoie un string au format json*/
        WebService wb = new WebService(url,ref);

        try {
            wb.CallWebService();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonStr = wb.CallWebService();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonStr != null) {
	    
	    try {
	    	JSONObject jsonObj = new JSONObject(jsonStr);
	    	
	    	// Getting JSON Array node
	    	internShip = jsonObj.getJSONObject(TAG_INTERNSHIP);



                intern = new lib.InternShip(
                   internShip.getString(TAG_TITLE),
                   internShip.getString(TAG_COMPANY),
                   internShip.getString(TAG_DURATION),
                   internShip.getString(TAG_MISSION)
                );

	    	
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	} else {
	    Log.e("ServiceHandler", "Couldn't get any data from the url");
	}
}
}
