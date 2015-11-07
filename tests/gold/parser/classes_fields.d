class A { int a; }
class B : A { int a; }

void foo(B b)
{
    b.a = 3;   // accesses field B.a
    b.A.a = 4; // accesses field A.a
}