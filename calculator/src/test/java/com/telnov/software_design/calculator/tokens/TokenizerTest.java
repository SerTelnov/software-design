package com.telnov.software_design.calculator.tokens;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.telnov.software_design.calculator.tokens.Tokenizer.parse;
import static org.hamcrest.MatcherAssert.assertThat;

class TokenizerTest {

    @Test
    void test01() {
        assertThat(
                parse("2 + 2 * 2 / 2 - 2"),
                Matchers.contains(
                        new NumberToken(2L),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(2L),
                        new OperationToken(TokenType.MULTIPLICATION),
                        new NumberToken(2L),
                        new OperationToken(TokenType.DIVISION),
                        new NumberToken(2L),
                        new OperationToken(TokenType.MINUS),
                        new NumberToken(2L)
                )
        );
    }

    @Test
    void test02() {
        assertThat(
                parse("2 + 2 * 2"),
                Matchers.contains(
                        new NumberToken(2L),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(2L),
                        new OperationToken(TokenType.MULTIPLICATION),
                        new NumberToken(2L)
                )
        );
    }

    @Test
    void test03() {
        assertThat(
                parse("1234567          \r\t + 8910"),
                Matchers.contains(
                        new NumberToken(1234567L),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(8910L)
                )
        );
    }

    @Disabled
    @Test
    void test04() {
        assertThat(
                parse("-12345"),
                Matchers.contains(new NumberToken(-12345))
        );
    }

    @Test
    void test05() {
        assertThat(
                parse("12345-12345"),
                Matchers.contains(
                        new NumberToken(12345),
                        new OperationToken(TokenType.MINUS),
                        new NumberToken(12345)
                )
        );
    }
}