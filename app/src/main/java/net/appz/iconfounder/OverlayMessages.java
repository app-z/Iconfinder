package net.appz.iconfounder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by App-z on 08.04.15.
 *
 * Not include in project. For future use
 */
public class OverlayMessages {

    private static final boolean DEBUG = true;
    private String TAG = this.getClass().getSimpleName();

    private final FragmentManager fragmentManeger;
    Context context;

    public OverlayMessages(Context context){
        this.context = context;
        fragmentManeger = ((FragmentActivity)context).getSupportFragmentManager();
    }


    void closeOverlayFragment(){
        Fragment fragment = fragmentManeger.
                findFragmentByTag(OverlayMessageFragment.class.getSimpleName());
        //if(fragment != null){
        //    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //    ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        //    //ft.remove(fragment).commit();
        //}
        fragmentManeger.popBackStack(OverlayMessageFragment.class.getSimpleName(),
                1/* FragmentManager.POP_BACK_STACK_INCLUSIVE */);
        if (DEBUG) Log.e(TAG, "closeOverlayDelay: " + fragment);
    }


    private void closeOverlayDelay(final int closeDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                closeOverlayFragment();
                // Reset flag fo continue load after error gone
                Fragment fragment = fragmentManeger.
                        findFragmentByTag(IconsGridFragment.class.getSimpleName());
                if (fragment != null)
                    ((IconsGridFragment)fragment).resetLoadingFlag();

            }
        }, closeDelay);
    }

    private void showOverlay(String text, int closeDelay){
        Fragment fragment = fragmentManeger.
                findFragmentByTag(OverlayMessageFragment.class.getSimpleName());
        //if(fragment == null) {
        Fragment overlayMessageFragment = OverlayMessageFragment.newInstance(text);
        FragmentTransaction ft = fragmentManeger.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.overlay, overlayMessageFragment, OverlayMessageFragment.class.getSimpleName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(OverlayMessageFragment.class.getSimpleName());
        ft.commit();

        closeOverlayDelay(closeDelay);
        //}
        if (DEBUG) Log.e(TAG, "showOverlay: " + fragment  + " : " + text + " : " + closeDelay);
    }

    private void clearBackStack() {
        if (fragmentManeger.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManeger.getBackStackEntryAt(0);
            fragmentManeger.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
