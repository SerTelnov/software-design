package com.telnov.software_design.reactive_app.model;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.util.Pair;

public final class Converter {

    private static final Map<Pair<Currency, Currency>, BigDecimal> CONVERTER_MAP = Map.of(
            Pair.of(Currency.USD, Currency.RUB), new BigDecimal("79.0817"),
            Pair.of(Currency.USD, Currency.EUR), new BigDecimal("0.93304"),

            Pair.of(Currency.EUR, Currency.RUB), new BigDecimal("84.7318"),
            Pair.of(Currency.EUR, Currency.USD), new BigDecimal("1.07145"),

            Pair.of(Currency.RUB, Currency.USD), new BigDecimal("0.01262"),
            Pair.of(Currency.RUB, Currency.EUR), new BigDecimal("0.01177")
    );

    private Converter() {
        throw new UnsupportedOperationException();
    }

    public static Product convertProductPrice(Product product, Currency otherCurrency) {
        final var otherPrice = convertPrice(product.getPrice(), product.getCurrency(), otherCurrency);
        return new Product(product.getId(), product.getTitle(), otherCurrency, otherPrice);
    }

    public static BigDecimal convertPrice(BigDecimal price, Currency from, Currency to) {
        return price.multiply(CONVERTER_MAP.getOrDefault(Pair.of(from, to), BigDecimal.ONE));
    }
}
