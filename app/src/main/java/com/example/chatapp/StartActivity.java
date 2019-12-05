package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button login, register;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerBtn);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }



                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        Intent intToMain = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(intToMain);
                    }
                });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                Intent intToMain = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });


    }
}
