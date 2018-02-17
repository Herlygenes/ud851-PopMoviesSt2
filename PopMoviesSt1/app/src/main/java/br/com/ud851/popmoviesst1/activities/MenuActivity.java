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

import br.com.ud851.popmoviesst1.adapters.MenuAdapter;
import br.com.ud851.popmoviesst1.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

abstract class MenuActivity extends FragmentActivity {
    private ToggleButton showHide;
    private FrameLayout sampleContent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.menu_activity);
        sampleContent = (FrameLayout) findViewById(R.id.sample_content);

        final ListView activityList = (ListView) findViewById(R.id.activity_list);
        final MenuAdapter adapter = new MenuAdapter(this);
        activityList.setAdapter(adapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.launch(position, MenuActivity.this);
            }
        });

        showHide = (ToggleButton) findViewById(R.id.faux_action_bar_control);
        showHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                activityList.setVisibility(checked ? VISIBLE : GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MenuActivity", "onDestroy(); Context:" + this.getBaseContext().toString());
        Picasso.with(this.getBaseContext()).cancelTag(this);
    }

    @Override public void onBackPressed() {
        if (showHide.isChecked()) {
            showHide.setChecked(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, sampleContent);
    }

    @Override public void setContentView(View view) {
        sampleContent.addView(view);
    }

    @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
        sampleContent.addView(view, params);
    }
}
