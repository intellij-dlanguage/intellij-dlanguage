module foo;

import core.runtime;
import std.stdio;

struct name { string name; }

class Foo
{
    unittest
    {
        writeln("foo.Foo.unittest");
    }
}

@name("foo") unittest
{
    writeln("foo.unittest");
}

template Tuple (T...)
{
    alias Tuple = T;
}

shared static this()
{
  // Override the default unit test runner to do nothing. After that, "main" will
  // be called.
  Runtime.moduleUnitTester = { return true; };
}

void main()
{
    writeln("start main");

    alias tests = Tuple!(__traits(getUnitTests, foo));
    static assert(tests.length == 1);

    alias attributes = Tuple!(__traits(getAttributes, tests[0]));
    static assert(attributes.length == 1);

    foreach (test; tests)
        test();

    foreach (test; __traits(getUnitTests, Foo))
        test();
}
