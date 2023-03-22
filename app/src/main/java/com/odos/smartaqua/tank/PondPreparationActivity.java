package com.odos.smartaqua.tank;

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
import com.odos.smartaqua.databinding.ActivityAddpondBinding;
import com.odos.smartaqua.databinding.ActivityPondprepareBinding;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PondPreparationActivity extends BaseActivity implements UploadBottomSheetFragment.ItemClickListener{
    private ActivityPondprepareBinding _activityPondprepareBinding;
    private PondPreparationViewModel pondPreparationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PondPreparationActivity.this);
        _activityPondprepareBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_pondprepare, activityBaseBinding.baseFragment, true);
        pondPreparationViewModel = new PondPreparationViewModel(PondPreparationActivity.this, _activityPondprepareBinding);
        _activityPondprepareBinding.setViewModel(pondPreparationViewModel);
        _activityPondprepareBinding.executePendingBindings();

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
                _activityPondprepareBinding.imgView.setImageBitmap(photo);
                pondPreparationViewModel.uploadBitmap(photo);
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
                _activityPondprepareBinding.imgView.setImageBitmap(selectedImage);
                pondPreparationViewModel.uploadBitmap(selectedImage);
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
                _activityPondprepareBinding.imgView.setImageBitmap(selectedImage);
                pondPreparationViewModel.uploadBitmap(selectedImage);
            }
        } else {
            Toast.makeText(this, "File Not Found.", Toast.LENGTH_SHORT).show();
        }

    }
}
