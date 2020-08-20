package ru.startandroid.develop.p0351_testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LOG_TAG = "myLogs";

    RequestQueue mQueue;

    Button btnDown, btnShow, btnClear;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDown = findViewById(R.id.btnDown);
        btnDown.setOnClickListener(this);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        mQueue = Volley.newRequestQueue(this);

        dbHelper = new DBHelper (this);
    }// onCreate

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        // объявляем Intent
        Intent intent;
        switch (v.getId()){
            case R.id.btnDown:
                // ссылка с которой получаем JSON object
                String url = "https://reqres.in/api/users/2";

                // Declaring the method to parse JSON object
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // объявляем элементы базы данных чтобы сохранить в ней распарсенный JSON object
                                SQLiteDatabase database = dbHelper.getWritableDatabase();
                                ContentValues contentValues = new ContentValues();
                                try {
                                    JSONObject data = response.getJSONObject("data");
                                    int id = data.getInt("id");
                                    String first_name = data.getString("first_name");
                                    String email = data.getString("email");
                                    String last_name = data.getString("last_name");
                                    String avatar = data.getString("avatar");

                                    JSONObject ad = response.getJSONObject("ad");
                                    String company = ad.getString("company");
                                    String url = ad.getString("url");
                                    String text = ad.getString("text");

                                    contentValues.put(DBHelper.KEY_ID,id);
                                    contentValues.put(DBHelper.KEY_EMAIL,email);
                                    contentValues.put(DBHelper.KEY_FIRST_NAME,first_name);
                                    contentValues.put(DBHelper.KEY_LAST_NAME,last_name);
                                    contentValues.put(DBHelper.KEY_AVATAR,avatar);

                                    contentValues.put(DBHelper.KEY_COMPANY,company);
                                    contentValues.put(DBHelper.KEY_URL,url);
                                    contentValues.put(DBHelper.KEY_TEXT,text);

                                    long rowID = database.insert(DBHelper.THIS_TABLE, null, contentValues);
                                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                                    dbHelper.close();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }// catch
                            }// onResponse
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }// onErrorResponse
                }); // Response.ErrorListener()
                mQueue.add(request);
                break;

            case R.id.btnShow:
                intent = new Intent(this, ShowData.class);
                startActivity(intent);
                break;

            // очищаем базу данных
            case R.id.btnClear:
                SQLiteDatabase databasel = dbHelper.getWritableDatabase();
                Log.d(LOG_TAG, "--- Clear myTable: ---");
                int clearCount = databasel.delete(DBHelper.THIS_TABLE, null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }// switch
        dbHelper.close();
    }// Onclick
}// class
//