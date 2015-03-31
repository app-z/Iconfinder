package net.appz.iconfinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.appz.iconfinder.Data.Category;
import net.appz.iconfinder.Data.Icon;
import net.appz.iconfinder.Data.RasterSize;

import java.util.Arrays;
import java.util.List;

/**
 * Created by App-z.net on 30.03.15.
 */
public class IconsDetailFragment extends Fragment {

    private static final String ARG_ICON = "icon";

    /**
     * Returns a new instance of this fragment
     *
     */
    public static IconsDetailFragment newInstance(Icon icon) {
        IconsDetailFragment fragment = new IconsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ICON, icon);

        fragment.setArguments(args);
        return fragment;
    }

    public IconsDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_icons, container, false);

        Bundle b = getArguments();
        Icon icon = b.getParcelable(ARG_ICON);

        TextView tag = (TextView)rootView.findViewById(R.id.tag);
        tag.setText(Arrays.toString(icon.getTags().toArray()));

        TextView category = (TextView)rootView.findViewById(R.id.category);
        String iconCategories = "";
        for (Category iconCategory : icon.getCategories()){
            iconCategories += iconCategory.getName() + " ";
        }
        category.setText(iconCategories);

        TextView rasetSizes = (TextView)rootView.findViewById(R.id.rasterSizes);
        String iconRaserSizes = "";
        for (RasterSize rasterSize : icon.getRasterSizes()){
            iconRaserSizes += "" + rasterSize.getSizeWidth() + "x" + rasterSize.getSizeHeight() + " ";
        }
        rasetSizes.setText(iconRaserSizes);

        TextView publishedAt = (TextView)rootView.findViewById(R.id.publishedAt);
        publishedAt.setText(icon.getPublishedAt());

        Button btnSearch = (Button) rootView.findViewById(R.id.btnSave);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) getActivity()).onCloseSaveIcon();
            }

        });

        final ImageView imgView = (ImageView) rootView.findViewById(R.id.grid_item_image);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int minimum_size = Integer.valueOf(prefs.getString("minimum_size_list", "16"));

        List<RasterSize> rasterSizes = icon.getRasterSizes();
        if(rasterSizes != null ){
            for (RasterSize rasterSize : rasterSizes) {
                if(rasterSize.getSize() >= minimum_size){
                    String imgUrl = rasterSize.getFormats().get(0).getPreviewUrl();
                    Picasso.with(getActivity()).load(imgUrl).into(imgView, new Callback() {
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

        return rootView;
    }

}
