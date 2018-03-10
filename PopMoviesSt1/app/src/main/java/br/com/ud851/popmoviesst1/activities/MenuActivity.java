package br.com.ud851.popmoviesst1.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.adapters.MenuAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

abstract class MenuActivity extends FragmentActivity {
    private static String LOG_TAG = MenuActivity.class.getSimpleName();
    private ToggleButton mShowHide;
    private FrameLayout mSampleContent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.menu_activity);
        mSampleContent = (FrameLayout) findViewById(R.id.sample_content);

        final ListView activityList = (ListView) findViewById(R.id.activity_list);
        final MenuAdapter adapter = new MenuAdapter(this);
        activityList.setAdapter(adapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.launch(position, MenuActivity.this);
            }
        });

        mShowHide = (ToggleButton) findViewById(R.id.faux_action_bar_control);
        mShowHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                activityList.setVisibility(checked ? VISIBLE : GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy(); Context:" + this.getBaseContext().toString());
        Picasso.with(this.getBaseContext()).cancelTag(this);
    }

    @Override public void onBackPressed() {
        if (mShowHide.isChecked()) {
            mShowHide.setChecked(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mSampleContent);
    }

    @Override public void setContentView(View view) {
        mSampleContent.addView(view);
    }

    @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
        mSampleContent.addView(view, params);
    }
}

