package ru.startandroid.develop.p0351_testproject;
import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

// class in through which data can be accessed
// класс нужен для ассинхронного выполнения методов
// базы данных
public class Repository {
    private DataBaseDAO dataBaseDAO;
    private LiveData<List<UserEntity>> allData;

    // constructor where we assign the variable
    // application is a subclass of context
    public Repository(Application application){
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance(application);
        dataBaseDAO = dataBaseHandler.dataBaseDAO();
        // method that was created in DAO interface
        allData = dataBaseDAO.getAll();
    }// constructor

    // we have to do this operations in AsyncTask because
    // Room doesn't allow to execute method in main thread

    public void insert(UserEntity userEntity){
        new InsertDataAsyncTask(dataBaseDAO).execute(userEntity);
    }// insert

    public void deleteAllData(){
        new DeleteAllDataAsyncTask(dataBaseDAO).execute();
    }// deleteAllData

    public LiveData<List<UserEntity>> getAllData() {
        return allData;
    }// getAllNotes


    public void fetchData(){
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

                int id = posts.data.getId();
                String email = posts.data.getEmail();
                String firstName = posts.data.getFirst_name();
                String lastName = posts.data.getLast_name();
                String avatar = posts.data.getAvatar();

                String company = posts.ad.getCompany();
                String url = posts.ad.getUrl();
                String text = posts.ad.getText();

                UserEntity userEntity = new UserEntity(id, email, firstName, lastName, avatar, company, url, text);
                insert(userEntity);

            }// onResponse
            @Override
            // a failure method to show the error
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
            }// onFailure
        });// enqueue
    }// fetchData



    // Creating nested AsyncTask classes for each method to perform them on a background thread

    private static class InsertDataAsyncTask extends AsyncTask<UserEntity, Void, Void>{
        // needs this variable to make DB operations
        private DataBaseDAO dataBaseDAO;

        // because AsyncTask is static we have to create a constructor
        // to access DataBaseDAO
        private InsertDataAsyncTask(DataBaseDAO dataBaseDAO){
            this.dataBaseDAO = dataBaseDAO;
        }// constructor

        @Override
        protected Void doInBackground(UserEntity... userEntities) {
            dataBaseDAO.insert(userEntities[0]);
            return null;
        }// doInBackground
    }// AsyncTask

    // the same procedures are done here as in the previous Async Task class
    // just for different DB methods

    private static class DeleteAllDataAsyncTask extends AsyncTask<Void, Void, Void>{
        private DataBaseDAO dataBaseDAO;

        private DeleteAllDataAsyncTask(DataBaseDAO dataBaseDAO){
            this.dataBaseDAO = dataBaseDAO;
        }// constructor

        @Override
        protected Void doInBackground(Void... voids) {
            dataBaseDAO.deleteAllData();
            return null;
        }// doInBackground
    }// AsyncTask
}// class