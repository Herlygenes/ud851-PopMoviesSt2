package br.com.ud851.popmoviesst1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.Trailer;

/**
 * Created by Herlygenes Pinto on 18/02/2018.
 */

public class TrailerAdapter extends BaseAdapter {
    private final Context context;
    private List<Trailer> trailers = new ArrayList<>();

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trailer trailer = (Trailer) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_movie_trailer, parent, false);
        }

        TextView tv_video_name = (TextView) convertView.findViewById(R.id.tv_video_name);

        tv_video_name.setText(trailer.getName());

        return convertView;
    }

}
