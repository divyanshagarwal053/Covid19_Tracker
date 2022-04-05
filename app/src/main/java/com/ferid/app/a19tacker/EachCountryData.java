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

import static com.ferid.app.a19tacker.Constants.COUNTRY_ACTIVE;
import static com.ferid.app.a19tacker.Constants.COUNTRY_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.COUNTRY_DECEASED;
import static com.ferid.app.a19tacker.Constants.COUNTRY_NAME;
import static com.ferid.app.a19tacker.Constants.COUNTRY_NEW_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.COUNTRY_NEW_DECEASED;
import static com.ferid.app.a19tacker.Constants.COUNTRY_RECOVERED;
import static com.ferid.app.a19tacker.Constants.COUNTRY_TESTS;

public class EachCountryData extends AppCompatActivity {

    Toolbar ECtoolbar;
    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_death, tv_death_new,
            tv_recovered, tv_recovered_new, tv_tests;

    private String str_countryName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new, str_tests;
    private PieChart pieChart;
    private MainActivity2 activity = new MainActivity2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_country_data);

        GetIntent();

        Init();

        LoadCountryData();


        setSupportActionBar(ECtoolbar);

        getSupportActionBar().setTitle(str_countryName);


    }

    private void Init() {
        tv_confirmed = findViewById(R.id.activity_each_country_data_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_each_country_data_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_each_country_data_active_textView);
        tv_active_new = findViewById(R.id.activity_each_country_data_active_new_textView);
        tv_recovered = findViewById(R.id.activity_each_country_data_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_each_country_data_recovered_new_textView);
        tv_death = findViewById(R.id.activity_each_country_data_death_textView);
        tv_death_new = findViewById(R.id.activity_each_country_data_death_new_textView);
        tv_tests = findViewById(R.id.activity_each_country_data_tests_textView);
        pieChart = findViewById(R.id.activity_each_country_data_piechart);
    }

    private void LoadCountryData() {
        //Show dialog
        activity.ShowDialog(this);

        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                /*int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));*/
                tv_active_new.setText("N/A");

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                //tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));
                tv_recovered_new.setText("N/A");

                tv_tests.setText(NumberFormat.getInstance().format(Integer.parseInt(str_tests)));

                //setting piechart
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#007afe")));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#08a045")));
                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));
                pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(str_confirmed), Color.parseColor("#FFA726")));

                pieChart.startAnimation();

                activity.DismissDialog();
            }
        },1000);

    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_countryName = intent.getStringExtra(COUNTRY_NAME);
        str_confirmed = intent.getStringExtra(COUNTRY_CONFIRMED);
        str_active = intent.getStringExtra(COUNTRY_ACTIVE);
        str_death = intent.getStringExtra(COUNTRY_DECEASED);
        str_recovered = intent.getStringExtra(COUNTRY_RECOVERED);
        str_confirmed_new = intent.getStringExtra(COUNTRY_NEW_CONFIRMED);
        str_death_new = intent.getStringExtra(COUNTRY_NEW_DECEASED);
        str_tests = intent.getStringExtra(COUNTRY_TESTS);
        ECtoolbar = findViewById(R.id.ECtoolbar);
    }

}