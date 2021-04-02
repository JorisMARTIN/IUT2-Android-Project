package fr.nezanmartin.lecoledesloustics.mathematics;

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

import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.mathematics.model.ListOperation;
import fr.nezanmartin.lecoledesloustics.mathematics.model.Operation;
import fr.nezanmartin.lecoledesloustics.mathematics.model.Operations;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String OPERATION_KEY = "operation_key";
    public static final String DIFFICULTY_KEY = "difficulty_key";

    //DATA
    int difficulty;
    Operations operation;

    ListOperation operations;

    int currentOperationID;
    LinkedHashMap<Operation, Integer> results;

    //VIEW
    LinearLayout operationContainner;

    TextView operationTitle, operationID, question;
    EditText answer;

    Button previousButton, nextButton, validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        /* Initialise all view */
        operationContainner = findViewById(R.id.operation_container);

        operationTitle = findViewById(R.id.operation_title);
        operationID = findViewById(R.id.operation_id);

        question = findViewById(R.id.operation_question);
        answer = findViewById(R.id.operation_answer);

        previousButton = findViewById(R.id.operation_previous_button);
        nextButton = findViewById(R.id.operation_next_button);
        validate = findViewById(R.id.operation_confirm);

        /* Initialise all datas */
        difficulty = getIntent().getIntExtra(DIFFICULTY_KEY, 1);
        operation = Operations.getOperationFromName(getIntent().getStringExtra(OPERATION_KEY));

        operations = new ListOperation(difficulty, operation);

        operationTitle.setText(operations.getOperation().getMessage());

        results = new LinkedHashMap<>();

        // Fill the result map whith the operation
        for(Operation op : this.operations.getOperations()){
            results.put(op, -1);
        }

        currentOperationID = 0;
        updateDisplay();

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        validate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Operation currentOperation;
        if(!TextUtils.isEmpty(answer.getText())){
            currentOperation = getOperationInMapByIndex(currentOperationID);
            results.put(currentOperation, Integer.valueOf(answer.getText().toString()));
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

    private Operation getOperationInMapByIndex(int index){
        return (Operation) this.results.keySet().toArray()[index];
    }

    private String getOperationQuestionById(int id){
        return getOperationInMapByIndex(id).getOperand1() + " " + getOperationInMapByIndex(id).getOperationCharacter() + " " + getOperationInMapByIndex(id).getOperand2() + " = ";
    }

    private void updateDisplay(){
        operationID.setText((currentOperationID +1)  + " /10");
        question.setText(getOperationQuestionById(currentOperationID));
        if(results.get(getOperationInMapByIndex(currentOperationID)) == -1){
            answer.setText("");
            answer.setHint("?");
        }else{
            answer.setText(results.get(getOperationInMapByIndex(currentOperationID)).toString());
        }

        validate.setVisibility((currentOperationID == 9) ? View.VISIBLE : View.INVISIBLE);
        nextButton.setVisibility((currentOperationID == 9 ? View.INVISIBLE : View.VISIBLE));
        previousButton.setVisibility((currentOperationID == 0 ? View.INVISIBLE : View.VISIBLE));

    }

    private void checkWin(){
        int goodAnswer = 0;
        for(Operation op : results.keySet()){
            if(op.getResult() == results.get(op)) goodAnswer++;
        }

        Intent intent = new Intent(OperationActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.SCORE_KEY, goodAnswer);

        startActivity(intent);
    }

}