package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;
import com.coep.puneet.boilerplate.UI.Adapter.CategoryGridAdapter;

import org.codepond.wizardroid.WizardStep;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * This step will block the user from proceeding to the next step
 * unless the user mark the checkbox. The step is marked as required
 * when the wizard flow is built.
 */
public class AddProductStep1_category extends WizardStep
{

    @Bind(R.id.category_grid_view) GridView categoryGridview;

    //You must have an empty constructor for every step
    public AddProductStep1_category()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_form_add_category, container, false);
        ButterKnife.bind(this, v);

        categoryGridview.setAdapter(new CategoryGridAdapter(getActivity(), ((AddProductActivity)getActivity()).manager.productCategories));
        return v;
    }
}
