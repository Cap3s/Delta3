package com.example.deltatask3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplitActivity extends AppCompatActivity {

    private FrameLayout gridContainer;
    private ImageButton floatingActionButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);

        gridContainer = findViewById(R.id.gridContainer);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the new grid layout
                View gridLayout = LayoutInflater.from(SplitActivity.this)
                        .inflate(R.layout.gridlayout, gridContainer, false);

                // Find the views in the grid layout
                EditText editTextGridName = gridLayout.findViewById(R.id.editTextGridName);
                Button buttonCloseGrid = gridLayout.findViewById(R.id.buttonCloseGrid);

                buttonCloseGrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the grid layout when the close button is clicked
                        gridContainer.removeView(gridLayout);
                    }
                });

                // Add the grid layout to the container
                gridContainer.addView(gridLayout);
            }
        });
    }
}
