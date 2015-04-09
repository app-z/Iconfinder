package net.appz.iconfounder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
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

    private final long showTime = 5000;

    /**
     * Returns a new instance of this fragment
     *
     */
    public static OverlayMessageFragment newInstance(Bundle bundle) {
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

    private void removeMessageOfTimeOut(){
        if( adapter != null && animation != null
            && !isAnimationStarted && messages.size() > 0){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    boolean changed = false;
                    synchronized (messages) {
                        if ( messages.size() > 0 ) {
                            int i = 0;    // fu*k! need for getChildAt
                            Iterator<Map<String, Object>> it = messages.iterator();
                            while (it.hasNext()) {
                                Map<String, Object> message = it.next();
                                long ts = (long) message.get(TIMESTAMP);
                                if (DEBUG)
                                    Log.e(TAG, "ts removed : " + (ts - System.currentTimeMillis()) + " : " + message);
                                if (ts < System.currentTimeMillis()) {
                                    it.remove();
                                    changed = true;
                                }
                                i++;
                            }
                        }
                    }
                    if(changed)
                        adapter.notifyDataSetChanged();

                }
            });

        }
    }

    private static Timer timer;
    private TimerTask timertask;
    private final int timer_cycle = 250;

    private void startTimer() {
        timertask = new TimerTask() {
            @Override
            public void run() {
                removeMessageOfTimeOut();
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

    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragmet_overlay_message, container, false);

        listView = (ListView)rootView.findViewById(R.id.messageListView);

        initAnimation();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if( !isAnimationStarted ){
                    clickPosition = position;
                    if (DEBUG) Log.e(TAG, "OnItemClickListener : " + position + " : isAnimationStarted = " + isAnimationStarted);
                    isAnimationStarted = true;
                    view.startAnimation(animation);
                }
            }
        });



        adapter = new SimpleAdapter(getActivity(),
                messages,
                R.layout.message_item,
                new String[]{MESSAGE},
                new int[]{R.id.messageText}
        );

        listView.setAdapter(adapter);
        return rootView;
    }

    int clickPosition;

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) attachActivity).onOverlayCreated();
    }

    Activity attachActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.attachActivity = activity;
        try {
            mCallbacks = (OverlayMessageFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OverlayMessageFragment.");
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

    boolean isAnimationStarted = false;

    /**
     *
     *
     */
    private void initAnimation() {
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (messages) {
                            messages.remove(clickPosition);
                        }
                        adapter.notifyDataSetChanged();
                        listView.getLayoutParams().width = (int) (getWidestView(getActivity(), adapter) * 1.05);
                    }
                });

                isAnimationStarted = false;
                if (DEBUG) Log.e(TAG, "onAnimation1End : isAnimation2Started = " + isAnimationStarted);
            }
        });

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
