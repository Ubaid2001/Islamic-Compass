package com.example.islamiccompass.api;

import com.example.islamiccompass.model.Book;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NodeServerApi {


    @GET("/books")
    Call<List<Book>> getBooks();

    @GET("/books/{Book_Name}")
    Call<ResponseBody> getBookPDF(@Path("Book_Name") String book_name);


}
