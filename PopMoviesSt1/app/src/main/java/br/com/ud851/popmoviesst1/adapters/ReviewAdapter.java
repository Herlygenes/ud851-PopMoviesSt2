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
import br.com.ud851.popmoviesst1.data.vos.ReviewVO;

/**
 * Created by Herlygenes Pinto on 04/03/2018.
 */

public class ReviewAdapter extends BaseAdapter {
    private final Context mContext;
    private List<ReviewVO> reviews = new ArrayList<>();

    public ReviewAdapter(Context context, List<ReviewVO> reviews) {
        this.mContext = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewVO review = (ReviewVO) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_movie_review, parent, false);
        }

        TextView tv_author = (TextView) convertView.findViewById(R.id.tv_author_name);
        TextView tv_review_content = (TextView) convertView.findViewById(R.id.tv_review_content);

        tv_author.setText(review.getAuthor());
        tv_review_content.setText(review.getContent());

        return convertView;
    }
}
