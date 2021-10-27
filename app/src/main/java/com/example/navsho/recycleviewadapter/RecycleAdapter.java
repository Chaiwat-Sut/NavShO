package com.example.navsho.recycleviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navsho.R;
import com.example.navsho.report.ShipOperation;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{
    private ArrayList<ShipOperation> shipOp;

    public RecycleAdapter(ArrayList<ShipOperation> shipOp){
        this.shipOp = shipOp;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView dateText;
        private TextView statusText;

        public MyViewHolder(View view){
            super(view);
            dateText = view.findViewById(R.id.dateText);
            statusText = view.findViewById(R.id.statusText);
        }
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        String date = shipOp.get(position).getDate();
        String status = shipOp.get(position).getStatus();
        holder.dateText.setText(date);
        holder.statusText.setText(status);
    }

    @Override
    public int getItemCount() {
        return shipOp.size();
    }
}
