package com.example.pkanukollu.androidphotos05;

import android.content.Intent;
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

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    Button create;
    Button delete;
    Button view;
    EditText albumname;
    ListView albumsList;
    ArrayList<String> albums = new ArrayList<String>();
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
       // Toast toast = Toast.makeText(this,"clicked create",Toast.LENGTH_SHORT);
        //toast.show();
        //System.out.println("created");
        create.setOnClickListener(e -> {

            String album = albumname.getText().toString();
            albums.add(album);
            adapter = new ArrayAdapter<String>(this,R.layout.activity_home_screen,albums);
            albumsList.setAdapter(adapter);
        } );

        delete.setOnClickListener(e -> {
            String album = albumname.getText().toString();
            albums.remove(album);
            adapter = new ArrayAdapter<String>(this,R.layout.activity_home_screen,albums);
            albumsList.setAdapter(adapter);
        });
        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {       //getting selected album
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) albumsList.getItemAtPosition(position);
            }
        });
        view.setOnClickListener(e -> {              //going to selected album screen
            Bundle bundle = new Bundle();
            bundle.putString(selected_item,selected);
            Intent intent = new Intent(this, InsideAlbumScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

}
