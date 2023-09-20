package com.example.FetchAndroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListIDActivity extends AppCompatActivity implements ListIDAdapter.OnIDRecyclerViewClickListener {
    private RecyclerView recyclerView;
    public  ArrayList<String> listIds = new ArrayList<>();
    public  ArrayList<JSONArray> groupedList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listid);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        String url ="https://fetch-hiring.s3.amazonaws.com/hiring.json";
        new GetDataTask().execute(url);
    }
    private class GetDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String urlStr = strings[0];
            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<JSONObject> jsonValues = new ArrayList<>();

                    createListIds(jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonValues.add(jsonArray.getJSONObject(i));
                    }

                    Collections.sort(listIds);
                    JSONArray sortedJsonArray = sortJsonArray(jsonArray, "name");
                    groupDataByListId(sortedJsonArray);

                    ListIDAdapter listIdAdapter = new ListIDAdapter(ListIDActivity.this, listIds, ListIDActivity.this);

                    recyclerView.setLayoutManager(new LinearLayoutManager(ListIDActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(listIdAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ListIDActivity.this, "Data Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createListIds(JSONArray jsonArray) throws JSONException {
        Set<String> uniqueListIds = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String listId = jsonObject.getString("listId");
            uniqueListIds.add(listId);
        }
        listIds.addAll(uniqueListIds);
    }
    private static final Pattern PATTERN = Pattern.compile("(?<nonDigit>\\D*)(?<digit>\\d*)");
    public JSONArray sortJsonArray(JSONArray jsonArray, String type) throws JSONException {
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getJSONObject(i));
        }

        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = a.optString(type, "");
                String valB = b.optString(type, "");

                Matcher m1 = PATTERN.matcher(valA);
                Matcher m2 = PATTERN.matcher(valB);

                while (m1.find() && m2.find()) {
                    int nonDigitCompare = m1.group(1).compareTo(m2.group(1));
                    if (nonDigitCompare != 0) {
                        return nonDigitCompare;
                    }

                    String numA = m1.group(2);
                    String numB = m2.group(2);

                    if (numA.isEmpty()) {
                        return numB.isEmpty() ? 0 : -1;
                    } else if (numB.isEmpty()) {
                        return 1;
                    }

                    BigInteger n1 = new BigInteger(numA);
                    BigInteger n2 = new BigInteger(numB);
                    int numberCompare = n1.compareTo(n2);

                    if (numberCompare != 0) {
                        return numberCompare;
                    }
                }

                return m1.hitEnd() && m2.hitEnd() ? 0 : (m1.hitEnd() ? -1 : 1);
            }
        });

        JSONArray sortedJsonArray = new JSONArray(list);

        return sortedJsonArray;
    }
    private void groupDataByListId(JSONArray sortedJsonArray) throws JSONException {
        for (String listId : listIds) {
            JSONArray listIDJsonArray = new JSONArray();

            for (int i = 0; i < sortedJsonArray.length(); i++) {
                JSONObject jsonObject = sortedJsonArray.getJSONObject(i);
                String idInList = jsonObject.getString("listId");
                String nameInList = jsonObject.optString("name", "");

                if (listId.equals(idInList) && !nameInList.trim().isEmpty()) {
                    listIDJsonArray.put(jsonObject);
                }
            }

            groupedList.add(listIDJsonArray);
        }
    }

    @Override
    public void OnClick(int pos) {
        JSONArray jsonArray = groupedList.get(pos);

        Intent intent = new Intent(this, DisplayListIDDetailActivity.class);
        intent.putExtra("data",jsonArray.toString());
        startActivity(intent);
    }
}
