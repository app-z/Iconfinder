package net.appz.iconfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.appz.iconfinder.Data.Iconset;

import java.util.List;

/**
 * Created by App-z.net on 29.03.15.
 */
public class IconSetsListAdapter  extends ArrayAdapter {

    public IconSetsListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        TextView name;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Iconset item = (Iconset)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.iconset_list_item, null);

            holder = new ViewHolder();
            holder.name = (TextView)viewToUse.findViewById(R.id.name);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.name.setText(item.getName());
        return viewToUse;
    }
}

