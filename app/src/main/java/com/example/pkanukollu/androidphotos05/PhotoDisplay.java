package com.example.pkanukollu.androidphotos05;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoDisplay extends AppCompatActivity {
    ArrayList<String> paths;
    String pic;
    int index;
    Button next;
    Button previous;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        Bundle b = getIntent().getExtras();
        paths = b.getStringArrayList("paths");
        index = paths.indexOf(pic);
        pic = b.getString("path");
        imageView = (ImageView) findViewById(R.id.image);
        Bitmap myBitmap = BitmapFactory.decodeFile(pic);
        imageView.setImageBitmap(myBitmap);
        //next = (Button) findViewById(R.id.button2);
        //previous = (Button) findViewById(R.id.button);
        /*next.setOnClickListener(e -> {
            if (index == paths.size() - 1) {
                index = 0;
            } else {
                index++;
            }
            String nextPic = paths.get(index);
            Intent intent = new Intent(getApplicationContext(), PhotoDisplay.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("paths", paths);
            bundle.putString("path", nextPic);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        previous.setOnClickListener(e -> {
            if (index == 0) {
                index = paths.size() - 1;
            } else {
                index--;
            }
            String previousPic = paths.get(index);
            Intent intent = new Intent(getApplicationContext(), PhotoDisplay.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("paths", paths);
            bundle.putString("path", previousPic);
            intent.putExtras(bundle);
            startActivity(intent);
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.right:
                index++;
                if(index >= 0 && index < paths.size()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(paths.get(index));
                    imageView.setImageBitmap(myBitmap);
                }else{
                    index--;
                }
                return true;
            case R.id.left:
                index--;
                if(index >= 0 && index < paths.size()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(paths.get(index));
                    imageView.setImageBitmap(myBitmap);
                }else{
                    index++;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}