package com.coep.puneet.boilerplate.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends BaseActivity
{

    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.profile_address_layout) ViewGroup AddressLayout;
    @Bind(R.id.profile_email_layout) LinearLayout EmailLayout;
    @Bind(R.id.profile_phone_layout) LinearLayout PhoneLayout;
    @Bind(R.id.tv_artisan_name) TextView mArtisanName;
    @Bind(R.id.tv_artisan_location) TextView mArtisanLocation;
    @Bind(R.id.tv_artisan_product_count) TextView mArtisanProductCount;
    @Bind(R.id.artisan_profile_image) CircleImageView profileImage;
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
        mArtisanProductCount.setText("" + manager.currentArtisanProducts.size());
        mArtisanName.setText(ParseUser.getCurrentUser().getString("name"));
        mArtisanLocation.setText("" + ParseUser.getCurrentUser().getString("location"));
        phoneText = ButterKnife.findById(PhoneLayout, R.id.profile_item_text);
        addressText = ButterKnife.findById(AddressLayout, R.id.profile_item_text);
        emailText = ButterKnife.findById(EmailLayout, R.id.profile_item_text);
        phoneText.setText(ParseUser.getCurrentUser().getString("phone"));
        addressText.setText(ParseUser.getCurrentUser().getString("address"));
        emailText.setText(ParseUser.getCurrentUser().getString("email"));

        phoneIcon = ButterKnife.findById(PhoneLayout, R.id.profile_item_image);
        emailIcon = ButterKnife.findById(EmailLayout, R.id.profile_item_image);
        addressIcon = ButterKnife.findById(AddressLayout, R.id.profile_item_image);
        phoneIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_call));
        emailIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_email));
        addressIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_grey_24dp));

        ParseFile profile_image = ParseUser.getCurrentUser().getParseFile("profile_image");
        Glide.with(this).load(profile_image.getUrl()).asBitmap().centerCrop().placeholder(R.drawable.background_material).into(profileImage);
    }

    String field = "";
    TextView textView = null;

    @OnClick({R.id.profile_address_layout, R.id.profile_email_layout, R.id.profile_phone_layout})
    public void showDetailFillAlert(LinearLayout view)
    {

        if (view == PhoneLayout)
        {
            field = "Phone number";
            textView = phoneText;
        }
        else if (view == AddressLayout)
        {
            field = "Address";
            textView = addressText;
        }
        else if (view == EmailLayout)
        {
            field = "Email";
            textView = emailText;
        }

        //AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Edit " + field);
        View customDialogView = inflater.inflate(R.layout.profile_popup_edit_details, null, false);
        final EditText popupEdittext = (EditText) customDialogView.findViewById(R.id.popup_editText);
        final String initialText = textView.getText().toString().trim();
        popupEdittext.setText(initialText);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (popupEdittext.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(ProfileActivity.this, "You can not leave this field Blank!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!initialText.equals(popupEdittext.getText().toString().trim()))
                    {
                        isEdited = true;
                    }
                    textView.setText(popupEdittext.getText().toString());
                }
            }
        });
        builder.setView(customDialogView);
        builder.create();
        builder.show();
    }

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
            changeImage();
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