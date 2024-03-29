void test()
{
    foreach (char c; "ab")
    {
        writefln("'%s'", c);
    }
    foreach (wchar w; "xy")
    {
        writefln("'%s'", w);
    }
}