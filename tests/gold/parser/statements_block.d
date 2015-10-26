void func1(int x)
{
    int x;    // illegal, x shadows parameter x

    int y;

    { int y; } // illegal, y shadows enclosing scope's y

    void delegate() dg;
    dg = { int y; }; // ok, this y is not in the same function

    struct S
    {
        int y;    // ok, this y is a member, not a local
    }

    { int z; }
    { int z; }  // ok, this z is not shadowing the other z

    { int t; }
    { t++;   }  // illegal, t is undefined
}