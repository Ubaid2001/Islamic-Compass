package com.example.islamiccompass.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamiccompass.recycleview.BooksRecViewAdapter;
import com.example.islamiccompass.api.NodeServerApi;
import com.example.islamiccompass.R;
import com.example.islamiccompass.model.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class BooksFragment extends Fragment {

    private RecyclerView booksRecView;
    private List<String> bookName = new ArrayList<>();
    private  List<String>  bookDesc = new ArrayList<>();
    private  List<String>  bookAuthor = new ArrayList<>();
    private  List<Integer>  bookImageId = new ArrayList<>();
    private BooksRecViewAdapter adapter;

    @Inject
    Retrofit retrofit;
    View view;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);

        booksRecView = view.findViewById(R.id.booksRecView);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        adapter = new BooksRecViewAdapter(new BooksRecViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Book book = adapter.getBookItem(position);
                String name = book.getName();
                String desc = book.getDesc();
                String author = book.getAuthor();
                int imageId = book.getImageId();

                String imagePath = book.getImagePath();
                Book book2 = new Book(name, desc, author, null, imageId, imagePath);
                String jsonString =  gson.toJson(book2);


                Fragment bookDetailsFragment = new BookDetailsFragment();
                Bundle args = new Bundle();
                System.out.println("data received " + jsonString);
                args.putString("data_received", jsonString);
                bookDetailsFragment.setArguments(args);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.fragment_book_container_view, bookDetailsFragment)
                        .commit();


            }
        });

        NodeServerApi nodeServerApi = retrofit.create(NodeServerApi.class);

        Call<List<Book>> call = nodeServerApi.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                System.out.println("The Android is connected to the Server For BOOKS!!!");
                List<Book> booksList = response.body();

                Map<Integer, Book> books = new HashMap<>();
                int num = 0;

                for (Book book : booksList) {

                    bookName.add(book.getName());
                    bookDesc.add(book.getDesc());
                    bookAuthor.add(book.getAuthor());
                    bookImageId.add(book.getImageId());

                    String name = book.getName();
                    String author = book.getAuthor();
                    String desc = book.getDesc();
                    int resId = book.getImageId();

                    String imagePath = book.getImagePath();
                    System.out.println(imagePath);

                    books.put(num, new Book(name, desc, author, requireContext().getDrawable(resId), resId, imagePath));
                    num++;

                }
                adapter.setBooks(books);

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

}