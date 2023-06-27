package com.odos.smartaqua.warehouse.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterProductlistBinding;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    ArrayList<ProductTypes> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterProductlistBinding binding;

        public MyViewHolder(AdapterProductlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public ProductListAdapter(Context context, ArrayList<ProductTypes> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterProductlistBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_productlist, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        ProductTypes model = homeModelArrayList.get(position);
        holder.binding.txtTitle.setText(model.getProductType());
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

    public interface ClickListener {
        void onClicked(ProductTypes model, int pos);
    }
}
