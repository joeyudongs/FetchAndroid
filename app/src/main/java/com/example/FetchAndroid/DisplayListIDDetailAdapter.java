package com.example.FetchAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayListIDDetailAdapter extends RecyclerView.Adapter<DisplayListIDDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<PeopleEntity> idEntities;

    public DisplayListIDDetailAdapter(Context context, ArrayList<PeopleEntity> idEntities) {
        this.context = context;
        this.idEntities = idEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.listid_details, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listId.setText(idEntities.get(position).listId);
        holder.id.setText(idEntities.get(position).id);
        holder.name.setText(idEntities.get(position).name);
    }

    @Override
    public int getItemCount() {
        return idEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listId, id, name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listId = itemView.findViewById(R.id.data_listId);
            id = itemView.findViewById(R.id.data_id);
            name = itemView.findViewById(R.id.data_name);
        }
    }
}
