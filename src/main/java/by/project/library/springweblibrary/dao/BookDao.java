package by.project.library.springweblibrary.dao;

import by.project.library.springweblibrary.domain.Book;

import java.util.List;

public interface BookDao extends GeneralDao<Book>{

    List<Book> findTopBooks (int limit);

}
