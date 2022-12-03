package edu.uga.cs.finalshoppingapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new loginClickListener() );
        registerButton.setOnClickListener(new registerClickListener() );

    } // onCreate

    private class loginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            List<AuthUI.IdpConfig> providers = Arrays.asList (
              new AuthUI.IdpConfig.EmailBuilder().build()
            );

            // creating intent to sign in to Firebase
            Intent loginIntent = AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers).setIsSmartLockEnabled(false).build();
            loginLauncher.launch(loginIntent);

        } // onClick

    } // loginListener

    // The ActivityResultLauncher class provides a new way to invoke an activity
    // for some result.  It is a replacement for the deprecated method startActivityForResult.
    //
    // The signInLauncher variable is a launcher to start the AuthUI's logging in process that
    // should return to the MainActivity when completed.  The overridden onActivityResult
    // is then called when the Firebase logging-in process is finished.
    private ActivityResultLauncher<Intent> loginLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                        @Override
                        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                            onLoginResult(result);
                        }
                    }
            );

    // This method is called once the Firebase sign-in activity (launched above) returns (completes).
    // Then, the current (logged-in) Firebase user can be obtained.
    // Subsequently, there is a transition to the ItemManagementActivity.
    private void onLoginResult( FirebaseAuthUIAuthenticationResult result ) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            if( response != null ) {

            }

            Intent intent = new Intent( this, ItemManagementActivity.class );
            startActivity( intent );
        }
        else {
            // Sign in failed. If response is null the user canceled the
            Toast.makeText( getApplicationContext(),
                    "Sign in failed",
                    Toast.LENGTH_SHORT).show();
        }
    } // onLoginResult

    private class registerClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }

} // MainActivity