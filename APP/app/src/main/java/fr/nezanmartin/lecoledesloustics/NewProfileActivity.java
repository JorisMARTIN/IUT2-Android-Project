package fr.nezanmartin.lecoledesloustics;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.User.User;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;
import fr.nezanmartin.lecoledesloustics.utils.Pair;

public class NewProfileActivity extends AppCompatActivity {

    private DatabaseClient database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        database = DatabaseClient.getInstance(getApplicationContext());
    }

    public void createUser(View view) {
        EditText firstnameInput = findViewById(R.id.new_profile_firstname_input);
        EditText nameInput = findViewById(R.id.new_profile_name_input);

        String firstname = firstnameInput.getText().toString();
        String name = nameInput.getText().toString();

        if (!firstname.equals("") && !name.equals("")) {
            User user = new User();
            user.setFirstname(firstname);
            user.setName(name);
            user.setCurrentUser(false);

            addNewUser(user);
        }
    }

    private void addNewUser(User user) {

        /**
         * Création d'une classe asynchrone pour récuperer les utilisateurs
         */
        class AddNewUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                UserDAO userDAO = database.getAppDatabase().userDAO();
                userDAO.insert(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void v){
                super.onPostExecute(v);

                Intent main = new Intent(NewProfileActivity.this, MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        }

        AddNewUser anu = new AddNewUser();
        anu.execute();
    }
}