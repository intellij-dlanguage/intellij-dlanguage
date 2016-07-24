alias int func(int);    // func is a alias to a function type
void foo()
{
    if (is(func[]))     // not satisfied because arrays of
                        // functions are not allowed
        writeln("satisfied");
    else
        writeln("not satisfied");


}

alias bar = short;
void foo(bar x)
{
    if (is(bar : int))   // satisfied because short can be
                         // implicitly converted to int
        writeln("satisfied");
    else
        writeln("not satisfied");
}

alias bar = short;

void test(bar x)
{
    if (is(bar == int))   // not satisfied because short is not
                          // the same type as int
        writeln("satisfied");
    else
        writeln("not satisfied");
}

alias bar = short;
void foo(bar x)
{
    static if (is(bar T))
        alias S = T;
    else
        alias S = long;

    writeln(typeid(S)); // prints "short"

}


alias bar = int;
alias abc = long*;
void foo(bar x, abc a)
{
    static if (is(bar T : int))
        alias S = T;
    else
        alias S = long;

    writeln(typeid(S));  // prints "int"

    static if (is(abc U : U*))
    {
        U u;
        writeln(typeid(typeof(u)));  // prints "long"
    }
}


alias bar = short;
enum E : byte { Emember }
void foo(bar x)
{
    static if (is(bar T == int))   // not satisfied, short is not int
        alias S = T;
    alias U = T;                   // error, T is not defined

    static if (is(E V == enum))    // satisified, E is an enum
        V v;                       // v is declared to be a byte
}

import std.stdio, std.typecons;

void main()
{
    alias Tup = Tuple!(int, string);
    alias AA = long[char[]];

    static if (is(Tup : TX!TL, alias TX, TL...))
    {
        writeln(is(TX!(int, long) == Tuple!(int, long)));  // true
        writeln(typeid(TL[0]));  // int
        writeln(typeid(TL[1]));  // immutable(char)[]
    }

    static if (is(AA T : T[U], U : const char[]))
    {
        writeln(typeid(T));  // long
        writeln(typeid(U));  // const char[]
    }

    static if (is(AA A : A[B], B : int))
    {
        assert(0);  // should not match, as B is not an int
    }

    static if (is(int[10] W : W[V], int V))
    {
        writeln(typeid(W));  // int
        writeln(V);          // 10
    }

    static if (is(int[10] X : X[Y], int Y : 5))
    {
        assert(0);  // should not match, Y should be 10
    }
}

