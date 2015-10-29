package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coep.puneet.boilerplate.Custom.MarginDecoration;
import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Adapter.ProductListAdapter;

import butterknife.Bind;

public class ViewProduct extends BaseActivity
{
    @Bind(R.id.productsListRecycler) RecyclerView mRecyclerView;
    @Bind(R.id.empty_result_text) TextView mEmptyText;
    @Bind(R.id.progress_loading) ProgressBar mProgressBar;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    ProductListAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager.loginArtisan("7507118432");
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_view_product;
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
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new ProductListAdapter(this, null);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(this));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_primary, R.color.signal_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                //makeAPICall();
            }
        });


    }
    @Override
    public void processFinish(String result, String type)
    {
        switch (type)
        {
            case AppConstants.RESULT_PRODUCT_LIST:
                if (manager.currentArtisanProducts.size() != 0)
                {

                }
                break;
            case AppConstants.RESULT_PRODUCT_LIST_ERROR:
                Toast.makeText(ViewProduct.this, "Error in retreiving product list", Toast.LENGTH_SHORT).show();

        }
    }

}
