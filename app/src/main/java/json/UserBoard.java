package json;

import java.io.IOException;
import java.util.ArrayList;

import lib.User;
import lib.WebService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.idboard.campusid.LoginActivity;

public class UserBoard {
	
	
	/**************Tags name pour chaque noeud du json*************/	
	public enum TAG {
		USER("User"),
		FIRSTNAME("FirstName"),
		LNAME("LastName"),
		RESULT("Result"),
		EXITCODE("ExitCode"),
        SITE("Sites"),
        LEVEL("Name"),
        ROLES("Roles"),
        NAME("Name");
		//GENDER("gender");
		
		private String value;
		
		TAG(String value) {
			this.setValue(value);
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	
	/********** The list of users **********/	
	private User user = new User();
	public User getUser() {
		return user;
	}
//	public void setContactsList(ArrayList<User> contactsList) {
//		this.contactsList = contactsList;
//	}
	
	
	/******************** Others ******************/
	/**http://www.androidhive.info/2012/01/android-json-parsing-tutorial/ pour le tuto**/
	private static final String url = "http://idboard.net/idws/api/UserBoard"; //URL DE TEST
    JSONObject userboard = null;
    JSONObject users = null;
    JSONObject result =  null;
    JSONArray sites = null;
	
	/************** Load the list of users 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws JSONException ****************/
	public void getUsers() throws ParseException{

        String jsonStr = null;
        WebService wb = new WebService(url);
        try {
            jsonStr = wb.CallWebService();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                 
                // Getting nodes
                result = jsonObj.getJSONObject(TAG.RESULT.getValue());
                String exitcode = result.getString(TAG.EXITCODE.getValue());

                if(Integer.parseInt(exitcode) == 0) {
                    users = jsonObj.getJSONObject(TAG.USER.getValue());
                    // looping through All Contacts
                    for (int i = 0; i < 1; i++) {
                        if (Integer.parseInt(result.getString(TAG.EXITCODE.getValue())) == 0) {
                            sites = users.getJSONArray(TAG.SITE.getValue());
                            JSONObject json_obj = sites.getJSONObject(0);
                            JSONArray jsonRole = json_obj.getJSONArray(TAG.ROLES.getValue());
                            json_obj = jsonRole.getJSONObject(1);
                            String classSplit = "";

                            try {
                                classSplit = json_obj.getString((TAG.NAME.getValue()));
                            } catch (Exception e) {
                                Log.e("Error =>", e.toString());
                            }


                            user = new User(
                                    users.getString(TAG.FIRSTNAME.getValue()),
                                    users.getString(TAG.LNAME.getValue()),
                                    LoginActivity.userSingleton.INSTANCE.getId(),
                                    LoginActivity.userSingleton.INSTANCE.getPassword(),
                                    classSplit.split("élèvede")[1],
                                    exitcode
                            );
                        }


                    }
                }
                else{
                    user = new User(exitcode);
                }

                
                
            } catch (JSONException e) {
                e.printStackTrace();

            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
	}
}