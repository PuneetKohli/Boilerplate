package com.coep.puneet.boilerplate.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.coep.puneet.boilerplate.Global.AppManager;
import com.coep.puneet.boilerplate.Global.AsyncResponse;
import com.coep.puneet.boilerplate.Global.Navigator;
import com.coep.puneet.boilerplate.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements AsyncResponse
{
    @Nullable @Bind(R.id.toolbar) Toolbar toolbar;
    Navigator navigator;
    public AppManager manager;


    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        navigator = new Navigator();
        manager = (AppManager) getApplication();
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            setupToolbar();
        }
        setupLayout();
    }

    protected abstract int getLayoutResource();

    protected abstract void setupToolbar();

    protected abstract void setupLayout();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String result, String type)
    {
    }

    void showNoInternetAlert()
    {
        if (!isFinishing())
        {
            dialog = new AlertDialog.Builder(this).setTitle("No Internet Connection").setMessage("Try again").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    // continue with delete
                }
            }).show();
        }
    }

    void countdownInternetAlert(long time)
    {
        if (dialog.isShowing())
        {
            dialog.setMessage("Retrying in " + time + " seconds..\nTap 'Ok' to manually retry.");
        }
    }

    void closeInternetAlert()
    {
        if (dialog.isShowing())
        {
            dialog.cancel();
        }
    }
}
