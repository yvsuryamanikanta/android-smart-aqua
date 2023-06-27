package com.odos.smartaqua.warehouse.stock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.Brandcnsts;
import com.odos.smartaqua.databinding.AdapterBrandlistBinding;
import com.odos.smartaqua.databinding.AdapterStocklistBinding;

import java.util.ArrayList;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.MyViewHolder> {

    ArrayList<StockCnsts> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterStocklistBinding binding;

        public MyViewHolder(AdapterStocklistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public StockListAdapter(Context context, ArrayList<StockCnsts> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterStocklistBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_stocklist, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        StockCnsts model = homeModelArrayList.get(position);
        holder.binding.txtTitle.setText(model.getProductname());
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

    public interface ClickListener {
        void onClicked(StockCnsts model, int pos);
    }
}
