import std.stdio;

class D
{
    this() { }
    ~this() { }
    void foo() { }
    int foo(int) { return 2; }
}

void main()
{
    D d = new D();

    foreach (t; __traits(getVirtualMethods, D, "foo"))
        writeln(typeid(typeof(t)));

    alias b = typeof(__traits(getVirtualMethods, D, "foo"));
    foreach (t; b)
        writeln(typeid(t));

    auto i = __traits(getVirtualMethods, d, "foo")[1](1);
    writeln(i);
}
