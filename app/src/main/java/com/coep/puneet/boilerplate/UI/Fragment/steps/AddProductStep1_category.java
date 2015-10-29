package com.coep.puneet.boilerplate.UI.Fragment.steps;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coep.puneet.boilerplate.ParseObjects.Category;
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
    int selectedIndex = -1;

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

        categoryGridview.setAdapter(new CategoryGridAdapter(getActivity(), ((AddProductActivity) getActivity()).manager.productCategories));
        categoryGridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                                    {
                                                        //view.setBackgroundResource(R.color.signal_green);
                                                        if (position == selectedIndex)
                                                        {

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
