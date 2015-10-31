package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;

import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductStep2_name extends WizardStep
{
    @Bind(R.id.et_product_name) EditText etProductName;
    TextToSpeech tts;

    @OnClick(R.id.say) void speak() {
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status == TextToSpeech.SUCCESS && tts != null) {

                    say(getString(R.string.description_add_name));
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
    @OnClick(R.id.fab_voice_input) void submit() {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, AppConstants.RESULT_SPEECH);
            etProductName.setText("");
            ((AddProductActivity) getActivity()).manager.currentProduct.setProduct_name("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getActivity(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }
    //You must have an empty constructor for every step
    public AddProductStep2_name()
    {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.step_form_add_name, container, false);
        ButterKnife.bind(this, v);

        ((AddProductActivity) getActivity()).manager.currentProduct.setProduct_name("");
        etProductName.setText("");
        etProductName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ((AddProductActivity) getActivity()).manager.currentProduct.setProduct_name(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppConstants.RESULT_SPEECH: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    etProductName.setText(text.get(0));

                }
                break;
            }

        }
    }
}
