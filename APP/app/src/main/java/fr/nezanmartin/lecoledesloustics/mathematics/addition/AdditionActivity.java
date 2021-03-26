package fr.nezanmartin.lecoledesloustics.mathematics.addition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Random;

import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.mathematics.ResultActivity;
import fr.nezanmartin.lecoledesloustics.mathematics.addition.model.Addition;

public class AdditionActivity extends AppCompatActivity implements View.OnClickListener{

    //DATA
    int difficulty = 1; //TODO: getDifficulty from previous activity

    int currentAdditionID;
    LinkedHashMap<Addition, Integer> results;

    //VIEW
    LinearLayout additonContainner;

    TextView additionID, question;
    EditText answer;

    Button previousButton, nextButton, validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        additonContainner = findViewById(R.id.addition_container);

        additionID = findViewById(R.id.addition_id);

        question = findViewById(R.id.addition_question);
        answer = findViewById(R.id.addition_answer);

        previousButton = findViewById(R.id.addition_previous_button);
        nextButton = findViewById(R.id.addition_next_button);
        validate = findViewById(R.id.addition_confirm);

        results = new LinkedHashMap<>();

        initAdditions();

        currentAdditionID = 0;
        updateDisplay();

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        validate.setOnClickListener(this);

    }

    private void initAdditions() {
        Random random = new Random();
        Addition addition;
        int operande1, operande2;


        for(int id=0; id<10; id++){

            if(difficulty == 1){
                operande1 = random.nextInt(9-1) + 1;
                operande2 = random.nextInt(9-1) + 1;
            }else if(difficulty == 2){
                operande1 = random.nextInt(9-1) + 1;
                operande2 = random.nextInt(99-10) + 10;
            }else{
                operande1 = random.nextInt(99-10) + 10;
                operande2 = random.nextInt(99-10) + 10;
            }

            addition = new Addition(operande1, operande2);

            //Hash map Addition object, addition answer
            results.put(addition, -1);
        }
    }

    @Override
    public void onClick(View v) {

        Addition currentAddition;
        if(!TextUtils.isEmpty(answer.getText())){
            currentAddition = getAdditionInMapByIndex(currentAdditionID);
            results.put(currentAddition, Integer.valueOf(answer.getText().toString()));
        }

        if(v == previousButton){
            if(currentAdditionID >= 1) currentAdditionID--;
        }else if(v == nextButton){
            if(currentAdditionID <= 8) currentAdditionID++;
        }
        updateDisplay();

        if(v == validate){
            checkWin();
        }

    }


    /* UTILS METHODS */

    private Addition getAdditionInMapByIndex(int index){
        return (Addition) this.results.keySet().toArray()[index];
    }

    private String getAdditionQuestionById(int id){
        return getAdditionInMapByIndex(id).getOperande1() + " + " + getAdditionInMapByIndex(id).getOperande2() + " = ";
    }

    private void updateDisplay(){
        additionID.setText((currentAdditionID+1)  + " /10");
        question.setText(getAdditionQuestionById(currentAdditionID));
        if(results.get(getAdditionInMapByIndex(currentAdditionID)) == -1){
            answer.setText("");
            answer.setHint("?");
        }else{
            answer.setText(results.get(getAdditionInMapByIndex(currentAdditionID)).toString());
        }

        if(currentAdditionID == 9){
            validate.setVisibility(View.VISIBLE);
        }else{
            validate.setVisibility(View.INVISIBLE);
        }
    }

    private void checkWin(){
        int goodAnswer = 0;
        for(Addition addition : results.keySet()){
            if(addition.getResultat() == results.get(addition)) goodAnswer++;
        }

        Intent intent = new Intent(AdditionActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.SCORE_KEY, goodAnswer);

        startActivity(intent);
    }

}