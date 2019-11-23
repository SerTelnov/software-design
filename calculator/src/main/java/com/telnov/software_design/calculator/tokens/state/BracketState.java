package com.telnov.software_design.calculator.tokens.state;

import com.telnov.software_design.calculator.tokens.BracketToken;
import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.TokenType;
import com.telnov.software_design.calculator.tokens.Tokenizer;

public class BracketState extends State {

    public BracketState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        final char ch = tokenizer.current();
        tokenizer.next();
        return new BracketToken(parseType(ch));
    }

    private static TokenType parseType(char ch) {
        switch (ch) {
            case '(':
                return TokenType.LPAREN;
            case ')':
                return TokenType.RPAREN;
        }

        throw new IllegalArgumentException("Undefined operation '" + ch + "'");
    }
}
