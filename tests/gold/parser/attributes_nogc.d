@nogc void foo1(char[] a)
{
    auto p = new int;  // error, operator new allocates
    a ~= 'c';          // error, appending to arrays allocates
    bar();             // error, bar() may allocate
}

void bar() { }

void function() fp;
void function() @nogc gp;  // pointer to @nogc function

void foo();
@nogc void bar();

void test()
{
    fp = &foo; // ok
    fp = &bar; // ok, it's covariant
    gp = &foo; // error, not contravariant
    gp = &bar; // ok
}