package net.appz.iconfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class AppUtils {

    private static final String TAG = "AppUtils";


    public static final String ICONFINDER_PREF_NAME = "iconfinder_xyz";

/*    private static final String MINIMUM_SIZE = "minimum_size";
    private static final String MAXIMUM_SIZE = "maximum_size";


    public static void setMinimumSize(Context context, int minimum_size) {
        SharedPreferences pref = context.getSharedPreferences(
                ICONFINDER_PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MINIMUM_SIZE, minimum_size).commit();
    }
    public static int getMinimumSize(Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                ICONFINDER_PREF_NAME, 0);
        return pref.getInt(MINIMUM_SIZE, 16);
    }

    public static void setMaximumSize(Context context, int maximum_size) {
        SharedPreferences pref = context.getSharedPreferences(
                ICONFINDER_PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MAXIMUM_SIZE, maximum_size).commit();
    }
    public static int getMaximumSize(Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                ICONFINDER_PREF_NAME, 0);
        return pref.getInt(MAXIMUM_SIZE, 512);
    }
*/
    //
    //
    //
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null, otherwise check
        // if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    /*
	 * 
	 * Show modal dialog with resource string
	 * 
	 */
    static void showDialog(Context context, int res_title, int res_message, boolean isFinish) {
        showDialog(context,
                context.getResources().getString(res_title),
                context.getResources().getString(res_message),
                isFinish);
    }

    /*
	 * 
	 * 
	 * 
	 */
    static void showDialog(final Context context, String title, String message, final boolean isFinish) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.dismiss();
                                if (isFinish)
                                    ((Activity) context).finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

/*
    final int ICONS_HANDLER = 1;
    final int STILES_HANDLER = 2;
    final int ICONSETS_HANDLER = 3;

    void doLoaderHandler(Loader<DataHolder> loader, DataHolder data){
        /*        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        if(loader.getId() == DataHolder.LOADER_ICONS_ID){
            offset += count;    // Prepare for next lazy load
            //b.putParcelable("Icons", (Icons) data.getData(loader.getId()));
            b.putParcelable("Icons", data.getIcons());
            msg.what = ICONS_HANDLER;
        } else if(loader.getId() == DataHolder.LOADER_STYLES_ID){
            b.putParcelable("Styles", data.getStyles());
            msg.what = STILES_HANDLER;
        } else if(loader.getId() == DataHolder.LOADER_ICONSETS_ID){
            b.putParcelable("IconSets", data.getIconsets());
            msg.what = ICONSETS_HANDLER;
        }
        msg.setData(b);
        mHandler.sendMessage(msg);
    }


    final Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            Bundle b;
            b=msg.getData();
            if(msg.what == ICONS_HANDLER){
                Icons icons = b.getParcelable("Icons");
                fillIcons(icons);
            } else if(msg.what == STILES_HANDLER){
                Styles styles = b.getParcelable("Styles");
                fillStyles(styles);
            } else if(msg.what == ICONSETS_HANDLER){
                Iconsets iconSets = b.getParcelable("IconSets");
                fillIconSets(iconSets);
            }
            super.handleMessage(msg);
        }
    };


    */


    /**
     *
     * Remove Fragment if exist
     */
    void removeFragmentByTag(FragmentManager fragmentManager, String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null){
            fragmentManager.beginTransaction().remove(fragment).commit();
            Log.e(TAG, "Remove fragment: " + fragment.getTag());
        }
    }

    /**
     *
     * Hide Fragment if exist
     */
    void hideFragmentByTag(FragmentManager fragmentManager, String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null){
            fragmentManager.beginTransaction().hide(fragment).commit();
            Log.e(TAG, "Hide fragment: " + fragment.getTag());
        }
    }

    /**
     *
     * Show Fragment if exist
     */
    void showFragmentByTag(FragmentManager fragmentManager, String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null){
            fragmentManager.beginTransaction().show(fragment).commit();
            Log.e(TAG, "Show fragment: " + fragment.getTag());
        }
    }


}
