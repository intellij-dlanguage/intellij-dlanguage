import std.stdio;

struct S
{
    void bar() { }
}

class C
{
    void bar() { }
    final void foo();
}

final class FC
{
    void foo();
}

void main()
{
    writeln(__traits(isFinalFunction, C.bar));  // false
    writeln(__traits(isFinalFunction, S.bar));  // false
    writeln(__traits(isFinalFunction, C.foo));  // true
    writeln(__traits(isFinalFunction, FC.foo)); // true
}