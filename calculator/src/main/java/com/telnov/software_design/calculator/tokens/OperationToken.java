package com.telnov.software_design.calculator.tokens;

import com.telnov.software_design.calculator.visitor.TokenVisitor;

import java.util.Objects;

public class OperationToken implements Token {

    private final TokenType type;

    public OperationToken(TokenType type) {
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

    public long evaluate(long a, long b) {
        switch (type) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case MULTIPLICATION:
                return a * b;
            case DIVISION:
                return a / b;
        }
        throw new RuntimeException();
    }

    @Override
    public String toVisitorString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationToken)) return false;
        OperationToken that = (OperationToken) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "OperationToken{" +
                "type=" + type +
                '}';
    }
}
