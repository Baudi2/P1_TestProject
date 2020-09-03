package ru.startandroid.develop.p0351_testproject;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

// интерфейс с описаем методов которые будут использоваться
// DAO - Data Access Object
@Dao
public interface DataBaseDAO {

    // onConflictStrategy.REPLACE нужен для того что бы не заносить
    // одни и те же данные повторно в бд. Если уже существующие данные
    // совпадают с теми что будут вноситься то и заменяются и не стакаются
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity userEntity);

    @Query("DELETE FROM data_table")
    void deleteAllData();

    @Query("SELECT * FROM data_table")
    LiveData<List<UserEntity>> getAll();

}// interface