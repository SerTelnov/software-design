package dao;

import config.DBUtil;
import model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    public void insert(Product product) {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            st.executeUpdate("INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES " +
                    "('" + product.getName() + "'," + product.getPrice() + ")");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Product> findProducts() {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
            return mapResultSet(rs);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public long sumProductPrices() {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT SUM(price) as res FROM PRODUCT");
            return rs.getLong("res");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int countProducts() {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT count(*) as res FROM PRODUCT");
            return rs.getInt("res");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<Product> findMaxProduct() {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
            return mapResultSet(rs).stream()
                    .findFirst();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<Product> findMinProduct() {
        try (Statement st = DBUtil.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
            return mapResultSet(rs).stream()
                    .findFirst();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Product> mapResultSet(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();

        while (rs.next()) {
            products.add(convertToProduct(rs));
        }

        return products;
    }

    private Product convertToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("name"),
                rs.getInt("price")
        );
    }
}
