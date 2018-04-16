package com.cucc.gallerycc;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Context context = this;

    Data buffer = new Data(this);
    private static ArrayList<String> links = new ArrayList<>();
    private AsyncTask task = new DownloadImageList();
    private List<Uri> imagesUri = new ArrayList<>();
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

        try {
            buffer.getLinks();
            links = buffer.getLinksList();
        } catch (IOException e) {
            Log.e("getLinks -> ", e.toString());
        }

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Gallery.cc");
        dialog.setMessage("Downloading images...");
        dialog.setCancelable(false);

        task = new DownloadImageList().execute(links);

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

    public class DownloadImageList extends AsyncTask<ArrayList<String>, Integer, List<Bitmap>> {

        private URL url;

        @Override
        protected void onPreExecute(){

            verifyStoragePermissions(MainActivity.this);
            dialog.show();
            dialog.setProgress(0);

        }


        @Override
        protected List<Bitmap> doInBackground(ArrayList<String>... lists) {
            int count = lists.length;

            HttpURLConnection connection = null;
            List<Bitmap> downloadedImages = new ArrayList<>();

            for(int i = 0; i < count; i++) {

                int listSize = lists[i].size();

                for (int j = 0; j < listSize; j++) {

                    URL url = stringToURL(lists[i].get(j));
                    try {

                        connection = (HttpURLConnection) url.openConnection();
                        connection.connect();

                        if (connection != null) Log.i("Connection: ", "Connected!");
                        else Log.i("Connection: ", "Not connected!");

                        InputStream is = connection.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
                        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                        downloadedImages.add(bmp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        connection.disconnect();
                    }

                    publishProgress((int) (((j) / (float) listSize) * 100));

                }

            }

            return downloadedImages;
        }

        protected void onProgressUpdate(Integer...integers){
            dialog.setProgress(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Bitmap> resultImages){

            dialog.dismiss();

            for(int i=0;i<resultImages.size();i++) {
                Bitmap bitmap = resultImages.get(i);
                saveImageToFolder(bitmap, i);
            }

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

        protected void saveImageToFolder(Bitmap bitmap, int index){

            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File file = Environment.getExternalStorageDirectory();
            file = new File(file, "Image" + index + ".jpg");

            try {
                OutputStream stream = null;
                stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
                stream.flush();
                stream.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }

            imagesUri.add(Uri.parse(file.getAbsolutePath()));
            Log.i("Absolute Path", file.getAbsolutePath());
        }

    }

}
