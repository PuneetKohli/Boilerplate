package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;

import com.coep.puneet.boilerplate.R;

import java.util.HashMap;

public class AddProductActivity extends BaseActivity
{
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status == TextToSpeech.SUCCESS && tts != null) {

                    say("abcd");
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


    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_add_product;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
