unittest
{

cast(foo) -p; // cast (-p) to type foo
(foo) - p;      // subtract p from foo
}

class A {  }
class B : A {  }

void test(A a, B b)
{
    B bx = a;         // error, need cast
    B bx = cast(B) a; // bx is null if a is not a B
    A ax = b;         // no cast needed
    A ax = cast(A) b; // no runtime check needed for upcast
}

