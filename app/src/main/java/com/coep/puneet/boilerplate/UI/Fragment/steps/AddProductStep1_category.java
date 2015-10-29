package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.coep.puneet.boilerplate.R;

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

    @Bind(R.id.sample_form2_checkbox) CheckBox checkBox;

    //You must have an empty constructor for every step
    public AddProductStep1_category()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_form2, container, false);
        ButterKnife.bind(this, v);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    //Notify that the step is completed
                    notifyCompleted();
                }
                else
                {
                    //Notify that the step is incomplete
                    notifyIncomplete();
                }
            }
        });
        return v;
    }
}
