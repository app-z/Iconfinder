package net.appz.iconfounder;

/**
 * Created by App-z.net on 29.03.15.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import net.appz.iconfounder.Data.Iconsets;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ICON_SETS = "iconsets";
    private IconSetsListAdapter mAdapter;
    private AbsListView mListView;

    private EditText queryText;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, Iconsets iconsets) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_ICON_SETS, iconsets);

        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        queryText = (EditText) rootView.findViewById(R.id.queryText);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String query = prefs.getString("query", "facebook");
        queryText.setText(query);

        Bundle b = getArguments();
        final int section = b.getInt(ARG_SECTION_NUMBER);
        final Iconsets iconsets = b.getParcelable(ARG_ICON_SETS);

        Button btnSearch = (Button) rootView.findViewById(R.id.btnSearh);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String query = queryText.getText().toString();
                if(query.trim().length() == 0){
                    AppUtils.showDialog(getActivity(), "Input empty", "Request empty", false);
                }else {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("query", query).commit();

                    ((MainActivity) getActivity()).onQueryIcons(query);
                }
            }
        });



        if(iconsets != null) {
            mAdapter = new IconSetsListAdapter(getActivity(), iconsets.getIconsets());

            // Set the adapter
            mListView = (AbsListView) rootView.findViewById(android.R.id.list);
            ((AdapterView) mListView).setAdapter(mAdapter);

        }
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
