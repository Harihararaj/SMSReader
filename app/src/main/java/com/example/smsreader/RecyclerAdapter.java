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
    ArrayList<Sms> sms=new ArrayList<>();
    TextView textView1,textView2;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        textView1.setText(sms.get(position).number);
        textView2.setText(sms.get(position).body);
    }

    @Override
    public int getItemCount() {
        return sms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.number);
            textView2=itemView.findViewById(R.id.body);
        }
    }
    public void setter(ArrayList<Sms> sms){
        this.sms=sms;
    }
}
