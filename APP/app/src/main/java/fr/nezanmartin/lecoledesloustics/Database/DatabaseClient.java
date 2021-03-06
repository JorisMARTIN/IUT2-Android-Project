package fr.nezanmartin.lecoledesloustics.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // Remplissage de la bdd avec addCallback pour les données "test" et createFromAsset pour
        // copier la bdd d'initialisation
        appDatabase = Room
                .databaseBuilder(context, AppDatabase.class, "EcoleDesLousticsDB")
                .addCallback(roomDatabaseCallback)
                .createFromAsset("base_levels.db")
                .build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            db.execSQL("INSERT INTO game (user_id, level_id, score) VALUES (0, 0, 3.0)");

            // db.execSQL("INSERT INTO user (name, firstname, current_user) VALUES(\"Bob\", \"loustic\", 0);");
            // db.execSQL("INSERT INTO user (name, firstname, current_user) VALUES(\"Marie\", \"lousticette\", 0);");
        }
    };
}