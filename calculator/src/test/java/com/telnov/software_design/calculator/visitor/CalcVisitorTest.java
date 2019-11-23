package com.telnov.software_design.calculator.visitor;

import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.OperationToken;
import com.telnov.software_design.calculator.tokens.TokenType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;

class CalcVisitorTest {

    @Test
    void test01() {
        CalcVisitor calcVisitor = new CalcVisitor();
        assertThat(
                calcVisitor.calc(
                        Arrays.asList(
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
                ),
                Matchers.equalTo(1L)
        );
    }

    @Test
    void test02() {
        CalcVisitor calcVisitor = new CalcVisitor();
        assertThat(
                calcVisitor.calc(
                        Arrays.asList(
                                new NumberToken(3L),
                                new NumberToken(4L),
                                new OperationToken(TokenType.MINUS),
                                new NumberToken(2L),
                                new OperationToken(TokenType.PLUS)
                        )
                ),
                Matchers.equalTo(1L)
        );
    }

    @Test
    void test03() {
        CalcVisitor calcVisitor = new CalcVisitor();
        assertThat(
                calcVisitor.calc(
                        Arrays.asList(
                                new NumberToken(2L),
                                new NumberToken(2L),
                                new NumberToken(2L),
                                new OperationToken(TokenType.MULTIPLICATION),
                                new OperationToken(TokenType.PLUS)
                        )
                ),
                Matchers.equalTo(6L)
        );
    }

    @Test
    void test04() {
        CalcVisitor calcVisitor = new CalcVisitor();
        assertThat(
                calcVisitor.calc(
                        Arrays.asList(
                                new NumberToken(2L),
                                new NumberToken(2L),
                                new OperationToken(TokenType.PLUS),
                                new NumberToken(2L),
                                new OperationToken(TokenType.MULTIPLICATION)
                        )
                ),
                Matchers.equalTo(8L)
        );
    }
}