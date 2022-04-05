package com.ferid.app.a19tacker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ferid.app.a19tacker.Adapters.StateAdapter;
import com.ferid.app.a19tacker.Adapters.StateDistrictAdapter;
import com.ferid.app.a19tacker.Models.StateDistrictModel;
import com.ferid.app.a19tacker.Models.StateModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateDistrictData extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    LinearLayout contentView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private RecyclerView rv_state;
    private StateDistrictAdapter stateDistrictAdapter;
    private ArrayList<StateDistrictModel> stateDistrictModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;


    private String str_state, str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_lastupdatedate;

    private MainActivity2 activity = new MainActivity2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_district_data);

        Init();

        //Fetch state data
        FetchStateData();

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Select State");


        //Setting swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchStateData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Search
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });

    }

    private void Filter(String text) {
        ArrayList<StateDistrictModel> filteredList = new ArrayList<>();
        for (StateDistrictModel item : stateDistrictModelArrayList) {
            if (item.getState().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        stateDistrictAdapter.filterList(filteredList, text);
    }

    private void FetchStateData() {
        //Show progress dialog
        activity.ShowDialog(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://api.covid19india.org/data.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            stateDistrictModelArrayList.clear();

                            for (int i = 1; i < jsonArray.length() ; i++){
                                JSONObject statewise = jsonArray.getJSONObject(i);

                                //After fetching, storing the data into strings
                                str_state = statewise.getString("state");

                                str_confirmed = statewise.getString("confirmed");
                                str_confirmed_new = statewise.getString("deltaconfirmed");

                                str_active = statewise.getString("active");

                                str_death = statewise.getString("deaths");
                                str_death_new = statewise.getString("deltadeaths");

                                str_recovered = statewise.getString("recovered");
                                str_recovered_new = statewise.getString("deltarecovered");
                                str_lastupdatedate = statewise.getString("lastupdatedtime");

                                //Creating an object of our statewise model class and passing the values in the constructor
                                StateDistrictModel stateDistrictModel = new StateDistrictModel(str_state, str_confirmed, str_confirmed_new, str_active,
                                        str_death, str_death_new, str_recovered, str_recovered_new, str_lastupdatedate);
                                //adding data to our arraylist
                                stateDistrictModelArrayList.add(stateDistrictModel);
                            }

                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    stateDistrictAdapter.notifyDataSetChanged();
                                    activity.DismissDialog();
                                }
                            }, 1000);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    private void Init() {
        swipeRefreshLayout = findViewById(R.id.state_district_swipe_refresh_layout);
        et_search = findViewById(R.id.state_district_search);

        rv_state = findViewById(R.id.state_district_recyclerview);
        rv_state.setHasFixedSize(true);
        rv_state.setLayoutManager(new LinearLayoutManager(this));

        stateDistrictModelArrayList = new ArrayList<>();
        stateDistrictAdapter = new StateDistrictAdapter(StateDistrictData.this, stateDistrictModelArrayList);
        rv_state.setAdapter(stateDistrictAdapter);
        toolbar = findViewById(R.id.SDtoolbar);
//        SdrawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.navigation_view);
        contentView = findViewById(R.id.content);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}