package net.appz.iconfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.appz.iconfinder.Data.Icons;

/**
 * Created by App-z.net on 01.04.15.
 */
public class IconsLoader extends Loader<Icons> {

    public static final String ARGS_URL = "url";
    private static final String TAG = "IconsLoader>";

    private static final String REQ_TAG = "Icons";

    private String urlFeed;

    RequestQueue requestQueue;

    public IconsLoader(Context context, Bundle bundle) {
        super(context);
        urlFeed = bundle.getString(ARGS_URL);

        requestQueue = Volley.newRequestQueue(context);

        // run only once
        onContentChanged();

    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        Log.i(TAG, "Loader onStopLoading()");
        requestQueue.cancelAll(REQ_TAG);
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        Log.i(TAG, "Loader onReset()");
        requestQueue.cancelAll(REQ_TAG);
        super.onReset();
    }

    @Override
    public void onForceLoad() {
        super.onForceLoad();

        Log.i(TAG, "Loader onForceLoad()");

        final GsonRequest gsonRequest = new GsonRequest(urlFeed, Icons.class, null, new Response.Listener<Icons>() {
            @Override
            public void onResponse(Icons icons) {
                deliverResult(icons);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    Log.e(TAG, "volleyError: " + volleyError.getMessage());
                deliverResult(null);
            }
        });
        gsonRequest.setTag(REQ_TAG);
        requestQueue.add(gsonRequest);
    }
}
