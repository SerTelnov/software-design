package com.telnov.software_design.calculator.tokens;

import com.telnov.software_design.calculator.visitor.TokenVisitor;

public interface Token {

    void accept(TokenVisitor visitor);

    TokenType getTokenType();

    String toVisitorString();
}
