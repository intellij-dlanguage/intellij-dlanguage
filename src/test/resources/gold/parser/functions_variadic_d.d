int abc(char c, ...);   // one required parameter: c
int def(...);           // ok

import core.vararg;

void test()
{
    foo(3, 4, 5);   // first variadic argument is 5
}

void foo(int x, int y, ...)
{
    int z;

    z = va_arg!int(_argptr); // z is set to 5
}

import std.stdio;
import core.vararg;

class Foo { int x = 3; }
class Bar { long y = 4; }

void printargs(int x, ...)
{
    writefln("%d arguments", _arguments.length);
    for (int i = 0; i < _arguments.length; i++)
    {
        writeln(_arguments[i]);

        if (_arguments[i] == typeid(int))
        {
            int j = va_arg!(int)(_argptr);
            writefln("\t%d", j);
        }
        else if (_arguments[i] == typeid(long))
        {
            long j = va_arg!(long)(_argptr);
            writefln("\t%d", j);
        }
        else if (_arguments[i] == typeid(double))
        {
            double d = va_arg!(double)(_argptr);
            writefln("\t%g", d);
        }
        else if (_arguments[i] == typeid(Foo))
        {
            Foo f = va_arg!(Foo)(_argptr);
            writefln("\t%s", f);
        }
        else if (_arguments[i] == typeid(Bar))
        {
            Bar b = va_arg!(Bar)(_argptr);
            writefln("\t%s", b);
        }
        else
            assert(0);
    }
}

void main()
{
    Foo f = new Foo();
    Bar b = new Bar();

    writefln("%s", f);
    printargs(1, 2, 3L, 4.5, f, b);
}
