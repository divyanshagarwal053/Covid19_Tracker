package com.ferid.app.a19tacker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ferid.app.a19tacker.EachDistrictData;
import com.ferid.app.a19tacker.Models.DistrictModel;
import com.ferid.app.a19tacker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ferid.app.a19tacker.Constants.DISTRICT_ACTIVE;
import static com.ferid.app.a19tacker.Constants.DISTRICT_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.DISTRICT_CONFIRMED_NEW;
import static com.ferid.app.a19tacker.Constants.DISTRICT_DEATH;
import static com.ferid.app.a19tacker.Constants.DISTRICT_DEATH_NEW;
import static com.ferid.app.a19tacker.Constants.DISTRICT_NAME;
import static com.ferid.app.a19tacker.Constants.DISTRICT_RECOVERED;
import static com.ferid.app.a19tacker.Constants.DISTRICT_RECOVERED_NEW;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<DistrictModel> districtModelArrayList;

    private String searchText="";
    private SpannableStringBuilder sb;

    public DistrictAdapter(Context mContext, ArrayList<DistrictModel> districtModelArrayList) {
        this.mContext = mContext;
        this.districtModelArrayList = districtModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        DistrictModel currentItem = districtModelArrayList.get(position);
        String districtName = currentItem.getDistrict();
        String districtTotal = currentItem.getConfirmed();
        holder.tv_districtTotalCases.setText(NumberFormat.getInstance().format(Integer.parseInt(districtTotal)));
        //holder.tv_districtName.setText(districtName);
        if(searchText.length()>0){
            //color your text here
            int index = districtName.indexOf(searchText);
            sb = new SpannableStringBuilder(districtName);
            Pattern word = Pattern.compile(searchText.toLowerCase());
            Matcher match = word.matcher(districtName.toLowerCase());
            while(match.find()){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(52, 195, 235)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                //index = stateName.indexOf(searchText,index+1);

            }
            holder.tv_districtName.setText(sb);

        }else{
            holder.tv_districtName.setText(districtName);
        }

        holder.lin_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistrictModel clickedItem = districtModelArrayList.get(position);
                Intent perDistrictIntent = new Intent(mContext, EachDistrictData.class);
                perDistrictIntent.putExtra(DISTRICT_NAME, clickedItem.getDistrict());
                perDistrictIntent.putExtra(DISTRICT_CONFIRMED, clickedItem.getConfirmed());
                perDistrictIntent.putExtra(DISTRICT_CONFIRMED_NEW, clickedItem.getNewConfirmed());
                perDistrictIntent.putExtra(DISTRICT_ACTIVE, clickedItem.getActive());
                perDistrictIntent.putExtra(DISTRICT_DEATH, clickedItem.getDeceased());
                perDistrictIntent.putExtra(DISTRICT_DEATH_NEW, clickedItem.getNewDeceased());
                perDistrictIntent.putExtra(DISTRICT_RECOVERED, clickedItem.getRecovered());
                perDistrictIntent.putExtra(DISTRICT_RECOVERED_NEW, clickedItem.getNewRecovered());
                mContext.startActivity(perDistrictIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return districtModelArrayList==null ? 0 : districtModelArrayList.size();
    }

    public void filterList(ArrayList<DistrictModel> filteredList, String search) {
        districtModelArrayList = filteredList;
        this.searchText = search;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_districtName, tv_districtTotalCases;
        LinearLayout lin_district;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_districtName = itemView.findViewById(R.id.state_layout_name);
            tv_districtTotalCases = itemView.findViewById(R.id.state_layout_confirmed);
            lin_district = itemView.findViewById(R.id.layout_state_lin);
        }
    }

}
