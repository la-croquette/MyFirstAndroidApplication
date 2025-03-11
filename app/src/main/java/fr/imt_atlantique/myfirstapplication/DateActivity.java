package fr.imt_atlantique.myfirstapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DateActivity extends AppCompatActivity {

    private TextView textSelectedDate;
    private int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        textSelectedDate = findViewById(R.id.text_selected_date);
        Button buttonValidate = findViewById(R.id.button_validate);
        Button buttonCancel = findViewById(R.id.button_cancel);


        Intent intent = getIntent();
        selectedYear = intent.getIntExtra("YEAR", 2023);
        selectedMonth = intent.getIntExtra("MONTH", 0);
        selectedDay = intent.getIntExtra("DAY", 1);

        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedYear = year;
            selectedMonth = month;
            selectedDay = dayOfMonth;
            textSelectedDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, selectedYear, selectedMonth, selectedDay).show();


        buttonValidate.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("YEAR", selectedYear);
            resultIntent.putExtra("MONTH", selectedMonth);
            resultIntent.putExtra("DAY", selectedDay);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        buttonCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
