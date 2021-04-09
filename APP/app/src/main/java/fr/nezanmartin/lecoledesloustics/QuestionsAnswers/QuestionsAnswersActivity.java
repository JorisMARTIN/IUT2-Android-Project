package fr.nezanmartin.lecoledesloustics.QuestionsAnswers;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.QuestionsAnswers.model.QuestionsAnswersLevel;
import fr.nezanmartin.lecoledesloustics.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionsAnswersActivity extends AppCompatActivity {

    public static final String LEVEL_KEY = "level_key";
    // VIEW
    TextView questionNumber, questionText;
    RadioGroup radioGroup;
    RadioButton answer0, answer1, answer2;
    Button validateButton;

    // DATA
    QuestionsAnswersLevel level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers);

        //Initalisation of all view
        questionNumber = findViewById(R.id.question_number_text);
        questionText = findViewById(R.id.question_text);

        radioGroup = findViewById(R.id.question_answers_radio_group);
        answer0 = findViewById(R.id.answer_number0);
        answer1 = findViewById(R.id.answer_number1);
        answer2 = findViewById(R.id.answer_number2);

        validateButton = findViewById(R.id.question_confirm);

        level = getIntent().getParcelableExtra(LEVEL_KEY);

        initLevel();
    }

    private void initLevel() {
        //TODO
    }
}