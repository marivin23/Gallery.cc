package com.cucc.gallerycc;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;

        public ViewHolder(View itemView) {

            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView1);

        }

    }

    public List<String> images;

    public ImageAdapter(List<String> uris){
        images = uris;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageAdapter.ViewHolder viewHolder, final int position){

        Log.i("PicassoURI", images.get(position));

        Picasso.get()
                .load(images.get(position))
                .placeholder(R.drawable.untitled)
                .fit()
                .centerCrop()
                .into(viewHolder.img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( viewHolder.itemView.getContext(), FullImageView.class);
                intent.putExtra("extra", images.get(position));
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}