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

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SCORE_KEY = "score_key";
    public static final String FINAL_SCORE_KEY = "final_score_key";

    //DATA
    int score, finalScore;

    //VIEW
    TextView message, scoreMessage;
    Button correctionButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        message = findViewById(R.id.result_message);
        scoreMessage = findViewById(R.id.result_score);

        score = getIntent().getIntExtra(SCORE_KEY, 0);
        finalScore = getIntent().getIntExtra(FINAL_SCORE_KEY, 0);

        if(score == 10){
            message.setText("Félicitation !\nVotre score : ");
        }else{
            message.setText("Dommage ...\nVotre score : ");
        }
        scoreMessage.setText(score + " /10");

        correctionButton = findViewById(R.id.result_correction_button);
        correctionButton.setOnClickListener(this);


        backButton = findViewById(R.id.result_return_button);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == correctionButton){
            setResult(RESULT_CANCELED);
        }else if(v == backButton){

            //TODO: Save finalScore in DB

            setResult(RESULT_OK);

        }

        finish();
    }
}