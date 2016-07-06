package com.idboard.campusid;

import com.idboard.campusid.LoginActivity.userSingleton;

import lib.TabsInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.campusid.idboard.R;


public class LateAndAbsenceTabHostFragment extends Fragment implements TabsInterface {
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public LateAndAbsenceTabHostFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_late_and_absence,container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontentLateAndAbsence);

        mTabHost.addTab(mTabHost.newTabSpec("Late").setIndicator(LATE),
                LateFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Absence").setIndicator(ABSENCE),
                AbsenceFragment.class, null);
        
        //Set the tabcontent (top) under the TabHost
        mTabHost.findViewById(R.id.tabcontentLateAndAbsence)
        		.setPadding(0, mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height, 0, 0);
	    /* Information de l'utilisateur en bas du screen */
        TextView textView = (TextView) rootView.findViewById(R.id.textViewID);
        textView.setText(userSingleton.INSTANCE.getId());
        textView = (TextView) rootView.findViewById(R.id.textViewName);
        textView.setText(userSingleton.INSTANCE.getfName() + " " + userSingleton.INSTANCE.getName());
        textView = (TextView) rootView.findViewById(R.id.textViewClass);
        textView.setText(userSingleton.INSTANCE.getClasse());

        return rootView;
    }
}
