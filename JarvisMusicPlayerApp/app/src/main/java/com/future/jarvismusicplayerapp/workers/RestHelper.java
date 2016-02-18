package com.future.jarvismusicplayerapp.workers;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.future.jarvismusicplayerapp.Constants;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
                restResponseInterface.responseReceived(RestResponseInterface.SUCCESS, response);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.TAG, "Error while calling the API.");
                Log.e(Constants.TAG, "Json Data Sent : " + jsonData);
                Log.e(Constants.TAG, error.toString());
                restResponseInterface.responseReceived(RestResponseInterface.FAILURE, null);
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

    public void receiveData(final Context context, final RestResponseInterface restResponseInterface, String endPoint, final Map<String, String> parametersMap) {
        String url = Constants.BASE_URL + endPoint;

        String urlSuffix = "?";
        for (Map.Entry<String, String> param : parametersMap.entrySet()) {
            urlSuffix += param.getKey() + "=" + param.getValue() + "&";
        }
        if (urlSuffix.length() > 1) {
            urlSuffix = urlSuffix.substring(0, urlSuffix.length() - 1);
            url += urlSuffix;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(Constants.TAG, "Volley Received response: ");
                Log.d(Constants.TAG, response);
                restResponseInterface.responseReceived(RestResponseInterface.SUCCESS, response);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.TAG, "Error while calling the API.");
                Log.e(Constants.TAG, "Json Data Received : " );
                Log.e(Constants.TAG, error.toString());
                restResponseInterface.responseReceived(RestResponseInterface.FAILURE, null);          //Always pick response data only when success
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);

    }
    public static class MultipartRequest extends Request<String> {

        private MultipartEntityBuilder entity = MultipartEntityBuilder.create();

        private static final String FILE_PART_NAME = "file";
        private static final String STRING_PART_NAME = "text";

        private final Response.Listener<String> mListener;
        private final File mFilePart;
        private final String mStringPart;

        public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, File file, String stringPart)
        {
            super(Method.POST, url, errorListener);

            mListener = listener;
            mFilePart = file;
            mStringPart = stringPart;
            buildMultipartEntity();
        }

        private void buildMultipartEntity()
        {
            entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
            try
            {
                entity.addPart(STRING_PART_NAME, new StringBody(mStringPart));
            }
            catch (UnsupportedEncodingException e)
            {
                VolleyLog.e("UnsupportedEncodingException");
            }
        }

        @Override
        public String getBodyContentType()
        {
            return entity.getContentType().getValue();

        }

        @Override
        public byte[] getBody() throws AuthFailureError
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try
            {
                entity.writeTo(bos);
            }
            catch (IOException e)
            {
                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response)
        {
            return Response.success("Uploaded", getCacheEntry());
        }

        @Override
        protected void deliverResponse(String response)
        {
            mListener.onResponse(response);
        }
    }

}
