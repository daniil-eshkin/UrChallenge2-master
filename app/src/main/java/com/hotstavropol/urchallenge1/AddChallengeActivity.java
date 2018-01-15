package com.hotstavropol.urchallenge1;

/**
 * Created by maximgran on 15.01.2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_challenge_button);
        Intent intent = getIntent();
        Button button = findViewById(R.id.add_challenge_confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView NameTextView = findViewById(R.id.add_challenge_name_editText);
                TextView ChallengeTextView = findViewById(R.id.add_challenge_description_editText);
                String challengeName = NameTextView.getText().toString();
                String challengeDescription = ChallengeTextView.getText().toString();
                // TODO db.add(gettext);
                boolean f = true;
                String msg;
                if (f) msg = "Добавлено";
                else msg = "Что-то пошло не так. Предагаю обвинить разработчиков";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

