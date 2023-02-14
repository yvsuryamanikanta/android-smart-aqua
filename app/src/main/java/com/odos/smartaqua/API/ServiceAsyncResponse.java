package com.odos.smartaqua.API;


import org.json.JSONArray;
import org.json.JSONObject;

public interface ServiceAsyncResponse {

    void stringResponse(String service, String response, int serviceno);

    void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno);

    void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno);

}


