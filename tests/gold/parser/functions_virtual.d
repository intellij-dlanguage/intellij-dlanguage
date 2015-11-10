class A
{
    int def() { ... }
    final int foo() { ... }
    final private int bar() { ... }
    private int abc() { ... }
}

class B : A
{
    override int def() { ... }  // ok, overrides A.def
    override int foo() { ... }  // error, A.foo is final
    int bar() { ... }  // ok, A.bar is final private, but not virtual
    int abc() { ... }  // ok, A.abc is not virtual, B.abc is virtual
}

void test(A a)
{
    a.def();    // calls B.def
    a.foo();    // calls A.foo
    a.bar();    // calls A.bar
    a.abc();    // calls A.abc
}

void func()
{
    B b = new B();
    test(b);
}

class A { }
class B : A { }

class Foo
{
    A test() { return null; }
}

class Bar : Foo
{
    override B test() { return null; } // overrides and is covariant with Foo.test()
}

class B
{
    int foo() { return 1; }
}
class C : B
{
    override int foo() { return 2; }

    void test()
    {
        assert(B.foo() == 1);  // translated to this.B.foo(), and
                               // calls B.foo statically.
        assert(C.foo() == 2);  // calls C.foo statically, even if
                               // the actual instance of 'this' is D.
    }
}
class D : C
{
    override int foo() { return 3; }
}
void main()
{
    auto d = new D();
    assert(d.foo() == 3);    // calls D.foo
    assert(d.B.foo() == 1);  // calls B.foo
    assert(d.C.foo() == 2);  // calls C.foo
    d.test();
}
