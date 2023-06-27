package com.odos.smartaqua.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.animal.AnimalObservationActivity;
import com.odos.smartaqua.animal.AnimalViewPagerActivity;
import com.odos.smartaqua.brand.BrandListActivity;
import com.odos.smartaqua.checktray.AddChecktrayActivity;
import com.odos.smartaqua.checktray.ChecktrayObservationActivity;
import com.odos.smartaqua.checktray.ChecktrayReportViewPagerActivity;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.expends.ExpendsObservationActivity;
import com.odos.smartaqua.expends.ExpendsReportViewPagerActivity;
import com.odos.smartaqua.feed.FeedListViewPagerActivity;
import com.odos.smartaqua.feed.TankViewPagerActivity;
import com.odos.smartaqua.growth.GrowthObservationActivity;
import com.odos.smartaqua.growth.GrowthReportViewPagerActivity;
import com.odos.smartaqua.lab.LabObservationActivity;
import com.odos.smartaqua.pcr.PCRObservationActivity;
import com.odos.smartaqua.pcr.PCRViewPagerActivity;
import com.odos.smartaqua.soil.SoilObservationActivity;
import com.odos.smartaqua.soil.SoilViewPagerActivity;
import com.odos.smartaqua.tank.PondListActivity;
import com.odos.smartaqua.tank.PondPreparationActivity;
import com.odos.smartaqua.tank.StockingActivity;
import com.odos.smartaqua.treatment.TreatmentObservationActivity;
import com.odos.smartaqua.treatment.TreatmentReportViewPagerActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.warehouse.products.AddProductActivity;
import com.odos.smartaqua.warehouse.products.ProductListActivity;
import com.odos.smartaqua.warehouse.stock.AddStockActivity;
import com.odos.smartaqua.warehouse.stock.StockListActivity;
import com.odos.smartaqua.water.WaterAnalysisViewPagerActivity;

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
                        AquaConstants.putIntent(_context, BrandListActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, ProductListActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        AquaConstants.putIntent(_context, StockListActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 3:
                        AquaConstants.putIntent(_context, ExpendsObservationActivity.class, AquaConstants.HOLD, null);
                        break;

                }
            } else if (_flag == 2) {
                switch (position) {
                    case 0:
                        AquaConstants.putIntent(_context, LabObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, PCRObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        String[] tankdetails = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, SoilObservationActivity.class, AquaConstants.HOLD, tankdetails);
                        break;
                    case 3:
                        String[] animal = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, AnimalObservationActivity.class, AquaConstants.HOLD, animal);
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
                        AquaConstants.putIntent(_context, PondPreparationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 3:
                        AquaConstants.putIntent(_context, StockingActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 4:
                        String[] values = new String[]{tankId, cultureId, tankName, cultureAccess};
                        AquaConstants.putIntent(_context, TankViewPagerActivity.class, AquaConstants.HOLD, values);
                        break;
                    case 5:
                        AquaConstants.putIntent(_context, AddChecktrayActivity.class, AquaConstants.HOLD, null);
                        break;
                }

            } else if (_flag == 4) {
                switch (position) {
                    case 0:
                        String[] tankdetails = new String[]{"0", "0", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
                        break;
                    case 1:
/*
                        ArrayList<String> headers = new ArrayList<>();
                        headers.add("Pond Name");
                        headers.add("B1");
                        headers.add("B2");
                        headers.add("B3");
                        headers.add("B4");
                        headers.add("B5");
                        headers.add("B6");

                        Intent intent = new Intent(_context, ReportTable.class);
//                        intent.putExtra("headers", headers);
                        _context.startActivity(intent);
*/
                        String[] checktrayData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, ChecktrayReportViewPagerActivity.class, AquaConstants.HOLD, checktrayData);
                        break;
                    case 2:
                        String[] tankData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, WaterAnalysisViewPagerActivity.class, AquaConstants.HOLD, tankData);
                        break;
                    case 3:
                        String[] pcrData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, PCRViewPagerActivity.class, AquaConstants.HOLD, pcrData);
                        break;

                    case 4:
                        String[] soilData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, SoilViewPagerActivity.class, AquaConstants.HOLD, soilData);
                        break;

                    case 5:
                        String[] animalData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, AnimalViewPagerActivity.class, AquaConstants.HOLD, animalData);
                        break;

                    case 6:
                        String[] gData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, GrowthReportViewPagerActivity.class, AquaConstants.HOLD, gData);
                        break;

                    case 7:
                        String[] tra = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, TreatmentReportViewPagerActivity.class, AquaConstants.HOLD, tra);
                        break;

                    case 8:
                        String[] eData = new String[]{"" + position, "2023-05-27", tankId, "feed", cultureResponse};
                        AquaConstants.putIntent(_context, ExpendsReportViewPagerActivity.class, AquaConstants.HOLD, eData);
                        break;
                }
            } else if (_flag == 5) {
                switch (position) {
                    case 0:
                        AquaConstants.putIntent(_context, ChecktrayObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 1:
                        AquaConstants.putIntent(_context, GrowthObservationActivity.class, AquaConstants.HOLD, null);
                        break;
                    case 2:
                        AquaConstants.putIntent(_context, TreatmentObservationActivity.class, AquaConstants.HOLD, null);
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
