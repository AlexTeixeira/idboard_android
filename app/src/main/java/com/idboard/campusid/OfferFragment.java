package com.idboard.campusid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import json.InternShipsCall;

import org.json.JSONException;


import lib.InternShipAsyncCall;
import lib.InternShips;
import lib.TabsInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OfferFragment extends Fragment implements TabsInterface {


    private getIntern internTask;

    private ArrayList<InternShips> internShip = new ArrayList<InternShips>();

    private class getIntern extends AsyncTask<String, Void, ArrayList<InternShips>> implements TabsInterface{

        private InternShipsCall intern = new InternShipsCall();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<InternShips> doInBackground(String... params) {
            ArrayList<InternShips> LocalInternShip = new ArrayList<InternShips>();
            // TODO Auto-generated method stub
            try {
                intern.getInternShipsJson();
                LocalInternShip =  intern.getInternShips();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return LocalInternShip;
        }

        public InternShipsCall getIntern(){
            return intern;
        }



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        internTask = new getIntern();
        try {
            internShip = internTask.execute().get();
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
        View V = inflater.inflate(R.layout.tab_offer, container, false);

        final Context myContext = getActivity().getApplicationContext();

        ListView listview = (ListView) V.findViewById(R.id.listViewOffer);

        ArrayList<String> list = new ArrayList<String>();


        for (int i = 0; i < internShip.size(); ++i) {
            list.add(internShip.get(i).getTitle() + " - " + internShip.get(i).getCompanyName());
            //list.put(internShip.get(i).getTitle() + " - " + internShip.get(i).getCompanyName(), internShip.get(i).getDuration());
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(V.getContext(), android.R.layout.simple_list_item_1, list);
        listview.setAdapter(listAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d("position =>",internShip.get(position).getReference());

                InternShipAsyncCall intershipAsyncCall = new InternShipAsyncCall();
                intershipAsyncCall.setRef(Integer.parseInt(internShip.get(position).getReference()));
                lib.InternShip intern = null;
                try {
                    intern = intershipAsyncCall.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if(intern != null){
                    AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(getActivity());
                    LicenseDialog.setTitle(intern.getTitle() + " - " + intern.getCompany());
                    LicenseDialog.setMessage("Duration : " + intern.getDuration() + "\n\n" +
                                    "Description : \n\n" + intern.getMissionSummary()

                    );
                    LicenseDialog.show();
                }else{
                    AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(getActivity());
                    LicenseDialog.setTitle("Erreur");
                    LicenseDialog.setMessage("Erreur lors de la récupération de la description de l'offre");
                    LicenseDialog.show();
                }

            }
        });

        return V;

    }
}