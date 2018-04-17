package com.cucc.gallerycc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data extends AppCompatActivity {

    private Context context;

    private static ArrayList<String> links;

    public Data(){
    }

    public void getLinks(String path) throws IOException {

        links = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(path));

        String msg = br.readLine();


        while(!(msg == null)){
            links.add(msg);
            //Log.i("Link: ", msg);
            msg = br.readLine();
        }

    }

    public static ArrayList<String> getLinksList(){ return links; }

}
