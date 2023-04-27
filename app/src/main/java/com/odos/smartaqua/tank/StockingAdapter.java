package com.odos.smartaqua.tank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterStockingBinding;

import java.util.ArrayList;

public class StockingAdapter extends RecyclerView.Adapter<StockingAdapter.MyViewHolder> {

    ArrayList<StockingModel> arrayList;
    private LayoutInflater layoutInflater;
    private StockingAdapter.ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterStockingBinding binding;

        public MyViewHolder(AdapterStockingBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public StockingAdapter(Context context, ArrayList<StockingModel> arrayList, StockingAdapter.ClickListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public StockingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterStockingBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_stocking, parent, false);
        return new StockingAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StockingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StockingModel stockingModel = (StockingModel) arrayList.get(position);
        holder.binding.setStockModel(stockingModel);

        if(isNullOrEmpty(stockingModel.ammonia)){
            holder.binding.txtAmmonia.setText(stockingModel.ammonia);
            holder.binding.linearAmmonia.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearAmmonia.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.plage)){
            holder.binding.txtPh.setText(stockingModel.plage);
            holder.binding.linearPh.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearPh.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.nitrite)){
            holder.binding.txtNitrite.setText(stockingModel.nitrite);
            holder.binding.linearNitrite.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearNitrite.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.alkalnity)){
            holder.binding.txtAlkalnity.setText(stockingModel.alkalnity);
            holder.binding.linearAlkalinity.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearAlkalinity.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.hardness)){
            holder.binding.txtHardness.setText(stockingModel.hardness);
            holder.binding.linearHardness.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearHardness.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.iron)){
            holder.binding.txtIron.setText(stockingModel.iron);
            holder.binding.linearIron.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearIron.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.mineral)){
            holder.binding.txtMineral.setText(stockingModel.mineral);
            holder.binding.linearMineralComposition.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMineralComposition.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.mineral)){
            holder.binding.txtMineral.setText(stockingModel.mineral);
            holder.binding.linearMineralComposition.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMineralComposition.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.clorine)){
            holder.binding.txtClorine.setText(stockingModel.clorine);
            holder.binding.linearClorine.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearClorine.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.salnity)){
            holder.binding.txtSalnity.setText(stockingModel.salnity);
            holder.binding.linearSalnity.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearSalnity.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(stockingModel.transparancy)){
            holder.binding.txtTransparency.setText(stockingModel.transparancy);
            holder.binding.linearTransparancy.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearTransparancy.setVisibility(View.GONE);
        }

    }

    boolean isNullOrEmpty(String data){
        return data != null && !data.equalsIgnoreCase("");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public interface ClickListener {
        void onClicked(StockingModel StockingModel, int pos);
    }
}
