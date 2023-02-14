package com.odos.smartaqua.feed;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterFeedBinding;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder> {

    ArrayList<FeedModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterFeedBinding binding;

        public MyViewHolder(AdapterFeedBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public FeedListAdapter(Context context, ArrayList<FeedModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterFeedBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_feed, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        FeedModel feedModel = (FeedModel) homeModelArrayList.get(position);
        holder.binding.setModel(homeModelArrayList.get(position));
        holder.binding.txtFeedHead.setText("Feed " + feedModel.getGroupname() + " ( " + feedModel.getFeeddate() + " )");
        try {
            JSONArray feedJsonArray = new JSONArray(feedModel.getFeedProducts());
            JSONArray supplimentsJsonArray = new JSONArray(feedModel.getSuppliments());
            String feedProducts = "";
            String suppliments = "";
            if (feedJsonArray.length() != 0) {
                for (int i = 0; i < feedJsonArray.length(); i++) {
                    JSONObject jsonObject1 = feedJsonArray.getJSONObject(i);
                    String productName = jsonObject1.getString("productName");
                    if(i == feedJsonArray.length()){
                        feedProducts = feedProducts + productName;
                    }else {
                        feedProducts = feedProducts + productName + " , ";
                    }
                }
            }
            if (supplimentsJsonArray.length() != 0) {
                for (int j = 0; j < supplimentsJsonArray.length(); j++) {
                    JSONObject jsonObject2 = supplimentsJsonArray.getJSONObject(j);
                    String productName = jsonObject2.getString("productName");
                    if(j == supplimentsJsonArray.length()){
                        suppliments = suppliments + productName;
                    }else {
                        suppliments = suppliments + productName + " , ";
                    }
                }
            }
            holder.binding.txtFeedProducts.setText(feedProducts);
            holder.binding.txtSuppliments.setText(suppliments);
        } catch (Exception e) {
            Log.e("data--==", "" + e);
        }

        holder.binding.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AquaConstants.putIntent(_context, EdittankTankActivity.class, 1, null);
            }
        });


        holder.binding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(_context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setCancelable(false);
                d.setContentView(R.layout.dialog_showmsg);
                d.show();
                TextView txt_content = (TextView) d.findViewById(R.id.txt_content);
                TextView txt_ok = (TextView) d.findViewById(R.id.txt_ok);
                TextView txt_cancel = (TextView) d.findViewById(R.id.txt_cancel);
                txt_cancel.setVisibility(View.VISIBLE);
                TextView txt_title = (TextView) d.findViewById(R.id.txt_title);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_content);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_ok);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_title);
                txt_content.setText(_context.getResources().getString(R.string.areyousuredelete));
                txt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

            }
        });

        holder.binding.llFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(homeModelArrayList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }


    public interface ClickListener {
        void onClicked(FeedModel feedModel, int pos);
    }
}