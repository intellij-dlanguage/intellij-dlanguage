void test()
{
    static uint[2] a = [7, 8];

    foreach (ref alias u; a)
    {
        u++;
    }
    foreach (immutable alias u; a)
    {
        writefln("%d", u);
    }
}
