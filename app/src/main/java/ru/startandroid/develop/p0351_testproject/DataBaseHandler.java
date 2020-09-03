package ru.startandroid.develop.p0351_testproject;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// класс для создания базы данных используя параметры
// предыдущих двух классов
// также для уничтожения и создания снова базы в
// случае ее изменения
@Database(entities = {UserEntity.class}, version = 22)
public abstract class DataBaseHandler extends RoomDatabase {
    private static DataBaseHandler instance;

    // Room auto generates all the necessary code for this method
    public abstract DataBaseDAO dataBaseDAO();

    // создает базу данных если таковой ещё нет
    // synchronized нужен для того чтобы метод нельзя
    // было вызывать в разных потоках.
    // only one thread at a time can call this method
    public static synchronized DataBaseHandler getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBaseHandler.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
            // метод fallbackToDestructiveMigration удалят базу данных
            // при внесении в неё каких либо изменений из создает её занова
        }// if
        return instance;
    }// synchronized
}// class
