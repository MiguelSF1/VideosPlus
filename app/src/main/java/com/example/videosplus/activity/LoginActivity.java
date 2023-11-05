package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.videosplus.R;
import com.example.videosplus.domain.User;
import com.google.gson.Gson;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        Button signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(view -> {
            if (!sendRequestUsers()) {
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private boolean sendRequestUsers() {
        AtomicBoolean loginSuccess = new AtomicBoolean(false);
        RequestQueue usersRequestQueue = Volley.newRequestQueue(this);
        StringRequest usersStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/users/" + username.getText().toString(), response -> {
            User user = new Gson().fromJson(response, User.class);
            if (Objects.equals(user.getPassword(), password.getText().toString())) {
                loginSuccess.set(true);
            }
        }, error -> Log.d("failure", "sendRequestUsers: Failed "));
        usersRequestQueue.add(usersStringRequest);
        return loginSuccess.get();
    }
}