package com.odos.smartaqua.warehouse.products;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterSearchproductBinding;

import java.util.List;


public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.MyViewHolder> implements Filterable {
    private CustomFilter mFilter;
    private List<WareHouseModel> _filteredList;
    private List<WareHouseModel> _productList;
    private ClickListener listener;
    private LayoutInflater layoutInflater;

    public SearchProductAdapter(List<WareHouseModel> productList, List<WareHouseModel> filteredList, ClickListener listener) {
        this._productList = productList;
        this._filteredList = filteredList;
        this.mFilter = new CustomFilter(SearchProductAdapter.this);
        this.listener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterSearchproductBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_searchproduct, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setViewModel(_filteredList.get(position));

        holder.binding.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(_filteredList.get(position), position);
                }
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AdapterSearchproductBinding binding;

        public MyViewHolder(AdapterSearchproductBinding itemBinding) {
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
        private SearchProductAdapter mAdapter;

        private CustomFilter(SearchProductAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            _filteredList.clear();
            final FilterResults results = new FilterResults();
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