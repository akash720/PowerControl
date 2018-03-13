package com.example.azura.powercontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

//    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

//        sp = getSharedPreferences("login",MODE_PRIVATE);
//
//        if(sp.getBoolean("logged",true)){
//            goToMainActivity();
//        }
    }

    public void sendMessage(View view) {

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Blank entries not allowed", Toast.LENGTH_LONG).show();
        }
        else{
            if (username.equals("admin") && password.equals("12345")) {

//                sp.edit().putBoolean("logged",true).apply();
                goToMainActivity();
                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void goToMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

//    @Override
//    protected void onStart() {
//        sp.edit().putBoolean("logged",false).apply();
//        super.onStart();
//    }
}
