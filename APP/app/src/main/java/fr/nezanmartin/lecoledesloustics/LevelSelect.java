package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        LayoutInflater inflater = getLayoutInflater();

        LinearLayout levelSelectArea = findViewById(R.id.level_select_area);


        for (int i = 0; i < 10; i++) {
            LinearLayout level_button = (LinearLayout) inflater.inflate(R.layout.component_level_button, null);

            Button level_button_button = level_button.findViewById(R.id.level_button_button);
            level_button_button.setText(String.valueOf(i));

            levelSelectArea.addView(level_button);
        }
    }
}
