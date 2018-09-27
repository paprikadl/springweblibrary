package by.project.library.springweblibrary.spring.controller;

import by.project.library.springweblibrary.domain.Author;
import by.project.library.springweblibrary.spring.repository.AuthorRepository;
import by.project.library.springweblibrary.spring.repository.BookRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Log
public class RedirectController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {

        Page<Author> pageList = authorRepository.findByFioContainingIgnoreCaseOrderByFio("д", PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "fio")));

        return "ok";

    }

}
