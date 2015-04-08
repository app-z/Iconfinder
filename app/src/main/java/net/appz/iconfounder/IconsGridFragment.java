package net.appz.iconfounder;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import net.appz.iconfounder.Data.Icon;
import net.appz.iconfounder.Data.Icons;

/**
 * Created by App-z.net on 29.03.15.
 */
public class IconsGridFragment extends Fragment {

    private static final String ARG_ICONS = "icons";

    // Check if new feeds are loading
    boolean loadingMore = true;
    boolean stopLoadingData = false;

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

    public int getCountItems(){
        return iconsGridAdapter.getCount();
    }

    public void addIcons(Icons icons){
        // get listview current position - used to maintain scroll position
        int currentPosition = gridView.getFirstVisiblePosition();

        //If the platform supports it, use addAll, otherwise add in loop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            iconsGridAdapter.addAll(icons.getIcons());
        } else {
            for(Icon icon: icons.getIcons()) {
                iconsGridAdapter.add(icon);
            }
        }


        // Setting new scroll position
        gridView.setSelection(currentPosition + 1);

        loadingMore = false;

        stopLoadingData = icons.getTotalCount() == iconsGridAdapter.getCount();
    }

    public void resetLoadingFlag(){
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
}
