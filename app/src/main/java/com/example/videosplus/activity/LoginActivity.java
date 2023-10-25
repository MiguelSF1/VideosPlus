package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videosplus.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameText, passwordText;
    private Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        usernameText = findViewById(R.id.username_input);
        passwordText = findViewById(R.id.password_input);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> {
            if (false) {
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}