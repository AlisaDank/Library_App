package org.springeducation.services;

import org.hibernate.Hibernate;
import org.springeducation.models.Book;
import org.springeducation.models.Person;
import org.springeducation.repositories.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository repository;

    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findOne(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Book> showBooks(int id) {
        Optional<Person> person = repository.findById(id);
        if (person.isPresent()) {
            List<Book> books = person.get().getBooks();
            Hibernate.initialize(books);
            for (Book book:
                 books) {
                if (book.getTakenTime() != null) {
                    Date current = new Date();
                    long difference = current.getTime() - book.getTakenTime().getTime();
                    int days = (int) (difference / (24 * 60 * 60 * 1000));
                    if (days > 10)
                        book.setTimeOver(true);
                }
            }
            return books;
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        repository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }
}
