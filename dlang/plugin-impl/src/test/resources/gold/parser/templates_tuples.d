template Print(args...)
{
    void print()
    {
        writeln("args are ", args); // args is an ExpressionTuple
    }
}

template Write(Args...)
{
    void write(Args args) // Args is a TypeTuple
                          // args is an ExpressionTuple
    {
        writeln("args are ", args);
    }
}

void main()
{
    //Print!(1,'a',6.8).print();                    // prints: args are 1a6.8
    //Write!(int, char, double).write(1, 'a', 6.8); // prints: args are 1a6.8
}

template print(T, Args...)
{
    void print(T first, Args args)
    {
        writeln(first);
        static if (args.length) // if more arguments
            print(args);        // recurse for remaining arguments
    }
}

void main()
{
    //print(1, 'a', 6.8);
}

import std.stdio;


R delegate(Args) partial(R, T, Args...)(R delegate(T, Args) dg, T first)
{
    // return a closure
    return (Args args) => dg(first, args);
}

void main()
{
    int plus(int x, int y, int z)
    {
        return x + y + z;
    }

    auto plus_two = partial(&plus, 2);
    writefln("%d", plus_two(6, 8)); // prints 16
}

template Foo(T)         { pragma(msg, "1"); }   // #1
template Foo(int n)     { pragma(msg, "2"); }   // #2
template Foo(alias sym) { pragma(msg, "3"); }   // #3
template Foo(Args...)   { pragma(msg, "4"); }   // #4

import std.stdio;

// Any sole template argument will never match to #4
alias foo1 = Foo!(int);          // instantiates #1
alias foo2 = Foo!(3);            // instantiates #2
alias foo3 = Foo!(std);          // instantiates #3

alias foo4 = Foo!(int, 3, std);  // instantiates #4