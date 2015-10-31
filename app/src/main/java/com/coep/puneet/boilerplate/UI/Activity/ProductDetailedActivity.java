package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coep.puneet.boilerplate.R;
import com.parse.ParseFile;

import java.util.ArrayList;

import butterknife.Bind;

public class ProductDetailedActivity extends BaseActivity
{

    @Bind(R.id.tv_product_title) TextView productName;
    @Bind(R.id.tv_product_price) TextView productPrice;
    @Bind(R.id.image_product_img) ImageView productImage;
    @Bind(R.id.tv_product_category) TextView productCategory;
    @Bind(R.id.tv_keypoints) TextView productTags;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.step_summary;
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
        productName.setText(manager.currentProduct.getProduct_name());
        productPrice.setText(manager.currentProduct.getProductPrice() + "");
        ParseFile image = manager.currentProduct.getProductImage();
        String url = image.getUrl();
        Glide.with(ProductDetailedActivity.this).load(url).asBitmap().centerCrop().placeholder(R.drawable.ab_background).into(productImage);
        productCategory.setText(manager.currentProduct.getCategory().getCategory_name());
        ArrayList<String> tags = (manager.currentProduct.getProductTags());
        String keypoints = "";
        if (tags.size() > 0)
        {

            for (int i = 0; i < tags.size() - 1; i++)
                keypoints += tags.get(i) + ", ";

            keypoints += tags.get(tags.size() - 1);
        }
        productTags.setText(keypoints);
    }

}
