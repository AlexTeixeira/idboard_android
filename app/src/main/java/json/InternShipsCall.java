package json;

import java.io.IOException;
import java.util.ArrayList;

import lib.InternShips;
import lib.JSONParser;
import lib.Message;
import lib.WebService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.idboard.campusid.LoginActivity.userSingleton;

import android.util.Log;

public class InternShipsCall {

	/*
	 * Json envoyé par le WB pour les entreprise
	 * 
	 * 
  "Result": {
    "ExitCode": 1,
    "ExitMessage": "sample string 2"
  },
  "Internships": [
    {
      "IDInternship": 1,
      "Title": "sample string 2",
      "CompanyName": "sample string 3",
      "Duration": "sample string 4"
    },
    {
      "IDInternship": 1,
      "Title": "sample string 2",
      "CompanyName": "sample string 3",
      "Duration": "sample string 4"
    },
    {
      "IDInternship": 1,
      "Title": "sample string 2",
      "CompanyName": "sample string 3",
      "Duration": "sample string 4"
    }
  ]
}

	 * 
	 * 
	 * 
	 */
	
	public InternShipsCall(){
		getInternShips();
	}
	
	/**************Tags name pour chaque noeud du json*************/
    private static final String TAG_INTERN = "Internships";
    private static final String TAG_REF = "IDInternship";
	private static final String TAG_TITLE = "Title";
	private static final String TAG_COMPANY = "CompanyName";
	private static final String TAG_DURATION = "Duration";

	/********************AUTRES******************/
	private static String url = "http://idboard.net/idws/api/Internships";
    JSONArray internShip = null;
	
	/******************Variable*******************/
	private ArrayList<InternShips> Internships = new ArrayList<InternShips>();
	
	/****************Setter & Getter*****************/
	public ArrayList<InternShips> getInternShips(){
		return Internships;
	}
	
	/**************M�thode de r�cup�ration 
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ClientProtocolException ****************/
	public void getInternShipsJson() throws JSONException, ClientProtocolException, IOException{
		/**
		 * Cette partie doit être factoriser
		 * 
		 */

		String jsonStr = "";

        /*appel au wb et renvoie un string au format json*/
        WebService wb = new WebService(url,0,20);

        jsonStr = wb.CallWebService();

        if (jsonStr != null) {
	    
	    try {
	    	JSONObject jsonObj = new JSONObject(jsonStr);
            internShip = jsonObj.getJSONArray(TAG_INTERN);


	    	// looping through All Contacts
            for (int i = 0; i < internShip.length(); i++) {

                    jsonObj = internShip.getJSONObject(i);
            		InternShips intern = new InternShips (
            				jsonObj.getString(TAG_REF),
            				jsonObj.getString(TAG_TITLE),
            				jsonObj.getString(TAG_COMPANY),
            				jsonObj.getString(TAG_DURATION)
                    		);
                    Internships.add(intern);

            }
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	} else {
	    Log.e("ServiceHandler", "Couldn't get any data from the url");
	}
}
}
