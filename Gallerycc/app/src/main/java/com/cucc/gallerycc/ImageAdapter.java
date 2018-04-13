package com.cucc.gallerycc;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
        public ImageView img4;
        public ImageView img5;
        public ImageView img6;
        public ImageView img7;
        public ImageView img8;
        public ImageView img9;
        public ImageView img10;
        public ImageView img11;
        public ImageView img12;

        public ViewHolder(View itemView) {
            super(itemView);

            img1 = (ImageView) itemView.findViewById(R.id.imageView1);
            img2 = (ImageView) itemView.findViewById(R.id.imageView2);
            img3 = (ImageView) itemView.findViewById(R.id.imageView3);
            img4 = (ImageView) itemView.findViewById(R.id.imageView4);
            img5 = (ImageView) itemView.findViewById(R.id.imageView5);
            img6 = (ImageView) itemView.findViewById(R.id.imageView6);
            img7 = (ImageView) itemView.findViewById(R.id.imageView7);
            img8 = (ImageView) itemView.findViewById(R.id.imageView8);
            img9 = (ImageView) itemView.findViewById(R.id.imageView9);
            img10 = (ImageView) itemView.findViewById(R.id.imageView10);
            img11 = (ImageView) itemView.findViewById(R.id.imageView11);
            img12 = (ImageView) itemView.findViewById(R.id.imageView12);
        }
    }

    private List<Data> images;

    public ImageAdapter(List<Data> imagesP){
        images = imagesP;
    }

    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View imageView = inflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(imageView);
        return viewHolder;
    }

    public void onBindViewHolder(ImageAdapter.ViewHolder viewHolder, int position){
        Data data = images.get(position);

        ImageView imageView1 = viewHolder.img1;
        ImageView imageView2 = viewHolder.img2;
        ImageView imageView3 = viewHolder.img3;
        ImageView imageView4 = viewHolder.img4;
        ImageView imageView5 = viewHolder.img5;
        ImageView imageView6 = viewHolder.img6;
        ImageView imageView7 = viewHolder.img7;
        ImageView imageView8 = viewHolder.img8;
        ImageView imageView9 = viewHolder.img9;
        ImageView imageView10 = viewHolder.img10;
        ImageView imageView11 = viewHolder.img11;
        ImageView imageView12 = viewHolder.img12;



    }

}
