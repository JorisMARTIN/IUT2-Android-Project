package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Level.LevelDAO;
import fr.nezanmartin.lecoledesloustics.Database.User.User;

public class LevelSelect extends AppCompatActivity {

    private DatabaseClient database;
    HashMap<String, List<Level>> allLevels;

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
        class CollectLevels extends AsyncTask<Void, Void, HashMap<String, List<Level>>> {

            @Override
            protected HashMap<String, List<Level>> doInBackground(Void... voids) {
                LevelDAO dao = database.getAppDatabase().levelDAO();

                // getting users
                List<String> activities = dao.getActivities();

                HashMap<String, List<Level>> levels = new HashMap<>();

                for (String activity: activities) {
                    levels.put(activity, dao.getActivityLevels(activity));
                }

                return levels;
            }

            @Override
            protected void onPostExecute(HashMap<String, List<Level>> levels){
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

        for (Map.Entry<String, List<Level>> entry: allLevels.entrySet()) {
            LinearLayout levelActivity = (LinearLayout) inflater.inflate(R.layout.component_level_activity, null);

            TextView levelActivityName = levelActivity.findViewById(R.id.level_activity_name);
            levelActivityName.setText(entry.getKey());

            LinearLayout levelActivityArea = levelActivity.findViewById(R.id.level_activity_layout);

            for (Level level: entry.getValue()) {
                LinearLayout levelButton = (LinearLayout) inflater.inflate(R.layout.component_level_button, null);
                Button levelButtonButton = levelButton.findViewById(R.id.level_button_button);
                levelButtonButton.setText(String.valueOf(level.getDifficulty()));

                levelActivityArea.addView(levelButton);
            }

            levelSelectArea.addView(levelActivity);
        }
    }
}
