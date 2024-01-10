package org.springeducation.services;

import org.hibernate.Hibernate;
import org.springeducation.models.Book;
import org.springeducation.models.Person;
import org.springeducation.repositories.BooksRepository;
import org.springeducation.repositories.PeopleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(boolean isSorted) {
        if (isSorted) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return booksRepository.findAll();
    }

    public List<Book> findSomeInPage(int page, int booksPerPage, boolean isSorted) {
        if (isSorted) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }
    
    public Person showOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Book> findByStartOfName(String name) {
        return booksRepository.findByNameStartingWith(name);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book book = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(book.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void addOwner(int book_id, int person_id) {
        Optional<Book> book = booksRepository.findById(book_id);
        Optional<Person> person = peopleRepository.findById(person_id);
        if (book.isPresent() && person.isPresent()) {
            person.get().addBooks(book.get());
            book.get().setTakenTime(new Date());
            peopleRepository.save(person.get());
        }
    }

    @Transactional
    public void deleteOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> value.setOwner(null));
        book.ifPresent(value -> value.setTakenTime(null));
    }
}
