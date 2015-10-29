package com.coep.puneet.boilerplate.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coep.puneet.boilerplate.ParseObjects.Category;
import com.coep.puneet.boilerplate.R;

import java.util.ArrayList;

/**
 * Created by Arun on 29-Oct-15.
 */
public class CategoryGridAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<Category> categoryList;
    private int height;

    public CategoryGridAdapter(Context mContext, ArrayList<Category> categoryList)
    {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount()
    {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.home_grid_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.nav_image);


            TextView textView = (TextView) gridView.findViewById(R.id.nav_label);

            //imageView.setImageResource(mNavIds[position]);
            //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height / 4));

            textView.setText(categoryList.get(position).getCategory_name());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
