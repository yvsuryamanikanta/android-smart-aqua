package com.odos.smartaqua.warehouse.search;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterSearchBinding;
import com.odos.smartaqua.warehouse.products.WareHouseModel;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private CustomFilter mFilter;
    private List<WareHouseModel> _filteredList;
    private List<WareHouseModel> _productList;
    private ClickListener listener;
    private LayoutInflater layoutInflater;

    public SearchAdapter(List<WareHouseModel> productList, List<WareHouseModel> filteredList, ClickListener _listener) {
        this._productList = productList;
        this._filteredList = filteredList;
        this.mFilter = new CustomFilter(SearchAdapter.this);
        this.listener = _listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterSearchBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_search, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setViewModel(_filteredList.get(position));

        holder.binding.productCard.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(_filteredList.get(position), position);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AdapterSearchBinding binding;

        public MyViewHolder(AdapterSearchBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface ClickListener {
        void onClicked(WareHouseModel wareHouseModel, int pos);
    }

    @Override
    public int getItemCount() {
        return _filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class CustomFilter extends Filter {
        private SearchAdapter mAdapter;

        private CustomFilter(SearchAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            _filteredList.clear();
            final FilterResults results = new FilterResults();
            Log.e("$$$$$$$$$$ ", " "+constraint);
            if (constraint.length() == 0) {
                _filteredList.addAll(_productList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final WareHouseModel mWords : _productList) {
                    if (mWords.getProductname().toLowerCase().startsWith(filterPattern)) {
                        _filteredList.add(mWords);
                    }
                }
            }
            results.values = _filteredList;
            results.count = _filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}