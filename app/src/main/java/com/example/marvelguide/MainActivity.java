package com.example.marvelguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // debug database
        try{
            SQLiteOpenHelper marvelDatabaseHelper = new MarvelDatabaseHelper(this);
            db = marvelDatabaseHelper.getReadableDatabase();
            cursor = db.query("MOVIE",
                    new String[]{"_id","NAME","CHRONOLOGICAL"},
                    null,//null,
                    null, null, null, null);
            Log.d("","****IntroActivity onCreate****" + DatabaseUtils.dumpCursorToString(cursor));
        } catch (SQLiteException e){
            System.out.println(e);
            Toast toast = Toast.makeText(this, "Movie Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        // debug database

        Button startButton = findViewById(R.id.button_intro);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhaseSelectorActivity.class);
                startActivity(intent);
            }
        });

        // recommend random movie to watch
        Button randomButton = findViewById(R.id.button_random);

        randomButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                intent.putExtra(MovieActivity.EXTRA_MOVIEID, new Random().nextInt(28));
                startActivity(intent);
            }
        });

        Button mapButton = findViewById(R.id.button_map);

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onMapNear(v);
            }
        });
    }
    public void onMapNear(View view){
        /*Desplegar en google maps*/
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=cine");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onMap(View view){
        TextView gymName= (TextView)findViewById(R.id.name);
        TextView dir= (TextView)findViewById(R.id.description);
        String address = gymName.getText().toString();
        address = "geo:0,0?q=" + address.concat(" "+dir.getText().toString());
        /*Desplegar en google maps*/
        Uri gmmIntentUri = Uri.parse(address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}