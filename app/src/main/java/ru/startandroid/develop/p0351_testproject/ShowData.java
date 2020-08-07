package ru.startandroid.develop.p0351_testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowData extends AppCompatActivity {

    TextView tvText, tvText_2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        DBHelper dbHelper;
        dbHelper = new DBHelper (this);

        tvText = findViewById(R.id.tvText);
        tvText_2 = findViewById(R.id.tvText_2);
        imageView = findViewById(R.id.imageView);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.THIS_TABLE, null, null, null, null,null, null);

        if(cursor.moveToFirst()) {
            int id_Index = cursor.getColumnIndex(DBHelper.KEY_ID);
            int email_Index = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
            int first_name_Index = cursor.getColumnIndex(DBHelper.KEY_FIRST_NAME);
            int last_name_Index = cursor.getColumnIndex(DBHelper.KEY_LAST_NAME);
            int avatar_Index = cursor.getColumnIndex(DBHelper.KEY_AVATAR);
            int company_Index = cursor.getColumnIndex(DBHelper.KEY_COMPANY);
            int url_Index = cursor.getColumnIndex(DBHelper.KEY_URL);
            int text_Index = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            String url = cursor.getString(avatar_Index);

            do {
                Log.d(MainActivity.LOG_TAG,  cursor.getString(id_Index) + cursor.getString(email_Index) + cursor.getString(first_name_Index)+
                        cursor.getString(last_name_Index) + cursor.getString(avatar_Index) + cursor.getString(company_Index) + cursor.getString(url_Index)
                + cursor.getString(text_Index));
                // собираем строку для отображение данных из базы
                String sent = "id: " + cursor.getString(id_Index) + "\n" + "Email: " + cursor.getString(email_Index) +
                        "\n" +  "First name: " + cursor.getString(first_name_Index) + "\n" +  "Last name: " +
                        cursor.getString(last_name_Index) + "\n" +  "Avatar: " + "\n";
                tvText.setText(sent);
                Glide.with(this).load(url).into(imageView);
                String sent2 = "Company: " + cursor.getString(company_Index) + "\n" + "Url: " + cursor.getString(url_Index) + "\n" +
                      "Text: " + cursor.getString(text_Index);
                tvText_2.setText(sent2);
            } while (cursor.moveToNext());
        }// if
        // если в таблице нет данных
        else {
            Log.d(MainActivity.LOG_TAG, "0 rows");
            String diff = "Database is empty";
            tvText.setText(diff);
        }// else
        cursor.close();

        // добавление кнопки "назад" в actionbar
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }// onCreate

    // реализация метода для кнопки "назад"
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }// onSupportNavigateUp
}// class