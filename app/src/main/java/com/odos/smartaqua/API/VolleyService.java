package com.odos.smartaqua.API;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.odos.smartaqua.R;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Volley Service to make the rest calls
 */
public class VolleyService {

    private static ServiceAsyncResponse delegate;

    /**
     * service request of type GET call with parameters
     *
     * @param context              -- returns context of class
     * @param requesttype          -- returns int value
     * @param serviceUrl           --returns service url
     * @param quertstrings         -- returns hashmap for query params
     * @param headermap            -- returns hashmap for header params
     * @param serviceAsyncResponse -- returns reference of serviceAsyncResponse interface
     */
    public static void volleyGetRequest(final Context context, final String requesttype, String serviceUrl, Map<String, String> quertstrings,
                                        Map<String, String> headermap, ServiceAsyncResponse serviceAsyncResponse, int serviceno, boolean loader) {

        Log.e(AquaConstants.TAG, "" + serviceUrl);
        delegate = serviceAsyncResponse;
        String arguments = null;
        try {
            if (quertstrings != null) {
                // check if this is not null only then send the arguments for get
                for (Object o : quertstrings.entrySet()) {

                    Map.Entry pairs = (Map.Entry) o;
                    if (arguments == null) {
                        arguments = "?";
                    } else {
                        arguments += "&";
                    }
                    arguments += URLEncoder.encode(pairs.getKey().toString(), context.getString(R.string.utfEncodingString)) + "=" + URLEncoder.encode(pairs.getValue().toString(), context.getString(R.string.utfEncodingString));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arguments != null) {
            serviceUrl = serviceUrl + arguments;
        }
        //final ProgressDialog progressIndicator = getProgressIndicator(context);
        final Loader progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        if (requesttype.equalsIgnoreCase(context.getString(R.string.stringrequest))) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, serviceUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (delegate != null) {
                        // System.out.println(response);
                        delegate.stringResponse(requesttype, response, serviceno);
                    }
                    if (loader) {
                        progressIndicator.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headermap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headermap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonobjectrequest))) {


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, serviceUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (delegate != null) {
                        delegate.jsonObjectResponse(requesttype, response, serviceno);
                    }
                    if (loader) {
                        progressIndicator.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headermap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headermap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);


        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonarrayrequest))) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, serviceUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (delegate != null) {
                        delegate.jsonArrayResponse(requesttype, response, serviceno);
                    }
                    if (loader) {
                        progressIndicator.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headermap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headermap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonArrayRequest);
        }


    }


    /**
     * service request of type POST call with parameters
     *
     * @param context              -- returns context of class
     * @param requesttype          -- returns int value
     * @param serviceUrl           --returns service url
     * @param paramsmap            -- returns hashmap for body params
     * @param headersmap           -- returns hashmap for header params
     * @param serviceAsyncResponse -- returns reference of serviceAsyncResponse interface
     */


    public static void volleyservicePostRequest(final Context context, final String requesttype, String serviceUrl, Map<String, Object> paramsmap,
                                                Map<String, String> headersmap, ServiceAsyncResponse serviceAsyncResponse, int serviceno, boolean loader) {
        delegate = serviceAsyncResponse;
        final Loader progressIndicator = getProgressIndicator(context);
        // final ProgressDialog progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        if (requesttype.equalsIgnoreCase(context.getString(R.string.stringrequest))) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, serviceUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (delegate != null) {
                            delegate.stringResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonobjectrequest))) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serviceUrl, new JSONObject(paramsmap),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (delegate != null) {
                                    delegate.jsonObjectResponse(requesttype, response, serviceno);
                                }
                                if (loader) {
                                    progressIndicator.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }

            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonarrayrequest))) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serviceUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if (delegate != null) {
                            delegate.jsonArrayResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonArrayRequest);
        }
    }


    /**
     * service request of type PUT call with parameters
     *
     * @param context               -- returns context of class
     * @param requesttype           -- returns int value
     * @param serviceUrl            --returns service url
     * @param paramsmap             -- returns hashmap for body params
     * @param headersmap            -- returns hashmap for header params
     * @param serviceAsyncResponset -- returns reference of serviceAsyncResponse
     */


    public static void volleyservicePutRequest(final Context context, final String requesttype, String serviceUrl, Map<String, String> paramsmap,
                                               Map<String, String> headersmap, ServiceAsyncResponse serviceAsyncResponset, int serviceno, boolean loader) {

        delegate = serviceAsyncResponset;
        final Loader progressIndicator = getProgressIndicator(context);
        //  final ProgressDialog progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        if (requesttype.equalsIgnoreCase(context.getString(R.string.stringrequest))) {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, serviceUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (delegate != null) {
                            delegate.stringResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonobjectrequest))) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, serviceUrl, new JSONObject(paramsmap),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (delegate != null) {
                                    delegate.jsonObjectResponse(requesttype, response, serviceno);
                                }
                                if (loader) {
                                    progressIndicator.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }

            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonarrayrequest))) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.PUT, serviceUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if (delegate != null) {
                            delegate.jsonArrayResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonArrayRequest);
        }
    }


    /**
     * service request of type DELETE call with parameters
     *
     * @param context              -- returns context of class
     * @param requesttype          -- returns int value
     * @param serviceUrl           --returns service url
     * @param paramsmap            -- returns hashmap for body params
     * @param headersmap           -- returns hashmap for header params
     * @param serviceAsyncResponse -- returns reference of serviceAsyncResponse
     */


    public static void volleyserviceDeleteRequest(final Context context, final String requesttype, String serviceUrl, Map<String, String> paramsmap,
                                                  Map<String, String> headersmap, ServiceAsyncResponse serviceAsyncResponse, int serviceno, boolean loader) {

        delegate = serviceAsyncResponse;
        //  final ProgressDialog progressIndicator = getProgressIndicator(context);
        final Loader progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        if (requesttype.equalsIgnoreCase(context.getString(R.string.stringrequest))) {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, serviceUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (delegate != null) {
                            delegate.stringResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonobjectrequest))) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, serviceUrl, new JSONObject(paramsmap),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (delegate != null) {
                                    delegate.jsonObjectResponse(requesttype, response, serviceno);
                                }
                                if (loader) {
                                    progressIndicator.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }

            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } else if (requesttype.equalsIgnoreCase(context.getString(R.string.jsonarrayrequest))) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, serviceUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if (delegate != null) {
                            delegate.jsonArrayResponse(requesttype, response, serviceno);
                        }
                        if (loader) {
                            progressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    processError(error, progressIndicator, context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    if (paramsmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : paramsmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            params.put(key, value);
                        }
                    }
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    if (headersmap != null) {
                        // check if this is not null only then send the arguments for get
                        for (Object o : headersmap.entrySet()) {
                            Map.Entry pairs = (Map.Entry) o;
                            String key = pairs.getKey().toString();
                            String value = pairs.getValue().toString();
                            headers.put(key, value);
                        }
                    }
                    return headers;
                }
            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    45000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonArrayRequest);
        }
    }


    public static void volleyImagetoByArrayUpload(final Context context, final String requesttype, String serviceUrl, Map<String, String> paramsmap,
                                                  Map<String, String> headersmap, ServiceAsyncResponse serviceAsyncResponse, int serviceno, boolean loader) {

        delegate = serviceAsyncResponse;
        //final ProgressDialog progressIndicator = getProgressIndicator(context);
        final Loader progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serviceUrl, new JSONObject(paramsmap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (delegate != null) {
                                delegate.jsonObjectResponse(requesttype, response, serviceno);
                            }
                            if (loader) {
                                progressIndicator.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                processError(error, progressIndicator, context);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {

                HashMap<String, String> headers = new HashMap<>();
                headers.put(context.getString(R.string.contentType), "application/json");
                try {
                    String source = "lK3S2ejEJYszKOS1uTR97SrRnVAoKv47JMgdEkLd:2gslre9izb";
                    byte[] byteArray = source.getBytes("UTF-8");
                    String bs = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    headers.put("Authorization", "Basic " + bs);
                    headers.put("X-Api-Key", "lK3S2ejEJYszKOS1uTR97SrRnVAoKv47JMgdEkLd");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                45000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public static void VolleyMultipartRequest(Context context, Bitmap bitmap,
                                              String requesttype, String serviceUrl, ServiceAsyncResponse serviceAsyncResponse, int serviceno, boolean loader,
                                              String filetype) {

        delegate = serviceAsyncResponse;
        final Loader progressIndicator = getProgressIndicator(context);
        if (loader) {
            progressIndicator.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, serviceUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            if (delegate != null) {
                                delegate.jsonObjectResponse(requesttype, obj, serviceno);
                            }
                            if (loader) {
                                progressIndicator.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        processError(error, progressIndicator, context);
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<String, DataPart>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("filetype", filetype);
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                45000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(volleyMultipartRequest);
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * ProgressDialog to get progress dialog for showing
     *
     * @param context -- returns context of class
     */

    private static Loader getProgressIndicator(Context context) {

        Loader loader = new Loader(context);
        /*ProgressDialog progressIndicator = new ProgressDialog(context);
        progressIndicator.setIndeterminate(true);
        progressIndicator.setMessage(context.getString(R.string.pleaseWait));
        progressIndicator.setCancelable(true);
        progressIndicator.setCanceledOnTouchOutside(false);*/
        return loader;


    }


    /**
     * processError to process service response Error
     *
     * @param error             --- returns volley error
     * @param progressIndicator -- returns progress dialog refreence
     * @param context           -- returns context of class
     */
    private static void processError(VolleyError error, Loader progressIndicator, Context context) {

        Log.e(AquaConstants.TAG, "" + error.toString());


        try {
            //NetworkResponse networkResponse = error.networkResponse;
            /*if (networkResponse != null) {
                Helper.showMessage(context, "Error. HTTP Status Code:" + networkResponse.statusCode, AquaConstants.FINISH);
                progressIndicator.dismiss();
            }*/
            if (error instanceof TimeoutError) {
                Helper.showMessage(context, context.getString(R.string.TimeoutErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();
            } else if (error instanceof NoConnectionError) {
                Helper.showMessage(context, context.getString(R.string.NoConnectionErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();

            } else if (error instanceof AuthFailureError) {
                Helper.showMessage(context, context.getString(R.string.AuthFailureErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();

            } else if (error instanceof ServerError) {
                Helper.showMessage(context, context.getString(R.string.ServerErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();

            } else if (error instanceof NetworkError) {
                Helper.showMessage(context, context.getString(R.string.NetworkErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();

            } else if (error instanceof ParseError) {
                Helper.showMessage(context, context.getString(R.string.ParseErrorMessage), AquaConstants.FINISH);
                progressIndicator.dismiss();
            }
        } catch (Exception ex) {
            progressIndicator.dismiss();
        }
    }
}


