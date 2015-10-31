package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.coep.puneet.boilerplate.ParseObjects.Category;
import com.coep.puneet.boilerplate.R;
import com.coep.puneet.boilerplate.UI.Activity.AddProductActivity;
import com.coep.puneet.boilerplate.UI.Adapter.CategoryGridAdapter;

import org.codepond.wizardroid.WizardStep;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This step will block the user from proceeding to the next step
 * unless the user mark the checkbox. The step is marked as required
 * when the wizard flow is built.
 */
public class AddProductStep1_category extends WizardStep
{

    @Bind(R.id.category_grid_view) GridView categoryGridview;
    @OnClick(R.id.say) void speak() {
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status == TextToSpeech.SUCCESS && tts != null) {

                    say(getString(R.string.description_select_category));
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
    int selectedIndex = -1;
    TextToSpeech tts;


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
        ((AddProductActivity) getActivity()).manager.currentProduct.setCategory(new Category());

        categoryGridview.setAdapter(new CategoryGridAdapter(getActivity(), ((AddProductActivity) getActivity()).manager.productCategories));
        categoryGridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                                    {
                                                        //view.setBackgroundResource(R.color.signal_green);
                                                        if (position == selectedIndex)
                                                        {
                                                            ((AddProductActivity) getActivity()).manager.currentProduct.setCategory(new Category());

                                                            for (int i = 0; i < categoryGridview.getChildCount(); i++)
                                                            {
                                                                View v1 = (View) categoryGridview.getChildAt(i);
                                                                v1.findViewById(R.id.selected_tick).setVisibility(View.INVISIBLE);
                                                                TextView currentLetter1 = (TextView) v1.findViewById(R.id.tv_cat_name);
                                                                currentLetter1.setTextColor(getResources().getColor(android.R.color.primary_text_light));
                                                            }
                                                            selectedIndex = -1;
                                                            //notifyIncomplete();
                                                        }
                                                        else
                                                        {
                                                            selectedIndex = position;
                                                            view.findViewById(R.id.selected_tick).setVisibility(View.VISIBLE);
                                                            TextView currentLetter = (TextView) view.findViewById(R.id.tv_cat_name);
                                                            currentLetter.setTextColor(getResources().getColor(R.color.app_primary));
                                                            ((AddProductActivity) getActivity()).manager.currentProduct.setCategory(((AddProductActivity) getActivity()).manager.productCategories.get(selectedIndex));
                                                            //ImageView currentImage = (ImageView) view.findViewById(R.id.ivIcon);
                                                            //currentImage.getDrawable().setColorFilter(0xFFFF5722, PorterDuff.Mode.SRC_ATOP);

                                                            for (int i = 0; i < categoryGridview.getChildCount(); i++)
                                                            {
                                                                if (i != position)
                                                                {
                                                                    View v1 = (View) categoryGridview.getChildAt(i);
                                                                    v1.findViewById(R.id.selected_tick).setVisibility(View.INVISIBLE);
                        /*ImageView currentImage1 = (ImageView) v1.findViewById(R.id.iv_cat_icon);
                        currentImage1.getDrawable().clearColorFilter();*/
                                                                    TextView currentLetter1 = (TextView) v1.findViewById(R.id.tv_cat_name);
                                                                    currentLetter1.setTextColor(getResources().getColor(android.R.color.secondary_text_light_nodisable));
                                                                }
                                                            }
                                                            //notifyCompleted();
                                                        }
                                                    }

                                                }

        );
        return v;
    }
}
