int function() fp;

void test()
{
    static int a = 7;
    static int foo() { return a + 3; }

    fp = &foo;
}

void bar()
{
    test();
    int i = fp();       // i is set to 10
}

int abc(int x) { return x + 1; }
int def(int y) { return y + 1; }

int function() fp1 = &abc;
int function() fp2 = &def;
// Do not rely on fp1 and fp2 being different values; the compiler may merge
// them.

int delegate() dg;

void test()
{
    int a = 7;
    int foo() { return a + 3; }

    dg = &foo;
    int i = dg(); // i is set to 10
}

int* bar()
{
    int b;
    test();
    int i = dg(); // ok, test.a is in a closure and still exists
    return &b;    // error, bar.b not valid after bar() exits
}

struct Foo
{
    int a = 7;
    int bar() { return a; }
}

int foo(int delegate() dg)
{
    return dg() + 1;
}

void test()
{
    int x = 27;
    int abc() { return x; }
    Foo f;
    int i;

    i = foo(&abc);   // i is set to 28
    i = foo(&f.bar); // i is set to 8
}


