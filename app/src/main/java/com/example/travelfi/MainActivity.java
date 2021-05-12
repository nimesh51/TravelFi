package com.example.travelfi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button signin;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        signin = findViewById(R.id.btn_signin);
        signup = findViewById(R.id.btn_signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (signin.getBackground() == null) {

                    signup.setBackgroundResource(0);
                    signup.setTextColor(Color.parseColor("#2490E4"));
                    signin.setBackgroundResource(R.drawable.button_shape);
                    signin.setTextColor(Color.WHITE);
                }

                startActivity(new Intent(MainActivity.this,SignInActivity.class));
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signin.setBackgroundResource(0);
                signin.setTextColor(Color.parseColor("#2490E4"));
                signup.setBackgroundResource(R.drawable.button_shape);
                signup.setTextColor(Color.WHITE);

                startActivity(new Intent(MainActivity.this,SignUpActivity.class));

            }
        });
    }
}