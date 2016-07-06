package com.idboard.campusid;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.ParseException;
import org.json.JSONException;

import json.UserBoard;
import lib.TabsInterface;
import lib.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.campusid.idboard.R;

public class LoginActivity extends Activity
{
	
	
	EditText editTextIdentification = null;
	EditText editTextPassword = null;
    CheckBox registerCheckBox = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
		setTitle("Connexion");
		
		editTextIdentification = (EditText)findViewById(R.id.editTextIdentification);

		/*
		 * This will set the "OK" button of the editTextPassword's Keyboard
		 * to call the method "connexionClick(View view)".
		 */
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					v.clearFocus();
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					return true;
				}
				return false;
			}

		});

        registerCheckBox = (CheckBox)findViewById(R.id.registerCheckBox);

        // Get the app's shared preferences to load informations
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String id = app_preferences.getString("id","");
        String pw = app_preferences.getString("pw","");
        boolean cb = app_preferences.getBoolean("cb",false);

        registerCheckBox.setChecked(cb ? true : false);
        editTextIdentification.setText(registerCheckBox.isChecked() ? id : "");
        editTextPassword.setText(registerCheckBox.isChecked() ? pw : "");
	}

    @Override
    protected void onStop() {
        super.onDestroy();

        // Get the app's shared preferences to save informations
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("id", editTextIdentification.getText().toString());
        editor.putString("pw", editTextPassword.getText().toString());
        editor.putBoolean("cb", registerCheckBox.isChecked());
        editor.commit();
    }

    //	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	/**
	 * EventTrigger for the connexion
	 * @param view The current view
	 */
	public void connexionClick(View view) throws InterruptedException, ExecutionException, TimeoutException {
        // Get the app's shared preferences to save informations
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("id", editTextIdentification.getText().toString());
        editor.putString("pw", editTextPassword.getText().toString());
        editor.putBoolean("cb", registerCheckBox.isChecked());
        editor.commit();

        userSingleton.INSTANCE.setId(editTextIdentification.getText().toString());
        userSingleton.INSTANCE.setPassword(editTextPassword.getText().toString());

		GetContactsAsyncTask contactsInstance = new GetContactsAsyncTask();
		contactsInstance.execute();
	}

    /**
     * Show the Splash Screen
     * @param view The current view
     */
    public void showSplashScreen(View view) {
        // start the splash screen to show information
        Intent intent = new Intent(LoginActivity.this, InformationActivity.class);
        startActivity(intent);
    }
	
	
	/************************************
	 * The singleton of the logged user *
	 ************************************/
	
	public enum userSingleton {
		
		INSTANCE;
		private String fName;
		private String name;

		private String id;
		private String password;

        private String level;

        public String getClasse() { return level; }
        public void setClasse(String _level) { level = _level; }
		
		public String getfName() {
			return fName;
		}
		public void setfName(String fName) {
			this.fName = fName;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

    /**Test connexion*/
    public boolean isConnectedInternet()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
        {
            NetworkInfo.State networkState = networkInfo.getState();
            if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0)
            {
                return true;
            }
            else return false;
        }
        else return false;
    }
	
	/**********************************************
	 * AsyncTask class who create a list of users *
	 **********************************************/
	
    private class GetContactsAsyncTask extends AsyncTask<String, Void, Void> implements TabsInterface{
    	UserBoard userBoard = new UserBoard();
    	Boolean isInUserBoard;
    	ProgressDialog progressDialog;
    	Builder alertDialog;



		@Override
		protected Void doInBackground(String... strings) {
			try {

                if(isConnectedInternet()){
                    userBoard.getUsers();
                }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            isInUserBoard = isInUserBoard();
			return null;
		}
		
		/**
		 * Show a progressdialog during the execution
		 */
		protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(PROGRESS_MESSAGE);
            progressDialog.show();
            alertDialog = new AlertDialog.Builder(LoginActivity.this);
        }
		
		/**
		 * Start the activity if the identification is good
		 */
		protected void onPostExecute(Void result) {
			if (progressDialog.isShowing()) {
                if(isInUserBoard) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    progressDialog.dismiss();
                    startActivity(intent);
                }
                else {
                    alertDialog.setMessage(PROGRESS_ERROR);
                    alertDialog.show();
                    progressDialog.dismiss();

                }
			}
			else {
	            alertDialog.setMessage(PROGRESS_ERROR);
	            alertDialog.show();
                progressDialog.dismiss();

            }
		}
		
		/**
		 * Verify if the user and the password are in the database
		 */
		private Boolean isInUserBoard() {
			Boolean isInUserBoard;
			//String g = userBoard.getUser().getfName();
			if(userBoard.getUser().getExitCode() != null){
                if(Integer.parseInt(userBoard.getUser().getExitCode()) == 0 ){
                    isInUserBoard = true;
                    userSingleton.INSTANCE.setfName(userBoard.getUser().getfName());
                    userSingleton.INSTANCE.setName(userBoard.getUser().getName());
                    userSingleton.INSTANCE.setId(userBoard.getUser().getId());
                    userSingleton.INSTANCE.setClasse(userBoard.getUser().getClasse());
                    userSingleton.INSTANCE.setPassword(userBoard.getUser().getPassword());


                }
                else{
                    isInUserBoard = false;
                }
            }
            else{
                isInUserBoard = false;
            }



			return isInUserBoard;
		}
    	
    }

}
