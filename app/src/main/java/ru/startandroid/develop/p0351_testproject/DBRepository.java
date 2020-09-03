package ru.startandroid.develop.p0351_testproject;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

// class in through which data can be accessed
// класс нужен для ассинхронного выполнения методов
// базы данных
public class DBRepository {
    private DataBaseDAO dataBaseDAO;
    private LiveData<List<UserEntity>> allData;

    // constructor where we assign the variable
    // application is a subclass of context
    public DBRepository(Application application){
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