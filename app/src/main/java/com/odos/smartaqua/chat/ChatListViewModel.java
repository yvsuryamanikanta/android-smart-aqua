package com.odos.smartaqua.chat;

import static com.odos.smartaqua.API.ServiceConstants.CHAT_LIST;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityChatListBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatListViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private boolean loading = true;
    private ActivityChatListBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ViewPagerFragmentAdapter myAdapter;

    public ChatListViewModel(Context context, ActivityChatListBinding chatBinding) {
        this._context = context;
        this._binding = chatBinding;

        initData();
//        loadData();
    }

    public void initData() {
        myAdapter = new ViewPagerFragmentAdapter(((AppCompatActivity) _context).getSupportFragmentManager(),
                ((AppCompatActivity) _context).getLifecycle());
        myAdapter.addFragment(ChatFragment.newInstance(0));
        myAdapter.addFragment(ChatFragment.newInstance(1));
        myAdapter.addFragment(ChatFragment.newInstance(2));
        // set Orientation in your ViewPager2
        _binding.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        _binding.viewpager.setAdapter(myAdapter);
//        _binding.viewpager.setPageTransformer(new MarginPageTransformer(1500));
        _binding.viewpager.setOffscreenPageLimit(1);
        _binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                FragmentLifecycle fragmentPending = (FragmentLifecycle) myAdapter.createFragment(position);
//                fragmentPending.onResumeFragment(DocumentsAct.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        new TabLayoutMediator(_binding.slidingTabs, _binding.viewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chat");
                    break;
                case 1:
                    tab.setText("Report");
                    break;
                case 2:
                    tab.setText("Details");
                    break;
            }
        }).attach();

        _binding.edtReply.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    _binding.imgAttach.setVisibility(View.GONE);
                    _binding.imgCamera.setVisibility(View.GONE);
                    _binding.imgSend.setVisibility(View.VISIBLE);
                }else {
                    _binding.imgAttach.setVisibility(View.VISIBLE);
                    _binding.imgCamera.setVisibility(View.VISIBLE);
                    _binding.imgSend.setVisibility(View.GONE);
                }
            }
        });
    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    CHAT_LIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
        }
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            Log.e("$$$$$$$$$$$$", new Gson().toJson(jsonobject));
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {


//                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
//                                _binding.recyclerView.setLayoutManager(mLayoutManager);
//                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//                                _binding.recyclerView.setAdapter(new ChatListAdapter(_context, arrayList, this));
                            } else {
                                Helper.showMessage(_context, "No Data Available Now.", AquaConstants.HOLD);
                            }
                        }
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }
}
