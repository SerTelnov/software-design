package com.telnov.software_design.calculator.tokens.state;

import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.Tokenizer;

public abstract class State {

    protected final Tokenizer tokenizer;

    public State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Token createToken();
}
