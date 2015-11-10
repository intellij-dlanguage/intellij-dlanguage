void fooref(ref int x)
{
    static assert(__traits(isRef, x));
    static assert(!__traits(isOut, x));
    static assert(!__traits(isLazy, x));
}

void fooout(out int x)
{
    static assert(!__traits(isRef, x));
    static assert(__traits(isOut, x));
    static assert(!__traits(isLazy, x));
}

void foolazy(lazy int x)
{
    static assert(!__traits(isRef, x));
    static assert(!__traits(isOut, x));
    static assert(__traits(isLazy, x));
}