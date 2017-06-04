void foo(int x, int y = 3)
{
}
foo(4);   // same as foo(4, 3);

@safe unittest
{
    int foo(int num, int[] = [1,2,3]);
 }
