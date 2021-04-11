package fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Questions.Question;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;
import fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers.model.QuestionsAnswersLevel;
import fr.nezanmartin.lecoledesloustics.Exercices.ResultActivity;
import fr.nezanmartin.lecoledesloustics.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsAnswersActivity extends AppCompatActivity {

    public static final String TAG_KEY = "tag_key";
    public static final String LEVEL_ID_KEY = "level_id_key";
    private static final int QUESTIONS_ANSWERS_REQUEST = 1;

    // VIEW
    TextView questionNumber, questionText, feedbackText, advice;
    RadioGroup radioGroup;
    RadioButton answer0, answer1, answer2;
    Button validateButton;

    // DATA
    QuestionsAnswersLevel level;
    String tag;
    int level_id;
    DatabaseClient database;
    HashMap<Integer, RadioButton> answers;

    boolean isCorrection = false;
    int finalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers);

        database = DatabaseClient.getInstance(getApplicationContext());

        //Initalisation of all view
        questionNumber = findViewById(R.id.question_number_text);
        questionText = findViewById(R.id.question_text);
        feedbackText = findViewById(R.id.question_answer_feedback_text);
        advice = findViewById(R.id.question_answer_advice);

        radioGroup = findViewById(R.id.question_answers_radio_group);
        answer0 = findViewById(R.id.answer_number0);
        answer1 = findViewById(R.id.answer_number1);
        answer2 = findViewById(R.id.answer_number2);

        validateButton = findViewById(R.id.question_confirm);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWin();
            }
        });

        answers = new HashMap<>();

        tag = getIntent().getStringExtra(TAG_KEY);
        level_id = getIntent().getIntExtra(LEVEL_ID_KEY, 0);

        /* Async class for collect questions all question from a specific tag */
        class CollectQuestions extends AsyncTask<Void, Void, List<Question>> {

            @Override
            protected List<Question> doInBackground(Void... voids) {

                List<Question> allQuestions = database.getAppDatabase().questionDAO().getQuestionsByTag(tag);

                return allQuestions;
            }

            @Override
            protected void onPostExecute(List<Question> allQuestions) {
                super.onPostExecute(allQuestions);

                level = new QuestionsAnswersLevel((ArrayList) allQuestions);
                updateLevel();
            }
        }

        CollectQuestions collectQuestions = new CollectQuestions();
        collectQuestions.execute();

    }

    private void updateLevel() {

        // Reset feedback and color of every choices
        feedbackText.setText("");
        answer0.setTextColor(0xFF000000);
        answer1.setTextColor(0xFF000000);
        answer2.setTextColor(0xFF000000);

        if(isCorrection){
            advice.setText("Attention : Tu dois aller au bout de la correction pour que ton premier score soit sauvegardé !");
            answer0.setChecked(false);
            answer1.setChecked(false);
            answer2.setChecked(false);

            int goodAnswer = level.getCurrentQuestion().getGood_answer();
            RadioButton checkedRadio = answers.get(level.getCurrentQuestionIndex());

            checkedRadio.setChecked(true);

            if((checkedRadio == answer0 && goodAnswer == 0)
            || (checkedRadio == answer1 && goodAnswer == 1)
            || (checkedRadio == answer2 && goodAnswer == 2)){
                answers.get(level.getCurrentQuestionIndex()).setTextColor(0xFF00FF00);
            }else{
                answers.get(level.getCurrentQuestionIndex()).setTextColor(0xFFFF0000);
            }
        }

        validateButton.setVisibility(View.VISIBLE);

        Question currentQuestion = level.getCurrentQuestion();
        this.questionNumber.setText((level.getCurrentQuestionIndex()+1) + " / " + level.getQuestions().size());

        this.questionText.setText(currentQuestion.getQuestion());
        String[] answers = currentQuestion.getAllAnswers();
        answer0.setText(answers[0]);
        answer1.setText(answers[1]);
        answer2.setText(answers[2]);
    }

    private void checkWin() {

        // Check if one option is selected
        if(!(answer0.isChecked() || answer1.isChecked() || answer2.isChecked())){
            feedbackText.setText("Tu dois choisir une réponse avant de valider :)");
            return;
        }

        //Save the answer for the optional correction
        answers.put(
                level.getCurrentQuestionIndex(),
                (answer0.isChecked()) ? answer0 : (answer1.isChecked()) ? answer1 : answer2);

        validateButton.setVisibility(View.INVISIBLE);

        int goodAnswer = level.getCurrentQuestion().getGood_answer();

        // The good answer is selected
        if((answer0.isChecked() && goodAnswer == 0)
        || (answer1.isChecked() && goodAnswer == 1)
        || (answer2.isChecked() && goodAnswer == 2)){
            // Good answer
            level.addGoodAnswer();
            feedbackText.setTextColor(0xFF00FF00);
            feedbackText.setText("Bonne réponse !");

            if(answer0.isChecked()) answer0.setTextColor(0xFF00FF00);
            else if(answer1.isChecked()) answer1.setTextColor(0xFF00FF00);
            else if(answer2.isChecked()) answer2.setTextColor(0xFF00FF00);

        }else{

            feedbackText.setTextColor(0xFFFF0000);
            feedbackText.setText("Mauvaise réponse ! La bonne réponse était " + level.getCurrentQuestion().getAllAnswers()[goodAnswer]);

            if(answer0.isChecked()) answer0.setTextColor(0xFFFF0000);
            else if(answer1.isChecked()) answer1.setTextColor(0xFFFF0000);
            else if(answer2.isChecked()) answer2.setTextColor(0xFFFF0000);

            if(goodAnswer == 0) answer0.setTextColor(0xFF00FF00);
            else if(goodAnswer == 1) answer1.setTextColor(0xFF00FF00);
            else if(goodAnswer == 2) answer2.setTextColor(0xFF00FF00);

        }

        feedbackText.setTextColor(0xFF000000);

        // Start a countdown to give a feedback on the answer
        new CountDownTimer(2500, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if(level.hasNextQuestion() == false){
                    if(!isCorrection) finalScore = level.getGoodAnswerNumber();

                    Intent intent = new Intent(QuestionsAnswersActivity.this, ResultActivity.class);
                    intent.putExtra(ResultActivity.SCORE_KEY, level.getGoodAnswerNumber());

                    startActivityForResult(intent, QUESTIONS_ANSWERS_REQUEST);

                }else{

                    level.nextQuestion();
                    updateLevel();
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != QUESTIONS_ANSWERS_REQUEST) return;

        if(resultCode == RESULT_OK){
            /**
             * Stocker le score dans la BDD
             */
            class SaveScore extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    UserDAO userDAO = database.getAppDatabase().userDAO();
                    GameDAO gameDAO = database.getAppDatabase().gameDAO();

                    int userID = userDAO.getCurrentUser().getId();
                    Game game = gameDAO.getLevelGame(userID, 12);
                    float score = (float) (finalScore * 0.3);


                    if (game == null) {
                        game = new Game();
                        game.setUserId(userID);
                        game.setLevelId(level_id);
                        game.setScore(score);
                        gameDAO.insert(game);
                    } else {
                        game.setScore(score);
                        gameDAO.update(game);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void v) {
                    super.onPostExecute(v);
                    finish();
                }
            }

            SaveScore sc = new SaveScore();
            sc.execute();

        }else if(resultCode == RESULT_CANCELED){
            isCorrection = true;
            level.setGoodAnswerNumber(0);
            level.setCurrentQuestionIndex(0);
            updateLevel();
        }

    }
}