package net.appz.iconfinder;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.appz.iconfinder.Data.Icon;

/**
 * Created by App-z.net on 30.03.15.
 */
public class IconsSaveFragment extends ListFragment {

    private static final String ARG_ICON = "icon";

    Icon icon;

    /**
     * Returns a new instance of this fragment
     *
     */
    public static IconsSaveFragment newInstance(Icon icon) {
        IconsSaveFragment fragment = new IconsSaveFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ICON, icon);

        fragment.setArguments(args);
        return fragment;
    }

    public IconsSaveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_icons, container, false);

        Bundle b = getArguments();
        icon = b.getParcelable(ARG_ICON);

        Button btnSearch = (Button) rootView.findViewById(R.id.btnSave);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(">>>", "Icons = ");

            }

        });

        return rootView;
    }

}
