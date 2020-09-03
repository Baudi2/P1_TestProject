package ru.startandroid.develop.p0351_testproject;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

// класс служит для смен конфигурации приложения
// для этого вместо context используется application
// чтобы при смене конфигурации данные не потерялись
// и не произошли никакие изменения
// и не было утечек памяти
// также класс служит как дополнительный слой абстракции
// между репозиторием и mainActivity
public class ViewModelData extends AndroidViewModel {

    private DBRepository repository;
    private LiveData<List<DataBase>> allData;

    public ViewModelData(@NonNull Application application) {
        super(application);
        repository = new DBRepository(application);
        allData = repository.getAllData();
    }// constructor

    public void insert(DataBase dataBase){
        repository.insert(dataBase);
    }// insert

    public void deleteAllData(){
        repository.deleteAllData();
    }// deleteAllData

    public LiveData<List<DataBase>> getAllData(){
        return allData;
    }// LiveData
}// class
