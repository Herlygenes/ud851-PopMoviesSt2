package br.com.ud851.popmoviesst1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

final class MenuAdapter extends BaseAdapter {

    enum Sample {
        POPULAR("Most Popular", MainActivity.class),
        TOP_RATED("Top Rated", MainActivity.class);

        private final Class<? extends Activity> activityClass;
        private final String name;

        Sample(String name, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.name = name;
        }

        public void launch(Activity activity) {
            Intent intent = new Intent(activity, activityClass);
            intent.putExtra("query_tmdb", name.equals("Most Popular")?NetworkUtils.TMDB_POPULAR_QUERY:NetworkUtils.TMDB_TOP_RATED_QUERY);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    private final LayoutInflater inflater;

    MenuAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return Sample.values().length;
    }

    @Override public Sample getItem(int position) {
        return Sample.values()[position];
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
