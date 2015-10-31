package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;

import org.codepond.wizardroid.WizardStep;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductStep4_price extends WizardStep
{

    @Bind(R.id.et_product_price) EditText etPrice;
    @Bind(R.id.et_product_quantity) EditText etQuantity;
    private boolean hasPrice = false, hasQuantity = false;

    TextToSpeech tts;

    @OnClick(R.id.say) void speak() {
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status == TextToSpeech.SUCCESS && tts != null) {

                    say(getString(R.string.description_add_price));
                    //
                    // OnUtteranceCompletedListener
                    //

                    //noinspection deprecation
                }
            }
        });
    }
    private void say(final String s) {
        final HashMap<String, String> map = new HashMap<String, String>(1);
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, AddProductActivity.class.getName());
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, map);
    }


    public AddProductStep4_price()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_form_add_price, container, false);
        ButterKnife.bind(this, v);

        ((AddProductActivity) getActivity()).manager.currentProduct.setProductQuantity(0);
        ((AddProductActivity) getActivity()).manager.currentProduct.setProductPrice(0);
        etPrice.setText("0");
        etQuantity.setText("0");

        etPrice.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(etPrice.getText().toString().equals("0"))
                    etPrice.setText("");
            }
        });
        etPrice.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("blah blahs", s.toString());
                int price = 0;
                if (s.toString().equals("")) price = 0;
                else price = Integer.parseInt(s.toString());
                ((AddProductActivity) getActivity()).manager.currentProduct.setProductPrice(price);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        etQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(etQuantity.getText().toString().equals("0"))
                    etQuantity.setText("");
            }
        });
        etQuantity.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                int quantity = 0;
                if(s.toString().equals(""))
                    quantity = 0;
                else
                    quantity = Integer.parseInt(s.toString());
                ((AddProductActivity) getActivity()).manager.currentProduct.setProductQuantity(quantity);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        return v;
    }

    /**
     * Called whenever the wizard proceeds to the next step or goes back to the previous step
     */

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
