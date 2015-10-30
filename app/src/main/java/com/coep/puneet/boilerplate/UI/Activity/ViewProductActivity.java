package com.coep.puneet.boilerplate.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coep.puneet.boilerplate.Custom.MarginDecoration;
import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Adapter.ProductListAdapter;

import butterknife.Bind;

public class ViewProductActivity extends BaseActivity
{
    @Bind(R.id.productsListRecycler) RecyclerView mRecyclerView;
    @Bind(R.id.empty_result_text) TextView mEmptyText;
    @Bind(R.id.progress_loading) ProgressBar mProgressBar;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    ProductListAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    boolean initialLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //manager.getAllProductsFromCurrentArtisan();
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
        manager.delegate = this;

        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new ProductListAdapter(this, manager.currentArtisanProducts);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(this));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_primary, R.color.signal_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                manager.getAllProductsFromCurrentArtisan();
            }
        });

        setViewStateBasedOnFetchedList();

    }

    void setViewStateBasedOnFetchedList()
    {
        mSwipeRefreshLayout.setRefreshing(false);
        if(manager.currentArtisanProducts.size() != 0)
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            mEmptyText.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(initialLoad)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                mEmptyText.setVisibility(View.INVISIBLE);
                manager.getAllProductsFromCurrentArtisan();
                initialLoad = false;
            }
            else
            {
                mProgressBar.setVisibility(View.INVISIBLE);
                mEmptyText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void processFinish(String result, String type)
    {
        switch (type)
        {
            case AppConstants.RESULT_PRODUCT_LIST:
                Log.d("ViewProduct", "Got product list with size " + manager.currentArtisanProducts.size());
                if (manager.currentArtisanProducts.size() != 0)
                {
                    setViewStateBasedOnFetchedList();
                    //Doing using clear and then add creating wierd unsolvable issues
                    mAdapter = new ProductListAdapter(ViewProductActivity.this, manager.currentArtisanProducts);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else {
                    setViewStateBasedOnFetchedList();
                }
                break;
            case AppConstants.RESULT_PRODUCT_LIST_ERROR:
                Toast.makeText(ViewProductActivity.this, "Error in retreiving product list", Toast.LENGTH_SHORT).show();
                showNoInternetSnackbar();
                setViewStateBasedOnFetchedList();
                break;
        }
    }

    void showNoInternetSnackbar()
    {
        Snackbar.make(mRecyclerView, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                manager.getAllProductsFromCurrentArtisan();
            }
        }).setActionTextColor(Color.GREEN).show();
    }

}
