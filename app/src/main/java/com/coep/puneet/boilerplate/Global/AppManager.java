package com.coep.puneet.boilerplate.Global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.coep.puneet.boilerplate.ParseObjects.Category;
import com.coep.puneet.boilerplate.ParseObjects.Product;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AppManager extends Application
{

    private String LOG_TAG = AppManager.class.getCanonicalName();

    public ArrayList<Category> productCategories = new ArrayList<>();
    public ArrayList<Product> currentArtisanProducts = new ArrayList<>();
    public AsyncResponse delegate = null;


    ConnectivityManager cm;
    NetworkInfo ni;


    @Override
    public void onCreate()
    {
        super.onCreate();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        parseInit();
    }

    private void parseInit() {
        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Product.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "A2M7yTjp5iULp8xiFXymM29yX2U9bHEKhJdcsJEN", "L7WlExlZM9DoWNQkCCxY8uf7ummyn6cy1yrhnU7U");
    }

    public void getAllCategory() {

        if ((ni != null) && (ni.isConnected()))
        {
            ParseQuery<Category> query = Category.getQuery();
            query.orderByAscending("category_name");
            query.findInBackground(new FindCallback<Category>()
            {
                @Override
                public void done(final List<Category> list, ParseException e)
                {
                    if(e == null)
                    {
                        ParseObject.unpinAllInBackground("category_list", list, new DeleteCallback() {
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.d(LOG_TAG, e.getMessage());
                                    return;
                                }

                                // Add the latest results for this query to the cache.
                                ParseObject.pinAllInBackground("category_list", list);
                            }
                        });
                        for (int i = 0; i < list.size(); i++)
                        {
                            productCategories.add(list.get(i));
                        }
                        delegate.processFinish(LOG_TAG, AppConstants.RESULT_CATEGORY_LIST);

                    }
                    else {
                        Log.d(LOG_TAG, e.getMessage());
                    }
                }
            });
        }
        else {
            getAllCategoryLocal();
        }
    }

    private void getAllCategoryLocal() {
        ParseQuery<Category> query = Category.getQuery();
        query.orderByAscending("category_name");
        query.fromPin("category_list");
        query.findInBackground(new FindCallback<Category>()
        {
            @Override
            public void done(List<Category> list, ParseException e)
            {
                if (e == null)
                {
                    productCategories.clear();
                    for (int i = 0; i < list.size(); i++)
                    {
                        productCategories.add(list.get(i));
                    }
                    delegate.processFinish(LOG_TAG, AppConstants.RESULT_CATEGORY_LIST);
                }
                else
                {
                    delegate.processFinish(LOG_TAG, AppConstants.RESULT_CATEGORY_LIST_ERROR);
                }
            }
        });
    }

    public void loginArtisan(String mobile) {

        if ((ni != null) && (ni.isConnected()))
        {
            ParseUser.logInInBackground(mobile, "password", new LogInCallback()
            {
                @Override
                public void done(ParseUser parseUser, ParseException e)
                {
                    if (e == null)
                    {
                        SharedPreferences.Editor editor = getSharedPreferences("Parse", MODE_PRIVATE).edit();
                        editor.putString("objectId", parseUser.getObjectId());
                        editor.apply();
                       // getAllProductsFromCurrentArtisan();
                    }
                    else
                    {
                        Log.d(LOG_TAG, e.getMessage());
                    }
                }
            });
        }
        else {
          //  getAllProductsFromCurrentArtisanOffline();
        }
    }

    public void getAllProductsFromCurrentArtisan()
    {
        if ((ni != null) && (ni.isConnected()))
        {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.include("user_products");
            query.include("user_products.product_category");

            query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseUser>()
            {
                @Override
                public void done(final ParseUser artisan, ParseException e)
                {
                    if (e == null)
                    {
                        @SuppressWarnings("unchecked") final ArrayList<Product> currentProducts = (ArrayList<Product>) artisan.get("user_products");
                        ParseObject.unpinAllInBackground("user_product_list", currentProducts, new DeleteCallback()
                        {
                            public void done(ParseException e)
                            {
                                if (e != null)
                                {
                                    Log.d(LOG_TAG, e.getMessage());
                                    return;
                                }

                                // Add the latest results for this query to the cache.
                                ParseObject.pinAllInBackground("user_product_list", currentProducts);
                            }
                        });
                        currentArtisanProducts.clear();
                        currentArtisanProducts.addAll(currentProducts);
                        delegate.processFinish("manager", AppConstants.RESULT_PRODUCT_LIST);
                    }
                    else
                    {
                        getAllProductsFromCurrentArtisanOffline();
                    }
                }
            });
        }
        else {
            getAllProductsFromCurrentArtisanOffline();
        }
    }

    private void getAllProductsFromCurrentArtisanOffline()
    {
        SharedPreferences prefs = getSharedPreferences("Parse", MODE_PRIVATE);
        String restoredText = prefs.getString("objectId", null);
        String objectId = null;
        if (restoredText != null) {
            objectId = prefs.getString("objectId", "");//"No name defined" is the default value.
        }

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.include("user_products");
        query.include("user_products.product_category");
        query.fromLocalDatastore();
        query.getInBackground(objectId, new GetCallback<ParseUser>()
        {
            @Override
            public void done(final ParseUser artisan, ParseException e)
            {
                if (e == null)
                {
                    @SuppressWarnings("unchecked") final ArrayList<Product> currentProducts = (ArrayList<Product>) artisan.get("user_products");
                    currentArtisanProducts.clear();
                    currentArtisanProducts.addAll(currentProducts);
                    delegate.processFinish("manager", AppConstants.RESULT_PRODUCT_LIST);
                }
                else
                {
                    delegate.processFinish(LOG_TAG, AppConstants.RESULT_PRODUCT_LIST_ERROR);
                }
            }
        });
    }
}
