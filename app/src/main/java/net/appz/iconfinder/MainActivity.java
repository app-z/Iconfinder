package net.appz.iconfinder;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.Icons;
import net.appz.iconfinder.Data.Iconset;
import net.appz.iconfinder.Data.Iconsets;
import net.appz.iconfinder.Data.Style;
import net.appz.iconfinder.Data.Styles;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    // Instantiate the RequestQueue.
    //private String url = "https://api.iconfinder.com/v2/styles?count=10&after=&_=1427589491914";
    private String urlStyles = "https://api.iconfinder.com" + "/v2/styles";

    private static final String urlIconsetsTmpl = "https://api.iconfinder.com" + "/v2/styles/%s/iconsets";
    private String urlIconsets;

    private String TAG = "MainActivity>";

    private Styles styles;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        urlStyles += "?_=" + new Random().nextInt();
        Log.d(TAG, "Request => " + urlStyles);


        final GsonRequest gsonRequest = new GsonRequest(urlStyles, Styles.class, null, new Response.Listener<Styles>() {
            @Override
            public void onResponse(Styles styles) {
                fillStyles(styles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null)
                    Log.e(TAG, volleyError.getMessage());
            }
        });
        gsonRequest.setTag("Styles");
        requestQueue.add(gsonRequest);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(0, null))
                .commit();
    }

    private void fillStyles(Styles styles) {
        this.styles = styles;
        int i=0;
        String [] stileTitles = new String[styles.getStyles().size()];
        for(Style style : styles.getStyles()) {
            stileTitles[i++] = style.getName();
        }

        // Set the adapter for the list view
        //mNavigationDrawerFragment.getDrawerListView().setAdapter(new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_activated_1, stileTitles));
        mNavigationDrawerFragment.getDrawerListView().setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                stileTitles));

    }

    private void fillIconSets(Iconsets iconsets, final int position) {
        for(Iconset iconset : iconsets.getIconsets()){
            Log.d(TAG, iconset.getName());
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position, iconsets))
                .commit();
    }

    private void fillIcons(Icons icons) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, IconsGridFragment.newInstance(icons))
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(final int position) {

        if(styles != null ) {
            // Download Iconsets
            urlIconsets = String.format(urlIconsetsTmpl, styles.getStyles().get(position).getIdentifier());
            Log.d(TAG, "urlIconsets = " + urlIconsets);
            final GsonRequest gsonRequest = new GsonRequest(urlIconsets, Iconsets.class, null, new Response.Listener<Iconsets>() {
                @Override
                public void onResponse(Iconsets iconsets) {
                    fillIconSets(iconsets, position);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError != null)
                        Log.e(TAG, volleyError.getMessage());
                }
            });
            gsonRequest.setTag("Iconsets");
            requestQueue.add(gsonRequest);
        }


    }

    @Override
    public void onOptionsItemSelectedReset() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(0, null))
                .commit();
    }

    public void onSectionAttached(int position) {
        if(styles != null && mTitle != null) {
            mTitle = styles.getStyles().get(position).getName();
            Log.d(TAG, "Title = " + mTitle.toString());

        }
    }

    /*

        Get Icons by query and Stile

     */
    public void onQueryIcons(String urlIcons) {
        final GsonRequest gsonRequest = new GsonRequest(urlIcons, Icons.class, null, new Response.Listener<Icons>() {
            @Override
            public void onResponse(Icons icons) {
                for (Icon icon : icons.getIcons()){
                    Log.d(TAG, icon.getType() + " : " + icon.getIconId());
                }
                fillIcons(icons);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    Log.e(TAG, "Error:" + volleyError.getMessage());
            }
        });
        gsonRequest.setTag("Icons");
        requestQueue.add(gsonRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
