package com.example.culturalwordsgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int currentIndex = -1;
    private ImageView culturalImgaeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLangSetup();
        setContentView(R.layout.activity_main);
        culturalImgaeView = findViewById(R.id.image_view_question);
    }

    public void getNewQuestion(View view) {
        Random random = new Random();
        currentIndex = random.nextInt(Constants.arrayCulturalImages.length);
        showImage();
    }

    private void showImage(){
        Drawable imageDrawable = ContextCompat.getDrawable(this, Constants.arrayCulturalImages[currentIndex]);
        culturalImgaeView.setImageDrawable(imageDrawable);

    }

    public void showAnswer(View view){
        if (currentIndex == -1) return;
        Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
        intent.putExtra(Constants.CURRENT_INDEX, currentIndex);
        startActivity(intent);
    }

    public void shareQuestion(View view){
        if (currentIndex == -1 ) return;
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra(Constants.CURRENT_INDEX, currentIndex);
        startActivity(intent);
    }

    public void changeLanguage(View view){
        showLanguageDialog();
    }

    private void showLanguageDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.Languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        saveLanguage(language);
                        LocaleHelper.setLocal(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }

    private void saveLanguage(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SAVED_LANG, lang);
        editor.apply();
    }

    private void getLangSetup(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
        String appLang = sharedPreferences.getString(Constants.SAVED_LANG, "");
        if(!appLang.equals(""))
            LocaleHelper.setLocal(this, appLang);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.BUNDLE_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentIndex = savedInstanceState.getInt(Constants.BUNDLE_CURRENT_INDEX);
        if (currentIndex != -1) {
            showImage();
        }
    }
}