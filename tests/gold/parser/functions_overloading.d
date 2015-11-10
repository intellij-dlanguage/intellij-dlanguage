class A { }
class B : A { }
class C : B { }
void foo(A);
void foo(B);

void test()
{
    C c;
    /* Both foo(A) and foo(B) match with implicit conversion rules.
     * Applying partial ordering rules,
     * foo(B) cannot be called with an A, and foo(A) can be called
     * with a B. Therefore, foo(B) is more specialized, and is selected.
     */
    foo(c); // calls foo(B)
}