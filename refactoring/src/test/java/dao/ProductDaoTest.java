package dao;

import config.DBUtil;
import model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

class ProductDaoTest {

    private ProductDao productDao;

    @BeforeEach
    void setup() throws Exception {
        DBUtil.initDB();
        productDao = new ProductDao();
    }

    @AfterEach
    void cleanDB() throws Exception {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            st.execute("delete from PRODUCT");
        }
    }

    @Test
    void insertOneProductTest() {
        Product product = new Product("product1", 1);
        productDao.insert(product);

        List<Product> products = productDao.findProducts();
        assertThat(products, Matchers.contains(product));
    }

    @Test
    void insertTwoProductsTest() {
        Product product1 = new Product("product1", 1);
        Product product2 = new Product("product2", 2);
        insertProducts(product1, product2);

        List<Product> products = productDao.findProducts();
        assertThat(products, Matchers.contains(product1, product2));
    }

    @Test
    void sumProductPriceTest() {
        insertProducts(
                new Product("pr1", 10),
                new Product("pr2", 20),
                new Product("pr3", 30),
                new Product("pr4", 40)
        );

        long sum = productDao.sumProductPrices();
        assertThat(sum, Matchers.equalTo(100L));
    }

    @Test
    void countProductsTest() {
        insertProducts(
                new Product("pr1", 10),
                new Product("pr2", 20),
                new Product("pr3", 30),
                new Product("pr4", 40)
        );

        int count = productDao.countProducts();
        assertThat(count, Matchers.equalTo(4));
    }

    @Test
    void findMaxTest() {
        insertProducts(
                new Product("pr1", 10),
                new Product("pr2", 20),
                new Product("pr3", 30),
                new Product("pr4", 40)
        );

        Optional<Product> actual = productDao.findMaxProduct();
        assertThat(actual.isPresent(), Matchers.equalTo(Boolean.TRUE));
        assertThat(actual.get(), Matchers.equalTo(new Product("pr4", 40)));
    }

    @Test
    void findMaxEmptyDBTest() {
        Optional<Product> actual = productDao.findMaxProduct();
        assertThat(actual.isEmpty(), Matchers.equalTo(Boolean.TRUE));
    }

    @Test
    void findMinTest() {
        insertProducts(
                new Product("pr1", 10),
                new Product("pr2", 20),
                new Product("pr3", 30),
                new Product("pr4", 40)
        );

        Optional<Product> actual = productDao.findMinProduct();
        assertThat(actual.isPresent(), Matchers.equalTo(Boolean.TRUE));
        assertThat(actual.get(), Matchers.equalTo(new Product("pr1", 10)));
    }

    @Test
    void findMinEmptyDBTest() {
        Optional<Product> actual = productDao.findMinProduct();
        assertThat(actual.isEmpty(), Matchers.equalTo(Boolean.TRUE));
    }

    private void insertProducts(Product... products) {
        Arrays.asList(products)
                .forEach(product -> productDao.insert(product));
    }
}
