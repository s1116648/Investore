package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import nl.hsleiden.investore.ui.login.SignInActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            findViewById(R.id.button).setVisibility(View.GONE);
            findViewById(R.id.button2).setVisibility(View.GONE);
            findViewById(R.id.button3).setVisibility(View.GONE);
        } else {

            findViewById(R.id.button).setVisibility(View.VISIBLE);
            findViewById(R.id.button2).setVisibility(View.VISIBLE);
            findViewById(R.id.button3).setVisibility(View.VISIBLE);
        }
    }

    public void goToAddItem(View view) {
        startActivity(new Intent(this, AddItemActivity.class));
    }

    public void goToList(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void goToNavigation(View view) {
        startActivity(new Intent(this, NavigationActivity.class));
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}