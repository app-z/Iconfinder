package net.appz.iconfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.RasterSize;

import java.util.List;

/**
 * Created by App-z.net on 29.03.15.
 */
public class IconsGridAdapter extends ArrayAdapter<Icon> {

    private int minimum_size;

    public IconsGridAdapter(Context context, List<Icon> icon) {
        super(context, 0, icon);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        minimum_size = Integer.valueOf(prefs.getString("minimum_size_list", "16"));

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Icon icon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.icon_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.grid_item_label);
        tvName.setText(icon.getType());

        final ImageView imgView = (ImageView) convertView.findViewById(R.id.grid_item_image);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        List<RasterSize> rasterSizes = icon.getRasterSizes();

        if(rasterSizes != null ){
            for (RasterSize rasterSize : rasterSizes) {
                if(rasterSize.getSize() >= minimum_size){
                    String imgUrl = rasterSize.getFormats().get(0).getPreviewUrl();
                    Picasso.with(getContext()).load(imgUrl).into(imgView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    // Log.d(">>>", imgUrl);
                    break;
                }
            }
        }

        return convertView;
    }
}
