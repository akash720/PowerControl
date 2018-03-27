package com.example.azura.powercontrol;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Find a reference to the {@link ListView} in the layout
    private ListView componentListView;
    public char[] charSet;
    public boolean smsState1;
    public boolean smsState2;
    private ImageButton signOut;
    private ImageButton emergencyButton;
    private AnimationDrawable animation;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String myNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        myNumber = null;

        charSet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};

        smsState1 = Boolean.TRUE;
        smsState2 = Boolean.TRUE;
        emergencyButton = findViewById(R.id.emergencyImage);
        signOut = findViewById(R.id.sign_out);
        componentListView = findViewById(R.id.list);
        ImageButton mInfoButton = findViewById(R.id.info);

        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Pop.class));
            }
        });

        // Get the list of components
        ArrayList<Component> components = new ArrayList<>();

        components.add(new Component(0, "Hall", Boolean.TRUE));
        components.add(new Component(1, "Room 1", Boolean.FALSE));
        components.add(new Component(0, "Hall", Boolean.FALSE));
        components.add(new Component(1, "Hall", Boolean.TRUE));
//        components.add(new Component(0, "Room 2", Boolean.TRUE));

        // Create a new adapter that takes the list of components as input
        final CustomAdapter adapter = new CustomAdapter(this, components);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        componentListView.setAdapter(adapter);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You have been successfully signed out", Toast.LENGTH_LONG).show();
                signOut();
            }
        });


    }
    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void setNumber(View view){
        myNumber = "7014055788";
        Toast.makeText(getApplicationContext(), "You found it!", Toast.LENGTH_SHORT).show();
    }

    public void emergency(View view) {
        if (emergencyButton.isClickable() == Boolean.TRUE) {
            int n;
            for (n = 0; n <= componentListView.getAdapter().getCount() - 1; n++) {
                int firstPosition = componentListView.getFirstVisiblePosition() - componentListView.getHeaderViewsCount(); // This is the same as child #0
                int wantedChild = n - firstPosition;
                // Say, first visible position is 8, you want position 10(n), wantedChild will now be 2
                // So that means your view is child #2 in the ViewGroup

                // Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
                View wantedView = componentListView.getChildAt(wantedChild);
                ToggleButton mToggle1 = wantedView.findViewById(R.id.toggle);
                mToggle1.setChecked(Boolean.FALSE);

                // TODO: Set your SIM module's number in phoneNo variable

                    String phoneNo = myNumber;
                String message = "#" + charSet[n] + "0";

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    smsSent();
                } catch (Exception e) {
                    smsNotSent();
                    e.printStackTrace();

                }
            }
            emergencyButton.setClickable(Boolean.FALSE);
        }
    }

    public void smsSent() {
        if (smsState1 == Boolean.TRUE) {
            Toast.makeText(getApplicationContext(),
                    "All appliances are OFF now",
                    Toast.LENGTH_LONG).show();
        }
        smsState1 = Boolean.FALSE;
    }

    public void smsNotSent() {
        if (smsState2 == Boolean.TRUE) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please check permissions and try again.",
                    Toast.LENGTH_LONG).show();
        }
        smsState2 = Boolean.FALSE;
    }
}