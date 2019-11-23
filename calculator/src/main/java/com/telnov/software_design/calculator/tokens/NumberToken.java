package com.telnov.software_design.calculator.tokens;

import com.telnov.software_design.calculator.visitor.TokenVisitor;

import java.util.Objects;

public class NumberToken implements Token {

    private final long value;

    public NumberToken(long value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberToken{" +
                "value=" + value +
                '}';
    }

    @Override
    public String toVisitorString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberToken)) return false;
        NumberToken that = (NumberToken) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
