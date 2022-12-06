package edu.uga.cs.finalshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ItemManagementActivity extends AppCompatActivity {

    private TextView signedinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_management);

        Button addButt = findViewById(R.id.addButton);
        Button viewButt = findViewById(R.id.viewallButton);
        Button settleButt = findViewById(R.id.settleButton);
        Button slistButt = findViewById(R.id.shoppingButton);
        Button logoutButt = findViewById(R.id.logoutButton);
        signedinText = findViewById(R.id.textView3);

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if( currentUser != null ) {
                    // User is signed in
                    String userEmail = currentUser.getEmail();
                    signedinText.setText( "Account: " + userEmail );
                } else {
                    // User is signed out
                    signedinText.setText( "Not Signed In" );
                }
            }
        });

        addButt.setOnClickListener(new addClickListener());
        viewButt.setOnClickListener(new viewallListener());
        settleButt.setOnClickListener(new settleListener());
        slistButt.setOnClickListener(new shoppingListener());
        logoutButt.setOnClickListener(new logoutListener());

    } // onCreate

    private class addClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AddItemActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class viewallListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ViewAllActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class settleListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SettleActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class shoppingListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), BasketActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class logoutListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            FirebaseAuth.getInstance().signOut();
            view.getContext().startActivity(intent);
        }
    }


} // ItemManagementActivity
