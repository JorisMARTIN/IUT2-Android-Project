package fr.nezanmartin.lecoledesloustics.Database.Questions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answers")
    private String answers;

    @ColumnInfo(name = "good_anwer")
    private int good_answer;

    @ColumnInfo(name = "tag")
    private String tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String getAnswers() {
        return answers;
    }

    public String[] getAllAnswers(){
        return getAnswers().replace(" ", "").split(",");
    }

    /**
     *
     * @param answers Partern : "answer1, answer2, ..., answnerN"
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getGood_answer() {
        return good_answer;
    }

    public void setGood_answer(int good_answer) {
        this.good_answer = good_answer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
