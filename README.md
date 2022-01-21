# Partially recursive functions.

This repository provides you ability to make partially recursive functions and execute them. Here you will find an interpreter for functions.

## What partially recursive functions?
These are functions that can be obtained by using **superposition**, **minimization**, and **primitive recursion** from the basis functions.
This implementation supports only natural numbers (otherwise, they can be used to implement non-integers and negative ones).

### Basic functions.
S(x) = x + 1.

In the code, you write the same way, only instead of x you need to substitute a function, for example, S(id(2,1)).

0<sup>(0)</sup> is a const which returns 0.

In the code, you can write any constant and specify the number of arguments is not required.

id<sup>n</sup><sub>m</sub> returns an argument numbered m from n.

In the code, you should write this function using parentheses in the following form: id(n, m), where n and m are natural numbers, and n >= m.

### Primitive recursion

f<sup>(n)</sup> = Pr[g<sup>(n-1)</sup>; h<sup>(n+1)</sup>]

First you calculate function g on n-1 arguments and add this value as n+1 argument to h function.
Then Pr works like for(int i = 0; i < value of n argument of f; i++), calculate h and add this result in n+1 argument of h.
For example, use a sum(2) := Pr[id(2, 1); S(id(3, 3))];
Use sum(3, 4):
0. id(2, 1) = 3.
1. S(id(3,3)) = S(3) = 4.
2. S(id(3,3)) = S(4) = 5.
3. S(id(3,3)) = S(5) = 6.
4. S(id(3,3)) = S(6) = 7.
Result of this calculation is a 7. Indeed, 3 + 4 = 7.

In programm you need to make function like this:

f(1) := Pr[someFunc1; someFunc2];

You can use superposition in primitive recursion.

### Superposition

f<sup>(n)</sup> = g<sup>(m)</sup>(f<sub>1</sub><sup>(n)</sup>, ..., f<sub>m</sub><sup>(n)</sup>).

In simple words, composing a complex function from small components so that all functions receive exactly as many arguments as they need.
For example, let's make a function with one argument that adds 1 twice.
f(1) = S(S);

In programm you need to make function like this:

f(1) := S(id(1,1));

### Minimization

f<sup>(n)</sup> = μg<sup>(n+1)</sup>. This is an analogue of the while loop, which checks the last argument of the function. If it is equal to 0, then the work ends and the
function returns value of n+1 argument, at the time of the stop.
For example, we will use d(1) function, which subtracts one from argument, if can and sub,
which subtracts from the first argument, the second, if it does not work, then the result is 0.
d(1) = Pr[0, id(2,1)];
sub(2) = Pr[id(1,1); d(id(3,3))];
f(1) = μsub

In this case, some argument will be given to the function f as input and it will look for such a value of the second argument,
on which sub will give zero, in other words, the same argument by value.

0. f(5) := sub(5, 0) = 5.
1. sub(5, 1) = 4.
2. sub(5, 2) = 3.
3. sub(5, 3) = 2.
4. sub(5, 4) = 1.
5. sub(5, 5) = 0. So, the result of calculating this minimization is 5 (the last argument).

In programm you need to make function like this:
f(1) := MIN(sub);

This is the simplest description of the above operations, if this is not enough for you, then you can familiarize yourself with partially recursive functions in more detail here: https://en.wikipedia.org/wiki/General_recursive_function

## What provides my version of partially recursive functions?

You can use any number as a const, which any count of arguments. Otherwise, nothing new.

In programm you need to make function like this:

## How to write a code?

1. Each line is a new function.
2. Each function can contain a superposition, but it cannot contain both primitive recursion and minimization.
3. The main function is main, only it starts when the program starts.
