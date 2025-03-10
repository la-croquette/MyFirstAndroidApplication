package fr.imt_atlantique.myfirstapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout phoneContainer;
    private Spinner spinnerDepartments;
    private EditText birthDateEditText;
    private int phoneCount = 0;
    private String selectedDepartment = "";

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

        phoneContainer = findViewById(R.id.phone_container);
        Button btnAddPhone = findViewById(R.id.btn_add_phone);
        spinnerDepartments = findViewById(R.id.spinner_departments);
        birthDateEditText = findViewById(R.id.edit_birth_date);

        btnAddPhone.setOnClickListener(v -> addPhoneNumberField());
        // anonymous class
        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = parent.getItemAtPosition(position).toString();
                updateSnackbarMessage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDepartment = "";
            }
        });

        birthDateEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            birthDateEditText.setText(selectedDate);
            updateSnackbarMessage();
        }, year, month, day);

        datePickerDialog.show();
    }

    public void validateAction(View v) {
        EditText lastName = findViewById(R.id.edit_last_name);
        EditText firstName = findViewById(R.id.edit_first_name);
        EditText birthCity = findViewById(R.id.edit_birth_city);

        List<String> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < phoneContainer.getChildCount(); i++) {
            View phoneEntry = phoneContainer.getChildAt(i);
            if (phoneEntry instanceof LinearLayout) {
                EditText phoneInput = (EditText) ((LinearLayout) phoneEntry).getChildAt(0);
                String phoneNumber = phoneInput.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    phoneNumbers.add(phoneNumber);
                }
            }
        }

        UserInfo userInfo = new UserInfo(
                lastName.getText().toString(),
                firstName.getText().toString(),
                birthCity.getText().toString(),
                birthDateEditText.getText().toString(),
                selectedDepartment,
                phoneNumbers
        );

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("USER_INFO", userInfo);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_wikipedia) {
            openWikipediaPage();
            return true;
        } else if (id == R.id.action_share_city) {
            shareBirthCity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openWikipediaPage() {
        EditText birthCity = findViewById(R.id.edit_birth_city);
        String city = birthCity.getText().toString().trim();

        if (city.isEmpty()) {
            Snackbar.make(findViewById(R.id.main_layout), "Veuillez entrer la ville de naissance.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Generate a Wikipedia search URL for the city, replacing spaces with %20
        String url = "http://fr.wikipedia.org/?search=" + city.replace(" ", "%20");
        // ACTION_VIEW is used to tell the intent " I want to view something(in this case, it is url, so please help me)
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(R.id.main_layout), "Aucun navigateur trouvé pour ouvrir le lien.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void shareBirthCity() {
        EditText birthCity = findViewById(R.id.edit_birth_city);
        String city = birthCity.getText().toString().trim();

        if (city.isEmpty()) {
            Snackbar.make(findViewById(R.id.main_layout), "Veuillez entrer la ville de naissance.", Snackbar.LENGTH_SHORT).show();
            return;
        }


        String shareText = "Je suis né(e) à " + city + ".";


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setType("text/plain");

        Intent chooser = Intent.createChooser(intent, "Partager via");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        } else {
            Snackbar.make(findViewById(R.id.main_layout), "Aucune application disponible pour partager.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void resetAction(MenuItem item) {
        EditText lastName = findViewById(R.id.edit_last_name);
        EditText firstName = findViewById(R.id.edit_first_name);
        EditText birthCity = findViewById(R.id.edit_birth_city);

        lastName.setText("");
        firstName.setText("");
        birthDateEditText.setText("");
        birthCity.setText("");
        phoneContainer.removeAllViews();
        phoneCount = 0;


        spinnerDepartments.setSelection(0);
        selectedDepartment = "";

        Snackbar.make(findViewById(R.id.main_layout), "All fields reset", Snackbar.LENGTH_SHORT).show();
    }

    private void addPhoneNumberField() {

        LinearLayout phoneEntry = new LinearLayout(this);
        phoneEntry.setOrientation(LinearLayout.HORIZONTAL);


        EditText phoneInput = new EditText(this);
        phoneInput.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        phoneInput.setHint("Enter phone number");
        phoneInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);


        ImageButton deleteButton = new ImageButton(this);
        deleteButton.setImageResource(android.R.drawable.ic_delete);
        deleteButton.setBackgroundColor(0);
        // Since this interface has only one method, we can use a lambda expression
        deleteButton.setOnClickListener(v -> {
            phoneContainer.removeView(phoneEntry);
            phoneCount--;
            updateSnackbarMessage();
        });


        phoneEntry.addView(phoneInput);
        phoneEntry.addView(deleteButton);
        phoneContainer.addView(phoneEntry);
        phoneCount++;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(phoneInput.getWindowToken(), 0);
        }

        updateSnackbarMessage();
    }

    private void updateSnackbarMessage() {
        String message = "Fields updated. Phone numbers: " + phoneCount + ", Birth Date: " + birthDateEditText.getText().toString() + ", Department: " + selectedDepartment;
        Snackbar.make(findViewById(R.id.main_layout), message, Snackbar.LENGTH_SHORT).show();
    }
}
