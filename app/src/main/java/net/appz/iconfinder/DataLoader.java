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
import net.appz.iconfinder.Data.Icons;
import net.appz.iconfinder.Data.Iconsets;
import net.appz.iconfinder.Data.Styles;

/**
 * Created by App-z.net on 02.04.15.
 */
public class DataLoader extends Loader<DataHolder> {
    private static final boolean DEBUG = true;
    private static final String TAG = "DataLoader>";

    public static final String ARGS_URL = "url";
    public static final String ARGS_LOADER_ID = "loaderId";

    private String urlFeed;

    private RequestQueue requestQueue;
    private DataHolder dataHolder = new DataHolder();

    public static final int LOADER_ICONS_ID = 1;
    public static final int LOADER_STYLES_ID = 2;
    public static final int LOADER_ICONSETS_ID = 3;

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

        if (DEBUG) Log.i(TAG, "Loader onForceLoad()");

        if(getId() == LOADER_STYLES_ID)
            doStylesRequest();
        else if ( getId() == LOADER_ICONS_ID )
            doIconsRequest();
        else if ( getId() == LOADER_ICONSETS_ID )
            doIconsetsRequest();

    }

    /**
     *
     * Get Iconsets
     */
    private void doIconsetsRequest() {
        final GsonRequest gsonRequest = new GsonRequest(urlFeed, Iconsets.class, null, new Response.Listener<Iconsets>() {
            @Override
            public void onResponse(Iconsets iconsets) {
                dataHolder.setIconsets(iconsets);
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

    /**
     *
     * get Styles
     */
    void doStylesRequest(){
        final GsonRequest gsonRequest = new GsonRequest(urlFeed, Styles.class, null, new Response.Listener<Styles>() {
            @Override
            public void onResponse(Styles styles) {
                dataHolder.setStyles(styles);
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

    /**
     *
     *  get Icons
     */
    void  doIconsRequest(){
        final GsonRequest gsonRequest = new GsonRequest(urlFeed, Icons.class, null, new Response.Listener<Icons>() {
            @Override
            public void onResponse(Icons icons) {
                dataHolder.setIcons(icons);
                deliverResult(dataHolder);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    if ( DEBUG ) Log.e(TAG, "volleyError: " + volleyError.getMessage());
                deliverResult(null);
            }
        });
        requestQueue.add(gsonRequest);
    }

}
