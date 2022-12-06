package edu.uga.cs.finalshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class FinalSettleActivity extends AppCompatActivity {

    private double finalCost;
    private TextView avgCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_settle);
        avgCost = findViewById(R.id.averageC);

        Intent intent = getIntent();
        finalCost = intent.getDoubleExtra("cost", 0);
        DecimalFormat f = new DecimalFormat("##.00");
        avgCost.setText("Average Per Roommate: $" + f.format(finalCost));


    } // onCreate


}
