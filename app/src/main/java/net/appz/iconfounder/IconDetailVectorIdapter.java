package net.appz.iconfounder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.appz.iconfounder.Data.Format;
import net.appz.iconfounder.Data.VectorSize;

import java.util.List;

/**
 * Created by App-z.net on 01.04.15.
 */
public class IconDetailVectorIdapter extends ArrayAdapter<VectorSize> {

    private AbsListView mListView;

    public IconDetailVectorIdapter(Context context, List<VectorSize> vectorSize) {
        super(context, 0, vectorSize);
    }

    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.icon_detail_vector_row, parent, false);
        }

        VectorSize vectorSize = getItem(position);

        TextView resolution = (TextView) rootView.findViewById(R.id.resolution);
        resolution.setText(String.valueOf(vectorSize.getSizeWidth()) + "x" + vectorSize.getSizeHeight());

        List<Format> formats = vectorSize.getFormats();
        FormatAdapter adapter = new FormatAdapter(getContext(), formats);
        // Set the adapter
        mListView = (AbsListView) rootView.findViewById(android.R.id.list);
        ((AdapterView) mListView).setAdapter(adapter);

        return rootView;
    }

}
