package by.project.library.springweblibrary.spring.repository;

import by.project.library.springweblibrary.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String name, String fio);

}
