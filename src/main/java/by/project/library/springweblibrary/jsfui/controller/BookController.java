package by.project.library.springweblibrary.jsfui.controller;

import by.project.library.springweblibrary.dao.BookDao;
import by.project.library.springweblibrary.domain.Book;
import by.project.library.springweblibrary.jsfui.enums.SearchType;
import by.project.library.springweblibrary.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
@Log
public class BookController extends AbstractController<Book> {

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final int TOP_BOOKS_LIMIT = 5;

    private int rowsCount = DEFAULT_PAGE_SIZE;

    private SearchType searchType;

    @Autowired
    private BookDao bookDao;

    private LazyDataTable<Book> lazyModel;

    private Page<Book> bookPages;

    private List<Book> topBooks;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        if (sortField == null){
            sortField = "name";
        }

        if (searchType == null){
            bookPages = bookDao.getAll(pageNumber,pageSize, sortField, sortDirection);
        } else {
            switch (searchType){
                case SEARCH_GENRE:
                    break;
                case SEARCH_TEXT:
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;
            }
        }
        return bookPages;
    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }
}
