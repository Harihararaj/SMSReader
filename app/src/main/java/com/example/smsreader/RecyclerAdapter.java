package com.example.smsreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
//run on ui thread
ArrayList<Sms> sms;

    RecyclerAdapter(ArrayList<Sms> sms){
        this.sms=sms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(sms.get(position).number);
        holder.textView2.setText(sms.get(position).body);
    }

    @Override
    public int getItemCount() {
        return sms.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.number);
            textView2=itemView.findViewById(R.id.body);
        }
    }
    public void setter(ArrayList<Sms> newSms){

        sms.clear();
        sms.addAll(newSms);
        notifyDataSetChanged();
    }
}
