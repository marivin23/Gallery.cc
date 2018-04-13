package com.cucc.gallerycc;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Data extends AppCompatActivity {

    private static ArrayList<String> links;

    public Data(){



    }

    public ArrayList<String> getLinks() throws IOException {

        links = new ArrayList<String>();
        String link;

        AssetManager am = getAssets();
        InputStream is = am.open("links.txt");
        int size = is.available();
        byte[] buffer = new byte[size];

        is.read(buffer);
        is.close();

        link = new String(buffer);
        String[] linkString = link.split("\n");

        for(String s : linkString){
            links.add(s);
        }


        return links;
    }

    public static int getLinkSize(){
        return links.size();
    }

}
