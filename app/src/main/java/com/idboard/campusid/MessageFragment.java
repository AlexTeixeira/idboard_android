package com.idboard.campusid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.idboard.campusid.LoginActivity.userSingleton;

import json.MessageCall;
import json.UserBoard;
import lib.Message;
import lib.TabsInterface;
import lib.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageFragment extends Fragment implements TabsInterface {

    private class getMessages extends AsyncTask<String, Void, ArrayList<Message>> implements TabsInterface{

    	private MessageCall msg = new MessageCall();
    	
		@Override
		protected ArrayList<Message> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				msg.getMessage();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return msg.getMessages();
		}

    }
	
    private getMessages msgTask;
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      msgTask = new getMessages();

        try {
            messages = msgTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    };

    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.tab_message, container, false);
		
	    ListView listview = (ListView) V.findViewById(R.id.listViewMessage);


	    ArrayList<String> list = new ArrayList<String>();

        /*remplie la liste de message*/
		for (int i = 0; i < messages.size(); i++) {
			list.add(messages.get(i).getTitle());

		}

	    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(V.getContext(), android.R.layout.simple_list_item_1, list);
	   
	    listview.setAdapter(listAdapter);

		return V;
	}
	
	
}