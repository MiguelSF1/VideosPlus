package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.videosplus.R;
import com.example.videosplus.object.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    public void initView() {
        usernameEditText = findViewById(R.id.username_input_register);
        passwordEditText = findViewById(R.id.password_input_register);
        passwordConfirmEditText = findViewById(R.id.password_input2_register);
        Button signUpButton = findViewById(R.id.sign_up_button);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();
            if (!password.equals(passwordConfirm) || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Passwords not equal or empty fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                RequestQueue usersRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", username);
                jsonBody.put("password", password);
                String responseBody = jsonBody.toString();

                StringRequest usersStringRequest = new StringRequest(Request.Method.PUT, "http://192.168.1.103:8080/api/users",
                        response -> startActivity(new Intent(RegisterActivity.this, MainActivity.class)),
                        error -> Toast.makeText(RegisterActivity.this, "Failed Register Attempt", Toast.LENGTH_SHORT).show()) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        return responseBody.getBytes(StandardCharsets.UTF_8);
                    }
                };

                usersRequestQueue.add(usersStringRequest);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}