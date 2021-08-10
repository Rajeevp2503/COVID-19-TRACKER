package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.covid19_tracker.databinding.ActivityMainBinding;
import com.example.covid19_tracker.model.countrydata;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ProgressDialog dialog;
    public   ArrayList<countrydata> arrayList;
    private static final  String url ="https://corona.lmao.ninja/v2/countries";

    String country ="India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayList = new ArrayList<>();

        // for dialog box
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();

        if(getIntent().getStringExtra("country")!=null){
            country= getIntent().getStringExtra("country");
            //for visibiity of other country
            if(country !="India"){
                binding.statewise.setVisibility(View.INVISIBLE);
            }else
                binding.statewise.setVisibility(View.VISIBLE);
            }


        binding.countrynamein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // arrayList = new ArrayList<>();
                //ArrayList<Object> object = new ArrayList<Object>();

                startActivity(new Intent(MainActivity.this,countryall.class));
            }
        });

        // for state wise data
        binding.statewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,stateactivity.class));
            }
        });

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Initialize a new JsonArrayRequest instance

                JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                Request.Method.GET,
                url,
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
                                Log.e("flag", flagurl.toString());

                                countrydata data = new countrydata(lastupdated,countryName,cases,todaycases,deaths,todaydeaths,recovered,todayrecovered,active,flagurl);
                                Log.d("raj", data.getCountry().toString());
                                arrayList.add(data);

                               if(arrayList.get(i).getCountry().equals(country)){
                                   //for visibiity of other country
                                   if(country!="India"){
                                       binding.statewise.setVisibility(View.INVISIBLE);
                                   }else
                                       binding.statewise.setVisibility(View.VISIBLE);
                                   binding.countrynamein.setText(country);

                                   Log.e("rajeev" , arrayList.get(i).getActive().toString());

                                     int confirmin= Integer.parseInt(arrayList.get(i).getCases());

                                   //  String lastupdatedin = arrayList.get(i).getUpdated();
                                   //Log.e("updated ", lastupdatedin);
                                     int activein =  Integer.parseInt(arrayList.get(i).getActive());

                                     int recoveredin =Integer.parseInt(arrayList.get(i).getRecovered());
                                     int deathin = Integer.parseInt(arrayList.get(i).getDeaths());
                                     int todaycase = Integer.parseInt(arrayList.get(i).getTodayCases());
                                     String flagurls = arrayList.get(i).getCountryInfo();


                                  //   binding.active.setText(String.valueOf(activein));
                                   binding.active.setText(NumberFormat.getInstance().format(activein));
                                   //for date changing of updated time
                                   datechanger(arrayList.get(i).getUpdated());
                                     binding.recovered.setText(NumberFormat.getInstance().format(recoveredin));
                                     binding.death.setText(NumberFormat.getInstance().format(deathin));
                                     binding.confirm.setText(NumberFormat.getInstance().format(confirmin));
                                     binding.today.setText(NumberFormat.getInstance().format(todaycase));

                                     binding.todaydeath.setText("(-"+todaycases+")");
                                     binding.todayrecovered.setText("(+"+todayrecovered+")");
                                    // binding.flag.
                                    Glide
                                           .with(MainActivity.this)
                                           .load(flagurls)
                                           .centerCrop()
                                           .placeholder(R.drawable.coronavirus)
                                           .into(binding.flag);

                                    // for data loading in pie chart
                                    binding.pieChart.addPieSlice(new PieModel("confirmin",confirmin,getResources().getColor(R.color.yellowpie)));
                                    binding.pieChart.addPieSlice(new PieModel("active",activein,getResources().getColor(R.color.bluepie)));
                                    binding.pieChart.addPieSlice(new PieModel("recovered",recoveredin,getResources().getColor(R.color.greenpie)));
                                    binding.pieChart.addPieSlice(new PieModel("death",deathin,getResources().getColor(R.color.redpie)));
                                   // for animation
                                    binding.pieChart.startAnimation();


                               }

                            }
                            dialog.dismiss();

                        }
                        catch (JSONException e) {
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

    }

    private void datechanger(String time) {
        String x =time;

        DateFormat formatter = new SimpleDateFormat("MMM , dd , yyyy");

        long milliSeconds= Long.parseLong(x);
        System.out.println(milliSeconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        System.out.println(formatter.format(calendar.getTime()));
        binding.date.setText("Last updated at "+ formatter.format(calendar.getTime()));
    }

}