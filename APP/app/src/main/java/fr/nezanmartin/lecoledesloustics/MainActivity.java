package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.User.User;

public class MainActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient database;
    List<User> allUsers;

    // VIEW
    ScrollView profilesScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instanciate database
        database = DatabaseClient.getInstance(getApplicationContext());

        // Instanciate list for users
        allUsers = new ArrayList<User>();

        setContentView(R.layout.activity_main);

        // Initialise view
        this.profilesScroll = findViewById(R.id.activity_main_profiles_scroll);

        //Get all users from database stock them in allUsers ArrayList
        getAllUsers();

        initView();
    }

    private void getAllUsers() {

        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
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

            }

        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        CollectUsers cu = new CollectUsers();
        cu.execute();
    }

    private void initView() {
        for(User user : allUsers){

            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.HORIZONTAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_user_profil, null);

            ImageView img =  layout.findViewById(R.id.profile_template_img);

            TextView name = (TextView) layout.findViewById(R.id.profile_template_name);
            name.setText(user.getFistname() + user.getName());

            globalLayout.addView(layout);

            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            profilesScroll.addView(globalLayout);
        }
    }
}