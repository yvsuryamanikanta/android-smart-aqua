package com.odos.smartaqua.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class AquaConstants {

    public static String aqua = "aqua";
    public static String IS_LANGUAGE_SELECTED = "islanguageselected";
    public static String IS_LOGGED = "islogged";
    public static int SPLASH_TIME = 100;
    public static int FINISH = 0;
    public static int HOLD = 1;

    public static String[] EMPTYSTRING = new String[]{};


    /*service constants*/
    public static final String PROTOCOL = "http";
    public static final String HOST = "192.168.8.144/api/";
    //public static final String HOST = "3.84.163.198/api/";


    public static String VERSION = "version/";
    public static String DEVICE = "device/";
    public static String LANGUAGE = "language/";
    public static String USER = "user/";
    public static String OTP = "/otp";
    public static String VERIFY = "verify";
    public static String RESEND_OTP = "/resend";
    public static String LIST = "list";
    public static String SAVE = "save";
    public static String UPDATE = "update/";
    public static String DELETE = "delete/";
    public static String ROLE = "role/";
    public static String POND = "pond/";
    public static String PRODUCTTYPE = "producttype/";
    public static String BRAND = "brand/";
    public static String QTYTYPES = "quantitytype/";
    public static String PRODUCT = "product/";
    public static String FEED = "feed/";
    public static String TEMPLATE = "template/";
    public static String truevalue = "/true";

    public static String TAG = "response====";
    public static String FEC_STATUS = "Fcm_Status";
    public static String LANGUAGEID = "LaguageId";
    public static String TANKCOUNT = "tankcount";
    public static String ISFIRST = "isfirst";
    public static String LOGIN_STATUS = "Login_Status";
    public static String AUTH_TOKEN = "AUTH_TOKEN";
    public static String USER_NAME = "username";
    public static String USER_NUMBER = "usernumber";
    public static String USER_TYPE = "USER_TYPE";
    public static String HESDER_USER_ID = "USERID";
    public static String HEADER_AUTH = "AUTH_TOKEN";
    public static String ACCEPT = "Accept";
    public static String CONTENT_TYPE = "Content-Type";
    public static String FCM_ID = "fcmid";
    public static String USER_RESPONSE = "useresponse";
    public static String ROLE_CODE = "rolecode";


    //api constants...
    public static String LOGIN_USER = "Mobile";
    public static String LOGIN_PWD = "Password";
    public static String POND_NAME = "PondName";
    public static String POND_WIDTH = "Width";
    public static String POND_HEIGHT = "Height";
    public static String POND_SIZE = "Pondsizeinacres";
    public static String POND_ADDR = "PondAddress";
    public static String POND_DESC = "PondComments";
    public static String POND_LATITUDE = "PondLatitude";
    public static String POND_LONGITUDE = "PondLongitude";
    public static String POND_USERID = "UID";
    public static String POND_IMAGE = "Pond_Image";
    public static String CROPNUMBER = "CropNo";
    public static String WATERSOURCE = "WaterSource";
    public static String SALNITYSTOCKING = "Salinityatstocking";
    public static String SALNITYDURINGCROP = "Salinityrangeduringcrop";
    public static String DATEOFSTOCKING = "DateofStocking";
    public static String NOOFPLSTOCKED = "noofplstocked";
    public static String STOCKDENSITY = "Stockingdensity";
    public static String DATEOFHARVEST = "DateofHarvest";
    public static String YIELD = "Yield";
    public static String SIZEOFHARVEST = "SizeatHarvest";
    public static String SURVIVAL = "Survival";
    public static String TOTALFEEDUSED = "TotalFeedUsed";
    public static String FEEDCONVERSIONRATIO = "FeedConversionratio";
    public static String LABOUR_ID = "LabourID";


    //keys for storage
    public static String APP_USER_TYPE = "appusertype";
    public static String APP_USER_NUMBER = "usernumber";
    public static String APP_USER_NAME = "username";
    public static String APP_USER_EMAIL = "useremail";
    public static String USER_ID = "userid";
    public static String ROLE_ID = "roleid";
    public static String UNIQUE_ID = "uniqueid";
    public static String CREATEDBY = "createdby";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";


    public static void postIntent(Context context, Class classname, String val) {
        Intent intent = new Intent(context, classname);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (val.equalsIgnoreCase("0")) {
            ((Activity) context).finish();
        }
    }

    public static void putIntent(Context context, Class classname, int val, String[] values) {
        Intent intent = new Intent(context, classname);
        intent.putExtra("values", values);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (val == 0) {
            ((Activity) context).finish();
        }
    }

    public static void putDoubleIntent(Context context, Class classname, int val, String[] values, int position) {
        Intent intent = new Intent(context, classname);
        intent.putExtra("values", values);
        intent.putExtra("position", position);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (val == 0) {
            ((Activity) context).finish();
        }
    }

    public static void claerAllActivities(Context context, Class classname,String[] values) {
        Intent i = new Intent(context, classname);
        i.putExtra("values", values);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);

    }

    public static void postDelayoption(final Context context, final Class classname, final String val) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app activity_addalarm activity
                Intent i = new Intent(context, classname);
                context.startActivity(i);

            }
        }, 150);
        if (val.equalsIgnoreCase("0")) {
            ((Activity) context).finish();
        }
    }


}
