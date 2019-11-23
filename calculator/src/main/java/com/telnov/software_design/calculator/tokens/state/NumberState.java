package com.telnov.software_design.calculator.tokens.state;

import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.Tokenizer;

public class NumberState extends State {

    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char currCh = tokenizer.current();
        StringBuilder builder = new StringBuilder();

        while (Character.isDigit(currCh)) {
            builder.append(currCh);
            currCh = tokenizer.next();
        }

        return new NumberToken(Long.parseLong(builder.toString()));
    }
}
