package com.odos.smartaqua.feed;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityFeedInfoBinding;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import org.json.JSONArray;
import org.json.JSONObject;

public class FeedInfoViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityFeedInfoBinding _activityFeedInfoBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String[] values;
    private ProgressDialog progressDialog;

    public FeedInfoViewModel(Context context, ActivityFeedInfoBinding activityFeedInfoBinding) {
        this._context = context;
        this._activityFeedInfoBinding = activityFeedInfoBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        _activityFeedInfoBinding.txtHeadName.setText("Feed "+values[1]);
        _activityFeedInfoBinding.txtHint.setText("Here we are given Feed Details Please use below Feed and Suppliments :");
        _activityFeedInfoBinding.txtFeedDetails.setText("Feed Details");
        _activityFeedInfoBinding.txtSupplimentDetails.setText("Suppliment Details");
        addViews(values[2],_activityFeedInfoBinding.llFeed);
        addViews(values[3],_activityFeedInfoBinding.llSuppliment);
        _activityFeedInfoBinding.txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.getWritePermission(_context)){
                    convertToPdf();
                }
            }
        });
    }
    private void convertToPdf(){
        PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
        /*LinearLayout linearLayout = new LinearLayout(_context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ImageView imageView = new ImageView(_context);
        imageView.setImageDrawable(_context.getResources().getDrawable(R.drawable.admob_banner));
        linearLayout.addView(imageView);
        linearLayout.addView(_activityFeedInfoBinding.pdfView);*/
        Bitmap bitmap = pdfGeneratorNew.getViewScreenShot(_activityFeedInfoBinding.pdfView);
        String bitmapPath = MediaStore.Images.Media.insertImage(_context.getContentResolver(), bitmap,"title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        _context.startActivity(Intent.createChooser(intent, "Share"));
        //pdfGenerator.saveImageToPDF(_activityFeedInfoBinding.pdfView, bitmap);
    }
    private void addViews(String response, LinearLayout ll){
        try {
            JSONArray feedJsonArray = new JSONArray(response);
            if(feedJsonArray.length()!=0){
                for (int i=0;i<feedJsonArray.length();i++){
                    JSONObject jsonObject1 = feedJsonArray.getJSONObject(i);
                    String productName = jsonObject1.getString("productName");
                    String productqty = jsonObject1.getString("productqty");
                    String quantity = jsonObject1.getString("quantity");
                    String comments = jsonObject1.getString("comments");
                    _activityFeedInfoBinding.txtComments.setText(comments);
                    String feedProduct = i+1+". " + productName + " -- "+productqty+" "+quantity;
                    TextView textView = new TextView(_context);
                    textView.setText(feedProduct);
                    textView.setPadding(5,5,5,5);
                    textView.setTextColor(_context.getResources().getColor(R.color.txtColor));
                    Helper.setTypeFace(_context,_context.getString(R.string.contentfont),textView);
                    ll.addView(textView);
                }
            }
        } catch (Exception e) {
            Log.e("data--==",""+e);
        }

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
