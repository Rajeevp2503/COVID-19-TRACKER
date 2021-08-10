package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19_tracker.adapter.stateadapter;
import com.example.covid19_tracker.databinding.ActivityMainBinding;
import com.example.covid19_tracker.databinding.ActivityStateactivityBinding;
import com.example.covid19_tracker.model.statemodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class stateactivity extends AppCompatActivity {
    ActivityStateactivityBinding binding;
    ArrayList<statemodel>statedata;
    private ProgressDialog dialog;
    private static final String urlls ="https://api.covid19india.org/data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStateactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        statedata =new ArrayList<>();

        stateadapter stateadapterr = new stateadapter(statedata,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.statewiserecview.setLayoutManager(layoutManager);

        binding.statewiserecview.setAdapter(stateadapterr);

        dialog = new ProgressDialog(this);
        dialog.setMessage("loadiing.....");
        dialog.setCancelable(false);
        dialog.show();



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,
                urlls,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*JSONObject jsonObject= response.getJSONObject("statewise");
                    Log.e("jsonobj",jsonObject.toString());*/
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    Log.e("jsonarray",jsonArray.toString());
                    for(int i = 0 ; i < jsonArray.length()-1; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i+1);
                        String active = jsonObject.getString("active");
                        String confirmed = jsonObject.getString("confirmed");
                        String deaths = jsonObject.getString("deaths");
                        String lastupdatedtime = jsonObject.getString("lastupdatedtime");
                        String recovered = jsonObject.getString("recovered");
                        String statename = jsonObject.getString("state");
                        Log.e("statename",statename);

                        statemodel state = new statemodel(active,confirmed,deaths,lastupdatedtime,recovered,statename);
                        statedata.add(state);
                        int sactive = Integer.parseInt(statedata.get(i).getActive());
                        Log.e("active ", String.valueOf(sactive));

                        int sconfirmed = Integer.parseInt(statedata.get(i).getConfirmed());
                        int sdeaths = Integer.parseInt(statedata.get(i).getDeaths());
                        int srecovered = Integer.parseInt(statedata.get(i).getRecovered());
                    }
                    stateadapterr.notifyDataSetChanged();
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