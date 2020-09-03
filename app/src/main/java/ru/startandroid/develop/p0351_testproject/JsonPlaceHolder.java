package ru.startandroid.develop.p0351_testproject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {
    // adding rest of the URL to get the JSON file
    @GET("api/users/2")
    // making call to get JSON data
    Call<Post> getPost();
}// interface
