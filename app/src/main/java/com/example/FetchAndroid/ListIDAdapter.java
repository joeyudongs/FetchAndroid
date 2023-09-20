package com.example.FetchAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListIDAdapter extends RecyclerView.Adapter<ListIDAdapter.ViewHolder>{
    ArrayList<String> listIds;
    Context context;
    OnIDRecyclerViewClickListener onIDRecyclerViewClickListener;

    public ListIDAdapter(Context context, ArrayList<String> listIds, OnIDRecyclerViewClickListener onIDRecyclerViewClickListener){
        this.context = context;
        this.listIds = listIds;
        this.onIDRecyclerViewClickListener = onIDRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.listid_group, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.textView.setText(listIds.get(position));
         final int currentPosition = holder.getAdapterPosition();
         holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIDRecyclerViewClickListener.OnClick(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_id_name);
            button = itemView.findViewById(R.id.displayListID);
        }
    }
    public interface OnIDRecyclerViewClickListener {
        void OnClick(int pos);
    }
}
