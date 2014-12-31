import std.stdio;

void main()
{
    foreach_reverse (char c; "ab")
    {
        writefln("'%s'", c);
    }
    foreach_reverse (wchar w; "xy")
    {
        writefln("'%s'", w);
    }
}
