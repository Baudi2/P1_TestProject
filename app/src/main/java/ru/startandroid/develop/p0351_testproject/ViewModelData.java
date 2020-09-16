package ru.startandroid.develop.p0351_testproject;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;

import java.util.List;

// класс служит для смен конфигурации приложения
// для этого вместо context используется application
// чтобы при смене конфигурации данные не потерялись
// и не произошли никакие изменения
// и не было утечек памяти
// также класс служит как дополнительный слой абстракции
// между репозиторием и mainActivity
public class ViewModelData extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<UserEntity>> allData;
    private UserEntity userEntity;

    public ViewModelData(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allData = repository.getAllData();
    }// constructor

    public void insert(UserEntity userEntity){
        repository.insert(userEntity);
    }// insert

    public void deleteAllData(){
        repository.deleteAllData();
    }// deleteAllData

    public void fetchData(){
      repository.fetchData();
    }// fetchData

    public LiveData<List<UserEntity>> getAllData(){
        return allData;
    }// LiveData
}// class
