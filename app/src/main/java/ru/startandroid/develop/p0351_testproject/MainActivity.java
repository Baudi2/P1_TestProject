package ru.startandroid.develop.p0351_testproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

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
                // initializing Retrofit with base URL
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://reqres.in/")
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build();
                // an interface that is used for retrofit to hold rest of the url
                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

                // the variable that is needed in order to get the parsed data form JSON
                Call<Post> call = jsonPlaceHolder.getPost();

                // extracting data from the JSON file
                call.enqueue(new Callback<Post>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        // response if the call wasn't successful
                        if(!response.isSuccessful()){
                            return;
                        }// if
                        Post posts = response.body();
                        assert posts != null;

                        // заполняем строки для отправки базу данных
                        int id = posts.data.getId();
                        String email = posts.data.getEmail();
                        String firstName = posts.data.getFirst_name();
                        String lastName = posts.data.getLast_name();
                        String avatar = posts.data.getAvatar();

                        String company = posts.ad.getCompany();
                        String url = posts.ad.getUrl();
                        String text = posts.ad.getText();

                        // указываем и отправляем строки в бд
                        UserEntity userEntity = new UserEntity(id, email, firstName, lastName, avatar, company, url, text);
                        viewModelData.insert(userEntity);
                    }// onResponse
                    @Override
                    // a failure method to show the error
                    public void onFailure(Call<Post> call, Throwable t) {
                        t.printStackTrace();
                    }// onFailure
                });// enqueue
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
