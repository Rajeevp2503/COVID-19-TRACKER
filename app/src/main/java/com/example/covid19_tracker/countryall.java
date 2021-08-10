package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19_tracker.adapter.countryadapter;
import com.example.covid19_tracker.databinding.ActivityCountryallBinding;
import com.example.covid19_tracker.model.countrydata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class countryall extends AppCompatActivity {
    ActivityCountryallBinding binding;
    private ProgressDialog dialog;
    public   ArrayList<countrydata> arrayListcountries;
    private  countryadapter countryadapter;
  // public static ArrayList<countrydata>arrayLists;
    private static final  String urls ="https://corona.lmao.ninja/v2/countries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountryallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayListcountries = new ArrayList<>();


        countryadapter = new countryadapter(arrayListcountries,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recview.setLayoutManager(layoutManager);

       // binding.recview.setLayoutManager(new LinearLayoutManager(this));

        binding.recview.setAdapter(countryadapter);


        RequestQueue requestQueue = Volley.newRequestQueue(this);




        dialog = new ProgressDialog(this);
        dialog.setMessage("loadiing.....");
        dialog.setCancelable(false);
        dialog.show();


        // Initialize a new JsonArrayRequest instance

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                Request.Method.GET,
                urls,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // JSONArray jsonArray = new JSONArray(response);
                            for(int i=0 ; i<response.length(); i++){

                                JSONObject jsonObject= response.getJSONObject(i);

                                String lastupdated =  jsonObject.getString("updated");
                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todaycases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todaydeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String todayrecovered = jsonObject.getString("todayRecovered");
                                String active = jsonObject.getString("active");
                                // String critical = jsonObject.getString("critical");
                                //jsonObject.getJSONObject("countryInfo");
                                JSONObject jsonObject1 = jsonObject.getJSONObject("countryInfo");
                                String flagurl = jsonObject1.getString("flag");
                                Log.e("countryall", flagurl.toString());

                                countrydata data = new countrydata(lastupdated,countryName,cases,todaycases,deaths,todaydeaths,recovered,todayrecovered,active,flagurl);
                                Log.d("sss", data.getCountry().toString());
                                arrayListcountries.add(data);

                            }
                            countryadapter.notifyDataSetChanged();
                            dialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred


                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });


    }

    private void filter(String text) {
        ArrayList<countrydata> filterList =new ArrayList<>();
        for(countrydata items : arrayListcountries){
            if(items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filterList.add(items);
            }
        }
        countryadapter.filterlist(filterList);
    }


}
