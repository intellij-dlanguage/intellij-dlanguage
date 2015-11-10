ref int foo()
{
    auto p = new int;
    return *p;
}
...
foo() = 3;  // reference returns can be lvalues