package by.project.library.springweblibrary.jsfui.servlets;

import by.project.library.springweblibrary.jsfui.controller.BookController;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "PdfContent", urlPatterns = {"/PdfContent"})
public class PdfContent extends HttpServlet {

    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OutputStream out = response.getOutputStream();

        try {
            long id = Long.valueOf(request.getParameter("id"));
            long viewCount = Long.valueOf(request.getParameter("viewCount"));

            BookController bookController = ((BookController)context.getBean("bookController"));

            byte[] content = bookController.getContent(id);

            if (content == null){
                response.sendRedirect(request.getContextPath() + "/error/error-pdf.html");
            }else {
                response.setContentType("application/pdf");

                bookController.updateViewCount(viewCount, id);

                response.setContentLength(content.length);
                out.write(content);
            }
        } catch (NumberFormatException | BeansException | IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
