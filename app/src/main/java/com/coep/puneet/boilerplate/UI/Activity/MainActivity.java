package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;

public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //TEST
        super.onCreate(savedInstanceState);
        manager.loginArtisan("7507118432");
        manager.getAllCategory();
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {
        manager.delegate = this;
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
                Toast.makeText(MainActivity.this, "Error in retreiving product list", Toast.LENGTH_SHORT).show();
            case AppConstants.RESULT_CATEGORY_LIST:
                if (manager.productCategories.size() != 0)
                {

                }
                break;
            case AppConstants.RESULT_CATEGORY_LIST_ERROR:
                Toast.makeText(MainActivity.this, "Error in retreiving categories", Toast.LENGTH_SHORT).show();
        }
    }
}
