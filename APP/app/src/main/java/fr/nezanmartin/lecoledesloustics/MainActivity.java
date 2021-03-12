package fr.nezanmartin.lecoledesloustics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // VIEW
    LinearLayout account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise view
        this.account = findViewById(R.id.adHoc);

        // Listen click event
        this.account.setClickable(true);
        this.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO
                Create intent with save of current user
                 */
            }
        });
    }
}