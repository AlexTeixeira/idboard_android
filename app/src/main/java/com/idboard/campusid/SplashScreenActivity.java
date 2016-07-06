package com.idboard.campusid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import com.campusid.idboard.R;

public class SplashScreenActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the app's shared preferences to load informations
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cb = app_preferences.getBoolean("cb",false);

        // Skip the SplashScreen if the user memorised his information
        if (cb) {

            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);

            // make sure we close the splash screen so the user won't come back when it presses back key
            finish();
        }
        else {
            setContentView(R.layout.activity_splash_screen);

            Handler handler = new Handler();

            // run a thread after 2 seconds to start the home screen
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // make sure we close the splash screen so the user won't come back when it presses back key
                    finish();

                    // start the home screen
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

            }, 3000);

        }
    }
}