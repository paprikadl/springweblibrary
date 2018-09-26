package by.project.library.springweblibrary.spring.repository;

import by.project.library.springweblibrary.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    List<Publisher> findByNameContainingIgnoreCaseOrderByName(String name);

}
