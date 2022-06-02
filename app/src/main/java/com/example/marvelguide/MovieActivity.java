package com.example.marvelguide;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieActivity extends Activity {
    public static final String EXTRA_MOVIEID = "movieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //send whatsapp
        Button submit = findViewById(R.id.share);

        //Get the drink from the intent
        int movieId = (Integer) getIntent().getExtras().get(EXTRA_MOVIEID);
        Log.d("","****movieId in MovieActivity = "+movieId+"****");

        //Create a cursor
        SQLiteOpenHelper marvelDatabaseHelper = new MarvelDatabaseHelper(this);
        try{
            SQLiteDatabase db = marvelDatabaseHelper.getReadableDatabase();

            Cursor cursor = db.query("MOVIE", new String[] {"NAME", "DESCRIPTION", "_CAST", "YEAR", "CHRONOLOGICAL", "PHASE", "IMAGE_RESOURCE_ID", "WATCHED", "DATE_WATCHED"},
                    "_id = "+movieId,
                    null,//new String[] {Integer.toString(movieId)},
                    null, null, null);

            if(cursor.moveToFirst()){
                //Get the movie details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                String castText = cursor.getString(2);
                int yearText = cursor.getInt(3);
                int orderText = cursor.getInt(4);
                int phaseText = cursor.getInt(5);
                int photoId = cursor.getInt(6);
                boolean isWatched = (cursor.getInt(7) == 1);
                String dateText = cursor.getString(8);

                //Populate the movie name
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //Populate the movie description
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate the movie cast
                TextView cast = (TextView) findViewById(R.id.cast);
                cast.setText("Cast:" + castText);

                //Populate the movie cast
                TextView year = (TextView) findViewById(R.id.year);
                year.setText("Release: " + yearText);

                //Populate the movie cast
                TextView order = (TextView) findViewById(R.id.order);
                order.setText("Chronological Order: " + orderText);

                //Populate the movie cast
                TextView phase = (TextView) findViewById(R.id.phase);
                phase.setText("Phase: " + phaseText);

                //Populate the movie image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                // Populate the watched checkbox
                CheckBox favorite = (CheckBox)findViewById(R.id.watched);
                favorite.setChecked(isWatched);

                //Populate the watched date
                TextView date = (TextView) findViewById(R.id.watched_date);
                if(isWatched) date.setText(dateText);
                else date.setText("");

                submit.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                // Getting the text
                                // from edit text
                                String message = "I just watched " + nameText;

                                // Calling the function
                                // to send message
                                sendMessage(message);
                            }
                        });

            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Update the database when the checkbox is clicked
    public void onWatchedClicked(View view){
        int movieId = (Integer) getIntent().getExtras().get(EXTRA_MOVIEID);
        new UpdateMovieTask().execute(movieId);
    }
    //Inner class to update the movie watched
    private class UpdateMovieTask extends AsyncTask<Integer, Void, Boolean> {
        private ContentValues movieValues;
        private String today;

        protected void onPreExecute() {
            CheckBox watched = findViewById(R.id.watched);
            movieValues = new ContentValues();
            movieValues.put("WATCHED", watched.isChecked());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            today = sdf.format(new Date());
            movieValues.put("DATE_WATCHED", today);
        }

        protected Boolean doInBackground(Integer...movies) {
            int movieId = movies[0];
            SQLiteOpenHelper marvelDatabaseHelper = new MarvelDatabaseHelper(MovieActivity.this);
            try {
                SQLiteDatabase db = marvelDatabaseHelper.getWritableDatabase();
                db.update("MOVIE", movieValues,
                        "_id = ?", new String[] {Integer.toString(movieId)});
                db.update("MOVIE", movieValues,
                        "_id = ?", new String[] {Integer.toString(movieId)});
                db.close();
                return true;
            } catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if (!success) {
                Toast toast = Toast.makeText(MovieActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                TextView date = (TextView) findViewById(R.id.watched_date);
                date.setText(today);
            }
        }
    }

    public void sendMessage(String message){
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }



}