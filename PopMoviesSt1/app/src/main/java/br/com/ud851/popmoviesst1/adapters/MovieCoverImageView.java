package br.com.ud851.popmoviesst1.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public final class MovieCoverImageView extends AppCompatImageView {
    public MovieCoverImageView(Context context) {
        super(context);
    }

    public MovieCoverImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }


}
