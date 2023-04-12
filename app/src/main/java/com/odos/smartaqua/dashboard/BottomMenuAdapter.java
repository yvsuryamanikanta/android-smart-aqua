package com.odos.smartaqua.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.AddBrandActivity;
import com.odos.smartaqua.checktray.AddChecktrayActivity;
import com.odos.smartaqua.checktray.ChecktrayObservationActivity;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.feed.AddFeedActivity;
import com.odos.smartaqua.feed.FeedObservationActivity;
import com.odos.smartaqua.growth.GrowthObservationActivity;
import com.odos.smartaqua.lab.LabObservationActivity;
import com.odos.smartaqua.tank.PondListActivity;
import com.odos.smartaqua.tank.PreparationListActivity;
import com.odos.smartaqua.tank.StockingListActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.warehouse.invoice.AddInvoiceActivity;
import com.odos.smartaqua.warehouse.products.AddProductActivity;
import com.odos.smartaqua.warehouse.stock.AddStockActivity;

import java.util.ArrayList;

public class BottomMenuAdapter extends RecyclerView.Adapter<BottomMenuAdapter.MyViewHolder> {
    ArrayList<String> data;
    private LayoutInflater layoutInflater;
    private Context _context;
    private int _flag, tankPosition;
    private String tankId, tankName, cultureId, cultureAccess, cultureResponse;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.txtView);
        }
    }

    public BottomMenuAdapter(Context context, ArrayList<String> arrayList, int flag, String tId, String tName, String cId, int pos, String cAccess, String response) {
        this.data = arrayList;
        this._context = context;
        this._flag = flag;
        this.tankId = tId;
        this.cultureId = cId;
        this.tankName = tName;
        this.cultureAccess = cAccess;
        this.cultureResponse = response;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View layout = layoutInflater.inflate(R.layout.list_item_create, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textView.setText("" + data.get(position));
        holder.textView.setOnClickListener(v -> {
            if (_flag == 1) {
                switch (position) {
                    case 0:
                        AquaConstants.putIntent(_context, AddBrandActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, AddProductActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        AquaConstants.putIntent(_context, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 3:
                        AquaConstants.putIntent(_context, AddStockActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 4:
                        // AquaConstants.putIntent(_context, AddStockActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 5:
                        // AquaConstants.putIntent(_context, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 6:
                        Toast.makeText(_context, "expenditure", Toast.LENGTH_SHORT).show();
                        break;

                }
            } else if (_flag == 3) {
                switch (position) {
                    case 0:
                        AquaConstants.putIntent(_context, PondListActivity.class, AquaConstants.HOLD, new String[]{"1"});
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, AddCultureActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        AquaConstants.putIntent(_context, PreparationListActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 3:
                        AquaConstants.putIntent(_context, StockingListActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 4:
                        String[] values = new String[]{tankId, cultureId, tankName, cultureAccess};
                        AquaConstants.putIntent(_context, AddFeedActivity.class, AquaConstants.HOLD, values);
                        break;
                    case 5:
                        AquaConstants.putIntent(_context, AddChecktrayActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 6:
                        Toast.makeText(_context, "add treatment", Toast.LENGTH_SHORT).show();
                        break;
                }

            } else if (_flag == 4) {
                switch (position) {
                    case 0:
                        Toast.makeText(_context, "Feed report", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(_context, "CheckTray report", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(_context, "Lab report", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(_context, "Growth report", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(_context, "Expends report", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else if (_flag == 5) {
                switch (position) {
                    case 0:
                        AquaConstants.putIntent(_context, FeedObservationActivity.class, AquaConstants.HOLD, new String[]{"1"});
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, ChecktrayObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        AquaConstants.putIntent(_context, GrowthObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 3:
                        AquaConstants.putIntent(_context, LabObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 4:
                        Toast.makeText(_context, "expends", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}