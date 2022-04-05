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

import com.ferid.app.a19tacker.DistrictData;
import com.ferid.app.a19tacker.EachDistrictData;
import com.ferid.app.a19tacker.EachStateData;
import com.ferid.app.a19tacker.Models.StateDistrictModel;

import com.ferid.app.a19tacker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ferid.app.a19tacker.Constants.STATE_ACTIVE;
import static com.ferid.app.a19tacker.Constants.STATE_CONFIRMED;
import static com.ferid.app.a19tacker.Constants.STATE_CONFIRMED_NEW;
import static com.ferid.app.a19tacker.Constants.STATE_DEATH;
import static com.ferid.app.a19tacker.Constants.STATE_DEATH_NEW;
import static com.ferid.app.a19tacker.Constants.STATE_LAST_UPDATE;
import static com.ferid.app.a19tacker.Constants.STATE_NAME;
import static com.ferid.app.a19tacker.Constants.STATE_RECOVERED;
import static com.ferid.app.a19tacker.Constants.STATE_RECOVERED_NEW;

public class StateDistrictAdapter extends RecyclerView.Adapter<StateDistrictAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StateDistrictModel> stateDistrictModelArrayList;
    private String searchText = "";
    private SpannableStringBuilder sb;
    public StateDistrictAdapter(Context mContext, ArrayList<StateDistrictModel> arrayList) {
        this.mContext = mContext;
        this.stateDistrictModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public StateDistrictAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state, parent, false);
        return new StateDistrictAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateDistrictAdapter.ViewHolder holder, final int position) {
        StateDistrictModel currentItem = stateDistrictModelArrayList.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format(Integer.parseInt(stateTotal)));
        //holder.tv_stateName.setText(stateName);
        if(searchText.length()>0){
            //color your text here
            sb = new SpannableStringBuilder(stateName);
            Pattern word = Pattern.compile(searchText.toLowerCase());
            Matcher match = word.matcher(stateName.toLowerCase());
            while(match.find()){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(52, 195, 235)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            holder.tv_stateName.setText(sb);

        }else{
            holder.tv_stateName.setText(stateName);
        }

        holder.lin_state_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateDistrictModel clickedItem = stateDistrictModelArrayList.get(position);
                Intent perStateIntent = new Intent(mContext, DistrictData.class);
                perStateIntent.putExtra(STATE_NAME, clickedItem.getState());
                perStateIntent.putExtra(STATE_CONFIRMED, clickedItem.getConfirmed());
                perStateIntent.putExtra(STATE_CONFIRMED_NEW, clickedItem.getConfirmed_new());
                perStateIntent.putExtra(STATE_ACTIVE, clickedItem.getActive());
                perStateIntent.putExtra(STATE_DEATH, clickedItem.getDeath());
                perStateIntent.putExtra(STATE_DEATH_NEW, clickedItem.getDeath_new());
                perStateIntent.putExtra(STATE_RECOVERED, clickedItem.getRecovered());
                perStateIntent.putExtra(STATE_RECOVERED_NEW, clickedItem.getRecovered_new());
                perStateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getLastupdate());

                mContext.startActivity(perStateIntent);
            }
        });
    }

    public void filterList(ArrayList<StateDistrictModel> filteredList, String text) {
        stateDistrictModelArrayList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stateDistrictModelArrayList==null ? 0 : stateDistrictModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stateName, tv_stateTotalCases;
        LinearLayout lin_state_district;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stateName = itemView.findViewById(R.id.state_layout_name);
            tv_stateTotalCases = itemView.findViewById(R.id.state_layout_confirmed);
            lin_state_district = itemView.findViewById(R.id.layout_state_lin);
        }
    }
}


