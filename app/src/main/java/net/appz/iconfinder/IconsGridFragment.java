package net.appz.iconfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import net.appz.iconfinder.Data.Icons;

/**
 * Created by spbmap on 29.03.15.
 */
public class IconsGridFragment extends Fragment {


    private static final String ARG_ICONS = "icons";

    Icons icons;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid_icons, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView1);

        Bundle b = getArguments();
        icons = b.getParcelable(ARG_ICONS);

        gridView.setAdapter(new IconsAdapter(getActivity(), icons.getIcons()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ((MainActivity) getActivity()).onClickIcon(icons.getIcons().get(position));

            }
        });

        return rootView;
    }
}
