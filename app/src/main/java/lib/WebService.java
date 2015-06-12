package lib;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by orcusz on 23/01/15.
 */



public class WebService {


    /*Variable*/
    //mandatory variable to web service
    private String url;


    //mandatory variable to get courses
    private String dateStart;
    private String dateEnd;
    private String[] monthYear;

    //mandatory to call all internship
    private int start;
    private int count;

    //mandatory to call one internship
    private int ref;

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String[] getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String[] monthYear) {
        this.monthYear = monthYear;
    }

    public WebService(){

    }

    public WebService(String _url){
        setUrl(_url);
    }



    public WebService(String _url,String _dateStart,String _dateEnd, String[] _monthYear){
        setUrl(_url);
        setDateStart(_dateStart);
        setDateEnd(_dateEnd);
        setMonthYear(_monthYear);
    }

    public WebService(String _url,int _start, int _count){
        setUrl(_url);
        setCount(_count);
        setStart(_start);
    }

    public WebService(String _url,int _ref){
        setUrl(_url);
        setRef(_ref);

    }

    private String getMonth(String month){
        String numMonth = "";
        String [] arrayMonth = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String [] arrayMonth1 = {"janv.","févr.","mars","avr.","mai.","juin","juil.","août","sept.","oct.","nov.","déc."};


        for(int i=0;i<arrayMonth.length;i++){
            if(arrayMonth[i].equals(month)){
                numMonth = "0"+Integer.toString(i+1);
                break;
            }
            else if(arrayMonth1[i].equals(month)){
                numMonth = "0"+Integer.toString(i+1);
                break;
            }
        }

        return numMonth;
    }

    public String CallWebService() throws IOException, JSONException {

        // Creating service handler class instance
//        ServiceHandler sh = new ServiceHandler();
//
//        // Making a request to url and getting response
//        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        String jsonStr = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(getUrl());
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-Type","application/json");

		/*Json pour l'envoie */
        String jsonSend = "";

        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("IDBoard", LoginActivity.userSingleton.INSTANCE.getId());
        jsonObject.accumulate("Password", LoginActivity.userSingleton.INSTANCE.getPassword());


        /*Condition for course call */
        if(getDateStart() != null && getDateEnd() != null){
            String trueDateStart;
            //monthYear[1]+"-"getMonth(monthYear[0])+"-"+dateStart;
            if(Integer.parseInt(dateEnd) < Integer.parseInt(dateStart)){
                //int month = Integer.parseInt(getMonth(monthYear[0])) - 1;
                int month;

                if(Integer.parseInt(getMonth(monthYear[0])) == 1){
                    month = (Integer.parseInt(getMonth(monthYear[0])) +11)%(13);
                }
                else{
                    month = (Integer.parseInt(getMonth(monthYear[0])) +12)%(13);
                }

                if(month < 10){
                    trueDateStart =  monthYear[1]+"-0"+Integer.toString(month)+"-"+dateStart;

                }
                else{
                    trueDateStart =  monthYear[1]+"-"+Integer.toString(month)+"-"+dateStart;

                }

            }
            else{
                trueDateStart =  monthYear[1]+"-"+getMonth(monthYear[0])+"-"+dateStart;

            }
            String trueDateEnd =  monthYear[1]+"-"+getMonth(monthYear[0])+"-"+dateEnd;

            jsonObject.accumulate("DateStart", trueDateStart+"T00:00:00.9941406+01:00");
            jsonObject.accumulate("DateEnd", trueDateEnd+"T23:59:59.9941406+01:00");
        }
        else if(Integer.valueOf(getStart()) != null && Integer.valueOf(getCount()) != null && getCount() != 0){
            jsonObject.accumulate("Start", getStart());
            jsonObject.accumulate("Count", getCount());
        }
        else if(Integer.valueOf(getRef()) != null){
            jsonObject.accumulate("IDInternship", getRef());
        }

        // 4. convert JSONObject to JSON to String
        jsonSend = jsonObject.toString();

        // ** Alternative way to convert Person object to JSON string usin Jackson Lib
        // ObjectMapper mapper = new ObjectMapper();
        // json = mapper.writeValueAsString(person);
        // 5. set json to StringEntity
        StringEntity se = new StringEntity(jsonSend);

        // 6. set httpPost Entity
        httppost.setEntity(se);

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);


        if (httppost instanceof HttpEntityEnclosingRequest) { //test if request is a POST
            HttpEntity entity = ((HttpEntityEnclosingRequest) httppost).getEntity();
            String body = EntityUtils.toString(entity); //here you have the POST body

            jsonStr = EntityUtils.toString(response.getEntity());

        }

        Log.d("jsonStr",jsonStr);
        return jsonStr;
    }
}
