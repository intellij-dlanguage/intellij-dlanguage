int foo(Args...)(auto ref Args args)
{
    int result;

    foreach (i, v; args)
    {
        if (v == 10)
            assert(__traits(isRef, args[i]));
        else
            assert(!__traits(isRef, args[i]));
        result += v;
    }
    return result;
}

void main()
{
    int y = 10;
    int r;
    r = foo(8);       // returns 8
    r = foo(y);       // returns 10
    r = foo(3, 4, y); // returns 17
    r = foo(4, 5, y); // returns 19
    r = foo(y, 6, y); // returns 26
}

auto ref min(T, U)(auto ref T lhs, auto ref U rhs)
{
    return lhs > rhs ? rhs : lhs;
}

void main()
{
    int x = 7, y = 8;
    int i;

    i = min(4, 3);     // returns 3
    i = min(x, y);     // returns 7
    min(x, y) = 10;    // sets x to 10
    static assert(!__traits(compiles, min(3, y) = 10));
    static assert(!__traits(compiles, min(y, 3) = 10));
}