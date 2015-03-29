package net.appz.iconfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.Icons;
import net.appz.iconfinder.Data.RasterSize;

import java.util.List;

/**
 * Created by spbmap on 29.03.15.
 */
public class IconsAdapter extends ArrayAdapter<Icon> {

    private int minSize = 128;

    public IconsAdapter(Context context, List<Icon> icon) {
        super(context, 0, icon);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Icon icon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.icon_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.grid_item_label);
        tvName.setText(icon.getType());

        ImageView img = (ImageView) convertView.findViewById(R.id.grid_item_image);

        List<RasterSize> rasterSizes = icon.getRasterSizes();

        if(rasterSizes != null ){
            for (RasterSize rasterSize : rasterSizes) {
                if(rasterSize.getSize() >= minSize){
                    String imgUrl = rasterSize.getFormats().get(0).getPreviewUrl();
                    Picasso.with(getContext()).load(imgUrl).into(img);
                    Log.d(">>>", imgUrl);
                    break;
                }
            }

        }

        return convertView;
    }
}
