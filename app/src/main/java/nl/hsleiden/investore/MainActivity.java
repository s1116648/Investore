package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.hsleiden.investore.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void goToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}