package com.example.FetchAndroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayListIDDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_listid_detail);

        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        String jsonArray = getIntent().getStringExtra("data");

        try {
            ArrayList<PeopleEntity> arrayList = new ArrayList<>();
            JSONArray array = new JSONArray(jsonArray);
            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                PeopleEntity peopleEntity = new PeopleEntity(jsonObject.getString("listId"), jsonObject.getString("id"), jsonObject.getString("name"));
                arrayList.add(peopleEntity);
            }

            DisplayListIDDetailAdapter displayListIDDetailAdapter = new DisplayListIDDetailAdapter(this, arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(DisplayListIDDetailActivity.this, LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(displayListIDDetailAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}