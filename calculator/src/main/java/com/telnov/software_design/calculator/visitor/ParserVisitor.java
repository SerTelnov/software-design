package com.telnov.software_design.calculator.visitor;

import com.telnov.software_design.calculator.tokens.BracketToken;
import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.OperationToken;
import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.TokenType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {

    private final Deque<Token> tokens;
    private final Deque<Token> stack;

    private ParserVisitor() {
        tokens = new ArrayDeque<>();
        stack = new ArrayDeque<>();
    }

    public static List<Token> convertToReversePolishNotation(List<Token> tokens) {
        return new ParserVisitor().convert(tokens);
    }

    private List<Token> convert(List<Token> input) {
        input.forEach(token -> token.accept(this));

        while (!stack.isEmpty()) {
            this.tokens.add(stack.pollLast());
        }

        return List.copyOf(this.tokens);
    }


    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(BracketToken token) {
        TokenType type = token.getTokenType();
        if (isOpenParen(type)) {
            stack.add(token);
        } else if (type == TokenType.RPAREN) {
            Token currToken = stack.pollLast();
            while (!stack.isEmpty() && !isOpenParen(currToken.getTokenType())) {
                tokens.add(currToken);
                currToken = stack.pollLast();
            }
        }
    }

    private static boolean isOpenParen(TokenType type) {
        return type == TokenType.LPAREN;
    }

    @Override
    public void visit(OperationToken token) {
        if (stack.isEmpty()) {
            stack.add(token);
        } else {
            TokenType currType = token.getTokenType();
            Token lastToken = stack.peekLast();
            while (!stack.isEmpty() && currType.getPriority() <= lastToken.getTokenType().getPriority()) {
                tokens.add(stack.pollLast());
                lastToken = stack.peekLast();
            }
            stack.add(token);
        }
    }
}
