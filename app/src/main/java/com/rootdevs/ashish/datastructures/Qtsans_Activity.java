package com.rootdevs.ashish.datastructures;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.QTS_Adapter;
import Model.Qts_list;

public class Qtsans_Activity extends AppCompatActivity {
    String jsonurl = "https://learncodeonline.in/api/android/datastructure.json";
    private RecyclerView recyclerView;
    private List<Qts_list> listItems;
    private QTS_Adapter adapter;
    String TAG = "Check ADAPTER", id;
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtsans_);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        listItems = new ArrayList<>();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        if(isNetworkAvailable()){
            loadRecyclerViewData();
        }
        else Toast.makeText(Qtsans_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable()){
                    listItems.clear();
                    loadRecyclerViewData();
                    mSwipeRefresh.setRefreshing(false);
                }

                else
                    Toast.makeText(Qtsans_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onBackPressed();
            }
        });


        final StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, response);
                        try {
                            String temp = "";
                            JSONObject o = new JSONObject(response);
                            JSONArray jsonArray = o.getJSONArray("questions");
                            for(int i=0; i<jsonArray.length(); i++){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(!(jsonObject.getString("question").equals(temp))){

                                    Qts_list item = new Qts_list(jsonObject.getString("question"), jsonObject.getString("Answer"));
                                    Log.v("DATA IS: ", jsonObject.getString("question") + jsonObject.getString("Answer"));
                                    listItems.add(item);
                                    temp = jsonObject.getString("question");
                                    progressDialog.dismiss();
                                    adapter = new QTS_Adapter(listItems, getApplicationContext());
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("EXCEPTION", "EXCEPTIONNNNNNNN");
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void open_link(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://courses.learncodeonline.in"));
        startActivity(browserIntent);
    }




}



