int foo(int x)
{
    mixin("auto y =", 3 * x);
    return mixin("x + 1") * 7;  // same as ((x + 1) * 7)
}