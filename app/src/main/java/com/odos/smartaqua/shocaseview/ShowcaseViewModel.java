package com.odos.smartaqua.shocaseview;


import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.dashboard.DashBoardActivity;
import com.odos.smartaqua.databinding.ActivityShowcaseBinding;
import com.odos.smartaqua.utils.ASPManager;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ShowcaseViewModel extends BaseObservable implements ServiceAsyncResponse {
    private ShowViewPagerAdapter showViewPagerAdapter;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Context _context;
    private ActivityShowcaseBinding _activityShowcaseBinding;
    private int tabPosition;
    private String[] titles;
    private int[] layouts;
    private TextView[] dots;
    private int pondcount;
    private ServiceAsyncResponse serviceAsyncResponse;
    private int count;

    public ShowcaseViewModel(Context context, ActivityShowcaseBinding activityShowcaseBinding) {
        this._context = context;
        this._activityShowcaseBinding = activityShowcaseBinding;
        ASPManager.setKey(_context, AquaConstants.TANKCOUNT, "1");
        layouts = new int[]{
                R.layout.slider_one
        };
        addBottomDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter();
        _activityShowcaseBinding.pager.setAdapter(myViewPagerAdapter);
        _activityShowcaseBinding.pager.addOnPageChangeListener(viewPagerPageChangeListener);
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        _activityShowcaseBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    _activityShowcaseBinding.pager.setCurrentItem(current);
                } else {
                    count = Integer.parseInt(ASPManager.getKey(_context, AquaConstants.TANKCOUNT, null));
                    if (count != 0) {
                        for (int i = 1; i <= count; i++) {
                            HashMap<String, Object> postparams = new HashMap<>();
                            postparams.put("userid", Helper.getUserID(_context));
                            postparams.put("tankname", "TANK - " + i);
                            postparams.put("tanklocation", "");
                            postparams.put("tankimage", "");
                            if(count==i){
                                VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                        ServiceConstants.SAVE_TANK, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, false);
                            }else{
                                VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                        ServiceConstants.SAVE_TANK, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
                            }
                        }

                    }
                }
            }
        });
    }

    private int getItem(int i) {
        return _activityShowcaseBinding.pager.getCurrentItem() + i;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = _context.getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = _context.getResources().getIntArray(R.array.array_dot_inactive);

        _activityShowcaseBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(_context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            _activityShowcaseBinding.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                _activityShowcaseBinding.btnNext.setText(_context.getString(R.string.start));
            } else {
                _activityShowcaseBinding.btnNext.setText(_context.getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonObject, int serviceno) {
        switch (serviceno) {

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (status.equalsIgnoreCase("Failed")) {
                        Helper.showMessage(_context, "" + errorMsg, AquaConstants.HOLD);
                    } else {
                        AquaConstants.putIntent(_context, DashBoardActivity.class, AquaConstants.FINISH, null);
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "saving failed please try again once", AquaConstants.HOLD);
                }
                break;
        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            if (position == 0) {
                TextView txt_number_ponds = (TextView) view.findViewById(R.id.txt_number_ponds);
                ImageView img_plus = (ImageView) view.findViewById(R.id.img_plus);
                ImageView img_minus = (ImageView) view.findViewById(R.id.img_minus);

                img_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int pondcount = Integer.parseInt(txt_number_ponds.getText().toString());
                        if (pondcount < 5) {
                            pondcount = pondcount + 1;
                            txt_number_ponds.setText(String.valueOf(pondcount));
                            ASPManager.setKey(_context, AquaConstants.TANKCOUNT, txt_number_ponds.getText().toString());
                        } else {
                            Toast.makeText(_context, "we are not allowed more than 5 tanks. if you want to create another you have to create manually after setup completed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                img_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pondcount = Integer.parseInt(txt_number_ponds.getText().toString());
                        if (pondcount == 1) {
                            Toast.makeText(_context, "atleast one pond required for former",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            pondcount = pondcount - 1;
                            txt_number_ponds.setText(String.valueOf(pondcount));
                            ASPManager.setKey(_context, AquaConstants.TANKCOUNT, txt_number_ponds.getText().toString());
                        }
                    }
                });

            } else if (position == 1) {
                LinearLayout ll_boy_options = (LinearLayout) view.findViewById(R.id.ll_boy_options);
                LinearLayout ll_boy_details = (LinearLayout) view.findViewById(R.id.ll_boy_details);
                Button btn_yes = (Button) view.findViewById(R.id.btn_yes);
                Button btn_no = (Button) view.findViewById(R.id.btn_no);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_boy_details.setVisibility(View.VISIBLE);
                        ll_boy_options.setVisibility(View.GONE);
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _activityShowcaseBinding.pager.setCurrentItem(3);
                    }
                });
            } else if (position == 2) {
                LinearLayout ll_boy_options = (LinearLayout) view.findViewById(R.id.ll_boy_options);
                LinearLayout ll_boy_details = (LinearLayout) view.findViewById(R.id.ll_boy_details);
                Button btn_yes = (Button) view.findViewById(R.id.btn_yes);
                Button btn_no = (Button) view.findViewById(R.id.btn_no);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_boy_details.setVisibility(View.VISIBLE);
                        ll_boy_options.setVisibility(View.GONE);
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }


            return view;

        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}
