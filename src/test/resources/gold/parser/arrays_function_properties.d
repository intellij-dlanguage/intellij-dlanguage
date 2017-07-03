int[] array;
void foo(int[] a, int x);

unittest
{
foo(array, 3);
array.foo(3);   // means the same thing
}
