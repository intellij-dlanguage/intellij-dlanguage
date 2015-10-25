@('c') string s;
pragma(msg, __traits(getAttributes, s)); // prints tuple('c')

template Tuple (T...)
{
    alias Tuple = T;
}

enum EEE = 7;
@("hello") struct SSS { }
@(3) { @(4) @EEE @SSS int foo; }

alias TP = Tuple!(__traits(getAttributes, foo));

pragma(msg, TP); // prints tuple(3, 4, 7, (SSS))
pragma(msg, TP[2]); // prints 7


TP[3] a; // a is declared as an SSS

pragma(msg, __traits(getAttributes, typeof(a))); // prints tuple("hello")