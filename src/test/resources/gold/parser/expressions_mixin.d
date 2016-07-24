int foo(int x)
{
    return mixin("x + 1") * 7;  // same as ((x + 1) * 7)
}