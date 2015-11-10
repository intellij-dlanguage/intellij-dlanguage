import std.stdio;

struct S
{
    int mx;
    static int my;
}

void main()
{
    S s;

    __traits(getMember, s, "mx") = 1;  // same as s.mx=1;
    writeln(__traits(getMember, s, "m" ~ "x")); // 1

    __traits(getMember, S, "mx") = 1;  // error, no this for S.mx
    __traits(getMember, S, "my") = 2;  // ok
}