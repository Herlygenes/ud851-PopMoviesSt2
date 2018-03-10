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
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;

/**
 * Created by Herlygenes Pinto on 18/02/2018.
 */

public class TrailerAdapter extends BaseAdapter {
    private final Context mContext;
    private List<TrailerVO> mTrailers = new ArrayList<>();

    public TrailerAdapter(Context mContext, List<TrailerVO> trailers) {
        this.mContext = mContext;
        this.mTrailers = trailers;
    }

    @Override
    public int getCount() {
        return mTrailers.size();
    }

    @Override
    public Object getItem(int position) {
        return mTrailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrailerVO trailer = (TrailerVO) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_movie_trailer, parent, false);
        }

        TextView tv_video_name = (TextView) convertView.findViewById(R.id.tv_video_name);

        tv_video_name.setText(trailer.getName());

        return convertView;
    }

}
