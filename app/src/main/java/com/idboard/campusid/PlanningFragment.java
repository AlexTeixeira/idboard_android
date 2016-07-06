package com.idboard.campusid;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import json.Courses;
import lib.Entity1;
import lib.Planning;
import lib.TabsInterface;
import lib.WeekSets;
import com.campusid.idboard.R;


public class PlanningFragment extends Fragment implements TabsInterface {

    private TextView textViewDate;
    private TextView textViewMon;
    private TextView textViewTue;
    private TextView textViewWed;
    private TextView textViewThu;
    private TextView textViewFri;
    private RelativeLayout relativeLayoutMonDay;
    private RelativeLayout relativeLayoutTueDay;
    private RelativeLayout relativeLayoutWedDay;
    private RelativeLayout relativeLayoutThuDay;
    private RelativeLayout relativeLayoutFriDay;
    public String[] NextPreWeekday;
    public String dateFormate;
    public String firstDayOfWeek;
    public String lastDayOfWeek;
    ;

    public static ArrayList<Entity1> arrayListEntity = new ArrayList<Entity1>();
    public static ArrayList<Entity1> arrayListEButtonId = new ArrayList<Entity1>();

    public int weekDaysCount = 2;
    String tapMargin ;
    String buttonHight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_planning, container, false);

        textViewDate = (TextView) V.findViewById(R.id.textViewDate);
        textViewMon = (TextView) V.findViewById(R.id.textViewMon);
        textViewTue = (TextView) V.findViewById(R.id.textViewTue);
        textViewWed = (TextView) V.findViewById(R.id.textViewWed);
        textViewThu = (TextView) V.findViewById(R.id.textViewThu);
        textViewFri = (TextView) V.findViewById(R.id.textViewFri);
        relativeLayoutMonDay = (RelativeLayout) V.findViewById(R.id.relativeLayoutMonDay);
        relativeLayoutTueDay = (RelativeLayout) V.findViewById(R.id.relativeLayoutTueDay);
        relativeLayoutWedDay = (RelativeLayout) V.findViewById(R.id.relativeLayoutWedDay);
        relativeLayoutThuDay = (RelativeLayout) V.findViewById(R.id.relativeLayoutThuDay);
        relativeLayoutFriDay = (RelativeLayout) V.findViewById(R.id.relativeLayoutFriDay);

        TextView textView = (TextView) V.findViewById(R.id.textViewID);
        textView.setText(LoginActivity.userSingleton.INSTANCE.getId());
        textView = (TextView) V.findViewById(R.id.textViewName);
        textView.setText(LoginActivity.userSingleton.INSTANCE.getfName() + " " + LoginActivity.userSingleton.INSTANCE.getName());
        textView = (TextView) V.findViewById(R.id.textViewClass);
        textView.setText(LoginActivity.userSingleton.INSTANCE.getClasse());

        createAction(getWeekDay());

        return V;
    }

    public void createAction(String[] weekDay) {

        NextPreWeekday = weekDay;
        firstDayOfWeek = convertWeekDays(NextPreWeekday[0]);
        lastDayOfWeek = convertWeekDays(NextPreWeekday[6]);
        textViewDate.setText(firstDayOfWeek + "-" + lastDayOfWeek + " "
                + convertWeekDaysMouth(NextPreWeekday[6]));

        textViewMon.setText(convertWeekDays(NextPreWeekday[0])
                + "\nLun");
        textViewTue.setText(convertWeekDays(NextPreWeekday[1])
                + "\nMar");
        textViewWed.setText(convertWeekDays(NextPreWeekday[2])
                + "\nMer");
        textViewThu.setText(convertWeekDays(NextPreWeekday[3])
                + "\nJeu");
        textViewFri.setText(convertWeekDays(NextPreWeekday[4])
                + "\nVen");
        try
        {
            new LoadViewsIntoWeekView().execute("").get();
        } catch (Exception e)
        {
            Log.getStackTraceString(e);
        }
    }

    public String[] getWeekDay() {

        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + weekDaysCount;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;

    }

    //@SuppressLint("SimpleDateFormat")
    public void getWeekDayNext() {

        weekDaysCount = weekDaysCount + 7;

        createAction(getWeekDay());

    }

    //@SuppressLint("SimpleDateFormat")
    public void getWeekDayPrev() {

        weekDaysCount = weekDaysCount - 7;

        createAction(getWeekDay());

    }

    public Button getButtonToLayout(int higth, int marginTop,
                                    String buttonText, String jobID, int buttonID, String backColor) {

        @SuppressWarnings("deprecation")
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, higth - dpToPx(1));
        Button button = new Button(getActivity().getApplicationContext());
        button.setLayoutParams(params);
        button.setBackgroundColor(Color.parseColor(backColor));
        button.setText(buttonText);
        button.setOnClickListener(buttonOnclickForAllWeekButton);
        button.setTextSize(8);
        button.setTextColor(Color.parseColor("#000000"));
        button.setId(buttonID);
        params.setMargins(0, marginTop + dpToPx(1), 0, 0);
        arrayListEntity.add(getEntity(jobID, buttonText));

        return button;

    }

    public OnClickListener buttonOnclickForAllWeekButton = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;

            String buttonText = b.getText().toString();
            int position = 0;

            for (int j = 0; j < arrayListEntity.size(); j++) {
                Entity1 itemOne = arrayListEntity.get(j);

                String arryJobRefID = itemOne.JobRefID;
                if (arryJobRefID.equals(buttonText)) {
                    position = j;
                    break;
                }
            }

            Entity1 itemOne1 = arrayListEntity.get(position);
            Toast.makeText(getActivity().getApplicationContext() , "  " + itemOne1.JobRefID , Toast.LENGTH_SHORT).show();

        }
    };

    public static Entity1 getEntity(String jobID, String jobRefID) {
        Entity1 E = new Entity1();
        E.JobIDForButton = jobID;
        E.JobRefID = jobRefID;
        return E;

    }

    public static Entity1 getButton(int layoutView, int buttonTag) {
        Entity1 E = new Entity1();
        E.layoutView = layoutView;
        E.buttonTag = buttonTag;
        return E;

    }

    public static WeekSets getWeekValues(String weekDay, String jobId,
                                         String jobRefId, String tapMargina, String buttonHighta, String backColor) {
        WeekSets W = new WeekSets();
        W.day = weekDay;
        W.jobID = jobId;
        W.jobRefID = jobRefId;
        W.tapMargin = tapMargina;
        W.buttonHight = buttonHighta;
        W.backColor = backColor;

        return W;
    }

    public String getWithAndHigthToButton(int startTime) {

        int time = startTime * 40;

        return Integer.toString(dpToPx(time));

    }

    public String getHightOfButton(int startTime, int endTime) {

        int time = (endTime - startTime) * 40;

        return Integer.toString(dpToPx(time));


    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public String convertWeekDays(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;

    }

    public String convertWeekDaysMouth(String date)
    {
        String formattedDate = null;
        try
        {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM yyyy");
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return formattedDate;

    }

    public int countHour(String hour){

        String[] hourSplit = hour.split(":");

        int result = (Integer.parseInt(hourSplit[0]) - 9);

        return result;
    }

    public class LoadViewsIntoWeekView extends AsyncTask<String, Void, ArrayList<WeekSets>> {
        private Courses courses = new Courses();
        private ArrayList<Planning> planningArray = new ArrayList<Planning>();

        @Override
        protected ArrayList<WeekSets> doInBackground(String... params) {

            ArrayList<WeekSets> weekData = new ArrayList<WeekSets>();
            try {
                //notre appel au WB

                String[] firstDate = convertWeekDaysMouth(NextPreWeekday[6]).split(" ");
                courses.getCallCourses(firstDayOfWeek, lastDayOfWeek, firstDate);
                //intern.getInternShips();
                planningArray.clear();
                planningArray = courses.getPlanning();

                for (Planning temp : planningArray) {
                    String[] dataStart = temp.getDateStart().split("T");
                    String[] dataEnd = temp.getDateEnd().split("T");


                    for(int i=0;i<NextPreWeekday.length;i++){
                        if(NextPreWeekday[i].equals(dataStart[0])){
                            tapMargin = getWithAndHigthToButton(countHour(dataStart[1]));
                            buttonHight = getHightOfButton(countHour(dataStart[1]), countHour(dataEnd[1]));
                            weekData.add(getWeekValues(String.valueOf(i+1), "12", temp.getName()+"\n"+temp.getTeacher(),
                                    tapMargin, buttonHight, temp.getBackColor()));
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


            return weekData;

        }

        @Override
        protected void onPostExecute(ArrayList<WeekSets>  str) {

            relativeLayoutMonDay.removeAllViews();
            relativeLayoutTueDay.removeAllViews();
            relativeLayoutWedDay.removeAllViews();
            relativeLayoutThuDay.removeAllViews();
            relativeLayoutFriDay.removeAllViews();

            try {

                WeekSets weekToDay;
                int length = str.size();
                Log.i("length===>", String.valueOf(length));

                if (length != 0) {
                    for (int k = 0; k < str.size(); k++) {
                        weekToDay = str.get(k);

                        int day = Integer.parseInt(weekToDay.day);
                        switch (day) {

                            case 1:
                                int MonDay = 200;
                                relativeLayoutMonDay
                                        .addView(getButtonToLayout(
                                                Integer.parseInt(weekToDay.buttonHight),
                                                Integer.parseInt(weekToDay.tapMargin),
                                                weekToDay.jobRefID,
                                                weekToDay.jobID, MonDay, weekToDay.backColor));
                                arrayListEButtonId.add(getButton(1, MonDay));
                                MonDay++;
                                break;
                            case 2:
                                int TueDay = 200;
                                relativeLayoutTueDay
                                        .addView(getButtonToLayout(
                                                Integer.parseInt(weekToDay.buttonHight),
                                                Integer.parseInt(weekToDay.tapMargin),
                                                weekToDay.jobRefID,
                                                weekToDay.jobID, TueDay, weekToDay.backColor));
                                arrayListEButtonId.add(getButton(2, TueDay));
                                TueDay++;
                                break;
                            case 3:
                                int WedDay = 200;
                                relativeLayoutWedDay
                                        .addView(getButtonToLayout(
                                                Integer.parseInt(weekToDay.buttonHight),
                                                Integer.parseInt(weekToDay.tapMargin),
                                                weekToDay.jobRefID,
                                                weekToDay.jobID, WedDay, weekToDay.backColor));
                                arrayListEButtonId.add(getButton(3, WedDay));
                                WedDay++;
                                break;
                            case 4:
                                int ThuDay = 200;
                                relativeLayoutThuDay
                                        .addView(getButtonToLayout(
                                                Integer.parseInt(weekToDay.buttonHight),
                                                Integer.parseInt(weekToDay.tapMargin),
                                                weekToDay.jobRefID,
                                                weekToDay.jobID, ThuDay, weekToDay.backColor));
                                arrayListEButtonId.add(getButton(4, ThuDay));
                                ThuDay++;
                                break;
                            case 5:
                                int FriDay = 200;
                                relativeLayoutFriDay
                                        .addView(getButtonToLayout(
                                                Integer.parseInt(weekToDay.buttonHight),
                                                Integer.parseInt(weekToDay.tapMargin),
                                                weekToDay.jobRefID,
                                                weekToDay.jobID, FriDay, weekToDay.backColor));
                                arrayListEButtonId.add(getButton(5, FriDay));
                                FriDay++;
                                break;

                            default:
                                break;
                        }

                    }

                }

            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            try {

                dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
                dialog.setContentView(R.layout.progress_layout);


            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

        }

    }

}