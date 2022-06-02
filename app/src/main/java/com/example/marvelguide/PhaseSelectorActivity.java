package com.example.marvelguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PhaseSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase_selector);

        setupOptionsListView();
    }

    private void setupOptionsListView() {
        AdapterView.OnItemClickListener itemClickListener = (listView, itemView, position, id) -> {
            Intent intent = new Intent(PhaseSelectorActivity.this, MovieSelectorActivity.class);
            intent.putExtra(MovieSelectorActivity.EXTRA_PHASEID, (int) id+1); //starts in
            startActivity(intent);
        };
        //Add the listener to the list view
        ListView listView = findViewById(R.id.list_options);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.list_items, R.id.list_content, new String[]{"Phase 1", "Phase 2", "Phase 3", "Phase 4"}));
        listView.setOnItemClickListener(itemClickListener);
    }
}