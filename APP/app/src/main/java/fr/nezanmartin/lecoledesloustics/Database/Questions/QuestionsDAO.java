package fr.nezanmartin.lecoledesloustics.Database.Questions;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QuestionsDAO {

    @Query("SELECT * FROM question")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM question WHERE tag = (:tag)")
    List<Question> getQuestionsByTag(String tag);

    @Query("SELECT DISTINCT tag FROM question")
    List<String> getAllTags();

    @Insert
    void insert(Question question);

    @Insert
    long[] insertAll(Question... questions);

    @Update
    void update(Question question);

}
