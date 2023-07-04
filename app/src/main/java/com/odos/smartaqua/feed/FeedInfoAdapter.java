package com.odos.smartaqua.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterFeedInfoBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedInfoAdapter extends RecyclerView.Adapter<FeedInfoAdapter.MyViewHolder> {

    List<FeedInfoModel> homeModelArrayList;
    List<JSONObject> jsonList = new ArrayList<JSONObject>();
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;

    public FeedInfoAdapter(Context context, List<FeedInfoModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterFeedInfoBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_feed_info, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        FeedInfoModel feedModel = (FeedInfoModel) homeModelArrayList.get(position);
        holder.binding.setModel(homeModelArrayList.get(position));

        holder.binding.txtProductId.setText(""+feedModel.getProductID());
        holder.binding.txtCatgId.setText(""+feedModel.getProductcatgeoryID());


    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    public interface ClickListener {
        void onClicked(FeedInfoModel feedModel, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterFeedInfoBinding binding;

        public MyViewHolder(AdapterFeedInfoBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}