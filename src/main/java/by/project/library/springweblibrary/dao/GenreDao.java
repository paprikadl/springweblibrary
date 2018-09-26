package by.project.library.springweblibrary.dao;

import by.project.library.springweblibrary.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();
    Genre get (long id);
    Genre save (Genre obj);
    void delete (Genre object);

    List<Genre> search(String... searchString);

}
