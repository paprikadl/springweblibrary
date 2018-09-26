package by.project.library.springweblibrary.dao;

import by.project.library.springweblibrary.domain.Publisher;

import java.util.List;

public interface PublisherDao {

    List<Publisher> getAll();
    Publisher get (long id);
    Publisher save (Publisher obj);
    void delete (Publisher object);

    List<Publisher> search(String... searchString);

}
