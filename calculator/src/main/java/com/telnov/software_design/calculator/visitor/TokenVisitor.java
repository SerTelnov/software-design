package com.telnov.software_design.calculator.visitor;

import com.telnov.software_design.calculator.tokens.BracketToken;
import com.telnov.software_design.calculator.tokens.NumberToken;
import com.telnov.software_design.calculator.tokens.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(BracketToken token);
    void visit(OperationToken token);
}
