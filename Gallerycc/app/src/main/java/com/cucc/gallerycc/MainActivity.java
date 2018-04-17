package com.cucc.gallerycc;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Context context = this;

    Data buffer = new Data();
    private static ArrayList<String> links = new ArrayList<>();
    private AsyncTask task = new DownloadImageList();
    private List<String> imagesUri = new ArrayList<>();
    protected RecyclerView recyclerView;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Gallery.cc");
        dialog.setMessage("Downloading database file...");
        dialog.setCancelable(false);

        task = new DownloadImageList().execute("https://www.dropbox.com/s/6rzn8e2x51x9qv2/images.txt?dl=1");

    }

    public static void verifyStoragePermissions(Activity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }

    public class DownloadImageList extends AsyncTask<String, Integer, String> {

        private URL url;

        @Override
        protected void onPreExecute(){

            verifyStoragePermissions(MainActivity.this);
            dialog.show();
            dialog.setProgress(0);

        }


        @Override
        protected String doInBackground(String... url) {
            int count = url.length;

            HttpURLConnection connection = null;

            int byteCount;
            String absolutePathOfFile = "";

            for(int i = 0; i < count; i++) {

                URL urlFile = stringToURL(url[0]);

                    try {

                        String root = Environment.getExternalStorageDirectory().toString();

                        connection = (HttpURLConnection) urlFile.openConnection();
                        connection.connect();

                        if (connection != null) Log.i("Connection: ", "Connected!");
                        else Log.i("Connection: ", "Not connected!");

                        InputStream is = new BufferedInputStream(urlFile.openStream(), 8192);
                        OutputStream os = new FileOutputStream(root + "/links.txt");
                        byte data[] = new byte[1024];
                        long total = 0;

                        while((byteCount = is.read(data)) != -1){
                            total += count;
                            os.write(data, 0, byteCount);
                        }

                        os.flush();
                        os.close();
                        is.close();

                        absolutePathOfFile = root + "/links.txt";
                        Log.i("path", absolutePathOfFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        connection.disconnect();
                    }

                publishProgress((int) (((i) / (float) count) * 100));

            }

            return absolutePathOfFile;
        }

        protected void onProgressUpdate(Integer...integers){
            dialog.setProgress(integers[0]);
        }

        @Override
        protected void onPostExecute(String absolutePath){

            dialog.dismiss();

            Data links = new Data();

            try {
                links.getLinks(absolutePath);
                imagesUri = links.getLinksList();
            }
            catch (IOException e) {}

            recyclerView = findViewById(R.id.rvImages);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(new ImageAdapter(imagesUri));

        }

        private URL stringToURL(String string) {

            try{
                URL url = new URL(string);
                return url;
            }catch(MalformedURLException e){
                e.printStackTrace();
            }
            return null;

        }

    }

}
