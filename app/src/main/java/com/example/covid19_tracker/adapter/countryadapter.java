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
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class countryadapter extends RecyclerView.Adapter<countryadapter.countryviewholder> {

    private ArrayList<countrydata> arrayList;
    private Context context;

    public countryadapter(ArrayList<countrydata> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public countryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_country,parent,false);

        return new countryviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull countryviewholder holder, int position) {

        countrydata countrydataa = arrayList.get(position);

        holder.countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(countrydataa.getCases())));
        holder.countryname.setText(countrydataa.getCountry());
        holder.sno.setText(String.valueOf(position+1));
        Picasso.get().load(countrydataa.getCountryInfo()).placeholder(R.drawable.ic_launcher_foreground).into(holder.flagimg);

       /* *//*Glide.with(context)
                .load(arrayList.get(position).getCountryInfo())
                .placeholder(R.drawable.coronavirus)
                .into(holder.flagimg);*//*
        Picasso.get().load(countrydataa.getCountryInfo()).placeholder(R.drawable.ic_launcher_foreground).into(holder.flagimg);*/
        holder.countryoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("country", countrydataa.getCountry());
                context.startActivity(intent);
            }
        });

    }
    public void filterlist(ArrayList<countrydata> filterlist){
        arrayList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class countryviewholder extends RecyclerView.ViewHolder{
        LinearLayout countryoverview;
        ImageView flagimg;
        TextView sno, countryname, countrycases;

        public countryviewholder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            countryname = itemView.findViewById(R.id.countryname);
            countrycases = itemView.findViewById(R.id.cases);
            flagimg = itemView.findViewById(R.id.countryimage);
            countryoverview = itemView.findViewById(R.id.countryoverview);

        }
    }
}
