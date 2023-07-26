package com.example.deltatask3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTransaction;
    private GridLayout gridLayoutTransactions;
    private TextView textViewAmount;
    private String username,type;
    private Float amount;
    private boolean isLent;
    private double lentAmount = 0;
    private double owedAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        // Initialize the FloatingActionButton and GridLayout
        fabAddTransaction = findViewById(R.id.fabAddTransaction);
        gridLayoutTransactions = findViewById(R.id.gridLayoutTransactions);

        // Set an OnClickListener for the FloatingActionButton
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to add a new rectangular box to the GridLayout
                addNewTransactionBox();
            }
        });
    }

    private void addNewTransactionBox() {
        // Inflate a new rectangular box layout
        View transactionBox = LayoutInflater.from(this).inflate(R.layout.transaction_box_layout, gridLayoutTransactions, false);

        // Find the views in the transaction box layout
        EditText editTextUsername = transactionBox.findViewById(R.id.editTextUsername);
        Spinner spinnerType = transactionBox.findViewById(R.id.spinnerType);
        EditText editTextAmount = transactionBox.findViewById(R.id.editTextAmount);
        Button buttonEnter = transactionBox.findViewById(R.id.buttonEnter);
        textViewAmount = transactionBox.findViewById(R.id.editTextAmount);

        // Set up the spinner adapter and populate it with user names from FastAPI
        ArrayAdapter<String> usernameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getUsernamesFromFastAPI());
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(usernameAdapter);

        // Set a flag to keep track of the dropdown selection
        // true = lent, false = owed
        isLent = true;

        // Create an array to hold dropdown options
        String[] options = new String[]{"Lent", "Owed"};

        // Create the dropdown adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the dropdown
        spinnerType.setAdapter(adapter);

        // Set an OnItemSelectedListener for the dropdown
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the flag based on the selection
                isLent = position == 0; // true if "Lent" is selected, false if "Owed" is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set an OnClickListener for the enter button
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the selected username, transaction type, and amount
                username = editTextUsername.getText().toString();
                type = spinnerType.getSelectedItem().toString();

                String buttonText = buttonEnter.getText().toString();

                if (buttonText.equals("Enter")) {
                    // Change button text to "Settle"
                    buttonEnter.setText("Settle");
                } else if (buttonText.equals("Settle")) {
                    // Change button text to "Settled"
                    buttonEnter.setText("Settled");

                    // Change button color and disable click
                    buttonEnter.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    buttonEnter.setEnabled(false);


                    // Disable the dropdown after clicking the button
                    spinnerType.setEnabled(false);

                    // Get the amount entered
                    String amountStr = editTextAmount.getText().toString();
                    double amount = Double.parseDouble(amountStr);

                    // Update the amount display based on the dropdown selection
                    if (isLent) {
                        // If "Lent" is selected, add '-' in front of the amount and turn text red
                        owedAmount += amount;
                        textViewAmount.setText("-" + amountStr);
                        textViewAmount.setTextColor(Color.RED);
                    } else {
                        // If "Owed" is selected, add '+' in front of the amount and turn text green
                        lentAmount -= amount;
                        textViewAmount.setText("+" + amountStr);
                        textViewAmount.setTextColor(Color.GREEN);
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedLentAmount", lentAmount);
                    resultIntent.putExtra("updatedOwedAmount", owedAmount);
                    setResult(RESULT_OK, resultIntent);

                    // Delay the fade-out animation by 2 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Fade out the transaction box
                            transactionBox.animate().alpha(0f).setDuration(500).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    // Remove the transaction box from the GridLayout
                                    gridLayoutTransactions.removeView(transactionBox);
                                }
                            }).start();
                        }
                    }, 2000);
                }
            }
        });

        // Add the transaction box to the GridLayout
        gridLayoutTransactions.addView(transactionBox);
    }

    private List<String> getUsernamesFromFastAPI() {
        // Implement the logic to retrieve user names from FastAPI
        // and return a list of usernames
        List<String> usernames = new ArrayList<>();
        // Replace with your implementation to fetch usernames from FastAPI
        return usernames;
    }
}
