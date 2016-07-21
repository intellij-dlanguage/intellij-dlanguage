void test()
{
    static uint[2] a = [7, 8];

    foreach (ref uint u; a)
    {
        u++;
    }
    foreach (uint u; a)
    {
        writefln("%d", u);
    }
}