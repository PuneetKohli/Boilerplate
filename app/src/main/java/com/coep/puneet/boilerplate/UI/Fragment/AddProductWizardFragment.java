package com.coep.puneet.boilerplate.UI.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep1_category;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep2_name;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep3;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep4_price;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.codepond.wizardroid.persistence.ContextManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A sample to demonstrate a form in multiple steps.
 */
public class AddProductWizardFragment extends WizardFragment
{
    @Bind(R.id.image_prev_button) ImageView prevImage;
    @Bind(R.id.image_next_button) ImageView nextImage;
    @Bind(R.id.wizard_next_button) TextView nextButton;
    @Bind(R.id.wizard_previous_button) TextView previousButton;

    private String mNextButtonText;
    private String mFinishButtonText;
    private String mBackButtonText;
    private ViewPager mViewPager;

    public AddProductWizardFragment()
    {
        super();
    }

    public AddProductWizardFragment(ContextManager contextManager)
    {
        super(contextManager);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.custom_wizard_layout, container, false);
        ButterKnife.bind(this, rootView);

        this.nextButton.setText(this.getNextButtonLabel());
        this.previousButton.setText(this.getBackButtonLabel());

        return rootView;
    }

    public void onResume()
    {
        super.onResume();
        this.updateWizardControls();
    }

    public void onStepChanged()
    {
        super.onStepChanged();
        this.updateWizardControls();
    }

    private void updateWizardControls()
    {
        this.previousButton.setEnabled(!this.wizard.isFirstStep());
        this.previousButton.setText(this.getBackButtonLabel());
        this.nextButton.setEnabled(this.wizard.canGoNext());
        this.nextButton.setText(this.wizard.isLastStep() ? this.getFinishButtonText() : this.getNextButtonLabel());
        if (this.wizard.isFirstStep())
        {
            this.prevImage.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
        else this.prevImage.clearColorFilter();
        if (!this.wizard.canGoNext())
        {
            this.nextImage.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
        else this.nextImage.clearColorFilter();
        if (this.wizard.isLastStep()) this.nextImage.setVisibility(View.INVISIBLE);
        else this.nextImage.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_next_button)
    void goNext()
    {

        int step = this.wizard.getCurrentStepPosition();
        switch (step)
        {
            case 0:
                this.wizard.goNext();
                break;
            case 1:
                String name = ((AddProductActivity) getActivity()).manager.currentProduct.getProduct_name();
                if (name.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View customDialogView = inflater.inflate(R.layout.profile_popup_edit_details, null, false);
                    builder.setTitle("Error");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                        }
                    });
                    builder.setView(customDialogView);
                    builder.create();
                    builder.show();
                }
                else
                {
                    this.wizard.goNext();
                }
                break;
            case 2:
                int price = ((AddProductActivity) getActivity()).manager.currentProduct.getProductPrice();
                int quantity = ((AddProductActivity) getActivity()).manager.currentProduct.getProductQuantity();
                if (price == 0 || quantity == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View customDialogView = inflater.inflate(R.layout.profile_popup_edit_details, null, false);
                    final TextView popupEdittext = (TextView) customDialogView.findViewById(R.id.popup_editText);
                    if(price == 0 && quantity == 0)
                        popupEdittext.setText("Please Enter Price and Quantity");
                    else if(price == 0)
                        popupEdittext.setText("Please Enter Price");
                    else
                        popupEdittext.setText("Please Enter Quantity");

                    builder.setTitle("Error");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                        }
                    });
                    builder.setView(customDialogView);
                    builder.create();
                    builder.show();
                }
                else
                {
                    this.wizard.goNext();
                }
                break;
            case 3:
                break;

        }

    }

    @OnClick(R.id.layout_prev_button)
    void goBack()
    {
        this.wizard.goBack();
    }

    @Override
    public WizardFlow onSetup()
    {
        return new WizardFlow.Builder()
                /*
                Add your steps in the order you want them to appear and eventually call create()
                to create the wizard flow.
                 */.addStep(AddProductStep1_category.class, true)
                /*
                Mark this step as 'required', preventing the user from advancing to the next step
                until a certain action is taken to mark this step as completed by calling WizardStep#notifyCompleted()
                from the step.
                 */.addStep(AddProductStep2_name.class).addStep(AddProductStep4_price.class).addStep(AddProductStep3.class).create();
    }

    /*
        You'd normally override onWizardComplete to access the wizard context and/or close the wizard
     */
    @Override
    public void onWizardComplete()
    {
        super.onWizardComplete();   //Make sure to first call the super method before anything else
        //... Access context variables here before terminating the wizard
        //...
        //String fullname = firstname + lastname;
        //Store the data in the DB or pass it back to the calling activity
        getActivity().finish();     //Terminate the wizard
    }

    public String getNextButtonLabel()
    {
        return TextUtils.isEmpty(this.mNextButtonText) ? this.getResources().getString(org.codepond.wizardroid.R.string.action_next) : this.mNextButtonText;
    }

    public void setNextButtonText(String nextButtonText)
    {
        this.mNextButtonText = nextButtonText;
    }

    public String getFinishButtonText()
    {
        return TextUtils.isEmpty(this.mFinishButtonText) ? this.getResources().getString(org.codepond.wizardroid.R.string.action_finish) : this.mFinishButtonText;
    }

    public void setFinishButtonText(String finishButtonText)
    {
        this.mFinishButtonText = finishButtonText;
    }

    public String getBackButtonLabel()
    {
        return TextUtils.isEmpty(this.mBackButtonText) ? this.getResources().getString(org.codepond.wizardroid.R.string.action_previous) : this.mBackButtonText;
    }

    public void setBackButtonText(String backButtonText)
    {
        this.mBackButtonText = backButtonText;
    }
}
