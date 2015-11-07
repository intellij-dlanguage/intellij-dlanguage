class Outer
{
    int m;

    class Inner
    {
        int foo()
        {
            return m;   // Ok to access member of Outer
        }
    }
}

void func()
{
    int m;

    class Inner
    {
        int foo()
        {
            return m; // Ok to access local variable m of func()
        }
    }
}

class Outer
{
    int m;
    static int n;

    static class Inner
    {
        int foo()
        {
            return m;   // Error, Inner is static and m needs a this
            return n;   // Ok, n is static
        }
    }
}

void func()
{
    int m;
    static int n;

    static class Inner
    {
        int foo()
        {
            return m;   // Error, Inner is static and m is local to the stack
            return n;   // Ok, n is static
        }
    }
}

class Outer
{
    class Inner { }

    static class SInner { }
}

void func()
{
    class Nested { }

    Outer o = new Outer;        // Ok
    Outer.Inner oi = new Outer.Inner;   // Error, no 'this' for Outer
    Outer.SInner os = new Outer.SInner; // Ok

    Nested n = new Nested;      // Ok
}

class Outer
{
    int a;

    class Inner
    {
        int foo()
        {
            return a;
        }
    }
}

int bar()
{
    Outer o = new Outer;
    o.a = 3;
    Outer.Inner oi = o.new Inner;
    return oi.foo();    // returns 3
}

class Outer
{
    class Inner
    {
        Outer foo()
        {
            return this.outer;
        }
    }

    void bar()
    {
        Inner i = new Inner;
        assert(this == i.foo());
    }
}

void test()
{
    Outer o = new Outer;
    o.bar();
}