package fr.nezanmartin.lecoledesloustics.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.nezanmartin.lecoledesloustics.Database.User.User;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

}