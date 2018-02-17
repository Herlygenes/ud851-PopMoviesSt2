package br.com.ud851.popmoviesst1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.ud851.popmoviesst1.utils.NetworkUtils;
import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.activities.MainActivity;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public final class MenuAdapter extends BaseAdapter {

    enum SortCategory {
        POPULAR("Most Popular", MainActivity.class),
        TOP_RATED("Top Rated", MainActivity.class);

        private final Class<? extends Activity> activityClass;
        private final String name;

        SortCategory(String name, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.name = name;
        }

        public void launch(Activity activity) {
            Intent intent = new Intent(activity, activityClass);
            if(name.equals("Most Popular")){
                intent.putExtra("query_tmdb", NetworkUtils.TMDB_POPULAR_QUERY);
            } else {
                intent.putExtra("query_tmdb", NetworkUtils.TMDB_TOP_RATED_QUERY);
            }
            activity.startActivity(intent);
            activity.finish();
        }
    }

    private final LayoutInflater inflater;

    public MenuAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return SortCategory.values().length;
    }

    @Override public SortCategory getItem(int position) {
        return SortCategory.values()[position];
    }

    public void launch(int position, Activity activity){
        getItem(position).launch(activity);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) inflater.inflate(R.layout.activity_main_item, parent, false);
        }

        view.setText(getItem(position).name);

        return view;
    }
}