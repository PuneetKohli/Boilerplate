package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coep.puneet.boilerplate.R;

import org.codepond.wizardroid.WizardStep;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductStep3_image extends WizardStep
{
    @Bind(R.id.container_layout_menu) View containerViewInitial; //Make this invisible on image shown
    @Bind(R.id.container_layout_preview) View containerViewPreview; //Make this visible on image shown

    @OnClick(R.id.layout_add_image_from_camera)
    void addFromCamera()
    {
    }

    @OnClick(R.id.layout_add_image_from_gallery)
    void addFromGallery()
    {
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("3. Upload Image");
        containerViewPreview.setVisibility(View.INVISIBLE);
        return v;
    }

}
