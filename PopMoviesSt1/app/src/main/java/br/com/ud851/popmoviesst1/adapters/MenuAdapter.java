package br.com.ud851.popmoviesst1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.activities.MainActivity;
import br.com.ud851.popmoviesst1.utils.App;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public final class MenuAdapter extends BaseAdapter {

    enum SortCategory {
        POPULAR(App.getContext().getResources().getString(R.string.menu_opt_most_popular), MainActivity.class),
        TOP_RATED(App.getContext().getResources().getString(R.string.menu_opt_top_rated), MainActivity.class),
        FAVORITES(App.getContext().getResources().getString(R.string.menu_opt_favorites), MainActivity.class);

        private final Class<? extends Activity> activityClass;
        private final String name;

        SortCategory(String name, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.name = name;
        }

        public void launch(Activity activity) {
            Intent intent = new Intent(activity, activityClass);
            if(name.equals(activity.getResources().getString(R.string.menu_opt_most_popular))){
                intent.putExtra(TMDBUtils.QUERY, TMDBUtils.POPULAR_QUERY);
            } else if(name.equals(activity.getResources().getString(R.string.menu_opt_top_rated))){
                intent.putExtra(TMDBUtils.QUERY, TMDBUtils.TOP_RATED_QUERY);
            } else {
                intent.putExtra(TMDBUtils.QUERY, TMDBUtils.TOP_RATED_QUERY);
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
