package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexAnalyzer {
    static public ArrayList<Token> AnalyzeLine(String line) throws RuntimeException {
        if(!line.matches("([)]|[(]|(S)|(Pr)|(MIN)|[\\[]|[]]|(id)|(:=)|[;]|[,]|[a-zA-Z0-9]+|[0-9]+|[ ]*)*")) throw new RuntimeException("the program does not correspond to the vocabulary of the language.");
        ArrayList<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("[)]|[(]|(S)|(Pr)|(MIN)|[\\[]|[]]|(id)|(:=)|[;]|[,]|[a-zA-Z0-9]+|[0-9]+");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String lexLine = line.substring(matcher.start(), matcher.end());
            switch (lexLine){
                case ")" -> tokens.add(new Token(Lexem.rrbr));
                case "(" -> tokens.add(new Token(Lexem.lrbr));
                case "S" -> tokens.add(new Token(Lexem.S));
                case "Pr" -> tokens.add(new Token(Lexem.Pr));
                case "MIN" -> tokens.add(new Token(Lexem.MIN));
                case "[" -> tokens.add(new Token(Lexem.lsbr));
                case "]" -> tokens.add(new Token(Lexem.rsbr));
                case "id" -> tokens.add(new Token(Lexem.id));
                case ":=" -> tokens.add(new Token(Lexem.assign));
                case ";" -> tokens.add(new Token(Lexem.semicolon));
                case "," -> tokens.add(new Token(Lexem.comma));
                default -> {
                    if(lexLine.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                        tokens.add(new Token(lexLine, Lexem.func));
                    }
                    else if(lexLine.matches("[0-9]+")){
                        tokens.add(new Token(Integer.parseInt(lexLine), Lexem.num));
                    }
                }
            }
        }
        return tokens;
    }

    static public ArrayList<Token> Analyze(BufferedReader stream) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        Set<String> libs = new TreeSet<>();
        Integer lineCounter = 0;

        while(stream.ready()) {
            lineCounter++;
            String line = stream.readLine();
            if(line == null) break;

            if(line.contains("%")){
                line = line.substring(0, line.indexOf('%'));
            }

            if(!line.contains("$")) {
                tokens.addAll(AnalyzeLine(line));
            }
            else {
                String libName = line.split("[$]")[1];
                if(!libs.contains(libName)) {
                    try {
                        libs.add(libName);
                        BufferedReader br = new BufferedReader(new FileReader(libName));
                        tokens.addAll(LexAnalyzer.Analyze(br));
                    }
                    catch (Exception e){
                        throw new IOException(lineCounter + ": " + e.getMessage());
                    }
                }
            }
        }
        return tokens;
    }
}
