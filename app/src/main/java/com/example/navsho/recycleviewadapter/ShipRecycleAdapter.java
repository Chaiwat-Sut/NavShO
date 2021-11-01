package com.example.navsho.recycleviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navsho.R;
import com.example.navsho.alluseclass.PatrolVessel;

import java.util.ArrayList;

public class ShipRecycleAdapter extends RecyclerView.Adapter<ShipRecycleAdapter.PatrolViewHolder> {
    private ArrayList<PatrolVessel> vesselList;
    private OnShipListener monShipListener;

    public ShipRecycleAdapter(ArrayList<PatrolVessel> vesselList, OnShipListener onShipListener){
        this.vesselList = vesselList;
        this.monShipListener = onShipListener;
    }

    public class PatrolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView vesselNameText;
        private TextView vesIDTextView;
        private ImageView imageShipView;
        OnShipListener onShipListener;

        public PatrolViewHolder(View view,OnShipListener onShipListener){
            super(view);
            vesselNameText = view.findViewById(R.id.vesselNameText);
            vesIDTextView = view.findViewById(R.id.vesIDTextView);
            imageShipView = view.findViewById(R.id.modifyImage);
            this.onShipListener = onShipListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onShipListener.onShipListener(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PatrolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ship_recycle_layout,parent,false);
        return new PatrolViewHolder(itemView,monShipListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PatrolViewHolder holder, int position) {
        String vesselName = vesselList.get(position).getVesName();
        holder.vesselNameText.setText(vesselName);

        String vesID = vesselList.get(position).getVesID();
        holder.vesIDTextView.setText(vesID);

        int vesPath = vesselList.get(position).getPicPath();
        holder.imageShipView.setImageResource(vesPath);
    }

    @Override
    public int getItemCount() {
        return vesselList.size();
    }

    public interface OnShipListener{
        void onShipListener(int position);
    }
}
