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

        class CollectLevels extends AsyncTask<Void, Void, HashMap<String, Pair<List<Level>, List<Game>>>> {

            @Override
            protected HashMap<String, Pair<List<Level>, List<Game>> > doInBackground(Void... voids) {
                LevelDAO levelDAO = database.getAppDatabase().levelDAO();
                GameDAO gameDAO = database.getAppDatabase().gameDAO();

                List<Level> activities = levelDAO.getAllLevels();

                HashMap<String, Pair<List<Level>, List<Game>>> levels = new HashMap<>();

                for (Level activity: activities) {
                    if(!activity.getGameMode().equalsIgnoreCase("géographie")){
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
                initView();
            }

        }

        CollectLevels cl = new CollectLevels();
        cl.execute();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout levelSelectArea = findViewById(R.id.mathematics_level_select_area);

        for (Map.Entry<String, Pair<List<Level>, List<Game>>> entry : mathsLevels.entrySet()) {
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

                        Intent intent = new Intent(MathematicsLevelSelect.this, OperationActivity.class);
                        intent.putExtra(OperationActivity.DIFFICULTY_KEY, level.getDifficulty());
                        intent.putExtra(OperationActivity.OPERATION_KEY, level.getGameMode());
                        intent.putExtra(OperationActivity.LEVEL_ID_KEY, level.getId());

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
        Intent main = new Intent(MathematicsLevelSelect.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}