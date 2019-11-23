package com.telnov.software_design.calculator.visitor;

import com.telnov.software_design.calculator.tokens.BracketToken;
import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.OperationToken;
import com.telnov.software_design.calculator.tokens.Token;

import java.util.List;
import java.util.StringJoiner;

public class PrintVisitor implements TokenVisitor {

    private final StringJoiner holder;

    public PrintVisitor() {
        this.holder = new StringJoiner(" ", "[", "]");
    }

    public String walk(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        return holder.toString();
    }

    @Override
    public void visit(NumberToken token) {
        join(token);
    }

    @Override
    public void visit(BracketToken token) {
        join(token);
    }

    @Override
    public void visit(OperationToken token) {
        join(token);
    }

    private void join(Token token) {
        holder.add(token.toVisitorString());
    }
}
