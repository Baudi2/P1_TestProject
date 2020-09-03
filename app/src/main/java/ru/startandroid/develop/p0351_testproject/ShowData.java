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
        viewModelData.getAllData().observe(this, userEntities -> {

            // достаем данные из бд через dataBases
            if(!userEntities.isEmpty()){
                for(int i = 0, size = userEntities.size(); i < size; i++){
                    String url = userEntities.get(i).getAvatar();

                    tvText.setText("Id: " + userEntities.get(i).getId() + "\n" + "Email: " + userEntities.get(i).getEmail() + "\n" +
                            "First name: " + userEntities.get(i).getFirst_name() + "\n" + "Last name: " + userEntities.get(i).getLast_name() +
                            "\n" + "Avatar: " + "\n");

                    Glide.with(ShowData.this).load(url).into(imageGlide);

                    tvText2.setText("Company: " + userEntities.get(i).getCompany() + "\n" +
                            "Url: " + userEntities.get(i).getUrl() + "\n" +
                            "Text: " + userEntities.get(i).getText());
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