import std.stdio;
int x;
immutable int y;
const int* pz;

pure int foo(int i,
             char* p,
             const char* q,
             immutable int* s)
{
    debug writeln("in foo()"); // ok, impure code allowed in debug statement
    x = i;   // error, modifying global state
    i = x;   // error, reading mutable global state
    i = y;   // ok, reading immutable global state
    i = *pz; // error, reading const global state
    return i;
}
