package fr.nezanmartin.lecoledesloustics.Exercices.mathematics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;

import fr.nezanmartin.lecoledesloustics.Database.DatabaseClient;
import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;
import fr.nezanmartin.lecoledesloustics.Exercices.ResultActivity;
import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model.ListOperation;
import fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model.Operation;
import fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model.Operations;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String OPERATION_KEY = "operation_key";
    public static final String DIFFICULTY_KEY = "difficulty_key";
    public static final String LEVEL_ID_KEY = "level_id_key";
    private static final int OPERATION_REQUEST = 0;

    //DATA
    private DatabaseClient database;
    boolean isCorrection = false; //Boolean to determine if the activity is in correction mode or not
    int finalScore;
    int difficulty;
    Operations operation;

    ListOperation operations;

    int levelID;
    int currentOperationID;
    LinkedHashMap<Operation, Integer> results;

    //VIEW
    LinearLayout operationContainner;

    TextView operationTitle, operationID, question, advice;
    EditText answer;

    Button previousButton, nextButton, validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        database = DatabaseClient.getInstance(getApplicationContext());

        /* Initialise all view */
        operationContainner = findViewById(R.id.operation_container);

        operationTitle = findViewById(R.id.operation_title);
        operationID = findViewById(R.id.operation_id);

        question = findViewById(R.id.operation_question);
        answer = findViewById(R.id.operation_answer);
        advice = findViewById(R.id.operation_advice);

        previousButton = findViewById(R.id.operation_previous_button);
        nextButton = findViewById(R.id.operation_next_button);
        validate = findViewById(R.id.operation_confirm);

        /* Initialise all datas */
        difficulty = getIntent().getIntExtra(DIFFICULTY_KEY, 1);
        operation = Operations.getOperationFromName(getIntent().getStringExtra(OPERATION_KEY));
        levelID = getIntent().getIntExtra(LEVEL_ID_KEY, 0);

        operations = new ListOperation(difficulty, operation);

        operationTitle.setText(operations.getOperation().getMessage());

        results = new LinkedHashMap<>();

        // Fill the result map whith the operation
        for(Operation op : this.operations.getOperations()){
            results.put(op, -1);
        }

        currentOperationID = 0;
        updateDisplay();

        answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onClick(currentOperationID <= 8 ? nextButton : validate);
                return false;
            }
        });

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

        if(isCorrection){
            advice.setText("Attention : Tu dois aller au bout de la correction pour que ton premier score soit sauvegardé !");
        }

        operationID.setText((currentOperationID +1)  + " /10");
        question.setText(getOperationQuestionById(currentOperationID));
        if(results.get(getOperationInMapByIndex(currentOperationID)) == -1){
            answer.setText("");
            answer.setHint("?");
        }else{
            answer.setText(results.get(getOperationInMapByIndex(currentOperationID)).toString());
            if(isCorrection && results.get(getOperationInMapByIndex(currentOperationID)) != getOperationInMapByIndex(currentOperationID).getResult()){
                answer.setTextColor(0xFFFF0000);
            }else{
                answer.setTextColor(0xFF000000);
            }
        }

        validate.setVisibility((currentOperationID == 9) ? View.VISIBLE : View.INVISIBLE);
        nextButton.setVisibility((currentOperationID == 9 ? View.INVISIBLE : View.VISIBLE));
        previousButton.setVisibility((currentOperationID == 0 ? View.INVISIBLE : View.VISIBLE));

    }

    private void checkWin() {
        int goodAnswer = 0;
        for (Operation op : results.keySet()) {
            if (op.getResult() == results.get(op)) goodAnswer++;
        }

        if(!isCorrection) finalScore = goodAnswer;

        Intent intent = new Intent(OperationActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.SCORE_KEY, goodAnswer);

        startActivityForResult(intent, OPERATION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPERATION_REQUEST){
            if (resultCode == RESULT_OK) {
                /**
                 * Stocker le score dans la BDD
                 */
                class SaveScore extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        UserDAO userDAO = database.getAppDatabase().userDAO();
                        GameDAO gameDAO = database.getAppDatabase().gameDAO();

                        int userID = userDAO.getCurrentUser().getId();
                        Game game = gameDAO.getLevelGame(userID, levelID);
                        float score = (float) (finalScore * 0.3);


                        if (game == null) {
                            game = new Game();
                            game.setUserId(userID);
                            game.setLevelId(levelID);
                            game.setScore(score);
                            gameDAO.insert(game);
                        } else {
                            game.setScore(score);
                            gameDAO.update(game);
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void v) {
                        super.onPostExecute(v);
                        finish();
                    }
                }

                SaveScore sc = new SaveScore();
                sc.execute();

            } else {
                isCorrection = true;
                updateDisplay();
            }
        }
    }
}