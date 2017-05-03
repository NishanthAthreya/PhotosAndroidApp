package com.example.pkanukollu.androidphotos05;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Queue;

/**
 * Nishanth Athreya nsa48
 * Pranav Kanukollu, pvk9
 */
public class HomeScreen extends AppCompatActivity {
    //Button create;
    //Button delete;
    Button view;
    EditText albumname;
    ArrayList<String> searchPhotos = new ArrayList<String>();
    ListView albumsList;
    private UserAlbum u;
    ArrayList<String> useralbums;
    LinkedHashMap<String, String> renameRequests;
    ArrayList<String> deleteRequests;
    int MY_PERMISSIONS_CAMERA;
    //ArrayList<String> albums = new ArrayList<String>();
    //UserAlbum u;
    ArrayAdapter<String> adapter;
    private String selected;
    public static final String selected_item = "selected album";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //albumname = (EditText) findViewById(R.id.editText);
        //create = (Button)findViewById(R.id.button11);
        //delete = (Button)findViewById(R.id.button9);
        view = (Button)findViewById(R.id.button12);
        albumsList = (ListView)findViewById(R.id.albumList);
        renameRequests = openRequests();
       // useralbums = new ArrayList<String>();
        useralbums = openAlbums();
        u = openUserAlbum();
        //useralbums.add("thing");
        //u = openUserAlbum();
        String s[] = {"thing1", "thing2"};

        //adapter = new ArrayAdapter<String>(this,R.layout.albums, s);
        String[] a = useralbums.toArray(new String[useralbums.size()]);

        adapter = new ArrayAdapter<String>(this,R.layout.albums, a);
        albumsList.setAdapter(adapter);

       // Toast toast = Toast.makeText(this,"clicked create",Toast.LENGTH_SHORT);
        //toast.show();
        //System.out.println("created");
        /*create.setOnClickListener(e -> {

            String album = albumname.getText().toString();
            //albums.add(album);
            if(!useralbums.contains(album)){
                //u.addAlbum(album);
                useralbums.add(album);
                saveAlbums(useralbums);*/
                //adapter = new ArrayAdapter<String>(this,R.layout.albums, albums.toArray(new String[albums.size()]));
                /*ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this,
                                R.layout.activity_home_screen,
                                u.getAlbums());*/
                //String s[] = {"thing1", "thing2"};
               /* String[] al = useralbums.toArray(new String[useralbums.size()]);
                adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
                albumsList.setAdapter(adapter);
            }else{
                Toast.makeText(HomeScreen.this, "Album already exists!", Toast.LENGTH_SHORT).show();
            }
        } );*/

