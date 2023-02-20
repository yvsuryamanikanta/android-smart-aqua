package com.odos.smartaqua.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.odos.smartaqua.R;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.dashboard.DashBoardActivity;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Helper {

    public static void setTypeFace(final Context ctx, final String fontname, final TextView textView) {
        Typeface myTypeface = Typeface.createFromAsset(ctx.getAssets(), fontname);
        textView.setTypeface(myTypeface);
    }

    public static String parseDate(String time) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getUsrtType(Context context) {

        return ASPManager.getKey(context, AquaConstants.ROLE_CODE, null);
    }

    public static String getUserID(Context context) {
        return ASPManager.getKey(context, AquaConstants.USER_ID, null);
    }

    public static int getDisplaywidth(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getDisplayheight(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        return height;
    }

    public static long createRandomInteger(int aStart, long aEnd) {
        Random r = new Random();
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        long range = aEnd - (long) aStart + 1;
        long fraction = (long) (range * r.nextDouble());
        long randomNumber = fraction + (long) aStart;
        return randomNumber;

    }

    public static String getHeaderFormat(Context ctx) {
        return "application/json";
    }

    public static int getVersioncode(Context _context) {

        try {
            PackageInfo pInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            int version = pInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static String getUniqueId(Context ctx) {
        String uniqid = Settings.Secure.getString(ctx.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return uniqid;
    }

    public static void showMessageWithNavigation(final Context ctx, String content, final int val) {

        final Dialog d = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_showmsg);
        d.show();
        TextView txt_content = (TextView) d.findViewById(R.id.txt_content);
        TextView txt_ok = (TextView) d.findViewById(R.id.txt_ok);
        TextView txt_cancel = (TextView) d.findViewById(R.id.txt_cancel);
        txt_ok.setVisibility(View.VISIBLE);
        txt_cancel.setVisibility(View.VISIBLE);
        txt_ok.setText("YES");
        txt_cancel.setText("NO");
        TextView txt_title = (TextView) d.findViewById(R.id.txt_title);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_content);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_ok);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_title);
        txt_content.setText(content);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val == 0) {
                    d.dismiss();
                    ctx.startActivity(new Intent(ctx, DashBoardActivity.class));
                    ((Activity) ctx).finish();
                } else {
                    d.dismiss();
                    ((Activity) ctx).finish();
                }

            }
        });
    }


    public static void showMessage(final Context ctx, String content, final int val) {

        final Dialog d = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_showmsg);
        d.show();
        TextView txt_content = (TextView) d.findViewById(R.id.txt_content);
        TextView txt_ok = (TextView) d.findViewById(R.id.txt_ok);
        TextView txt_title = (TextView) d.findViewById(R.id.txt_title);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_content);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_ok);
        Helper.setTypeFace(ctx, ctx.getString(R.string.contentfont), txt_title);
        txt_content.setText(content);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val == 0) {
                    d.dismiss();
                    ((Activity) ctx).finish();
                } else if (val == 10) {
                    d.dismiss();
                    ctx.startActivity(new Intent(ctx, AddCultureActivity.class));
                } else {
                    d.dismiss();
                }

            }
        });
    }

    public static HashMap<String, String> headerParams(Context _context) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(_context.getString(R.string.contentType), _context.getString(R.string.contentTypeValue));
        headers.put("Accept", "application/json");
        return headers;
    }

    public static void getDateTimepicker(final Context ctx, final TextView txt_time_date, final int val) {
        final Dialog d = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_datetimepicker);
        d.show();
        final TextView txt_date_time = (TextView) d.findViewById(R.id.txt_date_time);
        final LinearLayout ll_date_time_picker = (LinearLayout) d.findViewById(R.id.ll_date_time_picker);
        final DatePicker datePicker = (DatePicker) d.findViewById(R.id.datePicker_reminder);
        final TimePicker timePicker_reminder = (TimePicker) d.findViewById(R.id.timePicker_reminder);
        final Button btn_set_date = (Button) d.findViewById(R.id.btn_set_date);
        final Button btn_set_time = (Button) d.findViewById(R.id.btn_set_time);
        Button btn_cance_pickers = (Button) d.findViewById(R.id.btn_cance_pickers);
        timePicker_reminder.setIs24HourView(true);
        //  datePicker.setMinDate(new Date().getTime() - 10000);


        //date setup
        btn_set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    String selectedDate = year + "-" + month + "-" + day;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(selectedDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String formateddate = DateFormat.format("yyyy-MM-dd", calendar).toString();
                    txt_date_time.setText(selectedDate);
                    btn_set_date.setVisibility(View.GONE);
                    btn_set_time.setVisibility(View.VISIBLE);
                    timePicker_reminder.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.GONE);
                    if (val == 0) {
                        txt_time_date.setText(formateddate);
                        d.dismiss();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        //time setup
        btn_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date_select = txt_date_time.getText().toString().trim();
                int hour = timePicker_reminder.getCurrentHour();
                int minute = timePicker_reminder.getCurrentMinute();
                txt_date_time.setText(date_select + "  " + hour + " : " + minute + " : " + "00");
                txt_time_date.setText(date_select + " " + hour + ":" + minute + ":" + "00");
                ll_date_time_picker.setVisibility(View.GONE);
                d.dismiss();
            }
        });

        // cance date time picker

        btn_cance_pickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_date_time_picker.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);
                timePicker_reminder.setVisibility(View.GONE);
                d.dismiss();
            }
        });
    }

    public static boolean checkRutimePermissions(String permissionname, Context context) {
        if (ContextCompat.checkSelfPermission(context, permissionname)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static Bitmap decodeSampledBitmapFromResourceMemOpt(InputStream inputStream) {

        byte[] byteArr = new byte[0];
        byte[] buffer = new byte[1024];
        int len;
        int count = 0;

        try {
            while ((len = inputStream.read(buffer)) > -1) {
                if (len != 0) {
                    if (count + len > byteArr.length) {
                        byte[] newbuf = new byte[(count + len) * 2];
                        System.arraycopy(byteArr, 0, newbuf, 0, count);
                        byteArr = newbuf;
                    }

                    System.arraycopy(buffer, 0, byteArr, count, len);
                    count += len;
                }
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArr, 0, count, options);
            options.inSampleSize = 2;//calculateInSampleSize(options, reqWidth, reqHeight);
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;// .ARGB_8888;
            return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static boolean getCameraPermission(Context context) {
        if (!checkRutimePermissions(Manifest.permission.CAMERA, context)) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
        return checkRutimePermissions(Manifest.permission.CAMERA, context);
    }

    public static boolean getWritePermission(Context context) {
        if (!checkRutimePermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, context)) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        return checkRutimePermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, context);
    }

    public static boolean getReadPermission(Context context) {
        if (!checkRutimePermissions(Manifest.permission.READ_EXTERNAL_STORAGE, context)) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        return checkRutimePermissions(Manifest.permission.READ_EXTERNAL_STORAGE, context);
    }

    public static void updateDialog(Context _ctx, String updatepath, String updatemessage, boolean mandatory) {


        final Dialog fbDialogue = new Dialog(_ctx, android.R.style.Theme_Translucent_NoTitleBar);
        fbDialogue.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.dialog_updateapp);
        fbDialogue.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP)
                    ((Activity) _ctx).finish();
                return false;
            }
        });
        TextView updateTextView = fbDialogue.findViewById(R.id.update_textView);
        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ctx.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(updatepath)));

            }
        });
        TextView closeTextView = fbDialogue.findViewById(R.id.close_textView);
        TextView txt_content = fbDialogue.findViewById(R.id.txt_content);
        TextView txt_whatsnew = fbDialogue.findViewById(R.id.txt_whatsnew);
        LinearLayout ll_whatsnew = fbDialogue.findViewById(R.id.ll_whatsnew);
        if (updatemessage.equalsIgnoreCase("")) {
            ll_whatsnew.setVisibility(View.INVISIBLE);
        } else {
            ll_whatsnew.setVisibility(View.VISIBLE);
            txt_whatsnew.setText(updatemessage);
        }
        if (mandatory) {
            closeTextView.setVisibility(View.INVISIBLE);
        } else {
            closeTextView.setVisibility(View.VISIBLE);
        }
        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fbDialogue.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        txt_content.setText(updatemessage);
        fbDialogue.setCancelable(false);
        fbDialogue.show();

    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @SuppressLint("MissingPermission")
    public static void getCurrentLocation(Context context) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(context)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);
                            ASPManager.setKey(context, AquaConstants.LATITUDE, "" + lati);
                            ASPManager.setKey(context, AquaConstants.LONGITUDE, "" + lati);
                            Log.e("data--==", "" + lati);
                            Log.e("data--==", "" + longi);
                        }
                    }
                }, Looper.getMainLooper());
    }

    public static boolean checkPermission(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("Allow All Permissions");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]
                                        {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS},
                                1);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
