package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.SignUpFragment;

public class LoginSignUpMain extends AppCompatActivity {
    private Button loginbt, signupbt;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Login or Signup");
        loginbt = findViewById(R.id.loginbt);
        signupbt = findViewById(R.id.signupbt);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new LoginFragment());
        fragmentTransaction.commit();
        loginbt.setTextColor(Color.WHITE);
        loginbt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        signupbt.setBackgroundColor(getResources().getColor(R.color.loginbtcolor));
        signupbt.setTextColor(Color.BLACK);
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, new LoginFragment());
                    fragmentTransaction.commit();
                    loginbt.setTextColor(Color.WHITE);
                    loginbt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    signupbt.setBackgroundColor(getResources().getColor(R.color.loginbtcolor));
                    signupbt.setTextColor(Color.BLACK);
            }
        });

        signupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, new SignUpFragment());
                fragmentTransaction.commit();
                signupbt.setTextColor(Color.WHITE);
                signupbt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                loginbt.setBackgroundColor(getResources().getColor(R.color.loginbtcolor));
                loginbt.setTextColor(Color.BLACK);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}