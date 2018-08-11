package com.pheramor.registerationapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.models.Detail;

import java.util.ArrayList;
import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>{
    private Context mContext;
    private List<Detail> details;

    public SummaryAdapter(Context mContext) {
        this.mContext = mContext;
        details = new ArrayList<>();
    }

    public void setData(List<Detail> data) {
        this.details = data;
    }

    public class SummaryViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView itemTextView;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            itemTextView = itemView.findViewById(R.id.itemTextView);
        }
    }

    @NonNull
    @Override
    public SummaryAdapter.SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.summary_item, viewGroup, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryAdapter.SummaryViewHolder holder, int i) {
        Detail detail = details.get(i);
        holder.itemTextView.setText(detail.getItem());
        holder.titleTextView.setText(detail.getTitle());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
