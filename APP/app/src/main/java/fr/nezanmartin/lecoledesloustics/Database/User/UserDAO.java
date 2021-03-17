package fr.nezanmartin.lecoledesloustics.Database.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = (:id) LIMIT 1")
    User getUserById(int id);

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user WHERE current_user = 1 LIMIT 1")
    User getCurrentUser();

}
