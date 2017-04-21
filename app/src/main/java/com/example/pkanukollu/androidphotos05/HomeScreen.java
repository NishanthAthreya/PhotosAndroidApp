package com.example.pkanukollu.androidphotos05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    Button create;
    TextView albumname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        albumname = (TextView)findViewById(R.id.textView2);
        create = (Button)findViewById(R.id.button9);
        create.setOnClickListener(e -> {
            String album = albumname.getText().toString();
          //  albumname.setText(album);
        } );
    }

}
