package com.example.marvelguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieSelectorActivity extends Activity {
    public static final String EXTRA_PHASEID = "phaseId";

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selector);

        int phaseId = (Integer) getIntent().getExtras().get(EXTRA_PHASEID);

        ListView listMovies = findViewById(R.id.list_movies);

        try{
            SQLiteOpenHelper marvelDatabaseHelper = new MarvelDatabaseHelper(this);
            db = marvelDatabaseHelper.getReadableDatabase();
            cursor = db.query("MOVIE",
                    new String[]{"_id","NAME"},
                    "PHASE="+phaseId,//null,
                    null, null, null, null);

            ArrayList<String> movies = new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                movies.add(cursor.getString(1)); // index 1 = NAME
                cursor.moveToNext();
            }
            listMovies.setAdapter(new ArrayAdapter<String>(this, R.layout.list_items, R.id.list_content, movies));
        } catch (SQLiteException e){
            System.out.println(e);
            Toast toast = Toast.makeText(this, "Movie Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //Create the listener
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listDrinks, View view, int position, long id) {
                //Pass the drink the user clicks on to DrinkActivity
                Intent intent = new Intent(MovieSelectorActivity.this, MovieActivity.class);
                switch (phaseId){
                    case 4: id += 9;
                    case 3: id += 6;
                    case 2: id += 6;
                    default:
                        break;
                }
                intent.putExtra(MovieActivity.EXTRA_MOVIEID, (int) id + 1);
                startActivity(intent);
            }
        };
        //Assign the listener to the list view
        listMovies.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}