package ru.startandroid.develop.p0351_testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowData extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        // добавление кнопки "назад" в actionbar
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        tvText = findViewById(R.id.tvText);

        // получаем отправелнный intent и назначаем его содержимое для textView
        Intent intent = getIntent();

        String fsent = intent.getStringExtra("fsent");

        tvText.setText(fsent);
    }// onCreate

    // реализация метода для кнопки "назад"
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }// onSupportNavigateUp
}// class