package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;

import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddProductStep_summary extends WizardStep
{

    @Bind(R.id.tv_product_title) TextView productName;
    @Bind(R.id.tv_product_price) TextView productPrice;
    @Bind(R.id.image_product_img) ImageView productImage;
    @Bind(R.id.tv_product_category) TextView productCategory;
    @Bind(R.id.tv_keypoints) TextView productTags;

    //You must have an empty constructor for every step
    public AddProductStep_summary()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_summary, container, false);
        ButterKnife.bind(this, v);

        productName.setText(((AddProductActivity) getActivity()).manager.currentProduct.getProduct_name());
        productPrice.setText(((AddProductActivity) getActivity()).manager.currentProduct.getProductPrice() + "");
        productImage.setImageBitmap(((AddProductActivity) getActivity()).manager.currentBm);
        productCategory.setText(((AddProductActivity) getActivity()).manager.currentProduct.getCategory().getCategory_name());
        ArrayList<String> tags = ((AddProductActivity) getActivity()).manager.currentProduct.getProductTags();
        String keypoints = "";
        if (tags.size() > 0)
        {

            for (int i = 0; i < tags.size() - 1; i++)
                keypoints += tags.get(i) + ", ";

            keypoints += tags.get(tags.size() - 1);
        }
        productTags.setText(keypoints);

        return v;
    }

    @Override
    public void onExit(int exitCode)
    {
        switch (exitCode)
        {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields()
    {
        //Do some work
        //...
        //The values of these fields will be automatically stored in the wizard context
        //and will be populated in the next steps only if the same field names are used.
        //firstname = etProductName.getText().toString();
    }
}
