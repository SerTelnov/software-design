package com.telnov.software_design.reactive_app.model;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@ParametersAreNonnullByDefault
public class Product {

    @Id
    private final long id;
    @Nonnull
    private final String title;
    @Nonnull
    private final Currency currency;
    @Nonnull
    private final BigDecimal price;

    @ConstructorProperties({"id", "title", "currency", "price"})
    public Product(long id, String title, Currency currency, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.currency = currency;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nonnull
    public Currency getCurrency() {
        return currency;
    }

    @Nonnull
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id &&
                title.equals(product.title) &&
                currency == product.currency &&
                price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, currency, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", currency=" + currency +
                ", price=" + price +
                '}';
    }
}
