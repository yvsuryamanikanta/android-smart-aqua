package com.odos.smartaqua.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.odos.smartaqua.R;


public class ListBottomSheetFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private ItemClickListener mListener;
    private String flag,selectedDate,tankId,cultureResponse;
    private int position;
    public static ListBottomSheetFragment newInstance(String flag,String date,String tankId,int pos,String cultureResponse) {
        ListBottomSheetFragment listBottomSheetFragment = new ListBottomSheetFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putString("flag", flag);
        bundle_data.putString("selectedDate", date);
        bundle_data.putString("tankId", tankId);
        bundle_data.putInt("pos", pos);
        bundle_data.putString("cultureResponse", cultureResponse);
        listBottomSheetFragment.setArguments(bundle_data);
        return listBottomSheetFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getString("flag");
            selectedDate = getArguments().getString("selectedDate");
            tankId = getArguments().getString("tankId");
            cultureResponse = getArguments().getString("cultureResponse");
            position = getArguments().getInt("pos");
            if (flag.contains("FEED") && flag.contains("CHECKTRAY") && flag.contains("LAB")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_lab).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("FEED") && flag.contains("CHECKTRAY")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_lab).setVisibility(View.GONE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("FEED") && flag.contains("LAB")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.GONE);
                view.findViewById(R.id.ll_lab).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("CHECKTRAY") && flag.contains("LAB")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.GONE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_lab).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("FEED")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.GONE);
                view.findViewById(R.id.ll_lab).setVisibility(View.GONE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("CHECKTRAY")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.GONE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_lab).setVisibility(View.GONE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else if (flag.contains("LAB")) {
                view.findViewById(R.id.ll_feed).setVisibility(View.GONE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.GONE);
                view.findViewById(R.id.ll_lab).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.ll_feed).setVisibility(View.GONE);
                view.findViewById(R.id.ll_checktray).setVisibility(View.GONE);
                view.findViewById(R.id.ll_lab).setVisibility(View.GONE);
                view.findViewById(R.id.ll_growth).setVisibility(View.GONE);
                view.findViewById(R.id.ll_treatment).setVisibility(View.GONE);
            }
        }
        view.findViewById(R.id.ll_feed).setOnClickListener(this);
        view.findViewById(R.id.ll_checktray).setOnClickListener(this);
        view.findViewById(R.id.ll_lab).setOnClickListener(this);
        view.findViewById(R.id.ll_growth).setOnClickListener(this);
        view.findViewById(R.id.ll_treatment).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_feed:
                mListener.onItemClick("feed",selectedDate,tankId,position,cultureResponse);
                break;

            case R.id.ll_checktray:
                mListener.onItemClick("checktray",selectedDate,tankId,position,cultureResponse);
                break;

            case R.id.ll_lab:
                mListener.onItemClick("lab",selectedDate,tankId,position,cultureResponse);
                break;
            case R.id.ll_growth:
                mListener.onItemClick("growth",selectedDate,tankId,position,cultureResponse);
                break;
            case R.id.ll_treatment:
                mListener.onItemClick("treatment",selectedDate,tankId,position,cultureResponse);
                break;
        }
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item,String selectedDate,String tankId,int pos,String cultureResponse);
    }
}