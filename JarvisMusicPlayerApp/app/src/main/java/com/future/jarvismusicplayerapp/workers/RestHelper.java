package com.future.jarvismusicplayerapp.workers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

   public void sendData(final String jsonData, final Context context) {
        String url = Constants.BASE_URL + Constants.USER_REGISTRATION_END_POINT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Constants.TAG, "Volley Received response: ");
                Log.d(Constants.TAG, response);
                Toast.makeText(context,"User Registration Completed.", Toast.LENGTH_LONG).show();
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.TAG, "Error while calling the API.");
                Log.e(Constants.TAG, "Json Data Sent : " + jsonData);
                Log.e(Constants.TAG, error.toString());
                Toast.makeText(context, "Error while registering user.", Toast.LENGTH_LONG).show();
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

}
