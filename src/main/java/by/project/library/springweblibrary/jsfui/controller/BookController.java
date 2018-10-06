package by.project.library.springweblibrary.jsfui.controller;

import by.project.library.springweblibrary.dao.BookDao;
import by.project.library.springweblibrary.dao.GenreDao;
import by.project.library.springweblibrary.domain.Book;
import by.project.library.springweblibrary.jsfui.enums.SearchType;
import by.project.library.springweblibrary.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

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

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private GenreController genreController;

    private Book selectedBook;

    private LazyDataTable<Book> lazyModel;

    private byte[] uploadedImage;

    private byte[] uploadedContent;

    private Page<Book> bookPages;

    private List<Book> topBooks;

    private String searchText;

    private long selectedGenreId;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }

    public void save() {

        if (uploadedImage != null) {
            selectedBook.setImage(uploadedImage);
        }

        if (uploadedContent != null) {
            selectedBook.setContent(uploadedContent);
        }

        bookDao.save(selectedBook);
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");
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
                    bookPages = bookDao.findByGenre(pageNumber, pageSize, sortField, sortDirection, selectedGenreId);
                    break;
                case SEARCH_TEXT:
                    bookPages = bookDao.search(pageNumber, pageSize, sortField, sortDirection, searchText);
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;
            }
        }
        return bookPages;
    }

    @Override
    public void addAction() {

        selectedBook = new Book();
        uploadedImage = loadDefaultIcon();
        uploadedContent = null;

        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    private byte[] loadDefaultIcon(){

        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/no-cover.jpg");

        try {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onCloseDialog(CloseEvent event) {
        uploadedContent = null;
    }

    @Override
    public void editAction() {
        uploadedImage = selectedBook.getImage();
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    @Override
    public void deleteAction() {
        bookDao.delete(selectedBook);
    }

    public String getSearchMessage(){

        ResourceBundle bundle = ResourceBundle.getBundle("library", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        String message = null;

        if (searchType == null){
            return null;
        }
        switch (searchType) {
            case SEARCH_GENRE:
                message = bundle.getString("genre") + ": '" + genreDao.get(selectedGenreId) + "'";
                break;
            case SEARCH_TEXT:

                if (searchText==null || searchText.trim().length() == 0){
                    return null;
                }

                message = bundle.getString("search") + ": '" + searchText + "'";
                break;
        }

        return message;
    }

    public byte[] getContent(long id) {

        byte[] content;

        if (uploadedContent != null) {
            content = uploadedContent;
        } else {

            content = bookDao.getContent(id);

        }

        return content;
    }

    public void uploadImage(FileUploadEvent event) {
        if (event.getFile() != null) {
            uploadedImage = event.getFile().getContents();
        }
    }

    public void uploadContent(FileUploadEvent event) {
        if (event.getFile() != null) {
            uploadedContent = event.getFile().getContents();
        }
    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }

    public void showBooksByGenre(long selectedGenreId){
        searchType = SearchType.SEARCH_GENRE;
        this.selectedGenreId = selectedGenreId;
    }

    public void showAll(){
        searchType = SearchType.ALL;
    }

    public void searchAction(){
        searchText = searchText.trim();
        searchType = SearchType.SEARCH_TEXT;
    }

    public Page<Book> getBookPages(){
        return bookPages;
    }

    public void updateViewCount(long viewCount, long id){
        bookDao.updateViewCount(viewCount + 1, id);
    }
}
