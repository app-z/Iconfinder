package net.appz.iconfounder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by App-z.net on 07.04.15.
 */
public class OverlayMessageFragment extends Fragment {

    private static final boolean DEBUG = false;
    private String TAG = getClass().getSimpleName();

    private OverlayMessageFragmentCallbacks mCallbacks;

    // One Item message
/*    class OverlayMessage{
        String text;
        long timestamp;
        public OverlayMessage(String text, long timestamp){
            this.text = text;
            this.timestamp = timestamp;
        }
    } */

    private final List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
    private final static String MESSAGE = "message";
    private final static String TIMESTAMP = "timestamp";

    private SimpleAdapter adapter;
    private ListView listView;

    private final long showTime = 3000;

    /**
     * Returns a new instance of this fragment
     *
     */
    public static OverlayMessageFragment newInstance() {
        OverlayMessageFragment fragment = new OverlayMessageFragment();
        return fragment;
    }

    public OverlayMessageFragment(){}

    public void addMessage(String text){
        synchronized (messages) {
            long ts = System.currentTimeMillis();
            if(messages.size() > 0){
                ts = (long)messages.get(0).get(TIMESTAMP);
                ts += showTime * (messages.size() - 1);
            }
            ts += showTime;

            final Map<String, Object> message = new HashMap<String, Object>();
            message.put(MESSAGE, text);
            message.put(TIMESTAMP, ts);
            if (DEBUG) Log.e(TAG, "ts added : " + ts + " : " + message);
            messages.add(Collections.unmodifiableMap(message));
        }
        adapter.notifyDataSetChanged();
        listView.getLayoutParams().width = (int) (getWidestView(getActivity(), adapter)*1.05);
    }

    private void removeFirst(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null && messages.size() > 0) {
                    //messages.remove(0);
                    boolean changed = false;
                    synchronized (messages) {
                        Iterator<Map<String, Object>> it = messages.iterator();
                        while(it.hasNext()){
                            Map<String, Object> message = it.next();
                            long ts = (long) message.get(TIMESTAMP);
                            if (DEBUG) Log.e(TAG, "ts removed : " + (ts - System.currentTimeMillis()) + " : " + message);
                            if (ts < System.currentTimeMillis()) {
                                it.remove();
                                changed = true;
                            }
                        }
                    }
                    if(changed) {
                        adapter.notifyDataSetChanged();
                        listView.getLayoutParams().width = (int) (getWidestView(getActivity(), adapter) * 1.05);
                    }
                }
            }
        });
    }

    private static Timer timer;
    private TimerTask timertask;
    private final int timer_cycle = 250;

    private void startTimer() {
        timertask = new TimerTask() {
            @Override
            public void run() {
                removeFirst();
            }
        };
        timer = new Timer();
        timer.schedule(timertask, 0, timer_cycle); // execute in every N ms
    }

    private void stopTimer(){
        if ( timertask != null ){
            timertask.cancel();
        }
        if ( timer != null ){
            timer.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragmet_overlay_message, container, false);

        listView = (ListView)rootView.findViewById(R.id.messageListView);

        adapter = new SimpleAdapter(getActivity(),
                messages,
                R.layout.message_item,
                new String[]{MESSAGE},
                new int[]{R.id.messageText}
        );

        listView.setAdapter(adapter);

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

        startTimer();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        stopTimer();
    }

    public static interface OverlayMessageFragmentCallbacks{
        void onOverlayClick();
    }

    // http://stackoverflow.com/questions/6547154/wrap-content-for-a-listviews-width
    /**
     * Computes the widest view in an adapter, best used when you need to wrap_content on a ListView, please be careful
     * and don't use it on an adapter that is extremely numerous in items or it will take a long time.
     *
     * @param context Some context
     * @param adapter The adapter to process
     * @return The pixel width of the widest View
     */
    public static int getWidestView(Context context, Adapter adapter) {
        int maxWidth = 0;
        View view = null;
        FrameLayout fakeParent = new FrameLayout(context);
        for (int i=0, count=adapter.getCount(); i<count; i++) {
            view = adapter.getView(i, view, fakeParent);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = view.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

}
