package com.odos.smartaqua.warehouse.stock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddstockBinding;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddStockActivity extends BaseActivity implements UploadBottomSheetFragment.ItemClickListener{
    private ActivityAddstockBinding _binding;
    public boolean image_flag;
    private AddStockViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddStockActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_addstock, activityBaseBinding.baseFragment, true);
        viewModel = new AddStockViewModel(AddStockActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        image_flag = false;
    }

    @Override
    public void onItemClick(String item) {
        if (item.equalsIgnoreCase("camera")) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 1);
        } else if (item.equalsIgnoreCase("gallery")) {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 2);
        } else if (item.equalsIgnoreCase("doc")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 3);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            if (data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                _binding.imgView.setImageBitmap(photo);
                viewModel.uploadBitmap(photo);
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            if (data.getData() != null) {
                Uri selectedImageUri = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "File Not Found.", Toast.LENGTH_SHORT).show();
                }
                Bitmap selectedImage = Helper.decodeSampledBitmapFromResourceMemOpt(imageStream);
                _binding.imgView.setImageBitmap(selectedImage);
                viewModel.uploadBitmap(selectedImage);
            }
        } else if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            if (data.getData() != null) {
                Uri selectedImageUri = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "File Not Found.", Toast.LENGTH_SHORT).show();
                }
                Bitmap selectedImage = Helper.decodeSampledBitmapFromResourceMemOpt(imageStream);
                _binding.imgView.setImageBitmap(selectedImage);
                viewModel.uploadBitmap(selectedImage);
            }
        } else {
            Toast.makeText(this, "File Not Found.", Toast.LENGTH_SHORT).show();
        }

    }

}
