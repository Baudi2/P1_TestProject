package ru.startandroid.develop.p0351_testproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewModelData viewModelData;

    Button button_one, button_two, button_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_one = findViewById(R.id.button_one);
        button_one.setOnClickListener(this);

        button_two = findViewById(R.id.button_two);
        button_two.setOnClickListener(this);

        button_three = findViewById(R.id.button_three);
        button_three.setOnClickListener(this);

        // объявляем и инициализируем viewModel для доступа к методу insert в базе данных
        // в качестве context указываем application
        // объявляем observer
        viewModelData = new ViewModelProvider
                (this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ViewModelData.class);
        viewModelData.getAllData().observe(this, userEntities -> {
        });
    }// onCreate

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_one:
                viewModelData.fetchData();
                break;
            case R.id.button_two:
                viewModelData.deleteAllData();
                break;
            case R.id.button_three:
                intent = new Intent(this, ShowData.class);
                startActivity(intent);
                break;
        }// switch
    }// onClick
}// class