extern (C) void foo(int x, int y, ...);

foo(3, 4);      // ok
foo(3, 4, 6.8); // ok, one variadic argument
foo(2);         // error, y is a required argument

extern (C) int def(...); // error, must have at least one parameter

import core.stdc.stdarg;

void test()
{
    foo(3, 4, 5);   // first variadic argument is 5
}

void foo(int x, int y, ...)
{
    va_list args;

    version (X86)
        va_start(args, y);  // y is the last named parameter
    else
    version (Win64)
        va_start(args, y);  // ditto
    else
    version (X86_64)
        va_start(args, __va_argsave);
    else
    static assert(0, "Platform not supported.");

    int z;
    va_arg(args, z);  // z is set to 5
}