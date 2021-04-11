package fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Questions.Question;
import fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers.model.QuestionsAnswersLevel;
import fr.nezanmartin.lecoledesloustics.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAnswersLevelSelect extends AppCompatActivity {

    private DatabaseClient database;


    // VIEW
    LinearLayout levelSelectArea;

    // DATA
    List<String> allTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers_level_select);
        database = DatabaseClient.getInstance(getApplicationContext());

        levelSelectArea = findViewById(R.id.questions_answers_level_select_area);

        allTags = new ArrayList<>();

        initExercices();

    }

    private void initExercices() {

        class CollectTags extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {

                List<String> tags = database.getAppDatabase().questionDAO().getAllTags();

                return tags;
            }

            @Override
            protected void onPostExecute(List<String> tags) {
                super.onPostExecute(tags);

                allTags = tags;
                initView();
            }
        }

        CollectTags collectTags = new CollectTags();
        collectTags.execute();
    }

    private void initView() {

        for (String tag : this.allTags) {
            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.VERTICAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_questions_answers_level_select, null);
            Button button = (Button) layout.findViewById(R.id.question_answers_template_button);

            button.setText(tag);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(QuestionsAnswersLevelSelect.this, QuestionsAnswersActivity.class);
                    intent.putExtra(QuestionsAnswersActivity.TAG_KEY, tag);

                    startActivity(intent);
                }
            });


            RatingBar score = (RatingBar) layout.findViewById(R.id.question_answers_button_score);
            //TODO: Display score

            globalLayout.addView(layout);
            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            levelSelectArea.addView(globalLayout);
        }
    }

}