package net.appz.iconfounder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.appz.iconfounder.Data.Format;

import java.util.List;

/**
 * Created by App-z.net on 01.04.15.
 */
/**
 *
 * List in list
 *
 */
class FormatAdapter extends ArrayAdapter<Format> {

/*    FormatCallback callback;

    public interface FormatCallback{
        void onDownload(String url);
        void onPreview(String url);
    }*/

    public FormatAdapter(Context context, List<Format> format) {
        super(context, android.R.layout.simple_list_item_1, format);
    }


    @Override
    public View getView(final int position, View rootView, ViewGroup parent) {

        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.format_row, parent, false);
        }

        TextView format = (TextView) rootView.findViewById(R.id.format);
        format.setText(getItem(position).getFormat());

        // Download button
        Button downloadBtn = (Button) rootView.findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String url = getItem(position).getDownloadUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(browserIntent);
            }
        });

        // Preview button
        Button previewBtn = (Button) rootView.findViewById(R.id.previewBtn);
        previewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String url = getItem(position).getPreviewUrl();
                if(url != null && url.length() > 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    getContext().startActivity(browserIntent);
                }
            }
        });


        final ImageView imgView = (ImageView) rootView.findViewById(R.id.image);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        Picasso.with(getContext()).load(getItem(position).getPreviewUrl()).into(imgView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });


        return rootView;
    }
}
