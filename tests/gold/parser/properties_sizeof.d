struct S
{
    int a;
    static int foo()
    {
        return a.sizeof; // returns 4
    }
}

void test()
{
    int x = S.a.sizeof; // sets x to 4
}