package com.telnov.software_design.calculator.tokens.state;

import com.telnov.software_design.calculator.tokens.OperationToken;
import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.TokenType;
import com.telnov.software_design.calculator.tokens.Tokenizer;

public class OperationState extends State {

    public OperationState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        final char ch = tokenizer.current();
        tokenizer.next();
        return new OperationToken(parseTokenType(ch));
    }

    private static TokenType parseTokenType(char ch) {
        switch (ch) {
            case '+':
                return TokenType.PLUS;
            case '-':
                return TokenType.MINUS;
            case '*':
                return TokenType.MULTIPLICATION;
            case '/':
                return TokenType.DIVISION;
        }

        throw new IllegalArgumentException("Undefined operation '" + ch + "'");
    }
}
