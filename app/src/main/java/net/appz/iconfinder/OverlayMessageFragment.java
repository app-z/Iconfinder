package net.appz.iconfinder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by App-z.net on 07.04.15.
 */
public class OverlayMessageFragment extends Fragment {

    private static final boolean DEBUG = true;
    private String TAG = getClass().getSimpleName();

    private static final String ARG_MESSAGE = "message";
    private OverlayMessageFragmentCallbacks mCallbacks;

    /**
     * Returns a new instance of this fragment
     *
     */
    public static OverlayMessageFragment newInstance(String message) {
        OverlayMessageFragment fragment = new OverlayMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);

        fragment.setArguments(args);
        return fragment;
    }

    public OverlayMessageFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragmet_overlay_message, container, false);

        rootView.setClickable(true);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mCallbacks.onOverlayClick();
                    if (DEBUG) Log.e(TAG, "click ");
                }
                return false;
            }
        });


        TextView message = (TextView) rootView.findViewById(R.id.messageText);
        message.setText(getArguments().getString(ARG_MESSAGE));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (OverlayMessageFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }


    public static interface OverlayMessageFragmentCallbacks{
        void onOverlayClick();
    }
}
