package com.example.smsreader;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SmsRecyclerViewAdapter extends RecyclerView.Adapter<SmsRecyclerViewAdapter.ViewHolder>{


ArrayList<Sms> smsList;
Context context;
    SmsRecyclerViewAdapter(ArrayList<Sms> smsList, Context context){
        this.smsList = smsList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String checkingWhetherSenderOrReciever= smsList.get(position).type;
        holder.smsTextView.setText(smsList.get(position).number+" : "+ smsList.get(position).body);

        if(checkingWhetherSenderOrReciever.equals("2")){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.relativeLayoutOfEachSms.getLayoutParams();
            params.setMargins(400, 20, 16, 20);
            holder.relativeLayoutOfEachSms.setLayoutParams(params);
            holder.relativeLayoutOfEachSms.setBackgroundResource(R.drawable.rectangle);
            GradientDrawable drawable = (GradientDrawable) holder.relativeLayoutOfEachSms.getBackground();
            drawable.setColor(ContextCompat.getColor(context, R.color.tool_bar));
            holder.smsTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.relativeLayoutOfEachSms.setElevation(1);
        }
        else if(checkingWhetherSenderOrReciever.equals("1")){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.relativeLayoutOfEachSms.getLayoutParams();
            params.setMargins(16, 20, 400, 20);
            holder.relativeLayoutOfEachSms.setLayoutParams(params);
            holder.relativeLayoutOfEachSms.setBackgroundResource(R.drawable.rectangle);
            GradientDrawable drawable = (GradientDrawable) holder.relativeLayoutOfEachSms.getBackground();
            drawable.setColor(ContextCompat.getColor(context,
                    R.color.text_background));
            holder.smsTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.relativeLayoutOfEachSms.setElevation(1);
        }

    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayoutOfEachSms;
        TextView smsTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            smsTextView =itemView.findViewById(R.id.body);
            relativeLayoutOfEachSms =itemView.findViewById(R.id.reletivelayout);
        }
    }
    public int updatingAlreadyExsistingSmsListWithNewSms(ArrayList<Sms> newSmsList){
        if(newSmsList.size()> smsList.size() && smsList.size()>0){
            int difference = newSmsList.size()- smsList.size();
            smsList.clear();
            smsList.addAll(newSmsList);
            notifyDataSetChanged();
            return difference;
        }
        return 0;
    }
}
