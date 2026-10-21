package com.example.quanlysach;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String status;
    private float rating;
    private String notes;

    public Book(String title, String author, String genre, String status, float rating, String notes) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.rating = rating;
        this.notes = notes;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getStatus() { return status; }
    public float getRating() { return rating; }
    public String getNotes() { return notes; }

    public void setStatus(String status) { this.status = status; }
    public void setRating(float rating) { this.rating = rating; }
}
