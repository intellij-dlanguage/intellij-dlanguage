import std.stdio;

struct S
{
    int m;
}

void main()
{
    S s;

    writeln(__traits(hasMember, S, "m")); // true
    writeln(__traits(hasMember, s, "m")); // true
    writeln(__traits(hasMember, S, "y")); // false
    writeln(__traits(hasMember, int, "sizeof")); // true
}
