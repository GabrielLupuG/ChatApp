package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    EditText user, email, password;
    Button btnReg;

    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user= findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnReg=findViewById(R.id.registerBtn);

        auth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = user.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();

                if (TextUtils.isEmpty(txtUsername)|| TextUtils.isEmpty(txtEmail)|| TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                }
                else{
                    register(txtUsername, txtEmail,txtPassword);
                }
            }
        });


    }


    private void register(String username, String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    String userid= firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("User").child(userid);

                    HashMap<String, String>hashMap=new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    hashMap.put("imageURL", "default");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "You can't register with this email or password", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
