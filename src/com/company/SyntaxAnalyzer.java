package com.company;

import java.util.ArrayList;

// Every string is function
// Uses a function with name main.
// func - is a word, which contain letters and digits
// num - is a sequence of digits
// Function -> func(num) := F; Function | e
// F -> Pr[SF;SF] | MIN(SF) | SF
// SF -> num | S(SF) | id(num, num) | func(args) | func
// args -> SF, args | SF
public class SyntaxAnalyzer {
    private static Integer pointer = 0;
    private static ArrayList<Token> program = null;

    public static boolean Analyze(ArrayList<Token> someProgram){
        program = someProgram;
        pointer = 0;
        return Function() && pointer == program.size();
    }

    private static boolean Function(){
        if(pointer >= program.size()) return true;
        if(program.get(pointer++).getLexem().equals(Lexem.func) &&
                program.get(pointer++).getLexem().equals(Lexem.lrbr) &&
                program.get(pointer++).getLexem().equals(Lexem.num) &&
                program.get(pointer++).getLexem().equals(Lexem.rrbr) &&
                program.get(pointer++).getLexem().equals(Lexem.assign)){
            if(!F()) return false;
            if(program.get(pointer++).getLexem().equals(Lexem.semicolon)){
                return true;
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
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.MIN)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
            if(!SF()) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
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
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.id)){
            pointer++;
            if(!program.get(pointer++).getLexem().equals(Lexem.lrbr)) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.num)) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.comma)) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.num)) return false;
            if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.num)){
            pointer++;
            return true;
        }
        else if(program.get(pointer).getLexem().equals(Lexem.func)) {
            pointer++;
            if(program.get(pointer).getLexem().equals(Lexem.lrbr)){
                pointer++;
                if(!args()) return false;
                if(!program.get(pointer++).getLexem().equals(Lexem.rrbr)) return false;
            }
            return true;
        }
        else return false;
    }

    private static boolean args(){
        if(SF()){
            if(!program.get(pointer).getLexem().equals(Lexem.comma)){
                return true;
            }
            else {
                pointer++;
                return args();
            }
        }
        else return false;
    }
}
