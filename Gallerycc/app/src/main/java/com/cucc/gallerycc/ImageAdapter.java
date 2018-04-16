package com.cucc.gallerycc;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    protected Uri fullImageUri;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView1);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            Log.i("ImgPress","pressed");

        }
    }

    private List<Uri> images;

    public ImageAdapter(List<Uri> uris){
        images = uris;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder viewHolder, int position){
        Uri imageUri = images.get(position);
        //getFullImageUri(imageUri);
        Log.i("PicassoURI", imageUri.toString());
        Picasso.get()
                .load(new File(imageUri.toString()))
                .placeholder(R.drawable.untitled)
                .fit()
                .centerCrop()
                .into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    /*protected void getFullImageUri(Uri u){
        fullImageUri = u;
    }*/

}