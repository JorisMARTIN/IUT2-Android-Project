package fr.nezanmartin.lecoledesloustics.Exercices.mathematics;

import androidx.appcompat.app.AppCompatActivity;
import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Level.LevelDAO;
import fr.nezanmartin.lecoledesloustics.MainActivity;
import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.utils.Pair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathematicsLevelSelect extends AppCompatActivity {

    private DatabaseClient database;
    HashMap<String, Pair<List<Level>, List<Game>>> mathsLevels;
    boolean invalidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematics_level_select);

        database = DatabaseClient.getInstance(getApplicationContext());
        getLevels();

        invalidated = false;
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

    private void getLevels() {

        /**
         * Async task to fetch activities (each levels)
         * and associated game (object containing the score of the user at a level)
         */
        class CollectLevels extends AsyncTask<Void, Void, HashMap<String, Pair<List<Level>, List<Game>>>> {

            @Override
            protected HashMap<String, Pair<List<Level>, List<Game>> > doInBackground(Void... voids) {
                LevelDAO levelDAO = database.getAppDatabase().levelDAO();
                GameDAO gameDAO = database.getAppDatabase().gameDAO();

                List<Level> activities = levelDAO.getAllLevels();

                // Stores for each activities, all levels and games played by the current user.
                HashMap<String, Pair<List<Level>, List<Game>>> levels = new HashMap<>();

                for (Level activity: activities) {
                    // Ignores question/answers activities
                    if(!(activity.getGameMode().equalsIgnoreCase("géographie") || activity.getGameMode().equalsIgnoreCase("conjugaison"))){
                        levels.put(
                                activity.getName(),
                                new Pair<>(
                                        levelDAO.getLevels(activity.getName()),
                                        gameDAO.getCurrentUserGames()
                                )
                        );
                    }
                }

                return levels;
            }

            @Override
            protected void onPostExecute(HashMap<String, Pair<List<Level>, List<Game>> > levels){
                super.onPostExecute(levels);

                mathsLevels = levels;
                initView(); // When done, display the view to the user
            }

        }

        CollectLevels cl = new CollectLevels();
        cl.execute();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout levelSelectArea = findViewById(R.id.mathematics_level_select_area);

        // For each activities... (addition / substraction / etc...)
        for (Map.Entry<String, Pair<List<Level>, List<Game>>> entry : mathsLevels.entrySet()) {
            // Inflate a component level activity with a title and an area
            LinearLayout levelActivity = (LinearLayout) inflater.inflate(R.layout.component_level_activity, null);

            TextView levelActivityName = levelActivity.findViewById(R.id.level_activity_name);
            levelActivityName.setText(entry.getKey());

            LinearLayout levelActivityArea = levelActivity.findViewById(R.id.level_activity_layout);

            // For each activity level... (addition lvl 1, addition lvl 2, etc...)
            for (Level level: entry.getValue().getItem1()) {
                // Inflate a component level button with a button and a score display
                LinearLayout levelButton = (LinearLayout) inflater.inflate(R.layout.component_level_button, null);

                Button levelButtonButton = levelButton.findViewById(R.id.level_button_button);
                levelButtonButton.setText(String.valueOf(level.getDifficulty()));

                levelButtonButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // When we click the button, it start the corresponding level
                        Intent intent = new Intent(MathematicsLevelSelect.this, OperationActivity.class);
                        intent.putExtra(OperationActivity.DIFFICULTY_KEY, level.getDifficulty());
                        intent.putExtra(OperationActivity.OPERATION_KEY, level.getGameMode());
                        intent.putExtra(OperationActivity.LEVEL_ID_KEY, level.getId());

                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                    }
                });

                RatingBar levelButtonScore = levelButton.findViewById(R.id.level_button_score);
                levelButtonScore.setRating((float) 0); // Set default rating

                // From all user previous games, find the game corresponding to this level
                for (Game game: entry.getValue().getItem2()) {
                    if (game.getLevelId() == level.getId()) {
                        levelButtonScore.setRating(game.getScore()); // Overwrite default rating
                    }
                }

                levelActivityArea.addView(levelButton);
            }

            levelSelectArea.addView(levelActivity);
        }
    }

    public void switchUser(View v) {
        //Create intent
        Intent main = new Intent(MathematicsLevelSelect.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}