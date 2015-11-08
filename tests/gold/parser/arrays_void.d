void main()
{
    int[] data1 = [1,2,3];
    long[] data2;

    void[] arr = data1;            // OK, int[] implicit converts to void[].
    assert(data1.length == 3);
    assert(arr.length == 12);      // length is implicitly converted to bytes.

    //data1 = arr;                 // Illegal: void[] does not implicitly
                                   // convert to int[].
    int[] data3 = cast(int[]) arr; // OK, can convert with explicit cast.
    data2 = cast(long[]) arr;      // Runtime error: long.sizeof == 8, which
                                   // does not divide arr.length, which is 12
                                   // bytes.

    byte[2] x;
    int[2] y;

    void[2] a = x; // OK, lengths match
    void[2] b = y; // Error: int[2] is 8 bytes long, doesn't fit in 2 bytes.
}

