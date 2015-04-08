package net.appz.iconfounder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import net.appz.iconfounder.Data.DataHolder;
import net.appz.iconfounder.Data.Icon;
import net.appz.iconfounder.Data.Icons;
import net.appz.iconfounder.Data.Iconsets;
import net.appz.iconfounder.Data.Style;
import net.appz.iconfounder.Data.Styles;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<DataHolder>,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        OverlayMessageFragment.OverlayMessageFragmentCallbacks{

    private static final boolean DEBUG = true;
    private String TAG = this.getClass().getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //private String url = "https://api.iconfinder.com/v2/styles?count=10&after=&_=1427589491914";
    private static final String urlStylesTempl = "https://api.iconfinder.com" + "/v2/styles?_=%d";

    private static final String urlIconSetsTmpl = "https://api.iconfinder.com" + "/v2/styles/%s/iconsets";

    private static final String urlIconsTmpl = "https://api.iconfinder.com"
            + "/v2/icons/search?query=%s&minimum_size=%d&maximum_size=%d&count=%d&offset=%d";

    private int count = 20;
    private /* static */ int offset = 0;

    private int stylesPosition = -1;

    private Styles styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            offset = savedInstanceState.getInt("offset");
            stylesPosition = savedInstanceState.getInt("stylesPosition");
        }

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

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
            if ( DEBUG ) Log.d(TAG, "onCreate : currentFragment = " + currentFragment);
            if (currentFragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container,
                                PlaceholderFragment.newInstance(0, null),
                                PlaceholderFragment.class.getSimpleName())
                        .commit();
            }

            String urlStyles = String.format(urlStylesTempl, new Random().nextInt());
            Bundle bundle = new Bundle();
            bundle.putString(DataLoader.ARGS_URL, urlStyles);
            getSupportLoaderManager().restartLoader(DataHolder.LOADER_STYLES_ID, bundle, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("offset", offset);
        savedInstanceState.putInt("stylesPosition", stylesPosition);
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
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                        ? android.R.layout.simple_list_item_activated_1
                        : android.R.layout.simple_list_item_1,
                android.R.id.text1,
                stileTitles));
        if (DEBUG) Log.d(TAG, "fillStyles : " + Arrays.toString(stileTitles));
    }

    private void fillIconSets(Iconsets iconsets) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // update the main content by replacing fragments
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        PlaceholderFragment.newInstance(stylesPosition, iconsets),
                        PlaceholderFragment.class.getSimpleName())
                .commit();
    }

    synchronized private void fillIcons(Icons icons) {
        if (DEBUG) Log.d(TAG, "Icons size = " + icons.getIcons().size() + ": Total = " + icons.getTotalCount());

        // Resolved After Loader implementation
        //if(!fragmentManager.isDestroyed()) {    // Check problem after rotation screen

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment iconsGridFragment = fragmentManager.findFragmentByTag(IconsGridFragment.class.getSimpleName());
        if (iconsGridFragment != null) {
            ((IconsGridFragment) iconsGridFragment).addIcons(icons);
        } else {
            iconsGridFragment = IconsGridFragment.newInstance(icons);
            // Add the fragment to the activity, pushing this transaction on to the back stack.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, iconsGridFragment, IconsGridFragment.class.getSimpleName());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        if(styles != null ) {
            stylesPosition = position;
            mTitle = styles.getStyles().get(position).getName();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(mTitle);

            // Destroy current Loader if it's not finish
            destroyLoaders();

            String urlIconsets = String.format(urlIconSetsTmpl, styles.getStyles().get(position).getIdentifier());
            // Download Iconsets
            Bundle bundle = new Bundle();
            bundle.putString(DataLoader.ARGS_URL, urlIconsets);
            getSupportLoaderManager().restartLoader(DataHolder.LOADER_ICONSETS_ID, bundle, this);
        }
    }


    @Override
    public void onOptionsItemSelectedReset() {
        // Destroy current Loader if it's not finish
        destroyLoaders();

        String urlStyles = String.format(urlStylesTempl, new Random().nextInt());
        Bundle bundle = new Bundle();
        bundle.putString(DataLoader.ARGS_URL, urlStyles);
        getSupportLoaderManager().restartLoader(DataHolder.LOADER_STYLES_ID, bundle, this);

        stylesPosition = -1;
        mTitle = getResources().getString(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        PlaceholderFragment.newInstance(0, null),
                        PlaceholderFragment.class.getSimpleName())
                .commit();
    }


    @Override
    public Loader<DataHolder> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this, args);
    }


    @Override
    public void onOverlayClick() {
        closeOverlayFragment();
    }

    void closeOverlayFragment(){
        Fragment fragment = getSupportFragmentManager().
                findFragmentByTag(OverlayMessageFragment.class.getSimpleName());
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.remove(fragment).commit();
        }
    }


    private void closeOverlayDelay(final int closeDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                closeOverlayFragment();
                // Reset flag fo continue load after error gone
                Fragment fragment = getSupportFragmentManager().
                        findFragmentByTag(IconsGridFragment.class.getSimpleName());
                if (fragment != null)
                    ((IconsGridFragment)fragment).resetLoadingFlag();

            }
        }, closeDelay);
    }

    private void showOverlay(String text, int closeDelay){
        Fragment fragment = getSupportFragmentManager().
                findFragmentByTag(OverlayMessageFragment.class.getSimpleName());
        if(fragment == null) {
            Fragment overlayMessageFragment = OverlayMessageFragment.newInstance(text);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.overlay, overlayMessageFragment, OverlayMessageFragment.class.getSimpleName());
            ft.commit();
            closeOverlayDelay(closeDelay);
        }
        if (DEBUG) Log.e(TAG, "showOverlay: " + fragment  + " : " + text + " : " + closeDelay);
    }

    @Override
    public void onLoadFinished(final Loader<DataHolder> loader, final DataHolder data) {
        VolleyError volleyError = data.getError();
        if (volleyError != null) {
            if (DEBUG) Log.e(TAG, "volleyError message: " + volleyError.getMessage());
            NetworkResponse networkResponse = volleyError.networkResponse;
            if (networkResponse != null && networkResponse.statusCode == 429) {
                // HTTP Status Code: 429
                if (DEBUG) Log.e(TAG, "volleyError statusCode: " + networkResponse.statusCode);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        showOverlay("Server Error 429. Too many requests. Try later", 5000);
                    }
                });
                return;
            }
            return;
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if(loader.getId() == DataHolder.LOADER_ICONS_ID){
                    offset += count;    // Prepare for next lazy load
                    fillIcons(data.getIcons());
                } else if(loader.getId() == DataHolder.LOADER_STYLES_ID){
                    fillStyles(data.getStyles());
                } else if(loader.getId() == DataHolder.LOADER_ICONSETS_ID){
                    fillIconSets(data.getIconsets());
                }
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<DataHolder> loader) {
        if(loader.getId() == DataHolder.LOADER_ICONS_ID)
            offset = 0;
    }


    /**
     *
     *  Get Icons by query and Stile
     */
    void queryIcons(String query){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int minimum_size = Integer.valueOf(prefs.getString("minimum_size_list", "16"));
        int maximum_size = Integer.valueOf(prefs.getString("maximum_size_list", "512"));

        String urlIcons = String.format(urlIconsTmpl, query,
                minimum_size,
                maximum_size,
                count,
                offset);

        if (styles != null && stylesPosition != -1) {
            urlIcons += "&style=" + styles.getStyles().get(stylesPosition).getIdentifier();
        }

        if ( DEBUG ) Log.d(">>>", "urlIcons = " + urlIcons);

        Bundle bundle = new Bundle();
        bundle.putString(DataLoader.ARGS_URL, urlIcons);
        getSupportLoaderManager().restartLoader(DataHolder.LOADER_ICONS_ID, bundle, this);
    }


    /**
     *
     *  On click search button
     */
    public void onQueryIcons(String query) {
        offset = 0;
        queryIcons(query);
    }

    /**
     *
     * More Icon download to grid
     *
     */
    public void onLazyLoadMore() {
        // showOverlay("Load more from " + offset + ". Wait...", 3000);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString("query", "facebook");
        queryIcons(query);
    }


    /**
     *
     * Click on grid icons item
     * @param icon
     */
    public void onClickIcon(Icon icon) {
        if ( DEBUG ) Log.d(">>>", "Icons id = " + icon.getIconId());

        Fragment iconsDetailFragment = IconsDetailFragment.newInstance(icon);
        // Add the fragment to the activity, pushing this transaction on to the back stack.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, iconsDetailFragment, IconsDetailFragment.class.getSimpleName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     *
     * Close Save Icon Fragment
     */
    public void onCloseSaveIcon() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     *
     * Destroy all loaders
     */
    private void destroyLoaders(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.destroyLoader(DataHolder.LOADER_ICONS_ID);
        loaderManager.destroyLoader(DataHolder.LOADER_ICONSETS_ID);
        loaderManager.destroyLoader(DataHolder.LOADER_STYLES_ID);
    }

    @Override
    protected void onStop () {
        super.onStop();
        //destroyLoaders();

        List<Fragment> allFragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : allFragments) {
            if (fragment instanceof IconsGridFragment) {
                ((IconsGridFragment)fragment).resetLoadingFlag();
            }
        }
        if ( DEBUG ) Log.d(TAG, "onStop");
    }


    public void onSectionAttached(int position) {
        /*
        if(styles != null && mTitle != null) {
            mTitle = styles.getStyles().get(position).getName();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(mTitle);
            if ( DEBUG ) Log.d(TAG, "Title = " + mTitle.toString());
        }*/
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
