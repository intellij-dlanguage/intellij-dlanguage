import std.stdio;

class Base
{
    void foo() { }
}

class Foo : Base
{
    override void foo() { }
    void bar() { }
}

void main()
{
    writeln(__traits(isOverrideFunction, Base.foo)); // false
    writeln(__traits(isOverrideFunction, Foo.foo));  // true
    writeln(__traits(isOverrideFunction, Foo.bar));  // false
}
