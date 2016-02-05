package com.future.jarvismusicplayerapp.workers;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.future.jarvismusicplayerapp.Constants;

/**
 * Created by arungu on 2/3/2016.
 */
public class RestHelper {

   public void sendData(final String jsonData, final Context context, final RestResponseInterface restResponseInterface, String endPoint) {
        String url = Constants.BASE_URL + endPoint;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Constants.TAG, "Volley Received response: ");
                Log.d(Constants.TAG, response);
                restResponseInterface.responseReceived(RestResponseInterface.SUCCESS);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.TAG, "Error while calling the API.");
                Log.e(Constants.TAG, "Json Data Sent : " + jsonData);
                Log.e(Constants.TAG, error.toString());
                restResponseInterface.responseReceived(RestResponseInterface.FAILURE);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonData.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void receiveData(final Context context, final RestResponseInterface restResponseInterface, String endPoint) {
        String url = Constants.BASE_URL + endPoint;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Constants.TAG, "Volley Received response: ");
                Log.d(Constants.TAG, response);
                restResponseInterface.responseReceived(RestResponseInterface.SUCCESS);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.TAG, "Error while calling the API.");
                Log.e(Constants.TAG, "Json Data Received : " );
                Log.e(Constants.TAG, error.toString());
                restResponseInterface.responseReceived(RestResponseInterface.FAILURE);
            }
        }) {


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);
    }

}
