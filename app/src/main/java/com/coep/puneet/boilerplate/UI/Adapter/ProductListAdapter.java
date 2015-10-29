package com.coep.puneet.boilerplate.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coep.puneet.boilerplate.ParseObjects.Product;
import com.coep.puneet.boilerplate.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.VHProduct>
{

    private final Context mContext;
    private List<Product> mData;


    public ProductListAdapter(Context context, ArrayList<Product> data)
    {
        mContext = context;
        if (data != null)
        {
            mData = data;
        }
        else
        {
            mData = new ArrayList<Product>();
        }
    }

    public void add(Product s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, s);
        notifyItemInserted(position);
    }

    public void remove(int position)
    {
        if (position < getItemCount())
        {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VHProduct onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.products_grid_item, parent, false);
        return new VHProduct(view);
    }

    @Override
    public void onBindViewHolder(VHProduct holder, final int position)
    {
        Product product = mData.get(position);
        String imgPath = "";

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public static class VHProduct extends RecyclerView.ViewHolder
    {


        public VHProduct(View view)
        {
            super(view);

        }
    }
}
