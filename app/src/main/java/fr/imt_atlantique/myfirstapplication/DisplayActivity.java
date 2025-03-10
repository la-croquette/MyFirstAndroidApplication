package fr.imt_atlantique.myfirstapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView textView = findViewById(R.id.text_display);

        UserInfo userInfo = getIntent().getParcelableExtra("USER_INFO");

        if (userInfo != null) {
            StringBuilder displayText = new StringBuilder();
            displayText.append("Nom: ").append(userInfo.getLastName()).append("\n");
            displayText.append("Prénom: ").append(userInfo.getFirstName()).append("\n");
            displayText.append("Ville de naissance: ").append(userInfo.getBirthCity()).append("\n");
            displayText.append("Date de naissance: ").append(userInfo.getBirthDate()).append("\n");
            displayText.append("Département: ").append(userInfo.getDepartment()).append("\n");

            List<String> phoneNumbers = userInfo.getPhoneNumbers();
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                displayText.append("Numéros de téléphone:\n");
                for (String number : phoneNumbers) {
                    displayText.append("- ").append(number).append("\n");
                }
            } else {
                displayText.append("Numéros de téléphone: Aucun\n");
            }

            textView.setText(displayText.toString());
        }
    }
}
