package fr.imt_atlantique.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainLayout = findViewById(R.id.main_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (mainLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                Log.i("Lifecycle", "onCreate method");
                return insets;
            });
        } else {
            Log.e("Lifecycle", "Error: main_layout not found in activity_main.xml");
        }
    }

    public void validateAction(View v) {

        EditText lastName = findViewById(R.id.edit_last_name);
        EditText firstName = findViewById(R.id.edit_first_name);
        EditText birthDate = findViewById(R.id.edit_birth_date);
        EditText birthCity = findViewById(R.id.edit_birth_city);


        String textToShow = "Last Name: " + lastName.getText().toString() + "\n"
                + "First Name: " + firstName.getText().toString() + "\n"
                + "Birth Date: " + birthDate.getText().toString() + "\n"
                + "Birth City: " + birthCity.getText().toString() ;

        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_layout), textToShow, Snackbar.LENGTH_LONG);

        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        View snackbarView = snackbar.getView();
        TextView snackbarText = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);

        snackbarText.setMaxLines(5);
        snackbarText.setSingleLine(false);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void resetAction(MenuItem item) {

        EditText lastName = findViewById(R.id.edit_last_name);
        EditText firstName = findViewById(R.id.edit_first_name);
        EditText birthDate = findViewById(R.id.edit_birth_date);
        EditText birthCity = findViewById(R.id.edit_birth_city);

        lastName.setText("");
        firstName.setText("");
        birthDate.setText("");
        birthCity.setText("");

        Snackbar.make(findViewById(R.id.main_layout), "Fields have been reset", Snackbar.LENGTH_SHORT).show();
    }


}
