package com.telnov.software_design.calculator.tokens;

import com.telnov.software_design.calculator.visitor.TokenVisitor;

import java.util.Objects;

public class BracketToken implements Token {

    private final TokenType type;

    public BracketToken(TokenType type) {
        this.type = type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }

    @Override
    public String toVisitorString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BracketToken)) return false;
        BracketToken that = (BracketToken) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "BracketToken{" +
                "type=" + type +
                '}';
    }
}
