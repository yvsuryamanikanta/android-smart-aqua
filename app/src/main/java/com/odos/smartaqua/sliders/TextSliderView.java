package com.odos.smartaqua.sliders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.odos.smartaqua.R;


public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        String getint = getBundle().getString("sliderlink");
        if (getint.equalsIgnoreCase("") || getint.equalsIgnoreCase("null")) {

        } else {
            target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /* Intent postint = new Intent(getContext(),SliderHtmlActivity.class);
                    postint.putExtra("sliderlink",getBundle().getString("sliderlink"));
                    postint.putExtra("tabname",getBundle().getString("tabname"));
                    postint.putExtra("slidertitle",getBundle().getString("slidertitle"));
                    mContext.startActivity(postint);*/
                }
            });
        }

        return v;
    }


}
