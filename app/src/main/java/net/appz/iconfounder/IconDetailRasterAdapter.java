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
import net.appz.iconfounder.Data.RasterSize;

import java.util.List;

/**
 * Created by App-z.net on 01.04.15.
 */
public class IconDetailRasterAdapter extends ArrayAdapter<RasterSize> {

    private AbsListView mListView;

    public IconDetailRasterAdapter(Context context, List<RasterSize> rasterSize) {
        super(context, 0, rasterSize);

    }


    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.icon_detail_raster_row, parent, false);
        }

        RasterSize rasterSize = getItem(position);

        TextView resolution = (TextView) rootView.findViewById(R.id.resolution);
        resolution.setText(String.valueOf(rasterSize.getSizeWidth()) + "x" + rasterSize.getSizeHeight());

        List<Format> formats = rasterSize.getFormats();
        FormatAdapter adapter = new FormatAdapter(getContext(), formats);
        // Set the adapter
        mListView = (AbsListView) rootView.findViewById(android.R.id.list);
        ((AdapterView) mListView).setAdapter(adapter);

        return rootView;
    }


}