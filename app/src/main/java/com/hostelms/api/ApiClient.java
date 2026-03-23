package com.hostelms.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;

/**
 * Thin wrapper around Volley for POST / GET calls to the PHP API layer.
 * The app can also continue to use Room (local DB) alongside this.
 */
public class ApiClient {

    public interface Callback {
        void onSuccess(String response);
        void onError(String error);
    }

    /** HTTP POST with a Map of String parameters */
    public static void post(Context ctx, String url,
                            Map<String, String> params, Callback cb) {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest req = new StringRequest(Request.Method.POST, url,
                cb::onSuccess,
                error -> cb.onError(
                        error.getMessage() != null ? error.getMessage() : "Network error")) {
            @Override
            protected Map<String, String> getParams() { return params; }
        };
        queue.add(req);
    }

    /** HTTP GET */
    public static void get(Context ctx, String url, Callback cb) {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest req = new StringRequest(Request.Method.GET, url,
                cb::onSuccess,
                error -> cb.onError(
                        error.getMessage() != null ? error.getMessage() : "Network error"));
        queue.add(req);
    }
}
