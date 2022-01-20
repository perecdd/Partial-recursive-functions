package com.company;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LexAnalyzerTest {

    @Test
    void analyzeLineBrackets() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("()()");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.rrbr));
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.rrbr));
            assertEquals(expected, tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("(((((");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 5; i++)
                expected.add(new Token(Lexem.lrbr));
            assertEquals(expected, tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(")))))");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 5; i++)
                expected.add(new Token(Lexem.rrbr));
            assertEquals(expected, tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("[][]");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.lsbr));
            expected.add(new Token(Lexem.rsbr));
            expected.add(new Token(Lexem.lsbr));
            expected.add(new Token(Lexem.rsbr));
            assertEquals(expected, tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("[[[[[");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 5; i++)
                expected.add(new Token(Lexem.lsbr));
            assertEquals(expected, tokens);
        }

        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("]]]]]");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 5; i++)
                expected.add(new Token(Lexem.rsbr));
            assertEquals(expected, tokens);
        }
        {
            boolean error = false;
            try {
                ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("{}");
            }
            catch (Exception e){
                error = true;
            }
            assert (error);
        }
    }

    @Test
    void analyzeLinePunctiation(){
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(",;,:=:=;;,,;;");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.assign));
            expected.add(new Token(Lexem.assign));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.semicolon));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(" , ; , := := ; ; , , ; ; ");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.assign));
            expected.add(new Token(Lexem.assign));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.comma));
            expected.add(new Token(Lexem.semicolon));
            expected.add(new Token(Lexem.semicolon));
            assertEquals(expected, tokens);
        }
        {
            boolean error = false;
            try {
                ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(": =");
            }
            catch (Exception e){
                error = true;
            }
            assert (error);
        }
    }

    @Test
    void analyzeLineBasicFunctions() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("S(S(S()))");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.S));
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.S));
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.S));
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.rrbr));
            expected.add(new Token(Lexem.rrbr));
            expected.add(new Token(Lexem.rrbr));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("id id() id[] id");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token(Lexem.id));
            expected.add(new Token(Lexem.id));
            expected.add(new Token(Lexem.lrbr));
            expected.add(new Token(Lexem.rrbr));
            expected.add(new Token(Lexem.id));
            expected.add(new Token(Lexem.lsbr));
            expected.add(new Token(Lexem.rsbr));
            expected.add(new Token(Lexem.id));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("i d");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token("i", Lexem.func));
            expected.add(new Token("d", Lexem.func));
            assertEquals(expected, tokens);
        }
        {
            String line = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 54 3653 234 2323 32 34543345";
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(line);
            ArrayList<Token> expected = new ArrayList<>();
            for(String substring : line.split(" "))
                expected.add(new Token(Integer.parseInt(substring), Lexem.num));
            assertEquals(expected, tokens);
        }
        {
            boolean error = false;
            try {
                ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("-3434");
            }
            catch (Exception e){
                error = true;
            }
            assert (error);
        }
    }

    @Test
    void analyzeLineOtherFunctions() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("Pr[]Pr[]Pr[]");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                expected.add(new Token(Lexem.Pr));
                expected.add(new Token(Lexem.lsbr));
                expected.add(new Token(Lexem.rsbr));
            }
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("P r");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token("P", Lexem.func));
            expected.add(new Token("r", Lexem.func));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("MIN()MIN()MIN()");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                expected.add(new Token(Lexem.MIN));
                expected.add(new Token(Lexem.lrbr));
                expected.add(new Token(Lexem.rrbr));
            }
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("MI N");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token("MI", Lexem.func));
            expected.add(new Token("N", Lexem.func));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("M I N");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token("M", Lexem.func));
            expected.add(new Token("I", Lexem.func));
            expected.add(new Token("N", Lexem.func));
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("MIN Pr MIN Pr MIN Pr");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                expected.add(new Token(Lexem.MIN));
                expected.add(new Token(Lexem.Pr));
            }
            assertEquals(expected, tokens);
        }
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("MIN Pr MIN Pr MIN Pr");
            ArrayList<Token> expected = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                expected.add(new Token(Lexem.MIN));
                expected.add(new Token(Lexem.Pr));
            }
            assertEquals(expected, tokens);
        }
    }

    @Test
    void analyzeLineCustomFunctions() {
        {
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine("myFunction333");
            ArrayList<Token> expected = new ArrayList<>();
            expected.add(new Token("myFunction333", Lexem.func));
            assertEquals(expected, tokens);
        }
        {
            String toAnalyze = "These are my custom functions consisting of English words";
            ArrayList<Token> tokens = LexAnalyzer.AnalyzeLine(toAnalyze);
            ArrayList<Token> expected = new ArrayList<>();
            for(String subString : toAnalyze.split(" ")) {
                expected.add(new Token(subString, Lexem.func));
            }
            assertEquals(expected, tokens);
        }
    }

    @Test
    void analyze() throws IOException {
        String line = "MyFunction1 := S(id(5, 32432));" +
                "MyFunction2 := MyFunction1(0, 555, Pr[29; MIN(Function(34))])";
        ArrayList<Token> myProg = new ArrayList<>();
        myProg.add(new Token("MyFunction1", Lexem.func));
        myProg.add(new Token(Lexem.assign));
        myProg.add(new Token(Lexem.S));
        myProg.add(new Token(Lexem.lrbr));
        myProg.add(new Token(Lexem.id));
        myProg.add(new Token(Lexem.lrbr));
        myProg.add(new Token(5, Lexem.num));
        myProg.add(new Token(Lexem.comma));
        myProg.add(new Token(32432, Lexem.num));
        myProg.add(new Token(Lexem.rrbr));
        myProg.add(new Token(Lexem.rrbr));
        myProg.add(new Token(Lexem.semicolon));
        myProg.add(new Token("MyFunction2", Lexem.func));
        myProg.add(new Token(Lexem.assign));
        myProg.add(new Token("MyFunction1", Lexem.func));
        myProg.add(new Token(Lexem.lrbr));
        myProg.add(new Token(0, Lexem.num));
        myProg.add(new Token(Lexem.comma));
        myProg.add(new Token(555, Lexem.num));
        myProg.add(new Token(Lexem.comma));
        myProg.add(new Token(Lexem.Pr));
        myProg.add(new Token(Lexem.lsbr));
        myProg.add(new Token(29, Lexem.num));
        myProg.add(new Token(Lexem.semicolon));
        myProg.add(new Token(Lexem.MIN));
        myProg.add(new Token(Lexem.lrbr));
        myProg.add(new Token("Function", Lexem.func));
        myProg.add(new Token(Lexem.lrbr));
        myProg.add(new Token(34, Lexem.num));
        myProg.add(new Token(Lexem.rrbr));
        myProg.add(new Token(Lexem.rrbr));
        myProg.add(new Token(Lexem.rsbr));
        myProg.add(new Token(Lexem.rrbr));
        assertEquals(myProg, LexAnalyzer.Analyze(new BufferedReader(new StringReader(line))));
        // There is no point in these lines, it's just checking the correctness of recognition by the lexical analyzer.
    }
}