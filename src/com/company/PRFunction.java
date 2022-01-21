package com.company;

import java.util.ArrayList;
import java.util.function.Function;

public class PRFunction {
    public PRFunction(PRFunction prFunction) {
        this.function = prFunction.function;
        this.functionClass = prFunction.functionClass;
        this.argumentNumber = prFunction.argumentNumber;
    }

    public Integer evaluate(ArrayList<Integer> values){
        return function.apply(values);
    }

    protected Function<ArrayList<Integer>, Integer> function = null;
    private FunctionClass functionClass = null;
    private Integer argumentNumber = null;

    public PRFunction(FunctionClass functionClass, Integer argumentNumber, Function<ArrayList<Integer>, Integer> function){
        this.functionClass = functionClass;
        this.argumentNumber = argumentNumber;
        this.function = function;
    }

    public FunctionClass getFunctionClass() {
        return functionClass;
    }

    public Integer getArgumentNumber() {
        return argumentNumber;
    }
}

class S extends PRFunction {
    public S() {
        super(
                FunctionClass.S,
                1,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> integers) {
                        if(integers.size() == 1) {
                            return integers.get(0) + 1;
                        }
                        else{
                            throw new RuntimeException("Incorrect number of arguments in the function in S()");
                        }
                    }
                }
        );
    }
}

class id extends PRFunction{
    public id(Integer argumentNumber, Integer argumentIndex) {
        super(
                FunctionClass.id,
                argumentNumber,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> integers) {
                        if(integers != null && argumentNumber == integers.size()){
                            return integers.get(argumentIndex - 1); // Numbering starts from 1
                        }
                        else{
                            throw new RuntimeException("Incorrect number of arguments in the function in id");
                        }
                    }
                });
    }
}

class num extends PRFunction{
    public num(Integer argumentNumber, Integer number) {
        super(
                FunctionClass.num,
                argumentNumber,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> integers) {
                        return number;
                    }
                });
    }
}

class Pr extends PRFunction {
    public Pr(Integer argumentNumber, PRFunction first, PRFunction second) {
        super(
                FunctionClass.Pr,
                argumentNumber,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> args) {
                        ArrayList<Integer> integers = new ArrayList<>();
                        if(args != null) integers.addAll(args);
                        if(argumentNumber != integers.size()){
                            throw new RuntimeException("Incorrect number of arguments in the function in Pr");
                        }
                        ArrayList<Integer> arrayListForFirst = new ArrayList<>(integers);
                        arrayListForFirst.remove(arrayListForFirst.size() - 1);
                        Integer result = first.evaluate(arrayListForFirst);
                        integers.add(result);
                        Integer roof = integers.get(integers.size() - 2);

                        for(integers.set(integers.size() - 2, 0); // We start numbering from scratch
                            integers.get(integers.size() - 2) < roof;
                            integers.set(integers.size() - 2, integers.get(integers.size() - 2) + 1))
                        {
                            integers.set(integers.size() - 1, second.evaluate(integers));
                        }
                        return integers.get(integers.size() - 1);
                    }
                });
        if(argumentNumber < 1) throw new RuntimeException("Primitive recursion has too few arguments (should be > 0).");
    }
}

class Sup extends PRFunction {
    public Sup(Integer argumentNumber, PRFunction toFunction, ArrayList<PRFunction> fromFunctions) {
        super(
                FunctionClass.Sup,
                argumentNumber,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> args) {
                        ArrayList<Integer> integers = new ArrayList<>();
                        if(args != null) integers.addAll(args);
                        ArrayList<Integer> newIntegers = new ArrayList<>();
                        for(PRFunction prFunction : fromFunctions){
                            newIntegers.add(prFunction.evaluate(integers));
                        }
                        return toFunction.evaluate(newIntegers);
                    }
                });
    }
}

class Min extends PRFunction {
    public Min(Integer argumentNumber, PRFunction someFunction) {
        super(
                FunctionClass.Min,
                argumentNumber,
                new Function<>() {
                    @Override
                    public Integer apply(ArrayList<Integer> args) {
                        ArrayList<Integer> integers = new ArrayList<>();
                        if(args != null) integers.addAll(args);
                        if(argumentNumber != integers.size()){
                            throw new RuntimeException("Incorrect number of arguments in the function in Min");
                        }
                        integers.add(0);
                        while(someFunction.evaluate(integers) != 0){
                            integers.set(integers.size() - 1, integers.get(integers.size() - 1) + 1);
                        }
                        return integers.get(integers.size() - 1);
                    }
                });
    }
}