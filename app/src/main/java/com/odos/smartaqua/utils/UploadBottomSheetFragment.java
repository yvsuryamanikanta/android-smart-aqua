package com.odos.smartaqua.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.odos.smartaqua.R;


public class UploadBottomSheetFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private ItemClickListener mListener;

    public static UploadBottomSheetFragment newInstance() {
        return new UploadBottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upload_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txt_camera = (TextView) view.findViewById(R.id.txt_camera);
        TextView txt_gallery = (TextView) view.findViewById(R.id.txt_gallery);
        TextView txt_doc = (TextView) view.findViewById(R.id.txt_doc);

        Helper.setTypeFace(getActivity(), getString(R.string.contentfont), txt_camera);
        Helper.setTypeFace(getActivity(), getString(R.string.contentfont), txt_gallery);
        Helper.setTypeFace(getActivity(), getString(R.string.contentfont), txt_doc);


        view.findViewById(R.id.ll_camera).setOnClickListener(this);
        view.findViewById(R.id.ll_gallery).setOnClickListener(this);
        view.findViewById(R.id.ll_documents).setOnClickListener(this);

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
            case R.id.ll_camera:
                mListener.onItemClick("camera");
                break;

            case R.id.ll_gallery:
                mListener.onItemClick("gallery");
                break;

            case R.id.ll_documents:
                mListener.onItemClick("doc");
                break;


        }

        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}