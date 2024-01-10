package org.springeducation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Введите название книги")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Введите автора книги")
    private String author;
    @Column(name = "year")
    @Min(value = 1000, message = "Год издания книги не может быть меньше 1000")
    private int year;
    @Column(name = "taken_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenTime;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;
    @Transient
    private boolean IsTimeOver = false;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Date takenTime) {
        this.takenTime = takenTime;
    }

    public boolean isTimeOver() {
        return IsTimeOver;
    }

    public void setTimeOver(boolean timeOver) {
        IsTimeOver = timeOver;
    }
}
