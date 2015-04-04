package net.appz.iconfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.appz.iconfinder.Data.DataHolder;

/**
 * Created by App-z.net on 02.04.15.
 */
public class DataLoader extends Loader<DataHolder> {
    private static final boolean DEBUG = true;
    private static final String TAG = "DataLoader>";

    public static final String ARGS_URL = "url";

    private String urlFeed;

    private RequestQueue requestQueue;
    private DataHolder dataHolder = new DataHolder();

    public DataLoader(Context context, Bundle bundle) {
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
        if ( DEBUG ) Log.i(TAG, "Loader onStopLoading()");
        requestQueue.cancelAll(this);
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        if ( DEBUG ) Log.i(TAG, "Loader onReset()");
        requestQueue.cancelAll(this);
        super.onReset();
    }


    @Override
    public void onForceLoad() {
        super.onForceLoad();
        if ( DEBUG ) Log.d(TAG, "Loader onForceLoad() : feedUrl = " + urlFeed);
        doRequest(DataHolder.getClazz(getId()));
    }

    /**
     *
     * Get Data
     */
    private void doRequest(Class<?> clazz) {
        final GsonRequest gsonRequest = new GsonRequest(urlFeed,
                clazz,
                null,
                new Response.Listener<DataHolder.DataHolderItem>() {
            @Override
            public void onResponse(DataHolder.DataHolderItem data) {
                dataHolder.setData(getId(), data);
                deliverResult(dataHolder);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    if (DEBUG) Log.e(TAG, "volleyError: " + volleyError.getMessage());
                deliverResult(null);
            }
        });
        requestQueue.add(gsonRequest);
    }

}
