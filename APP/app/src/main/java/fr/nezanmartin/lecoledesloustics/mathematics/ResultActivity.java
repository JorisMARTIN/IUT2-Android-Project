package fr.nezanmartin.lecoledesloustics.mathematics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.nezanmartin.lecoledesloustics.LevelSelect;
import fr.nezanmartin.lecoledesloustics.MainActivity;
import fr.nezanmartin.lecoledesloustics.R;

public class ResultActivity extends AppCompatActivity {

    public static final String SCORE_KEY = "score_key";

    //DATA
    int score;

    //VIEW
    TextView message, scoreMessage;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        message = findViewById(R.id.result_message);
        scoreMessage = findViewById(R.id.result_score);

        score = getIntent().getIntExtra(SCORE_KEY, 0);

        if(score == 10){
            message.setText("Félicitation !\nVotre score : ");
        }else{
            message.setText("Dommage ...\nVotre score : ");
        }
        scoreMessage.setText(score + " /10");

        backButton = findViewById(R.id.result_return_button);

        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, LevelSelect.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}