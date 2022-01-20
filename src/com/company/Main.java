package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        try {
            var a = LexAnalyzer.Analyze(br);
            for(Token token : a) {
                System.out.println(token);
            }
            System.out.println(SyntaxAnalyzer.Analyze(a));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
