package by.project.library.springweblibrary.dao.service;

import by.project.library.springweblibrary.dao.BookDao;
import by.project.library.springweblibrary.domain.Book;
import by.project.library.springweblibrary.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findTopBooks(int limit) {
     return bookRepository.findTopBooks(new PageRequest(0, limit, new Sort(Sort.Direction.DESC, "viewCount")));
}

    @Override
    public byte[] getContent(long id) {
        return bookRepository.getContent(id);
    }

    @Override
    public Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId) {
        return bookRepository.findByGenre(genreId, new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }

    @Override
    public void updateViewCount(long viewCount, long id) {
        bookRepository.updateViewCount(viewCount, id);
    }

    @Override
    public void updateRating(long totalRating, long totalVoteCount, int avgRating, long id) {
        bookRepository.updateRating(totalRating, totalVoteCount, avgRating, id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book get(long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book save(Book book) {

        bookRepository.save(book);

        if (book.getContent() != null){
            bookRepository.updateContent(book.getContent(), book.getId());
        }

        return book;

    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> search(String... searchString) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0]);
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Page<Book> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return bookRepository.findAllWithoutContent(new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0], new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }
}
