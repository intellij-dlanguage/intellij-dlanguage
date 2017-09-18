int x;

template Foo(alias X)
{
    static int* p = &X;
}

void test()
{
    alias bar = Foo!(x);
    *bar.p = 3;       // set x to 3
    static int y;
    alias abc = Foo!(y);
    *abc.p = 3;       // set y to 3
}

class Foo
{
    static int p;
}

template Bar(alias T)
{
    alias q = T.p;
}

void test()
{
    alias bar = Bar!(Foo);
    bar.q = 3;  // sets Foo.p to 3
}

import std.string;

template Foo(alias X)
{
    alias y = X.toString;
}

void test()
{
    alias bar = Foo!(std.string);
    bar.y(3);   // calls std.string.toString(3)
}

int x;

template Foo(alias X)
{
    static int* p = &X;
}

template Bar(alias T)
{
    alias abc = T!(x);
}

void test()
{
    alias bar = Bar!(Foo);
    *bar.abc.p = 3;  // sets x to 3
}

int x;

template Foo(alias X)
{
    static int* p = &X;
}

template Bar(alias T)
{
    alias q = T.p;
}

void test()
{
    alias foo = Foo!(x);
    alias bar = Bar!(foo);
    *bar.q = 3;  // sets x to 3
}

template Foo(alias X, alias Y)
{
    static int i = X;
    static string s = Y;
}

void test()
{
    alias foo = Foo!(3, "bar");
    writeln(foo.i, foo.s);  // prints 3bar
}
