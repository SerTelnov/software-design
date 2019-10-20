package servlet;

import dao.ProductDao;
import model.Product;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author akirakozov
 */
@ParametersAreNonnullByDefault
public class GetProductsServlet extends HttpServlet {

    private final ProductDao dao;

    public GetProductsServlet(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = dao.findProducts();

        response.getWriter().println("<html><body>");
        products.forEach(product -> writeProduct(response, product));
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void writeProduct(HttpServletResponse response, Product product) {
        try {
            response.getWriter()
                    .println(product.getName() + "\t" + product.getPrice() + "</br>");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
