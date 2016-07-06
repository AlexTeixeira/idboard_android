package com.idboard.campusid;

import com.idboard.campusid.LoginActivity.userSingleton;

import json.UserBoard;
import lib.TabsInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.campusid.idboard.R;


public class HistoryTabHostFragment extends Fragment implements TabsInterface {
    private FragmentTabHost mTabHost;
    
    /**Creation de l'objet USer**/
    UserBoard user = new UserBoard();
    //Mandatory Constructor
    public HistoryTabHostFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history,container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontentHistory);

        mTabHost.addTab(mTabHost.newTabSpec("Messages").setIndicator(MESSAGE),
                MessageFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Offers").setIndicator(OFFER),
                OfferFragment.class, null);
        
        //Set the tabcontent (top) under the TabHost
        mTabHost.findViewById(R.id.tabcontentHistory)
        		.setPadding(0, mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height, 0, 0);
	    /* Information de l'utilisateur en bas du screen */
        TextView textView = (TextView) rootView.findViewById(R.id.textViewID);
        textView.setText(userSingleton.INSTANCE.getId());
        textView = (TextView) rootView.findViewById(R.id.textViewName);
        textView.setText(userSingleton.INSTANCE.getfName() + " " + userSingleton.INSTANCE.getName());
        textView = (TextView) rootView.findViewById(R.id.textViewClass);
        textView.setText(userSingleton.INSTANCE.getClasse());
	    /* End of the test */

        return rootView;
    }
    
}
