package com.example.pkanukollu.androidphotos05;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
/**
 * Nishanth Athreya nsa48
 * Pranav Kanukollu, pvk9
 */
public class InsideAlbumScreen extends AppCompatActivity {
    Button addPhoto;
    GridView grid;
    ArrayList<String> searchPhotos = new ArrayList<String>();
    ArrayAdapter<ImageView> adapter;
    private ArrayList<ImageView> images;
    private ArrayList<File> imgFiles;
    private Integer mThumbIds[];
    private String selected;
    private ImageAdapter myImgAdapter;
    private UserAlbum u;
    //private ImageView test;
    private ArrayList<Uri> uris;
    private ArrayList<String> paths;
    private String album_name;
    private int index;
    int i;
    int MY_PERMISSIONS_REQUEST_CAMERA;
    int MY_PERMISSIONS_CAMERA;
    int MY_PERMISSIONS_REQUEST_WRITE;
    final int THUMBSIZE = 64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_album_screen);
        Bundle b = getIntent().getExtras();
        album_name = b.getString("selected album");
        ArrayList<String> theAlbums = b.getStringArrayList("albums");
        grid = (GridView)findViewById(R.id.grid);
        index = -1;
        //test = (ImageView)findViewById(R.id.test);
        u = openUserAlbum();
        LinkedHashMap<String, String> albumRequests = openRequests();
        Iterator<String> itr = albumRequests.keySet().iterator();
        while(itr.hasNext()){
            String oldAlbum = itr.next();
            if(u.getAlbumMap().containsKey(oldAlbum)){
                u.renameAlbum(oldAlbum, albumRequests.get(oldAlbum));
            }
            Log.d("album", oldAlbum);
        }
        if(!u.getAlbumMap().containsKey(album_name)){
            u.addAlbum(album_name);
        }
        Iterator<String> itr2 = u.getAlbumMap().keySet().iterator();
        while(itr.hasNext()){
            String s = itr.next();
            if(!theAlbums.contains(s)){
                u.getAlbumMap().remove(s);
            }
        }
        for(int i = 0;i < theAlbums.size();i++){
            if(!u.getAlbumMap().containsKey(theAlbums.get(i)))
                u.addAlbum(theAlbums.get(i));
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

// Should we show an explanation?
if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                     Manifest.permission.READ_EXTERNAL_STORAGE)){

// Show an explanation to the user *asynchronously* -- don't block
 // this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.

} else {

 // No explanation needed, we can request the permission.

 ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
MY_PERMISSIONS_REQUEST_CAMERA);

// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
 // app-defined int constant. The callback method gets the
 // result of the request.
 }
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
        //uris = u.getUris(album_name);
        paths = u.getPaths(album_name);
        grid.setAdapter(myImgAdapter);
        //adapter = new ArrayAdapter<ImageView>(this, R.layout.activity_inside_album_screen, imgs);
        //grid.setAdapter(adapter);
        i = 0;
        albumRequests = new LinkedHashMap<String, String>();
        saveQueue(albumRequests);
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
                    File f = new File(getRealPathFromURI(selectedImage));
                    String path = f.getAbsolutePath();
                    imageView.setImageURI(selectedImage);
                    images.add(imageView);
                    imgFiles.add(new File(selectedImage.getPath()));
                    //File f = new File(selectedImage.getPath());
                    //uris.add(selectedImage);
                    myImgAdapter = new ImageAdapter(this);
                    grid.setAdapter(myImgAdapter);
                    Log.d("path", f.getPath());
                    u.addPic(album_name, path, f.getName());
                    paths = u.getPaths(album_name);
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
                    //selectedImage.
                    File f = new File(getRealPathFromURI(selectedImage));
                    String path = f.getAbsolutePath();
                    Log.d("path", getRealPathFromURI(selectedImage));
                    Log.d("path1", path);
                    imageView.setImageURI(selectedImage);
                    images.add(imageView);
                    imgFiles.add(new File(selectedImage.getPath()));
                    myImgAdapter = new ImageAdapter(this);
                    grid.setAdapter(myImgAdapter);
                    //File f = new File(selectedImage.getPath());
                    u.addPic(album_name,path , f.getName());
                    paths = u.getPaths(album_name);
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

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            //return mThumbIds.length;
            return paths.size();
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
            Log.d("myTag", position + " " + paths.get(position));
            //File f = new File(paths.get(position));
            //if(f.exists()) {
                //Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20170501_130704.jpg");
            Bitmap myBitmap = BitmapFactory.decodeFile(paths.get(position));
            if(myBitmap == null)
                    Log.d("is", "yes");
                imageView.setImageBitmap(myBitmap);
            imageView.setTag(paths.get(position));
            //}
            imageView.setOnClickListener(e->{
                selected = (String)imageView.getTag();
                Toast.makeText(InsideAlbumScreen.this, selected , Toast.LENGTH_LONG).show();
            });
            /*grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),PhotoDisplay.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("paths",paths);
                    // Uri imageSelected = uris.get(position);
                    selected = paths.get(position);
                    bundle.putString("path", selected);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });*/
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
            FileOutputStream fos = this.openFileOutput("newalbums.bin", Context.MODE_PRIVATE);
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
            FileInputStream fis = this.openFileInput("newalbums.bin");
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
    public LinkedHashMap<String, String> openRequests(){
        try{
            FileInputStream fis = this.openFileInput("waiting.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            /*FileInputStream fis = this.openFileInput("albums.bin");
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));*/
            Object o = ois.readObject();
            //Toast.makeText(HomeScreen.this, "useralbum" + o.toString(), Toast.LENGTH_SHORT).show();
            //ois.close();
            //fis.close();
            return (LinkedHashMap<String, String>)o;

        }catch(Exception e) {
            //Toast.makeText(HomeScreen.this, "useralbum" , Toast.LENGTH_SHORT).show();
            return new LinkedHashMap<String, String>();
        }

    }

    public void saveQueue(LinkedHashMap<String, String> albumQueue){
        try{
            FileOutputStream fos = this.openFileOutput("waiting.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /*FileOutputStream fos = this.openFileOutput("albums.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));*/
            oos.writeObject(albumQueue);
            oos.flush();
            oos.close();
            //Toast.makeText(HomeScreen.this, fos. , Toast.LENGTH_LONG).show();
            //fos.close();
        }catch(Exception e){

        }
    }

    public ArrayList<String> openDeleteRequests(){
        try{
            FileInputStream fis = this.openFileInput("deletes.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            /*FileInputStream fis = this.openFileInput("albums.bin");
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));*/
            Object o = ois.readObject();
            //Toast.makeText(HomeScreen.this, "useralbum" + o.toString(), Toast.LENGTH_SHORT).show();
            //ois.close();
            //fis.close();
            return (ArrayList<String>)o;

        }catch(Exception e) {
            //Toast.makeText(HomeScreen.this, "useralbum" , Toast.LENGTH_SHORT).show();
            return new ArrayList<String>();
        }

    }
    public void saveDeletes(ArrayList<String> deletes){
        try{
            FileOutputStream fos = this.openFileOutput("deletes.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /*FileOutputStream fos = this.openFileOutput("albums.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));*/
            oos.writeObject(deletes);
            oos.flush();
            oos.close();
            //Toast.makeText(HomeScreen.this, fos. , Toast.LENGTH_LONG).show();
            //fos.close();
        }catch(Exception e){

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
                //Intent pickPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pickPhoto,1);
                //startActivityForResult(pickPhoto, 0);
                return true;
            case R.id.addloc:
                if(selected != null) {
                    final Dialog add = new Dialog(InsideAlbumScreen.this);
                    add.setContentView(R.layout.addloc);
                    add.setTitle("Add location");
                    add.setCancelable(true);
                    add.show();

                    Button saveButton = (Button) add.findViewById(R.id.confirmLoc);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String loc = ((EditText) add.findViewById(R.id.loc)).getText().toString().trim();
                            //File f = new File(selected);
                            ArrayList<Picture> pics = u.getPics(album_name);
                            for (int i = 0; i < pics.size(); i++) {
                                Picture p = pics.get(i);
                                if (p.getPath().equals(selected)) {
                                    u.getPics(album_name).get(i).setLocation(loc);
                                }
                            }
                            saveUserAlbum(u);
                            add.dismiss();
                        }
                    });

                    Button cancelButton = (Button) add.findViewById(R.id.cancelLoc);
                    cancelButton.setOnClickListener(e -> {
                                add.dismiss();
                            }
                    );
                    Button removeButton = (Button) add.findViewById(R.id.deleteLoc);
                    removeButton.setOnClickListener(e -> {
                                //File f = new File(selected);
                                ArrayList<Picture> pics = u.getPics(album_name);
                                for (int i = 0; i < pics.size(); i++) {
                                    Picture p = pics.get(i);
                                    if (p.getPath().equals(selected)) {
                                        u.getPics(album_name).get(i).setLocation("");
                                    }
                                }
                                saveUserAlbum(u);
                                add.dismiss();
                            }
                    );
                }
                /*if(selected != null) {
                    ArrayList<Picture> pics = u.getPics(album_name);
                    for (int i = 0; i < pics.size(); i++) {
                        Picture p = pics.get(i);
                        if (p.getPath().equals(selected)) {
                            p.setLocation();
                        }
                    }
                }*/
                return true;
            case R.id.addperson:
                if(selected != null) {
                    final Dialog addperson = new Dialog(InsideAlbumScreen.this);
                    addperson.setContentView(R.layout.addtag);
                    addperson.setTitle("Add location");
                    addperson.setCancelable(true);
                    addperson.show();

                    Button saveButton = (Button) addperson.findViewById(R.id.confirmPerson);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String person = ((EditText) addperson.findViewById(R.id.person)).getText().toString().trim();
                            //File f = new File(selected);
                            ArrayList<Picture> pics = u.getPics(album_name);
                            for (int i = 0; i < pics.size(); i++) {
                                Picture p = pics.get(i);
                                if (p.getPath().equals(selected)) {
                                    u.getPics(album_name).get(i).setPerson(person);
                                }
                            }
                            saveUserAlbum(u);
                            addperson.dismiss();
                        }
                    });

                    Button cancelButton = (Button) addperson.findViewById(R.id.cancelPerson);
                    cancelButton.setOnClickListener(e -> {
                                addperson.dismiss();
                            }
                    );
                    Button removeButton = (Button) addperson.findViewById(R.id.deletePerson);
                    removeButton.setOnClickListener(e -> {
                        //File f = new File(selected);
                        ArrayList<Picture> pics = u.getPics(album_name);
                        for (int i = 0; i < pics.size(); i++) {
                            Picture p = pics.get(i);
                            if (p.getPath().equals(selected)) {
                                u.getPics(album_name).get(i).setPerson("");
                            }
                        }
                        saveUserAlbum(u);
                                addperson.dismiss();
                            }
                    );
                }
                return true;
            case R.id.play:
                Intent intent = new Intent(getApplicationContext(),PhotoDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("paths",paths);
                bundle.putString("path", selected);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.action_cut:
                final Dialog dialog = new Dialog(InsideAlbumScreen.this);
                dialog.setContentView(R.layout.move);
                dialog.setTitle("Move album");
                dialog.setCancelable(true);
                dialog.show();

                Button saveButton = (Button)dialog.findViewById(R.id.confirm_move);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String album = ((EditText)dialog.findViewById(R.id.albumTo)).getText().toString().trim().toUpperCase();
                        //File f = new File(selected);
                        if(u.getAlbumMap().containsKey(album)){
                            u.addPic(album,selected, "");
                            u.deletePic(album_name,selected);
                            saveUserAlbum(u);
                        }
                        dialog.dismiss();
                    }
                });

                Button cancelButton = (Button) dialog.findViewById(R.id.cancel_move);
                cancelButton.setOnClickListener(e->{
                    dialog.dismiss();
                        }
                );

                return true;
            case R.id.delete:
                if(selected != null) {
                    u.deletePic(album_name, selected);
                    //myImgAdapter = new ImageAdapter(this);
                    paths = u.getPaths(album_name);
                    myImgAdapter.notifyDataSetChanged();
                    grid.setAdapter(myImgAdapter);
                    saveUserAlbum(u);
                }
                return true;
            case R.id.viewTag:
                if(selected != null){
                    ArrayList<Picture> pics = u.getPics(album_name);
                    String s = "Location: ";
                    for(int i = 0;i < pics.size();i++){
                        if(pics.get(i).getPath().equals(selected) && pics.get(i).getLocation() != null)
                            s += pics.get(i).getLocation() + " ";

                        if(pics.get(i).getPath().equals(selected) && pics.get(i).getPerson() != null) {
                            s += "Person: " + pics.get(i).getPerson();
                        }
                    }
                    Toast.makeText(InsideAlbumScreen.this, s, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(InsideAlbumScreen.this, "no selection", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.searchfor:
                final Dialog search = new Dialog(InsideAlbumScreen.this);
                search.setContentView(R.layout.search);
                search.setTitle("Search album");
                search.setCancelable(true);
                search.show();
                //TextView oldAlbum = (TextView)search.findViewById(R.id.disp) ;
                //oldAlbum.setText("Old Album Name: " + selected);
                Button confirmButton = (Button)search.findViewById(R.id.confirmSearch);
                //Button cancelling = (Button)search.findViewById(R.id.cancelsearch);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String name = ((EditText)search.findViewById(R.id.personName)).getText().toString().trim().toUpperCase();
                        String location = ((EditText)search.findViewById(R.id.location)).getText().toString().trim().toUpperCase();
                        //String name = p.getText().toString();
                        //Toast.makeText(HomeScreen.this, name, Toast.LENGTH_LONG);

                        //String location = locSearch.getText().toString();
                        if (!(name.equals("Enter Name to Search")))     //searching by name
                        {
                            // Toast.makeText(HomeScreen.this, "Name", Toast.LENGTH_LONG).;
                            String[] albums = u.getAlbums();

                            for (int i = 0; i< albums.length;i++)
                            {
                                ArrayList<Picture> pics = u.getPics(albums[i]);
                                for (int j = 0; j<pics.size();j++)
                                {
                                    if (pics.get(j).getPerson() != null && pics.get(j).getPerson().toLowerCase().contains(name.toLowerCase()))
                                    {
                                        searchPhotos.add(pics.get(j).getPath());
                                    }
                                }
                            }
                            //Intent intent = new Intent(this, SearchResults.class);
                            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("search_results", searchPhotos);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        if (!(location.equals("Enter Location to Search"))) //searching by location
                        {
                            //Toast.makeText(HomeScreen.this, "Location", Toast.LENGTH_LONG);
                            String[] albums = u.getAlbums();
                            for (int i = 0; i< albums.length;i++)
                            {
                                ArrayList<Picture> pics = u.getPics(albums[i]);
                                for (int j = 0; j<pics.size();j++)
                                {
                                    if (pics.get(j).getLocation() != null && pics.get(j).getLocation().toLowerCase().contains(location.toLowerCase()))
                                    {
                                        searchPhotos.add(pics.get(j).getPath());
                                    }
                                }
                            }
                            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("search_results", searchPhotos);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else {
                            Toast.makeText(InsideAlbumScreen.this, "Invalid search constraints!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Button cancelBtn = (Button)search.findViewById(R.id.cancelSearch);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        search.dismiss();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
