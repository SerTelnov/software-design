package servlet;

import dao.ProductDao;
import model.Product;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
@ParametersAreNonnullByDefault
public class GetProductsServlet extends AbstractProductServlet {

    private final ProductDao dao;

    public GetProductsServlet(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        enrichResponseHttpInfo(
                response,
                dao.findProducts()
                        .stream()
                        .map(Product::toHttpString)
                        .collect(Collectors.toList())
        );
    }
}
