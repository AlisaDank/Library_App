package org.springeducation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "full_name")
    @NotEmpty(message = "Введите имя")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+", message = "Введите полное имя: Фамилия Имя Отчество")
    private String full_name;
    @Column(name = "birth_year")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int birth_year;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person(String full_name, int birth_year) {
        this.id = id;
        this.full_name = full_name;
        this.birth_year = birth_year;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBooks(Book book) {
        if (this.books == null)
            this.books = new ArrayList<>();

        this.books.add(book);
        book.setOwner(this);
    }
}
