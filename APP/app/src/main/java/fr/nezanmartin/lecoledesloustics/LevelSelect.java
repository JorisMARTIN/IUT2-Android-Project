package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

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

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Level.LevelDAO;
import fr.nezanmartin.lecoledesloustics.mathematics.OperationActivity;
import fr.nezanmartin.lecoledesloustics.mathematics.model.Operation;
import fr.nezanmartin.lecoledesloustics.mathematics.model.Operations;
import fr.nezanmartin.lecoledesloustics.utils.Pair;

public class LevelSelect extends AppCompatActivity {

    private DatabaseClient database;
    HashMap<String, Pair<List<Level>, List<Game>> > allLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        database = DatabaseClient.getInstance(getApplicationContext());
        getLevels();
    }

    private void getLevels() {

        /**
         * Création d'une classe asynchrone pour récuperer les utilisateurs
         */
        class CollectLevels extends AsyncTask<Void, Void, HashMap<String, Pair<List<Level>, List<Game>>>> {

            @Override
            protected HashMap<String, Pair<List<Level>, List<Game>> > doInBackground(Void... voids) {
                LevelDAO levelDAO = database.getAppDatabase().levelDAO();
                GameDAO gameDAO = database.getAppDatabase().gameDAO();

                // getting users
                List<String> activities = levelDAO.getActivities();

                HashMap<String, Pair<List<Level>, List<Game>>> levels = new HashMap<>();

                for (String activity: activities) {
                    levels.put(
                        activity,
                        new Pair<>(
                            levelDAO.getLevels(activity),
                            gameDAO.getCurrentUserGames()
                        )
                    );
                }

                return levels;
            }

            @Override
            protected void onPostExecute(HashMap<String, Pair<List<Level>, List<Game>> > levels){
                super.onPostExecute(levels);

                allLevels = levels;
                initView();
            }

        }

        CollectLevels cl = new CollectLevels();
        cl.execute();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout levelSelectArea = findViewById(R.id.level_select_area);

        for (Map.Entry<String, Pair<List<Level>, List<Game>>> entry : allLevels.entrySet()) {
            LinearLayout levelActivity = (LinearLayout) inflater.inflate(R.layout.component_level_activity, null);

            TextView levelActivityName = levelActivity.findViewById(R.id.level_activity_name);
            levelActivityName.setText(entry.getKey());

            LinearLayout levelActivityArea = levelActivity.findViewById(R.id.level_activity_layout);

            for (Level level: entry.getValue().getItem1()) {
                LinearLayout levelButton = (LinearLayout) inflater.inflate(R.layout.component_level_button, null);
                Button levelButtonButton = levelButton.findViewById(R.id.level_button_button);
                levelButtonButton.setText(String.valueOf(level.getDifficulty()));

                levelButtonButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(LevelSelect.this, OperationActivity.class);
                        intent.putExtra(OperationActivity.DIFFICULTY_KEY, level.getDifficulty());
                        intent.putExtra(OperationActivity.OPERATION_KEY, level.getGameMode());

                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                    }
                });

                RatingBar levelButtonScore = levelButton.findViewById(R.id.level_button_score);
                levelButtonScore.setRating((float) 0);

                for (Game game: entry.getValue().getItem2()) {
                    if (game.getLevelId() == level.getId()) {
                        levelButtonScore.setRating(game.getScore());
                    }
                }

                levelActivityArea.addView(levelButton);
            }

            levelSelectArea.addView(levelActivity);
        }
    }

    public void switchUser(View v) {
        //Create intent
        Intent main = new Intent(LevelSelect.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}
