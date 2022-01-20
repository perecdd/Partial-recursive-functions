package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        try {
            var a = LexAnalyzer.Analyze(br);
            var Func = PRFCreator.Analyze(a);
            if(Func.containsKey("main")){
                PRFunction prFunction = Func.get("main");

                System.out.print("Please enter arguments (" + prFunction.getArgumentNumber() + "): ");

                ArrayList<Integer> arguments = new ArrayList<>();
                for(int i = 0; i < prFunction.getArgumentNumber(); i++){
                    Scanner scanner = new Scanner(System.in);
                    arguments.add(scanner.nextInt());
                }
                System.out.println(prFunction.evaluate(arguments));
            }
            else{
                System.out.println("Function \"main\" is not found.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
