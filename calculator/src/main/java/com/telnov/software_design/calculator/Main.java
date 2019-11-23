package com.telnov.software_design.calculator;

import com.telnov.software_design.calculator.tokens.Token;
import com.telnov.software_design.calculator.tokens.Tokenizer;
import com.telnov.software_design.calculator.visitor.CalcVisitor;
import com.telnov.software_design.calculator.visitor.ParserVisitor;
import com.telnov.software_design.calculator.visitor.PrintVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String input = reader.readLine();
            if (input.isEmpty()) {
                continue;
            }

            List<Token> tokens = Tokenizer.parse(input);
            System.out.println("Your input: " + new PrintVisitor().walk(tokens));

            tokens = ParserVisitor.convertToReversePolishNotation(tokens);
            System.out.println("Reverse polish notation: " + new PrintVisitor().walk(tokens));

            System.out.println("Your value: " + new CalcVisitor().calc(tokens));
        }
    }
}
