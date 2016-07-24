void foo(int[2] a)
{
    assert(a == [2, 3]);
}
void bar(ref int[2] a)
{
    assert(a == [2, 3]);
    a[0] = 4;
    a[1] = 5;
    assert(a == [4, 5]);
}
void baz(int[3] a) {}

void main()
{
    int[] arr = [1, 2, 3];

    foo(arr[1 .. 3]);
    assert(arr == [1, 2, 3]);

    bar(arr[1 .. 3]);
    assert(arr == [1, 4, 5]);

  //baz(arr[1 .. 3]); // cannot match length
}