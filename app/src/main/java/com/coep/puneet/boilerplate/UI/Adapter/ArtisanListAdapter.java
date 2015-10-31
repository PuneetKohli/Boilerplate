package com.coep.puneet.boilerplate.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coep.puneet.boilerplate.R;
import com.parse.ParseUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtisanListAdapter extends RecyclerView.Adapter<ArtisanListAdapter.VHArtisan>
{
    private Context mContext;
    private ArrayList<ParseUser> artisanList;

    public ArtisanListAdapter(Context mContext, ArrayList<ParseUser> artisanList)
    {
        this.mContext = mContext;
        this.artisanList = artisanList;

    }

    @Override
    public ArtisanListAdapter.VHArtisan onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.artisan_list_item, parent, false);
        return new VHArtisan(view);
    }

    @Override
    public void onBindViewHolder(ArtisanListAdapter.VHArtisan holder, int position)
    {
        ParseUser user = artisanList.get(position);

        holder.name.setText(user.getString("name"));
        holder.category.setText(user.getString("primary_category"));

        Glide.with(mContext).load(user.getParseFile("profile_image").getUrl()).asBitmap().centerCrop().placeholder(R.drawable.photo).into(holder.image);
    }

    @Override
    public int getItemCount()
    {
        return artisanList.size();
    }

    public static class VHArtisan extends RecyclerView.ViewHolder
    {
        public final TextView name;
        public final TextView category;
        public final CircleImageView image;

        public VHArtisan(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.artisanName);
            category = (TextView) view.findViewById(R.id.artisanCategory);
            image = (CircleImageView) view.findViewById(R.id.artisanProfilePic);
        }
    }
}
