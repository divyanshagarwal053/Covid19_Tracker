package com.ferid.app.a19tacker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;

import static com.ferid.app.a19tacker.Constants.STATE_ACTIVE;
import static com.ferid.app.a19tacker.Constants.STATE_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.STATE_CONFIRMED_NEW;
import static com.ferid.app.a19tacker.Constants.STATE_DEATH;
import static com.ferid.app.a19tacker.Constants.STATE_DEATH_NEW;
import static com.ferid.app.a19tacker.Constants.STATE_LAST_UPDATE;
import static com.ferid.app.a19tacker.Constants.STATE_NAME;
import static com.ferid.app.a19tacker.Constants.STATE_RECOVERED;
import static com.ferid.app.a19tacker.Constants.STATE_RECOVERED_NEW;


public class EachStateData extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_death, tv_death_new,
            tv_recovered, tv_recovered_new, tv_lastupdatedate, tv_dist;

    private String str_stateName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new, str_lastupdatedate;

    private PieChart pieChart;

    private LinearLayout lin_district;

    private MainActivity2 activity = new MainActivity2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_state_data);

        GetIntent();

        Init();

        LoadStateData();

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(str_stateName);

        lin_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(EachStateDataActivity.this, "Select District of "+str_stateName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EachStateData.this, DistrictData.class);
                intent.putExtra(STATE_NAME, str_stateName);
                startActivity(intent);
            }
        });

    }

    private void LoadStateData() {
        //Show dialog
        activity.ShowDialog(this);
        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

                String formatDate = activity.FormatDate(str_lastupdatedate, 0);
                tv_lastupdatedate.setText(formatDate);

                tv_dist.setText("District data of "+str_stateName);

                //setting piechart
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#007afe")));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#08a045")));
                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));
                pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(str_confirmed), Color.parseColor("#29B6F6")));

                pieChart.startAnimation();

                activity.DismissDialog();
            }
        },1000);

    }

    private void Init() {
        tv_confirmed = findViewById(R.id.each_state_confirmed);
        tv_confirmed_new = findViewById(R.id.each_state_confirmed_new);
        tv_active = findViewById(R.id.each_state_active);
        tv_active_new = findViewById(R.id.each_state_active_new);
        tv_recovered = findViewById(R.id.each_state_recovered);
        tv_recovered_new = findViewById(R.id.each_state_recovered_new);
        tv_death = findViewById(R.id.each_state_death);
        tv_death_new = findViewById(R.id.each_state_death_new);
        tv_lastupdatedate = findViewById(R.id.each_state_lastupdate);
        tv_dist = findViewById(R.id.each_state_district_data_title);
        pieChart = findViewById(R.id.each_state_piechart);
        lin_district = findViewById(R.id.each_state_lin);
        toolbar = findViewById(R.id.EStoolbar);
    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_stateName = intent.getStringExtra(STATE_NAME);
        str_confirmed = intent.getStringExtra(STATE_CONFIRMED);
        str_confirmed_new = intent.getStringExtra(STATE_CONFIRMED_NEW);
        str_active = intent.getStringExtra(STATE_ACTIVE);
        str_death = intent.getStringExtra(STATE_DEATH);
        str_death_new = intent.getStringExtra(STATE_DEATH_NEW);
        str_recovered = intent.getStringExtra(STATE_RECOVERED);
        str_recovered_new = intent.getStringExtra(STATE_RECOVERED_NEW);
        str_lastupdatedate = intent.getStringExtra(STATE_LAST_UPDATE);
    }

}