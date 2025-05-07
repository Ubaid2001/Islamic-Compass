package com.example.islamiccompass.model;

import androidx.annotation.NonNull;

public class Hadith {
    private String book;
    private String bookName;
    private String chapterName;
    private String hadith_english;
    private String header;
    private int id;
    private String refno;

    public Hadith() {
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getHadith_english() {
        return hadith_english;
    }

    public void setHadith_english(String hadith_english) {
        this.hadith_english = hadith_english;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hadith {" +
                "book='" + book + '\'' +
                ", bookName='" + bookName + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", hadith_english='" + hadith_english + '\'' +
                ", header='" + header + '\'' +
                ", id='" + id + '\'' +
                ", refno='" + refno + '\'' +
                '}';
    }
}
