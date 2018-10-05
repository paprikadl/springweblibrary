package by.project.library.springweblibrary.jsfui.controller;

import by.project.library.springweblibrary.dao.GenreDao;
import by.project.library.springweblibrary.domain.Genre;
import by.project.library.springweblibrary.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
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
public class GenreController extends AbstractController<Genre> {

    private int rowsCount = 20;
    private int first;

    @Autowired
    private GenreDao genreDao;

    private Genre selectedGenre;

    private LazyDataTable<Genre> lazyModel;

    private Page<Genre> genrePages;


    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }

    public List<Genre> find(String name) {
        return genreDao.search(name);
    }

    public void save() {
        genreDao.save(selectedGenre);
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
    }

    @Override
    public Page<Genre> search(int first, int count, String sortField, Sort.Direction sortDirection) {
        return genrePages;
    }

    @Override
    public void addAction() {

    }

    @Override
    public void editAction() {

    }

    @Override
    public void deleteAction() {

    }

    public List<Genre> getAll() {
        return genreDao.getAll(new Sort(Sort.Direction.ASC, "name"));
    }
}
