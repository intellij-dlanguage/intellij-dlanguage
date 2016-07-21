interface I
{
    int foo(int i)
    in { assert(i > 7); }
    out (result) { assert(result & 1); }

    void bar();
}