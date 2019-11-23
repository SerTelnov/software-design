package com.telnov.software_design.calculator.tokens;

public enum TokenType {
    LPAREN,
    RPAREN,
    PLUS,
    MINUS,
    DIVISION,
    MULTIPLICATION,
    NUMBER;

    public int getPriority() {
        if (this == LPAREN || this == RPAREN) {
            return 0;
        } else if (this == PLUS || this == MINUS) {
            return 1;
        } else if (this == DIVISION || this == MULTIPLICATION) {
            return 2;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
