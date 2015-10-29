package com.coep.puneet.boilerplate.UI.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep2_name;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep1_category;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep3;
import com.coep.puneet.boilerplate.UI.Fragment.steps.AddProductStep4_price;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.codepond.wizardroid.persistence.ContextManager;
import org.codepond.wizardroid.persistence.ContextVariable;
import org.w3c.dom.Text;

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

    /**
     * Tell WizarDroid that these are context variables and set default values.
     * These values will be automatically bound to any field annotated with {@link ContextVariable}.
     * NOTE: Context Variable names are unique and therefore must
     * have the same name and type wherever you wish to use them.
     */
    @ContextVariable private String firstname = "WizarDroid";
    @ContextVariable private String lastname = "CondPond.org";

    public AddProductWizardFragment()
    {
        super();
    }

    public AddProductWizardFragment(ContextManager contextManager)
    {
        super(contextManager);
    }

    /*
        You must override this method and create a wizard flow by
        using WizardFlow.Builder as shown in this example
     */

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
        if(this.wizard.isFirstStep())
        {
            this.prevImage.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
        else
            this.prevImage.clearColorFilter();
        if(!this.wizard.canGoNext())
        {
            this.nextImage.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
        else
            this.nextImage.clearColorFilter();
        if(this.wizard.isLastStep())
            this.nextImage.setVisibility(View.INVISIBLE);
        else
            this.nextImage.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_next_button)
    void goNext()
    {
        this.wizard.goNext();
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
