package com.odos.smartaqua.tank;


import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.BaseObservable;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityStockingBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class StockingViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityStockingBinding _activityStockingBinding;
    private ServiceAsyncResponse serviceAsyncResponse;


    public StockingViewModel(Context context, ActivityStockingBinding activityStockingBinding) {
        this._context = context;
        this._activityStockingBinding = activityStockingBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;

        {
            String pcr_result[] = {"+ve","-ve"};
            ArrayAdapter<String> spinnerPCRResultAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_result);
            spinnerPCRResultAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityStockingBinding.spinPcrResult.setAdapter(spinnerPCRResultAdapter);
        }
        {
            String pcr_hepa[] = {"good","bad"};
            ArrayAdapter<String> spinnerHepatopancreasAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_hepa);
            spinnerHepatopancreasAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityStockingBinding.spinHepatopancreas.setAdapter(spinnerHepatopancreasAdapter);
        }
        {
            String pcr_accl[] = {"yes","no"};
            ArrayAdapter<String> spinnerAcclinitizationAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_accl);
            spinnerAcclinitizationAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityStockingBinding.spinAcclinitization.setAdapter(spinnerAcclinitizationAdapter);
        }
        {
            String pc_bag[] = {"pcs","bags"};
            ArrayAdapter<String> spinnerPckageAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pc_bag);
            spinnerPckageAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityStockingBinding.spinPakagingType.setAdapter(spinnerPckageAdapter);
        }
    }

    public void saveStocking(View view) {

    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }


}
