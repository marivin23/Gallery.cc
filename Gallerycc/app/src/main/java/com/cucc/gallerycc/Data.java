package com.cucc.gallerycc;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Data extends AppCompatActivity {

    private Context context;

    private static ArrayList<String> links;

    public Data(Context context){
        this.context = context;
    }

    public void getLinks() throws IOException {

        links = new ArrayList<String>();
        String link;

        InputStream is = context.getAssets().open("links.txt");
        int size = is.available();
        byte[] buffer = new byte[size];

        is.read(buffer);
        is.close();

        link = new String(buffer);
        String[] linkString = link.split("\n");

        for(String s : linkString){
            links.add(s);
            Log.i("Link: ", s);
        }

    }

    public static int getLinkSize(){
        return links.size();
    }
    public static ArrayList<String> getLinksList(){ return links; }

}
