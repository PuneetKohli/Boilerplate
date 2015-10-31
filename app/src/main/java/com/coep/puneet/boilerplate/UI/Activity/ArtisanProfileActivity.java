package com.coep.puneet.boilerplate.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.ParseObjects.Product;
import com.coep.puneet.boilerplate.R;
import com.parse.ParseFile;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;



public class ArtisanProfileActivity extends BaseActivity
{

    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.profile_address_layout) ViewGroup AddressLayout;
    @Bind(R.id.profile_email_layout) LinearLayout EmailLayout;
    @Bind(R.id.profile_phone_layout) LinearLayout PhoneLayout;
    @Bind(R.id.tv_artisan_name) TextView mArtisanName;
    @Bind(R.id.tv_artisan_location) TextView mArtisanLocation;
    @Bind(R.id.tv_artisan_product_count) TextView mArtisanProductCount;
    @Bind(R.id.artisan_profile_image) CircleImageView profileImage;
    @Bind(R.id.artisan_primary_category) TextView primaryCategory;
    private boolean isEdited = false;
    private Bitmap bm;
    private File f = null;


    @OnClick(R.id.artisan_profile_image)
    void changeImage()
    {
        new BottomSheet.Builder(this).title("Change Profile Picture").sheet(R.menu.menu_upload_profileimage).listener(new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case R.id.uploadGallery:
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), AppConstants.REQUEST_GALLERY);
                        break;
                    case R.id.uploadCamera:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, AppConstants.REQUEST_CAMERA);
                        break;
                }
            }
        }).show();
    }


    ImageView phoneIcon, addressIcon, emailIcon;
    TextView phoneText, addressText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_profile;
    }

    @Override
    protected void setupToolbar()
    {
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        manager.delegate = this;
        int size;
        if(((ArrayList<Product>) manager.currentArtisanSelected.get("user_products")) == null)
            size = 0;
        else
            size = ((ArrayList<Product>) manager.currentArtisanSelected.get("user_products")).size();
        mArtisanProductCount.setText("" + size);
        mArtisanName.setText(manager.currentArtisanSelected.getString("name"));
        mArtisanLocation.setText("" + manager.currentArtisanSelected.getString("location"));
        phoneText = ButterKnife.findById(PhoneLayout, R.id.profile_item_text);
        addressText = ButterKnife.findById(AddressLayout, R.id.profile_item_text);
        emailText = ButterKnife.findById(EmailLayout, R.id.profile_item_text);
        phoneText.setText(manager.currentArtisanSelected.getString("phone"));
        addressText.setText(manager.currentArtisanSelected.getString("address"));
        emailText.setText(manager.currentArtisanSelected.getString("email"));
        primaryCategory.setText(manager.currentArtisanSelected.getString("primary_category"));

        phoneIcon = ButterKnife.findById(PhoneLayout, R.id.profile_item_image);
        emailIcon = ButterKnife.findById(EmailLayout, R.id.profile_item_image);
        addressIcon = ButterKnife.findById(AddressLayout, R.id.profile_item_image);
        phoneIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_call));
        emailIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_email));
        addressIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_grey_24dp));

        ParseFile profile_image = manager.currentArtisanSelected.getParseFile("profile_image");
        Glide.with(this).load(profile_image.getUrl()).asBitmap().centerCrop().placeholder(R.drawable.background_material).into(profileImage);
    }

    String field = "";
    TextView textView = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile_picture)
        {
            return true;
        }
        else if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    void showNoInternetSnackbar()
    {
        Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        }).setActionTextColor(Color.GREEN).show();
    }
}