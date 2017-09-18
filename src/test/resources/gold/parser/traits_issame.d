import std.stdio;

struct S { }

int foo();
int bar();

void main()
{
    writeln(__traits(isSame, foo, foo)); // true
    writeln(__traits(isSame, foo, bar)); // false
    writeln(__traits(isSame, foo, S));   // false
    writeln(__traits(isSame, S, S));     // true
    writeln(__traits(isSame, std, S));   // false
    writeln(__traits(isSame, std, std)); // true
}
