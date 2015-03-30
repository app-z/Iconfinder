package net.appz.iconfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.Icons;
import net.appz.iconfinder.Data.Iconsets;
import net.appz.iconfinder.Data.Style;
import net.appz.iconfinder.Data.Styles;

import java.util.Random;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private String TAG = "MainActivity>";

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

    private static final String urlIconSetsTmpl = "https://api.iconfinder.com" + "/v2/styles/%s/iconsets";

    private static final String urlIconsTmpl = "https://api.iconfinder.com"
            + "/v2/icons/search?query=%s&minimum_size=%d&maximum_size=%d&count=%d&offset=%d";

    private int count = 20;
    private int offset = 0;

    private int stylesPosition = -1;


    private Styles styles;
//    private Icons icons;

    // Request for Json Download
    private RequestQueue requestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if(!AppUtils.isNetworkAvailable(this)){
            AppUtils.showDialog(this, "Internet error", "Check internet connection!", true);
        }else {

            if (requestQueue == null)
                requestQueue = Volley.newRequestQueue(this);


            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
            Log.e(TAG, "onCreate : currentFragment = " + currentFragment);
            if (currentFragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(0, null))
                        .commit();
            }

            urlStyles += "?_=" + new Random().nextInt();
            Log.d(TAG, "Request => " + urlStyles);


            requestQueue.cancelAll(this);
            final GsonRequest gsonRequest = new GsonRequest(urlStyles, Styles.class, null, new Response.Listener<Styles>() {
                @Override
                public void onResponse(Styles styles) {
                    fillStyles(styles);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError != null)
                        Log.e(TAG, "volleyError: " + volleyError.getMessage());
                }
            });
            gsonRequest.setTag("Styles");
            requestQueue.add(gsonRequest);
        }

    }

    private void fillStyles(Styles styles) {
        this.styles = styles;
        int i=0;
        String [] stileTitles = new String[styles.getStyles().size()];
        for(Style style : styles.getStyles()) {
            stileTitles[i++] = style.getName();
        }

        // Set the adapter for the list view left menu
        mNavigationDrawerFragment.getDrawerListView().setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                stileTitles));

    }


    private void fillIconSets(Iconsets iconsets, final int position) {
        stylesPosition = position;
        mTitle = styles.getStyles().get(position).getName();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position, iconsets))
                .commit();
    }


    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        if(styles != null ) {
            // Download Iconsets
            String urlIconsets = String.format(urlIconSetsTmpl, styles.getStyles().get(position).getIdentifier());
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
                        Log.e(TAG, "volleyError: " + volleyError.getMessage());
                }
            });
            gsonRequest.setTag("Iconsets");
            requestQueue.add(gsonRequest);
        }
    }

    @Override
    public void onOptionsItemSelectedReset() {
        stylesPosition = -1;
        mTitle = getResources().getString(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(0, null))
                .commit();
    }

    public void onSectionAttached(int position) {
        /*
        if(styles != null && mTitle != null) {
            mTitle = styles.getStyles().get(position).getName();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(mTitle);
            Log.d(TAG, "Title = " + mTitle.toString());
        }*/
    }



    synchronized private void fillIcons(Icons icons, boolean firstPage) {
        Log.d(">>>", "Icons size = " + icons.getIcons().size());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
        Log.e(TAG, "fillIcons : currentFragment = "
                + currentFragment
                + " : isDestroyed = "
                + fragmentManager.isDestroyed());

        if(!fragmentManager.isDestroyed()) {

            if (currentFragment != null && currentFragment instanceof IconsGridFragment) {
                ((IconsGridFragment) currentFragment).addIcons(icons);
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, IconsGridFragment.newInstance(icons))
                        .commitAllowingStateLoss();
            }
        }
    }

    /*

        Get Icons by query and Stile

     */
    public void onQueryIcons(String query, final boolean firstPage) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int minimum_size = Integer.valueOf(prefs.getString("minimum_size_list", "16"));
        int maximum_size = Integer.valueOf(prefs.getString("maximum_size_list", "512"));

        offset = firstPage ? 0 : (offset += count);

        String urlIcons = String.format(urlIconsTmpl, query,
                minimum_size,
                maximum_size,
                count,
                offset);

        if (styles != null && stylesPosition != -1) {
            urlIcons += "&style=" + styles.getStyles().get(stylesPosition).getIdentifier();
        }

        Log.d(">>>", "urlIcons = " + urlIcons);

        final GsonRequest gsonRequest = new GsonRequest(urlIcons, Icons.class, null, new Response.Listener<Icons>() {
            @Override
            public void onResponse(Icons icons) {
                fillIcons(icons , firstPage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    Log.e(TAG, "volleyError: " + volleyError.getMessage());
            }
        });
        gsonRequest.setTag("Icons");
        requestQueue.add(gsonRequest);
    }


    /**
     *
     * More Icon download to grid
     *
     */
    public void onLazyLoadMore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString("query", "facebook");
        onQueryIcons(query, false);
    }


    public void onClickIcon(Icon icon) {
        Log.d(">>>", "Icons id = " + icon.getIconId());
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
        Log.d(TAG, "onStop");
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
