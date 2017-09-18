import std.stdio;

int main()
{
    byte[] a = [1,2,3];
    auto b = cast(int[])a; // runtime array cast misalignment

    int[] c = [1, 2, 3];
    auto d = cast(byte[])c; // ok
    // prints:
    // [1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0]
    writeln(d);
    return 0;
}
