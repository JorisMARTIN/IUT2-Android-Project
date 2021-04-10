package fr.nezanmartin.lecoledesloustics.QuestionsAnswers;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Questions.Question;
import fr.nezanmartin.lecoledesloustics.QuestionsAnswers.model.QuestionsAnswersLevel;
import fr.nezanmartin.lecoledesloustics.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
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

        class CollectTags extends AsyncTask<Void, Void, List<String>>{

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

        for(String tag : this.allTags){
            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.VERTICAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_questions_answers_level_select, null);
            Button button = (Button) layout.findViewById(R.id.question_answers_template_button);

            button.setText(tag);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAllQuestionByTag(tag);
                }
            });
            globalLayout.addView(layout);
            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            levelSelectArea.addView(globalLayout);
        }
    }

    private void getAllQuestionByTag(String tag) {

        class CollectQuestions extends AsyncTask<Void, Void, List<Question>>{

            @Override
            protected List<Question> doInBackground(Void... voids) {

                List<Question> questions = database.getAppDatabase().questionDAO().getQuestionsByTag(tag);

                return questions;
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                super.onPostExecute(questions);

                launchActivity(questions);

            }
        }

        CollectQuestions collectQuestions = new CollectQuestions();
        collectQuestions.execute();

    }

    private void launchActivity(List<Question> questions) {

        QuestionsAnswersLevel level = new QuestionsAnswersLevel(new ArrayList<>(questions));

        Intent intent = new Intent(this, QuestionsAnswersLevel.class);
        intent.putExtra(QuestionsAnswersActivity.LEVEL_KEY, level);

    }
}