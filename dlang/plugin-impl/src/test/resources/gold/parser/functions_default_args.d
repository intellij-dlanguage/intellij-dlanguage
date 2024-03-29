void foo(int x, int y = 3)
{
}

@safe unittest
{
    foo(4);   // same as foo(4, 3);
    int foo(int num, int[] = [1,2,3]);
}
