package br.com.ud851.popmoviesst1.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ud851.popmoviesst1.R;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public final class MainAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<>();

    public MainAdapter(Context context, String[] images) {
        this.context = context;
        Collections.addAll(urls, images);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieCoverImageView view = (MovieCoverImageView) convertView;
        if (view == null) {
            view = new MovieCoverImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = (String)getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

}
