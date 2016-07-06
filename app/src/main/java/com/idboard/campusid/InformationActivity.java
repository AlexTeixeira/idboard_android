package com.idboard.campusid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.campusid.idboard.R;


public class InformationActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        //ajoute un OnClickListener qui permet de de fermer l'activité
        findViewById(R.id.splashScreenLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ferme l'activité
                finish();
            }
        });
    }
}