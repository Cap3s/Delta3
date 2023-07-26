package com.example.deltatask3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePage extends AppCompatActivity {

    private Button buttonSplit;
    private Button buttonTransact;
    private TextView textViewMoneyToBePaid,textViewMoneyLent;
    private static final String BASE_URL = "http://localhost:8000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonSplit = findViewById(R.id.buttonSplit);
        buttonTransact = findViewById(R.id.buttonTransact);

        textViewMoneyToBePaid = findViewById(R.id.textViewOwed);
        textViewMoneyLent = findViewById(R.id.textViewLent);

        // Make an HTTP request to retrieve money lent and paid data from the backend
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + "/money-data")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                // Parse the response data to extract money lent and paid values
                // Assuming the response is in JSON format: {"moneyLent": 200.0, "moneyPaid": 150.0}
                double moneyLent = extractMoneyLent(responseData); // Implement the logic to extract money lent value
                double moneyPaid = extractMoneyPaid(responseData); // Implement the logic to extract money paid value

                // Update the TextViews with the retrieved values
                textViewMoneyToBePaid.setText("Money to be Paid: $" + moneyLent);
                textViewMoneyLent.setText("Money Lent: $" + moneyPaid);
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
        }

        buttonSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the SplitActivity
                Intent intent = new Intent(HomePage.this, SplitActivity.class);
                startActivity(intent);
            }
        });

        buttonTransact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the TransactionHistoryActivity
                Intent intent = new Intent(HomePage.this, TransactionHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    // Implement the logic to extract the money lent value from the response data
    private double extractMoneyLent(String responseData) {
        // Parse the JSON response and extract the money lent value
        // Return the extracted value as a double
        return 0.0; // Replace with your implementation
    }

    // Implement the logic to extract the money paid value from the response data
    private double extractMoneyPaid(String responseData) {
        // Parse the JSON response and extract the money paid value
        // Return the extracted value as a double
        return 0.0; // Replace with your implementation
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRANSACTION_HISTORY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the updated lent and owed amounts from the result
            double updatedLentAmount = data.getDoubleExtra("updatedLentAmount", 0.0);
            double updatedOwedAmount = data.getDoubleExtra("updatedOwedAmount", 0.0);

            // Update the TextViews with the updated amounts
            TextView textViewLent = findViewById(R.id.textViewLent);
            TextView textViewOwed = findViewById(R.id.textViewOwed);

            textViewLent.setText("Lent: " + updatedLentAmount);
            textViewOwed.setText("Owed: " + updatedOwedAmount);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textViewLent = findViewById(R.id.textViewLent);
        TextView textViewOwed = findViewById(R.id.textViewOwed);
        getIntent(intent);
        textViewLent.setText("Lent: " + lentAmount);
        textViewOwed.setText("Owed: " + owedAmount);
    }

}
