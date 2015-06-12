package com.idboard.campusid;

import lib.DatePickerFragment;
import lib.TabsInterface;
import lib.TimePickerFragment;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity
{	
	private String[] mTabTitles;
	private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
    private PlanningFragment planningFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabTitles = getResources().getStringArray(R.array.tabs_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {
        };
        
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mTabTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState ==  null)
        {
            selectItem(0);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	/** Enable to click on a drawer item */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		switch (position) {
		case TabsInterface.PLANNING:
            planningFragment = new PlanningFragment();
			transaction.replace(R.id.content_frame, planningFragment);
			break;
		case TabsInterface.HISTORY:
			transaction.replace(R.id.content_frame, new HistoryTabHostFragment());
			break;
		case TabsInterface.LATE_AND_ABSENCE:
			transaction.replace(R.id.content_frame, new LateAndAbsenceTabHostFragment());
			break;
		default:
			transaction.replace(R.id.content_frame, planningFragment);
			break;
		}
		
		// and add the transaction to the back stack so the user can navigate back
//		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mTabTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}


    /**
     * Show an alert if the user press back
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes-vous sûr de vouloir retourner à la page d'identification ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //@SuppressLint("SimpleDateFormat")
    public void getWeekDayNext(View v) {

//        getSupportFragmentManager().getFragment()
        planningFragment.getWeekDayNext();

    }

    //@SuppressLint("SimpleDateFormat")
    public void getWeekDayPrev(View v) {

//        weekDaysCount = weekDaysCount - 7;
//
//
//        createAction(getWeekDay());


        planningFragment.getWeekDayPrev();

    }

	/**
	 * Create and show the time picker
	 * @param v
	 */
    public void showTimePickerDialog(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment((Button)findViewById(v.getId()));
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }
	
    /**
     * Create and show the date picker
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment((Button)findViewById(v.getId()));
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
    
    /**
     * Send an email from tab_late.xml
     * @param v
     */
    public void sendEmail(View v) {

    	String to = TabsInterface.TO;
    	String subject = "Une erreur est survenu lors de la création de l'email";
    	String message = subject;
    	
    	/** Set the subject and the message in function of the button */
    	//If it is for a late
    	if (v.getId() == R.id.buttonLateSend) {
    		subject = TabsInterface.LATE_EMAIL_SUBJECT;
    		message = "Bonjour,"
    				+ "\n\n"
    				+ "Je serais en retard de "
    				+ ((Button) findViewById(R.id.buttonTimePicker)).getText()
    				+ " (hh:mm) pour le motif suivant : "
    				+ ((EditText) findViewById(R.id.editTextLateReason)).getText()
    				+ ".\n\n"
    				+ "Message envoyé depuis l'application mobile.";
    	}
    	//Else if it is for an absence
    	else if (v.getId() == R.id.buttonAbsenceSend) {
    		subject = TabsInterface.ABSENCE_EMAIL_SUBJECT;
    		message = "Bonjour,"
    				+ "\n\n"
    				+ "Je serais absent à partir du "
    				+ "\n"
    				+ ((Button) findViewById(R.id.buttonDatePickerBegin)).getText()
    				+ "\n"
    				+ "jusqu'au"
    				+ ((Button) findViewById(R.id.buttonDatePickerEnd)).getText()
    				+ "\n"
    				+ "pour le motif suivant :"
    				+ ((EditText) findViewById(R.id.editTextAbsenceReason)).getText()
    				+ ".\n\n"
    				+ "Message envoyé depuis l'application mobile.";
    	}

    	/** Create and inform the email intent */
    	Intent email = new Intent(Intent.ACTION_SEND);
    	email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
    	//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
    	//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
    	email.putExtra(Intent.EXTRA_SUBJECT, subject);
    	email.putExtra(Intent.EXTRA_TEXT, message);

    	//need this to prompts email client only
    	email.setType("message/rfc822");

    	startActivity(Intent.createChooser(email, TabsInterface.EMAIL_CLIENT));

    }
}
