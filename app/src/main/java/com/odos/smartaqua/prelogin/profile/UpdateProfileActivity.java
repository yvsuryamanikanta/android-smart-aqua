package com.odos.smartaqua.prelogin.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityUpdateProfileBinding;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class UpdateProfileActivity extends BaseActivity implements UploadBottomSheetFragment.ItemClickListener{

    private ActivityUpdateProfileBinding binding;
UpdateProfileViewModel profileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(UpdateProfileActivity.this, R.layout.activity_update_profile);
        LayoutInflater mInflater = LayoutInflater.from(UpdateProfileActivity.this);
        binding = DataBindingUtil.inflate(mInflater, R.layout.activity_update_profile, activityBaseBinding.baseFragment, true);
        UserData userData = (UserData) getIntent().getSerializableExtra("userdata");
        profileViewModel = new UpdateProfileViewModel(UpdateProfileActivity.this, binding,userData);
        binding.setViewModel(profileViewModel);
        binding.executePendingBindings();

        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            if (data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                binding.imgProfile.setImageBitmap(photo);
                profileViewModel.uploadBitmap(photo);
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
                binding.imgProfile.setImageBitmap(selectedImage);
                profileViewModel.uploadBitmap(selectedImage);
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
                binding.imgProfile.setImageBitmap(selectedImage);
                profileViewModel.uploadBitmap(selectedImage);
            }
        } else {
            Toast.makeText(this, "File Not Found.", Toast.LENGTH_SHORT).show();
        }

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
}
