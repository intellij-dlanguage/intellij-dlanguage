int sum(int x, int y) pure nothrow { return x + y; }

// prints ("pure", "nothrow", "@system")
pragma(msg, __traits(getFunctionAttributes, sum));

struct S
{
    void test() const @system { }
}

// prints ("const", "@system")
pragma(msg, __traits(getFunctionAttributes, S.test));

// prints ("pure", "nothrow", "@nogc", "@trusted")
pragma(msg, __traits(getFunctionAttributes, (int x) @trusted { return x * 2; }));