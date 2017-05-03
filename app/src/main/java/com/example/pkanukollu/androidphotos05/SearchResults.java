package com.example.pkanukollu.androidphotos05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Nishanth on 5/2/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

    public class SearchResults extends AppCompatActivity {
        ArrayList<String> searchPaths;
        GridView grid;
        ArrayAdapter<ImageView> adapter;
        private Integer mThumbIds[];
        private ArrayList<ImageView> images;
        private SearchResults.ImageAdapter myImgAdapter;
        int i;
        private String selected;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_results);
            Bundle b = getIntent().getExtras();
            searchPaths = b.getStringArrayList("search_results");
            grid = (GridView)findViewById(R.id.grid2);
            mThumbIds = new Integer[2];
            myImgAdapter = new SearchResults.ImageAdapter(this);
            images = new ArrayList<ImageView>();
            grid.setAdapter(myImgAdapter);
        }

        public class ImageAdapter extends BaseAdapter {
            private Context mContext;

            public ImageAdapter(Context c) {
                mContext = c;
            }

            public int getCount() {
                //return mThumbIds.length;
                return searchPaths.size();
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            // create a new ImageView for each item referenced by the Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView == null) {  // if it's not recycled, initialize some attributes
                    imageView = new ImageView(mContext);
                    //imageView = images.get(position);
                    imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(8, 8, 8, 8);
                } else {
                    imageView = (ImageView) convertView;
                    // imageView = images.get(position);
                }

                //Bitmap myBitmap = BitmapFactory.decodeFile(imgFiles.get(position).getAbsolutePath());
                //imageView.setImageBitmap(myBitmap);
                //imageView.setImageResource(mThumbIds[position]);
                //imageView.setImageResource(images.get(position).getDrawable().getAlpha());
                //imageView.setImageURI(uris.get(position));
                //  Log.d("myTag", position + " " + paths.get(position));
                //File f = new File(paths.get(position));
                //if(f.exists()) {
                //Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20170501_130704.jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(searchPaths.get(position));
                if(myBitmap == null)
                    Log.d("is", "yes");
                imageView.setImageBitmap(myBitmap);
                //}
                return imageView;
            }

       /* public Integer getImgID(int position){
            //return mThumbIds[position];
            //return images.get(position).getId();
            return images.get(position).getDrawable().getAlpha();
        }*/

            // references to our images
            //private Integer[] mThumbIds = {
                /*R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7*/
            //};
        }
    }

