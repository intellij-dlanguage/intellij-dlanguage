module test;
import std.stdio;

struct Foo { }

enum Enum { RED }

void main()
{
    writeln((1+2).stringof);       // "1 + 2"
    writeln(Foo.stringof);         // "Foo"
    writeln(test.Foo.stringof);    // "Foo"
    writeln(int.stringof);         // "int"
    writeln((int*[5][]).stringof); // "int*[5u][]"
    writeln(Enum.RED.stringof);    // "cast(enum)0"
    writeln(test.myint.stringof);  // "myint"
    writeln((5).stringof);         // "5"
}
