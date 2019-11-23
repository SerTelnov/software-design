package com.telnov.software_design.calculator.visitor;

import com.telnov.software_design.calculator.tokens.BracketToken;
import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.OperationToken;
import com.telnov.software_design.calculator.tokens.TokenType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.telnov.software_design.calculator.visitor.ParserVisitor.convertToReversePolishNotation;
import static org.hamcrest.MatcherAssert.assertThat;

class ParserVisitorTest {

    @Test
    void test01() {
//      3 + 4 * 2 / (1 − 5)
        assertThat(
                convertToReversePolishNotation(
                        Arrays.asList(
                                new NumberToken(3L),
                                new OperationToken(TokenType.PLUS),
                                new NumberToken(4L),
                                new OperationToken(TokenType.MULTIPLICATION),
                                new NumberToken(2L),
                                new OperationToken(TokenType.DIVISION),
                                new BracketToken(TokenType.LPAREN),
                                new NumberToken(1L),
                                new OperationToken(TokenType.MINUS),
                                new NumberToken(5L),
                                new BracketToken(TokenType.RPAREN)
                        )
                ),
                Matchers.contains(
                        new NumberToken(3L),
                        new NumberToken(4L),
                        new NumberToken(2L),
                        new OperationToken(TokenType.MULTIPLICATION),
                        new NumberToken(1L),
                        new NumberToken(5L),
                        new OperationToken(TokenType.MINUS),
                        new OperationToken(TokenType.DIVISION),
                        new OperationToken(TokenType.PLUS)
                )
        );
    }

    @Test
    void test02() {
        assertThat(
                convertToReversePolishNotation(
                        Arrays.asList(
                                new NumberToken(3L),
                                new OperationToken(TokenType.MINUS),
                                new NumberToken(4L),
                                new OperationToken(TokenType.PLUS),
                                new NumberToken(2L)
                        )
                ),
                Matchers.contains(
                        new NumberToken(3L),
                        new NumberToken(4L),
                        new OperationToken(TokenType.MINUS),
                        new NumberToken(2L),
                        new OperationToken(TokenType.PLUS)
                )
        );
    }

    @Test
    void test03() {
        assertThat(
                convertToReversePolishNotation(
                        Arrays.asList(
                                new NumberToken(1L),
                                new OperationToken(TokenType.PLUS),
                                new NumberToken(2L),
                                new OperationToken(TokenType.MULTIPLICATION),
                                new NumberToken(3L)
                        )
                ),
                Matchers.contains(
                        new NumberToken(1L),
                        new NumberToken(2L),
                        new NumberToken(3L),
                        new OperationToken(TokenType.MULTIPLICATION),
                        new OperationToken(TokenType.PLUS)
                )
        );
    }

    @Test
    void test04() {
        assertThat(
                convertToReversePolishNotation(
                        Arrays.asList(
                                new BracketToken(TokenType.LPAREN),
                                new NumberToken(1L),
                                new OperationToken(TokenType.PLUS),
                                new NumberToken(2L),
                                new BracketToken(TokenType.RPAREN),
                                new OperationToken(TokenType.MULTIPLICATION),
                                new NumberToken(3L)
                        )
                ),
                Matchers.contains(
                        new NumberToken(1L),
                        new NumberToken(2L),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(3L),
                        new OperationToken(TokenType.MULTIPLICATION)
                )
        );
    }
}