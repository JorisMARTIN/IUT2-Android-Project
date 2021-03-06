package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers.QuestionsAnswersLevelSelect;
import fr.nezanmartin.lecoledesloustics.Exercices.mathematics.MathematicsLevelSelect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LevelSelect extends AppCompatActivity implements View.OnClickListener {

    // VIEW
    Button mathematicsButton, questionsAnswersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        mathematicsButton = findViewById(R.id.level_select_mathematics_button);
        mathematicsButton.setOnClickListener(this);

        questionsAnswersButton = findViewById(R.id.level_select_questions_answers_button);
        questionsAnswersButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Intent intent;

        if(v == mathematicsButton) {
            intent = new Intent(LevelSelect.this, MathematicsLevelSelect.class);

        }else if(v == questionsAnswersButton){
            intent = new Intent(LevelSelect.this, QuestionsAnswersLevelSelect.class);

        }else{
            intent = null;
            return;
        }
        startActivity(intent);
    }
}
