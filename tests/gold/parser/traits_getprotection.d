import std.stdio;

class D
{
    export void foo() { }
    public int bar;
}

void main()
{
    D d = new D();

    auto i = __traits(getProtection, d.foo);
    writeln(i);

    auto j = __traits(getProtection, d.bar);
    writeln(j);
}