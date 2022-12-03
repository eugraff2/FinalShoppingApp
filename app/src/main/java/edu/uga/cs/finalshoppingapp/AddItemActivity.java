package edu.uga.cs.finalshoppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameText = findViewById(R.id.editText1);
        priceText = findViewById(R.id.editText2);
        Button saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener(new saveClickListener());
    } // onCreate



    private class saveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString();
            double price = Double.parseDouble(priceText.getText().toString());
            final Item item = new Item(name, price);

            // Add a new element (Item) to the list of items in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("items");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new item.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain items.
            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Item created for " + item.getName(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            nameText.setText("");
                            priceText.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed to create a item for " + item.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }



} // AddItemActivity
