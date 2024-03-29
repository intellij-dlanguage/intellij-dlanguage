ref int foo()
{
    auto p = new int;
    return *p;
}

unittest
{
foo() = 3;  // reference returns can be lvalues
}
