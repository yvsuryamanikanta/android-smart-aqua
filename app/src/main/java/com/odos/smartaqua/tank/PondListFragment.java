package com.odos.smartaqua.tank;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentPondlistBinding;

import java.util.Arrays;
import java.util.List;

public class PondListFragment extends Fragment {
    private FragmentPondlistBinding _binding;
    private int pos;
    private String tankId, cultureId, tankName, cultureResponse;
    private PondListFragmentViewModel fragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int position, String tankId, String cultureId, String tankName, String response) {
        PondListFragment chatFragment = new PondListFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("pos", position);
        bundle_data.putString("tankId", tankId);
        bundle_data.putString("cultureId", cultureId);
        bundle_data.putString("tankName", tankName);
        bundle_data.putString("cultureResponse", response);
        chatFragment.setArguments(bundle_data);
        return chatFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pondlist, container, false);
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), apiKey);
        }
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            tankId = getArguments().getString("tankId");
            cultureId = getArguments().getString("cultureId");
            tankName = getArguments().getString("tankName");
            cultureResponse = getArguments().getString("cultureResponse");
            fragmentViewModel = new PondListFragmentViewModel(pos, getActivity(), _binding, tankId, cultureId, tankName, cultureResponse);
            _binding.setViewModel(fragmentViewModel);
            _binding.executePendingBindings();
        }

        _binding.addressEdit.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN")
                    .build(requireActivity());
            someActivityResultLauncher.launch(intent);
//            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

        return _binding.getRoot();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            fragmentViewModel.loadData();
            isLoaded = true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // You can do the assignment inside onAttach or onCreate, ie, before the activity is displayed
         someActivityResultLauncher = registerForActivityResult(
                new  ActivityResultContracts.StartActivityForResult(),
                 result -> {
                     if (result.getResultCode() == RESULT_OK && null!=result.getData()) {
                         Place place = Autocomplete.getPlaceFromIntent(result.getData());
                         Log.i("$$$$$$$$$", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                         Toast.makeText(getActivity(), "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();

                         _binding.txtTankAddress.setText(place.getName() + "   " + place.getAddress());


                     } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                         // TODO: Handle the error.
                         Status status = Autocomplete.getStatusFromIntent(result.getData());
                         Toast.makeText(getActivity(), "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                         Log.i("#######", status.getStatusMessage());
                     } else if (result.getResultCode() == RESULT_CANCELED) {
                         // The user canceled the operation.
                     }
                 });
    }
}
