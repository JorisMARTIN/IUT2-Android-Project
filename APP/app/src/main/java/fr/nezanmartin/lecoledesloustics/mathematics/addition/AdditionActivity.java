package fr.nezanmartin.lecoledesloustics.mathematics.addition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.nezanmartin.lecoledesloustics.R;
import fr.nezanmartin.lecoledesloustics.mathematics.addition.model.Addition;

public class AdditionActivity extends AppCompatActivity {

    //DATA
    int difficulty = 1; //TODO: getDifficulty from previous activity
    HashMap<Addition, EditText> results;

    //VIEW
    LinearLayout additonContainner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        additonContainner = findViewById(R.id.addition_container);

        results = new HashMap<>();

        initAdditions();
    }

    private void initAdditions() {
        Random random = new Random();
        Addition addition;
        int operande1, operande2;


        for(int i=0; i<10; i++){
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

            LinearLayout globalLayout = new LinearLayout(this);
            globalLayout.setOrientation(LinearLayout.HORIZONTAL);
            globalLayout.setGravity(Gravity.CENTER);

            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_addition, null);

            TextView text = (TextView) layout.findViewById(R.id.addition_template_question);
            text.setText(addition.getOperande1() + " + " + addition.getOperande2() + " = ");

            EditText res = (EditText) layout.findViewById(R.id.addition_template_answer);
            res.setHint("?");


            // Add to answer map
            results.put(addition, res);

            globalLayout.addView(layout);

            globalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            additonContainner.addView(globalLayout);

            //TODO: 1 Addition per page
        }
    }
}