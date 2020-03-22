package com.telnov.software_design.reactive_app.model;

import java.beans.ConstructorProperties;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@ParametersAreNonnullByDefault
public class User {

    @Id
    private final long id;
    @Nonnull
    private final String login;
    @Nonnull
    private final Currency currency;

    @ConstructorProperties({"id", "login", "currency"})
    public User(long id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getLogin() {
        return login;
    }

    @Nonnull
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                login.equals(user.login) &&
                currency == user.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, currency);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currency=" + currency +
                '}';
    }
}
