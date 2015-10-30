package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Adapter.NavGridAdapter;

import butterknife.Bind;

public class HomeActivity extends BaseActivity
{
    @Bind(R.id.category_grid_view) GridView navGrid;


    public Integer[] mNavIds = {
            R.drawable.bs_ic_clear,
            R.drawable.bs_ic_more,
            R.drawable.bs_ic_more_light,
            R.drawable.bs_ic_more_light,
            R.drawable.bs_ic_more_light,
            R.drawable.bs_ic_more_light,
            R.drawable.bs_ic_more_light,
            R.drawable.bs_ic_more_light
    };





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager.delegate = this;
        //manager.loginArtisan("7507118432");
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_home;
    }

    @Override
    protected void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void setupLayout()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height1 = metrics.heightPixels;
        String[] mNavLabels = {getString(R.string.home_button_1), getString(R.string.home_button_2), getString(R.string.home_button_3), getString(R.string.home_button_4), getString(R.string.home_button_5), getString(R.string.home_button_6), getString(R.string.home_button_7), getString(R.string.home_button_8)};
        navGrid.setAdapter(new NavGridAdapter(this, mNavIds, mNavLabels, height1));

        navGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        navigator.openNewActivity(HomeActivity.this, new ViewProductActivity());
                        break;
                    case 1:
                        navigator.openNewActivity(HomeActivity.this, new AddProductActivity());
                        break;
                    case 3:
                        navigator.openNewActivity(HomeActivity.this, new ProfileActivity());
                        break;
                }
            }
        });
    }
}
