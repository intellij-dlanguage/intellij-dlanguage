class A
{
    int foo(int x) { ... }
}

class B : A
{
    override int foo(int x) { ... }
}

void test()
{
    B b = new B();
    bar(b);
}

void bar(A a)
{
    a.foo(1);   // calls B.foo(int)
}

class A
{
    int foo(int x) { ... }
    int foo(long y) { ... }
}

class B : A
{
    override int foo(long x) { ... }
}

void test()
{
    B b = new B();
    b.foo(1);  // calls B.foo(long), since A.foo(int) not considered
    A a = b;

    a.foo(1);  // issues runtime error (instead of calling A.foo(int))
}

class A
{
    int foo(int x) { ... }
    int foo(long y) { ... }
}

class B : A
{
    alias foo = A.foo;
    override int foo(long x) { ... }
}

void test()
{
    B b = new B();
    bar(b);
}

void bar(A a)
{
    a.foo(1);      // calls A.foo(int)
    B b = new B();
    b.foo(1);      // calls A.foo(int)
}

class A
{
    void set(long i) { }
    void set(int i)  { }
}
class B : A
{
    void set(long i) { }
}

void foo(A a)
{
    int i;
    a.set(3);   // error, use of A.set(int) is hidden by B
                // use 'alias set = A.set;' to introduce base class overload set.
    assert(i == 1);
}

void main()
{
    foo(new B);
}

class A
{
    void foo(int x = 5) { ... }
}

class B : A
{
    void foo(int x = 7) { ... }
}

class C : B
{
    void foo(int x) { ... }
}

void test()
{
    A a = new A();
    a.foo();       // calls A.foo(5)

    B b = new B();
    b.foo();       // calls B.foo(7)

    C c = new C();
    c.foo();       // error, need an argument for C.foo
}

class B
{
    void foo() pure nothrow @safe {}
}
class D : B
{
    override void foo() {}
}
void main()
{
    auto d = new D();
    pragma(msg, typeof(&d.foo));
    // prints "void delegate() pure nothrow @safe" in compile time
}