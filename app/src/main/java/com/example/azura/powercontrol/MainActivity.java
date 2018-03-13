package com.example.azura.powercontrol;

import android.app.LoaderManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton mInfoButton = findViewById(R.id.info);
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Pop.class));
            }
        });

        // Get the list of components
        ArrayList<Component> components = new ArrayList<>();

        components.add(new Component(0,"Hall", Boolean.TRUE));
        components.add(new Component(1,"Room 1",Boolean.FALSE));
        components.add(new Component(0,"Hall",Boolean.TRUE));
        components.add(new Component(1,"Hall", Boolean.FALSE));
        components.add(new Component(0,"Room 2", Boolean.TRUE));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new adapter that takes the list of components as input
        final CustomAdapter adapter = new CustomAdapter(this, components);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }

    public void logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "You have been successfully signed out", Toast.LENGTH_LONG).show();
    }
}
