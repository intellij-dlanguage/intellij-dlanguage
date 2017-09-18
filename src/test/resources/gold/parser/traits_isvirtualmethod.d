import std.stdio;

struct S
{
    void bar() { }
}

class C
{
    void bar() { }
}

void main()
{
    writeln(__traits(isVirtualMethod, C.bar));  // true
    writeln(__traits(isVirtualMethod, S.bar));  // false
}
