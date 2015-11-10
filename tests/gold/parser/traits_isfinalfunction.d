import std.stdio;

struct S
{
    void bar() { }
}

class C
{
    void bar() { }
}

class AC
{
    abstract void foo();
}

void main()
{
    writeln(__traits(isAbstractFunction, C.bar));   // false
    writeln(__traits(isAbstractFunction, S.bar));   // false
    writeln(__traits(isAbstractFunction, AC.foo));  // true
}