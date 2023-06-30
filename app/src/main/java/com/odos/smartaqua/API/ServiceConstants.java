package com.odos.smartaqua.API;


import com.odos.smartaqua.utils.AquaConstants;

public class ServiceConstants {

    public static final String BASE_URL = String.format("%s://%s", AquaConstants.PROTOCOL, AquaConstants.HOST);
    public static final String GET_VERSION = BASE_URL + "version/list/true";
    public static final String GET_ROLES = BASE_URL + "roles/list";
    public static final String RESEND_OTP = BASE_URL + "otp/resend/";
    public static final String GET_CULTURES = BASE_URL + "culture/list/";
    public static final String GET_TANKLIST = BASE_URL + "tank/list/";
    public static final String SAVE_CULTURE = BASE_URL + "culture/save";
    public static final String SAVE_DEVICE = BASE_URL + "device/save";
    public static final String SAVE_TANK = BASE_URL + "tank/save";
    public static final String SAVE_USER = BASE_URL + "user/save";
    public static final String USER_LOGIN = BASE_URL + "user/login";
    public static final String VERIFY_OTP = BASE_URL + "otp/verify/";
    public static final String PRODUCT_CAATEGORIES = BASE_URL + "product/categories";
    public static final String BRAND_LIST = BASE_URL + "brand/list";
    public static final String QTY_LIST = BASE_URL + "quantity/categories";
    public static final String SAVE_FEED = BASE_URL + "feed/template/save";
    public static final String FEED_TEMPLATES = BASE_URL + "feed/groups/list/";
    public static final String FEED_LIST_BY_CULTUREID = BASE_URL + "feed/list";
    public static final String SAVE_CHECKTRAY = BASE_URL + "checktray/save";
    public static final String SAVE_GROWTH_OBSV = BASE_URL + "growth/observation/save";
    public static final String LIST_GROWTH_OBSV = BASE_URL + "growth/observation/list/";
    public static final String SAVE_PCR_OBSV = BASE_URL + "pcr/observation/save";
    public static final String LIST_PCR_OBSV = BASE_URL + "pcr/observation/list/";
    public static final String SAVE_SOIL_OBSV = BASE_URL + "soil/observation/save";
    public static final String LIST_SOIL_OBSV = BASE_URL + "soil/observation/list/";
    public static final String SAVE_ANIMAL_OBSV = BASE_URL + "animal/observation/save";
    public static final String LIST_ANIMAL_OBSV = BASE_URL + "animal/observation/list/";
    public static final String SAVE_EXPENDS_OBSV = BASE_URL + "expends/observation/save";
    public static final String LIST_EXPENDS_OBSV = BASE_URL + "expends/observation/list/";
    public static final String SAVE_TREATMENT_OBSV = BASE_URL + "treatment/save";
    public static final String LIST_TREATMENT_OBSV = BASE_URL + "treatment/list/";
    public static final String SAVE_LAB_OBSV = BASE_URL + "lab/observation/save";
    public static final String LIST_LAB_OBSV = BASE_URL + "lab/observation/list/";
    public static final String SAVE_CHECKTRAY_OBSV = BASE_URL + "checktray/observation/save";
    public static final String LIST_CHECKTRAY = BASE_URL + "checktray/list/";
    public static final String CHECKTRAY_OBSV_LIST = BASE_URL + "checktray/observation/list/";
    public static final String SAVE_PRODUCTS = BASE_URL + "product/save";
    public static final String SAVE_PRODUCT_CATG = BASE_URL + "product/categories/save";
    public static final String GET_STOCKLIST = BASE_URL + "stock/list/";
    public static final String BRAND_SAVE = BASE_URL + "brand/save";
    public static final String QTY_CATG_SAVE = BASE_URL + "quantity/categories/save";
    public static final String LIST_PRODUCTS = BASE_URL + "product/list/";
    public static final String SAVE_STOCK = BASE_URL + "stock/save";
    public static final String LIST_STOCK = BASE_URL + "stock/list/";
    public static final String SAVE_INVOICE = BASE_URL + "invoice/save";
    public static final String LIST_INVOICE = BASE_URL + "invoice/list/";
    public static final String UPLOAD = BASE_URL + "files/upload";
    public static final String GET_DASHBOARD = BASE_URL + "dashboard/";
    public static final String CHAT_LIST = BASE_URL + "chat/list/";
    public static final String CHAT_SAVE = BASE_URL + "chat/save/";
    public static final String PREPARATION_SAVE = BASE_URL + "tank/preparation/save";
    public static final String STOCKING_SAVE = BASE_URL + "tank/stocking/save";
    public static final String STOCKING_LIST = BASE_URL + "tank/stocking/list/";
    public static final String PREPARATION_LIST = BASE_URL + "tank/preparation/list/";
    public static final String TANK_INFO = BASE_URL + "tank/info/";
    public static final String CHANGE_PASSWORD = BASE_URL + "user/updatePassword";


}
