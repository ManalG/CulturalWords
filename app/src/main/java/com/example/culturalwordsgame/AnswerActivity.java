package com.example.culturalwordsgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;

public class AnswerActivity extends AppCompatActivity {

    private static String[] arrayAnswers;
    private static String[] arrayAnswerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        arrayAnswers = getResources().getStringArray(R.array.answers);
        arrayAnswerDescription = getResources().getStringArray(R.array.answer_description);

        int index = getIntent().getExtras().getInt(Constants.CURRENT_INDEX);
        TextView textViewAnswer = findViewById(R.id.text_view_answer);
        textViewAnswer.setText(arrayAnswers[index] + ": " + arrayAnswerDescription[index] + ".");
    }

    public void getReturnAction(View view) {
        finish();
    }
}