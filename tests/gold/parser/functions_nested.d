int bar(int a)
{
    int foo(int b)
    {
        int abc() { return 1; }

        return b + abc();
    }
    return foo(a);
}

void test()
{
    int i = bar(3); // i is assigned 4
}

void foo()
{
    void A()
    {
        B(); // error, B() is forward referenced
        C(); // error, C undefined
    }
    void B()
    {
        A(); // ok, in scope
        void C()
        {
            void D()
            {
                A();      // ok
                B();      // ok
                C();      // ok
                D();      // ok
            }
        }
    }
    A(); // ok
    B(); // ok
    C(); // error, C undefined
}


int bar(int a)
{
    int foo(int b) { return b + 1; }
    int abc(int b) { return foo(b); }   // ok
    return foo(a);
}

void test()
{
    int i = bar(3);     // ok
    int j = bar.foo(3); // error, bar.foo not visible
}

int bar(int a)
{
    int c = 3;

    int foo(int b)
    {
        b += c;       // 4 is added to b
        c++;          // bar.c is now 5
        return b + c; // 12 is returned
    }
    c = 4;
    int i = foo(a); // i is set to 12
    return i + c;   // returns 17
}

void test()
{
    int i = bar(3); // i is assigned 17
}


int bar(int a)
{
    int c = 3;

    int foo(int b)
    {
        int abc()
        {
            return c;   // access bar.c
        }
        return b + c + abc();
    }
    return foo(3);
}

int bar(int a)
{
    int c;
    static int d;

    static int foo(int b)
    {
        b = d;          // ok
        b = c;          // error, foo() cannot access frame of bar()
        return b + 1;
    }
    return foo(a);
}

struct Foo
{
    int a;

    int bar()
    {
        int c;

        int foo()
        {
            return c + a;
        }
        return 0;
    }
}


void test()
{
    void foo() { bar(); } // error, bar not defined
    void bar() { foo(); } // ok
}

void test()
{
    static struct S
    {
        static void foo() { bar(); } // ok
        static void bar() { foo(); } // ok
    }

    S.foo();  // compiles (but note the infinite runtime loop)
}

void test()
{
    void foo()() { bar(); } // ok (foo is a function template)
    void bar()   { foo(); } // ok
}

mixin template T()
{
    void foo() { bar(); } // ok
    void bar() { foo(); } // ok
}

void test()
{
    mixin T!();
}

void test()
{
    void delegate() fp;
    void foo() { fp(); }
    void bar() { foo(); }
    fp = &bar;
}