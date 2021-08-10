package com.example.covid19_tracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19_tracker.MainActivity;
import com.example.covid19_tracker.R;
import com.example.covid19_tracker.model.countrydata;
import com.example.covid19_tracker.model.statemodel;
import com.example.covid19_tracker.stateactivity;
import com.example.covid19_tracker.statedetails;

import java.text.NumberFormat;
import java.util.ArrayList;

public class stateadapter extends RecyclerView.Adapter<stateadapter.stateviewholer>{

    private ArrayList<statemodel> arrayList;
    private Context context;

    public stateadapter(ArrayList<statemodel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public stateviewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_country,parent,false);

        return new stateviewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stateviewholer holder, int position) {
        statemodel stateadapterr = arrayList.get(position);
        holder.countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(stateadapterr.getConfirmed())));
        holder.countryname.setText(stateadapterr.getState());
        holder.sno.setText(String.valueOf(position+1));
        holder.flagimg.setVisibility(View.INVISIBLE);

        holder.countryoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, statedetails.class);
                intent.putExtra("state", stateadapterr.getState());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class stateviewholer extends RecyclerView.ViewHolder{
        LinearLayout countryoverview;
        ImageView flagimg;
        TextView sno, countryname, countrycases;

        public stateviewholer(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            countryname = itemView.findViewById(R.id.countryname);
            countrycases = itemView.findViewById(R.id.cases);
            flagimg = itemView.findViewById(R.id.countryimage);
            countryoverview = itemView.findViewById(R.id.countryoverview);
        }
    }
}

