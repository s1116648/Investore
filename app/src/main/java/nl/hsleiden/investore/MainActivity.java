package nl.hsleiden.investore;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.UUID;

import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.ui.login.SignInActivity;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            findViewById(R.id.button).setVisibility(View.GONE);
            findViewById(R.id.button2).setVisibility(View.GONE);
            findViewById(R.id.button3).setVisibility(View.GONE);
            findViewById(R.id.button4).setVisibility(View.GONE);
            findViewById(R.id.button10).setVisibility(View.GONE);
        } else {

            findViewById(R.id.button).setVisibility(View.VISIBLE);
            findViewById(R.id.button2).setVisibility(View.VISIBLE);
            findViewById(R.id.button3).setVisibility(View.VISIBLE);
            findViewById(R.id.button4).setVisibility(View.VISIBLE);
            findViewById(R.id.button10).setVisibility(View.VISIBLE);
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

    public void goToDatabaseTest(View view) {
        startActivity(new Intent(this, DatabaseTestActivity.class));
    }

    public void logout(View view) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
}