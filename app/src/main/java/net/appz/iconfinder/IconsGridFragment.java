package net.appz.iconfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.Icons;

/**
 * Created by App-z.net on 29.03.15.
 */
public class IconsGridFragment extends Fragment {

    private static final String ARG_ICONS = "icons";
    //private static final String ARG_LOADING_MORE = "loadingMore";

    // Check if new feeds are loading
    boolean loadingMore = true;
    boolean stopLoadingData = false;

    //Icons icons;

    private GridView gridView;
    private IconsGridAdapter iconsGridAdapter;
    /**
     * Returns a new instance of this fragment
     *
     */
    public static IconsGridFragment newInstance(Icons icons) {
        IconsGridFragment fragment = new IconsGridFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ICONS, icons);

        fragment.setArguments(args);
        return fragment;
    }

    public IconsGridFragment() {
    }

    public void addIcons(Icons icons){
        // get listview current position - used to maintain scroll position
        int currentPosition = gridView.getFirstVisiblePosition();

        iconsGridAdapter.addAll(icons.getIcons());

        // Setting new scroll position
        gridView.setSelection(currentPosition + 1);

        loadingMore = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid_icons, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView1);


        Bundle b = getArguments();
        Icons icons = b.getParcelable(ARG_ICONS);
        iconsGridAdapter = new IconsGridAdapter(getActivity(), icons.getIcons());
        gridView.setAdapter(iconsGridAdapter);
        loadingMore = false;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Icon icon = iconsGridAdapter.getItem(position);
                ((MainActivity) getActivity()).onClickIcon(icon);
            }
        });

        // Lazy loader
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(loadingMore)) {

                    if (!stopLoadingData) {
                        loadingMore = true;
                        ((MainActivity) getActivity()).onLazyLoadMore();
                    }

                }
            }
        });
        return rootView;
    }

/*
    private static final String TAG = ">>>>>>";

    private static final Field sChildFragmentManagerField;

    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e(TAG, "Error setting mChildFragmentManager field", e);
            }
        }
    }
    */

    }
