package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PRFunctionTest {
    // f(0) := S(S(0))
    @Test
    void f0S(){
        num number = new num(0, 0);
        Sup functionS0 = new Sup(0, new S(), new ArrayList<>(List.of(number)));
        Sup f = new Sup(0, new S(), new ArrayList<>(List.of(functionS0)));
        assertEquals(2, f.evaluate(null));

        boolean error1 = false;
        try {
            assertEquals(2, f.evaluate(new ArrayList<>(Arrays.asList(1))));
        }
        catch (Exception e){
            error1 = true;
        }

        assert error1;

        boolean error2 = false;
        try {
            assertEquals(2, f.evaluate(new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
        }
        catch (Exception e){
            error2 = true;
        }

        assert error2;
    }

    // f(0) := 1;
    @Test
    void f01() {
        num f = new num(0, 1);

        assertEquals(f.evaluate(null), 1);
        assertEquals(1, f.evaluate(new ArrayList<>(Arrays.asList(1))));
        assertEquals(1, f.evaluate(new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
    }

    // f(1) := id(1, 1);
    @Test
    void f1id11() {
        id f = new id(1, 1);

        assertEquals(1, f.evaluate(new ArrayList<>(List.of(1))));
        assertEquals(5, f.evaluate(new ArrayList<>(List.of(5))));
        assertEquals(523434534, f.evaluate(new ArrayList<>(List.of(523434534))));

        boolean error1 = false;
        try {
            assertEquals(1, f.evaluate(new ArrayList<>(Arrays.asList(1, 5))));
        }
        catch (Exception e){
            error1 = true;
        }

        assert error1;

        boolean error2 = false;
        try {
            assertEquals(2, f.evaluate(new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
        }
        catch (Exception e){
            error2 = true;
        }
        assert error2;

        boolean error3 = false;
        try {
            assertEquals(2, f.evaluate(null));
        }
        catch (Exception e){
            error3 = true;
        }

        assert error3;
    }

    // f(N) := 0
    // f(N) := 53
    @Test
    void numTest(){
        num num0 = new num(0, 0);
        num num53 = new num(0, 53);

        assertEquals(0, num0.evaluate(new ArrayList<>(List.of(1))));
        assertEquals(0, num0.evaluate(new ArrayList<>(List.of(5, 543, 32, 23))));

        assertEquals(53, num53.evaluate(new ArrayList<>(List.of(523434534))));
        assertEquals(53, num53.evaluate(new ArrayList<>(List.of(0, 54, 4))));
    }

    // f(1) := Pr[0; 1];
    @Test
    void f1Pr01(){
        num num0 = new num(1, 0);
        num num1 = new num(1, 1);
        Pr f = new Pr(1, num0, num1);

        assertEquals(1, f.evaluate(new ArrayList<>(List.of(1))));
        assertEquals(1, f.evaluate(new ArrayList<>(List.of(5))));
        assertEquals(1, f.evaluate(new ArrayList<>(List.of(523434534))));
        assertEquals(0, f.evaluate(new ArrayList<>(List.of(0))));

        boolean error1 = false;
        try {
            assertEquals(1, f.evaluate(new ArrayList<>(Arrays.asList(1, 5))));
        }
        catch (Exception e){
            error1 = true;
        }

        assert error1;

        boolean error2 = false;
        try {
            assertEquals(2, f.evaluate(new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
        }
        catch (Exception e){
            error2 = true;
        }
        assert error2;

        boolean error3 = false;
        try {
            assertEquals(2, f.evaluate(null));
        }
        catch (Exception e){
            error3 = true;
        }

        assert error3;
    }

    // f(0) := MIN(0);
    @Test
    void f0MIN0(){
        num num0 = new num(0, 0);
        Min f = new Min(0, num0);

        assertEquals(0, f.evaluate(null));

        boolean error1 = false;
        try {
            assertEquals(1, f.evaluate(new ArrayList<>(Arrays.asList(1, 5))));
        }
        catch (Exception e){
            error1 = true;
        }
        assert error1;
    }

    // f2 := id(2, 2);
    // f1 := MIN(f2);
    @Test
    void f1Minf2(){
        Min f1 = new Min(1, new id(2, 2));

        assertEquals(0, f1.evaluate(new ArrayList<>(List.of(1))));
        assertEquals(0, f1.evaluate(new ArrayList<>(List.of(52323))));
        assertEquals(0, f1.evaluate(new ArrayList<>(List.of(233))));
        assertEquals(0, f1.evaluate(new ArrayList<>(List.of(2332335))));

        boolean error1 = false;
        try {
            assertEquals(1, f1.evaluate(new ArrayList<>(Arrays.asList(1, 5))));
        }
        catch (Exception e){
            error1 = true;
        }
        assert error1;
    }

    // d(5) = 4; d(0) = 0
    @Test
    void d(){
        // d(id(1, 1)) := Pr[0;
        Pr d = new Pr(1, new num(0, 0), new id(2, 1));
        assertEquals(0, d.evaluate(new ArrayList<>(List.of(0))));
        assertEquals(0, d.evaluate(new ArrayList<>(List.of(1))));
        assertEquals(4, d.evaluate(new ArrayList<>(List.of(5))));
        assertEquals(344, d.evaluate(new ArrayList<>(List.of(345))));
    }

    @Test
    void sum(){
        Pr sum = new Pr(2, new id(1, 1), new Sup(3, new S(), new ArrayList<>(List.of(new id(3, 3)))));
        assertEquals(0, sum.evaluate(new ArrayList<>(List.of(0, 0))));
        assertEquals(1, sum.evaluate(new ArrayList<>(List.of(0, 1))));
        assertEquals(10, sum.evaluate(new ArrayList<>(List.of(5, 5))));
        assertEquals(1000, sum.evaluate(new ArrayList<>(List.of(543, 457))));
    }

    @Test
    void sub(){
        Pr d = new Pr(1, new num(0, 0), new id(2, 1));
        Pr sub = new Pr(2, new id(1, 1), new Sup(3, d, new ArrayList<PRFunction>(List.of(new id(3, 3)))));
        assertEquals(0, sub.evaluate(new ArrayList<>(List.of(0, 0))));
        assertEquals(4, sub.evaluate(new ArrayList<>(List.of(5, 1))));
        assertEquals(50, sub.evaluate(new ArrayList<>(List.of(55, 5))));
        assertEquals(0, sub.evaluate(new ArrayList<>(List.of(0, 50))));
        assertEquals(0, sub.evaluate(new ArrayList<>(List.of(49, 50))));
        assertEquals(1, sub.evaluate(new ArrayList<>(List.of(51, 50))));
    }
}