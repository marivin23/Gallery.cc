package com.cucc.gallerycc;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ViewPagerAdapter extends PagerAdapter{

    Activity activity;
    String[] images = new String[1];
    LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity, String[] images) {
        this.activity = activity;
        this.images[0] = images[0];
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, (ViewGroup) container, false);

        ImageView image;
        image = (ImageView) itemView.findViewById(R.id.imageViewPager);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int widht = dm.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(widht);

        String msg = images[0];

        Log.i("ViewPagerURL", msg.toString());

        try{
            Picasso.get()
                    .load(images[0])
                    .into(image);

        } catch (Exception e) {}

        ((ViewGroup) container).addView(itemView);
        return itemView;
    }
}
