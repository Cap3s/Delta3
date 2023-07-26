package com.example.deltatask3;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDOB;
    private EditText editTextEmailAddress;
    private EditText editTextPhone;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignUp;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String BASE_URL = "http://localhost:8000"; // Replace with your FastAPI server URL
    // Map to store unique names and their corresponding passwords
    private Map<String, String> namePasswordMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = findViewById(R.id.editTextName);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the sign-up data from the input fields
                String username = editTextName.getText().toString();
                String dob = editTextDOB.getText().toString();
                String email = editTextEmailAddress.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextNewPassword.getText().toString();

                // Check if the password and confirm password match
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a JSON payload with the sign-up data
                String jsonBody = "{\"username\": \"" + username + "\", \"dob\": \"" + dob + "\", \"email\": \"" + email + "\", " +
                        "\"phone\": \"" + phone + "\", \"password\": \"" + password + "\"}";

                // Send the sign-up data to the FastAPI server
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(jsonBody, MEDIA_TYPE_JSON);
                Request request = new Request.Builder()
                        .url(BASE_URL + "/signup")
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        // Handle the response or navigate to a new activity as needed
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
                        // Handle the sign-up failure or display an appropriate error message
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
                    // Handle the exception or display an appropriate error message
                }
            }
        });

    }
}
