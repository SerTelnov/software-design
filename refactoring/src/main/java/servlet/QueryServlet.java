package servlet;

import dao.ProductDao;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
@ParametersAreNonnullByDefault
public class QueryServlet extends AbstractProductServlet {

    private final ProductDao dao;

    public QueryServlet(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    protected void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String command = req.getParameter("command");
        doCommand(command, resp);
    }

    private void doCommand(String command, HttpServletResponse response) throws IOException {
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
    }

    private void doCount(HttpServletResponse response) throws IOException {
        enrichResponseHttpInfo(
                response,
                List.of(
                        "Number of products: ",
                        Long.toString(dao.countProducts())
                )
        );
    }

    private void doSum(HttpServletResponse response) throws IOException {
        enrichResponseHttpInfo(
                response,
                List.of(
                        "Summary price: ",
                        Long.toString(dao.sumProductPrices())
                )
        );
    }

    private void doMin(HttpServletResponse response) throws IOException {
        enrichResponseHttpInfo(
                response,
                dao.findMinProduct()
                        .map(value -> List.of(
                                "<h1>Product with min price: </h1>",
                                value.toHttpString()
                        )).orElseGet(() -> List.of(
                        "<h1>Product with min price: </h1>"
                ))
        );
    }

    private void doMax(HttpServletResponse response) throws IOException {
        enrichResponseHttpInfo(
                response,
                dao.findMaxProduct()
                        .map(value -> List.of(
                                "<h1>Product with max price: </h1>",
                                value.toHttpString()
                        )).orElseGet(() -> List.of(
                        "<h1>Product with max price: </h1>"
                ))
        );
    }
}
