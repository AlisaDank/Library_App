package org.springeducation.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Person {
    private int id;
    @NotEmpty(message = "Введите имя")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+", message = "Введите полное имя: Фамилия Имя Отчество")
    private String full_name;
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int birth_year;

    public Person(int id, String full_name, int birth_year) {
        this.id = id;
        this.full_name = full_name;
        this.birth_year = birth_year;
    }

    public Person() {
    }

    public Person(int id, String full_name) {
        this.id = id;
        this.full_name = full_name;
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
}
