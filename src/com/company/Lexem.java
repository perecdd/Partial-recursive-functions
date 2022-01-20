package com.company;

public enum Lexem {
    rrbr, // )
    lrbr, // (
    S,
    Pr,
    MIN,
    lsbr, // [
    rsbr, // ]
    assign, // :=
    id,
    num,
    func, // start at any symbol, may contain digits
    semicolon, // ;
    comma // ,
}
