package fr.nezanmartin.lecoledesloustics.QuestionsAnswers.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import fr.nezanmartin.lecoledesloustics.Database.Questions.Question;

public class QuestionsAnswersLevel implements Parcelable {

    private ArrayList<Question> questions;
    private int currentQuestionIndex;

    public QuestionsAnswersLevel(ArrayList<Question> allQuestions){

        Collections.shuffle(allQuestions);
        questions = new ArrayList<>();

        for(int i = 0; i < allQuestions.size() || i < 10; i++){
            questions.add(allQuestions.get(i));
        }

        if(questions.isEmpty()){
            System.err.println("Aucunes questions chargées !");
            return;
        }
        currentQuestionIndex = 0;

    }

    public ArrayList<Question> getQuestions(){ return this.questions; }

    public boolean hasNextQuestion(){
        return currentQuestionIndex + 1 == questions.size();
    }

    public Question getCurrentQuestion(){
        return this.questions.get(currentQuestionIndex);
    }

    public void nextQuestion(){
        this.currentQuestionIndex ++;
    }



    /* PARCELABLE IMPLEMENTATION */

    protected QuestionsAnswersLevel(Parcel in) {
    }

    public static final Creator<QuestionsAnswersLevel> CREATOR = new Creator<QuestionsAnswersLevel>() {
        @Override
        public QuestionsAnswersLevel createFromParcel(Parcel in) {
            return new QuestionsAnswersLevel(in);
        }

        @Override
        public QuestionsAnswersLevel[] newArray(int size) {
            return new QuestionsAnswersLevel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
