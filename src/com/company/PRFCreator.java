package com.company;

import java.util.*;

// Every string is function
// Uses a function with name main.
// func - is a word, which contain letters and digits
// num - is a sequence of digits
// Function -> func(num) := F; Function | e
// F -> Pr[SF;SF] | MIN(SF) | SF
// SF -> num | S(SF) | id(num, num) | func(args) | func
// args -> SF, args | SF
public class PRFCreator {
    private static Integer pointer = 0;
    private static ArrayList<Token> program = null;

    private static Map<String, PRFunction> prFunctionArrayList = null;

    private static ArrayList<PRFunction> functionsInArgs = null;

    private static String functionName = null;
    private static Integer argumentsCount = null;
    private static Stack<PRFunction> prFunctions = null;

    public static Map<String, PRFunction> Analyze(ArrayList<Token> someProgram){
        program = someProgram;
        pointer = 0;
        prFunctions = new Stack<>();
        prFunctionArrayList = new TreeMap<String, PRFunction>();

        if(Function() && pointer == program.size()) {
            return prFunctionArrayList;
        }
        else{
            throw new RuntimeException("Bad program");
        }
    }

    private static boolean Function(){
        if(pointer >= program.size()) return true;
        if(!program.get(pointer).getLexem().equals(Lexem.func)) return false;
        functionName = program.get(pointer).getString();
        pointer++;
        if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
        if(!program.get(pointer).getLexem().equals(Lexem.num)) return false;
        argumentsCount = program.get(pointer).getNumber();
        pointer++;
        if(program.get(pointer++).getLexem().equals(Lexem.rrbr) &&
                program.get(pointer++).getLexem().equals(Lexem.assign)){
            if(!F()) return false;
            if(program.get(pointer++).getLexem().equals(Lexem.semicolon)){
                prFunctionArrayList.put(functionName, prFunctions.pop());
                return Function();
            }
            else return false;
        }
        else return false;
    }

    private static boolean F(){
        if(program.get(pointer).getLexem().equals(Lexem.Pr)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lsbr)) return false;
            if(!SF()) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.semicolon)) return false;
            if(!SF()) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.rsbr)) return false;
            PRFunction second = prFunctions.pop();
            PRFunction first = prFunctions.pop();
            Pr pr = new Pr(argumentsCount, first, second);
            prFunctions.push(pr);
            // Pr to stack
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.MIN)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
            if(!SF()) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            // Min to stack
            Min min = new Min(argumentsCount, prFunctions.pop());
            prFunctions.push(min);
            return true;
        }
        else if(SF()){
            return true;
        }
        return false;
    }

    private static boolean SF(){
        if(program.get(pointer).getLexem().equals(Lexem.S)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
            if(!SF()) return false;
            prFunctions.add(new Sup(1, new S(), new ArrayList<PRFunction>(List.of(prFunctions.pop()))));
            // S()
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.id)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
            if(!program.get(pointer).getLexem().equals(Lexem.num)) return false;
            Integer maxArgs = program.get(pointer).getNumber();
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.comma)) return false;
            if(!program.get(pointer).getLexem().equals(Lexem.num)) return false;
            Integer usedArg = program.get(pointer).getNumber();
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            // id()
            prFunctions.add(new id(maxArgs, usedArg));
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.num)){
            // num
            prFunctions.add(new num(argumentsCount, program.get(pointer).getNumber()));
            pointer++;
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.func)) {
            String funcName = program.get(pointer).getString();
            pointer++;
            if(program.get(pointer).getLexem().equals(Lexem.lrbr)){
                pointer++;
                functionsInArgs = new ArrayList<>();
                if(!args()) return false;
                if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
                // Sup func
                Sup sup = new Sup(functionsInArgs.size(), new PRFunction(prFunctionArrayList.get(funcName)), functionsInArgs);
                prFunctions.push(sup);
            }
            return true;
        }
        else return false;
    }

    private static boolean args(){
        if(SF()){
            // Add arg func
            functionsInArgs.add(prFunctions.pop());
            if(program.get(pointer).getLexem().equals(Lexem.comma)){
                pointer++;
                return args();
            }
        }
        else return false;
        return true;
    }
}
