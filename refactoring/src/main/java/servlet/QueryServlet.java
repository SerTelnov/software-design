package servlet;

import dao.ProductDao;
import model.Product;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author akirakozov
 */
@ParametersAreNonnullByDefault
public class QueryServlet extends HttpServlet {

    private final ProductDao dao;

    public QueryServlet(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max":
                doMax(response);
                break;
            case "min":
                doMin(response);
                break;
            case "sum":
                doSum(response);
                break;
            case "count":
                doCount(response);
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void doCount(HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            final int count = dao.countProducts();
            response.getWriter().println(count);
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doSum(HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            final long sum = dao.sumProductPrices();
            response.getWriter().println(sum);

            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doMin(HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            dao.findMinProduct().ifPresent(product -> uncheckedWrite(response, product));
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doMax(HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Product with max price: </h1>");

        dao.findMaxProduct().ifPresent(product -> uncheckedWrite(response, product));
        response.getWriter().println("</body></html>");
    }

    private void uncheckedWrite(HttpServletResponse response, Product product) {
        try {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
