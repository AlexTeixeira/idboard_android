package json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

public class MessageCall {
	/*
	 * Json envoyé par le WB pour les message
	 * 
			 * {
		  "Result": {
		    "ExitCode": 1,
		    "ExitMessage": "sample string 2"
		  },
		  "Messages": [
		    {
		      "Title": "sample string 1",
		      "BBCode": "sample string 2",
		      "DateStart": "2014-07-05T10:12:05.9404296+02:00",
		      "IsPriority": true
		    },
		    {
		      "Title": "sample string 1",
		      "BBCode": "sample string 2",
		      "DateStart": "2014-07-05T10:12:05.9404296+02:00",
		      "IsPriority": true
		    },
		    {
		      "Title": "sample string 1",
		      "BBCode": "sample string 2",
		      "DateStart": "2014-07-05T10:12:05.9404296+02:00",
		      "IsPriority": true
		    }
		  ]
		}
	 * 
	 * 
	 * 
	 */
	
	/**************Tags name pour chaque noeud du json*************/
	private static final String TAG_MESSAGE = "Messages";
	private static final String TAG_TITLE = "Title";
	private static final String TAG_BBCODE = "BBCode";
	private static final String TAG_DATESTART = "DateStart";
	private static final String TAG_ISPRIOTITY = "IsPriority";

	/********************AUTRES******************/
	private static String url = "http://idboard.net/idws/api/Messages"; //changer l'url par la bonne
    JSONArray message = null;
    //SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yy hh:mi:ss");
    
	
	/******************partie Message envoyé pas le WB********************/
	
	private ArrayList<Message> Messages = new ArrayList<Message>();
	
	
	/******************Getter & Setter************************/
	public ArrayList<Message> getMessages(){
		return Messages;
	}
	
		
	/**************M�thode de r�cup�ration 
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ClientProtocolException ****************/
	public void getMessage() throws JSONException, ClientProtocolException, IOException{
		

        String jsonStr = "";

        /*appel au wb et renvoie un string au format json*/
        WebService wb = new WebService(url,0,20);

        jsonStr = wb.CallWebService();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                 
                // Getting JSON Array node
                message = jsonObj.getJSONArray(TAG_MESSAGE);

                // looping through All Contacts
                for (int i = 0; i < message.length(); i++) {
                    JSONObject c = message.getJSONObject(i);
                    
                    /**Recuperation des noeuds**/
                    //String id = c.getString(TAG_ID);
                    Message msg = new Message (
                    		c.getString(TAG_TITLE),
                    		c.getString(TAG_BBCODE),
                    		c.getString(TAG_DATESTART),
                    		c.getString(TAG_ISPRIOTITY)
                    		);
                    getMessages().add(msg);                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
	}
}
