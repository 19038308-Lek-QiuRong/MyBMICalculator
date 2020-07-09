package sg.edu.rp.c346.id19036308.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvLastCalDate, tvLastCalBMI, tvBMIResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.buttonCal);
        btnReset = findViewById(R.id.buttonReset);
        tvLastCalDate = findViewById(R.id.textViewLastCalDate);
        tvLastCalBMI = findViewById(R.id.textViewLastCalBMI);
        tvBMIResult = findViewById(R.id.textViewBMI);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLastCalBMI.setText("");
                tvLastCalBMI.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor change = prefs.edit();
                change.clear();
                change.commit();
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWeight = etWeight.getText().toString();
                String strHeight = etHeight.getText().toString();
                float w = Float.parseFloat(strWeight);
                float h = Float.parseFloat(strHeight);
                float bmi = w / (h * h);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvLastCalDate.setText(datetime);
                tvLastCalBMI.setText(String.format("%.3f", bmi));
                etHeight.setText("");
                etWeight.setText("");

                if (bmi == 0.0){
                    tvBMIResult.setText("");

                } else if (bmi < 18.5){
                    tvBMIResult.setText("You are underweight");
                } else if (bmi >= 18.5 && bmi < 25){
                    tvBMIResult.setText("Your BMI is normal");
                } else if (bmi >=25 && bmi <30) {
                    tvBMIResult.setText("You are overweight");
                } else if (bmi >= 30){
                    tvBMIResult.setText("You are severely overweight");
                } else{
                    tvBMIResult.setText("Error in calculation");
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get the user input from the EditText and store it in a variable
        String strWeight = etWeight.getText().toString();
        String strHeight = etHeight.getText().toString();
        float weight = Float.parseFloat(strWeight);
        float height = Float.parseFloat(strHeight);

        // Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1d: Add the key-value pair
        prefEdit.putFloat("Weight", weight);
        prefEdit.putFloat("Height", height);

        // Step 1e: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
        float lastCalBMI = prefs.getFloat("lastCalBMI", 0.0f);
        String datetimeS = prefs.getString("datetime", "");



        tvLastCalDate.setText(datetimeS);
        tvLastCalBMI.setText(lastCalBMI + "");


    }
}
