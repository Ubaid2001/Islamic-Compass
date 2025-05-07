package com.example.islamiccompass.model;


import android.graphics.drawable.Drawable;

public class Book {
    private String Book_Name;
    private String Book_Desc;
    private String Book_Author;
    private Drawable image;
    private int imageId;
    private String imagePath;

    public Book(String Book_Name, String Book_Desc, String Book_Author) {
        this.Book_Name = Book_Name;
        this.Book_Desc = Book_Desc;
        this.Book_Author = Book_Author;
    }


    public Book(String Book_Name, String Book_Desc, String Book_Author, Drawable image) {
        this.Book_Name = Book_Name;
        this.Book_Desc = Book_Desc;
        this.Book_Author = Book_Author;
        this.image = image;
    }

    public Book(String Book_Name, String Book_Desc, String Book_Author, Drawable image, int imageId) {
        this.Book_Name = Book_Name;
        this.Book_Desc = Book_Desc;
        this.Book_Author = Book_Author;
        this.image = image;
        this.imageId = imageId;
    }

    public Book(String Book_Name, String Book_Desc, String Book_Author, Drawable image, int imageId, String imagePath) {
        this.Book_Name = Book_Name;
        this.Book_Desc = Book_Desc;
        this.Book_Author = Book_Author;
        this.image = image;
        this.imageId = imageId;
        this.imagePath = imagePath;
    }

    public String getName() {
        return Book_Name;
    }

    public String getDesc() {
        return Book_Desc;
    }

    public String getAuthor() {
        return Book_Author;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return
                "Book{" +
                "name='" + Book_Name + '\'' +
                ", desc='" + Book_Desc + '\'' +
                ", author='" + Book_Author + '\'' +
                ", image='" + image + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
