package com.telnov.software_design.calculator.tokens.state;

import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.Tokenizer;

public class StartState extends State {

    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        throw new UnsupportedOperationException();
    }
}
