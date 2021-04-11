package fr.nezanmartin.lecoledesloustics.Exercices.QuestionsAnswers;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Level.LevelDAO;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;
import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.utils.Pair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsAnswersLevelSelect extends AppCompatActivity {

    private DatabaseClient database;
    boolean invalidated;

    // VIEW
    LinearLayout levelSelectArea;

    // DATA
    List<String> allTags;
    HashMap<String, Pair<Level, Game>> questionsAnswersLevels; //Map for link every Level with the player score


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers_level_select);
        database = DatabaseClient.getInstance(getApplicationContext());

        levelSelectArea = findViewById(R.id.questions_answers_level_select_area);

        allTags = new ArrayList<>();

        invalidated = false;

        initExercices();

    }


    @Override
    protected void onPause() {
        super.onPause();

        invalidated = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (invalidated) {
            recreate();
        }
    }


    private void initExercices() {

        class CollectLevels extends AsyncTask<Void, Void, HashMap<String, Pair<Level, Game>>> {

            @Override
            protected HashMap<String, Pair<Level, Game>> doInBackground(Void... voids) {

                LevelDAO levelDAO = database.getAppDatabase().levelDAO();
                GameDAO gameDAO = database.getAppDatabase().gameDAO();
                UserDAO userDAO = database.getAppDatabase().userDAO();

                List<Level> activities = levelDAO.getAllLevels();

                HashMap<String, Pair<Level, Game>> levels = new HashMap<>();

                for (Level activity: activities) {
                    // Sepratate "géographie" and other activité between all avtivities
                    if(activity.getGameMode().equalsIgnoreCase("géographie") /*|| <other activity> */){
                        levels.put(
                                activity.getName(),
                                new Pair<>(
                                        levelDAO.getLevelById(activity.getId()),
                                        gameDAO.getLevelGame(userDAO.getCurrentUser().getId(), activity.getId())
                                )
                        );
                    }
                }

                return levels;
            }

            @Override
            protected void onPostExecute(HashMap<String, Pair<Level, Game>> levels){
                super.onPostExecute(levels);

                questionsAnswersLevels = levels;
                initView();
            }

        }

        CollectLevels cl = new CollectLevels();
        cl.execute();
    }

    private void initView() {

        // Loop on every exercices
        for (Map.Entry<String, Pair<Level, Game>> entry : questionsAnswersLevels.entrySet()) {
            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.VERTICAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_questions_answers_level_select, null);
            Button button = (Button) layout.findViewById(R.id.question_answers_template_button);

            button.setText(entry.getValue().getItem1().getName());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(QuestionsAnswersLevelSelect.this, QuestionsAnswersActivity.class);
                    intent.putExtra(QuestionsAnswersActivity.TAG_KEY, entry.getValue().getItem1().getGameMode());

                    startActivity(intent);
                }
            });

            // Display score if it already exist
            RatingBar score = (RatingBar) layout.findViewById(R.id.question_answers_button_score);
            Level level = entry.getValue().getItem1();
            Game game = entry.getValue().getItem2();

            if (game != null && game.getLevelId() == level.getId()) {
                score.setRating(game.getScore());
            }

            globalLayout.addView(layout);
            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            levelSelectArea.addView(globalLayout);
        }
    }

}