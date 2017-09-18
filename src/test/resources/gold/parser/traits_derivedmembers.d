import std.stdio;

class D
{
    this() { }
    ~this() { }
    void foo() { }
    int foo(int) { return 0; }
}

void main()
{
    auto a = [__traits(derivedMembers, D)];
    writeln(a);    // ["__ctor", "__dtor", "foo"]
}
