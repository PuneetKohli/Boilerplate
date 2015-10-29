package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Adapter.NavGridAdapter;

import butterknife.Bind;

public class NavigationGrid extends BaseActivity
{
    @Bind(R.id.navGrid) GridView navGrid;


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

    public String[] mNavLabels = {"1", "2", "3", "4", "5", "6", "7", "8"};




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_navigation_grid;
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
        navGrid.setAdapter(new NavGridAdapter(this, mNavIds, mNavLabels, height1));

        navGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0: navigator.openNewActivity(NavigationGrid.this, new ViewProduct());
                }
            }
        });
    }


}
