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
import fr.nezanmartin.lecoledesloustics.mathematics.operation.Addition;
import fr.nezanmartin.lecoledesloustics.mathematics.operation.Operation;

public class OperationActivity<T extends Operation> extends AppCompatActivity implements View.OnClickListener{

    //DATA
    int difficulty = 1; //TODO: getDifficulty from previous activity

    int currentOperationID;
    LinkedHashMap<T, Integer> results;

    //VIEW
    LinearLayout operationContainner;

    TextView operationID, question;
    EditText answer;

    Button previousButton, nextButton, validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        operationContainner = findViewById(R.id.addition_container);

        operationID = findViewById(R.id.addition_id);

        question = findViewById(R.id.addition_question);
        answer = findViewById(R.id.addition_answer);

        previousButton = findViewById(R.id.addition_previous_button);
        nextButton = findViewById(R.id.addition_next_button);
        validate = findViewById(R.id.addition_confirm);

        results = new LinkedHashMap<>();

        initOperation();

        currentOperationID = 0;
        updateDisplay();

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        validate.setOnClickListener(this);

    }

    private void initOperation() {
        Random random = new Random();
        T operation;
        int operand1, operand2;


        for(int id=0; id<10; id++){

            if(difficulty == 1){
                operand1 = random.nextInt(9-1) + 1;
                operand2 = random.nextInt(9-1) + 1;
            }else if(difficulty == 2){
                operand1 = random.nextInt(9-1) + 1;
                operand2 = random.nextInt(99-10) + 10;
            }else{
                operand1 = random.nextInt(99-10) + 10;
                operand2 = random.nextInt(99-10) + 10;
            }

            operation = new Operation(operand1, operand2);

            //Hash map Addition object, addition answer
            results.put(operation, -1);
        }
    }

    @Override
    public void onClick(View v) {

        Addition currentAddition;
        if(!TextUtils.isEmpty(answer.getText())){
            currentAddition = getAdditionInMapByIndex(currentOperationID);
            results.put(currentAddition, Integer.valueOf(answer.getText().toString()));
        }

        if(v == previousButton){
            if(currentOperationID >= 1) currentOperationID--;
        }else if(v == nextButton){
            if(currentOperationID <= 8) currentOperationID++;
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
        operationID.setText((currentOperationID +1)  + " /10");
        question.setText(getAdditionQuestionById(currentOperationID));
        if(results.get(getAdditionInMapByIndex(currentOperationID)) == -1){
            answer.setText("");
            answer.setHint("?");
        }else{
            answer.setText(results.get(getAdditionInMapByIndex(currentOperationID)).toString());
        }

        if(currentOperationID == 9){
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

        Intent intent = new Intent(OperationActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.SCORE_KEY, goodAnswer);

        startActivity(intent);
    }

}