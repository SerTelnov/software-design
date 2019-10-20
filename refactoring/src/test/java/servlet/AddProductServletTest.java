package servlet;

import dao.ProductDao;
import model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;

class AddProductServletTest {

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private ProductDao daoMock;

    private AddProductServlet addProductServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        addProductServlet = new AddProductServlet(daoMock);
    }

    @Test
    void addProductTest() throws Exception {
        Mockito.when(requestMock.getParameter(Mockito.eq("name")))
                .thenReturn("product1");
        Mockito.when(requestMock.getParameter(Mockito.eq("price")))
                .thenReturn("123");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMock.getWriter()).thenReturn(writer);

        addProductServlet.doGet(requestMock, responseMock);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(daoMock).insert(productCaptor.capture());

        Mockito.verify(requestMock, Mockito.atLeast(1))
                .getParameter("name");
        Mockito.verify(requestMock, Mockito.atLeast(1))
                .getParameter("price");
        writer.flush();

        assertThat(stringWriter.toString(), Matchers.startsWith("OK"));
        assertThat(
                productCaptor.getValue(),
                Matchers.equalTo(new Product("product1", 123L))
        );
    }
}
