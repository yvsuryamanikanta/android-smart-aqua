package com.odos.smartaqua.brand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterBrandlistBinding;

import java.util.ArrayList;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.MyViewHolder> {

    ArrayList<Brandcnsts> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterBrandlistBinding binding;

        public MyViewHolder(AdapterBrandlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public BrandListAdapter(Context context, ArrayList<Brandcnsts> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterBrandlistBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_brandlist, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        Brandcnsts model = homeModelArrayList.get(position);
        holder.binding.txtTitle.setText(model.getBrandname());
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

    public interface ClickListener {
        void onClicked(Brandcnsts model, int pos);
    }
}
