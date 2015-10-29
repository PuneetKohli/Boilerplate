package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;
import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.codepond.wizardroid.WizardStep;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductStep3_image extends WizardStep
{

    private static final String APP_ID = "fT_vdVp2I1INwwolmoF5G_cG2gbnRONmS4QnZpgH";
    private static final String APP_SECRET = "qFNVrIR411_-zR6ssALK_5UuB9QwcHx6RprHBGbb";
    private Bitmap bm;
    private static final int CODE_PICK = 1;

    private final ClarifaiClient client = new ClarifaiClient(APP_ID, APP_SECRET);

    @Bind(R.id.container_layout_menu) View containerViewInitial; //Make this invisible on image shown
    @Bind(R.id.container_layout_preview) View containerViewPreview; //Make this visible on image shown
    @Bind(R.id.image_preview) ImageView imageView;

    @OnClick(R.id.layout_add_image_from_camera)
    void addFromCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, AppConstants.REQUEST_CAMERA);
    }

    @OnClick(R.id.layout_add_image_from_gallery)
    void addFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), AppConstants.REQUEST_GALLERY);
    }

    @OnClick(R.id.closeImage) void close() {
        imageView.setImageBitmap(null);
        byte[] data = {0};
        ParseFile a = new ParseFile("null", data);
        ((AddProductActivity) getActivity()).manager.currentProduct.setProductImage(a);
        ((AddProductActivity) getActivity()).manager.currentProduct.setProductTags(new ArrayList<String>());
        containerViewInitial.setVisibility(View.VISIBLE);
        containerViewPreview.setVisibility(View.INVISIBLE);

    }

    //You must have an empty constructor for every step
    public AddProductStep3_image()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_form_add_image, container, false);
        ButterKnife.bind(this, v);
        byte[] data = {0};
        ParseFile a = new ParseFile("null", data);
        ((AddProductActivity) getActivity()).manager.currentProduct.setProductImage(a);
        ((AddProductActivity) getActivity()).manager.currentProduct.setProductTags(new ArrayList<String>());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("3. Upload Image");
        containerViewPreview.setVisibility(View.INVISIBLE);
        return v;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == getActivity().RESULT_OK) {
            // The user picked an image. Send it to Clarifai for recognition.
            //Log.d("", "User picked image: " + intent.getData());
            if (requestCode == AppConstants.REQUEST_CAMERA) {
                bm = (Bitmap) intent.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                assert bm != null;
                bm.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try
                {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if(requestCode == AppConstants.REQUEST_GALLERY) {
                bm = loadBitmapFromUri(intent.getData());
            }

            if (bm != null) {
                containerViewInitial.setVisibility(View.INVISIBLE);
                containerViewPreview.setVisibility(View.VISIBLE);

                imageView.setImageBitmap(bm);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byteArray = stream.toByteArray();

                final ParseFile image = new ParseFile("temp", byteArray);
                image.saveInBackground(new SaveCallback()
                {
                    @Override
                    public void done(ParseException e)
                    {
                        ((AddProductActivity) getActivity()).manager.currentProduct.setProductImage(image);
                    }
                });
                // Run recognition on a background thread since it makes a network call.
                new AsyncTask<Bitmap, Void, RecognitionResult>() {
                    @Override protected RecognitionResult doInBackground(Bitmap... bitmaps) {
                        return recognizeBitmap(bitmaps[0]);
                    }
                    @Override protected void onPostExecute(RecognitionResult result) {
                        updateUIForResult(result);
                    }
                }.execute(bm);
            } else {
                Toast.makeText(getActivity(), "Unable to load Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap loadBitmapFromUri(Uri uri) {
        try {
            // The image may be large. Load an image that is sized for display. This follows best
            // practices from http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, opts);
            int sampleSize = 1;
            while (opts.outWidth / (2 * sampleSize) >= imageView.getWidth() &&
                    opts.outHeight / (2 * sampleSize) >= imageView.getHeight()) {
                sampleSize *= 2;
            }

            opts = new BitmapFactory.Options();
            opts.inSampleSize = sampleSize;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, opts);
        } catch (IOException e) {
            Log.e("lol", "Error loading image: " + uri, e);
        }
        return null;
    }

    /** Sends the given bitmap to Clarifai for recognition and returns the result. */
    private RecognitionResult recognizeBitmap(Bitmap bitmap) {
        try {
            // Scale down the image. This step is optional. However, sending large images over the
            // network is slow and  does not significantly improve recognition performance.
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 320,
                    320 * bitmap.getHeight() / bitmap.getWidth(), true);

            // Compress the image as a JPEG.
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 90, out);
            byte[] jpeg = out.toByteArray();

            // Send the JPEG to Clarifai and return the result.
            return client.recognize(new RecognitionRequest(jpeg)).get(0);
        } catch (ClarifaiException e) {
            Log.e("lol", "Clarifai error", e);
            return null;
        }
    }

    /** Updates the UI by displaying tags for the given result. */
    private void updateUIForResult(RecognitionResult result) {
        if (result != null) {
            if (result.getStatusCode() == RecognitionResult.StatusCode.OK) {
                // Display the list of tags in the UI.
                StringBuilder b = new StringBuilder();
                for (Tag tag : result.getTags()) {
                    ((AddProductActivity) getActivity()).manager.currentProduct.addProductTags(tag.getName());
                    Log.e("lol", tag.getName());
                }
            } else {
                Log.e("lol", "Clarifai: " + result.getStatusMessage());
            }
        } else {
            Log.e("lol", "Sorry, there was an error recognizing your image.: " + result.getStatusMessage());
        }
    }
}
