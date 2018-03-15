package com.example.azura.powercontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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

    // Find a reference to the {@link ListView} in the layout
    private ListView componentListView;
    public char[] charSet;
    public boolean smsState1;
    public boolean smsState2;
    private ImageButton emergencyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        charSet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};

        smsState1 = Boolean.TRUE;
        smsState2 = Boolean.TRUE;
        emergencyButton = findViewById(R.id.emergencyImage);

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
        components.add(new Component(0, "Hall", Boolean.TRUE));
        components.add(new Component(1, "Hall", Boolean.FALSE));
        components.add(new Component(0, "Room 2", Boolean.TRUE));

        // Create a new adapter that takes the list of components as input
        final CustomAdapter adapter = new CustomAdapter(this, components);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        componentListView.setAdapter(adapter);

    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "You have been successfully signed out", Toast.LENGTH_LONG).show();
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
                ToggleButton mToggle = wantedView.findViewById(R.id.toggle);
                mToggle.setChecked(Boolean.FALSE);

                String phoneNo = "7014055788";
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

    public void sendSms(View view) {

        emergencyButton.setClickable(Boolean.TRUE);
        int n;
        for (n = 0; n <= componentListView.getAdapter().getCount() - 1; n++) {
            int firstPosition = componentListView.getFirstVisiblePosition() - componentListView.getHeaderViewsCount(); // This is the same as child #0
            int wantedChild = n - firstPosition;
            // Say, first visible position is 8, you want position 10(n), wantedChild will now be 2
            // So that means your view is child #2 in the ViewGroup

            // Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
            View wantedView = componentListView.getChildAt(wantedChild);
            final ToggleButton mToggle = wantedView.findViewById(R.id.toggle);
            final int finalN = n;
            mToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mToggle.isChecked()) {
                        String phoneNo = "7014767086";
                        String message = "#" + charSet[finalN] + "0";

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, message, null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please check permissions and try again.",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    } else {
                        String phoneNo = "7014767086";
                        String message = "#" + charSet[finalN] + "1";

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, message, null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please check permissions and try again.",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}