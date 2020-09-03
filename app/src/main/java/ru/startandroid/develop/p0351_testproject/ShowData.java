package ru.startandroid.develop.p0351_testproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class ShowData extends AppCompatActivity {

    ViewModelData viewModelData;
    private TextView tvText, tvText2;

    private ImageView imageGlide;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        tvText = findViewById(R.id.tvText);
        tvText2 = findViewById(R.id.tvText_2);

        imageGlide = findViewById(R.id.image_view_glide);

        // объявляем и инициализируем viewModel для доступа к методу insert в базе данных
        // в качестве context указываем application
        // объявляем observer
        viewModelData = new ViewModelProvider
                (this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ViewModelData.class);
        viewModelData.getAllData().observe(this, dataBases -> {

            // достаем данные из бд через dataBases
            if(!dataBases.isEmpty()){
                for(int i = 0, size = dataBases.size(); i < size; i++){
                    String url = dataBases.get(i).getAvatar();

                    tvText.setText("Id: " + dataBases.get(i).getId() + "\n" + "Email: " + dataBases.get(i).getEmail() + "\n" +
                            "First name: " + dataBases.get(i).getFirst_name() + "\n" + "Last name: " + dataBases.get(i).getLast_name() +
                            "\n" + "Avatar: " + "\n");

                    Glide.with(ShowData.this).load(url).into(imageGlide);

                    tvText2.setText("Company: " + dataBases.get(i).getCompany() + "\n" +
                            "Url: " + dataBases.get(i).getUrl() + "\n" +
                            "Text: " + dataBases.get(i).getText());
                }// else
            } else {
                tvText.setText("Database is empty");
            }// else
        });// viewModelData

        // добавление кнопки "назад" в actionbar
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }// onCreate

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }// onSupportNavigateUp
}// class