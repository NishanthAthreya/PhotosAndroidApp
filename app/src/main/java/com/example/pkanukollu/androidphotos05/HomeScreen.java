package com.example.pkanukollu.androidphotos05;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HomeScreen extends AppCompatActivity {
    Button create;
    Button delete;
    Button view;
    EditText albumname;
    ListView albumsList;
    ArrayList<String> useralbums;
    //ArrayList<String> albums = new ArrayList<String>();
    //UserAlbum u;
    ArrayAdapter<String> adapter;
    private String selected;
    public static final String selected_item = "selected album";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        albumname = (EditText) findViewById(R.id.editText);
        create = (Button)findViewById(R.id.button11);
        delete = (Button)findViewById(R.id.button9);
        view = (Button)findViewById(R.id.button12);
        albumsList = (ListView)findViewById(R.id.albumList);
       // useralbums = new ArrayList<String>();
        useralbums = openAlbums();
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
        create.setOnClickListener(e -> {

            String album = albumname.getText().toString();
            //albums.add(album);
            if(!useralbums.contains(album)){
                //u.addAlbum(album);
                useralbums.add(album);
                saveAlbums(useralbums);
                //adapter = new ArrayAdapter<String>(this,R.layout.albums, albums.toArray(new String[albums.size()]));
                /*ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this,
                                R.layout.activity_home_screen,
                                u.getAlbums());*/
                //String s[] = {"thing1", "thing2"};
                String[] al = useralbums.toArray(new String[useralbums.size()]);
                adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
                albumsList.setAdapter(adapter);
            }else{
                Toast.makeText(HomeScreen.this, "Album already exists!", Toast.LENGTH_SHORT).show();
            }
        } );

        delete.setOnClickListener(e -> {
            String album = albumname.getText().toString();
            //albums.remove(album);
            //u.deleteAlbum(album);
            useralbums.remove(album);
            saveAlbums(useralbums);
            String[] al = useralbums.toArray(new String[useralbums.size()]);
            adapter = new ArrayAdapter<String>(this,R.layout.albums, al);
            albumsList.setAdapter(adapter);
        });
        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {       //getting selected album
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) albumsList.getItemAtPosition(position);
                Toast.makeText(HomeScreen.this, "got selected: " + selected, Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(e -> {              //going to selected album screen
            if(useralbums.contains(selected)) {
                Bundle bundle = new Bundle();
                bundle.putString(selected_item, selected);
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
}
