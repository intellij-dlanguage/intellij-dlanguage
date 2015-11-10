class C
{
    int num;

    this(int n) { num = n; }

    template Foo()
    {
        // 'foo' can access 'this' reference of class C object.
        void foo(int n) { this.num = n; }
    }
}

void main()
{
    auto c = new C(1);
    assert(c.num == 1);

    c.Foo!().foo(5);
    assert(c.num == 5);

    template Bar()
    {
        // 'bar' can access local variable of 'main' function.
        void bar(int n) { c.num = n; }
    }
    Bar!().bar(10);
    assert(c.num == 10);
}

template Foo(alias sym)
{
    void foo() { sym = 10; }
}

class C
{
    int num;

    this(int n) { num = n; }

    void main()
    {
        assert(this.num == 1);

        alias fooX = Foo!(C.num).foo;

        // fooX will become member function implicitly, so &fooX returns delegate object.
        static assert(is(typeof(&fooX) == delegate));

        fooX(); // called by using valid 'this' reference.
        assert(this.num == 10);  // OK
    }
}

void main()
{
    new C(1).main();

    int num;
    alias fooX = Foo!num.foo;

    // fooX will become nested function implicitly, so &fooX returns delegate object.
    static assert(is(typeof(&fooX) == delegate));

    fooX();
    assert(num == 10);  // OK
}

class C
{
    int num;
    this(int n) { num = n; }

    class N(T)
    {
        // instantiated class N!T can become nested in C
        T foo() { return num * 2; }
    }
}

void main()
{
    auto c = new C(10);
    auto n = c.new N!int();
    assert(n.foo() == 20);
}

void main()
{
    int num = 10;
    struct S(T)
    {
        // instantiated struct S!T can become nested in main()
        T foo() { return num * 2; }
    }
    S!int s;
    assert(s.foo() == 20);
}

struct A(alias F)
{
    int fun(int i) { return F(i); }
}

A!F makeA(alias F)() { return A!F(); }

void main()
{
    int x = 40;
    int fun(int i) { return x + i; }
    A!fun a = makeA!fun();
    assert(a.fun(2) == 42);
}

