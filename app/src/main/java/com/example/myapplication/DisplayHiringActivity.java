package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayHiringActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        String jsonArray = getIntent().getStringExtra("data");

        try {
            ArrayList<HiringEntity> arrayList = new ArrayList<>();
            JSONArray array = new JSONArray(jsonArray);
            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                HiringEntity idEntity = new HiringEntity(jsonObject.getString("listId"), jsonObject.getString("id"), jsonObject.getString("name"));
                arrayList.add(idEntity);
            }

            //ViewIDAdapter viewIDAdapter = new ViewIDAdapter(this, arrayList);
            //recyclerView.setLayoutManager(new LinearLayoutManager(ViewIDActivity.this, LinearLayoutManager.VERTICAL,false));
            //recyclerView.setAdapter(viewIDAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
