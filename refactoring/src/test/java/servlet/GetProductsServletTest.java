package servlet;

import dao.ProductDao;
import model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class GetProductsServletTest {
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private ProductDao daoMock;

    private GetProductsServlet getProductsServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        getProductsServlet = new GetProductsServlet(daoMock);
    }

    @Test
    void getProductsTest() throws Exception {
        Mockito.when(daoMock.findProducts())
                .thenReturn(List.of(
                        new Product("pr1", 10L),
                        new Product("pr2", 20L)
                ));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        getProductsServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.startsWith(
                        "<html><body>\r\n" +
                        "pr1\t10</br>\r\n" +
                        "pr2\t20</br>\r\n" +
                        "</body></html>"
                )
        );
    }
}
