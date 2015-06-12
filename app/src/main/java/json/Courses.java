package json;

import android.annotation.SuppressLint;
import android.util.Log;


import com.idboard.campusid.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import lib.Planning;
import lib.WebService;


@SuppressLint("SimpleDateFormat")
public class Courses {
	/*
	 * Json envoyé par le WB pour les cours
	 *
	 * {
		  "Result": {
		    "ExitCode": 1,
		    "ExitMessage": "sample string 2"
		  },
		  "Courses": [
		    {
		      "Name": "sample string 1",
		      "Type": "sample string 2",
		      "Teacher": "sample string 3",
		      "Comments": "sample string 4",
		      "DateStart": "2014-07-05T10:43:28.5585937+02:00",
		      "DateEnd": "2014-07-05T10:43:28.5585937+02:00",
		      "BackColor": "sample string 7",
		      "BarColor": "sample string 8",
		      "FontColor": "sample string 9"
		    },
		    {
		      "Name": "sample string 1",
		      "Type": "sample string 2",
		      "Teacher": "sample string 3",
		      "Comments": "sample string 4",
		      "DateStart": "2014-07-05T10:43:28.5585937+02:00",
		      "DateEnd": "2014-07-05T10:43:28.5585937+02:00",
		      "BackColor": "sample string 7",
		      "BarColor": "sample string 8",
		      "FontColor": "sample string 9"
		    },
		    {
		      "Name": "sample string 1",
		      "Type": "sample string 2",
		      "Teacher": "sample string 3",
		      "Comments": "sample string 4",
		      "DateStart": "2014-07-05T10:43:28.5585937+02:00",
		      "DateEnd": "2014-07-05T10:43:28.5585937+02:00",
		      "BackColor": "sample string 7",
		      "BarColor": "sample string 8",
		      "FontColor": "sample string 9"
		    }
		  ]
		}
	 *
	 *
	 *
	 */

    /********************AUTRES******************/
    private static String url = "http://idboard.net/idws/api/Courses";
    JSONArray courses = null;
    /**************Tags name pour chaque noeud du json*************/
    private static final String TAG_COURSES = "Courses";
    private static final String TAG_NAME = "Name";
    private static final String TAG_TYPE = "Type";
    private static final String TAG_TEACHER = "Teacher";
    private static final String TAG_COMMENTS = "Comments";
    private static final String TAG_DATESTART = "DateStart";
    private static final String TAG_DATEEND = "DateEnd";
    private static final String TAG_BACKCOLOR = "BackColor";
    private static final String TAG_BARCOLOR = "BarColor";
    private static final String TAG_FONTCOLOR = "FontColor";


    //SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yy hh:mi:ss");

    /****************Variables***********/
    private String Name;
    private String Type;
    private String Teacher;
    private String Comments;
    private String DateStart;
    private String DateEnd;
    private String BackColor;
    private String BarColor;
    private String FontColor;


    /*************Getter && Setter*************/
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getTeacher() {
        return Teacher;
    }
    public void setTeacher(String teacher) {
        Teacher = teacher;
    }
    public String getComments() {
        return Comments;
    }
    public void setComments(String comments) {
        Comments = comments;
    }
    public String getDateStart() {
        return DateStart;
    }
    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }
    public String getDateEnd() {
        return DateEnd;
    }
    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }
    public String getBackColor() {
        return BackColor;
    }
    public void setBackColor(String backColor) {
        BackColor = backColor;
    }
    public String getBarColor() {
        return BarColor;
    }
    public void setBarColor(String barColor) {
        BarColor = barColor;
    }
    public String getFontColor() {
        return FontColor;
    }
    public void setFontColor(String fontColor) {
        FontColor = fontColor;
    }



    /**Array de planning**/
    ArrayList<Planning> planningArray = new ArrayList<Planning>();


    public ArrayList<Planning> getPlanning(){
        return planningArray;
    }



    /**************M�thode de r�cup�ration
     * @throws java.text.ParseException ****************/
    public void getCallCourses(String dateStart,String dateEnd, String[] monthYear) throws JSONException, IOException {


        String jsonStr = "";

        WebService wb = new WebService(url,dateStart,dateEnd, monthYear);

        /*appel au wb et renvoie un string au format json*/
        jsonStr = wb.CallWebService();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                courses = jsonObj.getJSONArray(TAG_COURSES);


                // looping through All Contacts
                for (int i = 0; i < courses.length(); i++) {
                    jsonObj = courses.getJSONObject(i);
                    Planning plan = new Planning (
                            jsonObj.getString(TAG_NAME),
                            jsonObj.getString(TAG_TEACHER),
                            jsonObj.getString(TAG_TYPE),
                            jsonObj.getString(TAG_DATESTART),
                            jsonObj.getString(TAG_DATEEND),
                            jsonObj.getString(TAG_BACKCOLOR)
                    );
                    planningArray.add(plan);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

    }
}