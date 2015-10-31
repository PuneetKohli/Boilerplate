package com.coep.puneet.boilerplate.UI.Activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.coep.puneet.boilerplate.Custom.RecyclerItemClickListener;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Adapter.EventListAdapter;

import butterknife.Bind;

public class EventSearch extends BaseActivity
{
    @Bind(R.id.artisanSearchRecycler) RecyclerView mRecyclerView;

    EventListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_artisan_search;
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               navigator.openNewActivity(EventSearch.this, new MapsActivityEvent());
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mAdapter = new EventListAdapter(this, manager.eventList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                manager.currentArtisanSelected = manager.artisanList.get(position);
                navigator.openNewActivity(EventSearch.this, new ArtisanProfileActivity());
            }
        }));

    }

}
