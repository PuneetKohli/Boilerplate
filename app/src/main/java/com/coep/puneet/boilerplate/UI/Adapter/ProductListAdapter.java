package com.coep.puneet.boilerplate.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coep.puneet.boilerplate.ParseObjects.Product;
import com.coep.puneet.boilerplate.R;
import com.parse.ParseFile;

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
        holder.title.setText(product.getProduct_name());
        holder.price.setText("" + product.getProductPrice());

        ParseFile image = product.getProductImage();
        String url = image.getUrl();
        //holder.image.setImageResource(R.drawable.blogspot);
        Glide.with(mContext).load(url).asBitmap().centerCrop().placeholder(R.drawable.ab_background).into(holder.image);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public static class VHProduct extends RecyclerView.ViewHolder
    {
        public final TextView title;
        public final TextView price;
        public final ImageView image;

        public VHProduct(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_product_title);
            price = (TextView) view.findViewById(R.id.tv_product_price);
            image = (ImageView) view.findViewById(R.id.img_product_thumb);
        }
    }
}
