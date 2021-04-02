package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.User.User;

public class MainActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient database;
    List<User> allUsers;

    // VIEW
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instanciate database
        database = DatabaseClient.getInstance(getApplicationContext());

        // Instanciate list for users
        allUsers = new ArrayList<User>();

        setContentView(R.layout.activity_main);

        // Initialise view
        this.container = findViewById(R.id.activity_main_profiles_scroll);


        //Get all users from database stock them in allUsers ArrayList
        getAllUsers();

    }

    private void getAllUsers() {

        /**
         * Création d'une classe asynchrone pour récuperer les utilisateurs
         */
        class CollectUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {

                // getting users
                List<User> users = database.getAppDatabase()
                        .userDAO()
                        .getAll();

                return users;
            }

            @Override
            protected void onPostExecute(List<User> users){
                super.onPostExecute(users);

                allUsers = users;
                // Display users on scroll view
                initView();
            }

        }

        CollectUsers cu = new CollectUsers();
        cu.execute();
    }

    private void initView() {

        for(User user : allUsers){

            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.HORIZONTAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_user_profil, null);

            TextView name = (TextView) layout.findViewById(R.id.profile_template_name);
            name.setText(user.getFirstname() + " " + user.getName());
            globalLayout.addView(layout);
            // Add onClickListener
            globalLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    setCurrentUser(user);
                }
            });
            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            container.addView(globalLayout);
        }
    }


    /**
     * Set current_user attribut in DB to false to the old current user and to true to the actual current user
     * Then create intent for start to use the app
     *
     * @param currentUser the current user
     */
    private void setCurrentUser(User currentUser){

        // Set current user in DB + Create intent
        currentUser.setCurrentUser(true);

        class SaveCurrentUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // Check if there was an old current user
                // If yes set the attribut to false
                User oldCurrentUser = database.getAppDatabase().userDAO().getCurrentUser();
                if(oldCurrentUser != null){
                    oldCurrentUser.setCurrentUser(false);
                    database.getAppDatabase().userDAO().update(oldCurrentUser);
                }

                // Update the current user
                database.getAppDatabase().userDAO().update(currentUser);

                // Return the actual current user
                return database.getAppDatabase().userDAO().getCurrentUser();
            }

            @Override
            protected void onPostExecute(User user){
                super.onPostExecute(user);

                //Create intent
                Intent levelSelect = new Intent(MainActivity.this, LevelSelect.class);
                startActivity(levelSelect);
            }

        }

        SaveCurrentUser save = new SaveCurrentUser();
        save.execute();
    }

    public void newUser(View view) {
        Intent newUser = new Intent(MainActivity.this, NewProfileActivity.class);
        startActivity(newUser);
    }
}