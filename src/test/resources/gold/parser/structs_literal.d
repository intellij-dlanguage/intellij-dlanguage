struct S { int x; float y; }

int foo(S s) { return s.x; }

unittest
{
    foo( S(1, 2) ); // set field x to 1, field y to 2
}
