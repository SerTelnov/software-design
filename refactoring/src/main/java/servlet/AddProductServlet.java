package servlet;

import dao.ProductDao;
import model.Product;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
@ParametersAreNonnullByDefault
public class AddProductServlet extends HttpServlet {

    private final ProductDao dao;

    public AddProductServlet(ProductDao dao) {
        super();
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dao.insert(
                new Product(
                        request.getParameter("name"),
                        Long.parseLong(request.getParameter("price"))
                )
        );

        response.getWriter().println("OK");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
