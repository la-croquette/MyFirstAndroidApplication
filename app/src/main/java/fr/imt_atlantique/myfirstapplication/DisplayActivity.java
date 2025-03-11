package fr.imt_atlantique.myfirstapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private LinearLayout phoneContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView nameTextView = findViewById(R.id.text_name);
        TextView cityTextView = findViewById(R.id.text_city);
        TextView birthDateTextView = findViewById(R.id.text_birth_date);
        TextView departmentTextView = findViewById(R.id.text_department);
        phoneContainer = findViewById(R.id.phone_container);

        UserInfo userInfo = getIntent().getParcelableExtra("USER_INFO");

        if (userInfo != null) {
            nameTextView.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
            cityTextView.setText("Ville: " + userInfo.getBirthCity());
            birthDateTextView.setText("Date de naissance: " + userInfo.getBirthDate());
            departmentTextView.setText("Département: " + userInfo.getDepartment());

            List<String> phoneNumbers = userInfo.getPhoneNumbers();
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                for (String number : phoneNumbers) {
                    addPhoneNumberView(number);
                }
            } else {
                TextView emptyText = new TextView(this);
                emptyText.setText("Aucun numéro de téléphone");
                phoneContainer.addView(emptyText);
            }
        }
    }

    private void addPhoneNumberView(String number) {
        TextView phoneTextView = new TextView(this);
        phoneTextView.setText(number);
        phoneTextView.setTextSize(18);
        phoneTextView.setPadding(0, 10, 0, 10);
        phoneTextView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));

        phoneTextView.setOnClickListener(v -> dialPhoneNumber(number));

        phoneContainer.addView(phoneTextView);
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
