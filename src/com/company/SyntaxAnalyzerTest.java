package com.company;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SyntaxAnalyzerTest {
    @org.junit.jupiter.api.Test
    void analyzeID() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := id(1, 1);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(2) := id(2, 1);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := id(1, 1;");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := id;");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r := id(1, 1);");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
    }

    @org.junit.jupiter.api.Test
    void analyzeConst() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := sum(1543, id(1, 1));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(5) := 154323;");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := 1543;");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := ;");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
    }

    @org.junit.jupiter.api.Test
    void analyzeS() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := S(1);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := S(S(5));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := S(S(t));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(0) := S(S(t));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
    }

    @org.junit.jupiter.api.Test
    void analyzeSuperposition() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := S(t(id(1,1)));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("tr(5) := S(sum(t(id(5,1)), test(g(5))));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := S(S(S(S(S(id(1, 1))))));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(2) := sum(id(2, 1), id(2, 2));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(2) := id(2, 1);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(2) := t;");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
    }

    @org.junit.jupiter.api.Test
    void analyzePr() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[0;1];");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[f;g];");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[f(1, 2, 3);g(4, 5, 6)];");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[id(1, 1);g(id(1, 1), 5, 6)];");
            assert SyntaxAnalyzer.Analyze(tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[id(1, 1), g(id(1, 1), 5, 6)];");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr(id(1, 1); g(id(1, 1), 5, 6));");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr[id(1, 1); g(id(1, 1), 5, 6));");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := Pr(id(1, 1); g(id(1, 1), 5, 6)];");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
    }

    @org.junit.jupiter.api.Test
    void analyzeMIN() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(0);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(f);");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(id(1, 1));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(g(1, 2, 3));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(S(id(1, 1)));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(S(S(id(1, 1))));");
            assert SyntaxAnalyzer.Analyze(tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN[S(S(id(1, 1)))];");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN(S(S(id(1, 1)))];");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("r(1) := MIN[S(S(id(1, 1))));");
            assert !SyntaxAnalyzer.Analyze(tokens);
        }
    }
}