       /* delete.setOnClickListener(e -> {
            String album = albumname.getText().toString();*/
            //albums.remove(album);
            //u.deleteAlbum(album);
            /*useralbums.remove(album);
            saveAlbums(useralbums);
            String[] al = useralbums.toArray(new String[useralbums.size()]);
            adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
            albumsList.setAdapter(adapter);
        });*/
        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {       //getting selected album
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < albumsList.getChildCount(); i++)
                {
                    if (position == i)
                    {
                        albumsList.getChildAt(i).setBackgroundColor(Color.BLUE);
                    }
                    else{
                        albumsList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                selected = (String) albumsList.getItemAtPosition(position);
                Toast.makeText(HomeScreen.this, "got selected: " + selected, Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(e -> {              //going to selected album screen
            if(useralbums.contains(selected)) {
                Bundle bundle = new Bundle();
                bundle.putString(selected_item, selected);
                bundle.putStringArrayList("albums",useralbums);
                //bundle.put
                Intent intent = new Intent(this, InsideAlbumScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void saveAlbums(ArrayList<String> albums){
        try{
            FileOutputStream fos = this.openFileOutput("useralbums.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /*FileOutputStream fos = this.openFileOutput("albums.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));*/
            oos.writeObject(albums);
            oos.flush();
            oos.close();
            //Toast.makeText(HomeScreen.this, fos. , Toast.LENGTH_LONG).show();
            //fos.close();
        }catch(Exception e){

        }
    }
    public ArrayList<String> openAlbums(){
        try{
            FileInputStream fis = this.openFileInput("useralbums.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            /*FileInputStream fis = this.openFileInput("albums.bin");
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));*/
            Object o = ois.readObject();
            //Toast.makeText(HomeScreen.this, "useralbum" + o.toString(), Toast.LENGTH_SHORT).show();
            //ois.close();
            //fis.close();
            return (ArrayList<String>)o;

        }catch(Exception e) {
            Toast.makeText(HomeScreen.this, "useralbum" , Toast.LENGTH_SHORT).show();
            return new ArrayList<String>();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addAlbum:
                final Dialog dialog = new Dialog(HomeScreen.this);
                dialog.setContentView(R.layout.create);
                dialog.setTitle("Add album");
                dialog.setCancelable(true);
                dialog.show();

                Button saveButton = (Button)dialog.findViewById(R.id.dialog_ok);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String album = ((EditText)dialog.findViewById(R.id.albumName)).getText().toString().trim().toUpperCase();
                        if(!useralbums.contains(album)){
                            //String album = albumname.getText().toString();
                            useralbums.add(album);
                            saveAlbums(useralbums);
                            String[] al = useralbums.toArray(new String[useralbums.size()]);
                            adapter = new ArrayAdapter<String>(HomeScreen.this,R.layout.albums, al);
                            albumsList.setAdapter(adapter);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(HomeScreen.this, "Album already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button cancelButton = (Button)dialog.findViewById(R.id.dialog_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
                /*if(!useralbums.contains(selected)){
                    String album = albumname.getText().toString();
                    useralbums.add(album);
                    saveAlbums(useralbums);
                    String[] al = useralbums.toArray(new String[useralbums.size()]);
                    adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
                    albumsList.setAdapter(adapter);
                }else{
                    Toast.makeText(HomeScreen.this, "Album already exists!", Toast.LENGTH_SHORT).show();
                }*/
                return true;
            case R.id.removeAlbum:
                useralbums.remove(selected);
                saveAlbums(useralbums);
                String[] al = useralbums.toArray(new String[useralbums.size()]);
                adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
                albumsList.setAdapter(adapter);
                //deleteRequests.add(selected);
                return true;
            case R.id.editAlbum:
                if (selected != null) {
                    final Dialog rename = new Dialog(HomeScreen.this);
                    rename.setContentView(R.layout.rename);
                    rename.setTitle("Rename album");
                    rename.setCancelable(true);
                    rename.show();
                    TextView oldAlbum = (TextView)rename.findViewById(R.id.oldAlbum) ;
                    oldAlbum.setText("Old Album Name: " + selected);
                    Button confirmButton = (Button)rename.findViewById(R.id.confirm);
                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String album = ((EditText)rename.findViewById(R.id.newAlbum)).getText().toString().trim().toUpperCase();
                            if(!useralbums.contains(album)){
                                //String album = albumname.getText().toString();
                                int i = useralbums.indexOf(selected);
                                useralbums.set(i, album);
                                saveAlbums(useralbums);
                                String[] al = useralbums.toArray(new String[useralbums.size()]);
                                adapter = new ArrayAdapter<String>(HomeScreen.this,R.layout.albums, al);
                                albumsList.setAdapter(adapter);
                                renameRequests.put(selected, album);
                                saveQueue(renameRequests);
                                rename.dismiss();
                            }else{
                                Toast.makeText(HomeScreen.this, "Album already exists!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Button cancelBtn = (Button)rename.findViewById(R.id.cancel);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            rename.dismiss();
                        }
                    });
                }
                return true;

            case R.id.search:
                final Dialog search = new Dialog(HomeScreen.this);
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
                                    if (pics.get(i).getPerson().contains(name))
                                    {
                                        searchPhotos.add(pics.get(i).getPath());
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
                        else if (!(location.equals("Enter Location to Search"))) //searching by location
                        {
                            //Toast.makeText(HomeScreen.this, "Location", Toast.LENGTH_LONG);
                            String[] albums = u.getAlbums();
                            for (int i = 0; i< albums.length;i++)
                            {
                                ArrayList<Picture> pics = u.getPics(albums[i]);
                                for (int j = 0; j<pics.size();j++)
                                {
                                    if (pics.get(j).getLocation().contains(location))
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
                            Toast.makeText(HomeScreen.this, "Invalid search constraints!", Toast.LENGTH_LONG).show();
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
    public UserAlbum openUserAlbum(){
        try{
            FileInputStream fis = this.openFileInput("savedalbums.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            /*FileInputStream fis = this.openFileInput("thealbum.bin");
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));*/
            Object o = ois.readObject();
            //Toast.makeText(HomeScreen.this, "useralbum" + o.toString(), Toast.LENGTH_SHORT).show();
            //ois.close();
            //fis.close();
            return (UserAlbum)o;

        }catch(Exception e) {
            Toast.makeText(HomeScreen.this, "useralbum" , Toast.LENGTH_SHORT).show();
            return new UserAlbum();
        }

    }
}
