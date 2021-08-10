package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19_tracker.databinding.ActivityStatedetailsBinding;
import com.example.covid19_tracker.model.statemodel;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class statedetails extends AppCompatActivity {

    ActivityStatedetailsBinding binding;
    ArrayList<statemodel>arrayListe;
    private ProgressDialog dialog;
    private static final String urrrl ="https://api.covid19india.org/data.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStatedetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       String statefromintent= getIntent().getStringExtra("state");

        arrayListe = new ArrayList<>();

        // for dialog box
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,
                urrrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*JSONObject jsonObject= response.getJSONObject("statewise");
                    Log.e("jsonobj",jsonObject.toString());*/
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    Log.e("jsonarray",jsonArray.toString());
                    for(int i = 0 ; i < jsonArray.length()-1 ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i+1);
                        String active = jsonObject.getString("active");
                        String confirmed = jsonObject.getString("confirmed");
                        String deaths = jsonObject.getString("deaths");
                        String lastupdatedtime = jsonObject.getString("lastupdatedtime");
                        String recovered = jsonObject.getString("recovered");
                        String statename = jsonObject.getString("state");
                        Log.e("statename",statename);

                        statemodel state = new statemodel(active,confirmed,deaths,lastupdatedtime,recovered,statename);
                        arrayListe.add(state);

                        if(arrayListe.get(i).getState().equals(statefromintent)){
                            int sactive = Integer.parseInt(arrayListe.get(i).getActive());
                            Log.e("active ", String.valueOf(sactive));
                            int sconfirmed = Integer.parseInt(arrayListe.get(i).getConfirmed());
                            int sdeaths = Integer.parseInt(arrayListe.get(i).getDeaths());
                            int srecovered = Integer.parseInt(arrayListe.get(i).getRecovered());

                            binding.statename.setText(statename);
                            binding.stateconfirm.setText(NumberFormat.getInstance().format(sconfirmed));
                            binding.stateactive.setText(NumberFormat.getInstance().format(sactive));
                            binding.statedeath.setText(NumberFormat.getInstance().format(sdeaths));
                            binding.staterecovered.setText(NumberFormat.getInstance().format(srecovered));
                            binding.statelastupdated.setText(lastupdatedtime);

                            //binding.pieChart.addPieSlice(new PieModel("confirmin",confirmin,getResources().getColor(R.color.yellowpie)));

                            binding.statepie.addPieSlice(new PieModel("sconfirmed",sconfirmed,getResources().getColor(R.color.yellowpie)));
                            binding.statepie.addPieSlice(new PieModel("sactive",sactive,getResources().getColor(R.color.bluepie)));
                            binding.statepie.addPieSlice(new PieModel("srecovered",srecovered,getResources().getColor(R.color.greenpie)));
                            binding.statepie.addPieSlice(new PieModel("sdeaths",sdeaths,getResources().getColor(R.color.redpie)));

                            binding.statepie.startAnimation();
                        }
                    }
                    dialog.dismiss();
                    // JSONArray jsonArray = response.getJSONArray("statewise");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}