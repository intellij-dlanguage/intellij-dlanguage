auto a1 = [1,2,3];  // type is int[], with elements 1, 2 and 3
auto a2 = [1u,2,3]; // type is uint[], with elements 1u, 2u, and 3u

void foo(long[2] a)
{
    assert(a == [2, 3]);
}
void bar(ref long[2] a)
{
    assert(a == [2, 3]);
    a[0] = 4;
    a[1] = 5;
    assert(a == [4, 5]);
}
void baz(const char[3] a) {}

void main()
{
    long[] arr = [1, 2, 3];

    foo(arr[1 .. 3]);
    assert(arr == [1, 2, 3]);

    bar(arr[1 .. 3]);
    assert(arr == [1, 4, 5]);

  //baz(arr[1 .. 3]); // cannot match length
}

int[] foo()
{
    return [1, 2, 3];
}

void main2()
{
    // cast array literal
    const short[] ct = cast(short[]) [cast(byte)1, 1];
    // this is equivalent with:
    // const short[] ct = [cast(short)1, cast(short)1];
    writeln(ct);  // writes [1, 1]

    // cast other array expression
    // --> normal behavior of CastExpression
    byte[] arr = [cast(byte)1, cast(byte)1];
    short[] rt = cast(short[]) arr;
    writeln(rt);  // writes [257]
}