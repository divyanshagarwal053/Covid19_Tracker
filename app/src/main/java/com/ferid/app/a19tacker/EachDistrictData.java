package com.ferid.app.a19tacker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;

import static com.ferid.app.a19tacker.Constants.DISTRICT_ACTIVE;
import static com.ferid.app.a19tacker.Constants.DISTRICT_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.DISTRICT_CONFIRMED_NEW;
import static com.ferid.app.a19tacker.Constants.DISTRICT_DEATH;
import static com.ferid.app.a19tacker.Constants.DISTRICT_DEATH_NEW;
import static com.ferid.app.a19tacker.Constants.DISTRICT_NAME;
import static com.ferid.app.a19tacker.Constants.DISTRICT_RECOVERED;
import static com.ferid.app.a19tacker.Constants.DISTRICT_RECOVERED_NEW;

public class EachDistrictData extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new,
            tv_recovered, tv_recovered_new, tv_death, tv_death_new;

    private String str_districtName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new;

    private PieChart pieChart;

    private MainActivity2 activity = new MainActivity2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_district_data);

        GetIntent();

        Init();

        LoadDistrictData();

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(str_districtName);


    }

    private void LoadDistrictData() {
        //Show dialog
        activity.ShowDialog(this);

        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+ NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+ NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

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

    private void GetIntent() {
        Intent intent = getIntent();
        str_districtName = intent.getStringExtra(DISTRICT_NAME);
        str_confirmed = intent.getStringExtra(DISTRICT_CONFIRMED);
        str_confirmed_new = intent.getStringExtra(DISTRICT_CONFIRMED_NEW);
        str_active = intent.getStringExtra(DISTRICT_ACTIVE);
        str_death = intent.getStringExtra(DISTRICT_DEATH);
        str_death_new = intent.getStringExtra(DISTRICT_DEATH_NEW);
        str_recovered = intent.getStringExtra(DISTRICT_RECOVERED);
        str_recovered_new = intent.getStringExtra(DISTRICT_RECOVERED_NEW);
    }

    private void Init() {
        tv_confirmed = findViewById(R.id.each_district_confirmed);
        tv_confirmed_new = findViewById(R.id.each_district_confirmed_new);
        tv_active = findViewById(R.id.each_district_active);
        tv_active_new = findViewById(R.id.each_district_active_new);
        tv_recovered = findViewById(R.id.each_district_recovered);
        tv_recovered_new = findViewById(R.id.each_district_recovered_new);
        tv_death = findViewById(R.id.each_district_death);
        tv_death_new = findViewById(R.id.each_district_death_new);
        pieChart = findViewById(R.id.each_district_piechart);
        toolbar = findViewById(R.id.EDtoolbar);
    }

}