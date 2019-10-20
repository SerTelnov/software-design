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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

class QueryServletTest {

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private ProductDao daoMock;

    private QueryServlet queryServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        queryServlet = new QueryServlet(daoMock);
    }

    @Test
    void findMaxTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("command")))
                .thenReturn("max");
        Mockito.when(daoMock.findMaxProduct())
                .thenReturn(Optional.of(new Product("prMax", 20L)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        queryServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.equalTo(
                        "<html><body>\r\n" +
                                "<h1>Product with max price: </h1>\r\n" +
                                "prMax\t20</br>\r\n" +
                                "</body></html>\r\n"
                )
        );
    }

    @Test
    void findMinTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("command")))
                .thenReturn("min");
        Mockito.when(daoMock.findMinProduct())
                .thenReturn(Optional.of(new Product("prMin", 20L)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        queryServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.equalTo(
                        "<html><body>\r\n" +
                                "<h1>Product with min price: </h1>\r\n" +
                                "prMin\t20</br>\r\n" +
                                "</body></html>\r\n"
                )
        );
    }

    @Test
    void sumTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("command")))
                .thenReturn("sum");
        Mockito.when(daoMock.sumProductPrices())
                .thenReturn(100L);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        queryServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.equalTo(
                        "<html><body>\r\n" +
                                "Summary price: \r\n" +
                                "100\r\n" +
                                "</body></html>\r\n"
                )
        );
    }

    @Test
    void countTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("command")))
                .thenReturn("count");
        Mockito.when(daoMock.countProducts())
                .thenReturn(100);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        queryServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.equalTo(
                        "<html><body>\r\n" +
                                "Number of products: \r\n" +
                                "100\r\n" +
                                "</body></html>\r\n"
                )
        );
    }

    @Test
    void invalidTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("command")))
                .thenReturn("some_other_command");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        queryServlet.doGet(requestMock, responseMock);

        writer.flush();
        assertThat(
                stringWriter.toString(),
                Matchers.equalTo(
                        "Unknown command: some_other_command\r\n"
                )
        );
    }
}