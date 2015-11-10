import std.stdio;

abstract class C { int foo(); }

void main()
{
    C c;
    writeln(__traits(isAbstractClass, C));
    writeln(__traits(isAbstractClass, c, C));
    writeln(__traits(isAbstractClass));
    writeln(__traits(isAbstractClass, int*));
}