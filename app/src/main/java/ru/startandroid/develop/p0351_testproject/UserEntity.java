package ru.startandroid.develop.p0351_testproject;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// описание того что будет храниться в базе данных
// Entity: Represents a table within the database
@Entity(tableName = "data_table")
public class UserEntity {

    // описание колонн базы
    //  Primary key нужно для указания начальной точки для бд
    @PrimaryKey(autoGenerate = true)
    private int idl;

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private String company;
    private String url;
    private String text;

    public UserEntity(int id, String email, String first_name, String last_name, String avatar, String company, String url, String text) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.company = company;
        this.url = url;
        this.text = text;
    }// constructor

    public int getIdl() {
        return idl;
    }


    public void setIdl(int idl) {
        this.idl = idl;
    }

    // getters чтобы получать данные из базы (для инкапсуляции)
    public int getId() {
        return id;
    }// getId

    public String getEmail() {
        return email;
    }// getEmail

    public String getFirst_name() {
        return first_name;
    }// getFirst_name

    public String getLast_name() {
        return last_name;
    }// getLast_name

    public String getAvatar() {
        return avatar;
    }// getAvatar

    public String getCompany() {
        return company;
    }// getCompany

    public String getUrl() {
        return url;
    }// getUrl

    public String getText() {
        return text;
    }// getText
}// class