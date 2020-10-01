package com.example.culturalwordsgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ShareActivity extends AppCompatActivity {

    private static int index;
    private EditText editTextShareTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        editTextShareTitle = findViewById(R.id.edit_text_share_title);
        index = getIntent().getExtras().getInt(Constants.CURRENT_INDEX);

        getSavedTitle();
        getSharedImage();
    }

    public void shareQuestionClicked(View view){
        String title = editTextShareTitle.getText().toString();
        saveTitle(title);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://com.example.culturalwordsgame/" + Constants.arrayCulturalImages[index]));
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void getSharedImage(){
        Drawable drawableImage = ContextCompat.getDrawable(this, Constants.arrayCulturalImages[index]);
        ImageView imageView = findViewById(R.id.image_view_question);
        imageView.setImageDrawable(drawableImage);
    }

    private void getSavedTitle(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
        editTextShareTitle.setText(sharedPreferences.getString(Constants.SAVED_TITLE, ""));
    }

    private void saveTitle(String title){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SAVED_TITLE, title);
        editor.apply();
    }
}