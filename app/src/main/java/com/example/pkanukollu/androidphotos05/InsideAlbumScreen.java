package com.example.pkanukollu.androidphotos05;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InsideAlbumScreen extends AppCompatActivity {
    Button addPhoto;
    GridView grid;
    ArrayAdapter<ImageView> adapter;
    private ArrayList<ImageView> images;
    private ArrayList<File> imgFiles;
    private Integer mThumbIds[];
    private ImageAdapter myImgAdapter;
    private UserAlbum u;
    //private ImageView test;
    private ArrayList<Uri> uris;
    private String album_name;
    int i;
    final int THUMBSIZE = 64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_album_screen);
        Bundle b = getIntent().getExtras();
        album_name = b.getString("selected album");
        grid = (GridView)findViewById(R.id.grid);
        //test = (ImageView)findViewById(R.id.test);
        u = openUserAlbum();
        if(!u.getAlbumMap().containsKey(album_name)){
            u.addAlbum(album_name);
        }
        /*String s = "";
        for(int i = 0;i < u.getAlbums().length;i++){
            s += s + " " + u.getAlbums()[i];
        }*/
        //Toast.makeText(InsideAlbumScreen.this, s , Toast.LENGTH_LONG).show();
        mThumbIds = new Integer[2];
        myImgAdapter = new ImageAdapter(this);
        images = new ArrayList<ImageView>();
        imgFiles = new ArrayList<File>();
        //uris = new ArrayList<Uri>();
        uris = u.getUris(album_name);
        grid.setAdapter(myImgAdapter);
        //adapter = new ArrayAdapter<ImageView>(this, R.layout.activity_inside_album_screen, imgs);
        //grid.setAdapter(adapter);
        i = 0;
        /*addPhoto = (Button) findViewById(R.id.button);
        addPhoto.setOnClickListener(e -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto,1);
        });*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        ImageView imageView = new ImageView(this);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                    images.add(imageView);
                    imgFiles.add(new File(selectedImage.getPath()));
                    File f = new File(selectedImage.getPath());
                    //uris.add(selectedImage);
                    grid.setAdapter(myImgAdapter);
                    u.addPic(album_name, selectedImage, f.getName());
                    uris = u.getUris(album_name);
                    String s = "";
                    for(int i = 0;i < u.getAlbums().length;i++){
                        s += s + " " + u.getAlbums()[i];
                    }
                    Toast.makeText(InsideAlbumScreen.this, s , Toast.LENGTH_LONG).show();
                    saveUserAlbum(u);
                    Toast.makeText(InsideAlbumScreen.this, s , Toast.LENGTH_LONG).show();
                    //test.setImageURI(selectedImage);
                    //imgs[i] = imageView;
                    //adapter = new ArrayAdapter<ImageView>(this, R.layout.activity_inside_album_screen, imgs);
                    //grid.setAdapter(adapter);
                    i++;
                }
                break;
            case 1:
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                    images.add(imageView);
                    imgFiles.add(new File(selectedImage.getPath()));
                    grid.setAdapter(myImgAdapter);
                    File f = new File(selectedImage.getPath());
                    u.addPic(album_name, selectedImage, f.getName());
                    uris = u.getUris(album_name);
                    String s = "";
                    for(int i = 0;i < u.getAlbums().length;i++){
                        s += s + " " + u.getAlbums()[i];
                    }
                    Toast.makeText(InsideAlbumScreen.this, s , Toast.LENGTH_LONG).show();
                    saveUserAlbum(u);
                    Toast.makeText(InsideAlbumScreen.this, s , Toast.LENGTH_LONG).show();
                    //test.setImageURI(selectedImage);
                    //uris.add(selectedImage);
                   /// for(int i = 0;i < 2;i++){
                      //  File f = new File(images.)
                       // mThumbIds[i] = images.get(i).getDrawable().getAlpha();
                    //}
                    //File f = new File(selectedImage.getPath()).g
                    //imgs[i] = imageView;
                    //adapter = new ArrayAdapter<ImageView>(this, R.layout.activity_inside_album_screen, imgs);
                    //grid.setAdapter(adapter);
                    i++;
                }
                break;
        }
    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            //return mThumbIds.length;
            return uris.size();
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
            imageView.setImageURI(uris.get(position));
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
    public void saveUserAlbum(UserAlbum userAlbum){
        try{
            FileOutputStream fos = this.openFileOutput("thealbum.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /*FileOutputStream fos = this.openFileOutput("thealbum.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));*/
            oos.writeObject(userAlbum);
            oos.flush();
            oos.close();
            //fos.close();
        }catch(Exception e){
            Toast.makeText(InsideAlbumScreen.this, "error" , Toast.LENGTH_LONG).show();
        }
    }
    public UserAlbum openUserAlbum(){
        try{
            FileInputStream fis = this.openFileInput("thealbum.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            /*FileInputStream fis = this.openFileInput("thealbum.bin");
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));*/
            Object o = ois.readObject();
            //Toast.makeText(HomeScreen.this, "useralbum" + o.toString(), Toast.LENGTH_SHORT).show();
            //ois.close();
            //fis.close();
            return (UserAlbum)o;

        }catch(Exception e) {
            Toast.makeText(InsideAlbumScreen.this, "useralbum" , Toast.LENGTH_SHORT).show();
            return new UserAlbum();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto,1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
