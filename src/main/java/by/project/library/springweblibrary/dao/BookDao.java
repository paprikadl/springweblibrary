package by.project.library.springweblibrary.dao;

import by.project.library.springweblibrary.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();
    Book get (long id);
    Book save (Book obj);
    void delete (Book object);

    List<Book> search(String... searchString);
    List<Book> findTopBooks (int limit);

}
